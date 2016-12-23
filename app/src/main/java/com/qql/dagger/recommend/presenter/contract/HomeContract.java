package com.qql.dagger.recommend.presenter.contract;

import com.qql.dagger.recommend.base.BasePresenter;
import com.qql.dagger.recommend.base.BaseView;
import com.qql.dagger.recommend.model.bean.BannerBean;

import java.util.ArrayList;

/**
 * Created by qql on 2016/12/22.
 */

public interface HomeContract {
    interface View extends BaseView {

        void showDailyBanners(ArrayList<BannerBean> banners);
    }

    interface Presenter extends BasePresenter<HomeContract.View> {

        void getDailyBanners();

    }
}
