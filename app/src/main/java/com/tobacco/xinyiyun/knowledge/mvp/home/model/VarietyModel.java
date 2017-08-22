package com.tobacco.xinyiyun.knowledge.mvp.home.model;

import android.text.TextUtils;

import com.tobacco.xinyiyun.knowledge.base.BaseApplication;
import com.tobacco.xinyiyun.knowledge.mvp.home.entity.Variety;
import com.tobacco.xinyiyun.knowledge.mvp.home.entity.VarietyDetail;
import com.tobacco.xinyiyun.knowledge.mvp.login.entity.User;
import com.tobacco.xinyiyun.knowledge.net.ApiService;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by YangQiang on 2017/8/18.
 */

public class VarietyModel {
    public Observable<Variety> getVarietyList(String context) {
        Map<String, Object> mMap = new HashMap<>();
        if (!TextUtils.isEmpty(context)) {
            mMap.put("context", context);
        }
        User mUser = BaseApplication.getUser();
        if (mUser != null) {
            mMap.put("userid", mUser.user.id);
        }
        return ApiService.get().getVarietyList(mMap);
    }

    public Observable<VarietyDetail> queyrVarietyDetail(String ywid) {
        return ApiService.get().queryVarietyDetail(ywid);
    }
}
