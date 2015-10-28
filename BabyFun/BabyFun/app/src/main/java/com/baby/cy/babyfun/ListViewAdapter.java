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
        View view = LayoutInflater.from(getContext()).inflate(resourceId,null);

        music_name = (TextView) view.findViewById(R.id.listview_music_name);
        singer_name = (TextView) view.findViewById(R.id.listview_singer_name);
        listview_music_id = (TextView)view.findViewById(R.id.listview_music_id);

        music_name.setText(musicInfos.getMusic_name());
        singer_name.setText(musicInfos.getSinger_name());
        listview_music_id.setText(musicInfos.getId()+"");
        return view;

    }
}
