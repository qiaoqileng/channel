package com.qql.dagger.recommend;

import java.io.File;

/**
 * Created by qiao on 2016/11/28.
 */

public class Constants {
    public static final String PKG_NAME = App.getInstance().getPackageName();

    public static final String DB_NAME = PKG_NAME.replace(".", "_");

    public static final String PATH_DATA = App.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/NetCache";

    public static final String USER_ID = "user_id";
}
