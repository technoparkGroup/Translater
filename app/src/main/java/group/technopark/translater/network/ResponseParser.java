package group.technopark.translater.network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ResponseParser {

    public static final String CODE = "code";
    public static final String TEXT = "text";


    private String response;

    public ResponseParser(String response) {
        this.response = response;
    }

    public JSONArray getMessage() {
        JSONArray text = null;
        try {
            JSONObject json = new JSONObject(response);
            int code = json.getInt(CODE);
            if(code == 200) {
                text = json.getJSONArray(TEXT);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return text;
    }
}
