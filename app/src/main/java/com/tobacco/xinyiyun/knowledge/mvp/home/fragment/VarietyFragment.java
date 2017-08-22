package com.tobacco.xinyiyun.knowledge.mvp.home.fragment;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.tobacco.xinyiyun.knowledge.mvp.common.entity.ContentEntity;
import com.tobacco.xinyiyun.knowledge.mvp.home.VarietyDetailActivity;
import com.tobacco.xinyiyun.knowledge.mvp.home.fragment.base.BaseKdgeFragment;
import com.tobacco.xinyiyun.knowledge.mvp.home.presenter.VarietyPresenter;

/**
 * Created by YangQiang on 2017/8/15.
 */

public class VarietyFragment extends BaseKdgeFragment<VarietyPresenter> {

    @Override
    public void loadData() {
        getP().getVarietyList("");
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        getP().getVarietyList("");
    }

    public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
        ContentEntity mEntity = mListEntity.get(position);
        mEntity.arg = getArgValue(ARG1);
        VarietyDetailActivity.start(getActivity(), VarietyDetailActivity.class, mEntity);
    }

}
