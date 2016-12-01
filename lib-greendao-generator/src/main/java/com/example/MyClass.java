package com.example;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class MyClass {
    public static void main(String[] args) throws Exception {
        //参数1是版本号
        //参数2是生成文件的包
        Schema schema = new Schema(1, "com.green.dao.output");
        addNewsDetail(schema);
        try {
            //后面的目录是在gradle中配置的路径
            new DaoGenerator().generateAll(schema, "app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建表
     * 生成dao和bean
     *
     * @param schema
     */
    private static void addNewsDetail(Schema schema) {
        Entity newsDetail = schema.addEntity("Student");
        newsDetail.setHasKeepSections(true);
        newsDetail.addStringProperty("id").primaryKey().index();
        newsDetail.addStringProperty("name");
        newsDetail.addStringProperty("url");
        newsDetail.addIntProperty("age");
    }
}
