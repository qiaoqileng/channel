package com.qql.dagger.recommend.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.qql.dagger.recommend.R;
import com.qql.dagger.recommend.adapter.RecyclerAdapter;
import com.qql.dagger.recommend.base.BaseActivity;
import com.qql.dagger.recommend.model.bean.GankItemBean;
import com.qql.dagger.recommend.presenter.GirlPresenter;
import com.qql.dagger.recommend.presenter.contract.GirlContract;
import com.qql.dagger.recommend.utils.LogUtil;
import com.qql.dagger.recommend.utils.SnackbarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<GirlPresenter> implements GirlContract.View {

    private static final int SPAN_COUNT = 2;
    private ArrayList<GankItemBean> mList;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    RecyclerAdapter mAdapter;
    @BindView(R.id.recyclerView)
    RecyclerView rvGirlContent;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    private boolean isLoadingMore = false;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData() {
        fab.setVisibility(View.VISIBLE);

        mList = new ArrayList<GankItemBean>();
        mAdapter = new RecyclerAdapter(mContext, mList);
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
        mStaggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        rvGirlContent.setLayoutManager(mStaggeredGridLayoutManager);
        rvGirlContent.setAdapter(mAdapter);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getGirlData();
            }
        });
        rvGirlContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int[] visibleItems = mStaggeredGridLayoutManager.findLastVisibleItemPositions(null);
                int lastItem = Math.max(visibleItems[0], visibleItems[1]);
                if (lastItem > mAdapter.getItemCount() - 5 && !isLoadingMore && dy > 0) {
                    isLoadingMore = true;
                    mPresenter.getMoreGirlData();
                }
            }
        });

        mAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, View shareView) {
                Intent intent = new Intent();
                intent.setClass(mContext, ImageDetailActivity.class);
                intent.putExtra("url", mList.get(position).getUrl());
                intent.putExtra("id", mList.get(position).get_id());
                if (Build.VERSION.SDK_INT >= 21) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, shareView, "shareView");
                    mContext.startActivity(intent, options.toBundle());
                } else {
                    mContext.startActivity(intent);
                }

            }
        });
//        ivProgress.start();
        mPresenter.getGirlData();
    }

    @Override
    public void showContent(List<GankItemBean> list) {
        if (swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        } else {
        }
        mList.clear();
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMoreContent(List<GankItemBean> list) {
        isLoadingMore = false;
        mList.addAll(list);
        for (int i = mList.size() - GirlPresenter.NUM_OF_PAGE; i < mList.size(); i++) {    //使用notifyDataSetChanged已加载的图片会有闪烁，遂使用inserted逐个插入
            mAdapter.notifyItemInserted(i);
        }
    }

    @Override
    public void showError(String msg) {
        if (swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        } else {
        }
        SnackbarUtil.showShort(rvGirlContent, msg);
    }

    @OnClick(R.id.fab)
    public void toTop(View view) {
        if (rvGirlContent != null) {
            rvGirlContent.scrollToPosition(0);
        }
    }
}
