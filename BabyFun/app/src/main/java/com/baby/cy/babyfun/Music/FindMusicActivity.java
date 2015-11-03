package com.baby.cy.babyfun.Music;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baby.cy.babyfun.Bean.MusicInfos;
import com.baby.cy.babyfun.ListViewAdapter;
import com.baby.cy.babyfun.LoginReceiver;
import com.baby.cy.babyfun.R;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.yalantis.phoenix.PullToRefreshView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Utils.StateUtils;
import Utils.URLUtils;
import butterknife.Bind;
import butterknife.ButterKnife;

public class FindMusicActivity extends AppCompatActivity implements LoginReceiver.OnLoginListener{

    @Bind(R.id.find_music_toolbar)Toolbar toolbar;
    @Bind(R.id.pull_to_refresh) PullToRefreshView pullToRefreshView;
    @Bind(R.id.find_listView) SwipeMenuListView listView;

    private List<MusicInfos> musicList;
    public RequestQueue mQueue;

    private LoginReceiver loginReceiver ;
    private IntentFilter intentFilter ;
    private boolean isLogin = false;
    private Long user_id = 0L; //当前已经登陆的用户ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_find_layout);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back_icon);
        toolbar.setTitle("发现摇篮曲");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FindMusicActivity.this.finish();
            }
        });


        //下拉刷新
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

        //判断当前的登录状态
        isLogin = StateUtils.isLogin();

        getMusicParams(URLUtils.find_music_url);

        loginReceiver = new LoginReceiver(this){
            @Override
            public void onReceive(Context context, Intent intent) {
                super.onReceive(context, intent);
                user_id = intent.getLongExtra("user_id",0);
            }
        };
        intentFilter = new IntentFilter();
        intentFilter.addAction(LoginReceiver.ON_LOGIN_FINISH);
        //注册广播接收器
        this.registerReceiver(loginReceiver, intentFilter);




    }



    public void initListView(){


        ListViewAdapter adapter = new ListViewAdapter(FindMusicActivity.this,R.layout.listview_item_layout,musicList);

        //添加并且显示
        listView.setAdapter(adapter);

        //添加点击
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Intent intent = new Intent(FindMusicActivity.this, PlayMusicActivity.class);
                intent.putExtra("whichIntent", 1);
                intent.putExtra("music_path", musicList.get(arg2).getMusic_path());
                intent.putExtra("music_name", musicList.get(arg2).getMusic_name());
                startActivity(intent);
            }
        });



        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "添加" item
                SwipeMenuItem downloadItem = new SwipeMenuItem(getApplicationContext());
                downloadItem.setBackground(R.color.listview_download);
                downloadItem.setWidth(300);
                downloadItem.setTitle("添加");
                downloadItem.setTitleSize(18);
                downloadItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(downloadItem);

            }
        };
        listView.setMenuCreator(creator);

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        if (isLogin) {
                            //当前用户是已经登陆的状态
                            Log.d("Tomato",user_id+".....");
                            MusicInfos music = musicList.get(position);
                            Long music_id = music.getId();
                            Log.d("Tomato", music_id + "");
                            addUserMusicParams(URLUtils.addUserMusic_url, user_id, music_id);
                        } else {
                            //用户还未登陆
                            new AlertDialog.Builder(FindMusicActivity.this)
                                    .setTitle("添加歌曲失败")
                                    .setMessage("您还未登录，请先登录？")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(FindMusicActivity.this, UserActivity.class);
                                            startActivity(intent);
                                        }
                                    })
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .show();

                        }
                        break;
                }
                return false;
            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                new AlertDialog.Builder(FindMusicActivity.this).setTitle("删除")
                        .setMessage("您确定删除该歌曲？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("Tomato", "exit");
                            }
                        })
                        .setNegativeButton("返回", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("Tomato", "no exit");
//                                        finish();
                            }
                        }).show();
                return false;
            }
        });
    }


    public void getMusicParams(String url){
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


    public void addUserMusicParams(String url, final Long user_id, final Long music_id){

        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        boolean addMusic_status = parseUserJson(response);
                        if(addMusic_status){
                            Toast.makeText(getApplicationContext(),"添加成功",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"添加失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Tomato", error.getMessage(), error);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                //在这里设置需要post的参数
                Map<String, String> data = new HashMap<String, String>();
                data.put("user_id", user_id.toString());
                data.put("music_id", music_id.toString());
                return data;
            }

        };
        mQueue.add(stringRequest);

    }


    public boolean parseUserJson(String response){

        boolean addMusic_status = false;
        try{
            JSONObject jsonObject = new JSONObject(response);
            addMusic_status = jsonObject.getBoolean("addMusic_status");
        }catch (Exception e){
            e.printStackTrace();
        }
        return addMusic_status;
    }

    @Override
    public void onLoginFinished() {
        isLogin = StateUtils.isLogin();
        Log.d("Tomato","oclick is Login");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(loginReceiver!=null){
            unregisterReceiver(loginReceiver);
        }
    }
}
