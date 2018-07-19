package com.qql.dagger.recommend.model.http;

import com.qql.dagger.recommend.model.bean.Page;
import com.qql.dagger.recommend.model.bean.Product;
import com.qql.dagger.recommend.model.bean.Type;
import com.qql.dagger.recommend.model.bean.VersionBean;
import com.qql.dagger.recommend.model.entity.User;

import java.util.List;
import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by qiao on 2016/12/1.
 */

public interface MyApis {

    String HOST = "http://192.168.4.110:8080/";

    /**
     * 获取最新版本信息
     * @return
     */
    @GET("version")
    Observable<GankHttpResponse<VersionBean>> getVersionInfo();

    @POST("lifting/user/submit")
    Observable<GankHttpResponse<String>> joinUs(@QueryMap Map<String,String> params);
    @FormUrlEncoded
    @POST("lifting/user/login")
    Observable<GankHttpResponse<User>> login(@FieldMap Map<String, String> params);
    @FormUrlEncoded
    @GET("lifting/product/list")
    Observable<GankHttpResponse<Page<Product>>> productList(@FieldMap Map<String, String> params);
    @GET("lifting/product/typeList")
    Observable<GankHttpResponse<List<Type>>> typeList();
}

