import com.google.gson.*;
import org.bson.Document;
import org.eclipse.jetty.websocket.api.Session;

public class PlayerDao {

    public String setToJson(PlayerDto y){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return gson.toJson(y);

    }

    public PlayerDto setPlayerDto (Session client, Document data){
        PlayerDto result = new PlayerDto(client, data);

        return result;
    }

}