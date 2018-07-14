package com.qql.dagger.recommend.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.qql.dagger.recommend.App;

/**
 * Created by qql on 2018/3/26.
 */

public class SharedPrefHelper {
    private static final String key = "login";
    private SharedPreferences sp;
    public SharedPrefHelper() {
        sp = App.getInstance().getSharedPreferences(key,Context.MODE_PRIVATE);
    }

    public SharedPreferences getSp() {
        return sp;
    }

    public SharedPreferences.Editor getEditor(){
        return sp.edit();
    }
}
