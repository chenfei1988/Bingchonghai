package com.tobacco.xinyiyun.knowledge.mvp.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.MenuItem;

import com.tobacco.xinyiyun.knowledge.R;
import com.tobacco.xinyiyun.knowledge.annocation.LayoutInject;
import com.tobacco.xinyiyun.knowledge.base.BaseActivity;
import com.tobacco.xinyiyun.knowledge.mvp.main.fragment.HomeFragment;
import com.tobacco.xinyiyun.knowledge.mvp.main.fragment.MineFragment;
import com.tobacco.xinyiyun.knowledge.mvp.main.fragment.RecommendFragment;
import com.tobacco.xinyiyun.knowledge.view.NoScollViewPager;

import butterknife.Bind;

@LayoutInject(value = R.layout.activity_main)
public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    public static final String EXTRA_BOOLEAN_AUTO_LOGIN = "extra_boolean_auto_login";

    Fragment[] mFragments;
    @Bind(R.id.vp_home)
    NoScollViewPager mVpHome;
    @Bind(R.id.bnv_menus)
    BottomNavigationView mBnvMenus;

    @Override
    public void init() {
        MineFragment mMineFragment = new MineFragment();
        Bundle mBundle = new Bundle();
        mBundle.putBoolean(EXTRA_BOOLEAN_AUTO_LOGIN, getIntent().getBooleanExtra(EXTRA_BOOLEAN_AUTO_LOGIN, false));
        mMineFragment.setArguments(mBundle);


        mFragments = new Fragment[]{new HomeFragment(), new RecommendFragment(), mMineFragment};
        mBnvMenus.setOnNavigationItemSelectedListener(this);
        mVpHome.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        mVpHome.setOffscreenPageLimit(3);

    }

    public static void start(Context context, Boolean autoLogin) {
        Intent mIntent = new Intent(context, MainActivity.class);
        mIntent.putExtra(EXTRA_BOOLEAN_AUTO_LOGIN, autoLogin);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(mIntent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home:
                mVpHome.setCurrentItem(0, false);
                break;
            case R.id.menu_recommend:
                mVpHome.setCurrentItem(1, false);
                break;
            case R.id.menu_mine:
                mVpHome.setCurrentItem(2, false);
                break;
        }
        return true;
    }


    private class PagerAdapter extends FragmentPagerAdapter {


        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments[position];
        }

        @Override
        public int getCount() {
            return mFragments.length;
        }
    }

}
