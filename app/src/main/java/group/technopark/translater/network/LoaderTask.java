package group.technopark.translater.network;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ProgressBar;

import java.util.ArrayList;

import group.technopark.translater.R;
import group.technopark.translater.activities.FragmentController;
import group.technopark.translater.activities.MainActivity;
import group.technopark.translater.adapters.LanguageElement;
import group.technopark.translater.fragments.LanguagesList;

public class LoaderTask extends AsyncTask<Void, Integer, Void> implements Helpers.ProgressUpdater {
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
        setProgress(0, 3);
        String response = Helpers.makeRequest(URLMaker.getLanguageUrl(), this);
        ArrayList<LanguageElement> languages = ResponseParser.getLanguages(response, mContext);
        ArrayList<String> directions = ResponseParser.getDirections(response, mContext);
        MainActivity.setLangToDirMap(Helpers.createLangToDirectionMap(languages, directions));
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
        mCallback.setFragment(R.id.container, new LanguagesList(), MainActivity.LANGUAGES_LIST_FRAGMENT_TAG, false);
    }

    public void setProgress(int progress, int max){
        mBar.setMax(max);
        mBar.setProgress(progress);
    }
}
