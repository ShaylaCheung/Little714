package com.baby.cy.babyfun.Animal;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baby.cy.babyfun.R;

import Utils.RandomUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Animal_Test_Activity extends Activity {

    private int[] animals_ID = {R.drawable.animal_chicken,R.drawable.animal_elephant,R.drawable.animal_dog,R.drawable.animal_camel,
            R.drawable.animal_bird,R.drawable.animal_crab,R.drawable.animal_snake,R.drawable.animal_lion,
            R.drawable.animal_snail,R.drawable.animal_tortoise,R.drawable.animal_rabbit, R.drawable.animal_panda,
            R.drawable.animal_duck, R.drawable.animal_sheep,R.drawable.animal_fish,R.drawable.animal_horse};

    private String[] animal_names = {"公鸡","大象","狗","骆驼","鸟","螃蟹","蛇","狮子","蜗牛","乌龟","兔子","熊猫",
        "鸭子","羊","鱼","马"};
    private String[] animal_eng_name = {"Chicken","Elephant","Dog","Camel","Bird","Crab","Snake","Lion","Snail",
            "Tortoise","Rabbit","Panda","Duck","Sheep","Fish","Horse"};

    private ImageView[] image;
    private RandomUtils randomUtils;
    private int count;

    @Bind(R.id.test_animal_1) ImageView test_image_1;
    @Bind(R.id.test_animal_2) ImageView test_image_2;
    @Bind(R.id.test_animal_3) ImageView test_image_3;
    @Bind(R.id.test_animal_4) ImageView test_image_4;
    @Bind(R.id.test_animal_5) ImageView test_image_5;
    @Bind(R.id.test_animal_6) ImageView test_image_6;
    @Bind(R.id.test_animal_7) ImageView test_image_7;
    @Bind(R.id.test_animal_8) ImageView test_image_8;
    @Bind(R.id.test_animal_9) ImageView test_image_9;
    @Bind(R.id.test_animal_10) ImageView test_image_10;
    @Bind(R.id.test_animal_11) ImageView test_image_11;
    @Bind(R.id.test_animal_12) ImageView test_image_12;
    @Bind(R.id.test_animal_13) ImageView test_image_13;
    @Bind(R.id.test_animal_14) ImageView test_image_14;
    @Bind(R.id.test_animal_15) ImageView test_image_15;
    @Bind(R.id.test_animal_16) ImageView test_image_16;

    @Bind(R.id.animal_test_name) TextView animalName;
    @Bind(R.id.animal_test_eng_name) TextView animalEngName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal__test);
        ButterKnife.bind(this);
        initAnimalTestImage();
    }

    public void initAnimalTestImage(){
        image=new ImageView[animals_ID.length];
        image[0] = test_image_1;
        image[1] = test_image_2;
        image[2] = test_image_3;
        image[3] = test_image_4;
        image[4] = test_image_5;
        image[5] = test_image_6;
        image[6] = test_image_7;
        image[7] = test_image_8;
        image[8] = test_image_9;
        image[9] = test_image_10;
        image[10] = test_image_11;
        image[11] = test_image_12;
        image[12] = test_image_13;
        image[13] = test_image_14;
        image[14] = test_image_15;
        image[15] = test_image_16;

        randomUtils = new RandomUtils(animals_ID,image);
        count = randomUtils.getChoice_count();

        randomUtils.setTestImageBackground(randomUtils.getChoice_count());

        animalName.setText(animal_names[randomUtils.getRight_choice_sort()[count]]);
        animalEngName.setText(animal_eng_name[randomUtils.getRight_choice_sort()[count]]);
    }




    @OnClick({R.id.test_animal_1,R.id.test_animal_2,R.id.test_animal_3,R.id.test_animal_4,R.id.test_animal_5,
            R.id.test_animal_6,R.id.test_animal_7,R.id.test_animal_8,R.id.test_animal_9,
            R.id.test_animal_10,R.id.test_animal_11,R.id.test_animal_12,R.id.test_animal_13,
            R.id.test_animal_14,R.id.test_animal_15,R.id.test_animal_16})
    public void choice_Result(ImageView image){
        if(image.getTag()!=null){

            if(image.getTag().toString().equals(String.valueOf(randomUtils.getRight_choice_id()))){
                Toast.makeText(this,"Your are right!", Toast.LENGTH_SHORT).show();

                if((count+1)% animals_ID.length!=0){
                    count++;
                    randomUtils.setTestImageBackground(count);
                    animalName.setText(animal_names[randomUtils.getRight_choice_sort()[count]]);
                    animalEngName.setText(animal_eng_name[randomUtils.getRight_choice_sort()[count]]);

                }else{
                    Toast.makeText(this,"if continue?",Toast.LENGTH_SHORT).show();
                }
            }
        }else{
            Toast.makeText(this,"再想想？",Toast.LENGTH_SHORT).show();
        }

    }
}
