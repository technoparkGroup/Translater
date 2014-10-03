package group.technopark.translater.async_task;

import android.os.AsyncTask;
import android.widget.ProgressBar;

import java.util.concurrent.TimeUnit;

import group.technopark.translater.R;
import group.technopark.translater.activities.FragmentController;
import group.technopark.translater.fragments.LanguagesList;

public class LoaderTask extends AsyncTask<Void, Integer, Void> {
    private ProgressBar mBar;
    private FragmentController mCallback;

    public LoaderTask(ProgressBar bar, FragmentController callback) {
        mBar = bar;
        mCallback = callback;
    }

    @Override
    protected Void doInBackground(Void... params) {
        int maxValue = 100;
        mBar.setProgress(0);
        mBar.setMax(maxValue);
        try {
            for(int i = 0 ; i < maxValue ; i++) {
                TimeUnit.MILLISECONDS.sleep(20);
                publishProgress(i + 1);
            }
        } catch (InterruptedException e) {
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
