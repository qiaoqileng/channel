package com.qql.dagger.recommend.presenter.contract;

import com.green.dao.output.MyBook;
import com.qql.dagger.recommend.base.BasePresenter;
import com.qql.dagger.recommend.base.BaseView;
import com.qql.dagger.recommend.model.bean.BBBean;
import com.qql.dagger.recommend.model.bean.CategoryBean;

import java.util.List;
import java.util.Map;

/**
 * Created by qql on 2016/12/22.
 */

public interface BookLibContract {
    interface View extends BaseView {

        void showBookList(List<MyBook> bbs);
        void showSearchResult(List<MyBook> bbs);
    }

    interface Presenter extends BasePresenter<View> {

        void getBookList(String path);
        void searchBook(Map<String, String> params);
    }
}
