package com.baby.cy.babyfun.Music;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.baby.cy.babyfun.R;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MusicChoiceActivity extends AppCompatActivity {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.collapsingToolbarLayout)CollapsingToolbarLayout toolbarLayout;
    @Bind(R.id.navigation) NavigationView navigation;
//    @Bind(R.id.music_local)RoundedImageView music_local;
//    @Bind(R.id.music_mother)RoundedImageView music_mother;
//    @Bind(R.id.music_find)RoundedImageView music_find;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_choice);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbarLayout.setTitle("宝宝摇篮曲");


        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.navItem1:
                        Intent set_intent = new Intent(MusicChoiceActivity.this,SetUserActivity.class);
                        startActivity(set_intent);
                        break;
                    case R.id.navItem2:

                        break;
                    case R.id.navItem3:
                        Intent local_intent = new Intent(MusicChoiceActivity.this,LocalMusicActivity.class);
                        startActivity(local_intent);
                        break;
                    case R.id.navItem4:

                        new AlertDialog.Builder(MusicChoiceActivity.this).setTitle("退出")
                                .setMessage("您确定退出登录状态？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.d("Tomato","exit");
                                    }
                                })
                                .setNegativeButton("返回", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.d("Tomato","no exit");
//                                        finish();
                                    }
                                }).show();

                        break;
                }
                return false;
            }
        });


//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//
//                return false;
//            }
//        });


        ;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 為了讓 Toolbar 的 Menu 有作用，這邊的程式不可以拿掉
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @OnClick(R.id.user_image)
    public void login_or_not(){
        //已经登录了
        if(!true){

        }else{
            Toast.makeText(this,"请先注册",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MusicChoiceActivity.this,UserActivity.class);
            startActivity(intent);
        }
    }

    @OnClick({R.id.music_local_rl,R.id.music_mother_rl,R.id.music_find_rl})
    public void music_choice(RelativeLayout relativeLayout){
        Intent intent = new Intent();
        switch(relativeLayout.getId()){
            case R.id.music_local_rl:
                intent.setClass(MusicChoiceActivity.this,LocalMusicActivity.class);
                startActivity(intent);
                break;
            case R.id.music_mother_rl:
                intent.setClass(MusicChoiceActivity.this,LocalMusicActivity.class);
                startActivity(intent);
                break;
            case R.id.music_find_rl:
                intent.setClass(MusicChoiceActivity.this,LocalMusicActivity.class);
                startActivity(intent);
                break;
        }
    }

//    @OnClick(R.id.test)
//    public void test(){
//        toolbar.setTitle("Test");
//    }

//    @OnClick(R.id.music_local)
//    public void local(){
//        Intent intent = new Intent();
//        intent.setClass(MusicChoiceActivity.this,LocalMusicActivity.class);
//        startActivity(intent);
//    }

}
