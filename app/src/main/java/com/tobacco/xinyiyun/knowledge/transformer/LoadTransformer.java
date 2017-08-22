package com.tobacco.xinyiyun.knowledge.transformer;

import android.view.View;

import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tobacco.xinyiyun.knowledge.R;
import com.tobacco.xinyiyun.knowledge.base.BaseActivity;
import com.tobacco.xinyiyun.knowledge.base.BaseFragment;
import com.tobacco.xinyiyun.knowledge.view.MultiStateView;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.PublishSubject;


/**
 * User: yq
 * Date: 2016-10-20
 * Time: 14:49
 */
public class LoadTransformer<T> implements Observable.Transformer<T, T> {
    private Object mTarget;
    private PublishSubject mSubject = PublishSubject.create();
    private MultiStateView mMultiStateView = null;
    private SmartRefreshLayout mSmartRefreshLayout = null;

    public static <T> LoadTransformer<T> build(Object target) {
        return new LoadTransformer(target);
    }

    public LoadTransformer(Object target) {
        this.mTarget = target;

        if (mTarget instanceof BaseActivity) {
            BaseActivity mAcvitity = ((BaseActivity) mTarget);
            mSmartRefreshLayout = mAcvitity.getRefresh();

        } else if (mTarget instanceof BaseFragment) {
            BaseFragment mFragment = ((BaseFragment) mTarget);
            mSmartRefreshLayout = mFragment.getRefresh();
        }

        if (mTarget instanceof BaseActivity) {
            BaseActivity mAcvitity = ((BaseActivity) mTarget);
            mMultiStateView = mAcvitity.getMultiState();

        } else if (mTarget instanceof BaseFragment) {
            BaseFragment mFragment = ((BaseFragment) mTarget);
            mMultiStateView = mFragment.getMutilState();
        }

        Logger.d((mMultiStateView == null) + "--" + (mSmartRefreshLayout == null));
        if (mMultiStateView != null) {
            mMultiStateView.findViewById(R.id.tx_retry).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSubject.onNext(true);
                }
            });
        }


    }


    @Override
    public Observable<T> call(Observable<T> tObservable) {

        return tObservable
                .observeOn(AndroidSchedulers.mainThread())
                .doOnRequest(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {

                        onLoading();
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e(throwable, "");
                        onError();
                    }
                })
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                    }
                })
                .doOnNext(new Action1<T>() {
                    @Override
                    public void call(T t) {
                        if (t != null) {
                            onComplate();
                        }
                    }
                })
                .retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
                    @Override
                    public Observable<?> call(Observable<? extends Throwable> observable) {
                        return observable.observeOn(AndroidSchedulers.mainThread())
                                .flatMap(new Func1<Throwable, Observable<?>>() {
                                    @Override
                                    public Observable<?> call(Throwable throwable) {
                                        Logger.e(throwable, "");
                                        onError();
                                        return mSubject;
                                    }
                                });
                    }
                });
    }


    private void onLoading() {
        if (mMultiStateView != null && (mSmartRefreshLayout == null || (!mSmartRefreshLayout.isRefreshing() && !mSmartRefreshLayout.isLoading()))) {
            if (mSmartRefreshLayout == null) {
                mMultiStateView.setHidenContent(false);
            }
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
        }
    }

    private void onError() {
        if (mMultiStateView != null && (mSmartRefreshLayout == null || (!mSmartRefreshLayout.isRefreshing() && !mSmartRefreshLayout.isLoading()))) {
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
        } else {
            if (mSmartRefreshLayout != null) {
                if (mSmartRefreshLayout.isRefreshing()) {
                    mSmartRefreshLayout.finishRefresh();
                }
                if (mSmartRefreshLayout.isLoading()) {
                    mSmartRefreshLayout.finishLoadmore();
                }
            }
        }
    }


    private void onComplate() {
        if (mMultiStateView != null && (mSmartRefreshLayout == null || (!mSmartRefreshLayout.isRefreshing() && !mSmartRefreshLayout.isLoading()))) {
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        } else {
            if (mSmartRefreshLayout != null) {
                if (mSmartRefreshLayout.isRefreshing()) {
                    mSmartRefreshLayout.finishRefresh();
                }
                if (mSmartRefreshLayout.isLoading()) {
                    mSmartRefreshLayout.finishLoadmore();
                }
            }
        }
    }

}
