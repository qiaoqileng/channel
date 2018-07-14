package com.qql.dagger.recommend.activity;

import com.qql.dagger.recommend.base.BaseActivity;
import com.qql.dagger.recommend.model.bean.SMSMsg;
import com.qql.dagger.recommend.presenter.RegisterPresenter;
import com.qql.dagger.recommend.presenter.contract.RegisterContract;
import com.qql.dagger.recommend.utils.SnackbarUtil;

/**
 * Created by qql on 2018/3/24.
 */

public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View{
    @Override
    public void showError(String msg) {

    }

    @Override
    public void showSMS(SMSMsg msg) {

    }

    @Override
    public void registerResult(boolean result) {

    }

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayout() {
        return 0;
    }

    @Override
    protected void initEventAndData() {

    }
}
