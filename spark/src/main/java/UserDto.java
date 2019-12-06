public class UserDto {
    public String username;
    public int wins;

    public UserDto(String username, int wins){
        this.username = username;
        this.wins = wins;
    }

    public String getPlayerUser(){
        return username;
    }

    public int getWins(){
        return wins;
    }

}
