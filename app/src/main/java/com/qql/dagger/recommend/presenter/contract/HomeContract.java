package com.qql.dagger.recommend.presenter.contract;

import com.qql.dagger.recommend.base.BasePresenter;
import com.qql.dagger.recommend.base.BaseView;

/**
 * Created by qql on 2016/12/22.
 */

public interface HomeContract {
    interface View extends BaseView {

        void showDailyBanners();
    }

    interface Presenter extends BasePresenter<HomeContract.View> {

        void getDailyBanners();

    }
}
