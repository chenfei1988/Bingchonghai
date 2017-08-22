package com.tobacco.xinyiyun.knowledge.mvp.main;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tobacco.xinyiyun.knowledge.R;
import com.tobacco.xinyiyun.knowledge.annocation.LayoutInject;
import com.tobacco.xinyiyun.knowledge.annocation.MultiStateInject;
import com.tobacco.xinyiyun.knowledge.annocation.ToolbarInject;
import com.tobacco.xinyiyun.knowledge.base.BaseActivity;
import com.tobacco.xinyiyun.knowledge.entity.Collect;
import com.tobacco.xinyiyun.knowledge.mvp.home.BakeDetailActivity;
import com.tobacco.xinyiyun.knowledge.mvp.home.PestDetailActivity;
import com.tobacco.xinyiyun.knowledge.mvp.home.VarietyDetailActivity;
import com.tobacco.xinyiyun.knowledge.mvp.main.presenter.CollectPresenter;
import com.tobacco.xinyiyun.knowledge.util.Utils;
import com.tobacco.xinyiyun.knowledge.view.HorizontalDivider;
import com.tobacco.xinyiyun.knowledge.view.MultiStateView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


@LayoutInject(R.layout.activity_collect)
@ToolbarInject(value = "我的收藏", showBack = true)
@MultiStateInject(value = R.id.rcl_collects)
public class CollectActivity extends BaseActivity<CollectPresenter> {

    @Bind(R.id.rcl_collects)
    RecyclerView mRclCollects;
    CollectAdapter mCollectAdapter;
    List<Collect> mListCollect = new ArrayList<>();

    @Override
    public void init() {
        mRclCollects.setLayoutManager(new LinearLayoutManager(this));
        mRclCollects.addItemDecoration(new HorizontalDivider.Builder(this)
                .sizeResId(R.dimen.dp_05)
                .colorResId(R.color.gray_line)
                .build());
        mCollectAdapter = new CollectAdapter(mListCollect);

        mRclCollects.setAdapter(mCollectAdapter);

        mCollectAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Collect mCollect = mListCollect.get(position);
                switch (mCollect.ywlx) {
                    case 101:
                    case 102:
                        PestDetailActivity.start(CollectActivity.this, PestDetailActivity.class, mCollect.convert());
                        break;
                    case 3:
                        VarietyDetailActivity.start(CollectActivity.this, VarietyDetailActivity.class, mCollect.convert());
                        break;
                    case 4:
                    case 5:
                    case 6:
                        BakeDetailActivity.start(CollectActivity.this, BakeDetailActivity.class, mCollect.convert());
                }
            }
        });
        mCollectAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                new MaterialDialog.Builder(CollectActivity.this).title("提示")
                        .content("你确定要删除这条收藏记录吗?")
                        .positiveText("确定")
                        .positiveColor(getResources().getColor(R.color.colorPrimary))
                        .negativeText("取消")
                        .negativeColor(getResources().getColor(R.color.colorPrimary))
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                getP().removeCollect(mListCollect.get(position));
                            }
                        }).build().show();

            }
        });
        getP().getCollectList();
    }

    public void setData(List<Collect> list) {
        if (list == null) {
            mListCollect.clear();
            setViewState(MultiStateView.VIEW_STATE_EMPTY);
        } else {
            mListCollect.clear();
            mListCollect.addAll(list);
        }
        mCollectAdapter.notifyDataSetChanged();
    }

    private class CollectAdapter extends BaseQuickAdapter<Collect, BaseViewHolder> {

        public CollectAdapter(@Nullable final List<Collect> data) {
            super(R.layout.view_collect_list_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Collect item) {
            helper.addOnClickListener(R.id.tx_remove);
            helper.setText(R.id.tx_title, item.title);
            helper.setText(R.id.tx_sub_title, item.subTitle);

            ImageView mImg1 = helper.getView(R.id.img_icon1);
            ImageView mImg2 = helper.getView(R.id.img_icon2);
            ImageView mImg3 = helper.getView(R.id.img_icon3);
            ImageView[] mImages = new ImageView[]{mImg1, mImg2, mImg3};
            String[] mUrls = Utils.trim(item.imgs.split("#"));
            for (int i = 0; i < mImages.length; i++) {
                if (i < mUrls.length) {
                    mImages[i].setVisibility(View.VISIBLE);
                } else {
                    mImages[i].setVisibility(View.GONE);
                }
            }
            for (int i = 0; i < mUrls.length && i < mImages.length; i++) {
                Glide.with(CollectActivity.this)
                        .load(mUrls[i])
                        .asBitmap()
                        .placeholder(R.mipmap.ic_loading)
                        .error(R.mipmap.ic_load_faile)
                        .centerCrop()
                        .dontAnimate()
                        .into(mImages[i]);
            }


        }
    }
}
