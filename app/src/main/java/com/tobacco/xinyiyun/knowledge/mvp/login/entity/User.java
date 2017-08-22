package com.tobacco.xinyiyun.knowledge.mvp.login.entity;

import com.google.gson.annotations.SerializedName;
import com.tobacco.xinyiyun.knowledge.net.entity.Response;

/**
 * Created by YangQiang on 2017/8/16.
 */

public class User extends Response {


    /**
     * success : true
     * msg : 验证用户信息成功
     * baseurl : http://222.219.184.73:9999/jyycs/
     * qxcode : ,11,21,31,32,41,51,71,72
     * orgname : 楚雄州
     * user : {"userid":2249,"username":"市领导","userpassword":"7c4a8d09ca3762af61e59520943dc26494f8941b","usertel":"sld001","userremark":"","userflag":10,"usesystem":3,"systime":"2017-03-15 17:26:35","usertx":"http://222.219.184.73:9999/jyycs/common/suzimo?ppp=upload/2017-08-16/touxiang/1502853242162-23.3038807933996.jpg","usersex":"男","userbrith":"2016-07-16 10:00:00","orgid":106,"ywid":null,"isenable":1,"gzbm":null,"gzzw":"经理"}
     * yannong : null
     * yanjiyuan : null
     * zuzhi : {"id":106,"orgname":"楚雄州","orgcode":"5002","orgplace":"楚雄彝族自治州","orgpid":1,"orgtype":2,"createid":1,"createtime":"2017-03-08 11:56:04","orgserver":"http://222.219.184.73:9999/jyycs/"}
     * yanzhan : null
     * wuzidians : null
     * khdata : null
     * qxdata : [{"qxcode":"1","qximg":"http://222.219.184.73:9999/jyycs/images/menu/amenu/1.png","isbt":0},{"qxcode":"2","qximg":"http://222.219.184.73:9999/jyycs/images/menu/amenu/2.png","isbt":0},{"qxcode":"3","qximg":"http://222.219.184.73:9999/jyycs/images/menu/amenu/3.png","isbt":0},{"qxcode":"4","qximg":"http://222.219.184.73:9999/jyycs/images/menu/amenu/4.png","isbt":0},{"qxcode":"5","qximg":"http://222.219.184.73:9999/jyycs/images/menu/amenu/5.png","isbt":0},{"qxcode":"7","qximg":"http://222.219.184.73:9999/jyycs/images/menu/amenu/7.png","isbt":0},{"qxcode":"8","qximg":"http://222.219.184.73:9999/jyycs/images/menu/amenu/8.png","isbt":0}]
     */

    public String baseurl;
    public String orgname;
    /**
     * userid : 2249
     * username : 市领导
     * userpassword : 7c4a8d09ca3762af61e59520943dc26494f8941b
     * usertel : sld001
     * userremark :
     * userflag : 10
     * usesystem : 3
     * systime : 2017-03-15 17:26:35
     * usertx : http://222.219.184.73:9999/jyycs/common/suzimo?ppp=upload/2017-08-16/touxiang/1502853242162-23.3038807933996.jpg
     * usersex : 男
     * userbrith : 2016-07-16 10:00:00
     * orgid : 106
     * ywid : null
     * isenable : 1
     * gzbm : null
     * gzzw : 经理
     */

    public String name;
    public String password;
    public UserEntity user;

    public static class UserEntity {
        @SerializedName("userid")
        public int id;
        @SerializedName("username")
        public String name;
        @SerializedName("userpassword")
        public String password;
        @SerializedName("usertel")
        public String tel;
        @SerializedName("userremark")
        public String remark;
        @SerializedName("userflag")
        public int flag;
        @SerializedName("usesystem")
        public int ystem;
        public String systime;
        @SerializedName("usertx")
        public String tx;
        @SerializedName("usersex")
        public String sex;
        @SerializedName("userbrith")
        public String brith;
        public int orgid;
        public int isenable;
        public String gzzw;
    }
}
