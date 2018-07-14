package com.qql.dagger.recommend.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qql.dagger.recommend.R;
import com.qql.dagger.recommend.activity.JoinUsActivity;
import com.qql.dagger.recommend.activity.MainActivity;
import com.qql.dagger.recommend.activity.SellerCenterActivity;
import com.qql.dagger.recommend.base.BaseFragment;
import com.qql.dagger.recommend.cache.DataCache;
import com.qql.dagger.recommend.model.entity.User;
import com.qql.dagger.recommend.option.GlideOptions;
import com.qql.dagger.recommend.presenter.HomePresenter;
import com.qql.dagger.recommend.presenter.MyPresenter;
import com.qql.dagger.recommend.presenter.contract.HomeContract;
import com.qql.dagger.recommend.presenter.contract.MyContract;
import com.qql.dagger.recommend.utils.SnackbarUtil;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.Tencent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qql on 2016/12/22.
 */

public class MyFragment extends BaseFragment<MyPresenter> implements MyContract.View {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.login)
    ImageView imageView;
    @BindView(R.id.join_in)
    TextView join_in;
    @BindView(R.id.seller_center)
    TextView seller_center;
    @BindView(R.id.download)
    TextView download;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initEventAndData() {
        initView();
        initLocalLogin();
    }

    private void initLocalLogin() {
        if (mPresenter.hasLocalLogin()){
            mPresenter.localLogin();
        }
    }

    private void initView(){
        mToolbar.inflateMenu(R.menu.menu_scrolling);
        mToolbar.bringToFront();
        //通过CollapsingToolbarLayout修改字体颜色
        mCollapsingToolbarLayout.setTitle("登录");
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的颜色
        mCollapsingToolbarLayout.setCollapsedTitleGravity(Gravity.CENTER);
        mCollapsingToolbarLayout.setCollapsedTitleTextAppearance(android.R.style.TextAppearance_DialogWindowTitle);
        mCollapsingToolbarLayout.setExpandedTitleTextAppearance(android.R.style.TextAppearance_DialogWindowTitle);
        seller_center.setVisibility(View.GONE);
        join_in.setVisibility(View.GONE);
        download.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.login,R.id.join_in,R.id.seller_center,R.id.download})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                mPresenter.login(this);
                break;
            case R.id.join_in:
                //  2017/12/15 申请入驻
                startActivity(new Intent(getActivity(),JoinUsActivity.class));
                break;
            case R.id.seller_center:
                //  2018/3/26 商家中心
                startActivity(new Intent(getActivity(),SellerCenterActivity.class));
                break;
            case R.id.download:
                startActivity(new Intent(getActivity(),MainActivity.class));
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN) {
            Tencent.onActivityResultData(requestCode,resultCode,data,mPresenter.getListener());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void showError(String msg) {
        SnackbarUtil.show(imageView,msg);
    }

    @Override
    public void loginResult(User user) {
        if (user != null){
            fillUserInfo(user);
        }
    }

    private void fillUserInfo(User user) {
        if (user != null){
            if (mCollapsingToolbarLayout != null)
                mCollapsingToolbarLayout.setTitle(user.getName());
            Glide.with(this).asBitmap().apply(GlideOptions.defaultOption()).load(user.getHead_url()).into(imageView);
            // TODO: 2018/3/26 屏蔽登陆按钮
            if (1 == user.getIs_saler()){
                seller_center.setVisibility(View.VISIBLE);
                join_in.setVisibility(View.GONE);
            } else {
                seller_center.setVisibility(View.GONE);
                join_in.setVisibility(View.VISIBLE);
            }
        }
    }
}
