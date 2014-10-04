package group.technopark.translater.network;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
        HttpURLConnection connection;
        JSONArray result = null;
        try {
            URL url  = new URL(URLMaker.getTranslateUrl(origin.getCode(), destination.getCode(), params[0]));
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream is = connection.getInputStream();
            String response = getResponse(is);
            is.close();
            result = ResponseParser.getTranslatedText(response, mContext);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
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

    public static String getResponse(InputStream is) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(is));
        StringBuilder response = new StringBuilder();
        String nextLine;
        while ((nextLine = bf.readLine()) != null) {
            response.append(nextLine);
        }
        return response.toString();
    }
}
