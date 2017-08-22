package com.tobacco.xinyiyun.knowledge.net;

import android.util.Log;

import com.tobacco.xinyiyun.knowledge.BuildConfig;
import com.tobacco.xinyiyun.knowledge.base.BaseApplication;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by YangQiang on 2017/8/6.
 */

public class ApiService {

    private static ApiInterface mApiInterface;
    private static OkHttpClient mOkHttpClient;

    private ApiService() {

    }

    public static ApiInterface get() {
        if (mApiInterface == null) {
            Retrofit mRetrofit = new Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
                    .client(getOkHttpClient())
                    .addCallAdapterFactory(
                            RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mApiInterface = mRetrofit.create(ApiInterface.class);
        }
        return mApiInterface;
    }


    public static void setBaseUrl(String url) {
        Retrofit mRetrofit = new Retrofit.Builder().baseUrl(url)
                .client(getOkHttpClient())
                .addCallAdapterFactory(
                        RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApiInterface = mRetrofit.create(ApiInterface.class);
    }

    public static OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            OkHttpClient.Builder mBuilder = new OkHttpClient.Builder();
            HttpLoggingInterceptor mHttpLoginInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.d(BaseApplication.TAG, message);
                }
            });
            mHttpLoginInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            mBuilder.retryOnConnectionFailure(true)
                    .addInterceptor(mHttpLoginInterceptor)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS);
            mOkHttpClient = mBuilder.build();
        }
        return mOkHttpClient;
    }

}
