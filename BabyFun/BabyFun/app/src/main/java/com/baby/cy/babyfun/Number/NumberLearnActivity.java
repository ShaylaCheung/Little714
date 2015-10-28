package com.baby.cy.babyfun.Number;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.baby.cy.babyfun.R;
import com.zzt.library.MaterialImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NumberLearnActivity extends Activity {

    @Bind(R.id.number_learn_image)
    MaterialImageView number_learn_image;
    @Bind(R.id.number_learn)ImageView imageView;

    private int[] Number_Pic_ID = {R.drawable.animal_chicken,R.drawable.animal_elephant,R.drawable.animal_dog,R.drawable.animal_camel,
            R.drawable.animal_bird,R.drawable.animal_crab,R.drawable.animal_snake,R.drawable.animal_lion,
            R.drawable.animal_snail,R.drawable.animal_tortoise,R.drawable.animal_rabbit, R.drawable.animal_panda,
            R.drawable.animal_duck, R.drawable.animal_sheep,R.drawable.animal_fish,R.drawable.animal_horse};

    private int[] number_name_id = {R.drawable.number1,R.drawable.number2,R.drawable.number3,R.drawable.number4,
            R.drawable.number5,R.drawable.number6,R.drawable.number7,R.drawable.number8,R.drawable.number9,
            R.drawable.number10};

    private int count = 0;

//    private RandomUtils randomUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.number_learn);

        ButterKnife.bind(this);
        //设置MaterialImageView图片的偏移量
        number_learn_image.setRotation(-10);

//        randomUtils = new RandomUtils(animals_ID,materialImageView);
//        randomUtils.setLearnImageBackground(count);
        initImage(count);
    }

    @OnClick(R.id.number_learn_image)
    public void nextNumber(){
        count++;
//        randomUtils.setLearnImageBackground(count);
        initImage(count);
    }

    public void initImage(int index){
        number_learn_image.setImageResource(Number_Pic_ID[index]);
        imageView.setImageResource(number_name_id[index]);
    }
}
