import org.eclipse.jetty.websocket.api.Session;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class gameRoom { //Matchmaking
      static Queue<PlayerDto> matchmakingQueue = new LinkedList<>(); //put player into queue for matchmaking
      static Map<Integer, TicTacToe> gamingRoom = new ConcurrentHashMap<>(); //Key value pair, int is for gameroom id which can use to find the specific room, tictactoe is game room
      static int RoomID = 0;


      public void addPlayer(PlayerDto client){  //PlayerDto is player profile, it contains session and doc, Add player into gamingRoom, and give a roomid for the room

           matchmakingQueue.add(client);
           System.out.println("Number in Matchmaking: " + matchmakingQueue.size());

           if(matchmakingQueue.size() >= 2) {
                PlayerDto aPlayer = matchmakingQueue.poll();
                PlayerDto aPlayer2 = matchmakingQueue.poll();
                TicTacToe aRoom = new TicTacToe(aPlayer, aPlayer2, RoomID);
                gamingRoom.put(RoomID, aRoom);
                RoomID++;
             System.out.println("Matched");
           }
      }





     public void kickPlayer(Session client){


          Iterator<PlayerDto> target = matchmakingQueue.iterator();
          while (target.hasNext()){
              PlayerDto player = target.next();
               if(player.client == client){

                   target.remove();

                    System.out.println("Player has been kicked, Player in gameRoom: " + matchmakingQueue.size() );
               }
          }
     }

     public void OpponentLeave(Session session){
          Iterator<TicTacToe> target = gamingRoom.values().iterator();        //check each game room to find the disconnected Player's room
          while (target.hasNext()){
              TicTacToe room = target.next();
              int remainUser = (int)room.sessionMap.keySet().stream().filter(Session::isOpen).count();
              NoteDto thingToSend = new NoteDto("gameOver", 0);
              try {
                  if (remainUser < 2) {

                      if (room.player1.client == session) {
                          addPlayer(room.player2);


                          room.player2.client.getRemote().sendString(ResponseDao.DAO(thingToSend));
                      } else {
                          addPlayer(room.player1);

                          room.player1.client.getRemote().sendString(ResponseDao.DAO(thingToSend));
                      }

                      gamingRoom.remove(room.RoomID);
                      System.out.println("Room closed, Player sent to matchmaking");

                  }

              }catch (Exception e){
                  System.out.println(e.toString());
              }

          }



     }


    public void closeGame(int roomID){
        PlayerDto player1 = gamingRoom.get(roomID).player1;
        PlayerDto player2 = gamingRoom.get(roomID).player2;
        gamingRoom.remove(roomID);

        NoteDto thingToSend = new NoteDto("gameOver", 0);

        try {
            player1.client.getRemote().sendString(ResponseDao.DAO(thingToSend));
            player2.client.getRemote().sendString(ResponseDao.DAO(thingToSend));
        }catch(Exception e){
            System.out.println(e.toString());
        }


       addPlayer(player1);
        addPlayer(player2);

        System.out.println("Size " + matchmakingQueue.size());



        System.out.println("Room removed " + roomID);






    }






}
