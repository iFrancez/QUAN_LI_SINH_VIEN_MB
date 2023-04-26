package com.example.quan_li_sinh_vien.model;

public class Majors {
    //các biến Majors
    //id môn khoa
    private int id;
    //tên môn khoa
    private String Majors_title;

    public Majors(String majors_title) {
        Majors_title = majors_title;
    }

    public Majors(int id, String majors_title) {
        this.id = id;
        Majors_title = majors_title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMajors_title() {
        return Majors_title;
    }

    public void setMajors_title(String majors_title) {
        Majors_title = majors_title;
    }
}
