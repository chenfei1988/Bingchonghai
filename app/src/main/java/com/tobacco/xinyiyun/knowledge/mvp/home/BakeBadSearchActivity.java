package com.tobacco.xinyiyun.knowledge.mvp.home;

import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tobacco.xinyiyun.knowledge.mvp.common.entity.ContentEntity;
import com.tobacco.xinyiyun.knowledge.mvp.home.base.BaseSearchActivity;
import com.tobacco.xinyiyun.knowledge.mvp.home.fragment.base.BaseKdgeFragment;
import com.tobacco.xinyiyun.knowledge.mvp.home.presenter.BakeBadSearchPresenter;
import com.tobacco.xinyiyun.knowledge.util.ToastUtils;
import com.tobacco.xinyiyun.knowledge.util.ViewUtils;

public class BakeBadSearchActivity extends BaseSearchActivity<BakeBadSearchPresenter> {
    @Override
    public void onSearch(String text) {
        String mSearch = mEditSearch.getText().toString().trim();
        if (TextUtils.isEmpty(mSearch)) {
            ToastUtils.show(this, mEditSearch.getHint());
            return;
        }
        ViewUtils.closeKeybord(mEditSearch, this);
        getP().getBakeList(getIntent().getStringExtra(BaseKdgeFragment.ARG1));
    }

    @Override
    public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
        ContentEntity mEntity = mListData.get(position);
        mEntity.arg = getIntent().getStringExtra(BaseKdgeFragment.ARG1);
        BakeDetailActivity.start(this, BakeDetailActivity.class, mEntity);
    }

}
