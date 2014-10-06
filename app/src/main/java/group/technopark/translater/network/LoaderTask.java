package group.technopark.translater.network;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ProgressBar;

import java.util.ArrayList;

import group.technopark.translater.Constants;
import group.technopark.translater.R;
import group.technopark.translater.activities.FragmentController;
import group.technopark.translater.activities.MainActivity;
import group.technopark.translater.adapters.LanguageElement;
import group.technopark.translater.fragments.LanguagesList;

public class LoaderTask extends AsyncTask<Void, Integer, Void> implements Helpers.ProgressUpdater {
    private ProgressBar mBar;
    private FragmentController mCallback;
    private Context mContext;
    private int maxProgress;
    private int progress;

    public LoaderTask(ProgressBar bar, FragmentController callback, Context context) {
        mBar = bar;
        mCallback = callback;
        mContext = context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try{
            maxProgress = 4;
            String response = Helpers.makeRequest(URLMaker.getLanguageUrl(), this);
            setProgress(1);
            Thread.sleep(2000);
            ArrayList<LanguageElement> languages = ResponseParser.getLanguages(response, mContext);
            setProgress(2);
            Thread.sleep(2000);
            ArrayList<String> directions = ResponseParser.getDirections(response, mContext);
            setProgress(3);
            Thread.sleep(2000);
            MainActivity.setLangToDirMap(Helpers.createLangToDirectionMap(languages, directions));
            setProgress(4);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if (mBar != null)
            mBar.setProgress(values[0]);
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
