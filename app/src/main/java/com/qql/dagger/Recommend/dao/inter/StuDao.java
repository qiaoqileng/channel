package com.qql.dagger.recommend.dao.inter;

import com.qql.dagger.recommend.bean.Student;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by qiao on 2016/11/23.
 */

public interface StuDao {
     /**
       * 插入一个用户
       * @param student    需要插入的用户对象
       * @throws SQLException
       */
     void insert(Student student) throws SQLException;

     /**
       * 获得所有的用户列表
       * @return 用户列表
       * @throws SQLException
       */
      List<Student> getAllStudent() throws SQLException;

     /**
       * 更新一个用户
       * @param student 需要更新的用户类
       * @return      更新后的对象
       * @throws SQLException
       */
      Student updateStudent(Student student) throws SQLException;

    /**
      * 根据姓名修改新姓名
      * @param name1 老名字
      * @param name2 新名字
      * @throws SQLException
      */
     void updateStudent(String name1,String name2) throws SQLException;

    /**
      * 根据id删除用户
      * @param id 用户主键
      * @throws SQLException
      */
     void deleteStudent(int id) throws SQLException;

    /**
      * 异步添加用户
      * @param student 需要添加的用户对象
      * @throws SQLException
      */
     void insertStudentAsync(Student student) throws SQLException;

    /**
      * 按名字或者年龄查找第一个Student
      */
     Student findByNameOrAge(String name1,int age1) throws SQLException;

    /**
      * 清楚所有
      * @throws SQLException
      */
     void deleteAll() throws SQLException;

    /**
      * 关闭事务
      */
     void closeRealm();
}
