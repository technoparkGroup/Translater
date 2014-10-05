package group.technopark.translater.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import group.technopark.translater.R;
import group.technopark.translater.activities.MainActivity;
import group.technopark.translater.adapters.LanguageAdapter;
import group.technopark.translater.adapters.LanguageElement;


public class LanguagesList extends Fragment implements AdapterView.OnItemClickListener{
    public static final String SELECTED_LANGUAGE = "selected_language";
    private ListView languages;
    private LanguageAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_languages_list, container, false);
        languages = (ListView)layout.findViewById(R.id.languages_list);
        adapter =
                new LanguageAdapter(getActivity(), R.layout.language_element_list,
                        new ArrayList<LanguageElement>(MainActivity.getLangWithDirections().keySet()));
        languages.setAdapter(adapter);
        languages.setOnItemClickListener(this);
        languages.setEmptyView(layout.findViewById(R.id.empty_view));
        return layout;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(LanguagesList.SELECTED_LANGUAGE, adapter.getElement(position));
        TranslateFragment translateFragment = new TranslateFragment();
        translateFragment.setArguments(bundle);
        ((MainActivity) getActivity()).
                setFragment(R.id.container, translateFragment, MainActivity.TRANSLATE_FRAGMENT_TAG, true);

    }
}
