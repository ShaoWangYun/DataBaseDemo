package com.imswy.databasedemo.bean;

import org.litepal.crud.LitePalSupport;

public class Student extends LitePalSupport{
    /**
     * 定义一个学生的实体类，作为单条数据的泛型
     * name 姓名
     * gender 性别
     * studentID 学号
     * age 年龄
     */
    private int id;
    private String name;
    private String gender;
    private String studentID;
    private int age;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Student() {
    }

    public Student(String name, String gender, String studentID, int age) {
        this.name = name;
        this.gender = gender;
        this.studentID = studentID;
        this.age = age;
    }

    public Student(int id, String name, String gender, String studentID, int age) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.studentID = studentID;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", studentID='" + studentID + '\'' +
                ", age=" + age +
                '}';
    }
}

