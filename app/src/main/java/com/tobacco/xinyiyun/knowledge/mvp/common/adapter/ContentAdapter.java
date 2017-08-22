package com.tobacco.xinyiyun.knowledge.mvp.common.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tobacco.xinyiyun.knowledge.R;
import com.tobacco.xinyiyun.knowledge.mvp.common.entity.ContentEntity;

import java.util.List;

/**
 * Created by YangQiang on 2017/8/17.
 */

public class ContentAdapter extends BaseMultiItemQuickAdapter<ContentEntity, BaseViewHolder> {
    Fragment mFragment;

    public ContentAdapter(@Nullable final List<ContentEntity> data, Fragment fragment) {
        super(data);
        addItemType(1, R.layout.view_kdge_list_item_1);
        addItemType(2, R.layout.view_kdge_list_item_2);
        addItemType(3, R.layout.view_kdge_list_item_3);
        this.mFragment = fragment;
//        setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
//                return position % 3 == 0 ? 2 : 1;
//            }
//        });
    }


    @Override
    protected void convert(BaseViewHolder helper, ContentEntity item) {
        helper.setText(R.id.tx_title, item.title);
        helper.setText(R.id.tx_sub_title, item.subTitle);
        ImageView mImg1 = helper.getView(R.id.img_icon1);
        ImageView mImg2 = helper.getView(R.id.img_icon2);
        ImageView mImg3 = helper.getView(R.id.img_icon3);
        ImageView[] mImages;
        if (item.getItemType() == 1) {
            mImages = new ImageView[]{mImg1};
        } else if (item.getItemType() == 2) {
            mImages = new ImageView[]{mImg1, mImg2};
        } else {
            mImages = new ImageView[]{mImg1, mImg2, mImg3};
        }

        for (int i = 0; i < mImages.length; i++) {
            Glide.with(mFragment)
                    .load(item.imgs[i])
                    .asBitmap()
                    .placeholder(R.mipmap.ic_loading)
                    .error(R.mipmap.ic_load_faile)
                    .centerCrop()
                    .dontAnimate()
                    .into(mImages[i]);
        }

    }

}
