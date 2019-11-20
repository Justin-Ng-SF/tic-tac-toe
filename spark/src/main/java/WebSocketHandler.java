import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;

import javax.swing.text.Document;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

@WebSocket
public class WebSocketHandler {
    static Map<Session, Session> sessionMap = new ConcurrentHashMap<>();
    static String OnlinePlayers = "0";
    static int onlineNumber = 0;
    static int[][] board = new int[3][3];


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
        sessionMap.remove(session);
        System.out.println(sessionMap.size());
        broadcast((((Integer)sessionMap.size()).toString()));

    }

    @OnWebSocketMessage
    public void message(Session session, String message) throws IOException {
        System.out.println("Got: " + message);   // Print message
        OnlinePlayers = message; // save the count
      //  broadcast(message);
    }





}
