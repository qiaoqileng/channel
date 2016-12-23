package com.qql.dagger.recommend.presenter;

import com.qql.dagger.recommend.base.RxPresenter;
import com.qql.dagger.recommend.model.bean.BannerBean;
import com.qql.dagger.recommend.model.http.RetrofitHelper;
import com.qql.dagger.recommend.presenter.contract.HomeContract;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by qql on 2016/12/22.
 */

public class HomePresenter extends RxPresenter<HomeContract.View> implements HomeContract.Presenter {

    private final RetrofitHelper mRetrofitHelper;

    @Inject
    public HomePresenter(RetrofitHelper mRetrofitHelper) {
        this.mRetrofitHelper = mRetrofitHelper;
    }

    @Override
    public void getDailyBanners() {
        mView.showDailyBanners(initTestDatas());
    }

    private ArrayList<BannerBean> initTestDatas() {
        ArrayList<BannerBean> banners = new ArrayList<BannerBean>();
        banners.add(new BannerBean(0,"http://img2.3lian.com/2014/f2/37/d/40.jpg"));
        banners.add(new BannerBean(0,"http://img2.3lian.com/2014/f2/37/d/39.jpg"));
        banners.add(new BannerBean(0,"http://www.8kmm.com/UploadFiles/2012/8/201208140920132659.jpg"));
        banners.add(new BannerBean(0,"http://f.hiphotos.baidu.com/image/h%3D200/sign=1478eb74d5a20cf45990f9df460b4b0c/d058ccbf6c81800a5422e5fdb43533fa838b4779.jpg"));
        banners.add(new BannerBean(0,"http://f.hiphotos.baidu.com/image/pic/item/09fa513d269759ee50f1971ab6fb43166c22dfba.jpg"));
        return banners;
    }
}
