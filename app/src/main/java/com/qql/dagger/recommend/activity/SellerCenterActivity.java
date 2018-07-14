package com.qql.dagger.recommend.activity;

import android.support.v7.widget.RecyclerView;

import com.qql.dagger.recommend.R;
import com.qql.dagger.recommend.base.SimpleActivity;
import com.qql.dagger.recommend.fragment.SellCenterFragment;

import butterknife.BindView;

/**
 * 商家中心
 * Created by qql on 2018/3/26.
 */

public class SellerCenterActivity extends SimpleActivity{
    @Override
    protected int getLayout() {
        return R.layout.activity_seller_center;
    }

    @Override
    protected void initEventAndData() {
        setTitle("商家中心");
        getSupportFragmentManager().beginTransaction().add(R.id.content,new SellCenterFragment()).commit();
    }
}
