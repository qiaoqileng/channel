package com.qql.dagger.dagger2test.dao;

import android.content.Context;

import com.qql.dagger.dagger2test.App;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by qiao on 2016/11/23.
 */
public class RealmUtils {
    private static RealmUtils mInstance;
    private String realName = "myRealm.realm";

    public static RealmUtils getInstance() {
        if (mInstance == null) {
            synchronized (RealmUtils.class) {
                if (mInstance == null) {
                    mInstance = new RealmUtils();
                }
            }
        }
        return mInstance;
    }

    /**
     * 获得Realm对象
     *
     * @return
     */
    public Realm getRealm() {
        return Realm.getInstance(new RealmConfiguration.Builder(App.getInstance()).name(realName).build());
    }
}
