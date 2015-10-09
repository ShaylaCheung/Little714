package com.baby.cy.babyfun;


import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

public class NumberLearnActivity_4 extends Activity {

    private ImageButton bhome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.number_learn_4);

        //点击home按钮  回到主界面
        bhome = (ImageButton)findViewById(R.id.number4_learn_3);
        bhome.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });



    }
}
