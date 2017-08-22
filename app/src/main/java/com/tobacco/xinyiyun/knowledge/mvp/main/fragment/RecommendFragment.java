package com.tobacco.xinyiyun.knowledge.mvp.main.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.tobacco.xinyiyun.knowledge.R;
import com.tobacco.xinyiyun.knowledge.annocation.LayoutInject;
import com.tobacco.xinyiyun.knowledge.annocation.MultiStateInject;
import com.tobacco.xinyiyun.knowledge.annocation.RefreshInject;
import com.tobacco.xinyiyun.knowledge.annocation.ToolbarInject;
import com.tobacco.xinyiyun.knowledge.base.BaseFragment;
import com.tobacco.xinyiyun.knowledge.mvp.common.adapter.ContentAdapter;
import com.tobacco.xinyiyun.knowledge.mvp.common.entity.ContentEntity;
import com.tobacco.xinyiyun.knowledge.mvp.home.PestDetailActivity;
import com.tobacco.xinyiyun.knowledge.mvp.main.presenter.RecommendPresenter;
import com.tobacco.xinyiyun.knowledge.view.HorizontalDivider;
import com.tobacco.xinyiyun.knowledge.view.MultiStateView;
import com.tobacco.xinyiyun.knowledge.view.VerticalDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by YangQiang on 2017/8/15.
 */

@LayoutInject(R.layout.fragment_recommend)
@ToolbarInject("推荐")
@RefreshInject(R.id.rcl_recommend)
@MultiStateInject(R.id.rcl_recommend)
public class RecommendFragment extends BaseFragment<RecommendPresenter> {
    @Bind(R.id.rcl_recommend)
    RecyclerView mRclRecommend;
    List<ContentEntity> mListData = new ArrayList<>();
    ContentAdapter mContentAdapter;

    @Override
    public void init(View view) {

        mRclRecommend.setLayoutManager(new LinearLayoutManager(getActivity()));

        mContentAdapter = new ContentAdapter(mListData, RecommendFragment.this);
        mRclRecommend.setAdapter(mContentAdapter);
        mRclRecommend.addItemDecoration(new VerticalDivider.Builder(getActivity())
                .sizeResId(R.dimen.dp_05)
                .colorResId(R.color.gray_line)
                .build());
        mRclRecommend.addItemDecoration(new HorizontalDivider.Builder(getActivity())
                .sizeResId(R.dimen.dp_05)
                .colorResId(R.color.gray_line)
                .build());
        mRclRecommend.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                ContentEntity mEntity = mListData.get(position);
                mEntity.arg = "1";
                PestDetailActivity.start(getActivity(), PestDetailActivity.class, mEntity);
            }
        });
        getP().getRecommendList();
    }


    public void setData(List<ContentEntity> list) {
        if (list == null) {
            mListData.clear();
            setViewState(MultiStateView.VIEW_STATE_EMPTY);
        } else {
            mListData.clear();
            mListData.addAll(list);
        }
        mContentAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        super.onRefresh(refreshlayout);
        getP().getRecommendList();
    }
}
