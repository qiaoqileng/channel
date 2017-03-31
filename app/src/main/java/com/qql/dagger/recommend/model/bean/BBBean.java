package com.qql.dagger.recommend.model.bean;

import org.greenrobot.greendao.annotation.Id;

/**
 * Created by qql on 2017/3/31.
 */

public class BBBean {
    private long id;
    private String name;
    private String mainImgUrl;

    public BBBean(long id, String name, String mainImgUrl) {
        this.id = id;
        this.name = name;
        this.mainImgUrl = mainImgUrl;
    }

    public BBBean() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMainImgUrl() {
        return mainImgUrl;
    }

    public void setMainImgUrl(String mainImgUrl) {
        this.mainImgUrl = mainImgUrl;
    }
}
