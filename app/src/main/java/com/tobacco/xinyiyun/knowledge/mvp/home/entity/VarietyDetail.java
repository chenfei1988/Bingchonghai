package com.tobacco.xinyiyun.knowledge.mvp.home.entity;

import com.tobacco.xinyiyun.knowledge.net.entity.Response;

import java.util.List;

/**
 * Created by YangQiang on 2017/8/18.
 */

public class VarietyDetail extends Response {

    /**
     * id : 4
     * title : K326
     * brief : 株式筒形或塔形，打顶株高90～110厘米，节距4～4.6厘米，茎围7～8.9厘米。腰叶长椭圆形，叶面较皱，叶缘波浪状，主脉较细，茎叶角度大，叶片厚度中等。移栽至中心花开放65天左右，大田生育期120天左右。田间生长整齐，腋芽生长势强。一般亩产量150～175kg，原烟桔黄色，油分多，光泽强，富弹性，叶片结构疏松，身份适中。
     * readnum : 340
     * tztx : 株式筒形或塔形，打顶株高90～110厘米，节距4～4.6厘米，茎围7～8.9厘米。腰叶长椭圆形，叶面较皱，叶缘波浪状，主脉较细，茎叶角度大，叶片厚度中等。移栽至中心花开放65天左右，大田生育期120天左右。田间生长整齐，腋芽生长势强。一般亩产量150～175kg，原烟桔黄色，油分多，光泽强，富弹性，叶片结构疏松，身份适中。
     * kbx : 抗黑胫病，中抗青枯病、南方根结线虫病和北方根结线虫病，抗爪哇根结线虫病，感野火病、普通花叶病、赤星病和气候性斑点病。
     * zpjsyd : K326耐肥力较强，宜在中等以上肥力的地块种植，亩施纯氮6～8kg，氮磷钾比例为1:1.5：2.5～3。每亩1100株左右，单株留叶20～22片。现蕾时打顶，苗期要注意防治普通花叶病，整个生育期要注意防治野火病，后期要注意防治赤星病。
     * hkjsyd :  K326分层落黄好，下部叶适熟采收，中部叶成熟采收，上部叶充分成熟采收。下二棚叶片大而薄，含水量多、烟筋粗，容易产生枯烟。中上部叶易烘烤，一般变黄期温度32～42℃，时间50～60小时，定色期43～55℃，30～40小时。干筋期温度不超过68℃，时间25～30小时，顶叶各阶段可适当延长，注意升温不过急。
     * remark : http://222.219.184.73:9999/jyycs/common/suzimo?ppp=upload/2017-05-23/1495551724048.jpg#http://222.219.184.73:9999/jyycs/common/suzimo?ppp=upload/2017-05-23/1495551804437.jpg#
     * createid : 1
     * createtime : 2017-05-18 14:00:47
     * updateid : 1
     * updatetime : 2017-05-23 23:03:26
     */

    public List<DataEntity> data;

    public static class DataEntity {
        public int id;
        public String title;
        public String brief;
        public int readnum;
        public String tztx;
        public String kbx;
        public String zpjsyd;
        public String hkjsyd;
        public String remark;
        public int createid;
        public String createtime;
        public int updateid;
        public String updatetime;
    }
}
