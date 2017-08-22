package com.tobacco.xinyiyun.knowledge.mvp.main.presenter;

import com.tobacco.xinyiyun.knowledge.base.BaseApplication;
import com.tobacco.xinyiyun.knowledge.base.BasePresenter;
import com.tobacco.xinyiyun.knowledge.entity.Collect;
import com.tobacco.xinyiyun.knowledge.mvp.login.entity.User;
import com.tobacco.xinyiyun.knowledge.mvp.login.model.LoginModel;
import com.tobacco.xinyiyun.knowledge.mvp.main.fragment.MineFragment;
import com.tobacco.xinyiyun.knowledge.net.ApiSubscribe;

/**
 * Created by YangQiang on 2017/8/21.
 */

public class MinePresenter extends BasePresenter<LoginModel, MineFragment> {

    public void refreshUser() {
        User mUser = BaseApplication.getUser();
        if (mUser != null) {
            getModel().login(mUser.name, mUser.password)
                    .compose(this.<User>appTransformer())
                    .subscribe(new ApiSubscribe<User>() {
                        @Override
                        public void onNext(User user) {
                            super.onNext(user);
                        }
                    });
        }
    }

    public void getCollect() {
        BaseApplication.getDBHelper().getRuntimeExceptionDao(Collect.class);
    }
}
