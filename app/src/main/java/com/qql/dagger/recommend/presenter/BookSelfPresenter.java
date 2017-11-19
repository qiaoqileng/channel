package com.qql.dagger.recommend.presenter;

import com.green.dao.GreenDaoManager;
import com.green.dao.output.MyBook;
import com.green.dao.output.MyBookDao;
import com.qql.dagger.recommend.App;
import com.qql.dagger.recommend.base.RxPresenter;
import com.qql.dagger.recommend.model.http.RetrofitHelper;
import com.qql.dagger.recommend.presenter.contract.BookSelfContract;
import com.qql.dagger.recommend.utils.LogUtil;
import com.qql.dagger.recommend.utils.RxUtil;

import org.geometerplus.android.fbreader.libraryService.BookCollectionShadow;
import org.geometerplus.fbreader.book.BookQuery;
import org.geometerplus.fbreader.book.Filter;
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
    private BookCollectionShadow collection;

    @Inject
    public BookSelfPresenter(RetrofitHelper mRetrofitHelper,GreenDaoManager greenDaoManager) {
        this.mRetrofitHelper = mRetrofitHelper;
        this.greenDaoManager = greenDaoManager;
    }
    @Override
    public void findBookSelfList(BookCollectionShadow collection,Map<String, String> params) {
        getBookList(collection);
//        if (params != null && params.size() > 0 && params.containsKey(USER_ID)) {
//            MyBookDao dao = greenDaoManager.getDaoSession().getMyBookDao();
//            QueryBuilder<MyBook> build = dao.queryBuilder();
//            build.where(MyBookDao.Properties.User_id.eq(params.get(USER_ID)));
//            build.orderDesc(MyBookDao.Properties.Last_read_time);
//            Subscription rxSubscription = build.rx().list()
//                    .compose(RxUtil.<List<MyBook>>rxSchedulerHelper())
//                    .subscribe(new Action1<List<MyBook>>() {
//                        @Override
//                        public void call(List<MyBook> books) {
//                            mView.showBookSelfList(books);
//                        }
//                    }, new Action1<Throwable>() {
//                        @Override
//                        public void call(Throwable throwable) {
//                            LogUtil.printException(throwable);
//                            mView.showError("数据加载失败ヽ(≧Д≦)ノ");
//                        }
//                    });
//            addSubscrebe(rxSubscription);
//        }
    }

    private void getBookList(BookCollectionShadow collection){
        BookQuery query = new BookQuery(new Filter.Empty(), 20);
        mView.showZLBooks(collection.books(query));
    }

    @Override
    public void insertBook(MyBook myBook) {
        if (myBook == null) {
            return;
        }
        MyBookDao dao = greenDaoManager.getDaoSession().getMyBookDao();
        Subscription rxSubscription = dao.rx().save(myBook)
                .compose(RxUtil.<MyBook>rxSchedulerHelper())
                .subscribe(new Action1<MyBook>() {
                    @Override
                    public void call(MyBook book) {
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
