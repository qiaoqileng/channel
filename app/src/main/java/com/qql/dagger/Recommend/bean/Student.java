package com.qql.dagger.recommend.bean;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by qiao on 2016/11/23.
 */

public class Student extends RealmObject {
    /**
     * 学号
     */
    @PrimaryKey
    private int num;
    /**
     * 姓名
     */
    @Required
    private String name;
    /**
     * 年龄
     */
    private int age;

    private int height;

    @Ignore   //表示忽视项,数据库不会存储该字段
    private boolean hasGrilFriend;//是否有女朋友
    /**
     * 头像
     */
    private String url;

    public Student() {
    }

    public Student(String name, int num) {
        this.name = name;
        this.num = num;
    }

    public Student(int num, String name, int age) {
        this.num = num;
        this.name = name;
        this.age = age;
    }

    public Student(int num, String name, String url) {
        this.num = num;
        this.name = name;
        this.url = url;
    }

    public Student(int num, String name, int age, String url) {
        this.num = num;
        this.name = name;
        this.age = age;
        this.url = url;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isHasGrilFriend() {
        return hasGrilFriend;
    }

    public void setHasGrilFriend(boolean hasGrilFriend) {
        this.hasGrilFriend = hasGrilFriend;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "num:" + num + " name:" + name + " age:" + age + " hasGrilFriend:" + hasGrilFriend + " height:" + height;
    }
}
