package com.qql.dagger.recommend.model.http;

import com.qql.dagger.recommend.model.bean.VersionBean;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by qiao on 2016/12/1.
 */

public interface MyApis {

    String HOST = "http://192.168.0.188:8080/";

    /**
     * 获取最新版本信息
     * @return
     */
    @GET("version")
    Observable<GankHttpResponse<VersionBean>> getVersionInfo();

    @POST("lifting/user/submit")
    Observable<GankHttpResponse<String>> joinUs(@QueryMap Map<String,String> params);
}
