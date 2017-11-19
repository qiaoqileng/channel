package com.qql.dagger.recommend.presenter.contract;

import com.green.dao.output.MyBook;
import com.qql.dagger.recommend.base.BasePresenter;
import com.qql.dagger.recommend.base.BaseView;

import org.geometerplus.android.fbreader.libraryService.BookCollectionShadow;
import org.geometerplus.fbreader.book.Book;

import java.util.List;
import java.util.Map;

/**
 * Created by qql on 2017/7/29.
 */

public interface BookSelfContract {
    interface View extends BaseView {

        void showBookSelfList(List<MyBook> bbs);
        void showZLBooks(List<Book> books);
    }

    interface Presenter extends BasePresenter<BookSelfContract.View> {

        void findBookSelfList(BookCollectionShadow collection, Map<String,String> params);
        void insertBook(MyBook myBook);
    }
}
