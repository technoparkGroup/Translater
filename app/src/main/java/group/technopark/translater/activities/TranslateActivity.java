package group.technopark.translater.activities;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import group.technopark.translater.R;
import group.technopark.translater.adapters.LanguageElement;
import group.technopark.translater.fragments.LanguagesList;
import group.technopark.translater.fragments.TranslateFragment;

public class TranslateActivity extends Activity implements FragmentController{

    private LanguageElement languageFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        Parcelable languageParcel = intent.getParcelableExtra(LanguagesList.SELECTED_LANGUAGE);
        languageFrom = (LanguageElement)languageParcel;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.translate_activity);

        if (savedInstanceState == null){
            TranslateFragment fragment = new TranslateFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(LanguagesList.SELECTED_LANGUAGE, languageFrom);
            fragment.setArguments(bundle);
            setFragment(R.id.fragment_translate, fragment);
        }
    }

    @Override
    public void setFragment(int container, Fragment fragment) {
        getFragmentManager().beginTransaction()
                .replace(container, fragment)
                .commit();
    }
}
