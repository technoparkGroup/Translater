package group.technopark.translater.network;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ProgressBar;

import java.util.ArrayList;

import group.technopark.translater.R;
import group.technopark.translater.activities.FragmentController;
import group.technopark.translater.activities.MainActivity;
import group.technopark.translater.fragments.LanguagesList;

public class LoaderTask extends AsyncTask<Void, Integer, Void> implements ProgressUpdater {
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
        setProgress(0, 1);
        String response = Helpers.makeRequest(URLMaker.getLanguageUrl(), null);
        publishProgress(1);
        MainActivity.languages = ResponseParser.getLanguages(response, mContext);
        publishProgress(1);
        ArrayList<String> directions = ResponseParser.getDirections(response, mContext);
        publishProgress(2);
        MainActivity.langWithDirections = Helpers.createLangToDirectionMap(MainActivity.languages, directions);
        publishProgress(3);
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
        mCallback.setFragment(R.id.container, new LanguagesList(), "");
    }

    public void setProgress(int progress, int max){
        mBar.setMax(max);
        mBar.setProgress(progress);
    }
}
