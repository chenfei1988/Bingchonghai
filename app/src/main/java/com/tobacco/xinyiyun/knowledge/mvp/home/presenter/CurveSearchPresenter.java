package com.tobacco.xinyiyun.knowledge.mvp.home.presenter;

import android.text.TextUtils;

import com.tobacco.xinyiyun.knowledge.base.BasePresenter;
import com.tobacco.xinyiyun.knowledge.mvp.common.entity.ContentEntity;
import com.tobacco.xinyiyun.knowledge.mvp.home.CurveSearchActivity;
import com.tobacco.xinyiyun.knowledge.mvp.home.entity.Curve;
import com.tobacco.xinyiyun.knowledge.mvp.home.model.CurveModel;
import com.tobacco.xinyiyun.knowledge.net.ApiSubscribe;
import com.tobacco.xinyiyun.knowledge.transformer.LoadTransformer;
import com.tobacco.xinyiyun.knowledge.util.Utils;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

/**
 * Created by YangQiang on 2017/8/18.
 */

public class CurveSearchPresenter extends BasePresenter<CurveModel, CurveSearchActivity> {

    public void getCurveList(String context, String syfw) {
        getModel().getCurveList(context, syfw)
                .map(new Func1<Curve, Curve>() {
                    @Override
                    public Curve call(Curve pest) {
                        if (pest.success && pest.data != null) {
                            for (Curve.DataEntity dataEntity : pest.data) {
                                if (!TextUtils.isEmpty(dataEntity.wztp)) {
                                    dataEntity.wztps = Utils.trim(dataEntity.wztp.split("#"));
                                }
                            }
                        }
                        return pest;
                    }
                })
                .compose(this.<Curve>appTransformer())
                .compose(LoadTransformer.<Curve>build(getView()))
                .subscribe(new ApiSubscribe<Curve>() {
                    @Override
                    public void onNext(Curve pest) {
                        super.onNext(pest);
                        if (pest.success && pest.data != null && pest.data.size() > 0) {
                            List<ContentEntity> mListData = new ArrayList<>();
                            for (Curve.DataEntity dataEntity : pest.data) {
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
