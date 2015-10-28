package com.baby.cy.babyfun;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

public class NumberLearnActivity_3 extends Activity {
    private ImageButton number3_learn_1;
    private ImageButton number3_learn_2;
    private ImageButton number3_learn_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.number_learn_3);

        //点击home按钮  回到主界面
        number3_learn_3 = (ImageButton)findViewById(R.id.number3_learn_3);
        number3_learn_3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });


        //点击07按钮 进入下一界面
        number3_learn_2= (ImageButton)findViewById(R.id.number3_learn_2);
        number3_learn_2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(NumberLearnActivity_3.this, NumberLearnActivity_4.class);
                startActivity(intent);
            }
        });



        //点击06按钮  不执行
        number3_learn_1= (ImageButton)findViewById(R.id.number3_learn_1);
        number3_learn_1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(NumberLearnActivity_3.this, "再想想？", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
