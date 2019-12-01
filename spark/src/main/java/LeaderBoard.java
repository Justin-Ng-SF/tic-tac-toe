import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.eclipse.jetty.websocket.api.WebSocketBehavior;

import java.nio.file.Files;
import java.util.ArrayList;

public class LeaderBoard {
    public MongoClient mongoClient;
    public MongoDatabase db;
    public MongoCollection<Document> myCollection;
    /*
    array of Documents containing player_id, wins
    */
    public ArrayList<PlayerDto> Player = new ArrayList<PlayerDto>();

    public LeaderBoard(MongoClient mongoClient, MongoDatabase db, MongoCollection<Document> myCollection){
        this.mongoClient = mongoClient;
        this.db = db;
        this.myCollection = myCollection;
    }

    public void addToLeaderBoard(Document doc){
        LeaderBoard lb = new LeaderBoard();

        //List<String> sortedList = list.stream().sorted().collect(Collectors.toList());

/*      DAO Encap = new DAO();

        MongoCursor<Document> cursor = myColection.find().iterator();
        try {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                NoteDTO oneNote = Encap.DAO(doc.get("data").toString(), doc.getObjectId("_id").toHexString(), doc.getObjectId("_id").getTimestamp());
                noteB.setNote(oneNote);
            }
        } finally {
            cursor.close();
        }*/

        PlayerDao encap = new PlayerDao();

        MongoCursor<Document> cursor = myCollection.find().iterator();



    }

}
