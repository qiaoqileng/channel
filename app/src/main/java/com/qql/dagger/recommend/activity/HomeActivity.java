package com.qql.dagger.recommend.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.qql.dagger.recommend.R;
import com.qql.dagger.recommend.base.BaseActivity;
import com.qql.dagger.recommend.fragment.HomeFragment;
import com.qql.dagger.recommend.fragment.SimpleCardFragment;
import com.qql.dagger.recommend.model.entity.TabEntity;
import com.qql.dagger.recommend.presenter.MainPresenter;
import com.qql.dagger.recommend.presenter.contract.MainContract;
import com.qql.dagger.recommend.utils.SnackbarUtil;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by qql on 2016/12/22.
 */

public class HomeActivity extends BaseActivity<MainPresenter> implements MainContract.View{
    @BindView(R.id.tl)
    CommonTabLayout commonTabLayout;
    @BindView(R.id.vp)
    ViewPager viewPager;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "消息", "联系人", "更多"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private int[] mIconUnselectIds = {
            R.mipmap.tab_home_unselect, R.mipmap.tab_speech_unselect,
            R.mipmap.tab_contact_unselect, R.mipmap.tab_more_unselect};
    private int[] mIconSelectIds = {
            R.mipmap.tab_home_select, R.mipmap.tab_speech_select,
            R.mipmap.tab_contact_select, R.mipmap.tab_more_select};

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void initEventAndData() {
        for (String title : mTitles) {
            if ("首页".equals(title)){
                mFragments.add(new HomeFragment());
            }else {
                mFragments.add(SimpleCardFragment.getInstance("Switch ViewPager " + title));
            }
        }
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        commonTabLayout.setTabData(mTabEntities);
        commonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                commonTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(0);
    }

    @Override
    public void showError(String msg) {
        SnackbarUtil.show(toolBar,msg);
    }

    @Override
    public void showUpdateDialog(String versionContent) {
        //TODO
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
