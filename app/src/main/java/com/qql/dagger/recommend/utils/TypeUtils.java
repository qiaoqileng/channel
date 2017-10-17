package com.qql.dagger.recommend.utils;

import android.text.TextUtils;

/**
 * Created by qql on 2017/10/13.
 */

public class TypeUtils {

    private static final String[] accept_types = {".epub",".oeb",".fb2",".mobi",".prc",".txt",".rtf",".pdf",".djvu",".cbr",".cbz"};

    public static boolean acceptType(String path){
        if (TextUtils.isEmpty(path)){
            return false;
        } else {
            for (String type:accept_types){
                if (path.contains(type)){
                    return true;
                }
            }
            return false;
        }
    }
}

