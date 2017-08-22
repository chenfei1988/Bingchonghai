package com.tobacco.xinyiyun.knowledge.entity;

import com.tobacco.xinyiyun.knowledge.net.entity.Response;

import java.util.List;

/**
 * Created by YangQiang on 2017/8/21.
 */

public class Search extends Response{

    public List<DataEntity> data;

    public static class DataEntity {
        public int ywid;
        public int ywlx;
        public String wzbt;
        public String twdz;
    }
}
