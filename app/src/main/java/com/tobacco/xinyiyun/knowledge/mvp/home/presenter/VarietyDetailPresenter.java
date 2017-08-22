package com.tobacco.xinyiyun.knowledge.mvp.home.presenter;

import com.tobacco.xinyiyun.knowledge.base.BasePresenter;
import com.tobacco.xinyiyun.knowledge.mvp.home.VarietyDetailActivity;
import com.tobacco.xinyiyun.knowledge.mvp.home.entity.VarietyDetail;
import com.tobacco.xinyiyun.knowledge.mvp.home.model.VarietyModel;
import com.tobacco.xinyiyun.knowledge.net.ApiSubscribe;
import com.tobacco.xinyiyun.knowledge.transformer.LoadTransformer;

/**
 * Created by YangQiang on 2017/8/18.
 */

public class VarietyDetailPresenter extends BasePresenter<VarietyModel,VarietyDetailActivity> {
    public void queryVarietyDetail(String ywid) {
        getModel().queyrVarietyDetail(ywid)
                .compose(this.<VarietyDetail>appTransformer())
                .compose(new LoadTransformer<VarietyDetail>(getView()))
                .subscribe(new ApiSubscribe<VarietyDetail>() {
                    @Override
                    public void onNext(VarietyDetail pestDetail) {
                        super.onNext(pestDetail);
                        if (pestDetail.success && pestDetail.data != null && pestDetail.data.size() > 0) {
                            getView().setData(pestDetail.data.get(0));
                        } else {
                            getView().setData(null);
                        }
                    }
                });
    }
}
