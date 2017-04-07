package com.qql.dagger.recommend.cache;

import com.qql.dagger.recommend.App;
import com.qql.dagger.recommend.model.bean.CategoryBean;

import java.util.List;

/**
 * Created by T-46 on 2017/4/7.
 */

public class DataCache {
    private App app;
    private List<CategoryBean> categories;

    public DataCache(App app) {
        this.app = app;
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
}
