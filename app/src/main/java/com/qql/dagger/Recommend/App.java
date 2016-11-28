package com.qql.dagger.recommend;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

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

    public void exitApp() {
        ActivityManager mActivityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        if (mActivityManager != null) {
            mActivityManager.killBackgroundProcesses(this.getPackageName());
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
