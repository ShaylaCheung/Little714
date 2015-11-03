package com.baby.cy.babyfun;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.baby.cy.babyfun.Bean.MusicInfos;

import java.util.List;

/**
 * Created by chenyu on 15/10/24.
 */
public class ListViewAdapter extends ArrayAdapter<MusicInfos> {

    private int resourceId ;

    private TextView listview_music_id;
    private TextView music_name;
    private TextView singer_name;

    public ListViewAdapter(Context context,int resourceId,List<MusicInfos> object){
        super(context,resourceId,object);
        this.resourceId = resourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MusicInfos musicInfos = getItem(position);
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

        viewHolder.listview_music_name.setText(musicInfos.getMusic_name());
        viewHolder.listview_singer_name.setText(musicInfos.getSinger_name());
        viewHolder.listview_music_id.setText(musicInfos.getId()+"");
        return view;

    }


    class ViewHolder{
        TextView listview_music_id;
        TextView listview_singer_name;
        TextView listview_music_name;
    }
}
