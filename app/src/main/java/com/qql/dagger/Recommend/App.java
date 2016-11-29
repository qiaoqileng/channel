package com.qql.dagger.recommend;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.umeng.analytics.MobclickAgent;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by qiao on 2016/11/23.
 */

public class App extends Application {
    private static App instance;
    private static String realName = "myRealm.realm";

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //友盟统计使用activity和fragment混合统计
//        MobclickAgent.openActivityDurationTrack(true);
//        MobclickAgent.startWithConfigure(new MobclickAgent.UMAnalyticsConfig(this,"583be16aaed179599700077d","channelId"));
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        RealmConfiguration configuration = new RealmConfiguration.Builder(this).name(realName).build();
        Realm.setDefaultConfiguration(configuration);
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
