package com.baby.cy.babyfun.Character;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baby.cy.babyfun.R;

import java.util.Random;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import java.util.ArrayList;    
import java.util.Collections;      
import java.util.List; 
import java.util.Iterator;   

public class ABC_Learn_Activity extends Activity {

    private int[] characters = {R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,
            R.drawable.e,R.drawable.f,R.drawable.g,R.drawable.h,
            R.drawable.i,R.drawable.j,R.drawable.k, R.drawable.l,
            R.drawable.m, R.drawable.n,R.drawable.o,R.drawable.p,
            R.drawable.q,R.drawable.r,R.drawable.s,R.drawable.t,
            R.drawable.u,R.drawable.v,R.drawable.w,R.drawable.x,
            R.drawable.y,R.drawable.z};

    private String[] character_names = {"Aa","Bb","Cc","Dd","Ee","Ff","Gg","Hh","Ii","Jj","Kk","Ll",
        "Mm","Nn","Oo","Pp","Qq","Rr","Ss","Tt","Uu","Vv","Ww","Xx","Yy","Zz"};

    private int Right_Character ;  //正确的图片ID
    private int Right_Sort = 0;   //正确图片在数组中的位置
    private int to_character[] = null;

    private ImageView[] image;
    private int count = 0;
    private int[] right_sorts;    //字母的排列顺序

    @Bind(R.id.test_abc_1) ImageView test_image_1;
    @Bind(R.id.test_abc_2) ImageView test_image_2;
    @Bind(R.id.test_abc_3) ImageView test_image_3;
    @Bind(R.id.test_abc_4) ImageView test_image_4;
    @Bind(R.id.test_abc_5) ImageView test_image_5;
    @Bind(R.id.test_abc_6) ImageView test_image_6;
    @Bind(R.id.test_abc_7) ImageView test_image_7;
    @Bind(R.id.test_abc_8) ImageView test_image_8;
//    @Bind(R.id.test_abc_9) ImageView test_image_9;
    // @Bind(R.id.test_abc_10) ImageView test_image_10;
    // @Bind(R.id.test_abc_11) ImageView test_image_11;
    // @Bind(R.id.test_abc_12) ImageView test_image_12;
    // @Bind(R.id.test_abc_13) ImageView test_image_13;
    // @Bind(R.id.test_abc_14) ImageView test_image_14;
    // @Bind(R.id.test_abc_15) ImageView test_image_15;
    // @Bind(R.id.test_abc_16) ImageView test_image_16;
    // @Bind(R.id.test_abc_17) ImageView test_image_17;
    // @Bind(R.id.test_abc_18) ImageView test_image_18;
    // @Bind(R.id.test_abc_19) ImageView test_image_19;
    // @Bind(R.id.test_abc_20) ImageView test_image_20;
    // @Bind(R.id.test_abc_21) ImageView test_image_21;
    // @Bind(R.id.test_abc_22) ImageView test_image_22;
    // @Bind(R.id.test_abc_23) ImageView test_image_23;
    // @Bind(R.id.test_abc_24) ImageView test_image_24;
    // @Bind(R.id.test_abc_25) ImageView test_image_25;
    // @Bind(R.id.test_abc_26) ImageView test_image_26;

    @Bind(R.id.abc_test_name) TextView characterName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abc_learn_gui);
        ButterKnife.bind(this);
        initABCTestImage();
        setImageBackground(count);
    }

    //生成右下角字母的随机序列
    public int[] getRandomSort(int size){
        int sorts[] = new int[size];
        Random random = new Random();
        sorts[0] = random.nextInt(size);
        for(int i=1;i<size;i++){
            sorts[i]=random.nextInt(size);
            //比较新生成的随机数是否与前面重复
            for(int j=0;j<i;j++){
                while(sorts[i]==sorts[j]&&i!=j){
                    i--;
                }
            }

            Log.d("tomato","sorts[i]:"+sorts[i]);
        }

        return sorts;
    }

    public void initABCTestImage(){
        image=new ImageView[8];
        image[0] = test_image_1;
        image[1] = test_image_2;
        image[2] = test_image_3;
        image[3] = test_image_4;
        image[4] = test_image_5;
        image[5] = test_image_6;
        image[6] = test_image_7;
        image[7] = test_image_8;
//        image[8] = test_image_9;
        // image[9] = test_image_10;
        // image[10] = test_image_11;
        // image[11] = test_image_12;
        // image[12] = test_image_13;
        // image[13] = test_image_14;
        // image[14] = test_image_15;
        // image[15] = test_image_16;
        // image[16] = test_image_17;
        // image[17] = test_image_18;
        // image[18] = test_image_19;
        // image[19] = test_image_20;
        // image[20] = test_image_21;
        // image[21] = test_image_22;
        // image[22] = test_image_23;
        // image[23] = test_image_24;
        // image[24] = test_image_25;
        // image[25] = test_image_26;
        //得到右下角所有字母出现的随机序列
        right_sorts = getRandomSort(character_names.length);
    }

    public void setImageBackground(int count){

    	to_character = new int[8];
    	boolean isNeedOne = true;

    	//Right_Character代表对应右下角显示的字母的ID即R.drawable.
        Right_Character = characters[right_sorts[count]];
        characterName.setText(character_names[right_sorts[count]]);

        Log.d("Tomato",character_names[right_sorts[count]]);

        

        //得到所有字母图片ID的随机序列
        int[] sorts = getRandomSort(characters.length);
        //选取该随机序列的前8个作为选项
        for(int c = 0;c < 8;c++){
        	
        	to_character[c] = sorts[c];
        	if(characters[sorts[c]]==Right_Character){
        		isNeedOne = false;
        	}
        	if(c == 7 && isNeedOne == true){
                to_character[7] = right_sorts[count];
            }
        }

        //将to_character进行乱序，防止正确答案在最后一个大面积集中
        ArrayList<Integer> character_list = new ArrayList<Integer>();
        for (int l = 0;l < to_character.length;l++) {
            character_list.add(to_character[l]);
        }
        Collections.shuffle(character_list);
        int Csize = character_list.size();
        // String[] character_s_array = (String[])character_list.toString().toArray(new String[Csize]);
        Integer[] character_i_array = character_list.toArray(new Integer[Csize]);
        // for(int i=0;i<8;i++){
        //     character_i_array[i] = Integer.parseInt(character_s_array[i]);
        // }
        // Iterator ite = character_list.iterator();
        // int temp = 0;
        // while(ite.hasNext()){
        //     character_i_array[temp] == ite.next();
        //     temp++;
        // }

        //找到正确图片答案的位置
        for(int j= 0;j<character_i_array.length;j++){
            if(characters[character_i_array[j]]==Right_Character){
                Right_Sort = character_i_array[j];
            }
        }

        //每次的左边图片变化。给正确答案设置tag
        for(int k=0;k<character_i_array.length;k++){
            image[k].setImageResource(characters[character_i_array[k]]);
            if(character_i_array[k] == Right_Sort){
                image[k].setTag(Right_Sort);
            }
        }

    }

    @OnClick({R.id.test_abc_1,R.id.test_abc_2,R.id.test_abc_3,R.id.test_abc_4,R.id.test_abc_5,
            R.id.test_abc_6,R.id.test_abc_7,R.id.test_abc_8})
//            ,R.id.test_abc_9})
            // R.id.test_abc_10,R.id.test_anbc_11,R.id.test_abc_12,R.id.test_abc_13,
            // R.id.test_abc_14,R.id.test_abc_15,R.id.test_abc_16,R.id.test_abc_17,
            // R.id.test_abc_18,R.id.test_abc_19,R.id.test_abc_20,R.id.test_abc_21,
            // R.id.test_abc_22,R.id.test_abc_23,R.id.test_abc_24,R.id.test_abc_25,R.id.test_abc_26})

    public void choice_Result(ImageView image){
        if(image.getTag()!=null){
            if(image.getTag().toString().equals(String.valueOf(Right_Sort))){
                Toast.makeText(this,"Your are right!",Toast.LENGTH_SHORT).show();
//                isNextAnimal = true;
                if((count+1)%right_sorts.length!=0){
                    count++;
                    setImageBackground(count);
                }else{
                    Toast.makeText(this,"if continue?",Toast.LENGTH_SHORT).show();
                }
            }
        }else{
            Toast.makeText(this,"再想想？",Toast.LENGTH_SHORT).show();
        }

    }

}
