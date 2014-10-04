package group.technopark.translater.adapters;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class LanguageElement implements Parcelable{
    private String title;
    private String code;

    public LanguageElement(String title, String code) {
        this.title = title;
        this.code = code;
    }

    public LanguageElement(Parcel parcel){
        readFromParcel(parcel);
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(code);
    }

    public void readFromParcel(Parcel income){
        title = income.readString();
        code = income.readString();
    }

    public static final Parcelable.Creator<LanguageElement> CREATOR
            = new Parcelable.Creator<LanguageElement>() {
        public LanguageElement createFromParcel(Parcel in) {
            return new LanguageElement(in);
        }

        public LanguageElement[] newArray(int size) {
            return new LanguageElement[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LanguageElement)) return false;

        LanguageElement that = (LanguageElement) o;

        if (!code.equals(that.code)) return false;
        if (!title.equals(that.title)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + code.hashCode();
        return result;
    }
}
