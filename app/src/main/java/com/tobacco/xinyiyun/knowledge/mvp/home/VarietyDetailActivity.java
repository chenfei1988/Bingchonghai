package com.tobacco.xinyiyun.knowledge.mvp.home;

import android.text.TextUtils;

import com.tobacco.xinyiyun.knowledge.R;
import com.tobacco.xinyiyun.knowledge.entity.Collect;
import com.tobacco.xinyiyun.knowledge.mvp.home.base.BaseDetailActivity;
import com.tobacco.xinyiyun.knowledge.mvp.home.entity.VarietyDetail;
import com.tobacco.xinyiyun.knowledge.mvp.home.presenter.VarietyDetailPresenter;
import com.tobacco.xinyiyun.knowledge.util.Utils;

public class VarietyDetailActivity extends BaseDetailActivity<VarietyDetailPresenter, VarietyDetail.DataEntity> {

    Collect mCollect;

    @Override
    public void loadData() {
        getP().queryVarietyDetail(mEntity.ywid);
    }

    @Override
    public Collect getCollect() {
        return mCollect;
    }


    @Override
    public String[] getGroups() {
        return getResources().getStringArray(R.array.variety_detail_items);
    }

    @Override
    public String[] getChilds(VarietyDetail.DataEntity data) {
        mCollect = new Collect();
        mCollect.title = data.title;
        mCollect.subTitle = mEntity.subTitle;
        mCollect.ywlx = 3;
        mCollect.imgs = data.remark;
        mCollect.ywid = mEntity.ywid;

        checkCollect(mCollect.ywid, mCollect.ywlx);
        mTxTitle.setText(data.title);
        mTxTime.setText(data.updatetime);
        mTxRead.setText(String.valueOf(data.readnum));
        if (!TextUtils.isEmpty(data.remark)) {
            mRpvGallery.setAdapter(new VarietyDetailActivity.GralleryAdapter(mRpvGallery, Utils.trim(data.remark.split("#"))));
        }

        return new String[]{data.tztx, data.kbx, data.zpjsyd, data.hkjsyd};
    }

}
