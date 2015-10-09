package com.baby.cy.babyfun;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Animal_Learn_Activity extends Activity {

    private ImageView animal_kinds;
    private TextView animal_name;
    private TextView animal_eng_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal__learn);

        animal_kinds = (ImageView)findViewById(R.id.animal_kinds);
        animal_name = (TextView)findViewById(R.id.animal_name);
        animal_eng_name = (TextView)findViewById(R.id.animal_eng_name);

        animal_kinds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animal_kinds.setBackgroundResource(R.mipmap.animal_demo_2);
                animal_eng_name.setText("DOG");
                animal_name.setText("狗");
            }
        });
    }

}
