package group.technopark.translater.fragments;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import group.technopark.translater.Constants;
import group.technopark.translater.R;
import group.technopark.translater.activities.MainActivity;
import group.technopark.translater.adapters.LanguageAdapter;
import group.technopark.translater.adapters.LanguageElement;
import group.technopark.translater.network.MyBroadcastReciever;
import group.technopark.translater.network.TranslatingService;

public class TranslateFragment
        extends Fragment
        implements View.OnClickListener, MyBroadcastReciever.TextViewSetter{

    private LanguageElement languageFrom;
    private LanguageElement languageTo;
    private ImageButton swapBtn;
    private TextView translatedText;
    private EditText textToTranslate;
    private Spinner originLanguage;
    private Spinner destinationLanguage;
    private CheckBox autoTranslateCheckbox;
    private ProgressBar translateProgress;

    private LanguageAdapter destinationLanguageAdapter;
    private LanguageAdapter originLanguageAdapter;
    private MyBroadcastReciever receiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        languageFrom = bundle.getParcelable(Constants.BUNDLE_ORIGIN);

        receiver = new MyBroadcastReciever(this);
        IntentFilter filter = new IntentFilter(Constants.BROADCAST);
        getActivity().registerReceiver(receiver, filter);

        originLanguageAdapter = new LanguageAdapter
                (getActivity(), R.layout.language_spinner_element, new ArrayList<LanguageElement>(MainActivity.getLangWithDirections().keySet()));

        destinationLanguageAdapter = new LanguageAdapter
                (getActivity(), R.layout.language_spinner_element, new ArrayList<LanguageElement>(MainActivity.getLangWithDirections().get(languageFrom)));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(getActivity() != null)
            getActivity().unregisterReceiver(receiver);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_translate, container, false);

        Button translate = (Button) layout.findViewById(R.id.btn_translate);
        translate.setOnClickListener(this);

        translateProgress = (ProgressBar)layout.findViewById(R.id.translate_progress);
        translateProgress.setVisibility(View.INVISIBLE);

        swapBtn = (ImageButton)layout.findViewById(R.id.swap);
        swapBtn.setOnClickListener(this);

        destinationLanguage = (Spinner)layout.findViewById(R.id.spinner_destination_lang);
        destinationLanguage.setAdapter(destinationLanguageAdapter);
        destinationLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                languageTo = destinationLanguageAdapter.getElement(position);
                tryEnableSwap();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        originLanguage = (Spinner)layout.findViewById(R.id.spinner_origin_lang);
        originLanguage.setAdapter(originLanguageAdapter);
        originLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                languageFrom = originLanguageAdapter.getElement(position);
                ArrayList<LanguageElement> list = new ArrayList<LanguageElement>(MainActivity.getLangWithDirections().get(languageFrom));
                destinationLanguageAdapter.changeArray(list);
                if (MainActivity.getLangWithDirections().get(languageFrom).contains(languageTo)){
                    destinationLanguage.setSelection(destinationLanguageAdapter.getPositionByElement(languageTo));
                }else
                    destinationLanguage.setSelection(0);

                languageTo = destinationLanguageAdapter.getElement(destinationLanguage.getSelectedItemPosition());
                tryEnableSwap();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        autoTranslateCheckbox = (CheckBox)layout.findViewById(R.id.auto_translate);
        final SharedPreferences preferences = getActivity().getSharedPreferences(Constants.SHARED_PREFS, Context.MODE_PRIVATE);
        boolean isAutoTranslate = preferences.getBoolean(Constants.AUTO_TRANSLATE_PREFS, false);
        autoTranslateCheckbox.setChecked(isAutoTranslate);
        autoTranslateCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                preferences.edit().putBoolean(Constants.AUTO_TRANSLATE_PREFS, isChecked).apply();
            }
        });

        translatedText = (EditText)layout.findViewById(R.id.translated_text);

        textToTranslate = (EditText)layout.findViewById(R.id.text_to_translate);
        textToTranslate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (autoTranslateCheckbox.isChecked() && languageFrom != null && languageTo != null) {
                    Intent serviceIntent = new Intent(getActivity(), TranslatingService.class);
                    serviceIntent.putExtra(Constants.BUNDLE_TEXT, s.toString())
                            .putExtra(Constants.BUNDLE_DESTINATION, languageTo.getCode())
                            .putExtra(Constants.BUNDLE_ORIGIN, languageFrom.getCode());
                    getActivity().startService(serviceIntent);
                }
            }
        });

        if (savedInstanceState != null){
            languageFrom = savedInstanceState.getParcelable(Constants.BUNDLE_ORIGIN);
            textToTranslate.setText(savedInstanceState.getString(Constants.BUNDLE_TEXT));
            translatedText.setText(savedInstanceState.getString(Constants.BUNDLE_TRANSLATED));
            languageTo = savedInstanceState.getParcelable(Constants.BUNDLE_DESTINATION);
        } else {
            languageTo = (LanguageElement) destinationLanguage.getSelectedItem();
        }
        originLanguage.setSelection(originLanguageAdapter.getPosition(languageFrom));
        destinationLanguage.setSelection(destinationLanguageAdapter.getPosition(languageTo));
        tryEnableSwap();
        return layout;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(Constants.BUNDLE_ORIGIN, languageFrom);
        outState.putString(Constants.BUNDLE_TEXT, textToTranslate.getText().toString());
        outState.putString(Constants.BUNDLE_TRANSLATED, translatedText.getText().toString());
        outState.putParcelable(Constants.BUNDLE_DESTINATION, languageTo);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_translate:
                Intent serviceIntent = new Intent(getActivity(), TranslatingService.class);
                serviceIntent.putExtra(Constants.BUNDLE_TEXT, textToTranslate.getText().toString())
                             .putExtra(Constants.BUNDLE_DESTINATION, languageTo.getCode())
                             .putExtra(Constants.BUNDLE_ORIGIN, languageFrom.getCode());
                getActivity().startService(serviceIntent);

                translateProgress.setVisibility(View.VISIBLE);
                translatedText.setText("");

                break;
            case R.id.swap:
                swapLanguages();
        }
    }

    public void swapLanguages(){
        //Если swapBtn был enabled, значит можно менять местами языки
        LanguageElement element = languageFrom;
        languageFrom = languageTo;
        languageTo = element;

        //меняем местами тексты
        String toTranslate = textToTranslate.getText().toString();
        textToTranslate.setText(translatedText.getText());
        translatedText.setText(toTranslate);

        destinationLanguageAdapter.changeArray(new ArrayList<LanguageElement>(MainActivity.getLangWithDirections().get(languageFrom)));

        int spinnerLanguage = destinationLanguageAdapter.getPositionByElement(languageTo);
        destinationLanguage.setSelection(spinnerLanguage);
        spinnerLanguage = originLanguageAdapter.getPositionByElement(languageFrom);
        originLanguage.setSelection(spinnerLanguage);

        tryEnableSwap();
    }

    public void tryEnableSwap(){
        swapBtn.setVisibility(View.INVISIBLE);
        ArrayList<LanguageElement> languageElements = new ArrayList<LanguageElement>(MainActivity.getLangWithDirections().get(languageTo));
        if(languageElements.contains(languageFrom))
            swapBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void setText(String text) {
        if(translatedText != null) {
            if (translateProgress != null)
                translateProgress.setVisibility(View.INVISIBLE);
            translatedText.setText(text);
        }
    }

    @Override
    public void notifyError() {
        if(getActivity() != null) {
            translateProgress.setVisibility(View.INVISIBLE);
            Toast.makeText(getActivity(), "Произошла ошибка", Toast.LENGTH_SHORT).show();
        }
    }
}
