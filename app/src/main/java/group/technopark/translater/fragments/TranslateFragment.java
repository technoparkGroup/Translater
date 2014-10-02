package group.technopark.translater.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import group.technopark.translater.R;
import group.technopark.translater.adapters.LanguageElement;

public class TranslateFragment extends Fragment implements View.OnClickListener{

    private LanguageElement languageElement;
    private ImageButton translate;
    private TextView translatedText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Bundle bundle = getArguments();
        languageElement = bundle.getParcelable(LanguagesList.SELECTED_LANGUAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_translate, container, false);
        translate = (ImageButton)layout.findViewById(R.id.swap);
        translate.setOnClickListener(this);
        translatedText = (TextView)layout.findViewById(R.id.translated_text);
        return layout;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.swap:
                translatedText.setText("TRANSLATED MUTHAFAKA");
                break;
        }
    }
}
