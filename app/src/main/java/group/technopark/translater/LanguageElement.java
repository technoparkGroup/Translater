package group.technopark.translater;

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
}
