import org.bson.Document;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;

public class PlayerInfoDto {
    int PlayerCount = 1;
    public String player_id;
    public int playerWins;

    public PlayerInfoDto(String player_id, int playerWins){
        this.player_id = player_id;
        this.playerWins = playerWins;

    }

    public String getPlayer_id(){
        return player_id;
    }

    public int getPlayerWins(){
        return playerWins;
    }

    @Override
    public String toString() {
        return "player_id: " + player_id + ", playerWins: " + playerWins;
    }

}
