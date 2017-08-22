package com.tobacco.xinyiyun.knowledge.mvp.home.presenter;

import android.text.TextUtils;

import com.tobacco.xinyiyun.knowledge.base.BasePresenter;
import com.tobacco.xinyiyun.knowledge.mvp.common.entity.ContentEntity;
import com.tobacco.xinyiyun.knowledge.mvp.home.CultivationSearchActivity;
import com.tobacco.xinyiyun.knowledge.mvp.home.entity.Cultivation;
import com.tobacco.xinyiyun.knowledge.mvp.home.model.CultivationModel;
import com.tobacco.xinyiyun.knowledge.net.ApiSubscribe;
import com.tobacco.xinyiyun.knowledge.transformer.LoadTransformer;
import com.tobacco.xinyiyun.knowledge.util.Utils;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

/**
 * Created by YangQiang on 2017/8/18.
 */

public class CultivationSearchPresenter extends BasePresenter<CultivationModel, CultivationSearchActivity> {

    public void getCultivationList(String wzlx, String context) {
        getModel().getCultivationList(wzlx, context)
                .map(new Func1<Cultivation, Cultivation>() {
                    @Override
                    public Cultivation call(Cultivation pest) {
                        if (pest.success && pest.data != null) {
                            for (Cultivation.DataEntity dataEntity : pest.data) {
                                if (!TextUtils.isEmpty(dataEntity.wztp)) {
                                    dataEntity.wztps = Utils.trim(dataEntity.wztp.split("#"));
                                }
                            }
                        }
                        return pest;
                    }
                })
                .compose(this.<Cultivation>appTransformer())
                .compose(LoadTransformer.<Cultivation>build(getView()))
                .subscribe(new ApiSubscribe<Cultivation>() {
                    @Override
                    public void onNext(Cultivation cultivation) {
                        super.onNext(cultivation);
                        if (cultivation.success && cultivation.data != null && cultivation.data.size() > 0) {
                            List<ContentEntity> mListData = new ArrayList<>();
                            for (Cultivation.DataEntity dataEntity : cultivation.data) {
                                ContentEntity mEntity = new ContentEntity();
                                mEntity.title = dataEntity.wzbt;
                                mEntity.subTitle = dataEntity.wzjj;
                                mEntity.imgs = dataEntity.wztps;
                                mEntity.ywid = String.valueOf(dataEntity.ywid);
                                mEntity.url = dataEntity.twdz;
                                mListData.add(mEntity);
                            }
                            getView().setData(mListData);
                        } else {
                            getView().setData(null);
                        }
                    }
                });
    }
}
