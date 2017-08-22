package com.tobacco.xinyiyun.knowledge.mvp.common.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tobacco.xinyiyun.knowledge.R;
import com.tobacco.xinyiyun.knowledge.mvp.common.entity.ContentEntity;

import java.util.List;

/**
 * Created by YangQiang on 2017/8/18.
 */

public class SearchResultAdapter extends BaseQuickAdapter<ContentEntity, BaseViewHolder> {
    Context mContext;

    public SearchResultAdapter(@Nullable List<ContentEntity> data, Context context) {
        super(R.layout.view_pest_search_list_item, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ContentEntity item) {
        helper.setText(R.id.tx_title, item.title);
        helper.setText(R.id.tx_sub_title, item.subTitle);
        Glide.with(mContext)
                .load(item.imgs[0])
                .placeholder(R.mipmap.ic_loading)
                .error(R.mipmap.ic_load_faile)
                .centerCrop()
                .dontAnimate()
                .into((ImageView) helper.getView(R.id.img_icon));
    }
}
