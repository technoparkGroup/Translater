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
            e.printStackTrace();
        }
        return response;
    }

    public static String getResponse(InputStream is, ProgressUpdater updater) throws IOException {
        int progress = 0;
        int max = is.available();
        if (updater != null)
            updater.setProgress(progress, max);
        BufferedReader bf = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        StringBuilder response = new StringBuilder();
        String nextLine;
        while ((nextLine = bf.readLine()) != null) {
            progress += nextLine.length();
            if (updater != null)
                updater.setProgress(progress, max);
            response.append(nextLine);
        }

//        byte[] response = new byte[.available()];
//        int line;
//        while ((line = is.read(response, progress, 80)) != 0){
//            progress += line;
//            if (updater != null)
//                updater.setProgress(progress, max);
//        }

        return new String(response);
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

    private void updateProgress(ProgressUpdater updater, int progress, int max){
        if (updater != null)
            updater.setProgress(progress, max);
    }

    public interface ProgressUpdater {
        public void setProgress(int progress, int max);
    }

}
