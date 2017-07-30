package com.qql.dagger.recommend.model.bean;

/**
 * Created by qiao on 2016/12/23.
 */

public class BannerBean {
    private long id;
    /**
     * 图片地址
     */
    private String imageUrl;

    public BannerBean(long id, String imageUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
