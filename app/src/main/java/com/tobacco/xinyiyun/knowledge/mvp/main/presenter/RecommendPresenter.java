package com.tobacco.xinyiyun.knowledge.mvp.main.presenter;

import android.text.TextUtils;

import com.tobacco.xinyiyun.knowledge.base.BasePresenter;
import com.tobacco.xinyiyun.knowledge.mvp.common.entity.ContentEntity;
import com.tobacco.xinyiyun.knowledge.mvp.home.entity.Pest;
import com.tobacco.xinyiyun.knowledge.mvp.home.model.PestModel;
import com.tobacco.xinyiyun.knowledge.mvp.main.fragment.RecommendFragment;
import com.tobacco.xinyiyun.knowledge.net.ApiSubscribe;
import com.tobacco.xinyiyun.knowledge.transformer.LoadTransformer;
import com.tobacco.xinyiyun.knowledge.util.Utils;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

/**
 * Created by YangQiang on 2017/8/22.
 */

public class RecommendPresenter extends BasePresenter<PestModel,RecommendFragment> {

    public void getRecommendList() {
        getModel().getPestList(null, null, "1", null)
                .map(new Func1<Pest, Pest>() {
                    @Override
                    public Pest call(Pest pest) {
                        if (pest.success && pest.data != null) {
                            for (Pest.DataEntity dataEntity : pest.data) {
                                if (!TextUtils.isEmpty(dataEntity.wztp)) {
                                    dataEntity.wztps = Utils.trim(dataEntity.wztp.split("#"));
                                }
                            }
                        }
                        return pest;
                    }
                })
                .compose(this.<Pest>appTransformer())
                .compose(LoadTransformer.<Pest>build(getView()))
                .subscribe(new ApiSubscribe<Pest>() {
                    @Override
                    public void onNext(Pest pest) {
                        super.onNext(pest);
                        if (pest.success && pest.data != null && pest.data.size() > 0) {
                            List<ContentEntity> mListData = new ArrayList<>();
                            for (Pest.DataEntity dataEntity : pest.data) {
                                ContentEntity mEntity = new ContentEntity();
                                mEntity.title = dataEntity.wzbt;
                                mEntity.subTitle = dataEntity.wzjj;
                                mEntity.imgs = dataEntity.wztps;
                                mEntity.ywid = String.valueOf(dataEntity.ywid);
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
