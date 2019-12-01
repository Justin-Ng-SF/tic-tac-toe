import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class LeaderBoard {
    /*
    array of Documents containing player_id, wins
    */
    public ArrayList<PlayerInfoDto> leaderboard = new ArrayList<>();
    public ArrayList<String> usernames = new ArrayList<>();

    public LeaderBoard(){

    }

    public void addToLeaderBoard(Document document) {
        PlayerInfoDao dao = new PlayerInfoDao();



        PlayerInfoDto playerDto = dao.setPlayerInfoDto(document.get("player_id").toString(), document.getInteger("wins"));

        //test
        playerDto = dao.setPlayerInfoDto("username", 5);

        leaderboard.add(playerDto);

        Comparator<PlayerInfoDto> compareByWins = Comparator
                .comparing(PlayerInfoDto::getPlayerWins)
                .thenComparing(PlayerInfoDto::getPlayer_id);

        List<PlayerInfoDto> playerList = leaderboard.stream()
                .sorted(compareByWins)
                .collect(Collectors.toList());


        leaderboard = null;
        leaderboard.addAll(playerList);

        usernames = null;

        for(int i=0; i<leaderboard.size(); i++){
            usernames.add(leaderboard.get(i).player_id);
        }


    }

    public String getLeaderBoard(){

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jsonParser = new JsonParser();
        JsonObject users = jsonParser.parse(String.valueOf(usernames)).getAsJsonObject();

        return gson.toJson(users);
    }

}
