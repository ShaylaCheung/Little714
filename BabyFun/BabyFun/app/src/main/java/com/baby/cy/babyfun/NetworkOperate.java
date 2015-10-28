package com.baby.cy.babyfun;

import android.content.Context;
import android.util.Log;

import com.baby.cy.babyfun.Bean.MusicInfos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Potato on 15/10/27.
 */
public class NetworkOperate {
    private Context context;
    public NetworkOperate(Context context){
        this.context = context;
        Log.d("Tomato","context------>"+context.toString());
    }


    private List<MusicInfos> musicInfosList = new ArrayList<MusicInfos>();








}
