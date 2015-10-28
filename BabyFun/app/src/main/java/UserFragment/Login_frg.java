package UserFragment;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.baby.cy.babyfun.NetwordOperate;
import com.baby.cy.babyfun.R;


/**
 * Created by Administrator on 2015/10/14.
 */
public class Login_frg extends BaseFragment {

    private EditText login_phone;
    private EditText login_password;
    private Button login;

    private NetwordOperate no;
    private String url = "http://192.168.1.104:8080/BabyFun/api/login";
    public Login_frg(){
        super(R.layout.login_layout);
    }

    @Override
    public void initView(View view) {
        login_phone = (EditText)view.findViewById(R.id.login_phone);
        login_password = (EditText)view.findViewById(R.id.login_password);
        login = (Button)view.findViewById(R.id.login);

        no = new NetwordOperate(this.getContext());

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_phone = login_phone.getText().toString();
                String user_password = login_password.getText().toString();

                if(user_phone!=null) {
                    if (user_password != null) {
                        no.postLoginParams(url, user_phone, user_password);
                    }else{
                        Log.d("Tomato","password null");
                    }
                }else{
                    Log.d("Tomato","user_name null");
                }
            }
        });
    }
}