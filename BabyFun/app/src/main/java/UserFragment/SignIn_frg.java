package UserFragment;

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
import com.baby.cy.babyfun.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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

    public RequestQueue mQueue;
    private boolean is_signin_success = false;
    private static final String signin_url = "http://192.168.1.114:8080/BabyFun/api/addUser";

    public SignIn_frg(){

        super(R.layout.signin_layout);


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

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signin_error_layout.setVisibility(View.INVISIBLE);
                String name = signin_name.getText().toString();
                String phone = signin_phone.getText().toString();
                String password1 = signin_password_1.getText().toString();
                String password2 = signin_password_2.getText().toString();

                if (!phone.equals("")) {
                    if (!name.equals("")) {
                        if (!password1.equals("")) {
                            if (!password2.equals("")) {
                                if (password1.equals(password2)) {

                                    postLoginParams(signin_url,name,password1,phone);
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



    public void postLoginParams(String url, final String user_name, final String user_password,final String user_phone){
        Log.d("Tomato","aaaaa");

        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        is_signin_success = parseUserJson(response);
                        if(is_signin_success){
                            Toast.makeText(getContext(), "注册成功", Toast.LENGTH_SHORT).show();
//                            viewPager.setCurrentItem(0);
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


    public boolean parseUserJson(String response){

        boolean signin_statue = false;
        try{
            JSONObject jsonObject = new JSONObject(response);
            signin_statue = jsonObject.getBoolean("signin_state");

        }catch (Exception e){
            e.printStackTrace();
        }
        return signin_statue;
    }
}
