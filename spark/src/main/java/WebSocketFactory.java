import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;


public class WebSocketFactory {
    MongoClient mongoClient = new MongoClient("localhost", 27017);
    MongoDatabase db = mongoClient.getDatabase("MyDatabase");
    MongoCollection<Document> myCollection = db.getCollection("MyCollection");

    public void process(ResponseDto data, Session client) {
        switch (data.type) {
            case "Register":
                Document doc = new Document("_id", data.userName).append("win", 0);
                myCollection.insertOne(doc);
                PlayerDto aPlayer = new PlayerDto(client, doc);
                WebSocketHandler.obj.addPlayer(aPlayer);
                break;

            case "gameRoomUpdate":
                gameRoom.gamingRoom.get(data.RoomID).setBoard(data.gameBoard);
               gameRoom.gamingRoom.get(data.RoomID).action();
                System.out.println(data.RoomID);

                break;
        }



    }

}
