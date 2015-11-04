package com.baby.cy.babyfun.Music;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.baby.cy.babyfun.LoginReceiver;
import com.baby.cy.babyfun.R;
import com.baby.cy.babyfun.SignInReceiver;
import com.baby.cy.babyfun.SignOutReceiver;
import com.pkmmte.view.CircularImageView;

import Utils.StateUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MusicChoiceActivity extends AppCompatActivity implements SignInReceiver.OnSignInListener,
        LoginReceiver.OnLoginListener,SignOutReceiver.OnSignOutListener{

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.collapsingToolbarLayout)CollapsingToolbarLayout toolbarLayout;
    @Bind(R.id.navigation) NavigationView navigation;
//    @Bind(R.id.user_text)  static TextView user_text;
//    @Bind(R.id.user_image)static CircularImageView user_image;
    private static TextView user_text;
    private static CircularImageView user_image;
    private static final int NEW_USER = 2;
    private static final int LOGIN_SUCCESS = 4;
    private static final int SIGNOUT_SUCCESS = 6;
    private  User user;

    private LoginReceiver loginReceiver ;
    private IntentFilter loginintentFilter ;
    private SignInReceiver signinReceiver ;
    private IntentFilter signinintentFilter ;
    private SignOutReceiver signOutReceiver ;
    private IntentFilter signOutintentFilter ;
    private  boolean isLogin = false;
    private Long user_id;
    private String user_name;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case NEW_USER:
                    user_text.setText(user_name);
                    user_image.setImageResource(R.drawable.user_image_login);
                    break;
                case LOGIN_SUCCESS:
                    user_text.setText(user_name);
                    user_image.setImageResource(R.drawable.user_image_login);
                    break;
                case SIGNOUT_SUCCESS:
                    user_text.setText("未登录");
                    user_image.setImageResource(R.drawable.user_image_unlogin);
                    break;
            }
        }
    };


    /**
     * 设置登陆的图像、用户名称
     * @param user_name
     * @param user_id
     */
    public static void setLoginImageText(String user_name,Long user_id){
        user_text.setText(user_name);
        user_image.setImageResource(R.drawable.user_image_login);
//        MusicChoiceActivity.user_id = user_id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_choice);
        user_text = (TextView)findViewById(R.id.user_text);
        user_image = (CircularImageView) findViewById(R.id.user_image);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbarLayout.setTitle("宝宝摇篮曲");

        //判断当前的登录状态
        isLogin = StateUtils.isLogin();
        initNavigation();

        loginReceiver = new LoginReceiver(this){
            @Override
            public void onReceive(Context context, Intent intent) {
                super.onReceive(context, intent);
                user_id = intent.getLongExtra("user_id", 0);
                user_name = intent.getStringExtra("user_name");
            }
        };
        loginintentFilter = new IntentFilter();
        loginintentFilter.addAction(LoginReceiver.ON_LOGIN_FINISH);
        //注册广播接收器
        this.registerReceiver(loginReceiver, loginintentFilter);


        signinReceiver = new SignInReceiver(this){
            @Override
            public void onReceive(Context context, Intent intent) {
                super.onReceive(context, intent);
                user_id = intent.getLongExtra("user_id", 0);
                user_name = intent.getStringExtra("user_name");
            }
        };
        signinintentFilter = new IntentFilter();
        signinintentFilter.addAction(LoginReceiver.ON_LOGIN_FINISH);
        //注册广播接收器
        this.registerReceiver(signinReceiver, signinintentFilter);

        signOutReceiver = new SignOutReceiver(this){
            @Override
            public void onReceive(Context context, Intent intent) {
                super.onReceive(context, intent);
                isLogin = intent.getBooleanExtra("isLogin",false);
                StateUtils.setIsLogin(false);
                StateUtils.setUser_id(0L);
                StateUtils.setUser_name(null);
                Log.d("Tomato",intent.getAction());
                if(intent.getAction().equals(ON_SIGNOUT_FINISH)){
                    onSignOutFinish();
                    return;
                }
            }
        };
        signOutintentFilter = new IntentFilter();
        signOutintentFilter.addAction(LoginReceiver.ON_LOGIN_FINISH);
        //注册广播接收器
        this.registerReceiver(signOutReceiver, signOutintentFilter);


        initNavigation();
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.navItem1:
                        Intent set_intent = new Intent(MusicChoiceActivity.this, SetUserActivity.class);
                        startActivity(set_intent);
                        break;
                    case R.id.navItem2:
                        Intent play_intent = new Intent(MusicChoiceActivity.this, RecordMusicActivity.class);
                        startActivity(play_intent);
                        break;
                    case R.id.navItem3:
                        Intent local_intent = new Intent(MusicChoiceActivity.this, FindMusicActivity.class);

                        startActivity(local_intent);
                        break;
                    case R.id.navItem4:

                        new AlertDialog.Builder(MusicChoiceActivity.this).setTitle("退出")
                                .setMessage("您确定退出登录状态？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        StateUtils.setIsLogin(false);
                                        StateUtils.setUser_id(0L);
                                        StateUtils.setUser_name(null);
                                        Intent intent = new Intent(SignOutReceiver.ON_SIGNOUT_FINISH);
                                        Log.d("Tomato","send:"+SignOutReceiver.ON_SIGNOUT_FINISH);
                                        intent.putExtra("isLogin",false);
                                        sendBroadcast(intent);
                                    }
                                })
                                .setNegativeButton("返回", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.d("Tomato", "no exit");
                                    }
                                }).show();

                        break;
                }
                return false;
            }
        });



    }

    public void initNavigation(){

        if(isLogin){
            user_text.setText(user_name);
        }else{
            user_text.setText("未登录");
        }


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
            startActivity(intent);
        }
    }

    @OnClick({R.id.music_local_rl,R.id.music_mother_rl,R.id.music_find_rl})
    public void music_choice(RelativeLayout relativeLayout){
        Intent intent = new Intent();
        switch(relativeLayout.getId()){
            case R.id.music_local_rl:
                intent.setClass(MusicChoiceActivity.this,FindMusicActivity.class);
                intent.putExtra("user_id",user_id);
                startActivity(intent);
                break;
            case R.id.music_mother_rl:
                intent.setClass(MusicChoiceActivity.this,MotherMusicActivity.class);
                startActivity(intent);
                break;
            case R.id.music_find_rl:
                intent.setClass(MusicChoiceActivity.this,PreferMusicActivity.class);
                startActivity(intent);
                break;
        }
    }


    /**
     * 已注册成功时，自动登陆
     */
    @Override
    public void onSignInFinish() {
        isLogin = StateUtils.isLogin();
        Message msg = new Message();
        msg.what = NEW_USER;
        handler.sendMessage(msg);

    }

    /**
     * 已登陆成功时
     */
    @Override
    public void onLoginFinished() {
        isLogin = StateUtils.isLogin();
        Message msg = new Message();
        msg.what = LOGIN_SUCCESS;
        handler.sendMessage(msg);
    }

    /**
     * 退出登录状态
     */
    @Override
    public void onSignOutFinish() {
        isLogin = StateUtils.isLogin();
        Log.d("Tomato","signout!");
        Message msg = new Message();
        msg.what = SIGNOUT_SUCCESS;
        handler.sendMessage(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(loginReceiver!=null){
            unregisterReceiver(loginReceiver);
        }
        if(signinReceiver!=null){
            unregisterReceiver(signinReceiver);
        }
        if(signOutReceiver!=null){
            unregisterReceiver(signOutReceiver);
        }

    }
}
