package com.qql.dagger.recommend.component;

import android.app.Activity;

import com.qql.dagger.recommend.annotation.FragmentScope;
import com.qql.dagger.recommend.fragment.HomeFragment;
import com.qql.dagger.recommend.module.FragmentModule;

import dagger.Component;

/**
 * Created by qql on 2016/12/22.
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    Activity getActivity();

    void inject(HomeFragment fragment);

}
