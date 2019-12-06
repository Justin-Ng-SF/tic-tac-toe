public class NoteDto {
  public final String type;     // let the front end know what type of information reciving
  public Integer playerCount = null; // number of player online
  public Integer roomId = null; //id specific for a game room
  public boolean turn = false;
  public String XO = "X";
  public String[] board;


  public NoteDto(String _id, int data) {  //Update player count
    this.type = _id;
    this.playerCount = data;
  }

  public NoteDto(int data, String _id, String[] board) {  //Initial new game
    this.type = _id;
    this.roomId = data;
    this.board = board;
  }

  public NoteDto(String id, boolean turn, String XO, String[] board){
    this.type = id;
    this.turn = turn;
    this.XO   = XO;
    this.board = board;
  }


}
