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


    PlayerDto aPlayer;
    Builder handler = new Builder();
    ResponseDao toJson = new ResponseDao();


    static gameRoom obj = new gameRoom();

    WebSocketFactory choice = new WebSocketFactory();


    MongoClient mongoClient = new MongoClient("localhost", 27017);
    MongoDatabase db = mongoClient.getDatabase("MyDatabase");
    MongoCollection<Document> myCollection = db.getCollection("MyCollection");


    LeaderBoard lb = new LeaderBoard(myCollection);




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

       //handler.setPlayerCount(sessionMap.size());

       // aPlayer = handler.build();

        //System.out.println(toJson.DAO(aPlayer));

        System.out.print(lb.getLeaderBoard());

        NoteDto aMessage = new NoteDto("PlayerCountUpdate", sessionMap.size());
        //NoteDto leaderBoard = new NoteDto("Leaderboard", lb.getLeaderBoard());



        toJson.DAO(aMessage);
        //broadcast(toJson.DAO(leaderBoard));

        //broadcast(infoToJson.setToJson(leaderboard.getLeaderBoard()));
        broadcast(toJson.DAO(aMessage));

      // broadcast(toJson.DAO(aPlayer));

        //session.getRemote().sendString((((Integer)sessionMap.size()).toString()));// and send it back
       // System.out.println("yes");

    }

    @OnWebSocketClose
    public void closed(Session session, int statusCode, String reason) {
        System.out.println("A client has disconnected");
        obj.kickPlayer(session);
        sessionMap.remove(session);
        System.out.println(sessionMap.size());
        NoteDto aMessage = new NoteDto("PlayerCountUpdate", sessionMap.size());





        broadcast(toJson.DAO(aMessage));
       // handler.setPlayerCount(sessionMap.size());
     //   aPlayer = handler.build();
      //  broadcast((((Integer)sessionMap.size()).toString()));
       // broadcast(toJson.DAO(aPlayer));

    }

    @OnWebSocketMessage
    public void message(Session session, String message) throws IOException {
        System.out.println("Got: " + message);   // Print message

        ResponseDto data = toJson.DAO(message);

       // System.out.println(data.userName);

        choice.process(data, session);



       // Document doc = new Document("PlayerId", message);
        //Maybe later able to retrieve Player data from Mongodb




       // OnlinePlayers = message; // save the count

       // handler.setClient(session);
      //  handler.setClientData(doc);

      //  aPlayer = handler.build();





        //aPlayer = PlayerDto(session, doc);// make a player, include it's PlayerId

      //  obj.addPlayer(aPlayer);// Add the player to the gameRoom for matchmaking.


       // broadcast(toJson.DAO(aPlayer));
    }





}
