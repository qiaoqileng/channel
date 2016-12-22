package com.qql.dagger.recommend.fragment;

import com.qql.dagger.recommend.base.BaseFragment;
import com.qql.dagger.recommend.presenter.HomePresenter;
import com.qql.dagger.recommend.presenter.contract.HomeContract;

/**
 * Created by qql on 2016/12/22.
 */

public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View{
    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void showDailyBanners() {

    }

    @Override
    public void showError(String msg) {

    }
}
