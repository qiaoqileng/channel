package com.qql.dagger.recommend.dao;

import android.content.Context;

import com.qql.dagger.recommend.App;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by qiao on 2016/11/23.
 */
public class RealmUtils {
    private static RealmUtils mInstance;
    private static String realName = "myRealm.realm";

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
    public static Realm getRealm(Context context) {
        return Realm.getInstance(new RealmConfiguration.Builder(context).name(realName).build());
    }
}
