package com.example.quan_li_sinh_vien.model;

public class ClassSub {
    private int id_class;
    private String class_name;
    private String class_code; //ko đổi
    private String teacher_name;
    private String teacher_sex;
    private String teacher_code; //ko đổi
    private String teacher_birth;
    private int id_subject;

    public ClassSub(String class_name, String class_code, String teacher_name, String teacher_sex, String teacher_code, String teacher_birth, int id_subject) {
        this.class_name = class_name;
        this.class_code = class_code;
        this.teacher_name = teacher_name;
        this.teacher_sex = teacher_sex;
        this.teacher_code = teacher_code;
        this.teacher_birth = teacher_birth;
        this.id_subject = id_subject;
    }

    public ClassSub(int id_class, String class_name, String class_code, String teacher_name, String teacher_sex, String teacher_code, String teacher_birth, int id_subject) {
        this.id_class = id_class;
        this.class_name = class_name;
        this.class_code = class_code;
        this.teacher_name = teacher_name;
        this.teacher_sex = teacher_sex;
        this.teacher_code = teacher_code;
        this.teacher_birth = teacher_birth;
        this.id_subject = id_subject;
    }

    public ClassSub(String class_name, String class_code, String teacher_name, String teacher_sex, String teacher_code, String teacher_birth) {
        this.class_name = class_name;
        this.class_code = class_code;
        this.teacher_name = teacher_name;
        this.teacher_sex = teacher_sex;
        this.teacher_code = teacher_code;
        this.teacher_birth = teacher_birth;
    }

    public int getId_class() {
        return id_class;
    }

    public void setId_class(int id_class) {
        this.id_class = id_class;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getClass_code() {
        return class_code;
    }

    public void setClass_code(String class_code) {
        this.class_code = class_code;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public String getTeacher_sex() {
        return teacher_sex;
    }

    public void setTeacher_sex(String teacher_sex) {
        this.teacher_sex = teacher_sex;
    }

    public String getTeacher_code() {
        return teacher_code;
    }

    public void setTeacher_code(String teacher_code) {
        this.teacher_code = teacher_code;
    }

    public String getTeacher_birth() {
        return teacher_birth;
    }

    public void setTeacher_birth(String teacher_birth) {
        this.teacher_birth = teacher_birth;
    }

    public int getId_subject() {
        return id_subject;
    }

    public void setId_subject(int id_subject) {
        this.id_subject = id_subject;
    }
}
