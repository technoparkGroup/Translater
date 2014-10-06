package group.technopark.translater.network;

import android.app.IntentService;
import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONException;

import group.technopark.translater.Constants;

public class TranslatingService extends IntentService {

    public TranslatingService() {
        super("Translating service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String origin = intent.getStringExtra(Constants.BUNDLE_ORIGIN);
        String destination = intent.getStringExtra(Constants.BUNDLE_DESTINATION);
        String text = intent.getStringExtra(Constants.BUNDLE_TEXT);
        String response = Helpers.makeRequest(URLMaker.getTranslateUrl(origin, destination, text), null);
        JSONArray s = ResponseParser.getTranslatedText(response, this);
        StringBuilder translation = new StringBuilder();
        try {
            for(int i = 0; i < s.length(); i++) {
                translation.append(s.getString(i))
                        .append('\n');
            }
            Intent broadcastIntent = new Intent(Constants.BROADCAST);
            broadcastIntent.putExtra(Constants.BUNDLE_TEXT, translation.toString());
            sendBroadcast(broadcastIntent);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
