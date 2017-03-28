package com.qql.dagger.recommend.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.qql.dagger.recommend.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by codeest on 16/8/11.
 * 无MVP的activity基类
 */

public abstract class SimpleActivity extends UMActivity {

    protected Activity mContext;
    private Unbinder mUnBinder;
    FrameLayout content;
    @BindView(R.id.toolbar)
    protected Toolbar toolBar;
    @BindView(R.id.fab)
    protected FloatingActionButton fab;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(hasToolBar()?R.layout.activity_base_toolbar:R.layout.activity_no_toolbar);
        mContext = this;
        View view = LayoutInflater.from(this).inflate(getLayout(),null);
        content = (FrameLayout) findViewById(R.id.content_demo);
        content.addView(view);
        mUnBinder = ButterKnife.bind(this);

        attachPresent();

        initEventAndData();
    }

    protected boolean hasToolBar(){
        return true;
    }

    protected void attachPresent() {
    }

    protected void setToolBar(String title) {
        if (toolBar == null) {
            return;
        }
        setSupportActionBar(toolBar);
        toolBar.setTitle(TextUtils.isEmpty(title)?getString(R.string.app_name):title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO activity和fragment的返回处理
                finish();
//                onBackPressedSupport();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnBinder.unbind();
    }

    protected abstract int getLayout();
    protected abstract void initEventAndData();
}
