package com.qql.dagger.recommend.presenter.contract;

import com.green.dao.output.MyBook;
import com.qql.dagger.recommend.base.BasePresenter;
import com.qql.dagger.recommend.base.BaseView;

import java.util.List;
import java.util.Map;

/**
 * Created by qql on 2017/7/29.
 */

public interface BookSelfContract {
    interface View extends BaseView {

        void showBookSelfList(List<MyBook> bbs);
    }

    interface Presenter extends BasePresenter<BookSelfContract.View> {

        void findBookSelfList(Map<String,String> params);
        void insertBook(MyBook myBook);
    }
}
