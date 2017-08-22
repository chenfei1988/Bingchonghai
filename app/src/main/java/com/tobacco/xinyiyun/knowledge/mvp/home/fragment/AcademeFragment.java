package com.tobacco.xinyiyun.knowledge.mvp.home.fragment;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.tobacco.xinyiyun.knowledge.mvp.common.WebBrowerActivity;
import com.tobacco.xinyiyun.knowledge.mvp.home.fragment.base.BaseKdgeFragment;
import com.tobacco.xinyiyun.knowledge.mvp.home.presenter.AcademePresenter;

/**
 * Created by YangQiang on 2017/8/15.
 */

public class AcademeFragment extends BaseKdgeFragment<AcademePresenter> {

    @Override
    public void loadData() {
        getP().getAcademeList(getArgValue(ARG1), "");
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        getP().getAcademeList(getArgValue(ARG1), "");
    }

    @Override
    public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
        WebBrowerActivity.start(getActivity(), mListEntity.get(position).url, mListEntity.get(position).title);
    }
}
