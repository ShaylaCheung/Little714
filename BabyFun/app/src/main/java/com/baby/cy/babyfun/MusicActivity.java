package com.baby.cy.babyfun;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MusicActivity extends Activity {

    private ViewPager viewpager;
    private View[] contentViews;
    private RelativeLayout container;

    private static final int TOTAL_COUNT = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music);

        viewpager = (ViewPager)findViewById(R.id.viewpager);
        container = (RelativeLayout)findViewById(R.id.container);
        initMusic();
    }



    public void initMusic(){
        contentViews = new View[TOTAL_COUNT];

        // 1.设置幕后item的缓存数目
        viewpager.setOffscreenPageLimit(TOTAL_COUNT);
        // 2.设置页与页之间的间距
        viewpager.setPageMargin(20);
        // 3.将父类的touch事件分发至viewPgaer，否则只能滑动中间的一个view对象
        container.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return viewpager.dispatchTouchEvent(event);
            }
        });


        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            boolean isScrolled = false;

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO 自动生成的方法存根
                if (container != null) {
                    container.invalidate();
                }
            }

            @Override
            public void onPageScrollStateChanged(int status) {
                // TODO 自动生成的方法存根
                switch (status)
                {
                    case 1:// 手势滑动
                        isScrolled = false;
                        break;
                    case 2:// 界面切换
                        isScrolled = true;
                        break;
                    case 0:// 滑动结束

                        // 当前为最后一张，此时从右向左滑，则切换到第一张
                        if (viewpager.getCurrentItem() == viewpager.getAdapter().getCount() - 1 && !isScrolled) {
                            viewpager.setCurrentItem(0);
                        }
                        // 当前为第一张，此时从左向右滑，则切换到最后一张
                        else if (viewpager.getCurrentItem() == 0 && !isScrolled) {
                            viewpager.setCurrentItem(viewpager.getAdapter().getCount() - 1);
                        }
                        break;
                }
            }
        });

        viewpager.setAdapter(new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {

                return TOTAL_COUNT;
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
//                ((ViewPager)container).removeView(contentViews[position]);
            }

            @Override
            public Object instantiateItem(View container, int position) {
//                ((ViewPager)container).addView(contentViews[position]);
                ImageView pager = new ImageView(getApplicationContext());
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(80,60);
                pager.setLayoutParams(layoutParams);
                pager.setBackgroundResource(R.mipmap.animal_demo_1);
                ((ViewPager)container).addView(pager,position);
                return pager;
            }

        });

    }

}
