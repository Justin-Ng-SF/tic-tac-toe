

//a constructor to take two player
//

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.mongodb.client.model.Filters.eq;


public class TicTacToe {
    public int RoomID;
    public Map<Session, Session> sessionMap = new ConcurrentHashMap<>();
    private String[] board = new String[9];
    public PlayerDto player1;
    public PlayerDto player2;
    public boolean winnerDecided;
    private int round = 9;


    MongoDatabase db = WebSocketHandler.mongoClient.getDatabase("MyDatabase");
    MongoCollection<Document> myCollection = db.getCollection("MyCollection");

    public void broadcast(String message) {
        sessionMap.keySet().stream().filter(Session::isOpen).forEach(session -> {
            try {

                session.getRemote().sendString(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    public TicTacToe(PlayerDto player1, PlayerDto player2, int ID) {
        sessionMap.put(player1.client, player1.client);
        sessionMap.put(player2.client, player2.client);
        RoomID = ID;
        winnerDecided = false;
        this.player1 = player1;
        this.player2 = player2;



        String name = player1.clientData.getString("_id");
        System.out.println(name);


        NoteDto thingToSend = new NoteDto(ID, "Matched", board, player1.clientData.getString("_id"), player2.clientData.getString("_id"));
        this.player1.turn = true;
        this.player2.turn = false;


        broadcast(ResponseDao.DAO(thingToSend));

        action();

    }

    public void action() {
        System.out.println("GameRoom number " + RoomID + " round number " + round);
        try {
            if (round == 0) {
                NoteDto thingToSend = new NoteDto("draw", 0);
                player1.client.getRemote().sendString(ResponseDao.DAO(thingToSend));
                player2.client.getRemote().sendString(ResponseDao.DAO(thingToSend));

            }

            if (player1.turn == true) {
                System.out.println("number in game" + sessionMap.keySet().stream().filter(Session::isOpen).count());

                NoteDto thingToSend = new NoteDto("Turn", true, "X", board, player1.clientData.get("_id").toString());
                player1.client.getRemote().sendString(ResponseDao.DAO(thingToSend));
                player1.turn = false;
                player2.turn = true;
                round--;
            } else {
                System.out.println("number in game" + sessionMap.keySet().stream().filter(Session::isOpen).count());
                NoteDto thingToSend = new NoteDto("Turn", true, "O", board, player2.clientData.get("_id").toString());
                player2.client.getRemote().sendString(ResponseDao.DAO(thingToSend));
                player2.turn = false;
                player1.turn = true;
                round--;


            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }


    }

    public void setBoard(String[] data) {
        this.board = data;
    }

    public void winnerDecided() {

        try {


            if (player1.turn == true) {
                String id = player1.clientData.get("_id").toString();
                System.out.println(id);
                player1.clientData = myCollection.find(eq("_id", id)).first();
                System.out.println(id + "22");
                Integer numberOfWin = Integer.valueOf(player1.clientData.get("win").toString());
                numberOfWin++;
                System.out.println("number" + numberOfWin);

                Document up = new Document("$set", new Document("win", numberOfWin));

                myCollection.updateOne(player1.clientData, up);

                NoteDto thingToSend = new NoteDto("roundEnd", 0);
                broadcast(ResponseDao.DAO(thingToSend));


                //player1.clientData = myCollection.find(player1.clientData).first();
                //player1.clientData = WebSocketHandler.myCollection.find(player1.clientData).first();
                // System.out.println("Number a of win :" + player1.clientData.get("win"));


            } else {

                String id = player2.clientData.get("_id").toString();
                System.out.println(id);
                player2.clientData = myCollection.find(eq("_id", id)).first();
                System.out.println(id + "22");

                Integer numberOfWin = Integer.valueOf(player2.clientData.get("win").toString());
                numberOfWin++;
                System.out.println("number" + numberOfWin);

                Document up = new Document("$set", new Document("win", numberOfWin));

                 myCollection.updateOne(player2.clientData, up);


                // WebSocketHandler.myCollection.updateOne(player2.clientData, new Document("$set", new Document("win", numberOfWin)));
                //player2.clientData = myCollection.find(player2.clientData).first();
                // System.out.println("Number of win :" + player2.clientData.get("win"));


            }


        } catch (Exception e) {
            System.out.println(e.toString());
        }


    }


}




