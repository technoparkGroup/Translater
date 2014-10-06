package group.technopark.translater.network;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import group.technopark.translater.Constants;

public class URLMaker {

    public static String getTranslateUrl(String originCode, String destinationCode, String text) {
        String url = "https://translate.yandex.net/api/v1.5/tr.json/translate?";
        url += "key=" + Constants.KEY;
        try {
            url += "&text=" + URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        url += "&lang=" + originCode + "-" + destinationCode;
        Log.d("URL", url);
        return url;
    }

    public static String getLanguageUrl() {
        String url = "https://translate.yandex.net/api/v1.5/tr.json/getLangs?";
        url += "key=" + Constants.KEY;
        url += "&ui=ru";
        return url;
    }


}
