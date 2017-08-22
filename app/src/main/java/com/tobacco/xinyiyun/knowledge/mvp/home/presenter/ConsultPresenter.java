package com.tobacco.xinyiyun.knowledge.mvp.home.presenter;

import android.text.TextUtils;

import com.tobacco.xinyiyun.knowledge.base.BasePresenter;
import com.tobacco.xinyiyun.knowledge.mvp.home.ConsultActivity;
import com.tobacco.xinyiyun.knowledge.mvp.home.entity.Consult;
import com.tobacco.xinyiyun.knowledge.mvp.home.model.ConsultModel;
import com.tobacco.xinyiyun.knowledge.net.ApiSubscribe;
import com.tobacco.xinyiyun.knowledge.transformer.LoadTransformer;
import com.tobacco.xinyiyun.knowledge.util.Utils;

import rx.functions.Func1;

/**
 * Created by YangQiang on 2017/8/19.
 */

public class ConsultPresenter extends BasePresenter<ConsultModel, ConsultActivity> {

    public void getConsultList() {
        getModel().getConsultList()
                .map(new Func1<Consult, Consult>() {
                    @Override
                    public Consult call(Consult consult) {
                        if (consult.success && consult.data != null) {
                            for (Consult.DataEntity dataEntity : consult.data) {
                                if (!TextUtils.isEmpty(dataEntity.qztp)) {
                                    dataEntity.qztps = Utils.trim(dataEntity.qztp.split("#"));
                                }
                            }
                        }
                        return consult;
                    }
                })
                .compose(this.<Consult>appTransformer())
                .compose(LoadTransformer.<Consult>build(getView()))
                .subscribe(new ApiSubscribe<Consult>() {
                    @Override
                    public void onNext(Consult consult) {
                        super.onNext(consult);
                        if (consult.success && consult.data != null && consult.data.size() > 0) {
                            getView().setData(consult.data);
                        } else {
                            getView().setData(null);
                        }
                    }
                });
    }
}
