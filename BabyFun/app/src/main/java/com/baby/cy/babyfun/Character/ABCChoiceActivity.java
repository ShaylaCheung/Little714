package com.baby.cy.babyfun.Character;

import com.baby.cy.babyfun.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class ABCChoiceActivity extends Activity {

    private Button abc_learn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abc_choices);

        abc_learn  = (Button)findViewById(R.id.abc_learn);

        abc_learn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ABCChoiceActivity.this,ABC_Learn_Activity.class);
                startActivity(intent);
            }
        });

    }

}
