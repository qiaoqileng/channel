package com.green.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.green.dao.output.DaoMaster;
import com.green.dao.output.DaoSession;
import com.qql.dagger.recommend.App;
import com.qql.dagger.recommend.Constants;

/**
 * Created by qiao on 2016/12/1.
 */
public class GreenDaoManager {
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    public GreenDaoManager(Context context) {
        MyOpenHelper helper = new MyOpenHelper(App.getInstance(), Constants.DB_NAME,null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public DaoMaster getDaoMaster() {
        return daoMaster;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
