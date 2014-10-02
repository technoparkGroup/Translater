package group.technopark.translater.activities;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import group.technopark.translater.R;
import group.technopark.translater.fragments.TranslateFragmentFrom;
import group.technopark.translater.fragments.TranslateFragmentTo;

public class TranslateActivity extends Activity implements FragmentController{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.translate_activity);
        if (savedInstanceState == null){
            setFragment(R.id.fragment_translate_from, new TranslateFragmentFrom());
            setFragment(R.id.fragment_translate_to, new TranslateFragmentTo());
        }
    }

    @Override
    public void setFragment(int container, Fragment fragment) {
        getFragmentManager().beginTransaction()
                .replace(container, fragment)
                .commit();
    }
}
