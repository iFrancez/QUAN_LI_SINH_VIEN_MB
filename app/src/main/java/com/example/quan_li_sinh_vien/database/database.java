package com.example.quan_li_sinh_vien.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.quan_li_sinh_vien.model.Student;
import com.example.quan_li_sinh_vien.model.Subject;

public class database extends SQLiteOpenHelper {

    //Tên database
    private static String DATABASE_NAME = "studentmanagement.db";
    //Bản Khoa
    private static String TABLE_MAJORS = "majors";
    private static String ID_MAJORS = "idmajors";
    private static String MAJORS_TITLE = "majorstitle";

    private static String MAJORS_CODE = "majorcode";

    private static int VERSION = 1;


    //Bảng môn học
    private static String TABLE_SUBJECTS = "subject";
    private static String ID_SUBJECTS = "idsubject";
    private static String SUBJECT_TITLE = "subjecttitle";
    private static String CREDITS = "credits";
    private static String TIME = "time";
    private static String PLACE = "place";
//    private static int VERSION = 1;

    //Bảng lớp
    private static String TABLE_CLASS = "class";
    private static String ID_CLASS = "idclass";
    private static String CLASS_TITLE = "classtitle";

    private static String CLASS_CODE = "classcode";


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


    //Tạo bảng khoa
    private String SQLQuery = "CREATE TABLE " + TABLE_MAJORS + " ( " + ID_MAJORS + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MAJORS_CODE + "TEXT, "
            + MAJORS_TITLE + " TEXT)";

    //Tạo bảng môn học
    private String SQLQuery2 = "CREATE TABLE " + TABLE_SUBJECTS + " ( " + ID_SUBJECTS + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + SUBJECT_TITLE + " TEXT, "
            + CREDITS + " INTEGER, "
            + TIME + " TEXT, "
            + PLACE + " TEXT )";

//            + PLACE + " TEXT, "
//            + ID_MAJORS + " INTEGER , FOREIGN KEY ( " + ID_MAJORS + " ) REFERENCES " +
//            TABLE_MAJORS + "(" + ID_MAJORS + "))";

    //Tạo bảng lớp
    private String SQLQuery3 = "CREATE TABLE " + TABLE_CLASS + " ( " + ID_CLASS + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CLASS_CODE + "TEXT, "
            + CLASS_TITLE + " TEXT, "
            + ID_SUBJECTS + " INTEGER , FOREIGN KEY ( " + ID_SUBJECTS + " ) REFERENCES " +
            TABLE_SUBJECTS + "(" + ID_SUBJECTS + "))";

    //Tạo bảng sinh viên
    private String SQLQuery4 = "CREATE TABLE "+ TABLE_STUDENT +" ( "+ID_STUDENT+" integer primary key AUTOINCREMENT, "
            +STUDENT_NAME+" TEXT, "
            +SEX+" TEXT, "
            +STUDENT_CODE+" TEXT, "
            +DATE_OF_BIRTH+" TEXT, "
            +ID_SUBJECTS+" INTEGER , FOREIGN KEY ( "+ ID_SUBJECTS +" ) REFERENCES "+
            TABLE_SUBJECTS+"("+ID_SUBJECTS+"))";

//            + STUDY_PROCESS + "REAL,"
//            + MID_TERM + "REAL,"
//            + END_TERM +"REAL,"
//            + ID_CLASS + " INTEGER , FOREIGN KEY ( " + ID_CLASS + " ) REFERENCES " +
//            TABLE_CLASS + "(" + ID_CLASS + "))";



    public database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        sqLiteDatabase.execSQL(SQLQuery);
        sqLiteDatabase.execSQL(SQLQuery2);
//        sqLiteDatabase.execSQL(SQLQuery3);
        sqLiteDatabase.execSQL(SQLQuery4);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    //insert subject
    public void AddSubject(Subject subject) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(SUBJECT_TITLE, subject.getSubject_title());
        values.put(CREDITS, subject.getNumber_of_credit());
        values.put(TIME, subject.getTime());
        values.put(PLACE, subject.getPlace());

        db.insert(TABLE_SUBJECTS, null, values);
        db.close();
    }


    //kiểm tra xem có tên môn học đó hay chưa
    public Boolean checkSubject(String subjectTitle){
        //projection: là một mảng các cột cần lấy ra từ bảng.
        //selection: là chuỗi điều kiện để chọn các bản ghi phù hợp.
        //selectionArgs: là một mảng các giá trị được truyền vào trong chuỗi điều kiện.
        //query(): là phương thức để thực hiện câu truy vấn trên bảng, và trả về một đối tượng Cursor chứa các bản ghi phù hợp.
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = { SUBJECT_TITLE };
        String selection = SUBJECT_TITLE.toLowerCase() + "=?";
        String[] selectionArgs = { subjectTitle.toLowerCase() };
        Cursor cursor = db.query(TABLE_SUBJECTS, projection, selection, selectionArgs, null, null, null);
        if(cursor.getCount()>0) return true;
        else return false;
    }

    //update subject
    public boolean UpdateSubject(Subject subject, int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Kiểm tra môn học đã tồn tại trong database hay chưa
        String subjectName = subject.getSubject_title();
        boolean isSubjectExists = checkSubject(subjectName);

        if (!isSubjectExists) {
            ContentValues values = new ContentValues();
            values.put(SUBJECT_TITLE, subjectName);
            values.put(CREDITS, subject.getNumber_of_credit());
            values.put(TIME, subject.getTime());
            values.put(PLACE, subject.getPlace());
            db.update(TABLE_SUBJECTS, values, ID_SUBJECTS + "=" + id, null);
            return true;
        } else {
            return false;
        }
    }



    //kiểm tra xem có tên sinh viên đó hay chưa
    public Boolean checkStudent(String codeStudent){
        //projection: là một mảng các cột cần lấy ra từ bảng.
        //selection: là chuỗi điều kiện để chọn các bản ghi phù hợp.
        //selectionArgs: là một mảng các giá trị được truyền vào trong chuỗi điều kiện.
        //query(): là phương thức để thực hiện câu truy vấn trên bảng, và trả về một đối tượng Cursor chứa các bản ghi phù hợp.
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = { STUDENT_CODE };
        String selection = STUDENT_CODE.toLowerCase() + "=?";
        String[] selectionArgs = { codeStudent.toLowerCase() };
        Cursor cursor = db.query(TABLE_STUDENT, projection, selection, selectionArgs, null, null, null);
        if(cursor.getCount()>0) return true;
        else return false;
    }

    //lấy giữ liệu subject
    public Cursor getDataSubject() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SUBJECTS, null);
        return cursor;
    }

    public int DeleteSubject(int i) {

        //Chú ý:
        //getWritableDatabase(): là cả đọc và ghi
        //getReadableDatabase(): chỉ đọc
        SQLiteDatabase db = this.getWritableDatabase();
        int res = db.delete(TABLE_SUBJECTS, ID_SUBJECTS + "=" + i, null);
        return res;
    }

    //dùng để xoá các student của subject
    public int DeleteSubjectStudent(int i){
        SQLiteDatabase db = this.getWritableDatabase();
        int res = db.delete(TABLE_STUDENT,ID_SUBJECTS+" = "+i,null);
        return res;
    }

    //insert student
    public void AddStudent(Student student){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(STUDENT_NAME,student.getStudent_name());
        values.put(SEX,student.getSex());
        values.put(STUDENT_CODE,student.getStudent_code());
        values.put(DATE_OF_BIRTH,student.getDate_of_birth());
        values.put(ID_SUBJECTS,student.getId_subject());

        db.insert(TABLE_STUDENT,null,values);
        db.close();
    }

    //Lấy tất cả sinh viên thuộc môn học đó
    public Cursor getDataStudent(int id_subject){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_STUDENT+" WHERE "+ID_SUBJECTS+" = "+id_subject,null);
        return res;
    }


    //Xoá student
    public int DeleteStudent(int i){
        SQLiteDatabase db = this.getWritableDatabase();
        int res = db.delete(TABLE_STUDENT,ID_STUDENT+"="+i,null);
        return res;
    }

    //cập nhật sv
    public boolean UpdateStudent(Student student,int id){
        SQLiteDatabase db = this.getWritableDatabase();

        // Kiểm tra sv đã tồn tại trong database hay chưa
        String studentName = student.getStudent_code();
        boolean isStudentExists = checkStudent(studentName);

        if(!isStudentExists) {
            ContentValues values = new ContentValues();
            values.put(STUDENT_NAME, student.getStudent_name());
            values.put(STUDENT_CODE, student.getStudent_code());
            values.put(SEX, student.getSex());
            values.put(DATE_OF_BIRTH, student.getDate_of_birth());
            db.update(TABLE_STUDENT, values, ID_STUDENT + " = " + id, null);
            return true;
        }
        else{
            return false;
        }
    }

}
