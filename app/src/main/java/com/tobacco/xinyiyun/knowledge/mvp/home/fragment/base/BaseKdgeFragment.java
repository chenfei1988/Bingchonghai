package com.tobacco.xinyiyun.knowledge.mvp.home.fragment.base;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.tobacco.xinyiyun.knowledge.R;
import com.tobacco.xinyiyun.knowledge.annocation.LayoutInject;
import com.tobacco.xinyiyun.knowledge.annocation.MultiStateInject;
import com.tobacco.xinyiyun.knowledge.annocation.RefreshInject;
import com.tobacco.xinyiyun.knowledge.base.BaseFragment;
import com.tobacco.xinyiyun.knowledge.base.BasePresenter;
import com.tobacco.xinyiyun.knowledge.mvp.common.adapter.ContentAdapter;
import com.tobacco.xinyiyun.knowledge.mvp.common.entity.ContentEntity;
import com.tobacco.xinyiyun.knowledge.view.HorizontalDivider;
import com.tobacco.xinyiyun.knowledge.view.MultiStateView;
import com.tobacco.xinyiyun.knowledge.view.VerticalDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by YangQiang on 2017/8/15.
 */

@LayoutInject(value = R.layout.fragment_knowledge, lazyLoad = true)
@RefreshInject(R.id.rcl_contents)
@MultiStateInject(R.id.rcl_contents)
public abstract class BaseKdgeFragment<P extends BasePresenter> extends BaseFragment<P> {
    public static final String ARG1 = "arg1";
    @Bind(R.id.rcl_contents)
    protected RecyclerView mRclContents;
    protected List<ContentEntity> mListEntity = new ArrayList<>();
    protected ContentAdapter mContentAdapter;

    @Override
    public void init(View view) {

        mContentAdapter = new ContentAdapter(mListEntity, this);
        mRclContents.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRclContents.setAdapter(mContentAdapter);
        mRclContents.addItemDecoration(new VerticalDivider.Builder(getActivity())
                .sizeResId(R.dimen.dp_05)
                .colorResId(R.color.gray_line)
                .build());
        mRclContents.addItemDecoration(new HorizontalDivider.Builder(getActivity())
                .sizeResId(R.dimen.dp_05)
                .colorResId(R.color.gray_line)
                .build());
        mRclContents.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                BaseKdgeFragment.this.onSimpleItemClick(adapter, view, position);
            }
        });
        loadData();
    }

    public void setData(List<ContentEntity> list) {
        if (list != null) {
            mListEntity.clear();
            mListEntity.addAll(list);

        } else {
            setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }

        mContentAdapter.notifyDataSetChanged();
    }

    public abstract void loadData();

    public abstract void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position);
}
