package com.example.quan_li_sinh_vien.model;

public class Student {
    private int id_student;
    private String student_name;
    private String sex;
    private String student_code;
    private String date_of_birth;
    private int id_class;

    //điểm quá trình, giữa kì, cuối kì, điểm trung bình
    private float point_process;
    private float midterm_score;
    private float final_score;

    private float avege_score;

    public Student(String student_name, String sex, String student_code, String date_of_birth,  float point_process, float midterm_score, float final_score, float avege_score,int id_class) {
        this.student_name = student_name;
        this.sex = sex;
        this.student_code = student_code;
        this.date_of_birth = date_of_birth;
        this.point_process = point_process;
        this.midterm_score = midterm_score;
        this.final_score = final_score;
        this.avege_score = avege_score;
        this.id_class = id_class;
    }

    public Student(int id_student, String student_name, String sex, String student_code, String date_of_birth, float point_process, float midterm_score, float final_score, float avege_score,int id_class) {
        this.id_student = id_student;
        this.student_name = student_name;
        this.sex = sex;
        this.student_code = student_code;
        this.date_of_birth = date_of_birth;
        this.id_class = id_class;
        this.point_process = point_process;
        this.midterm_score = midterm_score;
        this.final_score = final_score;
        this.avege_score = avege_score;
    }

    public Student(String student_name, String sex, String student_code, String date_of_birth) {
        this.student_name = student_name;
        this.sex = sex;
        this.student_code = student_code;
        this.date_of_birth = date_of_birth;
    }

    public Student(String student_name, String sex, String student_code, String date_of_birth, int id_class) {
        this.student_name = student_name;
        this.sex = sex;
        this.student_code = student_code;
        this.date_of_birth = date_of_birth;
        this.id_class = id_class;
    }

    public Student(int id_student, String student_name, String sex, String student_code, String date_of_birth, int id_class) {
        this.id_student = id_student;
        this.student_name = student_name;
        this.sex = sex;
        this.student_code = student_code;
        this.date_of_birth = date_of_birth;
        this.id_class = id_class;
    }

    public int getId_student() {
        return id_student;
    }

    public void setId_student(int id_student) {
        this.id_student = id_student;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getStudent_code() {
        return student_code;
    }

    public void setStudent_code(String student_code) {
        this.student_code = student_code;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public float getAvege_score() {
        return avege_score;
    }

    public void setAvege_score(float avege_score) {
        this.avege_score = avege_score;
    }

    public float getPoint_process() {
        return point_process;
    }

    public void setPoint_process(float point_process) {
        this.point_process = point_process;
    }

    public float getMidterm_score() {
        return midterm_score;
    }

    public void setMidterm_score(float midterm_score) {
        this.midterm_score = midterm_score;
    }

    public float getFinal_score() {
        return final_score;
    }

    public void setFinal_score(float final_score) {
        this.final_score = final_score;
    }


    public int getId_class() {
        return id_class;
    }

    public void setId_class(int id_class) {
        this.id_class = id_class;
    }


}
