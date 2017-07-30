package com.qql.dagger.recommend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.qql.dagger.recommend.KeySet;
import com.qql.dagger.recommend.R;
import com.qql.dagger.recommend.adapter.BBAdapter;
import com.qql.dagger.recommend.base.BaseActivity;
import com.qql.dagger.recommend.model.bean.BBBean;
import com.qql.dagger.recommend.model.bean.CategoryBean;
import com.qql.dagger.recommend.presenter.BBPresenter;
import com.qql.dagger.recommend.presenter.MainPresenter;
import com.qql.dagger.recommend.presenter.contract.BBListContract;
import com.qql.dagger.recommend.presenter.contract.MainContract;
import com.qql.dagger.recommend.utils.SnackbarUtil;

import java.util.List;

import butterknife.BindView;

public class BBListActivity extends BaseActivity<BBPresenter> implements BBListContract.View{
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    private long currentCategoryId;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_bb_list;
    }

    @Override
    protected boolean hasToolBar() {
        return false;
    }

    @Override
    protected void initEventAndData() {
        initIntentData();
        mPresenter.getCategories();
    }

    private void initIntentData() {
        currentCategoryId = getIntent().getLongExtra(KeySet.KEY_CATEGORY_ID,-1);
    }

    @Override
    public void showBBList(List<BBBean> bbs) {
        BBAdapter adapter = new BBAdapter(this,bbs);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(BBListActivity.this,BBDetailActivity.class));
            }
        });
    }

    @Override
    public void showCategories(List<CategoryBean> categories) {
        if (categories == null || categories.size() == 0) {
            //TODO  未获取到分类信息
        } else {
            for (int i=0;i<categories.size();i++) {
                CategoryBean bean = categories.get(i);
                TabLayout.Tab tab = tabLayout.newTab();
                tab.setText(bean.getTitle());
                tab.setTag(bean);
                if (currentCategoryId == bean.getId()) {
                    tab.select();
                }
                tabLayout.addTab(tab);
            }
            if (-1 == currentCategoryId) {
                currentCategoryId = categories.get(0).getId();
            }
            tabLayout.addOnTabSelectedListener(tabSelectListener);
            mPresenter.findBB(null);
        }
    }

    private TabLayout.OnTabSelectedListener tabSelectListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            SnackbarUtil.show(tabLayout,tab.getText().toString());
            CategoryBean bean = (CategoryBean) tab.getTag();
            currentCategoryId = bean.getId();
            refresh();
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    private void refresh() {
        //TODO refresh
    }

    @Override
    public void showError(String msg) {

    }
}
