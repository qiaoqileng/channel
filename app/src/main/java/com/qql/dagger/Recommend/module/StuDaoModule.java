package com.qql.dagger.recommend.module;

import android.content.Context;

import com.qql.dagger.recommend.dao.daoimpl.StuDaoImpl;
import com.qql.dagger.recommend.dao.inter.StuDao;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

/**
 * Created by qiao on 2016/11/24.
 */
@Module
public class StuDaoModule {

    @Provides
    StuDao getStuDao() {
        return new StuDaoImpl();
    }
}
