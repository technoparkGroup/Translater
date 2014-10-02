package group.technopark.translater;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Elvira on 02.10.2014.
 */
public class LanguageAdapter  extends android.widget.ArrayAdapter <LanguageElement> {
    LanguageElement[] array;
    int layoutResource;
    LayoutInflater inflater;

    public LanguageAdapter(Activity context, int resourceId, LanguageElement[] objects) {
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
            view = inflater.inflate(layoutResource,parent, false);
            holder = new LanguageElementHolder();
            if(view != null){
                holder.setView((TextView)view.findViewById(R.id.language_name));
                view.setTag(holder);
            }
        }
        else {
            holder = (LanguageElementHolder)convertView.getTag();
        }
        holder.getView().setText(array[position].getName());
        return view;
    }

    public LanguageListElement getElement(int position) {
        if(position >=0) {
            return array[position];
        }
        return null;
    }
}
