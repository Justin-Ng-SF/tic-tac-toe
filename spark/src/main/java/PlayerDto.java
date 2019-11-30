import org.bson.Document;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;

public class PlayerDto {
    int PlayerCount = 1;
    public Session client;
    public Document clientData;

    public PlayerDto(Session client, Document data){
        this.client = client;
        this.clientData = data;

    }

    public PlayerDto(){}


}
