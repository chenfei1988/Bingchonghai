package com.tobacco.xinyiyun.knowledge.mvp.home.entity;

import com.tobacco.xinyiyun.knowledge.net.entity.Response;

import java.util.List;

/**
 * Created by YangQiang on 2017/8/18.
 */

public class Bake extends Response {

    /**
     * ywid : 1
     * wzbt : 云烟87  下部叶采收
     * wzjj : 由于上部叶片的遮萌作用，脚叶和下二棚叶处在湿度大、光照差、通风不良的条件下生长成熟，烟叶含水量较多，干物质积累较少，成熟快、成熟期短。当主脉变白，叶色显现绿黄，就应立即采收。底脚叶起始的2~3片，无烘烤价值，通常不采收，被优化处理掉。
     * wztp : http://222.219.184.73:9999/jyycs/common/suzimo?ppp=upload/2017-05-23/1495551848868.jpg#
     * twdz : http://222.219.184.73:9999/jyycs/zhibaoshow/cscsshow?ywid=1
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
