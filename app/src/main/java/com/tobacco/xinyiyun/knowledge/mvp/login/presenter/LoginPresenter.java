package com.tobacco.xinyiyun.knowledge.mvp.login.presenter;

import com.tobacco.xinyiyun.knowledge.base.BasePresenter;
import com.tobacco.xinyiyun.knowledge.mvp.login.entity.User;
import com.tobacco.xinyiyun.knowledge.mvp.login.LoginActivity;
import com.tobacco.xinyiyun.knowledge.mvp.login.model.LoginModel;
import com.tobacco.xinyiyun.knowledge.net.ApiSubscribe;

/**
 * Created by YangQiang on 2017/8/16.
 */

public class LoginPresenter extends BasePresenter<LoginModel, LoginActivity> {


    /**
     * 登录
     *
     * @param name
     * @param password
     */
    public void login(String name, String password) {
        getModel().login(name, password)
                .compose(this.<User>appTransformer())
                .subscribe(new ApiSubscribe<User>() {
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        getView().refreshViewState();
                    }

                    @Override
                    public void onNext(User user) {
                        super.onNext(user);
                        if (user.success) {
                            getView().goHomeActivity();
                        } else {
                            getView().refreshViewState();
                        }
                    }
                });
    }
}
