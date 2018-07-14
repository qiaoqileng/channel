package com.qql.dagger.recommend.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.qql.dagger.recommend.R;
import com.qql.dagger.recommend.activity.JoinUsActivity;
import com.qql.dagger.recommend.activity.SellerCenterActivity;
import com.qql.dagger.recommend.base.BaseFragment;
import com.qql.dagger.recommend.model.bean.Product;
import com.qql.dagger.recommend.model.entity.User;
import com.qql.dagger.recommend.option.GlideOptions;
import com.qql.dagger.recommend.presenter.MyPresenter;
import com.qql.dagger.recommend.presenter.ProductPresenter;
import com.qql.dagger.recommend.presenter.contract.MyContract;
import com.qql.dagger.recommend.presenter.contract.ProductContract;
import com.qql.dagger.recommend.utils.SnackbarUtil;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.Tencent;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

/**
 * Created by qql on 2016/12/22.
 */

public class SellCenterFragment extends BaseFragment<ProductPresenter> implements ProductContract.View {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private StatusLayoutManager statusLayoutManager;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sell_center;
    }

    @Override
    protected void initEventAndData() {
        statusLayoutManager = new StatusLayoutManager.Builder(recyclerView)
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
                                }).build();
    }

    private void refresh() {
        // TODO: 2018/4/11 refresh
        mPresenter.list(1);
    }

    @Override
    public void list(List<Product> products) {

    }

    @Override
    public void delete(boolean result) {

    }

    @Override
    public void add(boolean result) {

    }

    @Override
    public void detail(boolean result) {

    }

    @Override
    public void modify(boolean result) {

    }

    @Override
    public void showError(String msg) {

    }
}
