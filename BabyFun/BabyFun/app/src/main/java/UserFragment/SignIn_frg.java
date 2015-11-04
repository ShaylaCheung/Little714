package UserFragment;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.baby.cy.babyfun.Bean.User;
import com.baby.cy.babyfun.Music.MusicChoiceActivity;
import com.baby.cy.babyfun.R;
import com.baby.cy.babyfun.SignInReceiver;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import Utils.StateUtils;
import Utils.URLUtils;


/**
 * Created by Administrator on 2015/10/14.
 */
public class SignIn_frg extends BaseFragment{

    private EditText signin_phone;
    private EditText signin_name;
    private EditText signin_password_1;
    private EditText signin_password_2;
    private Button signin;
    private LinearLayout signin_error_layout;
    private TextView signin_error_message;
    private ViewPager viewPager;
    private TabLayout login_tab_layout;

    private RequestQueue mQueue;
    private User newUser = null;

    private String name;
    private String phone;
    private String password1;
    private String password2;

    public SignIn_frg(){
        super(R.layout.signin_layout);
    }
    public User getNewUser() {
        return newUser;
    }
    public void setNewUser(User newUser) {
        this.newUser = newUser;
    }
    @Override
    public void initView(View view) {
        mQueue = Volley.newRequestQueue(view.getContext());

        signin_phone = (EditText)view.findViewById(R.id.signin_phone);
        signin_name = (EditText)view.findViewById(R.id.signin_name);
        signin_password_1 = (EditText)view.findViewById(R.id.signin_password_1);
        signin_password_2 = (EditText)view.findViewById(R.id.signin_password_2);
        signin = (Button)view.findViewById(R.id.signin);
        signin_error_layout = (LinearLayout)view.findViewById(R.id.signin_error_layout);
        signin_error_message = (TextView)view.findViewById(R.id.signin_error_message);
        viewPager = (ViewPager)view.findViewById(R.id.login_view_pager);
        login_tab_layout = (TabLayout)view.findViewById(R.id.login_tab_layout);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signin_error_layout.setVisibility(View.INVISIBLE);
                name = signin_name.getText().toString();
                phone = signin_phone.getText().toString();
                password1 = signin_password_1.getText().toString();
                password2 = signin_password_2.getText().toString();

                if (!phone.equals("")) {
                    if (!name.equals("")) {
                        if (!password1.equals("")) {
                            if (!password2.equals("")) {
                                if (password1.equals(password2)) {
                                    postisPhoneSignInParams(URLUtils.isPhoneSignIn_url,phone);
                                } else {
                                    signin_error_layout.setVisibility(View.VISIBLE);
                                    signin_error_message.setText("密码不一致");
                                }
                            } else {
                                signin_error_layout.setVisibility(View.VISIBLE);
                                signin_error_message.setText("请确认密码");
                            }
                        } else {
                            signin_error_layout.setVisibility(View.VISIBLE);
                            signin_error_message.setText("密码不能为空");
                        }
                    } else {
                        signin_error_layout.setVisibility(View.VISIBLE);
                        signin_error_message.setText("请填写昵称");
                    }
                } else {
                    signin_error_layout.setVisibility(View.VISIBLE);
                    signin_error_message.setText("请填写注册手机号");
                }
            }
        });
    }

    /**
     * 发送注册请求
     * @param url
     * @param user_name
     * @param user_password
     * @param user_phone
     */
    public void postSignInParams(String url, final String user_name, final String user_password,final String user_phone){

        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        newUser = parseSignInJson(response);
                        if(newUser.getId()!=null){
                            Toast.makeText(getContext(), "注册成功", Toast.LENGTH_SHORT).show();
                            StateUtils.setIsLogin(true);
                            StateUtils.setUser_name(newUser.getUser_name());
                            StateUtils.setUser_id(newUser.getId());
                            StateUtils.setUser_phone(newUser.getUser_phone());
                            Intent intent = new Intent(SignInReceiver.ON_SIGNIN_FINISH);
                            intent.putExtra("user_id",newUser.getId());
                            intent.putExtra("user_name",newUser.getUser_name());
                            intent.putExtra("user_phone",newUser.getUser_phone());
                            //发送广播
                            getActivity().sendBroadcast(intent);
                            getActivity().finish();

                            MusicChoiceActivity.setLoginImageText(newUser.getUser_name(),newUser.getId());
                        }else{
                            Toast.makeText(getContext(), "注册失败", Toast.LENGTH_SHORT).show();
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
                data.put("user_password", user_password);
                data.put("user_phone", user_phone);
                return data;
            }
        };
        mQueue.add(stringRequest);

    }

    /**
     * 解析注册请求获得的JSON字符串
     * @param response
     * @return
     */
    public User parseSignInJson(String response){

        User j_user = new User();
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONObject data = jsonObject.getJSONObject("user");

            j_user.setId(data.getLong("id"));
            j_user.setUser_name(data.getString("user_name"));
            j_user.setUser_password(data.getString("user_password"));
            j_user.setUser_password(data.getString("user_phone"));


        }catch (Exception e){
            e.printStackTrace();
        }
        Log.d("Tomato", "jname:" + j_user.getUser_name());
        return j_user;
    }


    /**
     * 发送请求，判读欲注册的手机号码是否已经注册
     * @param url
     * @param user_phone
     */
    public void postisPhoneSignInParams(String url,final String user_phone){

        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        boolean isPhoneSignIn  = parseisPhoneSignInJson(response);
                        if(isPhoneSignIn){
                            //欲注册的手机号码已被注册
                            signin_error_layout.setVisibility(View.VISIBLE);
                            signin_error_message.setText("该手机号码已被注册");
                        }else{
                            //该手机号未被注册，判断昵称是否被注册
                            postisNameSignInParams(URLUtils.isNameSignIn_url,name);
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
                data.put("user_phone", user_phone);
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
    public boolean parseisPhoneSignInJson(String response){
        boolean isPhoneSignIn = false;
        try{
            JSONObject data = new JSONObject(response);
            isPhoneSignIn = data.getBoolean("isPhoneSignIn");
        }catch (Exception e){
            e.printStackTrace();
        }
        return isPhoneSignIn;
    }

    /**
     * 发送请求，判读欲注册的用户昵称是否已经注册
     * @param url
     * @param user_name
     */
    public void postisNameSignInParams(String url,final String user_name){

        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        boolean isNameSignIn  = parseisNameSignInJson(response);
                        if(isNameSignIn){
                            //欲注册的用户昵称已被注册
                            signin_error_layout.setVisibility(View.VISIBLE);
                            signin_error_message.setText("该昵称已被注册");
                        }else{
                            //该用户昵称未被注册
                            //注册成功
                            postSignInParams(URLUtils.signin_url, name, password1, phone);
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
    public boolean parseisNameSignInJson(String response){
        boolean isNameSignIn = false;
        try{
            JSONObject data = new JSONObject(response);
            isNameSignIn = data.getBoolean("isNameSignIn");
        }catch (Exception e){
            e.printStackTrace();
        }
        return isNameSignIn;
    }

}
