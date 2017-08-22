package com.tobacco.xinyiyun.knowledge.mvp.home.entity;

import com.tobacco.xinyiyun.knowledge.net.entity.Response;

import java.util.List;

/**
 * Created by YangQiang on 2017/8/18.
 */

public class Variety extends Response {


    /**
     * ywid : 4
     * wzbt : K326
     * wzjj : 株式筒形或塔形，打顶株高90～110厘米，节距4～4.6厘米，茎围7～8.9厘米。腰叶长椭圆形，叶面较皱，叶缘波浪状，主脉较细，茎叶角度大，叶片厚度中等。移栽至中心花开放65天左右，大田生育期120天左右。田间生长整齐，腋芽生长势强。一般亩产量150～175kg，原烟桔黄色，油分多，光泽强，富弹性，叶片结构疏松，身份适中。
     * wztp : http://222.219.184.73:9999/jyycs/common/suzimo?ppp=upload/2017-05-23/1495551724048.jpg#http://222.219.184.73:9999/jyycs/common/suzimo?ppp=upload/2017-05-23/1495551804437.jpg#
     * twdz : http://222.219.184.73:9999/jyycs/zhibaoshow/zzpzshow?ywid=4
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
