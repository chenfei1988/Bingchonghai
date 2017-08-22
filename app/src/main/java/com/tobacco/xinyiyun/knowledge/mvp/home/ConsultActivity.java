package com.tobacco.xinyiyun.knowledge.mvp.home;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.tobacco.xinyiyun.knowledge.R;
import com.tobacco.xinyiyun.knowledge.annocation.LayoutInject;
import com.tobacco.xinyiyun.knowledge.annocation.MultiStateInject;
import com.tobacco.xinyiyun.knowledge.annocation.RefreshInject;
import com.tobacco.xinyiyun.knowledge.annocation.ToolbarInject;
import com.tobacco.xinyiyun.knowledge.base.BaseActivity;
import com.tobacco.xinyiyun.knowledge.mvp.home.entity.Consult;
import com.tobacco.xinyiyun.knowledge.mvp.home.presenter.ConsultPresenter;
import com.tobacco.xinyiyun.knowledge.view.HorizontalDivider;
import com.tobacco.xinyiyun.knowledge.view.MultiStateView;
import com.tobacco.xinyiyun.knowledge.view.VerticalDivider;
import com.wzgiceman.rxbuslibrary.rxbus.RxBus;
import com.wzgiceman.rxbuslibrary.rxbus.Subscribe;
import com.wzgiceman.rxbuslibrary.rxbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;

@LayoutInject(value = R.layout.activity_consult, rxbus = true)
@MultiStateInject(R.id.rcl_consults)
@RefreshInject(R.id.rcl_consults)
@ToolbarInject(value = "咨询专家", showBack = true, menuId = R.menu.menu_consult_question)
public class ConsultActivity extends BaseActivity<ConsultPresenter> {
    public static final int REFRESH_CODE = 10001;
    @Bind(R.id.rcl_consults)
    RecyclerView mRclConsults;
    List<Consult.DataEntity> mListData = new ArrayList<>();
    ConsultAdapter mConsultAdapter;

    @Override
    public void init() {
        mConsultAdapter = new ConsultAdapter(mListData);
        mRclConsults.setLayoutManager(new LinearLayoutManager(this));
        mRclConsults.setAdapter(mConsultAdapter);
        mRclConsults.addItemDecoration(new HorizontalDivider.Builder(this)
                .sizeResId(R.dimen.dp_05)
                .colorResId(R.color.gray_line)
                .build());
        mRclConsults.addOnItemTouchListener(new com.chad.library.adapter.base.listener.OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                ReplyActivity.start(ConsultActivity.this, mListData.get(position));
            }
        });
        getP().getConsultList();
    }

    public void setData(List<Consult.DataEntity> list) {
        if (list == null) {
            setViewState(MultiStateView.VIEW_STATE_EMPTY);
        } else {
            mListData.clear();
            mListData.addAll(list);
            mConsultAdapter.notifyDataSetChanged();
        }
    }

    private class ConsultAdapter extends BaseQuickAdapter<Consult.DataEntity, BaseViewHolder> {

        public ConsultAdapter(@Nullable List<Consult.DataEntity> data) {
            super(R.layout.view_consult_list_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Consult.DataEntity item) {
            helper.setText(R.id.tx_name, item.qzfbr);
            helper.setText(R.id.tx_consult, item.qznr);
            RecyclerView mRclPictures = helper.getView(R.id.rcl_imgs);
            mRclPictures.setLayoutManager(new GridLayoutManager(ConsultActivity.this, 3));
            mRclPictures.addItemDecoration(new HorizontalDivider.Builder(ConsultActivity.this)
                    .sizeResId(R.dimen.dp_2)
                    .colorResId(R.color.transparent)
                    .build());
            mRclPictures.addItemDecoration(new VerticalDivider.Builder(ConsultActivity.this)
                    .sizeResId(R.dimen.dp_2)
                    .colorResId(R.color.transparent)
                    .build());
            mRclPictures.setAdapter(new PictuerAdater(item.qztps == null ? new ArrayList<String>() : new ArrayList<>(Arrays.asList(item.qztps))));

            ImageView mImg = helper.getView(R.id.img_head);
            Glide.with(mContext)
                    .load(item.qzfbrtx)
                    .placeholder(R.mipmap.ic_loading)
                    .error(R.mipmap.ic_load_faile)
                    .centerCrop()
                    .dontAnimate()
                    .into(mImg);
        }
    }


    private class PictuerAdater extends BaseQuickAdapter<String, BaseViewHolder> {

        public PictuerAdater(@Nullable List<String> data) {
            super(R.layout.view_question_list_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            Glide.with(mContext)
                    .load(item)
                    .placeholder(R.mipmap.ic_loading)
                    .error(R.mipmap.ic_load_faile)
                    .centerCrop()
                    .dontAnimate()
                    .into((ImageView) helper.getView(R.id.img_picture));
        }
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.menu_question) {
            startActivity(new Intent(this, QuestionActivity.class));
        }
        return super.onMenuItemClick(item);
    }


    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        super.onRefresh(refreshlayout);
        getP().getConsultList();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, code = REFRESH_CODE)
    public void refresh(String msg) {
        getRefresh().autoRefresh(500);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getDefault().unRegister(this);
    }
}

