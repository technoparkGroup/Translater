package group.technopark.translater.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import group.technopark.translater.Constants;
import group.technopark.translater.R;
import group.technopark.translater.activities.FragmentController;
import group.technopark.translater.network.LoaderTask;

public class SplashScreen extends Fragment{

    ProgressBar bar;
    FragmentController controller;
    LoaderTask task;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        controller = (FragmentController)activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_splash, container, false);
        bar = (ProgressBar)layout.findViewById(R.id.progress_bar);

        bar.getProgressDrawable().setColorFilter(getResources().getColor(android.R.color.holo_blue_light), PorterDuff.Mode.SRC_IN);

        if (task == null) {
            task = new LoaderTask(bar, controller);
            task.execute();
        }else{
            if (task.getStatus() == AsyncTask.Status.FINISHED){
                controller.setFragment(R.id.container, new LanguagesList(), Constants.LANGUAGES_LIST_FRAGMENT_TAG, false);
            }
            task.setController(controller);
            task.setProgressBar(bar);
        }
        return layout;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        task.setController(null);
        task.setProgressBar(null);
    }


}
