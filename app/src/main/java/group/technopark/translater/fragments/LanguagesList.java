package group.technopark.translater.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import group.technopark.translater.LanguageElement;
import group.technopark.translater.R;
import group.technopark.translater.network.RequestTask;

public class LanguagesList extends Fragment{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_languages_list, container, false);
        Button btn = (Button)layout.findViewById(R.id.button);
        final TextView textView = (TextView)layout.findViewById(R.id.translation);
        final EditText editText = (EditText)layout.findViewById(R.id.forTranslate);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                RequestTask task = new RequestTask(new LanguageElement("RuS", "en"), new LanguageElement("eng", "ru"), s.toString(), textView);
                task.execute();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(LanguagesList.this.getActivity(), TranslateActivity.class);
                //startActivity(intent);
                RequestTask task = new RequestTask(new LanguageElement("RuS", "en"), new LanguageElement("eng", "ru"), editText.getText().toString(), textView);
                task.execute();
            }
        });
        return layout;
    }
}
