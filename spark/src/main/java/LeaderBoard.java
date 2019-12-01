import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class LeaderBoard {
    public MongoClient mongoClient;
    public MongoDatabase db;
    public MongoCollection<Document> myCollection;
    /*
    array of Documents containing player_id, wins
    */
    public ArrayList<PlayerInfoDto> playerList = new ArrayList<PlayerInfoDto>();

    public LeaderBoard(MongoClient mongoClient, MongoDatabase db, MongoCollection<Document> myCollection){
        this.mongoClient = mongoClient;
        this.db = db;
        this.myCollection = myCollection;
    }

    public ArrayList<PlayerInfoDto> addToLeaderBoard(Document document) {
        PlayerInfoDao dao = new PlayerInfoDao();

        PlayerInfoDto playerDto = dao.setPlayerInfoDto(document.get("player_id").toString(), document.getInteger("wins"));
        playerList.add(playerDto);
        Comparator<PlayerInfoDto> compareByWins = Comparator
                .comparing(PlayerInfoDto::getPlayerWins)
                .thenComparing(PlayerInfoDto::getPlayer_id);

        List<PlayerInfoDto> sortedPlayerList = playerList.stream()
                .sorted(compareByWins)
                .collect(Collectors.toList());
        playerList = null;
        playerList.addAll(sortedPlayerList);


        return playerList;
    }


}
