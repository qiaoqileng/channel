package com.qql.dagger.recommend.base;

import android.support.v7.app.AppCompatDelegate;

import com.qql.dagger.recommend.App;
import com.qql.dagger.recommend.component.ActivityComponent;
import com.qql.dagger.recommend.component.DaggerActivityComponent;
import com.qql.dagger.recommend.module.ActivityModule;

import javax.inject.Inject;

/**
 * Created by codeest on 2016/8/2.
 * MVP activity基类
 */
public abstract class BaseActivity<T extends BasePresenter> extends SimpleActivity implements BaseView{

    @Inject
    protected T mPresenter;

    @Override
    protected void attachPresent() {
        initInject();
        if (mPresenter != null)
            mPresenter.attachView(this);
        setToolBar("");
    }

    protected ActivityComponent getActivityComponent(){
        return  DaggerActivityComponent.builder()
                .appComponent(App.getAppComponent())
                .activityModule(getActivityModule())
                .build();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
    }

    @Override
    public void useNightMode(boolean isNight) {
        if (isNight) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO);
        }
        recreate();
    }
    protected ActivityModule getActivityModule(){
        return new ActivityModule(this);
    }

    protected abstract void initInject();
    protected abstract int getLayout();
    protected abstract void initEventAndData();
}