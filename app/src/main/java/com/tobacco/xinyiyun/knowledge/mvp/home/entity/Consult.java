package com.tobacco.xinyiyun.knowledge.mvp.home.entity;

import com.tobacco.xinyiyun.knowledge.net.entity.Response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by YangQiang on 2017/8/19.
 */

public class Consult extends Response {

    /**
     * ywid : 64
     * xxid : 178
     * hfnr : sfg
     */

    public List<XxdataEntity> xxdata;
    /**
     * ywid : 125
     * qzfbr : 市领导(市级领导)
     * qzfbrtx : http://222.219.184.73:9999/jyycs/common/suzimo?ppp=http://222.219.184.73:9999/jyycs/common/suzimo?ppp=http://222.219.184.73:9999/jyycs/common/suzimo?ppp=upload/2017-08-16/touxiang/1502853242162-23.3038807933996.jpg
     * qzdd :
     * qznr : 和你你看嘛
     * qztp :
     * qzsj : 2017年08月19日12:58
     * dznum : null
     * scnum : null
     * fxnum : null
     * plnum : null
     * dzbz : null
     * scbz : null
     */

    public List<DataEntity> data;

    /**
     * ywid : 75
     * qzfbr : 烟科院烟农(烟农)
     * qzfbrtx : http://127.0.0.1:8080/jyycs/common/suzimo?ppp=upload/touxiang/1500280956552-43.336419768983106.jpg
     * qzdd :
     * qznr : 小粉测试问题
     * qztp : http://222.219.184.73:9999/jyycs/common/suzimo?ppp=upload/zxzj/1500382314970-94.56572030997904.jpeg#
     * qzsj : 2017年07月18日20:51
     */


    public static class XxdataEntity {
        public int ywid;
        public int xxid;
        public String hfnr;
    }


    public static class DataEntity implements Serializable {
        public int ywid;
        public String qzfbr;
        public String qzfbrtx;
        public String qzdd;
        public String qznr;
        public String qztp;
        public String[] qztps;
        public String qzsj;
        public Object dznum;
        public Object scnum;
        public Object fxnum;
        public Object plnum;
        public Object dzbz;
        public Object scbz;
    }
}
