package group.technopark.translater.network;

public class URLMaker {

    public static final String KEY = "trnsl.1.1.20141001T173904Z.0c42a06b5eb21ee0.bab94abfffa9e01a5ae05451089056e4f0a620c7";

    public static String getTranslateUrl(String originCode, String destinationCode, String text) {
        String url = "https://translate.yandex.net/api/v1.5/tr.json/translate?";
        url += "key=" + KEY;
        url += "&text=" + text;
        url += "&lang=" + originCode + "-" + destinationCode;
        return url;
    }

    public static String getLanguageUrl() {
        String url = "https://translate.yandex.net/api/v1.5/tr.json/getLangs?";
        url += "key=" + KEY;
        url += "&ui=ru";
        return url;
    }


}
