package com.baby.cy.babyfun.Music;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baby.cy.babyfun.Bean.User;
import com.baby.cy.babyfun.R;
import com.pkmmte.view.CircularImageView;
import UserFragment.SignIn_frg;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MusicChoiceActivity extends AppCompatActivity {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.collapsingToolbarLayout)CollapsingToolbarLayout toolbarLayout;
    @Bind(R.id.navigation) NavigationView navigation;
    @Bind(R.id.user_text) TextView user_text;
    @Bind(R.id.user_image) CircularImageView user_image;
    private boolean isLogin = false;

    private static final int NEW_USER = 2;
    private static final int LOGIN_SUCCESS = 4;
    private User user;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case NEW_USER:
                    user_text.setText(user.getUser_name());
                    user_image.setImageResource(R.drawable.user_image);
                    break;
                case LOGIN_SUCCESS:
                    user_text.setText(user.getUser_name());
                    user_image.setImageResource(R.drawable.user_image);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_choice);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbarLayout.setTitle("宝宝摇篮曲");

        initNavigation();

        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.navItem1:
                        Intent set_intent = new Intent(MusicChoiceActivity.this,SetUserActivity.class);
                        startActivity(set_intent);
                        break;
                    case R.id.navItem2:
                        Intent play_intent = new Intent(MusicChoiceActivity.this,PlayMusicActivity.class);
                        startActivity(play_intent);
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
        if(isLogin){
            Toast.makeText(this,"您已登录",Toast.LENGTH_SHORT).show();
        }else{

            Intent intent = new Intent(MusicChoiceActivity.this,UserActivity.class);
//            intent.putExtra("isLogin",false);
            startActivityForResult(intent, 1);
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

    public void initNavigation(){

        user = new SignIn_frg().getNewUser();
        if(user!=null){
            user_text.setText(user.getUser_name());
//            user_image.setImageResource(R.drawable.user_image);
        }else{
            user_text.setText("未登录");
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            user = new User();
            user.setId(data.getLongExtra("id", 0));
            user.setUser_name(data.getStringExtra("user_name"));
            user.setUser_password(data.getStringExtra("user_password"));
            user.setUser_phone(data.getStringExtra("user_phone"));
            if(user.getId()!=null){
                Message msg = new Message();
                msg.what = NEW_USER;
                handler.sendMessage(msg);
            }
        }
        if(resultCode==RESULT_FIRST_USER){
            user = new User();
            user.setId(data.getLongExtra("id", 0));
            user.setUser_name(data.getStringExtra("user_name"));
            user.setUser_password(data.getStringExtra("user_password"));
            user.setUser_phone(data.getStringExtra("user_phone"));
            if(user.getId()!=null){
                Message msg = new Message();
                msg.what = LOGIN_SUCCESS;
                handler.sendMessage(msg);
            }
        }
    }
}
