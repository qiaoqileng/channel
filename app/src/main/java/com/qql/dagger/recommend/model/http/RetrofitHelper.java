package com.qql.dagger.recommend.model.http;


import com.qql.dagger.recommend.BuildConfig;
import com.qql.dagger.recommend.Constants;
import com.qql.dagger.recommend.model.bean.BBBean;
import com.qql.dagger.recommend.model.bean.BannerBean;
import com.qql.dagger.recommend.model.bean.GankItemBean;
import com.qql.dagger.recommend.model.bean.Page;
import com.qql.dagger.recommend.model.bean.Product;
import com.qql.dagger.recommend.model.bean.Type;
import com.qql.dagger.recommend.model.bean.VersionBean;
import com.qql.dagger.recommend.model.entity.User;
import com.qql.dagger.recommend.option.LogIntercept;
import com.qql.dagger.recommend.utils.SystemUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by codeest on 2016/8/3.
 */
public class RetrofitHelper {

    private static OkHttpClient okHttpClient = null;
    private static MyApis myApiService = null;
    private static GankApis gankApiService = null;
    private void init() {
        initOkHttp();
        myApiService = getMyApiService();
        gankApiService = getGankApiService();
    }

    public RetrofitHelper() {
        init();
    }

    private static void initOkHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            // https://drakeet.me/retrofit-2-0-okhttp-3-0-config
            LogIntercept loggingInterceptor = new LogIntercept();
            builder.addInterceptor(loggingInterceptor);
        }
        // http://www.jianshu.com/p/93153b34310e
        File cacheFile = new File(Constants.PATH_CACHE);
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!SystemUtil.isNetworkConnected()) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (SystemUtil.isNetworkConnected()) {
                    int maxAge = 0;
                    // 有网络时, 不缓存, 最大保存时长为0
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                return response;
            }
        };
        //设置缓存
        builder.addNetworkInterceptor(cacheInterceptor);
        builder.addInterceptor(cacheInterceptor);
        builder.cache(cache);
        //设置超时
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        okHttpClient = builder.build();
    }

    private static MyApis getMyApiService() {
        Retrofit myRetrofit = new Retrofit.Builder()
                .baseUrl(MyApis.HOST)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return myRetrofit.create(MyApis.class);
    }

    public Observable<GankHttpResponse<String>> joinUs(Map<String,String> params) {
        return myApiService.joinUs(params);
    }

    public Observable<GankHttpResponse<User>> login(Map<String,String> params) {
        return myApiService.login(params);
    }

    private static GankApis getGankApiService() {
        Retrofit gankRetrofit = new Retrofit.Builder()
                .baseUrl(GankApis.HOST)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return gankRetrofit.create(GankApis.class);
    }

    public Observable<GankHttpResponse<List<GankItemBean>>> fetchGirlList(int num, int page) {
        return gankApiService.getGirlList(num, page);
    }
    public Observable<GankHttpResponse<VersionBean>> fetchVersionInfo() {
        return myApiService.getVersionInfo();
    }

    public Observable<GankHttpResponse<List<BannerBean>>> getBanners(){
        //TODO 这里后台写接口返回，先上测试数据
        ArrayList<BannerBean> banners = new ArrayList<BannerBean>();
        banners.add(new BannerBean(0,"http://img2.3lian.com/2014/f2/37/d/40.jpg"));
        banners.add(new BannerBean(0,"http://img2.3lian.com/2014/f2/37/d/39.jpg"));
        banners.add(new BannerBean(0,"http://img4.imgtn.bdimg.com/it/u=958691974,197794884&fm=23&gp=0.jpg"));
        banners.add(new BannerBean(0,"http://f.hiphotos.baidu.com/image/h%3D200/sign=1478eb74d5a20cf45990f9df460b4b0c/d058ccbf6c81800a5422e5fdb43533fa838b4779.jpg"));
        banners.add(new BannerBean(0,"http://f.hiphotos.baidu.com/image/pic/item/09fa513d269759ee50f1971ab6fb43166c22dfba.jpg"));
        GankHttpResponse<List<BannerBean>> response = new GankHttpResponse<List<BannerBean>>();
        response.setResults(banners);
        response.setError(false);
        Observable<GankHttpResponse<List<BannerBean>>> responseObservable = Observable.just(response).flatMap(new Func1<GankHttpResponse<List<BannerBean>>, Observable<GankHttpResponse<List<BannerBean>>>>() {
            @Override
            public Observable<GankHttpResponse<List<BannerBean>>> call(GankHttpResponse<List<BannerBean>> listMyHttpResponse) {
                return Observable.just(listMyHttpResponse);
            }
        });
        return responseObservable;
    }

    public Observable<GankHttpResponse<List<Type>>> getCategories(){
        return myApiService.typeList();
    }

    public Observable<GankHttpResponse<Page<Product>>> getProducts(Map<String,String> params){
        return myApiService.productList(params);
    }

}
