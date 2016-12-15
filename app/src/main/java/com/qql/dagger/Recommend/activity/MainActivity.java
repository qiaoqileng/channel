package com.qql.dagger.recommend.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.qql.dagger.recommend.R;
import com.qql.dagger.recommend.adapter.RecyclerAdapter;
import com.qql.dagger.recommend.base.BaseActivity;
import com.qql.dagger.recommend.databinding.ActivityMainBinding;
import com.qql.dagger.recommend.model.bean.GankItemBean;
import com.qql.dagger.recommend.presenter.GirlPresenter;
import com.qql.dagger.recommend.presenter.contract.GirlContract;
import com.qql.dagger.recommend.utils.LogUtil;
import com.qql.dagger.recommend.utils.SnackbarUtil;
import com.qql.dagger.recommend.BR;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<GirlPresenter> implements GirlContract.View{

    private static final int SPAN_COUNT = 2;
    private ArrayList<GankItemBean> mList;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private RecyclerAdapter mAdapter;
    private RecyclerView rvGirlContent;
    private SwipeRefreshLayout swipeRefresh;
    private FloatingActionButton toTop;
    private boolean isLoadingMore = false;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
        binding.setVariable(BR.listener,new ClickListener());
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData() {
        rvGirlContent = ((ActivityMainBinding)binding).recyclerView;
        swipeRefresh = ((ActivityMainBinding)binding).swipeRefresh;
        toTop = ((ActivityMainBinding)binding).toTop;

        mList = new ArrayList<GankItemBean>();
        mAdapter = new RecyclerAdapter(mContext, mList);
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(SPAN_COUNT,StaggeredGridLayoutManager.VERTICAL);
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
                int lastItem = Math.max(visibleItems[0],visibleItems[1]);
                if (lastItem > mAdapter.getItemCount() - 5 && !isLoadingMore && dy > 0 ) {
                    isLoadingMore = true;
                    mPresenter.getMoreGirlData();
                }
            }
        });

//        mAdapter.setOnItemClickListener(new GirlAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClickListener(int position, View shareView) {
//                Intent intent = new Intent();
//                intent.setClass(mContext, GirlDetailActivity.class);
//                intent.putExtra("url",mList.get(position).getUrl());
//                intent.putExtra("id",mList.get(position).get_id());
//                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(mActivity, shareView, "shareView");
//                mContext.startActivity(intent,options.toBundle());
//            }
//        });
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
        for(int i =mList.size() - GirlPresenter.NUM_OF_PAGE ; i < mList.size(); i++) {    //使用notifyDataSetChanged已加载的图片会有闪烁，遂使用inserted逐个插入
            mAdapter.notifyItemInserted(i);
        }
    }

    @Override
    public void showError(String msg) {
        if (swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        } else {
        }
        SnackbarUtil.showShort(rvGirlContent,msg);
    }

    public class ClickListener {

        public void addItem(View view) {
            // TODO: 2016/11/28
            LogUtil.d("add a item");
        }

        public void deleteItem(View view) {
            // TODO: 2016/11/28
        }

        public void toTop(View view){
            if (rvGirlContent != null) {
                rvGirlContent.scrollToPosition(0);
            }
        }
    }
}
