package com.qql.dagger.recommend.presenter.contract;

import android.support.v4.app.Fragment;

import com.qql.dagger.recommend.base.BasePresenter;
import com.qql.dagger.recommend.base.BaseView;
import com.qql.dagger.recommend.model.bean.Product;
import com.qql.dagger.recommend.model.entity.User;

import java.util.List;
import java.util.Map;

/**
 * Created by qql on 2016/12/22.
 */

public interface ProductContract {
    interface View extends BaseView {

        void list(List<Product> products);
        void delete(boolean result);
        void add(boolean result);
        void detail(boolean result);
        void modify(boolean result);
    }

    interface Presenter extends BasePresenter<View> {

        void list(int page);
        void detail(long id);
        void delete(long id);
        void add(Map<String,String> params);
        void modify(Map<String,String> params);
    }
}
