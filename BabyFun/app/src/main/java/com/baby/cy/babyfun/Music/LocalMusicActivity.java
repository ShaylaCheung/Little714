package com.baby.cy.babyfun.Music;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.baby.cy.babyfun.ListViewAdapter;
import com.baby.cy.babyfun.MusicInfos;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.yalantis.phoenix.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LocalMusicActivity extends AppCompatActivity {

    @Bind(R.id.local_music_toolbar)Toolbar toolbar;


    @Bind(R.id.pull_to_refresh) PullToRefreshView pullToRefreshView;
    @Bind(R.id.local_listView) SwipeMenuListView listView;

    private List<MusicInfos> musicList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_local_layout);


        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalMusicActivity.this.finish();
            }
        });



        pullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pullToRefreshView.setRefreshing(false);
                    }
                }, 2000);
            }
        });

        initListView();

    }



    public void initListView(){
        //生成动态数组，加入数据
        musicList = new ArrayList<MusicInfos>();
        initMusicList();

        ListViewAdapter adapter = new ListViewAdapter(LocalMusicActivity.this,R.layout.listview_item_layout,musicList);

        //添加并且显示
        listView.setAdapter(adapter);

        //添加点击
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                setTitle("点击第" + arg2 + "个项目");
            }
        });



        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem downloadItem = new SwipeMenuItem(getApplicationContext());
                downloadItem.setBackground(R.color.listview_download);
                downloadItem.setWidth(300);
                downloadItem.setTitle("download");
                downloadItem.setTitleSize(18);
                downloadItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(downloadItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                deleteItem.setBackground(R.color.listview_delete);
                deleteItem.setWidth(300);
                deleteItem.setTitle("delete");
                deleteItem.setTitleSize(18);
                deleteItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(deleteItem);
            }
        };
        listView.setMenuCreator(creator);

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // open
                        toolbar.setTitle("download");
                        break;
                    case 1:
                        // delete
                        toolbar.setTitle("delete");
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                new AlertDialog.Builder(LocalMusicActivity.this).setTitle("删除")
                        .setMessage("您确定退出登录状态？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("Tomato", "exit");
                            }
                        })
                        .setNegativeButton("返回", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("Tomato","no exit");
//                                        finish();
                            }
                        }).show();
                return false;
            }
        });
    }


    public void initMusicList(){
        MusicInfos music1 = new MusicInfos("music1","chenyu");
        musicList.add(music1);
        MusicInfos music2 = new MusicInfos("music2","chenyu");
        musicList.add(music2);
        MusicInfos music3 = new MusicInfos("music3","chenyu");
        musicList.add(music3);
        MusicInfos music4 = new MusicInfos("music4","chenyu");
        musicList.add(music4);
        MusicInfos music5 = new MusicInfos("music5","chenyu");
        musicList.add(music5);
        MusicInfos music6 = new MusicInfos("music6","chenyu");
        musicList.add(music6);
        MusicInfos music7 = new MusicInfos("music7","chenyu");
        musicList.add(music7);
        MusicInfos music8 = new MusicInfos("music8","chenyu");
        musicList.add(music8);
        MusicInfos music9 = new MusicInfos("music9","chenyu");
        musicList.add(music9);
        MusicInfos music10 = new MusicInfos("music10","chenyu");
        musicList.add(music10);
    }
}
