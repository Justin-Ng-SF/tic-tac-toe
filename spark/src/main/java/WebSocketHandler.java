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



    static ResponseDao toJson = new ResponseDao();


    static gameRoom obj = new gameRoom();

    WebSocketFactory choice = new WebSocketFactory();


    static MongoClient mongoClient = new MongoClient("localhost", 27017);
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

        //handler.setPlayerCount(sessionMap.size());

        // aPlayer = handler.build();

        //System.out.println(toJson.DAO(aPlayer));

        //System.out.print(lb.getLeaderBoard());

        LeaderBoard lb = new LeaderBoard(myCollection);

        NoteDto aMessage = new NoteDto("PlayerCountUpdate", sessionMap.size());
        NoteDto leaderBoard = new NoteDto("Leaderboard", lb.getLeaderBoard());




        broadcast(toJson.DAO(leaderBoard));
        broadcast(toJson.DAO(aMessage));



    }

    @OnWebSocketClose
    public void closed(Session session, int statusCode, String reason) {
        System.out.println("A client has disconnected");
        obj.kickPlayer(session);
        obj.OpponentLeave(session);
        sessionMap.remove(session);

        System.out.println(sessionMap.size());
        NoteDto aMessage = new NoteDto("PlayerCountUpdate", sessionMap.size());





        broadcast(toJson.DAO(aMessage));


    }

    @OnWebSocketMessage
    public void message(Session session, String message) throws IOException {
        System.out.println("Got: " + message);   // Print message

        ResponseDto data = toJson.DAO(message);



        choice.process(data, session);



    }





}