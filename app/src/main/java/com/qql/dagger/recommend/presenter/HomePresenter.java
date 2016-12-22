package com.qql.dagger.recommend.presenter;

import com.qql.dagger.recommend.base.RxPresenter;
import com.qql.dagger.recommend.model.http.RetrofitHelper;
import com.qql.dagger.recommend.presenter.contract.HomeContract;

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

    }
}
