package group.technopark.translater.network;

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

import group.technopark.translater.LanguageElement;

public class RequestTask extends AsyncTask<Void, Void, JSONArray> {

    LanguageElement origin;
    LanguageElement destination;
    String text;
    TextView mTextView;

    public RequestTask(LanguageElement origin, LanguageElement destination, String text, TextView textView) {
        this.origin = origin;
        this.destination = destination;
        this.text = text;
        mTextView = textView;
    }

    @Override
    protected JSONArray doInBackground(Void... params) {
        HttpURLConnection connection = null;
        URLMaker urlMaker = new URLMaker(origin.getCode(), destination.getCode(), text);
        JSONArray result = null;
        try {
            URL url  = new URL(urlMaker.getUrl());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream is = connection.getInputStream();
            String response = getResponse(is);
            is.close();
            result = (new ResponseParser(response)).getMessage();
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


    private String getResponse(InputStream is) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(is));
        StringBuilder response = new StringBuilder();
        String nextLine = "";
        while ((nextLine = bf.readLine()) != null) {
            response.append(nextLine);
        }
        return response.toString();
    }
}
