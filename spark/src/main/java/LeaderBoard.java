import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.eclipse.jetty.websocket.api.WebSocketBehavior;

import java.util.ArrayList;

public class LeaderBoard {
    MongoClient mongoClient = new MongoClient("localhost", 27017);
    MongoDatabase db = mongoClient.getDatabase("MyDatabase");
    MongoCollection<Document> myCollection = db.getCollection("MyCollection");
    /*
    array of Documents containing player_id, wins
    */

    public ArrayList<PlayerDto> Player = new ArrayList<PlayerDto>();

    public LeaderBoard(PlayerDto leaderBoard[]){

    }

    public void addToLeaderBoard(Document doc){

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





    }




}
