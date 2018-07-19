package com.qql.dagger.recommend.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.qql.dagger.recommend.KeySet;
import com.qql.dagger.recommend.R;
import com.qql.dagger.recommend.adapter.BBAdapter;
import com.qql.dagger.recommend.base.BaseActivity;
import com.qql.dagger.recommend.model.bean.BBBean;
import com.qql.dagger.recommend.model.bean.Page;
import com.qql.dagger.recommend.model.bean.Product;
import com.qql.dagger.recommend.model.bean.Type;
import com.qql.dagger.recommend.presenter.BBPresenter;
import com.qql.dagger.recommend.presenter.contract.BBListContract;
import com.qql.dagger.recommend.utils.CommonUtils;
import com.qql.dagger.recommend.utils.SnackbarUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

public class BBListActivity extends BaseActivity<BBPresenter> implements BBListContract.View{
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    private long currentCategoryId;
    private StatusLayoutManager statusLayoutManager;

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
        statusLayoutManager = new StatusLayoutManager.Builder(listView)
                .setOnStatusChildClickListener(new OnStatusChildClickListener() {
                    @Override
                    public void onEmptyChildClick(View view) {
                        statusLayoutManager.showLoadingLayout();
                        refresh();
                    }

                    @Override
                    public void onErrorChildClick(View view) {
                        statusLayoutManager.showLoadingLayout();
                        refresh();
                    }

                    @Override
                    public void onCustomerChildClick(View view) {
                    }
                })
                .build();
        mPresenter.getCategories();
    }

    private void initIntentData() {
        currentCategoryId = getIntent().getLongExtra(KeySet.KEY_CATEGORY_ID,-1);
    }

    @Override
    public void showBBList(Page<Product> bbs) {
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
    public void showCategories(List<Type> categories) {
        if (CommonUtils.isEmptyList(categories)) {
            //TODO  未获取到分类信息
        } else {
            for (int i=0;i<categories.size();i++) {
                Type bean = categories.get(i);
                TabLayout.Tab tab = tabLayout.newTab();
                tab.setText(bean.getType_name());
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

        }
    }

    private TabLayout.OnTabSelectedListener tabSelectListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            SnackbarUtil.show(tabLayout,tab.getText().toString());
            Type bean = (Type) tab.getTag();
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
        Map<String,String> params = new HashMap<>();
        params.put("typeId",currentCategoryId + "");
        params.put("page","1");
        mPresenter.findBB(params);
    }

    @Override
    public void showError(String msg) {

    }
}
