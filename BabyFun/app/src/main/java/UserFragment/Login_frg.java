package UserFragment;

import android.content.Intent;
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
import com.baby.cy.babyfun.LoginReceiver;
import com.baby.cy.babyfun.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import Utils.StateUtils;
import Utils.URLUtils;


/**
 * Created by Administrator on 2015/10/14.
 */
public class Login_frg extends BaseFragment {

    private EditText login_phone;
    private EditText login_password;
    private Button login;

    private LinearLayout login_error_layout;
    private TextView login_error_message;

    public RequestQueue mQueue;

    private static User user;

    public Login_frg(){
        super(R.layout.login_layout);
    }

    @Override
    public void initView(View view) {
        login_phone = (EditText)view.findViewById(R.id.login_phone);
        login_password = (EditText)view.findViewById(R.id.login_password);
        login = (Button)view.findViewById(R.id.login);

        login_error_layout = (LinearLayout)view.findViewById(R.id.login_error_layout);
        login_error_message = (TextView)view.findViewById(R.id.login_error_message);

        mQueue = Volley.newRequestQueue(view.getContext());

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_error_layout.setVisibility(View.INVISIBLE);
                String user_phone = login_phone.getText().toString();
                String user_password = login_password.getText().toString();

                if (!user_phone.equals("")) {
                    if (!user_password.equals("")) {
                        //登陆成功
                        postLoginParams(URLUtils.login_url, user_phone, user_password);

                    } else {

                        login_error_layout.setVisibility(View.VISIBLE);
                        login_error_message.setText("密码不能为空");
                    }
                } else {
                    login_error_layout.setVisibility(View.VISIBLE);
                    login_error_message.setText("手机号码不能为空");
                }
            }
        });


    }


    public void postLoginParams(String url, final String user_phone, final String user_password){

        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        user = parseUserJson(response);
                        if(user.getId()==null){
                            login_error_layout.setVisibility(View.VISIBLE);
                            login_error_message.setText("用户不存在或密码错误");
                        }else{
                            Toast.makeText(getContext(),"登录成功",Toast.LENGTH_SHORT).show();

                            StateUtils.setIsLogin(true);
                            StateUtils.setUser_name(user.getUser_name());
                            StateUtils.setUser_id(user.getId());
                            //登陆成功，发送广播
                            Intent login_intent = new Intent(LoginReceiver.ON_LOGIN_FINISH);
                            login_intent.putExtra("user_id",user.getId());
                            login_intent.putExtra("user_name",user.getUser_name());
                            getActivity().sendBroadcast(login_intent);
                            getActivity().finish();

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
                Map<String, String> data = new HashMap<String, String>();
                data.put("user_phone", user_phone);
                data.put("user_password", user_password);
                return data;
            }
        };
        mQueue.add(stringRequest);
    }


    public User parseUserJson(String response){
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
        return j_user;
    }

}