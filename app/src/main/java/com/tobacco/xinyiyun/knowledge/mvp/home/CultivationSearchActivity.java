package com.tobacco.xinyiyun.knowledge.mvp.home;

import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tobacco.xinyiyun.knowledge.mvp.common.WebBrowerActivity;
import com.tobacco.xinyiyun.knowledge.mvp.home.base.BaseSearchActivity;
import com.tobacco.xinyiyun.knowledge.mvp.home.fragment.base.BaseKdgeFragment;
import com.tobacco.xinyiyun.knowledge.mvp.home.presenter.CultivationSearchPresenter;
import com.tobacco.xinyiyun.knowledge.util.ToastUtils;
import com.tobacco.xinyiyun.knowledge.util.ViewUtils;

public class CultivationSearchActivity extends BaseSearchActivity<CultivationSearchPresenter> {

    @Override
    public void onSearch(String text) {
        if (TextUtils.isEmpty(text)) {
            ToastUtils.show(this, mEditSearch.getHint());
            return;
        }
        ViewUtils.closeKeybord(mEditSearch, this);
        getP().getCultivationList(getIntent().getStringExtra(BaseKdgeFragment.ARG1), text);
    }

    @Override
    public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
        WebBrowerActivity.start(CultivationSearchActivity.this, mListData.get(position).url, mListData.get(position).title);
    }
}
