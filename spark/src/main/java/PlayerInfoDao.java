import com.google.gson.*;
import org.bson.Document;
import org.eclipse.jetty.websocket.api.Session;

public class PlayerInfoDao {

    public String setToJson(PlayerDto y){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return gson.toJson(y);

    }

    public PlayerInfoDto setPlayerInfoDto (String player_id, int playerWins){
        PlayerInfoDto result = new PlayerInfoDto(player_id, playerWins);

        return result;
    }



}