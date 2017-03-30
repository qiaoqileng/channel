package com.qql.dagger.recommend.fragment;

import android.widget.GridView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.qql.dagger.recommend.R;
import com.qql.dagger.recommend.adapter.CategoryAdapter;
import com.qql.dagger.recommend.animotion.ZoomInTransformer;
import com.qql.dagger.recommend.base.BaseFragment;
import com.qql.dagger.recommend.model.bean.BannerBean;
import com.qql.dagger.recommend.model.bean.CategoryBean;
import com.qql.dagger.recommend.model.holder.NetImageHolder;
import com.qql.dagger.recommend.presenter.HomePresenter;
import com.qql.dagger.recommend.presenter.contract.HomeContract;
import com.qql.dagger.recommend.utils.SnackbarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by qql on 2016/12/22.
 */

public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View{
    @BindView(R.id.convenientBanner)
    ConvenientBanner convenientBanner;
    @BindView(R.id.gridview)
    GridView gridView;
    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
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
    public void showCategory(List<CategoryBean> categories) {
        //TODO 显示分类
        if (categories == null || categories.size() == 0){
            return;
        }
        gridView.setAdapter(new CategoryAdapter(categories,getActivity()));
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
