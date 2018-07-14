package com.qql.dagger.recommend.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.WindowManager;

import com.qql.dagger.recommend.App;

/**
 * Created by codeest on 16/9/3.
 */

public class SnackbarUtil {

    public static void show(View view, String msg) {
        WindowManager wmManager=(WindowManager) App.getInstance().getSystemService(Context.WINDOW_SERVICE);

        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show();
    }

    public static void showShort(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
    }
}
