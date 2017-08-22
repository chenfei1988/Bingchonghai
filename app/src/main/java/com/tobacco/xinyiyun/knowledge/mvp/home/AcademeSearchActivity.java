package com.tobacco.xinyiyun.knowledge.mvp.home;

import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tobacco.xinyiyun.knowledge.mvp.common.WebBrowerActivity;
import com.tobacco.xinyiyun.knowledge.mvp.home.base.BaseSearchActivity;
import com.tobacco.xinyiyun.knowledge.mvp.home.fragment.base.BaseKdgeFragment;
import com.tobacco.xinyiyun.knowledge.mvp.home.presenter.AcademeSearchPresenter;
import com.tobacco.xinyiyun.knowledge.util.ToastUtils;
import com.tobacco.xinyiyun.knowledge.util.ViewUtils;

public class AcademeSearchActivity extends BaseSearchActivity<AcademeSearchPresenter> {

    @Override
    public void onSearch(String text) {
        String mSearch = mEditSearch.getText().toString().trim();
        if (TextUtils.isEmpty(mSearch)) {
            ToastUtils.show(this, mEditSearch.getHint());
            return;
        }
        ViewUtils.closeKeybord(mEditSearch, this);
        getP().getAcademeList(getIntent().getStringExtra(BaseKdgeFragment.ARG1), text);
    }

    @Override
    public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
        WebBrowerActivity.start(AcademeSearchActivity.this, mListData.get(position).url, mListData.get(position).title);
    }
}
