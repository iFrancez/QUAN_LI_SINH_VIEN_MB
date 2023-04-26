package com.example.quan_li_sinh_vien.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.quan_li_sinh_vien.model.Subject;

public class database extends SQLiteOpenHelper {

    //Tên database
    private static String DATABASE_NAME = "studentmanagement";
    //Bản Khoa
    private static String TABLE_MAJORS = "majors";
    private static String ID_MAJORS = "idmajors";
    private static String MAJORS_TITLE = "majorstitle";

    //Bảng môn học
    private static String TABLE_SUBJECTS = "subject";
    private static String ID_SUBJECTS = "idsubject";
    private static String SUBJECT_TITLE = "subjecttitle";
    private static String CREDITS = "credits";
    private static String TIME = "time";
    private static String PLACE = "place";
    private static int VERSION = 1;

    //Bảng lớp
    private static String TABLE_CLASS = "class";
    private static String ID_CLASS = "idclass";
    private static String CLASS_TITLE = "classtitle";


    //Bảng sinh viên
    private static String TABLE_STUDENT = "student";
    private static String ID_STUDENT = "idstudent";
    private static String STUDENT_NAME = "sudentname";
    private static String SEX = "sex";
    private static String STUDENT_CODE = "studentcode";
    private static String DATE_OF_BIRTH = "dateofbirth";

    private static String STUDY_PROCESS = "studyofprocess";

    private static String MID_TERM ="mid";

    private static String END_TERM ="end";




    //Tạo bảng khoa
    private String SQLQuery = "CREATE TABLE " + TABLE_MAJORS + " ( " + ID_MAJORS + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MAJORS_TITLE + " TEXT) ";

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
            +ID_SUBJECTS+" INTEGER , FOREIGN KEY ( "+ ID_SUBJECTS +" ) REFERENCES "+
            TABLE_SUBJECTS+"("+ID_SUBJECTS+"))";

    //Tạo bảng sinh viên
    private String SQLQuery4 = "CREATE TABLE " + TABLE_STUDENT + " ( " + ID_STUDENT + " integer primary key AUTOINCREMENT, "
            + STUDENT_NAME + " TEXT, "
            + SEX + " TEXT, "
            + STUDENT_CODE + " TEXT, "
            + DATE_OF_BIRTH + " TEXT, "
            + STUDY_PROCESS + "REAL,"
            + MID_TERM + "REAL,"
            + END_TERM +"REAL,"
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


    //insert subject
    public void AddSubject(Subject subject){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(SUBJECT_TITLE,subject.getSubject_title());
        values.put(CREDITS,subject.getNumber_of_credit());
        values.put(TIME,subject.getTime());
        values.put(PLACE,subject.getPlace());

        db.insert(TABLE_SUBJECTS,null,values);
        db.close();
    }

    //update subject
    public boolean UpdateSubject(Subject subject,int id){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SUBJECT_TITLE,subject.getSubject_title());
        values.put(CREDITS,subject.getNumber_of_credit());
        values.put(TIME,subject.getTime());
        values.put(PLACE,subject.getPlace());

        db.update(TABLE_SUBJECTS,values,ID_SUBJECTS+"="+id,null);
        return true;
    }

    public Cursor getDataSubject(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_SUBJECTS,null);
        return cursor;
    }

    public int DeleteSubject(int i){

        //Chú ý:
        //getWritableDatabase(): là cả đọc và ghi
        //getReadableDatabase(): chỉ đọc
        SQLiteDatabase db = this.getWritableDatabase();
        int res = db.delete(TABLE_SUBJECTS,ID_SUBJECTS+"="+i,null);
        return res;
    }
}
