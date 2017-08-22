package com.tobacco.xinyiyun.knowledge.mvp.welcome;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.tobacco.xinyiyun.knowledge.R;
import com.tobacco.xinyiyun.knowledge.annocation.LayoutInject;
import com.tobacco.xinyiyun.knowledge.base.BaseActivity;
import com.tobacco.xinyiyun.knowledge.mvp.login.LoginActivity;
import com.tobacco.xinyiyun.knowledge.mvp.login.entity.User;
import com.tobacco.xinyiyun.knowledge.mvp.main.MainActivity;
import com.tobacco.xinyiyun.knowledge.util.SPreferencesUtils;

import butterknife.Bind;

@LayoutInject(value = R.layout.activity_welcome, translucentStatus = true)
public class WelcomeActivity extends BaseActivity {

    @Bind(R.id.vp_welcome)
    ViewPager mVpWelcome;

    @Override
    public void init() {
        if (SPreferencesUtils.getBoolean(this, getString(R.string.pre_key_boolean_is_first_lanuch), true)) {
            mVpWelcome.setAdapter(new WelcomeAdapter());
        } else {

            boolean isRemember = SPreferencesUtils.getBoolean(this, getString(R.string.pre_key_boolean_remember_the_password), false);
            User mUser = SPreferencesUtils.getObject(this, getString(R.string.pref_key_object_user), User.class);
            //记住密码自动登录
            if (isRemember && mUser != null) {
                MainActivity.start(this, true);
                finish();
            } else {
                goLoginActivity();
            }
        }

    }


    /**
     * 跳转到登录界面
     */
    private void goLoginActivity() {
        startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
        finish();
    }


    private class WelcomeAdapter extends PagerAdapter {

        int[] mImgRes = new int[]{R.mipmap.img_welcome_1, R.mipmap.img_welcome_2, R.mipmap.img_welcome_3};

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View mView = LayoutInflater.from(WelcomeActivity.this).inflate(R.layout.view_welcome_pager_item, container, false);
            ImageView mImg = (ImageView) mView.findViewById(R.id.img_guide);
            Button mBtnExperience = (Button) mView.findViewById(R.id.btn_experience);
            mBtnExperience.setVisibility(position == mImgRes.length - 1 ? View.VISIBLE : View.GONE);
            mBtnExperience.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SPreferencesUtils.putBoolean(WelcomeActivity.this, getString(R.string.pre_key_boolean_is_first_lanuch), false);
                    goLoginActivity();
                }
            });
            mImg.setImageResource(mImgRes[position]);
            container.addView(mView);
            return mView;
        }

        @Override
        public int getCount() {
            return mImgRes.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

}
