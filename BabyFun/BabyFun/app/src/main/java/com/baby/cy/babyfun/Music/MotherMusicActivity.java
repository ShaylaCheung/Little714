package com.baby.cy.babyfun.Music;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.baby.cy.babyfun.MotherListViewAdapter;
import com.baby.cy.babyfun.R;
import com.yalantis.phoenix.PullToRefreshView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MotherMusicActivity extends AppCompatActivity {

    @Bind(R.id.mother_music_toolbar) Toolbar toolbar;


    @Bind(R.id.mother_pull_to_refresh) PullToRefreshView pullToRefreshView;
    @Bind(R.id.mother_listview) ListView listView;


    private String music_path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Music";
    private List<String> music_name_list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_mother_layout);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("妈妈摇篮曲");
        toolbar.setNavigationIcon(R.drawable.back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MotherMusicActivity.this.finish();
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

        File file = new File(music_path);
        getMusicFile(file);
        initMotherMusicList();
    }

    /**
     * 所有文件名称
     * @param file
     */
    public void getMusicFile(File file){
        File[] filelist = file.listFiles();
        for(int i = 0;i<filelist.length;i++){
            File f = filelist[i];
            if(f.getName().endsWith(".amr")){
                String music_name = f.getName().substring(0,f.getName().length()-4);
                music_name_list.add(music_name);
            }
        }
    }

    public void initMotherMusicList(){
        MotherListViewAdapter adapter = new MotherListViewAdapter(MotherMusicActivity.this,R.layout.listview_item_layout,music_name_list);

        //添加并且显示
        listView.setAdapter(adapter);

        //添加点击
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
//                new PlayMusicActivity(music_name_list.get(arg2));
                Intent intent = new Intent(MotherMusicActivity.this,PlayMusicActivity.class);
                intent.putExtra("whichIntent",2);
                intent.putExtra("music_name",music_name_list.get(arg2));
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final String music_name =music_name_list.get(position);
                Log.d("Tomato",music_name);
                new AlertDialog.Builder(MotherMusicActivity.this).setTitle("删除")
                        .setMessage("您确定删除该歌曲？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("Tomato", "exit");
                                File file = new File(music_path+"/"+music_name+".amr");
                                Log.d("Toamto",music_path+"/"+music_name+".amr");
                                file.delete();
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
}
