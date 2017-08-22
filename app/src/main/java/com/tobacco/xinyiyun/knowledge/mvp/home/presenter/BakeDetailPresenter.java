package com.tobacco.xinyiyun.knowledge.mvp.home.presenter;

import com.tobacco.xinyiyun.knowledge.base.BasePresenter;
import com.tobacco.xinyiyun.knowledge.mvp.home.base.BaseDetailActivity;
import com.tobacco.xinyiyun.knowledge.mvp.home.entity.BakeDetail;
import com.tobacco.xinyiyun.knowledge.mvp.home.model.BakeModel;
import com.tobacco.xinyiyun.knowledge.net.ApiSubscribe;
import com.tobacco.xinyiyun.knowledge.transformer.LoadTransformer;

/**
 * Created by YangQiang on 2017/8/18.
 */

public class BakeDetailPresenter extends BasePresenter<BakeModel, BaseDetailActivity> {

    public void getBakeDetail(String wzlx, String ywid) {
        getModel().getBakeDetail(wzlx, ywid)
                .compose(this.<BakeDetail>appTransformer())
                .compose(new LoadTransformer<BakeDetail>(getView()))
                .subscribe(new ApiSubscribe<BakeDetail>() {
                    @Override
                    public void onNext(BakeDetail pestDetail) {
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
