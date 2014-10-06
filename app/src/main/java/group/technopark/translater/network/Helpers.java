package group.technopark.translater.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import group.technopark.translater.Constants;
import group.technopark.translater.adapters.LanguageElement;

public class Helpers {

    public static String makeRequest(String urlStr, ProgressUpdater updater) {
        String response = null;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream is = connection.getInputStream();
            response = getResponse(is, updater);
            is.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            response = Constants.ERROR_RESPONSE;
            e.printStackTrace();
        }
        return response;
    }

    public static String getResponse(InputStream is, ProgressUpdater updater) throws IOException {
        String nextLine;
        InputStreamReader isr = new InputStreamReader(is, "UTF-8");
        BufferedReader bf = new BufferedReader(isr);

        StringBuilder response = new StringBuilder();
        while ((nextLine = bf.readLine()) != null) {
            response.append(nextLine);
        }

        return response.toString();
    }

    public static HashMap<LanguageElement, ArrayList<LanguageElement>> createLangToDirectionMap(ArrayList<LanguageElement> languages, ArrayList<String> directions) {
        HashMap<String, String> codeToLang = new HashMap<String, String>(); //карта чтоб брать название по коду быстро
        for(LanguageElement element: languages) {
            codeToLang.put(element.getCode(), element.getTitle());
        }
        HashMap<LanguageElement, ArrayList<LanguageElement>> langsToDiriections = new HashMap<LanguageElement, ArrayList<LanguageElement>>();
        // создается карта. ключом карты является LanguageElement какого-либо языка. Значение карты - список всех направлений для перевода у этого языка
        for(LanguageElement language : languages) {
            ArrayList<LanguageElement> listOfDirections = new ArrayList<LanguageElement>();
            for(String direction: directions) {
                LanguageElement directionOfOrigin = ResponseParser.directionForLang(language.getCode(), direction, codeToLang);
                if(directionOfOrigin != null) {
                    listOfDirections.add(directionOfOrigin);
                }
            }
            if(!listOfDirections.isEmpty()) {
                langsToDiriections.put(language, listOfDirections);
            }
        }
        return langsToDiriections;
    }

    public interface ProgressUpdater {
        public void setProgress(int progress);
    }

}
