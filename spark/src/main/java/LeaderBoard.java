import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.eclipse.jetty.server.Authentication;
import spark.Spark;

import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class LeaderBoard {

    /*
    array of Documents containing player_id, wins
    */
    private static SparkDemo sparkdemo;
    private static MongoCollection<Document> myCollection;

    public ArrayList<UserDto> leaderboard = new ArrayList<>();

    UserDto test;



    public LeaderBoard( MongoCollection<Document> myCollection){
        this.myCollection = myCollection;
    }

    public void makeLeaderboard(Document document) {

    }

    public String getLeaderBoard(){
        MongoCursor<Document> cursor = myCollection.find().iterator();
        Document doc;
        StringBuilder a = new StringBuilder("");

        try {
            while (cursor.hasNext()) {
                doc = cursor.next();
                UserDto userDto = new UserDto(doc.get("_id").toString(), Integer.valueOf(doc.get("win").toString()));
                leaderboard.add(userDto);

            }
        } finally {
            cursor.close();
        }

        Comparator<UserDto> compareByWins = Comparator
                .comparing(UserDto::getPlayerUser)
                .thenComparing(UserDto::getWins);

        List<UserDto> playerList = leaderboard.stream()
                .sorted(compareByWins)
                .collect(Collectors.toList());


        Gson gson = new Gson();
        String json = gson.toJson(playerList);
        return json;
        //return a.toString();
    }

}
