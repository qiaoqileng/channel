package com.qql.dagger.dagger2test.inter;

import com.qql.dagger.dagger2test.activity.MainActivity;
import com.qql.dagger.dagger2test.dao.inter.StuDao;
import com.qql.dagger.dagger2test.module.StuDaoModule;

import dagger.Component;

/**
 * Created by qiao on 2016/11/24.
 */

@Component(modules = StuDaoModule.class)
public interface DaoStuComponent {

    StuDao getStuDao();

    void inject(MainActivity mainActivity);
}
