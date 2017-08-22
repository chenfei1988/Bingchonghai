package com.tobacco.xinyiyun.knowledge.net;

import com.orhanobut.logger.Logger;

import rx.Subscriber;

/**
 * Created by YangQiang on 16-11-21.
 */

public abstract class ApiSubscribe<T> extends Subscriber<T> {

    @Override
    public void onError(Throwable e) {
        Logger.e(e, "");
        e.printStackTrace();
    }

    @Override
    public void onCompleted() {
    }


    @Override
    public void onNext(T t) {

    }
}
