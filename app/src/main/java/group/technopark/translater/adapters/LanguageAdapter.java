package group.technopark.translater.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import group.technopark.translater.R;

public class LanguageAdapter  extends android.widget.ArrayAdapter <LanguageElement> {
    ArrayList<LanguageElement> array;
    int layoutResource;
    LayoutInflater inflater;

    public LanguageAdapter(Activity context, int resourceId, ArrayList<LanguageElement> objects) {
        super(context, resourceId, objects);
        this.array = objects;
        inflater = context.getLayoutInflater();
        layoutResource = resourceId;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        LanguageElementHolder holder;
        if(view == null) {
            view = inflater.inflate(layoutResource, parent, false);
            holder = new LanguageElementHolder();
            if(view != null){
                holder.setView((TextView)view.findViewById(R.id.language_name));
                view.setTag(holder);
            }
        }
        else {
            holder = (LanguageElementHolder)convertView.getTag();
        }
        TextView holderView = holder.getView();
        holderView.setText(array.get(position).getTitle());
        return view;
    }

    public LanguageElement getElement(int position) {
        if(position >=0) {
            return array.get(position);
        }
        return null;
    }
}
