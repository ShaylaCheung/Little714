package com.baby.cy.babyfun.Music;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
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

public class PreferMusicActivity extends AppCompatActivity implements LoginReceiver.OnLoginListener{

    @Bind(R.id.local_music_toolbar)Toolbar toolbar;
    @Bind(R.id.local_listView) SwipeMenuListView listView;
    @Bind(R.id.play_layout) CoordinatorLayout play_layout ;

    private List<MusicInfos> musicList;
    public RequestQueue mQueue;
    private Long user_id;
    private LoginReceiver loginReceiver ;
    private IntentFilter intentFilter ;
    private boolean isLogin = false;
    private ListViewAdapter adapter;
    private boolean isDeleteSuccess = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_play_layout);


        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back_icon);
        toolbar.setTitle("播放列表");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferMusicActivity.this.finish();
            }
        });

        loginReceiver = new LoginReceiver(this){
            @Override
            public void onReceive(Context context, Intent intent) {
                super.onReceive(context, intent);
                user_id = intent.getLongExtra("user_id",0);
                getMusicParams(URLUtils.prefer_music_url,user_id);
            }
        };
        intentFilter = new IntentFilter();
        intentFilter.addAction(LoginReceiver.ON_LOGIN_FINISH);
        //注册广播接收器
        this.registerReceiver(loginReceiver, intentFilter);

        //得到当前的登录状态
        isLogin = StateUtils.isLogin();

        if(isLogin){
            //已经登录
            user_id = StateUtils.getUser_id();
            getMusicParams(URLUtils.prefer_music_url,user_id);
        }else{
            new AlertDialog.Builder(PreferMusicActivity.this)
                    .setTitle("获取播放列表失败")
                    .setMessage("您还未登录，请先登录？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(PreferMusicActivity.this, UserActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            play_layout.setBackgroundResource(R.drawable.play_unlogin_bg);
                        }
                    })
                    .show();
        }

    }



    public void initListView(){
        adapter = new ListViewAdapter(PreferMusicActivity.this,R.layout.listview_item_layout,musicList);

        //添加并且显示
        listView.setAdapter(adapter);

        //添加点击
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Intent intent = new Intent(PreferMusicActivity.this, PlayMusicActivity.class);
                intent.putExtra("whichIntent", 1);
                intent.putExtra("music_path", musicList.get(arg2).getMusic_path());
                intent.putExtra("music_name", musicList.get(arg2).getMusic_name());
                startActivity(intent);
            }
        });



        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem downloadItem = new SwipeMenuItem(getApplicationContext());
                downloadItem.setBackground(R.color.listview_delete);
                downloadItem.setWidth(300);
                downloadItem.setTitle("删除");
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
                        Long music_id = musicList.get(position).getId();
                        deleteMusicParams(URLUtils.deleteUserMusic_url,user_id,music_id,position);
                        break;
                }
                return false;
            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(PreferMusicActivity.this).setTitle("删除")
                        .setMessage("您确定删除该歌曲？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Long music_id = musicList.get(position).getId();
                                deleteMusicParams(URLUtils.deleteUserMusic_url, user_id, music_id,position);
                            }
                        })
                        .setNegativeButton("返回", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
                return true;
            }
        });
    }


    public void getMusicParams(String url,final Long user_id){
        mQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        musicList = parseAllPreferMusicJson(response);
                        initListView();
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
                return data;
            }
        };
        mQueue.add(stringRequest);
    }

    public List<MusicInfos> parseAllPreferMusicJson(String response){
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

    @Override
    public void onLoginFinished() {
        isLogin = StateUtils.isLogin();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void deleteMusicParams(String url,final Long user_id,final Long music_id,final int position){
        mQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //得到删除的结果---true为删除成功
                        isDeleteSuccess = parseDeleteMusicJson(response);
                        Log.d("Tomato","url:"+URLUtils.deleteUserMusic_url);
                        Log.d("Tomato",isDeleteSuccess+"");
                        if (isDeleteSuccess) {
                            //删除成功
                            musicList.remove(position);//选择行的位置
                            adapter.notifyDataSetChanged();
                            listView.invalidate();
                        } else {
                            Toast.makeText(PreferMusicActivity.this,"删除失败",Toast.LENGTH_SHORT).show();
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
                data.put("music_id",music_id.toString());
                return data;
            }
        };
        mQueue.add(stringRequest);
    }

    public boolean parseDeleteMusicJson(String response){
        boolean delete_status = false;
        try{
            JSONObject data = new JSONObject(response);
            delete_status = data.getBoolean("delete_status");
        }catch (Exception e){
            e.printStackTrace();
        }
        return delete_status;
    }
}
