package com.qql.dagger.dagger2test.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.qql.dagger.dagger2test.R;
import com.qql.dagger.dagger2test.bean.Student;
import com.qql.dagger.dagger2test.dao.inter.StuDao;
import com.qql.dagger.dagger2test.inter.DaggerDaoStuComponent;

import java.sql.SQLException;

import javax.inject.Inject;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {
    @Inject
    StuDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DaggerDaoStuComponent.builder().build().inject(this);
        initData();
    }

    private void initData() {
        try {
            for (int i = 0; i < 10; i++) {
                dao.insert(new Student(i, "qql", i + 18));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.closeRealm();
        }
    }
}
