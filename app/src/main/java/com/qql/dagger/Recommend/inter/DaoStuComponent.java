package com.qql.dagger.recommend.inter;

import com.qql.dagger.recommend.activity.MainActivity;
import com.qql.dagger.recommend.dao.inter.StuDao;
import com.qql.dagger.recommend.module.StuDaoModule;

import dagger.Component;

/**
 * Created by qiao on 2016/11/24.
 */

@Component(modules = StuDaoModule.class)
public interface DaoStuComponent {

//    StuDao getStuDao();

    void inject(MainActivity mainActivity);
}
