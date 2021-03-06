package group.technopark.translater.network;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import group.technopark.translater.Constants;
import group.technopark.translater.adapters.LanguageElement;

public class ResponseParser {

    public static void showErrMsg(String response, Context context) {
        if(context != null)
            try {
                JSONObject json = new JSONObject(response);
                int code = json.getInt(Constants.RESPONSE_CODE);
                String msg = json.getString(Constants.RESPONSE_MESSAGE);
                Toast.makeText(context, code + " " + msg, Toast.LENGTH_SHORT).show();
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
    }

    public static JSONArray getTranslatedText(String response) {
        JSONArray text = null;
        if(response != null)
            try {
                JSONObject json = new JSONObject(response);
                text = json.getJSONArray(Constants.RESPONSE_TEXT);
            } catch (JSONException e) {
//                showErrMsg(response, context);
            }
        return text;
    }

    public static ArrayList<String> getDirections(String response) {
        ArrayList<String> directions = null;
        if(response != null)
            try {
                JSONObject json = new JSONObject(response);
                JSONArray dirs = json.getJSONArray(Constants.RESPONSE_DIRECTIONS);
                directions = new ArrayList<String>();
                for(int i = 0; i < dirs.length(); i++) {
                    directions.add(dirs.getString(i));
                }
            } catch (JSONException e) {
//                showErrMsg(response, context);
            }
        return directions;
    }

    public static ArrayList<LanguageElement> getLanguages(String response) {
        ArrayList<LanguageElement> languages = new ArrayList<LanguageElement>();
        if(response != null) {
            try {
                JSONObject json = new JSONObject(response);
                JSONObject langs = json.getJSONObject(Constants.RESPONSE_LANGUAGES);
                Iterator<String> iter = langs.keys();
                while (iter.hasNext()) {
                    String code = iter.next();
                    languages.add(new LanguageElement(langs.getString(code), code));
                }
            } catch (JSONException e) {
//                showErrMsg(response, context);
            }
        }
        return languages;
    }

    public static LanguageElement directionForLang(String originCode, String direction, HashMap<String,String> codeToLangs) {
        int directionOriginIdx = direction.indexOf('-');
        String directionOrigin = direction.substring(0,directionOriginIdx);
        String directionDestination = direction.substring(directionOriginIdx + 1, direction.length());
        LanguageElement languageElement = null;
        if(originCode.equals(directionOrigin)) {
            languageElement = new LanguageElement(codeToLangs.get(directionDestination),directionDestination);
        }
        return languageElement;
    }
}
