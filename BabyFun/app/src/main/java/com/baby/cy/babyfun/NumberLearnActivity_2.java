package com.baby.cy.babyfun;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class NumberLearnActivity_2 extends Activity {

    private ImageButton number2_learn_1;
    private ImageButton Number2_learn_2;
    private ImageButton Number2_learn_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.number_learn_2);

        //点击home按钮  回到主界面
        Number2_learn_home = (ImageButton)findViewById(R.id.number2_learn_3);
        Number2_learn_home.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });


        //点击07按钮 进入下一界面
        number2_learn_1= (ImageButton)findViewById(R.id.number2_learn_1);
        number2_learn_1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(NumberLearnActivity_2.this, NumberLearnActivity_3.class);
                startActivity(intent);
            }
        });


        //点击05按钮  不执行
        Number2_learn_2= (ImageButton)findViewById(R.id.number2_learn_2);
        Number2_learn_2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(NumberLearnActivity_2.this, "再想想？", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
