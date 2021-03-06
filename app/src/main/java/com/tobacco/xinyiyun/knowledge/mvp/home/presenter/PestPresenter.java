package com.tobacco.xinyiyun.knowledge.mvp.home.presenter;

import android.text.TextUtils;

import com.tobacco.xinyiyun.knowledge.base.BasePresenter;
import com.tobacco.xinyiyun.knowledge.mvp.common.entity.ContentEntity;
import com.tobacco.xinyiyun.knowledge.mvp.home.entity.Pest;
import com.tobacco.xinyiyun.knowledge.mvp.home.fragment.PestFragment;
import com.tobacco.xinyiyun.knowledge.mvp.home.model.PestModel;
import com.tobacco.xinyiyun.knowledge.net.ApiSubscribe;
import com.tobacco.xinyiyun.knowledge.transformer.LoadTransformer;
import com.tobacco.xinyiyun.knowledge.util.Utils;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

/**
 * Created by YangQiang on 2017/8/16.
 */

public class PestPresenter extends BasePresenter<PestModel, PestFragment> {
    /**
     * 获取病虫害列表
     *
     * @param type
     * @param ssszzq
     * @param ssbw
     * @param context
     */
    public void getPestList(String type, String ssszzq, String ssbw, String context) {
        getModel().getPestList(ssszzq, ssbw, type, context)
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
