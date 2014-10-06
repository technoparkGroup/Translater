package group.technopark.translater.activities;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;

import group.technopark.translater.Constants;
import group.technopark.translater.R;
import group.technopark.translater.adapters.LanguageElement;
import group.technopark.translater.fragments.SplashScreen;

public class MainActivity extends Activity implements FragmentController {

    private static HashMap<LanguageElement, ArrayList<LanguageElement>> langWithDirections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        restoreFragment();
    }

    @Override
    public void setFragment(int container, Fragment fragment, String tag, Boolean backStack) {
        if (backStack)
            getFragmentManager().beginTransaction()
                    .addToBackStack(null)
                    .replace(container, fragment, tag)
                    .commit();
        else
        getFragmentManager().beginTransaction()
                .replace(container, fragment, tag)
                .commit();


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void restoreFragment(){
        Fragment fragment;
        fragment = getFragmentManager().findFragmentByTag(Constants.TRANSLATE_FRAGMENT_TAG);
        if (fragment != null){
            setFragment(R.id.container, fragment, Constants.TRANSLATE_FRAGMENT_TAG, false);
            return;
        }

        fragment = getFragmentManager().findFragmentByTag(Constants.LANGUAGES_LIST_FRAGMENT_TAG);
        if (fragment != null){
            setFragment(R.id.container, fragment, Constants.LANGUAGES_LIST_FRAGMENT_TAG, false);
            return;
        }
        setFragment(R.id.container, new SplashScreen(), "", false);
    }

    public static void setLangToDirMap(HashMap<LanguageElement, ArrayList<LanguageElement>> map) {
        langWithDirections = map;
    }

    public static HashMap<LanguageElement, ArrayList<LanguageElement>> getLangWithDirections() {
        return langWithDirections;
    }
}
