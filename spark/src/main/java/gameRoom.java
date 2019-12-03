import org.eclipse.jetty.websocket.api.Session;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class gameRoom { //Matchmaking
      static Queue<PlayerDto> matchmakingQueue = new LinkedList<>(); //put player into queue for matchmaking
      static Map<Integer, TicTacToe> gamingRoom = new ConcurrentHashMap<>(); //Key value pair, int is for gameroom id which can use to find the specific room, tictactoe is game room
      int RoomID = 0;


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
               if(target.next().client == client){

                    target.remove();

                    System.out.println("Player has been kicked, Player in gameRoom: " + matchmakingQueue.size() );
               }
          }
     }






}
