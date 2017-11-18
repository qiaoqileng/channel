package com.qql.dagger.recommend.model.bean;

/**
 * Created by qql on 2017/10/26.
 */

public class TypeBean {
    private String type;
    private int resId;
    private String typeName;

    public TypeBean(String type, int resId, String typeName) {
        this.type = type;
        this.resId = resId;
        this.typeName = typeName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
