import com.google.gson.*;

public class ResponseDao {
    public static String DAO(NoteDto data){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return gson.toJson(data);

    }

    public ResponseDto DAO(String message){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jsonParser = new JsonParser();
        JsonObject obj = jsonParser.parse(message).getAsJsonObject();

        return gson.fromJson(obj, ResponseDto.class);
    }
}
