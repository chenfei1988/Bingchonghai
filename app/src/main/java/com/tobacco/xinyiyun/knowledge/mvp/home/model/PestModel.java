package com.tobacco.xinyiyun.knowledge.mvp.home.model;

import android.text.TextUtils;

import com.tobacco.xinyiyun.knowledge.base.BaseApplication;
import com.tobacco.xinyiyun.knowledge.mvp.home.entity.Pest;
import com.tobacco.xinyiyun.knowledge.mvp.home.entity.PestDetail;
import com.tobacco.xinyiyun.knowledge.mvp.login.entity.User;
import com.tobacco.xinyiyun.knowledge.net.ApiService;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by YangQiang on 2017/8/16.
 */

public class PestModel {

    /**
     * 获取病虫害列表
     *
     * @return
     */
    public Observable<Pest> getPestList(String ssszzq, String ssbw, String bchlx, String context) {
        Map<String, Object> mMap = new HashMap<>();
        if (!TextUtils.isEmpty(ssszzq)) {
            mMap.put("ssszzq", ssszzq);
        }
        if (!TextUtils.isEmpty(ssbw)) {
            mMap.put("ssbw", ssbw);
        }
        if (!TextUtils.isEmpty(context)) {
            mMap.put("context", context);
        }
        mMap.put("bchlx", bchlx);
        User mUser = BaseApplication.getUser();
        if (mUser != null) {
            mMap.put("userid", mUser.user.id);
        }
        return ApiService.get().getPestList(mMap);
    }

    /**
     * 查询病虫害详情
     *
     * @param ywid
     * @return
     */
    public Observable<PestDetail> queryPestDetail(String ywid) {
        return ApiService.get().queryPestDetail(ywid);
    }
}
