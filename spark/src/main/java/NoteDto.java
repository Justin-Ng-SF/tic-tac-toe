public class NoteDto {
  public final String type;
  public final int playerCount;

  public NoteDto(String _id, int data) {
    this.type = _id;
    this.playerCount = data;
  }
}
