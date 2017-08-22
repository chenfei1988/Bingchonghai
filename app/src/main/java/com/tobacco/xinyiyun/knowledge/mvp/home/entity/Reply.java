package com.tobacco.xinyiyun.knowledge.mvp.home.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.tobacco.xinyiyun.knowledge.net.entity.Response;

import java.util.List;

/**
 * Created by YangQiang on 2017/8/21.
 */

public class Reply extends Response {


    /**
     * ywid : 141
     * qzfbr : 市领导(市级领导)
     * qzfbrtx : http://222.219.184.73:9999/jyycs/common/suzimo?ppp=http://222.219.184.73:9999/jyycs/common/suzimo?ppp=http://222.219.184.73:9999/jyycs/common/suzimo?ppp=upload/2017-08-16/touxiang/1502853242162-23.3038807933996.jpg
     * qzdd :
     * qznr : 哈哈斤斤计较
     * qztp :
     * qzsj : 2017年08月20日13:46
     * dznum : null
     * scnum : null
     * fxnum : null
     * plnum : null
     * dzbz : null
     * scbz : null
     */

    public List<MdataEntity> mdata;
    /**
     * hfrid : 2249
     * hfrflag : 10
     * hfrxm : 市领导(市级领导)
     * hfrtx : http://222.219.184.73:9999/jyycs/common/suzimo?ppp=http://222.219.184.73:9999/jyycs/common/suzimo?ppp=http://222.219.184.73:9999/jyycs/common/suzimo?ppp=upload/2017-08-16/touxiang/1502853242162-23.3038807933996.jpg
     * hfsj : 2017年08月21日09:51
     * wtlx :
     * hfnr : 哈哈哈
     * qhfrid : 0
     * qhfrxm : (注册用户)
     */

    public List<HdataEntity> hdata;

    public static class MdataEntity {
        public int ywid;
        public String qzfbr;
        public String qzfbrtx;
        public String qzdd;
        public String qznr;
        public String qztp;
        public String qzsj;
        public Object dznum;
        public Object scnum;
        public Object fxnum;
        public Object plnum;
        public Object dzbz;
        public Object scbz;
    }

    public static class HdataEntity implements MultiItemEntity {

        public HdataEntity() {
        }

        public HdataEntity(int type) {
            this.itemType = type;
        }

        public int hfrid;
        public int hfrflag;
        public String hfrxm;
        public String hfrtx;
        public String hfsj;
        public String wtlx;
        public String hfnr;
        public int qhfrid;
        public String qhfrxm;
        public int itemType;

        @Override
        public int getItemType() {
            return itemType;
        }
    }
}
