package com.tobacco.xinyiyun.knowledge.mvp.login.model;

import android.text.TextUtils;

import com.tobacco.xinyiyun.knowledge.R;
import com.tobacco.xinyiyun.knowledge.base.BaseApplication;
import com.tobacco.xinyiyun.knowledge.mvp.login.entity.User;
import com.tobacco.xinyiyun.knowledge.net.ApiService;
import com.tobacco.xinyiyun.knowledge.util.SPreferencesUtils;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by YangQiang on 2017/8/16.
 */

public class LoginModel {


    /**
     * 登录
     *
     * @param name
     * @param password
     */
    public Observable<User> login(final String name, final String password) {
        return ApiService.get().login(name, password).map(new Func1<User, User>() {
            @Override
            public User call(User user) {
                if (user.success) {
                    user.name = name;
                    user.password = password;
                    SPreferencesUtils.putObject(BaseApplication.getInstance(), BaseApplication.getInstance().getString(R.string.pref_key_object_user), user);
                    if (!TextUtils.isEmpty(user.baseurl)) {
                        ApiService.setBaseUrl(user.baseurl);
                    }
                }
                return user;
            }
        });
    }
}
