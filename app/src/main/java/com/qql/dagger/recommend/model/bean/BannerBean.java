package com.qql.dagger.recommend.model.bean;

/**
 * Created by qiao on 2016/12/23.
 */

public class BannerBean {
    private int id;
    /**
     * 图片地址
     */
    private String imageUrl;

    public BannerBean(int id, String imageUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
