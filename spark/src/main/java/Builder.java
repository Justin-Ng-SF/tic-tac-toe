import org.bson.Document;
import org.eclipse.jetty.websocket.api.Session;

public class Builder {
    int playerCount = 0;
    public Session client = null;
    public Document clientData = null;

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    public void setClient(Session client) {
        this.client = client;
    }

    public void setClientData(Document clientData) {
        this.clientData = clientData;
    }

    public PlayerDto build(){
        PlayerDto data = new PlayerDto();
       // data.PlayerCount = playerCount;
        data.client      = client;
        data.clientData  = clientData;
        return data;

    }
}
