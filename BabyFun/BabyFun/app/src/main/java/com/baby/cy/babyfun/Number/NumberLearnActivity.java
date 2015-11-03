package com.baby.cy.babyfun.Number;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.baby.cy.babyfun.R;
import com.zzt.library.MaterialImageView;

import Utils.RandomUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NumberLearnActivity extends Activity {

    @Bind(R.id.number_learn_image)
    MaterialImageView number_learn_image;
    @Bind(R.id.number_learn)ImageView imageView;

    private int[] Number_Pic_ID = {R.drawable.math_num_1,R.drawable.math_num_2,
            R.drawable.math_num_3, R.drawable.math_num_4,R.drawable.math_num_5,R.drawable.math_num_6,
            R.drawable.math_num_7, R.drawable.math_num_8,R.drawable.math_num_9, R.drawable.math_num_10};

    private int[] number_name_id = {R.drawable.number1,R.drawable.number2,R.drawable.number3,R.drawable.number4,
            R.drawable.number5,R.drawable.number6,R.drawable.number7,R.drawable.number8,R.drawable.number9,
            R.drawable.number10};

    private int count = 0;

    private RandomUtils randomUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.number_learn);

        ButterKnife.bind(this);
        //设置MaterialImageView图片的偏移量
        number_learn_image.setRotation(-10);

        randomUtils = new RandomUtils(Number_Pic_ID,number_learn_image);
        randomUtils.setLearnImageBackground(count);
        imageView.setImageResource(number_name_id[randomUtils.getRight_choice_sort()[count % Number_Pic_ID.length]]);


    }

    @OnClick(R.id.number_learn_image)
    public void nextNumber(){
        count++;
        randomUtils.setLearnImageBackground(count);
        imageView.setImageResource(number_name_id[randomUtils.getRight_choice_sort()[count%Number_Pic_ID.length]]);
    }

}
