import static spark.Spark.*;

import static com.mongodb.client.model.Filters.*;


import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class SparkDemo {
  public static void main(String[] args) {
    port(1234);

    MongoClient mongoClient = new MongoClient("localhost", 27017);
    MongoDatabase db = mongoClient.getDatabase("MyDatabase");
    MongoCollection<Document> myCollection = db.getCollection("MyCollection");

    webSocket("/ws", WebSocketHandler.class);


    post("/player", (req, res) -> {

      Document doc = new Document("PlayerId", req.body());

     myCollection.insertOne(doc);

      System.out.println("Player: " + req.body());



      return "kk";








            });
  }
}