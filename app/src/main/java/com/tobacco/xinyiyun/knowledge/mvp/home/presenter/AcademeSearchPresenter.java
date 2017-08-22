package com.tobacco.xinyiyun.knowledge.mvp.home.presenter;

import android.text.TextUtils;

import com.tobacco.xinyiyun.knowledge.base.BasePresenter;
import com.tobacco.xinyiyun.knowledge.mvp.common.entity.ContentEntity;
import com.tobacco.xinyiyun.knowledge.mvp.home.AcademeSearchActivity;
import com.tobacco.xinyiyun.knowledge.mvp.home.entity.Academe;
import com.tobacco.xinyiyun.knowledge.mvp.home.model.AcademeModel;
import com.tobacco.xinyiyun.knowledge.net.ApiSubscribe;
import com.tobacco.xinyiyun.knowledge.transformer.LoadTransformer;
import com.tobacco.xinyiyun.knowledge.util.Utils;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

/**
 * Created by YangQiang on 2017/8/18.
 */

public class AcademeSearchPresenter extends BasePresenter<AcademeModel, AcademeSearchActivity> {

    public void getAcademeList(String wzlx, String context) {
        getModel().getAcademeList(wzlx, context)
                .map(new Func1<Academe, Academe>() {
                    @Override
                    public Academe call(Academe pest) {
                        if (pest.success && pest.data != null) {
                            for (Academe.DataEntity dataEntity : pest.data) {
                                if (!TextUtils.isEmpty(dataEntity.wztp)) {
                                    dataEntity.wztps = Utils.trim(dataEntity.wztp.split("#"));
                                }
                            }
                        }
                        return pest;
                    }
                })
                .compose(this.<Academe>appTransformer())
                .compose(LoadTransformer.<Academe>build(getView()))
                .subscribe(new ApiSubscribe<Academe>() {
                    @Override
                    public void onNext(Academe cultivation) {
                        super.onNext(cultivation);
                        if (cultivation.success && cultivation.data != null && cultivation.data.size() > 0) {
                            List<ContentEntity> mListData = new ArrayList<>();
                            for (Academe.DataEntity dataEntity : cultivation.data) {
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
