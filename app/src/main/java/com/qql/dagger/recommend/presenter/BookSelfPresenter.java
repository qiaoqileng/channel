package com.qql.dagger.recommend.presenter;

import com.green.dao.GreenDaoManager;
import com.green.dao.output.Book;
import com.green.dao.output.BookDao;
import com.qql.dagger.recommend.base.RxPresenter;
import com.qql.dagger.recommend.cache.DataCache;
import com.qql.dagger.recommend.model.http.RetrofitHelper;
import com.qql.dagger.recommend.presenter.contract.BookSelfContract;
import com.qql.dagger.recommend.utils.LogUtil;
import com.qql.dagger.recommend.utils.RxUtil;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action1;

import static com.qql.dagger.recommend.Constants.USER_ID;

/**
 * Created by qql on 2017/7/29.
 */

public class BookSelfPresenter extends RxPresenter<BookSelfContract.View> implements BookSelfContract.Presenter{
    private final RetrofitHelper mRetrofitHelper;
    private final GreenDaoManager greenDaoManager;
    @Inject
    public BookSelfPresenter(RetrofitHelper mRetrofitHelper,GreenDaoManager greenDaoManager) {
        this.mRetrofitHelper = mRetrofitHelper;
        this.greenDaoManager = greenDaoManager;
    }
    @Override
    public void findBookSelfList(Map<String, String> params) {
        if (params != null && params.size() > 0 && params.containsKey(USER_ID)) {
            BookDao dao = greenDaoManager.getDaoSession().getBookDao();
            QueryBuilder<Book> build = dao.queryBuilder();
            build.where(BookDao.Properties.User_id.eq(params.get(USER_ID)));
            build.orderDesc(BookDao.Properties.Last_read_time);
            Subscription rxSubscription = build.rx().list()
                    .compose(RxUtil.<List<Book>>rxSchedulerHelper())
                    .subscribe(new Action1<List<Book>>() {
                        @Override
                        public void call(List<Book> books) {
                            mView.showBookSelfList(books);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            LogUtil.printException(throwable);
                            mView.showError("数据加载失败ヽ(≧Д≦)ノ");
                        }
                    });
            addSubscrebe(rxSubscription);
        }
    }

    @Override
    public void insertBook(Book book) {
        if (book == null) {
            return;
        }
        BookDao dao = greenDaoManager.getDaoSession().getBookDao();
        Subscription rxSubscription = dao.rx().save(book)
                .compose(RxUtil.<Book>rxSchedulerHelper())
                .subscribe(new Action1<Book>() {
                    @Override
                    public void call(Book book) {
                        LogUtil.d("插入成功");
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtil.printException(throwable);
                        mView.showError("数据插入失败ヽ(≧Д≦)ノ");
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
