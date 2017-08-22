package com.tobacco.xinyiyun.knowledge.mvp.home.presenter;

import android.text.TextUtils;

import com.tobacco.xinyiyun.knowledge.base.BasePresenter;
import com.tobacco.xinyiyun.knowledge.mvp.common.entity.ContentEntity;
import com.tobacco.xinyiyun.knowledge.mvp.home.entity.Variety;
import com.tobacco.xinyiyun.knowledge.mvp.home.fragment.VarietyFragment;
import com.tobacco.xinyiyun.knowledge.mvp.home.model.VarietyModel;
import com.tobacco.xinyiyun.knowledge.net.ApiSubscribe;
import com.tobacco.xinyiyun.knowledge.transformer.LoadTransformer;
import com.tobacco.xinyiyun.knowledge.util.Utils;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

/**
 * Created by YangQiang on 2017/8/18.
 */

public class VarietyPresenter extends BasePresenter<VarietyModel, VarietyFragment> {

    public void getVarietyList(String context) {
        getModel().getVarietyList(context)
                .map(new Func1<Variety, Variety>() {
                    @Override
                    public Variety call(Variety pest) {
                        if (pest.success && pest.data != null) {
                            for (Variety.DataEntity dataEntity : pest.data) {
                                if (!TextUtils.isEmpty(dataEntity.wztp)) {
                                    dataEntity.wztps = Utils.trim(dataEntity.wztp.split("#"));
                                }
                            }
                        }
                        return pest;
                    }
                })
                .compose(this.<Variety>appTransformer())
                .compose(LoadTransformer.<Variety>build(getView()))
                .subscribe(new ApiSubscribe<Variety>() {
                    @Override
                    public void onNext(Variety cultivation) {
                        super.onNext(cultivation);
                        if (cultivation.success && cultivation.data != null && cultivation.data.size() > 0) {
                            List<ContentEntity> mListData = new ArrayList<>();
                            for (Variety.DataEntity dataEntity : cultivation.data) {
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
