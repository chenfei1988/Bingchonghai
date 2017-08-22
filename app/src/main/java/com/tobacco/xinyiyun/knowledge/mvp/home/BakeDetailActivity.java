package com.tobacco.xinyiyun.knowledge.mvp.home;

import android.text.TextUtils;

import com.tobacco.xinyiyun.knowledge.R;
import com.tobacco.xinyiyun.knowledge.entity.Collect;
import com.tobacco.xinyiyun.knowledge.mvp.home.base.BaseDetailActivity;
import com.tobacco.xinyiyun.knowledge.mvp.home.entity.BakeDetail;
import com.tobacco.xinyiyun.knowledge.mvp.home.presenter.BakeDetailPresenter;
import com.tobacco.xinyiyun.knowledge.util.NumberUtils;
import com.tobacco.xinyiyun.knowledge.util.Utils;

public class BakeDetailActivity extends BaseDetailActivity<BakeDetailPresenter, BakeDetail.DataEntity> {

    Collect mCollect;


    @Override
    public String[] getGroups() {
        switch (NumberUtils.toInt(mEntity.arg)) {
            case 1:
                return getResources().getStringArray(R.array.harvest_detail_items);
            case 2:
                return getResources().getStringArray(R.array.bake_detail_items);
            case 3:
                return getResources().getStringArray(R.array.bake_bad_items);
            default:
                return new String[0];
        }

    }

    @Override
    public String[] getChilds(BakeDetail.DataEntity data) {
        mCollect = new Collect();
        mCollect.title = data.title;
        mCollect.imgs = data.remark;
        mCollect.arg = mEntity.arg;
        mCollect.ywid = mEntity.ywid;
        mCollect.subTitle = mEntity.subTitle;
        mTxTitle.setText(data.title);
        mTxTime.setText(data.updatetime);
        mTxRead.setText(String.valueOf(data.readnum));
        if (!TextUtils.isEmpty(data.remark)) {
            mRpvGallery.setAdapter(new GralleryAdapter(mRpvGallery, Utils.trim(data.remark.split("#"))));
        }

        switch (NumberUtils.toInt(mEntity.arg)) {
            case 1:
                mCollect.ywlx = 4;
                checkCollect(mCollect.ywid, mCollect.ywlx);
                return new String[]{data.csd, data.csbz, data.csff, data.cszysx};
            case 2:
                mCollect.ywlx = 5;
                checkCollect(mCollect.ywid, mCollect.ywlx);
                return new String[]{data.syfw, data.byzy, data.hkgy};
            case 3:
                mCollect.ywlx = 6;
                checkCollect(mCollect.ywid, mCollect.ywlx);
                return new String[]{data.msxx, data.csyy, data.fzjscs};
            default:
                return new String[0];
        }

    }


    @Override
    public void loadData() {
        getP().getBakeDetail(mEntity.arg, mEntity.ywid);
    }

    @Override
    public Collect getCollect() {
        return mCollect;
    }

}
