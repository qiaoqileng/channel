package com.qql.dagger.recommend.presenter;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.qql.dagger.recommend.base.RxPresenter;
import com.qql.dagger.recommend.cache.DataCache;
import com.qql.dagger.recommend.model.entity.User;
import com.qql.dagger.recommend.model.http.RetrofitHelper;
import com.qql.dagger.recommend.presenter.contract.MyContract;
import com.qql.dagger.recommend.presenter.contract.RegisterContract;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;

import java.util.Map;

import javax.inject.Inject;

/**
 * Created by qql on 2016/12/22.
 */

public class RegisterPresenter extends RxPresenter<RegisterContract.View> implements RegisterContract.Presenter {

    private final RetrofitHelper mRetrofitHelper;
    private final DataCache dataCache;
    private Context context;

    @Inject
    public RegisterPresenter(RetrofitHelper mRetrofitHelper, DataCache dataCache, Tencent tencent) {
        this.mRetrofitHelper = mRetrofitHelper;
        this.dataCache = dataCache;
    }

    @Override
    public void getSMSMsg(String mobile) {

    }

    @Override
    public void register(Map<String, String> params) {

    }
}
