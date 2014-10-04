package group.technopark.translater.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import group.technopark.translater.R;
import group.technopark.translater.adapters.LanguageAdapter;
import group.technopark.translater.adapters.LanguageElement;
import group.technopark.translater.network.RequestTask;

public class TranslateFragment
        extends Fragment
        implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    public static final String LANGUAGE_ELEMENT_FROM = "language_element_from";
    public static final String LANGUAGE_ELEMENT_TO = "language_element_to";
    public static final String TEXT_TO_TRANSLATE = "text_to_translate";
    public static final String TRANSLATED_TEXT = "translated_text";

    private LanguageElement languageFrom;
    private LanguageElement languageTo;
    private Button translate;
    private ImageButton swap;
    private TextView translatedText;
    private TextView textToTranslate;
    private TextView languageFromTextView;
    private Spinner spinner;
    private CheckBox autoTranslateCheckbox;

    private ArrayList<LanguageElement> languageElements;
    private LanguageAdapter languageAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.print("Fragment onCreate");
        Bundle bundle = getArguments();
        languageFrom = bundle.getParcelable(LanguagesList.SELECTED_LANGUAGE);

        String[] langs = getResources().getStringArray(R.array.lang_title);
        String[] cods = getResources().getStringArray(R.array.lang_codes);
        languageElements = LanguageElement.getLangList(langs, cods);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_translate, container, false);

        translate = (Button)layout.findViewById(R.id.btn_translate);
        translate.setOnClickListener(this);

        swap = (ImageButton)layout.findViewById(R.id.swap);
        swap.setOnClickListener(this);

        spinner = (Spinner)layout.findViewById(R.id.spinner_select_lang);
        languageAdapter = new LanguageAdapter
                (getActivity(), R.layout.language_element_list, languageElements);
        spinner.setAdapter(languageAdapter);
        spinner.setOnItemSelectedListener(this);

        languageTo = (LanguageElement)spinner.getSelectedItem();

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
                if (autoTranslateCheckbox.isChecked()) {
                    RequestTask task = new RequestTask(languageFrom, languageTo, s.toString(), translatedText);
                    task.execute();
                }
            }
        });

        languageFromTextView = (TextView)layout.findViewById(R.id.language_from_textview);
        languageFromTextView.setText(languageFrom.getTitle());

        autoTranslateCheckbox = (CheckBox)layout.findViewById(R.id.auto_translate_checkbox);

        if (savedInstanceState != null){
            languageFrom = savedInstanceState.getParcelable(LANGUAGE_ELEMENT_FROM);
            textToTranslate.setText(savedInstanceState.getString(TEXT_TO_TRANSLATE));
            translatedText.setText(savedInstanceState.getString(TRANSLATED_TEXT));
            languageTo = savedInstanceState.getParcelable(LANGUAGE_ELEMENT_TO);
        }

        return layout;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(LANGUAGE_ELEMENT_FROM, languageFrom);
        outState.putString(TEXT_TO_TRANSLATE, textToTranslate.getText().toString());
        outState.putString(TRANSLATED_TEXT, translatedText.getText().toString());
        outState.putParcelable(LANGUAGE_ELEMENT_TO, languageTo);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_translate:
                RequestTask task = new RequestTask(languageFrom, languageTo, textToTranslate.getText().toString(),translatedText);
                task.execute();
                break;
            case R.id.swap:
                swapLanguages();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        languageTo = languageAdapter.getElement(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void swapLanguages(){
        LanguageElement element = languageFrom;
        languageFrom = languageTo;
        languageTo = element;
        String toTranslate = textToTranslate.getText().toString();
        textToTranslate.setText(translatedText.getText());
        translatedText.setText(toTranslate);
        int spinnerLanguage = languageAdapter.getPositionByElement(languageTo);
        if (spinnerLanguage >= 0)
            spinner.setSelection(spinnerLanguage);
        else
            spinner.setSelection(0);
        languageFromTextView.setText(languageFrom.getTitle());
    }
}
