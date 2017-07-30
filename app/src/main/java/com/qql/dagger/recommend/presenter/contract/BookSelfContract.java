package com.qql.dagger.recommend.presenter.contract;

import com.green.dao.output.Book;
import com.qql.dagger.recommend.base.BasePresenter;
import com.qql.dagger.recommend.base.BaseView;

import java.util.List;
import java.util.Map;

/**
 * Created by qql on 2017/7/29.
 */

public interface BookSelfContract {
    interface View extends BaseView {

        void showBookSelfList(List<Book> bbs);
    }

    interface Presenter extends BasePresenter<BookSelfContract.View> {

        void findBookSelfList(Map<String,String> params);
        void insertBook(Book book);
    }
}
