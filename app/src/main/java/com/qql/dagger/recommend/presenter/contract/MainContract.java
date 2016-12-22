package com.qql.dagger.recommend.presenter.contract;

import com.qql.dagger.recommend.base.BasePresenter;
import com.qql.dagger.recommend.base.BaseView;
import com.qql.dagger.recommend.model.bean.GankItemBean;

import java.util.List;

/**
 * Created by qql on 2016/12/22.
 */

public interface MainContract {
    interface View extends BaseView {

        void showUpdateDialog(String versionContent);

    }

    interface Presenter extends BasePresenter<View> {

        void checkVersion(String currentVersion);

    }
}
