package com.qql.dagger.dagger2test;

import android.app.Application;

/**
 * Created by qiao on 2016/11/23.
 */

public class App extends Application {
    private static App instance;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
