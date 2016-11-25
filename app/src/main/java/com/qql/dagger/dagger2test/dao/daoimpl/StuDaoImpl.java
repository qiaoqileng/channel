package com.qql.dagger.dagger2test.dao.daoimpl;

import android.content.Context;

import com.qql.dagger.dagger2test.bean.Student;
import com.qql.dagger.dagger2test.dao.RealmUtils;
import com.qql.dagger.dagger2test.dao.inter.StuDao;

import java.sql.SQLException;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by qiao on 2016/11/23.
 */

public class StuDaoImpl implements StuDao {
    private Realm mRealm;

    public StuDaoImpl() {
        this.mRealm = RealmUtils.getInstance().getRealm();
    }

    @Override
    public void insert(Student student) throws SQLException {
        mRealm.beginTransaction();
        mRealm.copyToRealm(student);
        mRealm.commitTransaction();
    }

    @Override
    public List<Student> getAllStudent() throws SQLException {
        mRealm.beginTransaction();
        RealmResults<Student> students = mRealm.where(Student.class).findAll();
        students.sort("name", Sort.DESCENDING);
        mRealm.commitTransaction();
        return students;
    }

    @Override
    public Student updateStudent(Student student) throws SQLException {
        mRealm.beginTransaction();
        student = mRealm.copyToRealmOrUpdate(student);
        mRealm.commitTransaction();
        return student;
    }

    @Override
    public void updateStudent(String name1, String name2) throws SQLException {
        mRealm.beginTransaction();
        mRealm.where(Student.class).equalTo("name",name1).findFirst().setName(name2);
        mRealm.commitTransaction();
    }

    @Override
    public void deleteStudent(int id) throws SQLException {
        mRealm.beginTransaction();
        mRealm.where(Student.class).equalTo("id",id).findFirst().deleteFromRealm();
        mRealm.commitTransaction();
    }

    @Override
    public void insertStudentAsync(final Student student) throws SQLException {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.beginTransaction();
                realm.copyToRealm(student);
                realm.commitTransaction();
                realm.close();
            }
        });
    }

    @Override
    public Student findByNameOrAge(String name1, int age1) throws SQLException {
        mRealm.beginTransaction();
        Student student = mRealm.where(Student.class).equalTo("name",name1).or().equalTo("age",age1).findFirst();
        mRealm.commitTransaction();
        return student;
    }

    @Override
    public void deleteAll() throws SQLException {
        mRealm.beginTransaction();
        mRealm.where(Student.class).findAll().deleteAllFromRealm();
        mRealm.commitTransaction();
    }

    @Override
    public void closeRealm() {
        if (mRealm != null && !mRealm.isClosed()) {
            this.mRealm.close();
        }
    }
}
