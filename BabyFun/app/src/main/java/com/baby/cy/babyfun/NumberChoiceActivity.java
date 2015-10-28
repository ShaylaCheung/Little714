package com.baby.cy.babyfun;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NumberChoiceActivity extends Activity {

    private Button number_choice_learn;
    private Button number_choice_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.number_choice);

        number_choice_learn= (Button)findViewById(R.id.number_choice_learn);
        number_choice_home = (Button)findViewById(R.id.number_choice_home);

        //点击进入学习界面的按钮
        number_choice_learn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NumberChoiceActivity.this,NumberLearnActivity_1.class);
                startActivity(intent);
            }
        });

        //点击进入主界面的按钮
        number_choice_home.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
