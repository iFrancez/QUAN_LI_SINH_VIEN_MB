package com.example.quan_li_sinh_vien.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.quan_li_sinh_vien.model.ClassSub;
import com.example.quan_li_sinh_vien.model.Majors;
import com.example.quan_li_sinh_vien.model.Student;
import com.example.quan_li_sinh_vien.model.Subject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class database extends SQLiteOpenHelper {

    //Tên database
    private static String DATABASE_NAME = "studentmanagement3.db";

    //Bản Khoa
    private static String TABLE_MAJORS = "major";
    private static String ID_MAJORS = "idmajors";
    private static String MAJORS_TITLE = "majorstitle";

    private static String MAJORS_CODE = "majorcode";

    private static String MAJORS_SCHOOL = "majorschool";

    private static int VERSION = 1;


    //Bảng môn học
    private static String TABLE_SUBJECTS = "subject";
    private static String ID_SUBJECTS = "idsubject";
    private static String SUBJECT_TITLE = "subjecttitle";
    private static String CREDITS = "credits";
    private static String TIME = "time";
    private static String PLACE = "place";
//    private static int VERSION = 1;

    //Bảng lớp và giảng viên
    private static String TABLE_CLASS = "class";
    private static String ID_CLASS = "idclass";
    private static String CLASS_TITLE = "classtitle";
    private static String CLASS_CODE = "classcode"; //mã lớp không được thay đổi

    private static String TEACHER_NAME = "teachername";

    private static String TEACHER_SEX = "teachersex";

    private static String TEACHER_CODE = "teachercode";

    private static String TEACHER_BIRTH = "teacherbirth";


    //Bảng sinh viên
    private static String TABLE_STUDENT = "student";
    private static String ID_STUDENT = "idstudent";
    private static String STUDENT_NAME = "sudentname";
    private static String SEX = "sex";
    private static String STUDENT_CODE = "studentcode";
    private static String DATE_OF_BIRTH = "dateofbirth";

    private static String STUDY_PROCESS = "studyofprocess";

    private static String MID_TERM = "mid";

    private static String END_TERM = "endterm";

    private static String AVEGE_TERM = "avegeterm";


    // NHỚ CÁCH MẤY CÁI KIỂU DỮ LIỆU RA KO LÀ LỖI Á VD: " TEXT "

    //Tạo bảng khoa
    private String SQLQuery = "CREATE TABLE " + TABLE_MAJORS + " ( " + ID_MAJORS + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MAJORS_TITLE + " TEXT, "
            + MAJORS_CODE + " TEXT, "
            + MAJORS_SCHOOL + " TEXT )";

    //Tạo bảng môn học
    private String SQLQuery2 = "CREATE TABLE " + TABLE_SUBJECTS + " ( " + ID_SUBJECTS + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + SUBJECT_TITLE + " TEXT, "
            + CREDITS + " INTEGER, "
            + TIME + " TEXT, "
            + PLACE + " TEXT, "
            + ID_MAJORS + " INTEGER , FOREIGN KEY ( " + ID_MAJORS + " ) REFERENCES " +
            TABLE_MAJORS + "(" + ID_MAJORS + "))";

    //Tạo bảng lớp
    private String SQLQuery3 = "CREATE TABLE " + TABLE_CLASS + " ( " + ID_CLASS + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CLASS_TITLE + " TEXT, "
            + CLASS_CODE + " TEXT, "
            + TEACHER_NAME + " TEXT, "
            + TEACHER_SEX + " TEXT, "
            + TEACHER_CODE + " TEXT, "
            + TEACHER_BIRTH + " TEXT, "
            + ID_SUBJECTS + " INTEGER , FOREIGN KEY ( " + ID_SUBJECTS + " ) REFERENCES " +
            TABLE_SUBJECTS + "(" + ID_SUBJECTS + "))";

    //Tạo bảng sinh viên
    private String SQLQuery4 = "CREATE TABLE " + TABLE_STUDENT + " ( " + ID_STUDENT + " integer primary key AUTOINCREMENT, "
            + STUDENT_NAME + " TEXT, "
            + SEX + " TEXT, "
            + STUDENT_CODE + " TEXT, "
            + DATE_OF_BIRTH + " TEXT, "
            + STUDY_PROCESS + " REAL DEFAULT 0, "
            + MID_TERM + " REAL DEFAULT 0, "
            + END_TERM +" REAL DEFAULT 0, "
            + AVEGE_TERM + " REAL DEFAULT 0, "
            + ID_CLASS + " INTEGER , FOREIGN KEY ( " + ID_CLASS + " ) REFERENCES " +
            TABLE_CLASS + "(" + ID_CLASS + "))";


    public database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQLQuery);
        sqLiteDatabase.execSQL(SQLQuery2);
        sqLiteDatabase.execSQL(SQLQuery3);
        sqLiteDatabase.execSQL(SQLQuery4);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //insert major
    public void AddMajor(Majors major) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(MAJORS_TITLE, major.getMajors_title());
        values.put(MAJORS_CODE, major.getMajors_code());
        values.put(MAJORS_SCHOOL, major.getMajors_school());

        db.insert(TABLE_MAJORS, null, values);
        db.close();
    }

    //update major
    public boolean UpdateMajor(Majors major, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MAJORS_TITLE, major.getMajors_title());
        values.put(MAJORS_CODE, major.getMajors_code());
        values.put(MAJORS_SCHOOL, major.getMajors_school());

        db.update(TABLE_MAJORS, values, ID_MAJORS + "=" + id, null);
        return true;
    }

    //lấy giữ liệu major
    public Cursor getDataMajor() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_MAJORS, null);
        return cursor;


    }

    //kiểm tra xem có mã ngành học đó hay chưa
    public Boolean checkMajor(String majorcode) {
        //projection: là một mảng các cột cần lấy ra từ bảng.
        //selection: là chuỗi điều kiện để chọn các bản ghi phù hợp.
        //selectionArgs: là một mảng các giá trị được truyền vào trong chuỗi điều kiện.
        //query(): là phương thức để thực hiện câu truy vấn trên bảng, và trả về một đối tượng Cursor chứa các bản ghi phù hợp.
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {MAJORS_CODE};
        String selection = "LOWER(" + MAJORS_CODE + ")=?";
        String[] selectionArgs = {majorcode.toLowerCase()};
        Cursor cursor = db.query(TABLE_MAJORS, projection, selection, selectionArgs, null, null, null);
        if (cursor.getCount() > 0) return true;
        else return false;
    }


    //xoá Major
    public int DeleteMajor(int i) {
        //Chú ý:
        //getWritableDatabase(): là cả đọc và ghi
        //getReadableDatabase(): chỉ đọc
        SQLiteDatabase db = this.getWritableDatabase();
        int res = db.delete(TABLE_MAJORS, ID_MAJORS + "=" + i, null);
        return res;
    }


    //thêm lớp học
    public void AddClass(ClassSub classSub) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CLASS_TITLE, classSub.getClass_name());
        values.put(CLASS_CODE, classSub.getClass_code());
        values.put(TEACHER_NAME, classSub.getTeacher_name());
        values.put(TEACHER_SEX, classSub.getTeacher_sex());
        values.put(TEACHER_CODE, classSub.getTeacher_code());
        values.put(TEACHER_BIRTH, classSub.getTeacher_birth());
        values.put(ID_SUBJECTS, classSub.getId_subject());

        db.insert(TABLE_CLASS, null, values);
        db.close();
    }

    //lấy giữ liệu class
    public Cursor getDataClass(int id_subject) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CLASS + " WHERE " + ID_SUBJECTS + " = " + id_subject, null);
        return cursor;
    }

    //xoá lớp học
    public int DeleteClass(int i) {

        SQLiteDatabase db = this.getWritableDatabase();
        int res = db.delete(TABLE_CLASS, ID_CLASS + "=" + i, null);
        return res;
    }

    //update lớp
    public boolean UpdateClass(ClassSub classSub,int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CLASS_TITLE,classSub.getClass_name());
        values.put(CLASS_CODE,classSub.getClass_code());
        values.put(TEACHER_NAME,classSub.getTeacher_name());
        values.put(TEACHER_SEX,classSub.getTeacher_sex());
        values.put(TEACHER_CODE,classSub.getTeacher_code());
        values.put(TEACHER_BIRTH,classSub.getTeacher_birth());
        db.update(TABLE_CLASS, values, ID_CLASS + "=" + id, null);
        return true;
    }
    //kiểm tra xem có mã giảng viên và mã lớp đó hay chưa
    public Boolean checkClass(String classCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {CLASS_CODE};
        String selection = "LOWER(" + CLASS_CODE + ")=?";
        String[] selectionArgs = {classCode.toLowerCase()};
        Cursor cursor = db.query(TABLE_CLASS, projection, selection, selectionArgs, null, null, null);
        if (cursor.getCount() > 0) return true;
        else return false;
    }


    //insert subject
    public void AddSubject(Subject subject) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SUBJECT_TITLE, subject.getSubject_title());
        values.put(CREDITS, subject.getNumber_of_credit());
        values.put(TIME, subject.getTime());
        values.put(PLACE, subject.getPlace());
        values.put(ID_MAJORS, subject.getId_major());
        db.insert(TABLE_SUBJECTS, null, values);
        db.close();
    }


    //kiểm tra xem có tên môn học đó hay chưa
    public Boolean checkSubject(String subjectTitle) {
        //projection: là một mảng các cột cần lấy ra từ bảng.
        //selection: là chuỗi điều kiện để chọn các bản ghi phù hợp.
        //selectionArgs: là một mảng các giá trị được truyền vào trong chuỗi điều kiện.
        //query(): là phương thức để thực hiện câu truy vấn trên bảng, và trả về một đối tượng Cursor chứa các bản ghi phù hợp.
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {SUBJECT_TITLE};
        String selection = "LOWER(" + SUBJECT_TITLE + ")=?";
        String[] selectionArgs = {subjectTitle.toLowerCase()};
        Cursor cursor = db.query(TABLE_SUBJECTS, projection, selection, selectionArgs, null, null, null);
        if (cursor.getCount() > 0) return true;
        else return false;
    }

    //update subject
    public boolean UpdateSubject(Subject subject, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SUBJECT_TITLE, subject.getSubject_title());
        values.put(CREDITS, subject.getNumber_of_credit());
        values.put(TIME, subject.getTime());
        values.put(PLACE, subject.getPlace());
        db.update(TABLE_SUBJECTS, values, ID_SUBJECTS + "=" + id, null);
        return true;
    }


    //lấy giữ liệu subject
    public Cursor getDataSubject(int id_major) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SUBJECTS + " WHERE " + ID_MAJORS + " = " + id_major, null);
        return cursor;
    }

    //xoá subject
    public int DeleteSubject(int i) {
        //Chú ý:
        //getWritableDatabase(): là cả đọc và ghi
        //getReadableDatabase(): chỉ đọc
        SQLiteDatabase db = this.getWritableDatabase();
        int res = db.delete(TABLE_SUBJECTS, ID_SUBJECTS + "=" + i, null);
        return res;
    }


    //kiểm tra xem có tên sinh viên đó hay chưa
    public Boolean checkStudent(String codeStudent) {
        //projection: là một mảng các cột cần lấy ra từ bảng.
        //selection: là chuỗi điều kiện để chọn các bản ghi phù hợp.
        //selectionArgs: là một mảng các giá trị được truyền vào trong chuỗi điều kiện.
        //query(): là phương thức để thực hiện câu truy vấn trên bảng, và trả về một đối tượng Cursor chứa các bản ghi phù hợp.
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {STUDENT_CODE};
        String selection = "LOWER(" + STUDENT_CODE + ")=?";
        String[] selectionArgs = {codeStudent.toLowerCase()};
        Cursor cursor = db.query(TABLE_STUDENT, projection, selection, selectionArgs, null, null, null);
        if (cursor.getCount() > 0) return true;
        else return false;
    }
    //insert student
    public void AddStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STUDENT_NAME, student.getStudent_name());
        values.put(SEX, student.getSex());
        values.put(STUDENT_CODE, student.getStudent_code());
        values.put(DATE_OF_BIRTH, student.getDate_of_birth());
        values.put(ID_CLASS, student.getId_class());
        db.insert(TABLE_STUDENT, null, values);
        db.close();
    }


    //Lấy tất cả sinh viên thuộc môn học đó
    public Cursor getDataStudent(int id_subject) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_STUDENT + " WHERE " + ID_CLASS + " = " + id_subject, null);
        return res;
    }


    //Xoá student
    public int DeleteStudent(int i) {
        SQLiteDatabase db = this.getWritableDatabase();
        int res = db.delete(TABLE_STUDENT, ID_STUDENT + "=" + i, null);
        return res;
    }

    //cập nhật sv
    public boolean UpdateStudent(Student student, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STUDENT_NAME, student.getStudent_name());
        values.put(STUDENT_CODE, student.getStudent_code());
        values.put(SEX, student.getSex());
        values.put(DATE_OF_BIRTH, student.getDate_of_birth());
        values.put(STUDY_PROCESS, student.getPoint_process());
        values.put(MID_TERM,student.getMidterm_score());
        values.put(END_TERM,student.getFinal_score());
        values.put(AVEGE_TERM,student.getAvege_score());
        db.update(TABLE_STUDENT, values, ID_STUDENT + " = " + id, null);
        boolean isStudentDate = isValidDate(student.getDate_of_birth());
        if (isStudentDate) {
            return true;
        } else {
            return false;
        }
    }

    //kiểm tra xem ngày sinh hợp lệ hay không
    private boolean isValidDate(String input) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        format.setLenient(false);
        try {
            Date date = format.parse(input);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH) + 1;
            int year = calendar.get(Calendar.YEAR);
            if (year < 0) {
                return false;
            }
            if (month < 1 || month > 12) {
                return false;
            }
            if (day < 1 || day > 31) {
                return false;
            }
            if (month == 4 || month == 6 || month == 9 || month == 11) {
                return (day <= 30);
            }
            if (month == 2) {
                if (year % 4 == 0) {
                    return (day <= 29);
                } else {
                    return (day <= 28);
                }
            }
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

}
