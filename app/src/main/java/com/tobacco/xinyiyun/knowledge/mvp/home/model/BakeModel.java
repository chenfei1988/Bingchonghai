package com.tobacco.xinyiyun.knowledge.mvp.home.model;

import android.text.TextUtils;

import com.tobacco.xinyiyun.knowledge.base.BaseApplication;
import com.tobacco.xinyiyun.knowledge.mvp.home.entity.Bake;
import com.tobacco.xinyiyun.knowledge.mvp.home.entity.BakeDetail;
import com.tobacco.xinyiyun.knowledge.mvp.login.entity.User;
import com.tobacco.xinyiyun.knowledge.net.ApiService;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by YangQiang on 2017/8/18.
 */

public class BakeModel {

    public Observable<Bake> getBakeList(String wzlx, String yypz, String yybw, String context) {
        Map<String, Object> mMap = new HashMap<>();
        mMap.put("wzlx", wzlx);
        if (!TextUtils.isEmpty(yypz)) {
            mMap.put("yypz", yypz);
        }
        if (!TextUtils.isEmpty(yybw)) {
            mMap.put("yybw", yybw);
        }
        if (!TextUtils.isEmpty(context)) {
            mMap.put("context", context);
        }
        User mUser = BaseApplication.getUser();
        if (mUser != null) {
            mMap.put("userid", mUser.user.id);
        }
        return ApiService.get().getBakeList(mMap);
    }

    public Observable<Bake> getBakeList(String wzlx, String yypz, String yybw, String context, String hkkf, String hkhsl) {
        Map<String, Object> mMap = new HashMap<>();
        mMap.put("wzlx", wzlx);
        if (!TextUtils.isEmpty(yypz)) {
            mMap.put("yypz", yypz);
        }
        if (!TextUtils.isEmpty(yybw)) {
            mMap.put("yybw", yybw);
        }
        if (!TextUtils.isEmpty(context)) {
            mMap.put("context", context);
        }
        if (!TextUtils.isEmpty(hkkf)) {
            mMap.put("hkkf", hkkf);
        }
        if (!TextUtils.isEmpty(hkhsl)) {
            mMap.put("hkhsl", hkhsl);
        }
        User mUser = BaseApplication.getUser();
        if (mUser != null) {
            mMap.put("userid", mUser.user.id);
        }
        return ApiService.get().getBakeList(mMap);
    }

    public Observable<BakeDetail> getBakeDetail(String wzlx, String ywid) {
        return ApiService.get().getBakeDetail(wzlx, ywid);
    }
}
