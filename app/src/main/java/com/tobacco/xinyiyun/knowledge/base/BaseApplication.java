package com.tobacco.xinyiyun.knowledge.base;

import android.app.Application;

import com.orhanobut.logger.Logger;
import com.tobacco.xinyiyun.knowledge.R;
import com.tobacco.xinyiyun.knowledge.db.DBHelper;
import com.tobacco.xinyiyun.knowledge.mvp.login.entity.User;
import com.tobacco.xinyiyun.knowledge.util.SPreferencesUtils;

import java.io.File;

/**
 * Created by YangQiang on 2017/8/6.
 */

public class BaseApplication extends Application {
    public static final String TAG = "Knowledge";
    private static DBHelper mDBHelper;
    private static BaseApplication mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        Logger.init(TAG);
    }



    public static BaseApplication getInstance() {
        return mApp;
    }


    public static DBHelper getDBHelper() {
        if (mDBHelper == null) {
            mDBHelper = new DBHelper(getInstance());
        }
        return mDBHelper;
    }


    public static User getUser() {
        User mUser = SPreferencesUtils.getObject(getInstance(), getInstance().getString(R.string.pref_key_object_user), User.class);
        return mUser;
    }


    public static String getImageCachePath() {
        return getInstance().getCacheDir() + File.separator + "imageCache" + File.separator;
    }
}
