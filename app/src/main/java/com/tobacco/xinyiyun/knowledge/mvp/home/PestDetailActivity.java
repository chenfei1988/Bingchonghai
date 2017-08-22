package com.tobacco.xinyiyun.knowledge.mvp.home;

import android.text.TextUtils;

import com.tobacco.xinyiyun.knowledge.R;
import com.tobacco.xinyiyun.knowledge.entity.Collect;
import com.tobacco.xinyiyun.knowledge.mvp.home.base.BaseDetailActivity;
import com.tobacco.xinyiyun.knowledge.mvp.home.entity.PestDetail;
import com.tobacco.xinyiyun.knowledge.mvp.home.presenter.PestDetailPresenter;
import com.tobacco.xinyiyun.knowledge.util.Utils;

public class PestDetailActivity extends BaseDetailActivity<PestDetailPresenter, PestDetail.DataEntity> {

    Collect mCollect;

    @Override
    public String[] getGroups() {
        return getResources().getStringArray(R.array.pets_detail_items);
    }

    @Override
    public String[] getChilds(PestDetail.DataEntity data) {
        mCollect = new Collect();
        mCollect.subTitle = mEntity.subTitle;
        mCollect.title = data.bchbt;
        mCollect.imgs = data.remark;
        mCollect.ywid = mEntity.ywid;
        mCollect.ywlx = mEntity.arg.equals("1") ? 101 : 102;
        checkCollect(mCollect.ywid, mCollect.ywlx);
        mTxTitle.setText(data.bchbt);
        mTxTime.setText(data.updatetime);
        mTxRead.setText(String.valueOf(data.readnum));
        if (!TextUtils.isEmpty(data.remark)) {
            mRpvGallery.setAdapter(new GralleryAdapter(mRpvGallery, Utils.trim(data.remark.split("#"))));
        }
        return new String[]{data.whzz, data.fzff, data.bhms, data.bhyl, data.fsgl};
    }

    @Override
    public void loadData() {
        getP().queryPestDetail(mEntity.ywid);
    }

    @Override
    public Collect getCollect() {
        return mCollect;
    }


}
