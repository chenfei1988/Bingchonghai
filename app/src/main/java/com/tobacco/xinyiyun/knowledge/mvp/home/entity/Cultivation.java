package com.tobacco.xinyiyun.knowledge.mvp.home.entity;


import com.tobacco.xinyiyun.knowledge.net.entity.Response;

import java.util.List;

/**
 * Created by YangQiang on 2017/8/17.
 */

public class Cultivation extends Response {


    /**
     * ywid : 15
     * wzbt : 区域选择
     * wzjj : 区域选择要以环境保护为基础。植烟环境保护是指以烟叶生产为主的自然环境保护，主要包括植烟土壤养护与综合利用、灌溉水保护、空气污染物管理等。植烟环境保护，不仅是烟草与自然和谐发展的需要，更是农业可持续发展的需要。
     * wztp : http://222.219.184.73:9999/jyycs/common/suzimo?ppp=upload/2017-05-23/1495554545728.png
     * twdz : http://222.219.184.73:9999/jyycs/zhibaoshow/zpsjshow?ywid=15
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
