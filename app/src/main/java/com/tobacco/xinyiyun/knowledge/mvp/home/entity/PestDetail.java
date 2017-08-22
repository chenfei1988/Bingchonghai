package com.tobacco.xinyiyun.knowledge.mvp.home.entity;

import com.tobacco.xinyiyun.knowledge.net.entity.Response;

import java.util.List;

/**
 * Created by YangQiang on 2017/8/17.
 */

public class PestDetail extends Response {


    /**
     * id : 26
     * bchbt : 烟草普通花叶病
     * bchlx : 1
     * bhms : 中国各烟区普遍发生，是烟草主要病毒病之一。在烟草栽培中最常见的病毒病，此病为全生育期病害，以大田期发病较重。
     * bhyl :
     * fsgl : （1）气温偏高，干旱少雨的天气发病重。
     * （2）晚栽的比早栽的烟田发病重。
     * （3）连作田块发病重。
     * （4）管理不当、土层薄，施肥不足的田块发病重。
     * （5）此病害主要是接触传染，大量传播主要是人们的田间农事操作。
     * <p>
     * whzz : 最初出现明脉，对光照射似有透明感。接着出现黄绿相间症状，称花叶。严重时叶片畸形，厚薄不匀，叶缘紧缩向下翻卷，叶面凸凹不平呈“耳突状”，上部新叶抽出后还产生枯死斑，称为花叶灼斑；较早发病植株明显矮化。
     * fzff : （1）种植抗耐病品种。
     * （2）培育无病壮苗。
     * （3）每2-3 年实行一次轮作。
     * （4）保持田间操作卫生，先操作健株，后操作病株，并及时用肥皂洗手，不在田间吸烟。
     * （5）适时早播、早栽。
     * （6）药剂防治：用2%宁南霉素水剂150-250倍液或8%宁南霉素水剂宁南霉素水剂1200-1600倍液叶面喷雾；也可用20%吗胍?乙酸铜可湿性粉剂800-1200倍液叶面喷雾，7-10天一次，连续4次。烟草病毒病防治用药及施用方法见表1
     * 表1 烟草病毒病防治药剂用量与施用方法
     * 产品名称	常用量(g/亩)
     * 稀释倍数	最高用量 (g/亩)
     * 稀释倍数	施药方法	最多使
     * 用次数	安全间隔期(天)
     * 2%宁南霉素水剂	250×	150×	喷雾	4	7～10
     * 8%宁南霉素水剂	1600×	1200×	喷雾	4	7～10
     * 20%吗胍?乙酸铜可湿性粉剂	1200×	800×	喷雾	4	7～10
     * 24%混脂?硫酸铜水乳剂	900×	600×	喷雾	4	7～10
     * 8%混脂?硫酸铜水乳剂	1000×	500×	喷雾	4	7～10
     * 0.5%香菇多糖水剂	500×	300×	喷雾	4	7～10
     * 20%盐酸吗啉胍可湿性粉剂	400×	300×	喷雾	4	7～10
     * 6%烯?羟?硫酸铜可湿性粉剂	400×	300×	喷雾	4	7～10
     * 20%吗胍?乙酸铜可湿性粉剂	1200×	800×	喷雾	4	7～10
     * 20%吗胍?乙酸铜可溶性粉剂	1200×	800×	喷雾	4	7～10
     * 0.5%氨基寡糖素水剂	600×	400×	喷雾	4	7～10
     * 2%氨基寡糖素水剂	600×	500×	喷雾	4	7～10
     * 0.5%氨基寡糖素水剂	600×	400×	喷雾	4	7～10
     * 20%盐酸吗啉呱可湿性粉剂	600×	400×	喷雾	4	7～10
     * <p>
     * ssszzq : 团棵期,旺长期,成熟期,
     * ssbw : 叶部,
     * remark : http://222.219.184.73:9999/jyycs/common/suzimo?ppp=upload/2017-01-22/1485050044734.jpg#http://222.219.184.73:9999/jyycs/common/suzimo?ppp=upload/2017-01-22/1485050046684.jpg#http://222.219.184.73:9999/jyycs/common/suzimo?ppp=upload/2017-01-22/1485050048775.jpg#http://222.219.184.73:9999/jyycs/common/suzimo?ppp=upload/2017-01-22/1485050050584.jpg#http://222.219.184.73:9999/jyycs/common/suzimo?ppp=upload/2017-01-22/1485050054562.bmp#
     * createid : 1
     * createtime : 2017-01-22 09:54:16
     * updateid : 1
     * updatetime : 2017-01-22 09:54:16
     * readnum : 563
     */

    public List<DataEntity> data;

    public static class DataEntity {
        public int id;
        public String bchbt;
        public int bchlx;
        public String bhms;
        public String bhyl;
        public String fsgl;
        public String whzz;
        public String fzff;
        public String ssszzq;
        public String ssbw;
        public String remark;
        public int createid;
        public String createtime;
        public int updateid;
        public String updatetime;
        public int readnum;
    }
}
