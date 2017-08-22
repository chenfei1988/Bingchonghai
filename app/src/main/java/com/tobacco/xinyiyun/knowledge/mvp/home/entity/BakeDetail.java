package com.tobacco.xinyiyun.knowledge.mvp.home.entity;

import com.tobacco.xinyiyun.knowledge.net.entity.Response;

import java.util.List;

/**
 * Created by YangQiang on 2017/8/18.
 */

public class BakeDetail extends Response{


    /**
     * id : 1
     * title : 云烟87  下部叶采收
     * brief : 由于上部叶片的遮萌作用，脚叶和下二棚叶处在湿度大、光照差、通风不良的条件下生长成熟，烟叶含水量较多，干物质积累较少，成熟快、成熟期短。当主脉变白，叶色显现绿黄，就应立即采收。底脚叶起始的2~3片，无烘烤价值，通常不采收，被优化处理掉。
     * readnum : 544
     * csd : 由于上部叶片的遮萌作用，脚叶和下二棚叶处在湿度大、光照差、通风不良的条件下生长成熟，烟叶含水量较多，干物质积累较少，成熟快、成熟期短。当主脉变白，叶色显现绿黄，就应立即采收。底脚叶起始的2~3片，无烘烤价值，通常不采收，被优化处理掉。

     * csbz : 叶色绿黄，叶面2/3茸毛基本脱落，支脉绿白。云烟87黄绿；茎叶角度等于90°，细脉与叶肉同色。叶龄50~60天(移栽后70~80天) 。 根据烟叶的田间长势长相，下部烟叶适当早收，掌握成熟标准宜宽。

     * csff : 首先采烟人员统一成熟标准，确定需要采摘的烟叶，然后以中指和食指托住口十基部，拇指放在柄端上面，向两侧拧压。这样摘下的烟叶，其柄端呈“马蹄形“。注意不要将烟株上的皮撕下，损坏烟茎，影响烟株生长。
     * cszysx : 采收烟叶必须分期进行，每次采烟要求做到“三同”，即同一品种、同一部位、同一成熟度。采烟时间宜在早晨或上午进行，容易识别成熟度。旱天采露水烟，以利保湿变黄。烟叶成熟后，若遇阵雨，可在雨后立即采收，以防返青。若降雨时间过长，出现返青烟，应等其重新落黄后再采收。烟叶采收时，要求不采青、不过熟、不漏采，确保烟叶成熟度整齐度一致；采后不曝日西、不挤压，确保鲜烟叶质量不受损失 。
     * yypz : 云烟-87,
     * yybw : 下部叶,
     * remark : http://222.219.184.73:9999/jyycs/common/suzimo?ppp=upload/2017-05-23/1495551848868.jpg#
     * createid : 1
     * createtime : 2017-05-12 13:14:14
     * updateid : 1
     * updatetime : 2017-05-23 23:04:10
     */

    public List<DataEntity> data;

    public static class DataEntity {
        public int id;
        public String title;
        public String brief;
        public int readnum;
        public String csd;
        public String csbz;
        public String csff;
        public String cszysx;
        public String yypz;
        public String yybw;
        public String remark;
        public int createid;
        public String createtime;
        public int updateid;
        public String updatetime;
        public String syfw;
        public String hkgy;
        public String byzy;
        public String msxx;
        public String csyy;
        public String fzjscs;
    }
}
