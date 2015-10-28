package com.baby.cy.babyfun.Music;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.baby.cy.babyfun.R;

import java.util.ArrayList;
import java.util.List;

import UserFragment.Login_frg;
import UserFragment.SignIn_frg;
import butterknife.Bind;
import butterknife.ButterKnife;

public class UserActivity extends AppCompatActivity {

    @Bind(R.id.login_tab_layout) TabLayout tabLayout;

    @Bind(R.id.login_view_pager) ViewPager viewPager;

    @Bind(R.id.toolbar)Toolbar toolbar;

    private boolean isLogin;

    private List<String> nameList;

    private List<Fragment> loginFragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_layout);
        ButterKnife.bind(this);
        initView();

        Intent intent = getIntent();
        isLogin = intent.getBooleanExtra("isLogin",false);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserActivity.this.finish();
            }
        });

    }


    public void initView() {

        nameList = new ArrayList<String>();

        //tabLayout的标签名称
        nameList.add("登陆");
        nameList.add("注册");

        //Fragment
        loginFragmentList = new ArrayList<Fragment>();
        loginFragmentList.add(new Login_frg());
        loginFragmentList.add(new SignIn_frg());

        viewPager.setAdapter(new LoginPagerAdapter(getSupportFragmentManager(), loginFragmentList));
        viewPager.setOnPageChangeListener(new ViewPagerListener());
        tabLayout.setupWithViewPager(viewPager);

    }


    public class LoginPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList;

        @Override
        public CharSequence getPageTitle(int position) {
            return nameList.get(position);
        }

        public LoginPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

    class ViewPagerListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int arg0) {

        }


        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int index) {
            viewPager.setCurrentItem(index);
        }
    }



}
