package group.technopark.translater.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import group.technopark.translater.R;

public class LanguageAdapter  extends android.widget.ArrayAdapter <LanguageElement> {
    int layoutResource;
    LayoutInflater inflater;

    public LanguageAdapter(Activity context, int resourceId, ArrayList<LanguageElement> objects) {
        super(context, resourceId, objects);
        Collections.sort(objects);
        inflater = context.getLayoutInflater();
        layoutResource = resourceId;
    }

    @Override
    public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
        return getCustomView(position, cnvtView, prnt);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        LanguageElementHolder holder;
        if (view == null) {
            view = inflater.inflate(layoutResource, parent, false);
            holder = new LanguageElementHolder();
            if (view != null) {
                holder.setView((TextView) view.findViewById(R.id.language_name));
                view.setTag(holder);
            }
        } else {
            holder = (LanguageElementHolder) convertView.getTag();
        }
        TextView holderView = holder.getView();
        holderView.setText(this.getItem(position).getTitle());
        return view;
    }

    public LanguageElement getElement(int position) {
        if (position >= 0) {
            return getItem(position);
        }
        return null;
    }

    public int getPositionByElement(LanguageElement element) {
        for (int i = 0; i < this.getCount(); i++) {
            LanguageElement el = getItem(i);
            if (el.getCode().equals(element.getCode())
                    && el.getTitle().equals(element.getTitle()))
                return i;
        }
        return -1;
    }

    public void changeArray(ArrayList<LanguageElement> newArray) {
        Collections.sort(newArray);
        this.clear();
        this.addAll(newArray);
        notifyDataSetChanged();
    }

}
