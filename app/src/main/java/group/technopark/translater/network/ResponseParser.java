package group.technopark.translater.network;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import group.technopark.translater.adapters.LanguageElement;

public class ResponseParser {

    public static final String CODE = "code";
    public static final String TEXT = "text";
    public static final String DIRECTIONS = "dirs";
    public static final String LANGUAGES = "langs";
    public static final String MESSAGE = "message";

    public static void showErrMsg(String response, Context context) {
        if(context != null)
            try {
                JSONObject json = new JSONObject(response);
                int code = json.getInt(CODE);
                String msg = json.getString(MESSAGE);
                Toast.makeText(context, code + " " + msg, Toast.LENGTH_SHORT).show();
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
    }

    public static JSONArray getTranslatedText(String response, Context context) {
        JSONArray text = null;
        try {
            JSONObject json = new JSONObject(response);
            text = json.getJSONArray(TEXT);
        } catch (JSONException e) {
            showErrMsg(response, context);
        }
        return text;
    }

    public static ArrayList<String> getDirections(String response, Context context) {
        ArrayList<String> directions = null;
        try {
            JSONObject json = new JSONObject(response);
            JSONArray dirs = json.getJSONArray(DIRECTIONS);
            directions = new ArrayList<String>();
            for(int i = 0; i < dirs.length(); i++) {
                directions.add(dirs.getString(i));
            }
        } catch (JSONException e) {
            showErrMsg(response, context);
        }
        return directions;
    }

    public static ArrayList<LanguageElement> getLanguages(String response, Context context) {
        ArrayList<LanguageElement> languages = new ArrayList<LanguageElement>();
        try {
            JSONObject json = new JSONObject(response);
            JSONObject langs = json.getJSONObject(LANGUAGES);
            Iterator<String> iter = langs.keys();
            while (iter.hasNext()) {
                String code = iter.next();
                languages.add(new LanguageElement(langs.getString(code),code));
            }
        } catch (JSONException e) {
            showErrMsg(response, context);
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
