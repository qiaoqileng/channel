package com.qql.dagger.dagger2test.bean;

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

    @Ignore   //表示忽视项,数据库不会存储该字段
    private boolean hasGrilFriend;//是否有女朋友

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

    @Override
    public String toString() {
        return "num:" + num + " name:" + name + " age:" + age + " hasGrilFriend:" + hasGrilFriend;
    }
}
