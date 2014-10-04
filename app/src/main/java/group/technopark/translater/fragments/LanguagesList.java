package group.technopark.translater.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import group.technopark.translater.R;
import group.technopark.translater.activities.MainActivity;
import group.technopark.translater.activities.TranslateActivity;
import group.technopark.translater.adapters.LanguageAdapter;


public class LanguagesList extends Fragment implements AdapterView.OnItemClickListener{
    public static final String SELECTED_LANGUAGE = "selected_language";
    private ListView languages;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_languages_list, container, false);
        languages = (ListView)layout.findViewById(R.id.languages_list);
        LanguageAdapter adapter =
                new LanguageAdapter(getActivity(), R.layout.language_element_list, MainActivity.getLanguages());
        languages.setAdapter(adapter);
        languages.setOnItemClickListener(this);

        return layout;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(LanguagesList.this.getActivity(), TranslateActivity.class);
        intent.putExtra(SELECTED_LANGUAGE, MainActivity.getLanguages().get(position));
        startActivity(intent);
    }
}
