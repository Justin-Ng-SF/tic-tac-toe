

//a constructor to take two player
//

import org.eclipse.jetty.websocket.api.Session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TicTacToe {
    static Map<Session, Session> sessionMap = new ConcurrentHashMap<>();



    public TicTacToe(PlayerDto player1, PlayerDto player2){
        sessionMap.put(player1.client, player1.client);
        sessionMap.put(player2.client, player2.client);

    }


}
