package com.qql.dagger.recommend.presenter;

import com.qql.dagger.recommend.App;
import com.qql.dagger.recommend.base.RxPresenter;
import com.qql.dagger.recommend.model.bean.BBBean;
import com.qql.dagger.recommend.model.http.RetrofitHelper;
import com.qql.dagger.recommend.presenter.contract.BBListContract;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by T-46 on 2017/4/6.
 */

public class BBPresenter extends RxPresenter<BBListContract.View> implements BBListContract.Presenter {
    private final RetrofitHelper mRetrofitHelper;
    @Inject
    public BBPresenter(RetrofitHelper mRetrofitHelper) {
        this.mRetrofitHelper = mRetrofitHelper;
    }

    @Override
    public void findBB(Map<String, String> params) {

    }
}
