package com.qql.dagger.recommend.model.bean;

import org.greenrobot.greendao.annotation.Id;

/**
 * Created by qql on 2017/3/31.
 */

public class BBBean {
    private long id;
    private String name;
    private String mainImgUrl;
    private int score;
    private long praise;
    private long collections;

    public BBBean() {
    }

    public BBBean(long id, String name, String mainImgUrl, int score, long praise, long collections) {
        this.id = id;
        this.name = name;
        this.mainImgUrl = mainImgUrl;
        this.score = score;
        this.praise = praise;
        this.collections = collections;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public long getPraise() {
        return praise;
    }

    public void setPraise(long praise) {
        this.praise = praise;
    }

    public long getCollections() {
        return collections;
    }

    public void setCollections(long collections) {
        this.collections = collections;
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
