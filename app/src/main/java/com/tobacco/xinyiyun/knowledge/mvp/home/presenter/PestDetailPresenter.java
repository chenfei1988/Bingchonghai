package com.tobacco.xinyiyun.knowledge.mvp.home.presenter;

import com.tobacco.xinyiyun.knowledge.base.BasePresenter;
import com.tobacco.xinyiyun.knowledge.mvp.home.entity.PestDetail;
import com.tobacco.xinyiyun.knowledge.mvp.home.PestDetailActivity;
import com.tobacco.xinyiyun.knowledge.mvp.home.model.PestModel;
import com.tobacco.xinyiyun.knowledge.net.ApiSubscribe;
import com.tobacco.xinyiyun.knowledge.transformer.LoadTransformer;

/**
 * Created by YangQiang on 2017/8/17.
 */

public class PestDetailPresenter extends BasePresenter<PestModel, PestDetailActivity> {

    public void queryPestDetail(String  ywid) {
        getModel().queryPestDetail(ywid)
                .compose(this.<PestDetail>appTransformer())
                .compose(new LoadTransformer<PestDetail>(getView()))
                .subscribe(new ApiSubscribe<PestDetail>() {
                    @Override
                    public void onNext(PestDetail pestDetail) {
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
