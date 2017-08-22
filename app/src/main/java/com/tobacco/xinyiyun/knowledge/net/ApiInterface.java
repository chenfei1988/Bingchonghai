package com.tobacco.xinyiyun.knowledge.net;


import com.tobacco.xinyiyun.knowledge.entity.Search;
import com.tobacco.xinyiyun.knowledge.mvp.home.entity.Academe;
import com.tobacco.xinyiyun.knowledge.mvp.home.entity.Bake;
import com.tobacco.xinyiyun.knowledge.mvp.home.entity.BakeDetail;
import com.tobacco.xinyiyun.knowledge.mvp.home.entity.Consult;
import com.tobacco.xinyiyun.knowledge.mvp.home.entity.Cultivation;
import com.tobacco.xinyiyun.knowledge.mvp.home.entity.Curve;
import com.tobacco.xinyiyun.knowledge.mvp.home.entity.Pest;
import com.tobacco.xinyiyun.knowledge.mvp.home.entity.PestDetail;
import com.tobacco.xinyiyun.knowledge.mvp.home.entity.Reply;
import com.tobacco.xinyiyun.knowledge.mvp.home.entity.Variety;
import com.tobacco.xinyiyun.knowledge.mvp.home.entity.VarietyDetail;
import com.tobacco.xinyiyun.knowledge.mvp.login.entity.User;
import com.tobacco.xinyiyun.knowledge.net.entity.Response;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by YangQiang on 2017/8/6.
 */

public interface ApiInterface {


    /**
     * 登录
     *
     * @param name
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("portal/apploginInNew")
    Observable<User> login(@Field("nmbbyzk") String name, @Field("nmbzkgcc") String password);


    /**
     * 获取病虫害列表
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("jyycMAppGetData/queryBingChongHai")
    Observable<Pest> getPestList(@FieldMap Map<String, Object> map);


    /**
     * 查询病虫害详情
     *
     * @param ywid
     * @return
     */
    @FormUrlEncoded
    @POST("jyycMAppGetData/queryBingChongHaiOne")
    Observable<PestDetail> queryPestDetail(@Field("ywid") String ywid);


    /**
     * 查询植保技术
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("jyycMAppGetData/queryZaiPeiJiShu")
    Observable<Cultivation> getCultivationList(@FieldMap Map<String, Object> map);


    /**
     * 查询品种资源
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("jyycMAppGetData/queryZhongZhiPinZhong")
    Observable<Variety> getVarietyList(@FieldMap Map<String, Object> map);


    /**
     * 查询品种详情
     *
     * @param ywid
     * @return
     */
    @FormUrlEncoded
    @POST("jyycMAppGetData/queryZhongZhiPinZhongOne")
    Observable<VarietyDetail> queryVarietyDetail(@Field("ywid") String ywid);

    /**
     * 查询金叶学苑
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("jyycMAppGetData/queryJinYeXueYuan")
    Observable<Academe> getAcademeList(@FieldMap Map<String, Object> map);


    /**
     * 查询采收烘烤
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("jyycMAppGetData/queryCaiShouHongKao")
    Observable<Bake> getBakeList(@FieldMap Map<String, Object> map);


    /**
     * 获取采收烘烤详情
     *
     * @return
     */
    @FormUrlEncoded
    @POST("jyycMAppGetData/queryCaiShouHongKaoOne")
    Observable<BakeDetail> getBakeDetail(@Field("wzlx") String wzlx, @Field("ywid") String ywid);


    /**
     * 获取烘烤曲线
     *
     * @return
     */
    @FormUrlEncoded
    @POST("jyycMAppGetData/queryHongKaoQuXian")
    Observable<Curve> getCurveList(@FieldMap Map<String, Object> map);

    /**
     * 获取问题列表
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("jyycMAppPutZhuanJiaQuanZiData/queryZiXunZhuanJiaPage")
    Observable<Consult> getConsultList(@FieldMap Map<String, Object> map);


    /**
     * 提交问题
     *
     * @return
     */
    @POST("jyycMAppPutZhuanJiaQuanZiData/saveZiXunZhuanJia")
    Observable<Response> commitQuestion(@Body RequestBody body);


    /**
     * 获取回答详情
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("jyycMAppPutZhuanJiaQuanZiData/queryZiXunZhuanJiaOne")
    Observable<Reply> getReplyList(@FieldMap Map<String, Object> map);


    /**
     * 回复问题
     *
     * @param content
     * @return
     */
    @FormUrlEncoded
    @POST("jyycMAppPutZhuanJiaQuanZiData/saveZiXunZhuanJiaHuiFu")
    Observable<Response> replyQuestion(@Field("params") String content);


    /**
     * 撤回回复
     *
     * @return
     */
    @FormUrlEncoded
    @POST("jyycMAppPutZhuanJiaQuanZiData/saveZiXunZhuanJiaCheHui")
    Observable<Response> removeQuestion(@Field("ywid") String ywid);


    /**
     * 查询知识点
     *
     * @return
     */
    @FormUrlEncoded
    @POST("jyycMAppGetData/queryCommonWenzhang")
    Observable<Search> searchKnowledge(@Field("context") String context);


    /**
     * 注册账号
     *
     * @param phone
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("portal/saveRegister")
    Observable<Response> register(@Field("nmbbyzk") String phone, @Field("nmbzkgcc") String password);
}

