package com.qql.dagger.recommend.presenter.contract;


import com.qql.dagger.recommend.base.BasePresenter;
import com.qql.dagger.recommend.base.BaseView;
import com.qql.dagger.recommend.model.bean.GankItemBean;

import java.util.List;

/**
 * Created by codeest on 16/8/19.
 */

public interface GirlContract {

    interface View extends BaseView {

        void showContent(List<GankItemBean> list);

        void showMoreContent(List<GankItemBean> list);
    }

    interface Presenter extends BasePresenter<View> {

        void getGirlData();

        void getMoreGirlData();

    }
}
