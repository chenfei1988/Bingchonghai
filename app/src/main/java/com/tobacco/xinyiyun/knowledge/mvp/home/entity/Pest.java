package com.tobacco.xinyiyun.knowledge.mvp.home.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.tobacco.xinyiyun.knowledge.net.entity.Response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by YangQiang on 2017/8/16.
 */

public class Pest extends Response {

    public List<DataEntity> data;

    public static class DataEntity implements Serializable, MultiItemEntity {
        public int ywid;
        public String wzbt;
        public String wzjj;
        public String wztp;
        public String twdz;
        public String[] wztps;
        public int dzbz;
        public int scbz;
        public int itemType;

        @Override
        public int getItemType() {
            return itemType;
        }
    }
}
