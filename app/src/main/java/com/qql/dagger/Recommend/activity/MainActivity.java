package com.qql.dagger.recommend.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.qql.dagger.recommend.R;
import com.qql.dagger.recommend.adapter.RecyclerAdapter;
import com.qql.dagger.recommend.adapter.decoration.SpacesItemDecoration;
import com.qql.dagger.recommend.bean.Student;
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
    ActivityMainBinding binding;
    private RecyclerView recyclerView;
    private String[] testUrl = {
            "https://ss1.baidu.com/-4o3dSag_xI4khGko9WTAnF6hhy/image/h%3D200/sign=7b0b6a60972f070840052d00d925b865/d62a6059252dd42ad4e979840a3b5bb5c9eab839.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3245622106,3393111959&fm=116&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3360982095,837156739&fm=116&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2572418635,526691847&fm=111&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=155835295,818460970&fm=111&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=4024270163,1453867343&fm=11&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=2320358549,4024875240&fm=11&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=1645933247,1801190051&fm=11&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=1140917331,2673036058&fm=23&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=1216701244,3400018650&fm=23&gp=0.jpg",
    };
    int i = 0;
    private RecyclerAdapter adapter;

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
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
//        recyclerView.addItemDecoration(new SpacesItemDecoration(16));
        adapter = new RecyclerAdapter(this, students);
        recyclerView.setAdapter(adapter);
    }

    private void testDatas() {

        Observable.from(testUrl)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.d("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.printException("onerror", e);
                    }

                    @Override
                    public void onNext(String url) {
                        Student student = new Student(i, "qql" + i, i + 18, url);
                        i++;
                        LogUtil.d("onNext()  student:" + student);
                        setData(student);
                    }

                });
    }

    @Nullable
    private List<Student> getData() {
        try {
            DaggerDaoStuComponent.builder().build().inject(this);
            return dao.getAllStudent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setData(Student student) {
        try {
            DaggerDaoStuComponent.builder().build().inject(this);
            dao.insert(student);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
            LogUtil.d("add a item");
        }

        public void deleteItem(View view) {
            // TODO: 2016/11/28
        }
    }
}
