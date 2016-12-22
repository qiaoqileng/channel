package com.qql.dagger.recommend.presenter;

import com.qql.dagger.recommend.base.RxPresenter;
import com.qql.dagger.recommend.model.bean.VersionBean;
import com.qql.dagger.recommend.model.http.MyHttpResponse;
import com.qql.dagger.recommend.model.http.RetrofitHelper;
import com.qql.dagger.recommend.presenter.contract.MainContract;
import com.qql.dagger.recommend.utils.LogUtil;
import com.qql.dagger.recommend.utils.RxUtil;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by qql on 2016/12/22.
 */

public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter{

    private RetrofitHelper mRetrofitHelper;
    @Inject
    public MainPresenter(RetrofitHelper mRetrofitHelper) {
        this.mRetrofitHelper = mRetrofitHelper;
    }
    @Override
    public void checkVersion(final String currentVersion) {
        Subscription rxSubscription = mRetrofitHelper.fetchVersionInfo()
                .compose(RxUtil.<MyHttpResponse<VersionBean>>rxSchedulerHelper())
                .compose(RxUtil.<VersionBean>handleMyResult())
                .filter(new Func1<VersionBean, Boolean>() {
                    @Override
                    public Boolean call(VersionBean versionBean) {
                        return Integer.valueOf(currentVersion.replace(".", "")) < Integer.valueOf(versionBean.getCode().replace(".", ""));
                    }
                })
                .map(new Func1<VersionBean, String>() {
                    @Override
                    public String call(VersionBean bean) {
                        StringBuilder content = new StringBuilder("版本号: v");
                        content.append(bean.getCode());
                        content.append("\r\n");
                        content.append("版本大小: ");
                        content.append(bean.getSize());
                        content.append("\r\n");
                        content.append("更新内容:\r\n");
                        content.append(bean.getDes().replace("\\r\\n","\r\n"));
                        return content.toString();
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String versionContent) {
                        mView.showUpdateDialog(versionContent);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtil.d(throwable.toString());
                    }
                });
        addSubscrebe(rxSubscription);
    }


}
