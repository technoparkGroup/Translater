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
    private TranslateFragment translateFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        Parcelable languageParcel = intent.getParcelableExtra(LanguagesList.SELECTED_LANGUAGE);
        languageFrom = (LanguageElement)languageParcel;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.translate_activity);

        if (savedInstanceState == null){
            translateFragment = new TranslateFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(LanguagesList.SELECTED_LANGUAGE, languageFrom);
            translateFragment.setArguments(bundle);
            setFragment(R.id.fragment_translate, translateFragment);
        }else {
            translateFragment = (TranslateFragment)getFragmentManager().getFragment(savedInstanceState, "translateFragment");
            setFragment(R.id.fragment_translate, translateFragment);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getFragmentManager().putFragment(outState, "translateFragment", translateFragment);
    }

    @Override
    public void setFragment(int container, Fragment fragment) {
        getFragmentManager().beginTransaction()
                .replace(container, fragment)
                .commit();
    }
}
