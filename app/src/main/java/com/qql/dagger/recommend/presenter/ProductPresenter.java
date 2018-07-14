package com.qql.dagger.recommend.presenter;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.qql.dagger.recommend.base.RxPresenter;
import com.qql.dagger.recommend.cache.DataCache;
import com.qql.dagger.recommend.model.entity.User;
import com.qql.dagger.recommend.model.http.RetrofitHelper;
import com.qql.dagger.recommend.presenter.contract.MyContract;
import com.qql.dagger.recommend.presenter.contract.ProductContract;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;

import java.util.Map;

import javax.inject.Inject;

/**
 * Created by qql on 2016/12/22.
 */

public class ProductPresenter extends RxPresenter<ProductContract.View> implements ProductContract.Presenter {

    private final RetrofitHelper mRetrofitHelper;
    private final DataCache dataCache;

    @Inject
    public ProductPresenter(RetrofitHelper mRetrofitHelper, DataCache dataCache, Tencent tencent) {
        this.mRetrofitHelper = mRetrofitHelper;
        this.dataCache = dataCache;
    }

    @Override
    public void list(int page) {

    }

    @Override
    public void detail(long id) {

    }

    @Override
    public void delete(long id) {

    }

    @Override
    public void add(Map<String, String> params) {

    }

    @Override
    public void modify(Map<String, String> params) {

    }
}