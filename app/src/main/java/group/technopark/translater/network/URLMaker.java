package group.technopark.translater.network;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class URLMaker {

    public static final String KEY = "trnsl.1.1.20141001T173904Z.0c42a06b5eb21ee0.bab94abfffa9e01a5ae05451089056e4f0a620c7";

    private String originCode;
    private String destinationCode;
    private String text;

    public URLMaker(String originCode, String destinationCode, String text) {
        this.originCode = originCode;
        this.destinationCode = destinationCode;
        this.text = text;
    }

    public String getUrl() throws UnsupportedEncodingException {
        String url = "https://translate.yandex.net/api/v1.5/tr.json/translate?";
        url += "key=" + KEY;
        url += "&text=" + URLEncoder.encode(text, "UTF-8");
        url += "&lang=" + originCode + "-" + destinationCode;
        Log.d("URL", url);
        return url;
    }

}
