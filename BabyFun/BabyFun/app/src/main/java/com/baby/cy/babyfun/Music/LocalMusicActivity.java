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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.baby.cy.babyfun.ListViewAdapter;
import com.baby.cy.babyfun.Bean.MusicInfos;
import com.baby.cy.babyfun.R;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.yalantis.phoenix.PullToRefreshView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LocalMusicActivity extends AppCompatActivity {

    @Bind(R.id.local_music_toolbar)Toolbar toolbar;


    @Bind(R.id.pull_to_refresh) PullToRefreshView pullToRefreshView;
    @Bind(R.id.local_listView) SwipeMenuListView listView;

    private List<MusicInfos> musicList;
    public RequestQueue mQueue;
    private String local_music_url = "http://192.168.1.99:8080/BabyFun/api/getAllMusicList";

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
                }, 1000);
            }
        });

        getParams(local_music_url);

    }



    public void initListView(){
//        //生成动态数组，加入数据
//        initMusicList();

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


    public void getParams(String url){
        mQueue = Volley.newRequestQueue(this);
        Log.d("Tomato", url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Tomato", response.toString());
                        musicList = parseAllMusicJson(response.toString());
                        initListView();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Tomato", error.getMessage(), error);
            }
        });


        mQueue.add(jsonObjectRequest);

    }

    public List<MusicInfos> parseAllMusicJson(String response){
        List<MusicInfos> list = new ArrayList<MusicInfos>();
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("musicList");

            for(int i =0;i<jsonArray.length();i++){
                JSONObject data = jsonArray.getJSONObject(i);
                MusicInfos musicInfos = new MusicInfos();
                musicInfos.setId(data.getLong("id"));
                musicInfos.setMusic_name(data.getString("music_name"));
                musicInfos.setSinger_name(data.getString("singer_name"));
                musicInfos.setMusic_path(data.getString("music_path"));
                list.add(musicInfos);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
