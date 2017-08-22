package com.tobacco.xinyiyun.knowledge.mvp.home;

import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tobacco.xinyiyun.knowledge.mvp.common.entity.ContentEntity;
import com.tobacco.xinyiyun.knowledge.mvp.home.base.BaseSearchActivity;
import com.tobacco.xinyiyun.knowledge.mvp.home.presenter.VarietySearchPresenter;
import com.tobacco.xinyiyun.knowledge.util.ToastUtils;
import com.tobacco.xinyiyun.knowledge.util.ViewUtils;

public class VarietySearchActivity extends BaseSearchActivity<VarietySearchPresenter> {

    @Override
    public void onSearch(String text) {
        String mSearch = mEditSearch.getText().toString().trim();
        if (TextUtils.isEmpty(mSearch)) {
            ToastUtils.show(this, mEditSearch.getHint());
            return;
        }
        ViewUtils.closeKeybord(mEditSearch, this);
        getP().getVarietyList(text);
    }

    @Override
    public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
        ContentEntity mEntity = mListData.get(position);
        VarietyDetailActivity.start(this, VarietyDetailActivity.class, mEntity);
    }
}
