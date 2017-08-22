package com.tobacco.xinyiyun.knowledge.mvp.home.model;

import com.tobacco.xinyiyun.knowledge.net.entity.Response;

import java.util.List;

/**
 * Created by YangQiang on 2017/8/18.
 */

public class academe extends Response {


    /**
     * ywid : 4
     * wzbt : 烟叶专卖管理办法
     * wzjj : 《烟叶专卖管理办法》，1999年5月28日起试行
     * wztp : http://222.219.184.73:9999/jyycs/common/suzimo?ppp=upload/2017-07-14/1500008919942.png
     * twdz : http://222.219.184.73:9999/jyycs/zhibaoshow/jyxyshow?ywid=4
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
        public int dzbz;
        public int scbz;
        public Object fbrq;
        public String[] wztps;
    }
}
