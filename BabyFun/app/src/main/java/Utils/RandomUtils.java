package Utils;

import android.util.Log;
import android.widget.ImageView;

/**
 * Created by Administrator on 2015/10/21.
 */
public class RandomUtils {


    private int[] imageResourceID;   //资源ID数组

    private int right_choice_id;  //正确的图片ID;

    private ImageView[] imageViews;   //
    private ImageView learnImageView;

    private int choice_count = 0;    //选择的次数

    private int[] right_choice_sort;   //随机生成的数组,重新排列正确图片ID顺序

    public RandomUtils(int[] imageResourceID,ImageView learnImageView){
        this.imageResourceID = imageResourceID;
        this.learnImageView = learnImageView;
        right_choice_sort = getRandomSort(imageResourceID.length);
    }


    public RandomUtils(int[] imageResourceID,ImageView[] imageViews){
        this.imageResourceID = imageResourceID;
        this.imageViews = imageViews;
        right_choice_sort = getRandomSort(imageResourceID.length);
    }

    public int getRight_choice_id() {
        return right_choice_id;
    }

    public int[] getRight_choice_sort() {
        return right_choice_sort;
    }

    public int getChoice_count() {
        return choice_count;
    }

    public void  setChoice_count(int choice_count) {
        this.choice_count = choice_count;
    }

    /**
     * 得到随机排列的数组
     * @return
     */
    public static int[] getRandomSort(int size){
        int sorts[] = new int[size];
        java.util.Random random = new java.util.Random();
        sorts[0] = random.nextInt(size);
        for(int i=1;i<size;i++){
            sorts[i]=random.nextInt(size);
            for(int j=0;j<i;j++){
                while(sorts[i]==sorts[j]&&i!=j){
                    i--;
                }
            }

            Log.d("tomato", "sorts[i]:" + sorts[i]);
        }

        return sorts;
    }


    /**
     * 设置显示ImageView的资源ID
     * @param count
     */
    public void setTestImageBackground(int count){

        right_choice_id = imageResourceID[right_choice_sort[count]];

        //生成随机数组，重新排列所有图片ID
        int[] sorts = getRandomSort(imageResourceID.length);


        for(int k=0;k<sorts.length;k++){
            imageViews[k].setTag(null);
            imageViews[k].setImageResource(imageResourceID[sorts[k]]);

            if(imageResourceID[sorts[k]]==right_choice_id){
                imageViews[k].setTag(right_choice_id);
            }
        }

    }

    public void setLearnImageBackground(int count){
        learnImageView.setImageResource(imageResourceID[right_choice_sort[count%imageResourceID.length]]);
    }

}
