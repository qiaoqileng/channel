package com.qql.dagger.dagger2test.module;

import com.qql.dagger.dagger2test.dao.daoimpl.StuDaoImpl;
import com.qql.dagger.dagger2test.dao.inter.StuDao;

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
