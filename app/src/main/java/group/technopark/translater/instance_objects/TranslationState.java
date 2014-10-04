package group.technopark.translater.instance_objects;


import android.os.Parcel;
import android.os.Parcelable;

import group.technopark.translater.adapters.LanguageElement;

public class TranslationState implements Parcelable {
    private LanguageElement languageElementFrom;
    private LanguageElement languageElementTo;
    private String textToTranslate;
    private String translatedText;

    public TranslationState(Parcel in){
        readFromParcel(in);
    }

    public TranslationState(){
    }

    public String getTranslatedText() {
        return translatedText;
    }

    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }

    public String getTextToTranslate() {
        return textToTranslate;
    }

    public void setTextToTranslate(String textToTranslate) {
        this.textToTranslate = textToTranslate;
    }

    public LanguageElement getLanguageElementTo() {
        return languageElementTo;
    }

    public void setLanguageElementTo(LanguageElement languageElementTo) {
        this.languageElementTo = languageElementTo;
    }

    public LanguageElement getLanguageElementFrom() {
        return languageElementFrom;
    }

    public void setLanguageElementFrom(LanguageElement languageElementFrom) {
        this.languageElementFrom = languageElementFrom;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(languageElementFrom, 0);
        dest.writeParcelable(languageElementTo, 0);
        dest.writeString(textToTranslate);
        dest.writeString(translatedText);
    }

    public void readFromParcel(Parcel in){
        languageElementFrom = in.readParcelable(LanguageElement.class.getClassLoader());
        languageElementTo = in.readParcelable(LanguageElement.class.getClassLoader());
        textToTranslate = in.readString();
        translatedText = in.readString();
    }
}
