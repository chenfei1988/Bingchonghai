package com.tobacco.xinyiyun.knowledge.mvp.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.tobacco.xinyiyun.knowledge.R;
import com.tobacco.xinyiyun.knowledge.annocation.LayoutInject;
import com.tobacco.xinyiyun.knowledge.annocation.ToolbarInject;
import com.tobacco.xinyiyun.knowledge.base.BaseActivity;
import com.tobacco.xinyiyun.knowledge.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

@LayoutInject(R.layout.activity_knowledge)
@ToolbarInject(showBack = true, menuId = R.menu.menu_knowledge_top)
public class TabActivity extends BaseActivity {

    public static final String EXTRA_STRING_TITLE = "extra_string_title";
    private static final String EXTRA_INT_ARRAY_ID = "extra_int_array_id";
    @Bind(R.id.vp_types)
    ViewPager mVpTypes;
    PageAdapter mPageAdapter;
    @Bind(R.id.tbs_titles)
    TabLayout mTbsTitles;
    List<BaseFragment> mListFragment = new ArrayList<>();
    List<Class> mListSearchClass = new ArrayList<>();
    int mArrayId;

    public static void start(Context context, int arrayid, String title) {
        Intent mIntnet = new Intent(context, TabActivity.class);
        mIntnet.putExtra(EXTRA_INT_ARRAY_ID, arrayid);
        mIntnet.putExtra(EXTRA_STRING_TITLE, title);
        context.startActivity(mIntnet);
    }

    @Override
    public void init() {
        mArrayId = getIntent().getIntExtra(EXTRA_INT_ARRAY_ID, 0);
        setTitle(getIntent().getStringExtra(EXTRA_STRING_TITLE));
        mPageAdapter = new PageAdapter(getSupportFragmentManager());

        String[] mArrays = getResources().getStringArray(mArrayId);
        for (String val : mArrays) {
            String[] vals = val.split("#");
            Bundle mBundle = new Bundle();
            mBundle.putString(BaseFragment.EXTRA_STRING_TITLE, vals[0]);
            String[] mArgs = vals[3].split(",");

            for (String mArg : mArgs) {
                String[] mVa = mArg.split("=");
                mBundle.putString(mVa[0], mVa[1]);
            }

            try {
                BaseFragment mFragment = (BaseFragment) Class.forName(vals[1]).newInstance();
                mFragment.setBundle(mBundle);
                mListFragment.add(mFragment);

                mListSearchClass.add(Class.forName(vals[2]));
            } catch (Exception e) {
                Logger.e(e.getLocalizedMessage());
            }
        }
        mVpTypes.setAdapter(mPageAdapter);
        mVpTypes.setOffscreenPageLimit(mListFragment.size());
        mTbsTitles.setupWithViewPager(mVpTypes);
        mTbsTitles.setVisibility(mListFragment.size() <= 1 ? View.GONE : View.VISIBLE);
    }

    private class PageAdapter extends FragmentStatePagerAdapter {

        public PageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mListFragment.get(position);
        }

        @Override
        public int getCount() {
            return mListFragment.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mListFragment.get(position).title;
        }
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.menu_search) {

            Intent mIntent = new Intent(TabActivity.this, mListSearchClass.get(mVpTypes.getCurrentItem()));
            Bundle mBundle = mListFragment.get(mVpTypes.getCurrentItem()).getArguments();
            for (String key : mBundle.keySet()) {
                mIntent.putExtra(key, mBundle.getString(key));
            }
            startActivity(mIntent);
        }
        return super.onMenuItemClick(item);
    }


}
