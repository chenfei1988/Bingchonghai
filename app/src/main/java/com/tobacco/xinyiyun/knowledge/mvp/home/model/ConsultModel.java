package com.tobacco.xinyiyun.knowledge.mvp.home.model;

import com.google.gson.Gson;
import com.tobacco.xinyiyun.knowledge.base.BaseApplication;
import com.tobacco.xinyiyun.knowledge.mvp.home.entity.Consult;
import com.tobacco.xinyiyun.knowledge.mvp.home.entity.Reply;
import com.tobacco.xinyiyun.knowledge.mvp.login.entity.User;
import com.tobacco.xinyiyun.knowledge.net.ApiService;
import com.tobacco.xinyiyun.knowledge.net.entity.Response;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by YangQiang on 2017/8/19.
 */

public class ConsultModel {

    public Observable<Consult> getConsultList() {
        Map<String, Object> mMap = new HashMap<>();
        User mUser = BaseApplication.getUser();
        if (mUser != null) {
            mMap.put("userid", mUser.user.id);
        }
        return ApiService.get().getConsultList(mMap);
    }


    public Observable<Response> commitQuestion(String questoin, String type, List<File> imgs) {
        Map<String, Object> mMap = new HashMap<>();
        User mUser = BaseApplication.getUser();
        if (mUser != null) {
            mMap.put("userid", mUser.user.id);
        }
        mMap.put("qznr", questoin);
        mMap.put("qzgl", type);

        RequestBody mBody;
        if (imgs.size() > 0) {
            MultipartBody.Builder multipartBodybuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            for (File img : imgs) {
                RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), img);
                multipartBodybuilder.addFormDataPart("xczp", img.getName(), fileBody);
            }
            multipartBodybuilder.addFormDataPart("params", new Gson().toJson(mMap));
            mBody = multipartBodybuilder.build();
        } else {
            FormBody.Builder bodyBuilder = new FormBody.Builder();
            bodyBuilder.add("params", new Gson().toJson(mMap));
            mBody = bodyBuilder.build();
        }
        return ApiService.get().commitQuestion(mBody);
    }


    public Observable<Reply> getReplyList(String ywid) {
        Map<String, Object> mMap = new HashMap<>();
        User mUser = BaseApplication.getUser();
        if (mUser != null) {
            mMap.put("userid", mUser.user.id);
        }
        mMap.put("ywid", ywid);
        return ApiService.get().getReplyList(mMap);
    }


    public Observable<Response> replyQuestion(String ywid, String context) {
        Map<String, Object> mMap = new HashMap<>();
        mMap.put("ywid", ywid);
        User mUser = BaseApplication.getUser();
        if (mUser != null) {
            mMap.put("userid", mUser.user.id);
        }

        mMap.put("qzhfnr", context);
        mMap.put("qhfrid", "0");
        return ApiService.get().replyQuestion(new Gson().toJson(mMap));
    }

    public Observable<Response> removeQuestion(String ywid) {
        return ApiService.get().removeQuestion(ywid);
    }
}
