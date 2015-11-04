package com.baby.cy.babyfun.Music;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baby.cy.babyfun.LoginReceiver;
import com.baby.cy.babyfun.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import Utils.StateUtils;
import Utils.URLUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetUserActivity extends AppCompatActivity {

    @Bind(R.id.set_user_toolbar)Toolbar toolbar;
    @Bind(R.id.set_user_name) EditText update_user_name;
    @Bind(R.id.set_name_text) TextView set_name_text;
    @Bind(R.id.set_user_phone) EditText update_user_phone;
    @Bind(R.id.set_phone_text) TextView set_phone_text;
    @Bind(R.id.update_layout) LinearLayout update_layout;

    private static final int CHANGE_TEXT_INVISIBLE = 0;
    private static final int CHANGE_TEXT_VISIBLE = 1;

    private static final int CHANGE_PHONE_INVISIBLE = 2;
    private static final int CHANGE_PHONE_VISIBLE = 3;
    private static final int UPDATE_SUCCESS = 4;
    private RequestQueue mQueue;
    private String update_name;
    private String update_phone;
    private Long user_id;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CHANGE_TEXT_INVISIBLE:
                    set_name_text.setVisibility(View.INVISIBLE);
                    break;
                case CHANGE_TEXT_VISIBLE:
                    set_name_text.setVisibility(View.VISIBLE);
                    break;
                case CHANGE_PHONE_INVISIBLE:
                    set_phone_text.setVisibility(View.INVISIBLE);
                    break;
                case CHANGE_PHONE_VISIBLE:
                    set_phone_text.setVisibility(View.VISIBLE);
                    break;
                case UPDATE_SUCCESS:
                    set_name_text.setVisibility(View.VISIBLE);
                    set_name_text.setText(StateUtils.getUser_name());
                    set_phone_text.setVisibility(View.VISIBLE);
                    set_phone_text.setText(StateUtils.getUser_phone());
                    update_user_name.setText(null);
                    update_user_phone.setText(null);
                    update_layout.setFocusableInTouchMode(true);
                    update_layout.setFocusable(true);
                    break;
                default:break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_user_layout);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetUserActivity.this.finish();
            }
        });

        mQueue = Volley.newRequestQueue(this);

        initSetView();
        update_user_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!update_user_name.getText().toString().equals("")) {
                    Message message = new Message();
                    message.what = CHANGE_TEXT_INVISIBLE;
                    handler.sendMessage(message);
                } else {
                    Message message = new Message();
                    message.what = CHANGE_TEXT_VISIBLE;
                    handler.sendMessage(message);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        update_user_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!update_user_phone.getText().toString().equals("")) {
                    Message message = new Message();
                    message.what = CHANGE_PHONE_INVISIBLE;
                    handler.sendMessage(message);
                } else {
                    Message message = new Message();
                    message.what = CHANGE_PHONE_VISIBLE;
                    handler.sendMessage(message);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    /**
     * 初始化用户信息设置界面
     */
    public void initSetView(){
        if(StateUtils.isLogin()){
            //已经登录成功
            user_id = StateUtils.getUser_id();
            set_name_text.setText(StateUtils.getUser_name());
            set_phone_text.setText(StateUtils.getUser_phone());

        }else{
            new AlertDialog.Builder(this)
                    .setTitle("更改用户信息失败")
                    .setMessage("您未登录，请先登录？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(SetUserActivity.this, UserActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            play_layout.setBackgroundResource(R.drawable.play_unlogin_bg);
                        }
                    })
                    .show();
        }
    }


    @OnClick(R.id.update)
    public void update(){
        update_name = update_user_name.getText().toString();
        update_phone = update_user_phone.getText().toString();
        if(update_name.equals("")) {
            update_name = set_name_text.getText().toString();
            Log.d("Tomato","update_name"+update_name);
        }
        if(update_phone.equals("")){
            update_phone = set_phone_text.getText().toString();
            Log.d("Tomato","update_phone"+update_phone);
        }
        postisPhoneSignInParams(URLUtils.updateUserInformation_url,update_name,update_phone,user_id);
    }

    public void postisPhoneSignInParams(String url,final String user_name,final String user_phone,final Long user_id){

        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        boolean update_state  = parseUpdateJson(response);
                        if(update_state){
                            //更新成功
                            Toast.makeText(getBaseContext(),"更新信息成功",Toast.LENGTH_SHORT).show();
                            StateUtils.setUser_name(update_name);
                            StateUtils.setUser_phone(update_phone);
                            Message msg = new Message();
                            msg.what = UPDATE_SUCCESS;
                            handler.sendMessage(msg);

                            //发送广播
                            Intent login_intent = new Intent(LoginReceiver.ON_LOGIN_FINISH);
                            login_intent.putExtra("user_id", user_id);
                            login_intent.putExtra("user_name", update_name);
                            login_intent.putExtra("user_phone", update_phone);
                            sendBroadcast(login_intent);
                        }else{
                            Toast.makeText(getBaseContext(),"更新失败",Toast.LENGTH_SHORT).show();
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
                data.put("user_name", user_name);
                data.put("user_phone", user_phone);
                data.put("user_id", user_id.toString());
                return data;
            }
        };
        mQueue.add(stringRequest);
    }
    /**
     * 解析JSON
     * @param response
     * @return
     */
    public boolean parseUpdateJson(String response){
        boolean update_state = false;
        try{
            JSONObject data = new JSONObject(response);
            update_state = data.getBoolean("update_state");
            Log.d("Tomato",update_state+"");
        }catch (Exception e){
            e.printStackTrace();
        }
        return update_state;
    }


}
