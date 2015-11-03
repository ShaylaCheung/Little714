package com.baby.cy.babyfun;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by chenyu on 15/10/29.
 */
public class MotherListViewAdapter extends ArrayAdapter<String> {

    private int resourceId ;

    public MotherListViewAdapter(Context context,int resourceId,List<String> object){
        super(context,resourceId,object);
        this.resourceId = resourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String music_name = getItem(position);
        View view ;
        ViewHolder viewHolder;
        if(convertView==null){
            view =  LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder = new ViewHolder();
            viewHolder.listview_music_id = (TextView)view.findViewById(R.id.listview_music_id);
            viewHolder.listview_singer_name = (TextView) view.findViewById(R.id.listview_singer_name);
            viewHolder.listview_music_name = (TextView) view.findViewById(R.id.listview_music_name);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }

        viewHolder.listview_music_name.setText(music_name);
        viewHolder.listview_singer_name.setText(null);
        viewHolder.listview_music_id.setText(1+"");
        return view;

    }


    class ViewHolder{
        TextView listview_music_id;
        TextView listview_singer_name;
        TextView listview_music_name;
    }
}
