package com.tobacco.xinyiyun.knowledge.mvp.home.presenter;

import android.text.TextUtils;

import com.tobacco.xinyiyun.knowledge.base.BasePresenter;
import com.tobacco.xinyiyun.knowledge.mvp.common.entity.ContentEntity;
import com.tobacco.xinyiyun.knowledge.mvp.home.entity.Bake;
import com.tobacco.xinyiyun.knowledge.mvp.home.fragment.BakeFragment;
import com.tobacco.xinyiyun.knowledge.mvp.home.model.BakeModel;
import com.tobacco.xinyiyun.knowledge.net.ApiSubscribe;
import com.tobacco.xinyiyun.knowledge.transformer.LoadTransformer;
import com.tobacco.xinyiyun.knowledge.util.Utils;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

/**
 * Created by YangQiang on 2017/8/18.
 */

public class BakePresenter extends BasePresenter<BakeModel, BakeFragment> {

    public void getBakeList(String wzlx, String yypz, String yybw, String context) {
        getModel().getBakeList(wzlx, yypz, yybw, context)
                .map(new Func1<Bake, Bake>() {
                    @Override
                    public Bake call(Bake pest) {
                        if (pest.success && pest.data != null) {
                            for (Bake.DataEntity dataEntity : pest.data) {
                                if (!TextUtils.isEmpty(dataEntity.wztp)) {
                                    dataEntity.wztps = Utils.trim(dataEntity.wztp.split("#"));
                                }
                            }
                        }
                        return pest;
                    }
                })
                .compose(this.<Bake>appTransformer())
                .compose(LoadTransformer.<Bake>build(getView()))
                .subscribe(new ApiSubscribe<Bake>() {
                    @Override
                    public void onNext(Bake pest) {
                        super.onNext(pest);
                        if (pest.success && pest.data != null && pest.data.size() > 0) {
                            List<ContentEntity> mListData = new ArrayList<>();
                            for (Bake.DataEntity dataEntity : pest.data) {
                                ContentEntity mEntity = new ContentEntity();
                                mEntity.title = dataEntity.wzbt;
                                mEntity.subTitle = dataEntity.wzjj;
                                mEntity.imgs = dataEntity.wztps;
                                mEntity.ywid = String.valueOf(dataEntity.ywid);
                                mEntity.url=dataEntity.twdz;
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
