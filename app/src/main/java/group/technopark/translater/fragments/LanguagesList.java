package group.technopark.translater.fragments;


import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import group.technopark.translater.Constants;
import group.technopark.translater.R;
import group.technopark.translater.activities.FragmentController;
import group.technopark.translater.activities.MainActivity;
import group.technopark.translater.adapters.LanguageAdapter;
import group.technopark.translater.adapters.LanguageElement;


public class LanguagesList extends Fragment implements AdapterView.OnItemClickListener{
    private LanguageAdapter adapter;
    FragmentController mCallback;
    InternetBroadcastReceiver receiver;
    boolean isRegistered = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            mCallback = (FragmentController)getActivity();
        }
        catch (ClassCastException e) {
            e.printStackTrace();
            mCallback = null;
        }
        if(MainActivity.getLangWithDirections().isEmpty() && mCallback != null) {
            IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION); //надо указать экшн
            receiver = new InternetBroadcastReceiver();
            getActivity().registerReceiver(receiver, filter);
            isRegistered = true;
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(isRegistered) {
            getActivity().unregisterReceiver(receiver);
            isRegistered = false;
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_languages_list, container, false);
        ListView languages = (ListView) layout.findViewById(R.id.languages_list);
        adapter = new LanguageAdapter(getActivity(), R.layout.language_element_list, new ArrayList<LanguageElement>(MainActivity.getLangWithDirections().keySet()));
        languages.setAdapter(adapter);
        languages.setOnItemClickListener(this);
        languages.setEmptyView(layout.findViewById(R.id.empty_view));
        return layout;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.BUNDLE_ORIGIN, adapter.getElement(position));
        TranslateFragment translateFragment = new TranslateFragment();
        translateFragment.setArguments(bundle);
        if(mCallback != null)
            mCallback.setFragment(R.id.container, translateFragment, Constants.TRANSLATE_FRAGMENT_TAG, true);
    }

    public class InternetBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            final ConnectivityManager connMgr = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            final android.net.NetworkInfo wifi = connMgr
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            final android.net.NetworkInfo mobile = connMgr
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if (wifi.isConnected() || mobile.isConnected()) {
                setSplashFragment();
            }
        }

        private void setSplashFragment() {
            if(MainActivity.getLangWithDirections().isEmpty())
                mCallback.setFragment(R.id.container, new SplashScreen(), Constants.SPLASH_FRAGMENT_TAG, false);
        }
    }


}
