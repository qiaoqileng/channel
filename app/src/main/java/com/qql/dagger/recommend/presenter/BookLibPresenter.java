package com.qql.dagger.recommend.presenter;

import android.os.Environment;

import com.qql.dagger.recommend.base.RxPresenter;
import com.qql.dagger.recommend.cache.DataCache;
import com.qql.dagger.recommend.model.bean.BBBean;
import com.qql.dagger.recommend.model.bean.CategoryBean;
import com.qql.dagger.recommend.model.http.GankHttpResponse;
import com.qql.dagger.recommend.model.http.RetrofitHelper;
import com.qql.dagger.recommend.presenter.contract.BBListContract;
import com.qql.dagger.recommend.presenter.contract.BookLibContract;
import com.qql.dagger.recommend.utils.RxUtil;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by T-46 on 2017/4/6.
 */

public class BookLibPresenter extends RxPresenter<BookLibContract.View> implements BookLibContract.Presenter {
    private final RetrofitHelper mRetrofitHelper;
    private final DataCache dataCache;
    private final String rootPath = Environment.getExternalStorageDirectory().toString();
    @Inject
    public BookLibPresenter(RetrofitHelper mRetrofitHelper, DataCache dataCache) {
        this.mRetrofitHelper = mRetrofitHelper;
        this.dataCache = dataCache;
    }

    @Override
    public void getBookList(String path) {

    }

    @Override
    public void searchBook(Map<String, String> params) {

    }
}
