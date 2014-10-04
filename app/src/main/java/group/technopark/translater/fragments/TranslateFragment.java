package group.technopark.translater.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import group.technopark.translater.R;
import group.technopark.translater.adapters.LanguageElement;
import group.technopark.translater.network.RequestTask;

public class TranslateFragment extends Fragment implements View.OnClickListener{

    public static final String LANGUAGE_ELEMENT = "language_element";
    public static final String TEXT_TO_TRANSLATE = "text_to_translate";
    public static final String TRANSLATED_TEXT = "translated_text";

    private LanguageElement languageElement;
    private Button translate;
    private TextView translatedText;
    private TextView textToTranslate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        languageElement = bundle.getParcelable(LanguagesList.SELECTED_LANGUAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_translate, container, false);
        translate = (Button)layout.findViewById(R.id.btn_translate);
        translate.setOnClickListener(this);
        translatedText = (TextView)layout.findViewById(R.id.translated_text);
        textToTranslate = (TextView)layout.findViewById(R.id.text_to_translate);
        textToTranslate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                RequestTask task = new RequestTask(languageElement, new LanguageElement("asd", "ru"),translatedText, TranslateFragment.this.getActivity());
                task.execute(s.toString());
            }
        });
        if (savedInstanceState != null){
            languageElement = savedInstanceState.getParcelable(LANGUAGE_ELEMENT);
            textToTranslate.setText(savedInstanceState.getString(TEXT_TO_TRANSLATE));
            translatedText.setText(savedInstanceState.getString(TRANSLATED_TEXT));
        }
        return layout;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(LANGUAGE_ELEMENT, languageElement);
        outState.putString(TEXT_TO_TRANSLATE, textToTranslate.getText().toString());
        outState.putString(TRANSLATED_TEXT, translatedText.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_translate:
                RequestTask task = new RequestTask(languageElement, new LanguageElement("asd", "ru"), translatedText, getActivity());
                task.execute(textToTranslate.getText().toString());
                break;
        }
    }
}
