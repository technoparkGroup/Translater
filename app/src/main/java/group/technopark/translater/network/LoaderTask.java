package group.technopark.translater.network;

import android.os.AsyncTask;
import android.widget.ProgressBar;

import java.util.ArrayList;

import group.technopark.translater.Constants;
import group.technopark.translater.R;
import group.technopark.translater.activities.FragmentController;
import group.technopark.translater.activities.MainActivity;
import group.technopark.translater.adapters.LanguageElement;
import group.technopark.translater.fragments.LanguagesList;

public class LoaderTask extends AsyncTask<Void, Integer, Void> {
    private ProgressBar mBar;
    private FragmentController mCallback;
    private int maxProgress;
    private int progress;

    public LoaderTask(ProgressBar bar, FragmentController callback) {
        mBar = bar;
        mCallback = callback;
    }

    @Override
    protected Void doInBackground(Void... params) {
        maxProgress = 4;
        progress = 0;
        setProgress(progress);
        String response = Helpers.makeRequest(URLMaker.getLanguageUrl());
        publishProgress(1);
        ArrayList<LanguageElement> languages = ResponseParser.getLanguages(response);
        publishProgress(2);
        ArrayList<String> directions = ResponseParser.getDirections(response);
        publishProgress(3);
        MainActivity.setLangToDirMap(Helpers.createLangToDirectionMap(languages, directions));
        publishProgress(4);
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if (mBar != null) {
            mBar.setProgress(values[0]);
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (mCallback != null) {
            mCallback.setFragment(R.id.container, new LanguagesList(), Constants.LANGUAGES_LIST_FRAGMENT_TAG, false);
        }
    }

    public void setProgress(int p){
        progress = p;
        if (mBar != null) {
            mBar.setMax(maxProgress);
            mBar.setProgress(progress);
        }
    }

    public void setController(FragmentController controller){
        this.mCallback = controller;
    }

    public void setProgressBar(ProgressBar bar){
        mBar = bar;
        setProgress(progress);
    }
}
