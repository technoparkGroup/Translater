package group.technopark.translater.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import group.technopark.translater.R;
import group.technopark.translater.activities.FragmentController;
import group.technopark.translater.network.LoaderTask;

public class SplashScreen extends Fragment{

    ProgressBar bar;
    FragmentController controller;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        controller = (FragmentController)activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_splash, container, false);
        bar = (ProgressBar)layout.findViewById(R.id.progress_bar);
        LoaderTask task = new LoaderTask(bar, controller, getActivity());
        task.execute();
        return layout;
    }


}
