package UserFragment;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baby.cy.babyfun.UserBean.User;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.baby.cy.babyfun.R;

/**
 * Created by Administrator on 2015/10/14.
 */
public class SignIn_frg extends BaseFragment{

    private EditText signin_phone;
    private EditText signin_name;
    private EditText signin_password_1;
    private EditText signin_password_2;
    private Button signin;
    private LinearLayout error_layout;
    private TextView error_message;

    private static final String path = "http://192.168.1.104:8080/BabyFun/api/addUser";

    public SignIn_frg(){

        super(R.layout.signin_layout);

    }

    @Override
    public void initView(View view) {
        signin_phone = (EditText)view.findViewById(R.id.signin_phone);
        signin_name = (EditText)view.findViewById(R.id.signin_name);
        signin_password_1 = (EditText)view.findViewById(R.id.signin_password_1);
        signin_password_2 = (EditText)view.findViewById(R.id.signin_password_2);
        signin = (Button)view.findViewById(R.id.signin);
        error_layout = (LinearLayout)view.findViewById(R.id.error_layout);
        error_message = (TextView)view.findViewById(R.id.error_message);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                error_layout.setVisibility(View.INVISIBLE);
                User user = new User();
                String name = signin_name.getText().toString();
                String phone = signin_phone.getText().toString();
                String password1 = signin_password_1.getText().toString();
                String password2 = signin_password_2.getText().toString();

                if (!phone.equals("")) {
                    if (!name.equals("")) {
                        if (!password1.equals("")) {
                            if (!password2.equals("")) {
                                if (password1.equals(password2)) {
                                    user.setUser_name(name);
                                    user.setUser_phone(phone);
                                    user.setUser_password(password1);

                                    doAction(path,name,password1,phone);

                                } else {
                                    error_layout.setVisibility(View.VISIBLE);
                                    error_message.setText("密码不一致");
                                }
                            } else {
                                error_layout.setVisibility(View.VISIBLE);
                                error_message.setText("请确认密码");
                            }
                        } else {
                            error_layout.setVisibility(View.VISIBLE);
                            error_message.setText("密码不能为空");
                        }
                    } else {
                        error_layout.setVisibility(View.VISIBLE);
                        error_message.setText("请填写昵称");
                    }
                } else {
                    error_layout.setVisibility(View.VISIBLE);
                    error_message.setText("请填写注册手机号");

                }

            }
        });
    }




    //发送数据
    public static boolean doAction(String path,String user_name, String user_password, String user_phone) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("user_name", user_name);
        params.put("user_password", user_password);
        params.put("user_phone",user_phone);
        try {
            return sendGETRequest(path, params, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }




    private static boolean sendGETRequest(String path, Map<String, String> params, String encoding) throws Exception {
        StringBuilder url = new StringBuilder(path);
        url.append("?");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            url.append(entry.getKey()).append("=");
            url.append(URLEncoder.encode(entry.getValue(), encoding));// 编码带中文的请求路径
            url.append("&");
        }
        url.deleteCharAt(url.length() - 1);// 最后会多一个'&'
        Log.d("Tomato",url.toString());
        HttpURLConnection conn = (HttpURLConnection) new URL(url.toString()).openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() == 200)
            return true;
        return false;
    }

}
