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

    public ArrayList<String> getLeaderBoard(){
        return usernames;
    }

}
