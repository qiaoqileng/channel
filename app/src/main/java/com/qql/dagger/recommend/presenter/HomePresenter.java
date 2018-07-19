package com.qql.dagger.recommend.presenter;

import com.qql.dagger.recommend.base.RxPresenter;
import com.qql.dagger.recommend.cache.DataCache;
import com.qql.dagger.recommend.model.bean.BannerBean;
import com.qql.dagger.recommend.model.bean.Type;
import com.qql.dagger.recommend.model.http.GankHttpResponse;
import com.qql.dagger.recommend.model.http.RetrofitHelper;
import com.qql.dagger.recommend.presenter.contract.HomeContract;
import com.qql.dagger.recommend.utils.RxUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by qql on 2016/12/22.
 */

public class HomePresenter extends RxPresenter<HomeContract.View> implements HomeContract.Presenter {

    private final RetrofitHelper mRetrofitHelper;
    private final DataCache dataCache;
    @Inject
    public HomePresenter(RetrofitHelper mRetrofitHelper,DataCache dataCache) {
        this.mRetrofitHelper = mRetrofitHelper;
        this.dataCache = dataCache;
    }

    @Override
    public void getDailyBanners() {
        Subscription rxSubscription = mRetrofitHelper.getBanners()
                .compose(RxUtil.<GankHttpResponse<List<BannerBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<BannerBean>>handleResult())
                .subscribe(new Action1<List<BannerBean>>() {
                    @Override
                    public void call(List<BannerBean> gankItemBeen) {
                        mView.showDailyBanners(gankItemBeen);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError("加载更多数据失败ヽ(≧Д≦)ノ");
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void getCategory() {
        if (dataCache.isCategoryEmpty()) {
            Subscription rxSubscription = mRetrofitHelper.getCategories()
                    .compose(RxUtil.<GankHttpResponse<List<Type>>>rxSchedulerHelper())
                    .compose(RxUtil.<List<Type>>handleResult())
                    .subscribe(new Action1<List<Type>>() {
                        @Override
                        public void call(List<Type> gankItemBeen) {
                            dataCache.setCategories(gankItemBeen);
                            mView.showCategory(gankItemBeen);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            mView.showError("加载更多数据失败ヽ(≧Д≦)ノ");
                        }
                    });
            addSubscrebe(rxSubscription);
        } else {
            mView.showCategory(dataCache.getCategories());
        }
    }
}
