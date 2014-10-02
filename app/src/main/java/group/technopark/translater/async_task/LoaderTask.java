package group.technopark.translater.async_task;

import android.os.AsyncTask;
import android.widget.ProgressBar;

import java.util.concurrent.TimeUnit;

public class LoaderTask extends AsyncTask<Void, Integer, Void> {
    private ProgressBar mBar;

    public LoaderTask(ProgressBar bar) {
        mBar = bar;
    }

    @Override
    protected Void doInBackground(Void... params) {
        int maxValue = 100;
        mBar.setMax(maxValue);
        try {
            for(int i = 0 ; i < maxValue ; i++) {
                TimeUnit.MILLISECONDS.sleep(20);
                publishProgress(i);
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
        //устанговить в фрагмент список языков
    }
}
