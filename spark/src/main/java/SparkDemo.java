import static spark.Spark.*;

import static com.mongodb.client.model.Filters.*;


import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class SparkDemo {
    private static MongoClient mongoClient;
    private static MongoDatabase db;
    private static MongoCollection<Document> myCollection;

  public static void main(String[] args) {
    port(1234);

    mongoClient = new MongoClient("localhost", 27017);
    db = mongoClient.getDatabase("MyDatabase");
    myCollection = db.getCollection("MyCollection");

    LeaderBoard lb = new LeaderBoard(myCollection);
    System.out.print(lb.getLeaderBoard());

    webSocket("/ws", WebSocketHandler.class);

    post("/player", (req, res) -> {
        Document doc = new Document("PlayerId", req.body());
        myCollection.insertOne(doc);
        // System.out.println("Player: " + req.body());
        return "Welcome " + req.body();
    });


  }


    public MongoDatabase getDb(){
        return db;
    }
}