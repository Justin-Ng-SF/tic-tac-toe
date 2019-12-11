import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import static com.mongodb.client.model.Filters.*;
import org.bson.types.ObjectId;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;


public class WebSocketFactory {                   //Function to decide what action need to do

    MongoDatabase db = WebSocketHandler.mongoClient.getDatabase("MyDatabase");
    MongoCollection<Document> myCollection = db.getCollection("MyCollection");

    public void process(ResponseDto data, Session client) {
        switch (data.type) {
            case "Register": //Save user gametag into mongodb if no gamertag is in mongodb
                try {
                    Document doc = new Document("_id", data.userName).append("win", 0);
                    Document doc2 = myCollection.find(eq("_id", data.userName)).first();
                    System.out.println(doc.get("_id"));

                    if (!data.userName.equals(doc2.get("_id"))) {
                        System.out.println("New user");
                        myCollection.insertOne(doc);
                        PlayerDto aPlayer = new PlayerDto(client, doc);
                        WebSocketHandler.obj.addPlayer(aPlayer);
                    } else {
                        PlayerDto aPlayer = new PlayerDto(client, doc2);
                        WebSocketHandler.obj.addPlayer(aPlayer);
                    }


                } catch (Exception e) {
                    System.out.println("New user");
                    Document doc = new Document("_id", data.userName).append("win", 0);
                    myCollection.insertOne(doc);
                    PlayerDto aPlayer = new PlayerDto(client, doc);
                    WebSocketHandler.obj.addPlayer(aPlayer);
                }
                break;

            case "gameRoomUpdate":
                gameRoom.gamingRoom.get(data.RoomID).setBoard(data.gameBoard); //player tictactoe board save to backend, and then send to all players in the room.
                if (gameRoom.gamingRoom.get(data.RoomID).winnerDecided != true) {
                    gameRoom.gamingRoom.get(data.RoomID).action();
                }//action use to order front end to act
                System.out.println(data.RoomID);

                break;

            case "winner":
                if (gameRoom.gamingRoom.get(data.RoomID).winnerDecided == false){
                    gameRoom.gamingRoom.get(data.RoomID).winnerDecided = true;
                gameRoom.gamingRoom.get(data.RoomID).winnerDecided();
                    LeaderBoard lb = new LeaderBoard(myCollection);
                   NoteDto leaderBoard = new NoteDto("Leaderboard", lb.getLeaderBoard());
                 WebSocketHandler.broadcast(WebSocketHandler.toJson.DAO(leaderBoard));
        }

                break;

            case "newGame":
                WebSocketHandler.obj.closeGame(data.RoomID);
                break;






        }



    }

}