import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class LeaderBoard {

    /*
    array of Documents containing player_id, wins
    */
    private static SparkDemo sparkdemo;
    private static MongoCollection<Document> myCollection;

    public ArrayList<UserDto> userDtoList = new ArrayList<>();
    public String leaderboard;

    public LeaderBoard(MongoCollection<Document> myCollection){
        this.myCollection = myCollection;
        makeLeaderboard();
    }

    public void makeLeaderboard() {
        MongoCursor<Document> cursor = myCollection.find().iterator();
        Document doc;

        try {
            while (cursor.hasNext()) {
                doc = cursor.next();
                UserDto userDto = new UserDto(doc.get("_id").toString(), Integer.valueOf(doc.get("win").toString()));
                userDtoList.add(userDto);

            }
        } finally {
            cursor.close();
        }

        Comparator<UserDto> compareByWins = Comparator
                .comparing(UserDto::getWins)
                .thenComparing(UserDto::getPlayerUser);

        List<UserDto> playerList = userDtoList.stream()
                .sorted(compareByWins.reversed())
                .limit(5)
                .collect(Collectors.toList());

        Gson gson = new Gson();
        String json = gson.toJson(playerList);
        //leaderboard = json.substring(0, 1) + "{\"type\":\"Leaderboard\"}," + json.substring(1);
        leaderboard = json;
    }

    public String getLeaderBoard(){
        return leaderboard;
    }

}
