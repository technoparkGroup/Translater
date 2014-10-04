package group.technopark.translater.network;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import group.technopark.translater.adapters.LanguageElement;

public class RequestTask extends AsyncTask<String, Void, JSONArray> {

    LanguageElement origin;
    LanguageElement destination;
    TextView mTextView;
    Context mContext;

    public RequestTask(LanguageElement origin, LanguageElement destination, TextView textView, Context context) {
        this.origin = origin;
        this.destination = destination;
        mTextView = textView;
        mContext = context;
    }

    @Override
    protected JSONArray doInBackground(String... params) {
        String response = Helpers.makeRequest(URLMaker.getTranslateUrl(origin.getCode(), destination.getCode(), params[0]), null);
        return ResponseParser.getTranslatedText(response, mContext);
    }

    @Override
    protected void onPostExecute(JSONArray s) {
        super.onPostExecute(s);
        StringBuilder translation = new StringBuilder();
        try {
            for(int i = 0; i < s.length(); i++) {
                translation.append(s.getString(i))
                            .append('\n');
            }
            mTextView.setText(translation.toString());
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
