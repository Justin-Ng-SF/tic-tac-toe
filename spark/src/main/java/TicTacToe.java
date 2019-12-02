

//a constructor to take two player
//

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebSocket
public class TicTacToe {
    private int RoomID;
    private Map<Session, Session> sessionMap = new ConcurrentHashMap<>();
    private String[] board = new String[9];
    private PlayerDto player1;
    private PlayerDto player2;

    public void broadcast(String message) {
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
        this.player1 = player1;
        this.player2 = player2;

        NoteDto thingToSend = new NoteDto(ID, "Matched", board);
        this.player1.turn = true;





        broadcast(ResponseDao.DAO(thingToSend));

        action();

    }

    public void action() {
        try {

            if (player1.turn == true) {

                NoteDto thingToSend = new NoteDto("Turn", true, "X", board);
                player1.client.getRemote().sendString(ResponseDao.DAO(thingToSend));
                player1.turn = false;
                player2.turn = true;
            } else {
                NoteDto thingToSend = new NoteDto("Turn", true, "O", board);
                player2.client.getRemote().sendString(ResponseDao.DAO(thingToSend));
                player2.turn = false;
                player1.turn = true;


            }
        }catch(Exception e){
            System.out.println(e.toString());
        }



    }

    public void setBoard(String[] data){
        this.board = data;
    }


}
