package com.baby.cy.babyfun.Music;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.baby.cy.babyfun.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SetUserActivity extends AppCompatActivity {

    @Bind(R.id.set_user_toolbar)Toolbar toolbar;
    @Bind(R.id.set_user_name) EditText set_user_name;
    @Bind(R.id.set_name_text) TextView set_name_text;
    @Bind(R.id.set_user_phone) EditText set_user_phone;
    @Bind(R.id.set_phone_text) TextView set_phone_text;

    private static final int CHANGE_TEXT_INVISIBLE = 0;
    private static final int CHANGE_TEXT_VISIBLE = 1;

    private static final int CHANGE_PHONE_INVISIBLE = 2;
    private static final int CHANGE_PHONE_VISIBLE = 3;


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

        set_user_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!set_user_name.getText().toString().equals("")) {
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

        set_user_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!set_user_phone.getText().toString().equals("")) {
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

}
