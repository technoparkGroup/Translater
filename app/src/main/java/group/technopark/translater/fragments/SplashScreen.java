package group.technopark.translater.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import group.technopark.translater.R;
import group.technopark.translater.activities.FragmentController;

public class SplashScreen extends Fragment{

    TextView helloWorld;
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
        helloWorld = (TextView).findViewById(R.id.hello_world);
        helloWorld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.setFragment(R.id.container, new LanguagesList());
            }
        });
        return layout;
    }



}
