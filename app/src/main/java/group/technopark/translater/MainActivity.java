package group.technopark.translater;

import android.app.Activity;
import android.os.Bundle;

import group.technopark.translater.fragments.SplashScreen;

public class MainActivity extends Activity {

    private SplashScreen splashScreen = new SplashScreen();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, splashScreen)
                    .commit();
        }
    }



}
