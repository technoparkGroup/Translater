package group.technopark.translater.network;

import android.app.IntentService;
import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONException;

import group.technopark.translater.fragments.TranslateFragment;

public class TranslatingService extends IntentService {

    public static final String TEXT = "text";
    public static final String ORIGIN = "origin";
    public static final String DESTINATION = "destination";


    public TranslatingService() {
        super("Translating service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String origin = intent.getStringExtra(ORIGIN);
        String destination = intent.getStringExtra(DESTINATION);
        String text = intent.getStringExtra(TEXT);
        String response = Helpers.makeRequest(URLMaker.getTranslateUrl(origin, destination, text), null);
        JSONArray s = ResponseParser.getTranslatedText(response, this);
        StringBuilder translation = new StringBuilder();
        try {
            for(int i = 0; i < s.length(); i++) {
                translation.append(s.getString(i))
                        .append('\n');
            }
            Intent broadcastIntent = new Intent(TranslateFragment.BROADCAST);
            broadcastIntent.putExtra(TEXT, translation.toString());
            sendBroadcast(broadcastIntent);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
