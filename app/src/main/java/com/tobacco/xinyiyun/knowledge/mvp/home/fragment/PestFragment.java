package com.tobacco.xinyiyun.knowledge.mvp.home.fragment;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.tobacco.xinyiyun.knowledge.mvp.common.entity.ContentEntity;
import com.tobacco.xinyiyun.knowledge.mvp.home.PestDetailActivity;
import com.tobacco.xinyiyun.knowledge.mvp.home.fragment.base.BaseKdgeFragment;
import com.tobacco.xinyiyun.knowledge.mvp.home.presenter.PestPresenter;

/**
 * Created by YangQiang on 2017/8/15.
 */

public class PestFragment extends BaseKdgeFragment<PestPresenter> {

    @Override
    public void loadData() {
        getP().getPestList(getArgValue(ARG1), null, null, null);
    }


    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        getP().getPestList(getArgValue(ARG1), null, null, null);
    }

    public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
//        start(getActivity(), PestDetailActivity.class, mListEntity.get(position).ywid, mListEntity.get(position).subTitle,getArgValue(ARG1));
        ContentEntity mEntity = mListEntity.get(position);
        mEntity.arg = getArgValue(ARG1);
        PestDetailActivity.start(getActivity(), PestDetailActivity.class, mEntity);
    }

}
