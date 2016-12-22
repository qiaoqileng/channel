package com.qql.dagger.recommend.model.http;

import com.qql.dagger.recommend.model.bean.VersionBean;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by qiao on 2016/12/1.
 */

public interface MyApis {

    String HOST = "http://codeest.me/api/geeknews/";

    String APK_DOWNLOAD_URL = "http://codeest.me/apk/geeknews.apk";

    /**
     * 获取最新版本信息
     * @return
     */
    @GET("version")
    Observable<MyHttpResponse<VersionBean>> getVersionInfo();
}
