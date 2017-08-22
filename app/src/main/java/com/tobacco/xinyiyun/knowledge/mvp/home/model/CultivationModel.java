package com.tobacco.xinyiyun.knowledge.mvp.home.model;

import android.text.TextUtils;

import com.tobacco.xinyiyun.knowledge.base.BaseApplication;
import com.tobacco.xinyiyun.knowledge.mvp.home.entity.Cultivation;
import com.tobacco.xinyiyun.knowledge.mvp.login.entity.User;
import com.tobacco.xinyiyun.knowledge.net.ApiService;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by YangQiang on 2017/8/17.
 */

public class CultivationModel {

    public Observable<Cultivation> getCultivationList(String wzlx, String context) {
        Map<String, Object> mMap = new HashMap<>();
        if (!TextUtils.isEmpty(wzlx)) {
            mMap.put("wzlx", wzlx);
        }
        if (!TextUtils.isEmpty(context)) {
            mMap.put("context", context);
        }
        User mUser = BaseApplication.getUser();
        if (mUser != null) {
            mMap.put("userid", mUser.user.id);
        }
        return ApiService.get().getCultivationList(mMap);
    }
}
