package com.baby.cy.babyfun.Animal;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.baby.cy.babyfun.R;
import com.zzt.library.MaterialImageView;

import Utils.RandomUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Animal_Learn_Activity extends Activity {

    @Bind(R.id.animal_learn)
    MaterialImageView materialImageView;
    @Bind(R.id.animal_name)TextView animal_name_text;
    @Bind(R.id.animal_eng_name)TextView animal_eng_name_text;

    private int[] animals_ID = {R.drawable.animal_chicken,R.drawable.animal_elephant,R.drawable.animal_dog,R.drawable.animal_camel,
            R.drawable.animal_bird,R.drawable.animal_crab,R.drawable.animal_snake,R.drawable.animal_lion,
            R.drawable.animal_snail,R.drawable.animal_tortoise,R.drawable.animal_rabbit, R.drawable.animal_panda,
            R.drawable.animal_duck, R.drawable.animal_sheep,R.drawable.animal_fish,R.drawable.animal_horse};

    private String[] animal_names = {"公鸡","大象","狗","骆驼","鸟","螃蟹","蛇","狮子","蜗牛","乌龟","兔子","熊猫",
            "鸭子","羊","鱼","马"};
    private String[] animal_eng_name = {"Chicken","Elephant","Dog","Camel","Bird","Crab","Snake","Lion","Snail",
            "Tortoise","Rabbit","Panda","Duck","Sheep","Fish","Horse"};
    private int count = 0;

    private RandomUtils randomUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal__learn);

        ButterKnife.bind(this);
        //设置MaterialImageView图片的偏移量
        materialImageView.setRotation(-10);
        randomUtils = new RandomUtils(animals_ID,materialImageView);
        randomUtils.setLearnImageBackground(count);
        animal_eng_name_text.setText(animal_names[randomUtils.getRight_choice_sort()[count%animals_ID.length]]);
        animal_name_text.setText(animal_eng_name[randomUtils.getRight_choice_sort()[count%animals_ID.length]]);
    }

    @OnClick(R.id.animal_learn)
    public void nextAnimal(){
        count++;
        randomUtils.setLearnImageBackground(count);
        animal_eng_name_text.setText(animal_names[randomUtils.getRight_choice_sort()[count%animals_ID.length]]);
        animal_name_text.setText(animal_eng_name[randomUtils.getRight_choice_sort()[count%animals_ID.length]]);

    }
}
