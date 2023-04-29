package com.example.quan_li_sinh_vien.model;

public class Majors {
    //các biến Majors

    //id môn khoa
    private int id;

    //tên khoa
    private String majors_title;

    //mã khoa(ko đổi được khi update)
    private String majors_code;

    //trường
    private String majors_school;

    public Majors(int id, String majors_title, String majors_code, String majors_school) {
        this.id = id;
        this.majors_title = majors_title;
        this.majors_code = majors_code;
        this.majors_school = majors_school;
    }

    public Majors(String majors_title, String majors_code, String majors_school) {
        this.majors_title = majors_title;
        this.majors_code = majors_code;
        this.majors_school = majors_school;
    }

    public String getMajors_school() {
        return majors_school;
    }

    public void setMajors_school(String majors_school) {
        this.majors_school = majors_school;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMajors_title() {
        return majors_title;
    }

    public void setMajors_title(String majors_title) {
        this.majors_title = majors_title;
    }

    public String getMajors_code() {
        return majors_code;
    }

    public void setMajors_code(String majors_code) {
        this.majors_code = majors_code;
    }
}
