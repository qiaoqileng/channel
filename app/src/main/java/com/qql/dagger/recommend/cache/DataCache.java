package com.qql.dagger.recommend.cache;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.qql.dagger.recommend.App;
import com.qql.dagger.recommend.base.RxPresenter;
import com.qql.dagger.recommend.model.bean.BBBean;
import com.qql.dagger.recommend.model.bean.CategoryBean;
import com.qql.dagger.recommend.model.entity.User;
import com.qql.dagger.recommend.model.http.GankHttpResponse;
import com.qql.dagger.recommend.model.http.RetrofitHelper;
import com.qql.dagger.recommend.utils.LogUtil;
import com.qql.dagger.recommend.utils.RxUtil;
import com.qql.dagger.recommend.utils.SharedPrefHelper;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by T-46 on 2017/4/7.
 */

public class DataCache extends RxPresenter{
    private final RetrofitHelper retrofitHelper;
    private App app;
    private final Tencent tencent;
    private List<CategoryBean> categories;
    private User user;
    private LoginListener loginListener;
    private String token;
    private String expires;
    private String openId;
    private SharedPrefHelper helper;
    private static final String KEY_OPEN_ID = "key_open_id";
    private static final String KEY_TOKEN = "key_token";
    private static final String KEY_EXPIRES = "key_expires";

    private IUiListener listener = new IUiListener() {
        @Override
        public void onComplete(Object o) {
            if (o == null){
                if (loginListener != null)
                    loginListener.showError("登陆失败");
            }
            LogUtil.d("asdasd"+o);
            if (o instanceof JSONObject){
                try {
                    token = ((JSONObject) o).getString(Constants.PARAM_ACCESS_TOKEN);
                    openId = ((JSONObject) o).getString(Constants.PARAM_OPEN_ID);
                    expires = ((JSONObject) o).getString(Constants.PARAM_EXPIRES_IN);
                    initOpenidAndToken(openId,token,expires);
                }catch (Exception e){
                    if (loginListener != null) {
                        loginListener.showError("登陆失败");
                    }
                }
            } else {
                if (loginListener != null)
                loginListener.showError("登陆失败");
            }
        }

        @Override
        public void onError(UiError uiError) {
            if (loginListener != null)
                loginListener.showError(uiError==null?"登陆失败":uiError.errorMessage);
        }

        @Override
        public void onCancel() {
            if (loginListener != null)
                loginListener.showError("cancel");
        }
    };

    public DataCache(App application, Tencent tencent, RetrofitHelper retrofitHelper) {
        this.app = application;
        this.tencent = tencent;
        this.retrofitHelper = retrofitHelper;
        helper = new SharedPrefHelper();
    }

    public List<CategoryBean> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryBean> categories) {
        this.categories = categories;
    }

    public boolean isCategoryEmpty(){
        return categories == null || categories.size() == 0;
    }

    public User getUser() {
        return user;
    }

    public void login(Fragment fragment,LoginListener loginListener){
        this.loginListener = loginListener;
        if (user != null){
            loginListener.success(user);
        } else {
            if (!tencent.isSessionValid()) {
                tencent.login(fragment,"all",listener);
            } else {
                localLogin(loginListener);
            }
        }
    }

    public void localLogin(LoginListener loginListener) {
        this.loginListener = loginListener;
        getLoginStatus();
        initOpenidAndToken(openId,token,expires);
    }

    public boolean checkLogin(){
        if (user != null){
            return true;
        } else {
            getLoginStatus();
            if (openId != null){
                return true;
            }
        }
        return false;
    }

    public IUiListener getListener() {
        return listener;
    }

    private void initOpenidAndToken(String openId, String token,String expires) {
        try {
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(openId) && !TextUtils.isEmpty(expires)) {
                tencent.setAccessToken(token, expires);
                tencent.setOpenId(openId);
            } else {
                tencent.logout(app);
                if (loginListener != null)
                    loginListener.showError("登陆失效，请重新登录");
                return;
            }
            saveLoginStatus(openId,token,expires);
            if (tencent.isSessionValid()) {
                IUiListener listener = new IUiListener() {

                    @Override
                    public void onError(UiError e) {
                        LogUtil.d(e);
                        if (loginListener != null)
                            loginListener.showError("登陆失败");
                    }

                    @Override
                    public void onComplete(final Object response) {
                        if (response == null || !(response instanceof JSONObject)){
                            if (loginListener != null)
                                loginListener.showError("登陆失败");
                        } else {
                            LogUtil.d("result:" + response);
                            processResult((JSONObject)response);
                        }
                    }

                    @Override
                    public void onCancel() {
                        if (loginListener != null)
                            loginListener.showError("cancel");
                    }
                };
                UserInfo mInfo = new UserInfo(app, tencent.getQQToken());
                mInfo.getUserInfo(listener);
            }
        } catch(Exception e) {
        }
    }

    private void saveLoginStatus(String openId, String token,String expires) {
        SharedPreferences.Editor editor = helper.getEditor();
        editor.putString(KEY_OPEN_ID,openId);
        editor.putString(KEY_TOKEN,token);
        editor.putString(KEY_EXPIRES,expires);
        editor.commit();
    }

    private void getLoginStatus(){
       openId = helper.getSp().getString(KEY_OPEN_ID,null);
       token = helper.getSp().getString(KEY_TOKEN,null);
        expires = helper.getSp().getString(KEY_EXPIRES,null);
    }

    private void processResult(JSONObject response) {
        Map<String,String> params = new HashMap<>();
        try {
            if (response.has("nickname")){
                params.put("user.name",response.getString("nickname"));
            }
            params.put("user.open_id",openId);
            params.put("user.third_name","qq");
            if (response.has("figureurl")){
                params.put("user.head_url",response.getString("figureurl"));
            }
            Subscription local = retrofitHelper.login(params)
                    .compose(RxUtil.<GankHttpResponse<User>>rxSchedulerHelper())
                    .compose(RxUtil.<User>handleResult())
                    .subscribe(new Action1<User>() {
                        @Override
                        public void call(User user) {
                            DataCache.this.user = user;
                            loginListener.success(user);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            loginListener.showError("数据提交失败ヽ(≧Д≦)ノ");
                        }
                    });
            addSubscrebe(local);
        } catch (JSONException e) {
            LogUtil.printException(e);
        }
    }

    public interface LoginListener{
        void showError(String error);
        void success(User user);
    }
}
