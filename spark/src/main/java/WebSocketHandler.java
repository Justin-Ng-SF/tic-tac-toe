import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

@WebSocket
public class WebSocketHandler {
    static Map<Session, Session> sessionMap = new ConcurrentHashMap<>();
    static String OnlinePlayers = "0";
    static int onlineNumber = 0;
    static int[][] board = new int[3][3];

    gameRoom obj = new gameRoom();

    MongoClient mongoClient = new MongoClient("localhost", 27017);
    MongoDatabase db = mongoClient.getDatabase("MyDatabase");
    MongoCollection<Document> myCollection = db.getCollection("MyCollection");


    public static void broadcast(String message) {
        sessionMap.keySet().stream().filter(Session::isOpen).forEach(session -> {
            try {
                session.getRemote().sendString(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }





    @OnWebSocketConnect
    public void connected(Session session) throws IOException {
        System.out.println("A client has connected");
        sessionMap.put(session, session);


        System.out.println(sessionMap.size());

        broadcast((((Integer)sessionMap.size()).toString()));



        //session.getRemote().sendString((((Integer)sessionMap.size()).toString()));// and send it back
       // System.out.println("yes");

    }

    @OnWebSocketClose
    public void closed(Session session, int statusCode, String reason) {
        System.out.println("A client has disconnected");
        obj.kickPlayer(session);
        sessionMap.remove(session);
        System.out.println(sessionMap.size());
        broadcast((((Integer)sessionMap.size()).toString()));

    }

    @OnWebSocketMessage
    public void message(Session session, String message) throws IOException {
        System.out.println("Got: " + message);   // Print message

        Document doc = new Document("PlayerId", message);
        //Maybe later able to retrieve Player data from Mongodb

        myCollection.insertOne(doc);


       // OnlinePlayers = message; // save the count



        PlayerDto aPlayer = new PlayerDto(session, doc);// make a player, include it's PlayerId

        obj.addPlayer(aPlayer);// Add the player to the gameRoom for matchmaking.


      //  broadcast(message);
    }





}
