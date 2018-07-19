package com.qql.dagger.recommend.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.qql.dagger.recommend.KeySet;
import com.qql.dagger.recommend.R;
import com.qql.dagger.recommend.activity.BBListActivity;
import com.qql.dagger.recommend.adapter.CategoryAdapter;
import com.qql.dagger.recommend.animotion.ZoomInTransformer;
import com.qql.dagger.recommend.base.BaseFragment;
import com.qql.dagger.recommend.model.bean.BannerBean;
import com.qql.dagger.recommend.model.bean.Type;
import com.qql.dagger.recommend.model.holder.NetImageHolder;
import com.qql.dagger.recommend.presenter.HomePresenter;
import com.qql.dagger.recommend.presenter.contract.HomeContract;
import com.qql.dagger.recommend.utils.SnackbarUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by qql on 2016/12/22.
 */

public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View{
    @BindView(R.id.convenientBanner)
    ConvenientBanner convenientBanner;
    @BindView(R.id.gridview)
    GridView gridView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;
    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initEventAndData();
                refreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected void initEventAndData() {
        mPresenter.getDailyBanners();
        mPresenter.getCategory();
    }

    @Override
    public void showDailyBanners(List<BannerBean> banners) {
        initBanners(banners);
    }

    @Override
    public void showCategory(final List<Type> categories) {
        //TODO 显示分类
        if (categories == null || categories.size() == 0){
            return;
        }
        gridView.setAdapter(new CategoryAdapter(categories,getActivity()));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Type categoryBean = categories.get(i);
                toBBList(categoryBean.getId());
            }
        });
    }

    private void initBanners(List<BannerBean> banners){
        //test
        convenientBanner.getViewPager().setPageTransformer(true,new ZoomInTransformer());
        convenientBanner.setPages(new CBViewHolderCreator<NetImageHolder>() {
            @Override
            public NetImageHolder createHolder() {
                return new NetImageHolder();
            }
        },banners).setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused})  ;
        convenientBanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                SnackbarUtil.show(convenientBanner,"第"+position+"个");
            }
        });
    }

    private void toBBList(long categoryId){
        Intent intent = new Intent(getActivity(), BBListActivity.class);
        if (categoryId != -1){
            intent.putExtra(KeySet.KEY_CATEGORY_ID,categoryId);
        }
        startActivity(intent);
    }

    private void toBBList(){
        toBBList(-1);
    }

    @OnClick({R.id.search,R.id.voice})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search:
                //TODO S
                toBBList();
                break;
            case R.id.voice:
                //TODO to be continue
                break;
        }
    }

    // 开始自动翻页
    @Override
    public void onResume() {
        super.onResume();
        //开始自动翻页
        convenientBanner.startTurning(5000);
    }

    // 停止自动翻页
    @Override
    public void onPause() {
        super.onPause();
        //停止翻页
        convenientBanner.stopTurning();
    }

    @Override
    public void showError(String msg) {
        SnackbarUtil.show(convenientBanner,msg);
    }
}
