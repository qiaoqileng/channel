package com.green.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.green.dao.output.DaoMaster;

/**
 * Created by qiao on 2016/12/1.
 */

public class MyOpenHelper extends DaoMaster.OpenHelper {

    public MyOpenHelper(Context context, String name) {
        super(context, name);
    }

    public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }
}
