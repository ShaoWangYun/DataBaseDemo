package com.imswy.databasedemo.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.imswy.databasedemo.bean.Student;
import java.util.ArrayList;
import java.util.List;

public class SQLiteDBOperator {

    private SQLiteDBHelper sqLiteDBHelper;
    private SQLiteDatabase db;

    public SQLiteDBOperator(Context context) {
        //数据库名：db_sqlite
        sqLiteDBHelper = new SQLiteDBHelper(context, "db_sqlite", null, 1);
        //初始化数据库操作对象
        db = sqLiteDBHelper.getWritableDatabase();
    }
    //增加一条学生数据
    public void addStudent(Student student_add) {
        //这里因为id设置为自增，那么就不设置，也是因此插入数据时必须要指明字段，不能使用默认的字段（含id，但是没有id数据），会报错
        db.execSQL("insert into Student(name,gender,studentID,age) values(?,?,?,?)"
                , new Object[] { student_add.getName(), student_add.getGender(), student_add.getStudentID(),student_add.getAge() });
    }

    //更新某一学生的数据
    public void updateStudent(Student student_update) {
        db.execSQL("update Student set name=?,gender=?,age=? where studentID=?",
                new Object[] { student_update.getName(), student_update.getGender(), student_update.getAge(), student_update.getStudentID() });
    }

    //删除某一学生的数据
    public void deleteStudent(String studentID) {
        db.execSQL("delete from Student where studentID=?", new String[] { studentID });
    }

    //判断当前的学生信息已经被录入
    public Student isExit(String studentID) {
        Student student = null;
        Cursor c = db.rawQuery("select * from Student where studentID= ?", new String[] { studentID });
        while (c.moveToNext()) {
            student = new Student();
            student.setName(c.getString(1));
            student.setGender(c.getString(2));
            student.setStudentID(c.getString(3));
            student.setAge(c.getInt(4));
        }
        c.close();
        return student;
    }

    //获取数据库中student表中的所有数据
    public List<Student> findAllStudentData() {
        List<Student> list = new ArrayList<>();
        Cursor c = db.rawQuery("select * from Student", null);
        while (c.moveToNext()) {
            Student student = new Student();
            student.setName(c.getString(1));
            student.setGender(c.getString(2));
            student.setStudentID(c.getString(3));
            student.setAge(c.getInt(4));
            list.add(student);
        }
        c.close();
        return list;
    }

}
