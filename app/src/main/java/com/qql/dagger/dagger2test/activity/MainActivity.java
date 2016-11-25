package com.qql.dagger.dagger2test.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.qql.dagger.dagger2test.R;
import com.qql.dagger.dagger2test.bean.Student;
import com.qql.dagger.dagger2test.dao.inter.StuDao;
import com.qql.dagger.dagger2test.databinding.ActivityMainBinding;
import com.qql.dagger.dagger2test.inter.DaggerDaoStuComponent;
import com.qql.dagger.dagger2test.utils.LogUtil;

import java.sql.SQLException;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    @Inject
    StuDao dao;
    int i = 0;
    ActivityMainBinding binding;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setListener(new ClickListener());

        recyclerView = binding.recyclerView;
        testDatas();
    }

    private void testDatas() {
        Observable.range(1, 10, Schedulers.io()).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                LogUtil.d("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.printException("onerror", e);
            }

            @Override
            public void onNext(Integer integer) {
                Student student = new Student(integer, "qql", integer + 18);
                LogUtil.d("onNext()  student:" + student);
                setData(student);
            }
        });
    }

    private void setData(Student student) {
        try {
            DaggerDaoStuComponent.builder().build().inject(this);
            dao.insert(student);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.closeRealm();
        }
    }

    public class ClickListener {

        public void addItem(View view) {

        }

        public void deleteItem(View view) {

        }
    }
}
