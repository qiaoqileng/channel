package com.qql.dagger.recommend.presenter;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.qql.dagger.recommend.base.RxPresenter;
import com.qql.dagger.recommend.cache.DataCache;
import com.qql.dagger.recommend.model.entity.User;
import com.qql.dagger.recommend.model.http.RetrofitHelper;
import com.qql.dagger.recommend.presenter.contract.MyContract;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;

import javax.inject.Inject;

/**
 * Created by qql on 2016/12/22.
 */

public class MyPresenter extends RxPresenter<MyContract.View> implements MyContract.Presenter {

    private final RetrofitHelper mRetrofitHelper;
    private final DataCache dataCache;
    private Context context;
    private DataCache.LoginListener listener = new DataCache.LoginListener() {
        @Override
        public void showError(String error) {
            mView.showError(error);
        }

        @Override
        public void success(User user) {
            mView.loginResult(user);
        }
    };

    @Inject
    public MyPresenter(RetrofitHelper mRetrofitHelper, DataCache dataCache,Tencent tencent) {
        this.mRetrofitHelper = mRetrofitHelper;
        this.dataCache = dataCache;
    }

    @Override
    public void login(Fragment fragment) {
        User user = dataCache.getUser();
        if (user == null){
            dataCache.login(fragment, listener);
        } else {
            mView.loginResult(user);
        }
    }

    @Override
    public void localLogin() {
        User user = dataCache.getUser();
        if (user == null){
            dataCache.localLogin(listener);
        } else {
            mView.loginResult(user);
        }
    }

    public boolean hasLocalLogin(){
        return dataCache.checkLogin();
    }

    public IUiListener getListener() {
        return dataCache.getListener();
    }
}
