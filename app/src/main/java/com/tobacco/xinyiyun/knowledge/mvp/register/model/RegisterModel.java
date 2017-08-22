package com.tobacco.xinyiyun.knowledge.mvp.register.model;

import com.tobacco.xinyiyun.knowledge.net.ApiService;
import com.tobacco.xinyiyun.knowledge.net.entity.Response;

import rx.Observable;

/**
 * Created by YangQiang on 2017/8/22.
 */

public class RegisterModel {
    public Observable<Response> register(String phone, String pwd) {
        return ApiService.get().register(phone, pwd);
    }
}
