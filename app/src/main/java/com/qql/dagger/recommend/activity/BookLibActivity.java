package com.qql.dagger.recommend.activity;

import com.green.dao.output.MyBook;
import com.qql.dagger.recommend.base.BaseActivity;
import com.qql.dagger.recommend.presenter.BBPresenter;
import com.qql.dagger.recommend.presenter.BookLibPresenter;
import com.qql.dagger.recommend.presenter.contract.BBListContract;
import com.qql.dagger.recommend.presenter.contract.BookLibContract;

import java.util.List;

/**
 *
 * Created by qql on 2017/10/17.
 */

public class BookLibActivity extends BaseActivity<BookLibPresenter> implements BookLibContract.View{
    @Override
    public void showError(String msg) {

    }

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayout() {
        return 0;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void showBookList(List<MyBook> bbs) {

    }

    @Override
    public void showSearchResult(List<MyBook> bbs) {

    }
}
