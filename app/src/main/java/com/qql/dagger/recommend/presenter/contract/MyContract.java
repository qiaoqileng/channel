package com.qql.dagger.recommend.presenter.contract;

import android.support.v4.app.Fragment;

import com.qql.dagger.recommend.base.BasePresenter;
import com.qql.dagger.recommend.base.BaseView;
import com.qql.dagger.recommend.model.entity.User;

/**
 * Created by qql on 2016/12/22.
 */

public interface MyContract {
    interface View extends BaseView {

        void loginResult(User user);
    }

    interface Presenter extends BasePresenter<View> {

        void login(Fragment fragment);
        void localLogin();
    }
}
