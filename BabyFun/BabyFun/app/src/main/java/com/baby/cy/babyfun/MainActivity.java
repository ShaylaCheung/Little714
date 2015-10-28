package com.baby.cy.babyfun;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.ImageButton;

import com.baby.cy.babyfun.Animal.AnimalChoiceActivity;
import com.baby.cy.babyfun.Music.MusicChoiceActivity;
import com.baby.cy.babyfun.Number.NumberChoiceActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.main_animal,R.id.main_char,R.id.main_number,R.id.main_music})
    public void choiceGame(ImageButton imageButton){
        switch (imageButton.getId()){
            case R.id.main_animal:
                Intent animalIntent = new Intent(MainActivity.this,AnimalChoiceActivity.class);
                startActivity(animalIntent);
                break;
            case R.id.main_char:
                break;
            case R.id.main_number:
                Intent numberIntent = new Intent(MainActivity.this, NumberChoiceActivity.class);
                startActivity(numberIntent);
                break;
            case R.id.main_music:
                Intent musicIntent = new Intent(MainActivity.this,MusicChoiceActivity.class);
                startActivity(musicIntent);
                break;
        }
    }
}



