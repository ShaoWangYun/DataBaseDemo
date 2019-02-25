package com.imswy.databasedemo.litepal;

import com.imswy.databasedemo.bean.Student;

import org.litepal.LitePal;

import java.util.List;

public class LitepalDBOperator {

    public LitepalDBOperator() {
    }

    //增加一条学生数据
    public void addStudent(Student student_add) {
        Student student = new Student();
        student.setName(student_add.getName());
        student.setGender(student_add.getGender());
        student.setStudentID(student_add.getStudentID());
        student.setAge(student_add.getAge());
        student.save();
    }

    //更新某一学生的数据
    public void updateStudent(Student student_update) {
        Student student = new Student();
        student.setName(student_update.getName());
        student.setGender(student_update.getGender());
        student.setAge(student_update.getAge());
        student.updateAll("studentID = ?", student_update.getStudentID());
    }

    //删除某一学生的数据
    public void deleteStudent(String studentID) {
        LitePal.deleteAll(Student.class, "studentID = ?", studentID);
    }

    //判断当前的学生信息已经被录入
    public Student isExit(String studentID) {
        List<Student> list = LitePal.where("studentID = ?", studentID).find(Student.class);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    //获取数据库中student表中的所有数据
    public List<Student> findAllStudentData() {
        List<Student> list = LitePal.findAll(Student.class);
        return list;
    }

}
