

//a constructor to take two player
//

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebSocket
public class TicTacToe {
    int RoomID;
    static Map<Session, Session> sessionMap = new ConcurrentHashMap<>();
    static int[] board = new int[9];

    public static void broadcast(String message) {
        sessionMap.keySet().stream().filter(Session::isOpen).forEach(session -> {
            try {
                session.getRemote().sendString(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }





    public TicTacToe(PlayerDto player1, PlayerDto player2, int ID){
        sessionMap.put(player1.client, player1.client);
        sessionMap.put(player2.client, player2.client);
        RoomID = ID;

        broadcast("true");

    }

    public void test(){
        System.out.println("Hello");
    }


}
