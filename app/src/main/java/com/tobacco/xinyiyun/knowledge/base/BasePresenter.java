package com.tobacco.xinyiyun.knowledge.base;


import android.content.Context;
import android.text.TextUtils;

import com.tobacco.xinyiyun.knowledge.R;
import com.tobacco.xinyiyun.knowledge.net.entity.Response;
import com.tobacco.xinyiyun.knowledge.util.RxUtils;
import com.tobacco.xinyiyun.knowledge.util.ToastUtils;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * User: yq
 * Date: 2016-06-23
 * Time: 10:35
 */
public abstract class BasePresenter<M, V> {

    protected M mModel;
    protected V mView;
    protected Observable.Transformer mLifecycleTransformer;
    protected Context mContext;
    protected boolean mShowErrorMessage = true;
    protected boolean mShowSuccessMessage = false;

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    protected M getModel() {
        return this.mModel;
    }

    protected void setModel(M model) {
        this.mModel = model;
    }

    protected V getView() {
        return this.mView;
    }


    protected void setView(V view) {
        this.mView = view;
    }

    public <T> Observable.Transformer<T, T> getLifecycleTransformer() {
        return mLifecycleTransformer;
    }

    public void setLifecycleTransformer(Observable.Transformer mLifecycleTransformer) {
        this.mLifecycleTransformer = mLifecycleTransformer;
    }

    public <T> Observable.Transformer<T, T> appTransformer() {
        return (Observable.Transformer<T, T>) createTransformer();
    }


    public <T> Observable.Transformer<T, T> appTransformer(boolean showErrorMsg, boolean showSuccessMsg) {
        this.mShowErrorMessage = showErrorMsg;
        this.mShowSuccessMessage = showSuccessMsg;
        return (Observable.Transformer<T, T>) createTransformer();
    }


    private <T> Observable.Transformer<T, T> createTransformer() {

        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(final Observable<T> o) {
                final Observable<T> mObservable = o.compose((Observable.Transformer<T, T>) RxUtils.androidTransformer())
                        .compose((Observable.Transformer<T, T>) getLifecycleTransformer())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(new Action1<T>() {
                            @Override
                            public void call(T o) {
                                if (o instanceof Response) {
                                    Response mResponse = (Response) o;
                                    if (!TextUtils.isEmpty(mResponse.message)) {
                                        if (!mResponse.success && mShowErrorMessage) {
                                            ToastUtils.show(getContext(), mResponse.message);
                                        }
                                        if (mResponse.success && mShowSuccessMessage) {
                                            ToastUtils.show(getContext(), mResponse.message);
                                        }
                                    }
                                }
                            }
                        })
                        .doOnError(new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                if (mShowErrorMessage) {
                                    ToastUtils.show(getContext(), R.string.network_error);
                                }
                            }
                        });

                return Observable.timer(500, TimeUnit.MILLISECONDS)
                        .flatMap(new Func1<Long, Observable<T>>() {
                            @Override
                            public Observable<T> call(Long aLong) {
                                return mObservable;
                            }
                        });
            }
        };
    }

}
