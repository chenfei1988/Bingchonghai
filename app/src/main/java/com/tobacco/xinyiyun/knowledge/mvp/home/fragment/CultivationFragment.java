package com.tobacco.xinyiyun.knowledge.mvp.home.fragment;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.tobacco.xinyiyun.knowledge.mvp.common.WebBrowerActivity;
import com.tobacco.xinyiyun.knowledge.mvp.home.fragment.base.BaseKdgeFragment;
import com.tobacco.xinyiyun.knowledge.mvp.home.presenter.CultivationPresenter;

/**
 * Created by YangQiang on 2017/8/17.
 */

public class CultivationFragment extends BaseKdgeFragment<CultivationPresenter> {


    @Override
    public void loadData() {
        getP().getCultivationList(getArgValue(ARG1));
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        getP().getCultivationList(getArgValue(ARG1));
    }

    @Override
    public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
        WebBrowerActivity.start(getActivity(), mListEntity.get(position).url, mListEntity.get(position).title);
    }
}
