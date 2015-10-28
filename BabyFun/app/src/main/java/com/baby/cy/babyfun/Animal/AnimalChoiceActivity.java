package com.baby.cy.babyfun.Animal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.baby.cy.babyfun.R;


public class AnimalChoiceActivity extends Activity {

    private Button animal_learn;
    private Button animal_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_choices);
        animal_learn  = (Button)findViewById(R.id.animal_learn);
        animal_test = (Button)findViewById(R.id.animal_test);

        animal_learn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnimalChoiceActivity.this, Animal_Learn_Activity.class);
                startActivity(intent);
            }
        });

        animal_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnimalChoiceActivity.this,Animal_Test_Activity.class);
                startActivity(intent);
            }
        });
    }

}
