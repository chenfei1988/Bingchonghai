package com.tobacco.xinyiyun.knowledge.net.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by YangQiang on 16-11-21.
 */

public class Response {

    /**
     * 请求是否成功
     */
    @SerializedName("success")
    public boolean success;

    /**
     * 服务器返回的请求结果通知信息
     */
    @SerializedName("msg")
    public String message;

}
