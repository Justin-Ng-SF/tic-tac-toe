import org.bson.Document;
import java.util.ArrayList;

public class LeaderBoard {
    /*array of Documents containing player_id, wins
    *
    *
    *
    * */

    public ArrayList<PlayerDto> Player = new ArrayList<PlayerDto>();

    public LeaderBoard(PlayerDto leaderBoard[]){

    }

    public void addToLeaderBoard(Document doc){
        //List<String> sortedList = list.stream().sorted().collect(Collectors.toList());
/*        MongoCursor<Document> cursor = myColection.find().iterator();
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
