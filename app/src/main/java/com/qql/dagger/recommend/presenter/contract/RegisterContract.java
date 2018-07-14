package com.qql.dagger.recommend.presenter.contract;

import com.qql.dagger.recommend.base.BasePresenter;
import com.qql.dagger.recommend.base.BaseView;
import com.qql.dagger.recommend.model.bean.BBBean;
import com.qql.dagger.recommend.model.bean.CategoryBean;
import com.qql.dagger.recommend.model.bean.SMSMsg;

import java.util.List;
import java.util.Map;

/**
 * Created by qql on 2016/12/22.
 */

public interface RegisterContract {
    interface View extends BaseView {

        void showSMS(SMSMsg msg);
        void registerResult(boolean result);
    }

    interface Presenter extends BasePresenter<View> {

        void getSMSMsg(String mobile);
        void register(Map<String,String> params);
    }
}
