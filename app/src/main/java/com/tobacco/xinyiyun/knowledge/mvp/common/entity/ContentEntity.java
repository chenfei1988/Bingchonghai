package com.tobacco.xinyiyun.knowledge.mvp.common.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * Created by YangQiang on 2017/8/17.
 */

public class ContentEntity implements MultiItemEntity,Serializable {
    public String[] imgs;
    public String title;
    public String subTitle;
    public String ywid;
    public String url;
    public String arg;

    @Override
    public int getItemType() {
        if (imgs.length <= 1) {
            return 1;
        } else if (imgs.length == 2) {
            return 2;
        } else {
            return 3;
        }
    }
}
