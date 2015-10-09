package com.baby.cy.babyfun;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class NumberLearnActivity_1 extends Activity {
    private ImageButton number1_learn_1;
    private ImageButton number1_learn_2;
    private ImageButton number1_learn_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.number_learn_1);

        //点击home按钮  回到主界面
        number1_learn_3 = (ImageButton)findViewById(R.id.number1_learn_3);
        number1_learn_3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });


        //点击10按钮 进入下一界面
        number1_learn_1= (ImageButton)findViewById(R.id.number1_learn_1);
        number1_learn_1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NumberLearnActivity_1.this, NumberLearnActivity_2.class);
                startActivity(intent);
            }
        });


        //点击08按钮  不执行
        number1_learn_2= (ImageButton)findViewById(R.id.number1_learn_2);
        number1_learn_2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(NumberLearnActivity_1.this, "再想想？", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
