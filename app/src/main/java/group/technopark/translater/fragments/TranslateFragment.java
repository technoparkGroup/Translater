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
import group.technopark.translater.activities.MainActivity;
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

    private LanguageAdapter languageAdapter;
    private ArrayList<LanguageElement> spinnerLanguageListElements = new ArrayList<LanguageElement>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.print("Fragment onCreate");
        Bundle bundle = getArguments();
        languageFrom = bundle.getParcelable(LanguagesList.SELECTED_LANGUAGE);
        spinnerLanguageListElements.addAll(MainActivity.langWithDirections.get(languageFrom));
        languageAdapter = new LanguageAdapter
                (getActivity(), R.layout.language_element_list, spinnerLanguageListElements);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_translate, container, false);

        translate = (Button)layout.findViewById(R.id.btn_translate);
        translate.setOnClickListener(this);

        swap = (ImageButton)layout.findViewById(R.id.swap);
        swap.setOnClickListener(this);

        spinner = (Spinner)layout.findViewById(R.id.spinner_select_lang);

        spinner.setAdapter(languageAdapter);
        spinner.setOnItemSelectedListener(this);

        languageTo = (LanguageElement)spinner.getSelectedItem();

        tryEnableSwap();

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
                    RequestTask task = new RequestTask(languageFrom, languageTo, translatedText, getActivity());
                    task.execute(s.toString());
                }
            }
        });

        languageFromTextView = (TextView)layout.findViewById(R.id.origin_language);
        languageFromTextView.setText(languageFrom.getTitle());

        autoTranslateCheckbox = (CheckBox)layout.findViewById(R.id.auto_translate);

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
                RequestTask task = new RequestTask(languageFrom, languageTo, translatedText, getActivity());
                task.execute(textToTranslate.getText().toString());
                break;
            case R.id.swap:
                swapLanguages();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        languageTo = languageAdapter.getElement(position);
        tryEnableSwap();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void swapLanguages(){
        //Если swap был enabled, значит можно менять местами языки
        LanguageElement element = languageFrom;
        languageFrom = languageTo;
        languageTo = element;

        String toTranslate = textToTranslate.getText().toString();
        textToTranslate.setText(translatedText.getText());
        translatedText.setText(toTranslate);

        spinnerLanguageListElements.clear();
        spinnerLanguageListElements.addAll(MainActivity.langWithDirections.get(languageFrom));
        languageAdapter.notifyDataSetChanged();
        
        int spinnerLanguage = languageAdapter.getPositionByElement(languageTo);
        spinner.setSelection(spinnerLanguage);

        languageFromTextView.setText(languageFrom.getTitle());

        tryEnableSwap();
    }

    public void tryEnableSwap(){
        swap.setVisibility(View.INVISIBLE);
        if (MainActivity.langWithDirections.containsKey(languageTo)){
            ArrayList<LanguageElement> languageElements = MainActivity.langWithDirections.get(languageTo);
            if (languageElements.contains(languageFrom))
                swap.setVisibility(View.VISIBLE);
        }
    }
}
