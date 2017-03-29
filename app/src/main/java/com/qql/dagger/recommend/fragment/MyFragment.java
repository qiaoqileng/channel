package com.qql.dagger.recommend.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.ali.auth.third.core.callback.LoginCallback;
import com.ali.auth.third.core.model.Session;
import com.ali.auth.third.login.LoginService;
import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.adapter.login.AlibcLogin;
import com.alibaba.baichuan.android.trade.callback.AlibcLoginCallback;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.qql.dagger.recommend.R;
import com.qql.dagger.recommend.animotion.ZoomInTransformer;
import com.qql.dagger.recommend.base.BaseFragment;
import com.qql.dagger.recommend.model.bean.BannerBean;
import com.qql.dagger.recommend.model.holder.NetImageHolder;
import com.qql.dagger.recommend.presenter.HomePresenter;
import com.qql.dagger.recommend.presenter.contract.HomeContract;
import com.qql.dagger.recommend.utils.SnackbarUtil;
import com.qql.dagger.recommend.utils.ToastUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qql on 2016/12/22.
 */

public class MyFragment extends Fragment {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.login)
    ImageView imageView;
    private View view;
    private String username;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my, null);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mToolbar.inflateMenu(R.menu.menu_scrolling);
        mToolbar.bringToFront();
        //使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
        mCollapsingToolbarLayout.setTitle(TextUtils.isEmpty(username)?"登录":username);
        //通过CollapsingToolbarLayout修改字体颜色
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的颜色
        mCollapsingToolbarLayout.setCollapsedTitleGravity(Gravity.CENTER);
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R.id.login)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                //TODO to login
                showLogin(view);
                break;
        }
    }
    public void showLogin(View view) {
        AlibcLogin.getInstance().showLogin(getActivity(), new AlibcLoginCallback() {
            @Override
            public void onSuccess() {
                ToastUtil.show("success");
            }

            @Override
            public void onFailure(int i, String s) {
                ToastUtil.show("failure");
            }
        });
//        LoginService loginService = AlibabaSDK.getService(LoginService.class);
//        loginService.showLogin(this, new LoginCallback() {
//
//            @Override
//            public void onSuccess(Session session) {
//                Toast.makeText(MyFragment.this, "-isLogin-"+session.isLogin()+"-UserId-"session.getUserId() + "-LoginTime-"+ session.getLoginTime()+"[user]:nick="+session.getUser().nick + "头像"+ session.getUser().avatarUrl,Toast.LENGTH_SHORT).show();
//            }
//            @Override
//            public void onFailure(int code, String message) {
//                Toast.makeText(MyFragment.this, "授权取消"+code+message,
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
