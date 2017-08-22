package com.tobacco.xinyiyun.knowledge.util;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RxUtils {
    public static <T> Observable.Transformer<T, T> androidTransformer() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.subscribeOn(Schedulers.io())
                                  .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
