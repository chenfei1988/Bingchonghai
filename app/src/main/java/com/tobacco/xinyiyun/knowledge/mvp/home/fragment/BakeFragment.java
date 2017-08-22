package com.tobacco.xinyiyun.knowledge.mvp.home.fragment;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.tobacco.xinyiyun.knowledge.mvp.common.entity.ContentEntity;
import com.tobacco.xinyiyun.knowledge.mvp.home.BakeDetailActivity;
import com.tobacco.xinyiyun.knowledge.mvp.home.fragment.base.BaseKdgeFragment;
import com.tobacco.xinyiyun.knowledge.mvp.home.presenter.BakePresenter;

/**
 * Created by YangQiang on 2017/8/15.
 */

public class BakeFragment extends BaseKdgeFragment<BakePresenter> {

    @Override
    public void loadData() {
        getP().getBakeList(getArguments().getString("arg1"), null, null, null);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        getP().getBakeList(getArguments().getString("arg1"), null, null, null);
    }

    @Override
    public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
//        start(getActivity(), BakeDetailActivity.class, getArguments().getString("arg1"), mListEntity.get(position).ywid, mListEntity.get(position).subTitle);
        ContentEntity mEntity = mListEntity.get(position);
        mEntity.arg = getArguments().getString("arg1");
        BakeDetailActivity.start(getActivity(), BakeDetailActivity.class, mEntity);
    }
}
