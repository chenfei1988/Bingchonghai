package com.tobacco.xinyiyun.knowledge.mvp.register.presenter;

import com.tobacco.xinyiyun.knowledge.base.BasePresenter;
import com.tobacco.xinyiyun.knowledge.mvp.login.entity.User;
import com.tobacco.xinyiyun.knowledge.mvp.login.model.LoginModel;
import com.tobacco.xinyiyun.knowledge.mvp.register.RegisterActivity;
import com.tobacco.xinyiyun.knowledge.mvp.register.model.RegisterModel;
import com.tobacco.xinyiyun.knowledge.net.ApiSubscribe;
import com.tobacco.xinyiyun.knowledge.net.entity.Response;

/**
 * Created by YangQiang on 2017/8/22.
 */

public class RegisterPresenter extends BasePresenter<RegisterModel, RegisterActivity> {

    public void regiseter(String phone, String pwd) {
        getModel().register(phone, pwd)
                .compose(this.<Response>appTransformer())
                .subscribe(new ApiSubscribe<Response>() {
                    @Override
                    public void onNext(Response response) {
                        super.onNext(response);
                        getView().setData(response);
                    }
                });
    }

    public void autoLogin(String phone, String pwd) {
        new LoginModel().login(phone, pwd).compose(this.<User>appTransformer())
                .subscribe(new ApiSubscribe<User>() {
                    @Override
                    public void onNext(User user) {
                        super.onNext(user);
                        getView().loginData(user);
                    }
                });
    }
}
