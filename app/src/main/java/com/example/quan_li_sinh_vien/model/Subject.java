package com.example.quan_li_sinh_vien.model;

public class Subject {

    //các biến subject
    //id môn học
    private int id;
    //tên môn học
    private String subject_title;
    //mã học phần
    private String course_code;
    //id tín chỉ
    private int number_of_credit;
    //thời gian học
    private String time;
    //địa điểm
    private String place;

    //id major
    private int id_major;



//    public Subject(String subject_title, int number_of_credit, String time, String place) {
//        this.subject_title = subject_title;
//        this.number_of_credit = number_of_credit;
//        this.time = time;
//        this.place = place;
//    }
//
//    public Subject(String subject_title, int number_of_credit, String time, String place, int id_major) {
//        this.subject_title = subject_title;
//        this.number_of_credit = number_of_credit;
//        this.time = time;
//        this.place = place;
//        this.id_major = id_major;
//    }
//
//    public Subject(int id, String subject_title, int number_of_credit, String time, String place, int id_major) {
//        this.id = id;
//        this.subject_title = subject_title;
//        this.number_of_credit = number_of_credit;
//        this.time = time;
//        this.place = place;
//        this.id_major = id_major;
//    }

    public Subject(String subject_title, String course_code, int number_of_credit, String time, String place) {
        this.subject_title = subject_title;
        this.course_code = course_code;
        this.number_of_credit = number_of_credit;
        this.time = time;
        this.place = place;
    }

    public Subject(String subject_title, String course_code, int number_of_credit, String time, String place, int id_major) {
        this.subject_title = subject_title;
        this.course_code = course_code;
        this.number_of_credit = number_of_credit;
        this.time = time;
        this.place = place;
        this.id_major = id_major;
    }

    public Subject(int id, String subject_title, String course_code, int number_of_credit, String time, String place, int id_major) {
        this.id = id;
        this.subject_title = subject_title;
        this.course_code = course_code;
        this.number_of_credit = number_of_credit;
        this.time = time;
        this.place = place;
        this.id_major = id_major;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public int getId_major() {
        return id_major;
    }

    public void setId_major(int id_major) {
        this.id_major = id_major;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject_title() {
        return subject_title;
    }

    public void setSubject_title(String subject_title) {
        this.subject_title = subject_title;
    }

    public int getNumber_of_credit() {
        return number_of_credit;
    }

    public void setNumber_of_credit(int number_of_credit) {
        this.number_of_credit = number_of_credit;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
