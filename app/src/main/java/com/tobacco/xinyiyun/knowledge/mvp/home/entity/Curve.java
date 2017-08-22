package com.tobacco.xinyiyun.knowledge.mvp.home.entity;

import com.tobacco.xinyiyun.knowledge.net.entity.Response;

import java.util.List;

/**
 * Created by YangQiang on 2017/8/18.
 */

public class Curve extends Response {

    /**
     * ywid : 5
     * wzbt : K326
     * wzjj : 2017年适用
     * wztp : http://222.219.184.73:9999/jyycs/common/suzimo?ppp=upload/2017-08-01/1501550599798.png
     * twdz : http://222.219.184.73:9999/jyycs/zhibaoshow/hkqxshow?ywid=5
     * dzbz : 0
     * scbz : 0
     * fbrq : null
     */

    public List<DataEntity> data;

    public static class DataEntity {
        public int ywid;
        public String wzbt;
        public String wzjj;
        public String wztp;
        public String twdz;
        public String[] wztps;
        public int dzbz;
        public int scbz;
        public Object fbrq;
    }
}
