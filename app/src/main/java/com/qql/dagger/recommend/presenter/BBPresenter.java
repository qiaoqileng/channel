package com.qql.dagger.recommend.presenter;

import com.qql.dagger.recommend.App;
import com.qql.dagger.recommend.base.RxPresenter;
import com.qql.dagger.recommend.cache.DataCache;
import com.qql.dagger.recommend.model.bean.BBBean;
import com.qql.dagger.recommend.model.bean.BannerBean;
import com.qql.dagger.recommend.model.bean.CategoryBean;
import com.qql.dagger.recommend.model.http.GankHttpResponse;
import com.qql.dagger.recommend.model.http.RetrofitHelper;
import com.qql.dagger.recommend.presenter.contract.BBListContract;
import com.qql.dagger.recommend.utils.RxUtil;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by T-46 on 2017/4/6.
 */

public class BBPresenter extends RxPresenter<BBListContract.View> implements BBListContract.Presenter {
    private final RetrofitHelper mRetrofitHelper;
    private final DataCache dataCache;
    @Inject
    public BBPresenter(RetrofitHelper mRetrofitHelper,DataCache dataCache) {
        this.mRetrofitHelper = mRetrofitHelper;
        this.dataCache = dataCache;
    }

    @Override
    public void findBB(Map<String, String> params) {
        Subscription rxSubscription = mRetrofitHelper.getBBList(params)
                .compose(RxUtil.<GankHttpResponse<List<BBBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<BBBean>>handleResult())
                .subscribe(new Action1<List<BBBean>>() {
                    @Override
                    public void call(List<BBBean> gankItemBeen) {
                        mView.showBBList(gankItemBeen);
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
    public void getCategories() {
        if (dataCache.isCategoryEmpty()) {
            Subscription rxSubscription = mRetrofitHelper.getCategories()
                    .compose(RxUtil.<GankHttpResponse<List<CategoryBean>>>rxSchedulerHelper())
                    .compose(RxUtil.<List<CategoryBean>>handleResult())
                    .subscribe(new Action1<List<CategoryBean>>() {
                        @Override
                        public void call(List<CategoryBean> gankItemBeen) {
                            dataCache.setCategories(gankItemBeen);
                            mView.showCategories(gankItemBeen);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            mView.showError("加载更多数据失败ヽ(≧Д≦)ノ");
                        }
                    });
            addSubscrebe(rxSubscription);
        } else {
            mView.showCategories(dataCache.getCategories());
        }
    }
}
