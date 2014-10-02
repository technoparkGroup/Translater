package group.technopark.translater.adapters;

import java.util.ArrayList;

public class LanguageElement {
    private String title;
    private String code;

    public LanguageElement(String title, String code) {
        this.title = title;
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public String getCode() {
        return code;
    }

    public static ArrayList<LanguageElement> getLangList(String[] langs, String[] cods){
        ArrayList<LanguageElement> list = new ArrayList<LanguageElement>();
        for (int i = 0; i < langs.length; i++){
            LanguageElement element = new LanguageElement(langs[i], cods[i]);
            list.add(element);
        }
        return list;
    }

}
