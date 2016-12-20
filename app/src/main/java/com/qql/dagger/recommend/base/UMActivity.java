package com.qql.dagger.recommend.base;

import android.support.v7.app.AppCompatActivity;

import com.umeng.analytics.MobclickAgent;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by qiao on 2016/11/28.
 * 友盟activity
 */

public class UMActivity extends SupportActivity {

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
