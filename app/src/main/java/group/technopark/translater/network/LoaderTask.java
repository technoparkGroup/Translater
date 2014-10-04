package group.technopark.translater.network;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import group.technopark.translater.R;
import group.technopark.translater.activities.FragmentController;
import group.technopark.translater.adapters.LanguageElement;
import group.technopark.translater.fragments.LanguagesList;

public class LoaderTask extends AsyncTask<Void, Integer, Void> {
    private ProgressBar mBar;
    private FragmentController mCallback;
    private Context mContext;

    public LoaderTask(ProgressBar bar, FragmentController callback, Context context) {
        mBar = bar;
        mCallback = callback;
        mContext = context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        int maxValue = 3;
        mBar.setProgress(0);
        mBar.setMax(maxValue);
        HttpURLConnection connection;
        try {
            URL url  = new URL(URLMaker.getLanguageUrl());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream is = connection.getInputStream();
            String response = RequestTask.getResponse(is);
            is.close();
            ArrayList<LanguageElement> languages = ResponseParser.getLanguages(response, mContext);
            HashMap<String, String> codeToLang = new HashMap<String, String>(); //карта чтоб брать название по коду быстро
            for(LanguageElement element: languages) {
                codeToLang.put(element.getCode(), element.getTitle());
            }
            publishProgress(1);
            JSONArray directions = ResponseParser.getDirections(response, mContext);
            publishProgress(2);
            HashMap<LanguageElement, ArrayList<LanguageElement>> langsToDiriections = new HashMap<LanguageElement, ArrayList<LanguageElement>>();
            // создается карта. ключом карты является LanguageElement какого-либо языка. Значение карты - список всех направлений для перевода у этого языка
            for(LanguageElement language : languages) {
                ArrayList<LanguageElement> listOfDirections = new ArrayList<LanguageElement>();
                for(int i = 0; i < directions.length(); i++) {
                    LanguageElement directionOfOrigin = ResponseParser.directionForLang(language.getCode(), directions.getString(i), codeToLang);
                     if(directionOfOrigin != null) {
                         listOfDirections.add(directionOfOrigin);
                     }
                }
                if(!listOfDirections.isEmpty()) {
                    langsToDiriections.put(language, listOfDirections);
                }
            }
            publishProgress(3);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        mBar.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mCallback.setFragment(R.id.container, new LanguagesList());
    }
}
