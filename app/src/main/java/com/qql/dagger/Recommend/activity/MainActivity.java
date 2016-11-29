package com.qql.dagger.recommend.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.qql.dagger.recommend.R;
import com.qql.dagger.recommend.adapter.RecyclerAdapter;
import com.qql.dagger.recommend.bean.Student;
import com.qql.dagger.recommend.dao.daoimpl.StuDaoImpl;
import com.qql.dagger.recommend.dao.inter.StuDao;
import com.qql.dagger.recommend.databinding.ActivityMainBinding;
import com.qql.dagger.recommend.inter.DaggerDaoStuComponent;
import com.qql.dagger.recommend.module.StuDaoModule;
import com.qql.dagger.recommend.utils.LogUtil;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends UMActivity {
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
        LogUtil.d("主线程:" + android.os.Process.myPid());
        recyclerView = binding.recyclerView;
//        testDatas();
        showList();
    }

    private void showList() {
        Observable.create(new Observable.OnSubscribe<List<Student>>() {
            @Override
            public void call(Subscriber<? super List<Student>> subscriber) {
                LogUtil.d("call线程:" + android.os.Process.myPid());
                subscriber.onNext(getData());
            }
        }).observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Student>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Student> students) {
                        LogUtil.d("onNext线程:" + android.os.Process.myPid());
                        initAdapter(students);
                    }
                });

    }

    private void initAdapter(List<Student> students) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecyclerAdapter(this, students));
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

    @Nullable
    private List<Student> getData() {
        try {
            DaggerDaoStuComponent.builder().stuDaoModule(getStuDaoModule()).build().inject(this);
            return dao.getAllStudent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setData(Student student) {
        try {
//            DaggerDaoStuComponent.builder().stuDaoModule(getStuDaoModule()).build().inject(this);
            dao = new StuDaoImpl();
            dao.insert(student);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private StuDaoModule getStuDaoModule(){
        return new StuDaoModule();
    }

    @Override
    protected void onDestroy() {
        if (dao != null) {
            dao.closeRealm();
        }
        super.onDestroy();
    }

    public class ClickListener {

        public void addItem(View view) {
            // TODO: 2016/11/28
        }

        public void deleteItem(View view) {
            // TODO: 2016/11/28
        }
    }
}
