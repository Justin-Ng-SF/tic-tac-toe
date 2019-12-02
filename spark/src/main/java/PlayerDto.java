import org.bson.Document;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;

public class PlayerDto {
    public Session client;
    public Document clientData;
    public boolean turn = false;

    public PlayerDto(Session client, Document data){
        this.client = client;
        this.clientData = data;

    }

    public PlayerDto(){}


}
