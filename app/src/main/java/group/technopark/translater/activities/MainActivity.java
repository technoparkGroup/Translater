package group.technopark.translater.activities;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;

import group.technopark.translater.R;
import group.technopark.translater.adapters.LanguageElement;
import group.technopark.translater.fragments.SplashScreen;

public class MainActivity extends Activity implements FragmentController {

    private SplashScreen splashScreen = new SplashScreen();

    private static ArrayList<LanguageElement> languages;
    private static HashMap<LanguageElement, ArrayList<LanguageElement>> langWithDirections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            setFragment(R.id.container, splashScreen);
        }
    }

    @Override
    public void setFragment(int container, Fragment fragment) {
        getFragmentManager().beginTransaction()
                .replace(container, fragment)
                .commit();
    }

    public static void setLanguageList(ArrayList<LanguageElement> langList) {
        languages = langList;
    }

    public static void setLangToDirMap(HashMap<LanguageElement, ArrayList<LanguageElement>> map) {
        langWithDirections = map;
    }

    public static ArrayList<LanguageElement> getLanguages() {
        return languages;
    }

    public static HashMap<LanguageElement, ArrayList<LanguageElement>> getLangWithDirections() {
        return langWithDirections;
    }
}
