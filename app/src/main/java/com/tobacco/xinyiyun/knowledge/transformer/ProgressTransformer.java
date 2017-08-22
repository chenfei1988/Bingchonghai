package com.tobacco.xinyiyun.knowledge.transformer;

import android.content.Context;
import android.content.DialogInterface;

import com.afollestad.materialdialogs.MaterialDialog;
import com.tobacco.xinyiyun.knowledge.R;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

/**
 * User: yq
 * Date: 2016-10-20
 * Time: 14:49
 */
public class ProgressTransformer<T> implements Observable.Transformer<T, T> {
    Subject<Boolean, Boolean> mSubject = PublishSubject.create();
    private Context mContext;
    private MaterialDialog.Builder mBuilder;
    private MaterialDialog mMaterialDialog;

    public static <T> ProgressTransformer<T> build(Context context) {
        return new ProgressTransformer(context);
    }

    public ProgressTransformer(Context ct) {
        init(ct, false);
    }


    public ProgressTransformer(Context ct, boolean cancellable) {
        init(ct, cancellable);
    }

    private void init(Context ct, boolean cancellable) {
        if (mBuilder == null) {
            this.mContext = ct;

            mBuilder = new MaterialDialog.Builder(ct)
                    .content(R.string.please_wait)
                    .progress(true, 0);
            mBuilder.cancelable(cancellable);
            mBuilder.canceledOnTouchOutside(cancellable);
            mBuilder.cancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    mSubject.onNext(true);
                }
            });
            mMaterialDialog = mBuilder.build();
        }
    }

    @Override
    public Observable<T> call(Observable<T> tObservable) {

        return tObservable
                .takeUntil(mSubject.takeFirst(new Func1<Boolean, Boolean>() {
                    @Override
                    public Boolean call(Boolean aBoolean) {
                        return aBoolean;
                    }
                }))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnRequest(
                        new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                mMaterialDialog.show();
                            }
                        })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mMaterialDialog.dismiss();
                    }
                })
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        mMaterialDialog.dismiss();
                    }
                })
                .doOnNext(new Action1<T>() {
                    @Override
                    public void call(T t) {
                        mMaterialDialog.dismiss();
                    }
                });
    }
}
