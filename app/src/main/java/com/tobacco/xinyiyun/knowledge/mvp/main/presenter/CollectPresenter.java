package com.tobacco.xinyiyun.knowledge.mvp.main.presenter;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.tobacco.xinyiyun.knowledge.base.BaseApplication;
import com.tobacco.xinyiyun.knowledge.base.BasePresenter;
import com.tobacco.xinyiyun.knowledge.entity.Collect;
import com.tobacco.xinyiyun.knowledge.mvp.login.entity.User;
import com.tobacco.xinyiyun.knowledge.mvp.main.CollectActivity;
import com.tobacco.xinyiyun.knowledge.net.ApiSubscribe;
import com.tobacco.xinyiyun.knowledge.transformer.LoadTransformer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by YangQiang on 2017/8/22.
 */

public class CollectPresenter extends BasePresenter<Object, CollectActivity> {

    public void getCollectList() {
        Observable.create(new Observable.OnSubscribe<List<Collect>>() {
            @Override
            public void call(Subscriber<? super List<Collect>> subscriber) {
                RuntimeExceptionDao<Collect, Integer> mCollectDao = BaseApplication.getDBHelper().getRuntimeExceptionDao(Collect.class);
                User mUser = BaseApplication.getUser();
                if (mUser == null) {
                    subscriber.onNext(new ArrayList<Collect>());
                } else {
                    try {
                        subscriber.onNext(mCollectDao.queryBuilder().where().eq("userId", mUser.user.id).query());
                    } catch (SQLException e) {
                        subscriber.onError(e);
                    }
                }
                subscriber.onCompleted();

            }
        }).compose(this.<List<Collect>>appTransformer())
                .compose(LoadTransformer.<List<Collect>>build(getView()))
                .subscribe(new ApiSubscribe<List<Collect>>() {
                    @Override
                    public void onNext(List<Collect> collect) {
                        super.onNext(collect);
                        if (collect.size() > 0) {
                            getView().setData(collect);
                        } else {
                            getView().setData(null);
                        }
                    }
                });

    }

    public void removeCollect(Collect collect) {
        RuntimeExceptionDao<Collect, Integer> mCollectDao = BaseApplication.getDBHelper().getRuntimeExceptionDao(Collect.class);
        mCollectDao.delete(collect);
        getCollectList();
    }
}
