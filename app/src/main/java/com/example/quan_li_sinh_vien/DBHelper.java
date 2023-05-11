package com.example.quan_li_sinh_vien;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



import androidx.annotation.Nullable;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "Login.db";

    public DBHelper(Context context) {
        super(context, "Login.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users(username TEXT primary key,password TEXT, number INTEGER, date DATE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
    }

    public Boolean insertData(String username, String password, int number, String date){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("username",username);
        //mã hoá mật khẩu
        contentValues.put("password", BCrypt.withDefaults().hashToString(12, password.toCharArray()));
        contentValues.put("number", number);
        contentValues.put("date", date);

        long result = MyDB.insert("users",null,contentValues);
        if(result==-1)return false;
        else
            return true;
    }



    public Boolean checkusername(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?",new String[]{username});
        if(cursor.getCount()>0) return true;
        else return false;
    }
    public boolean checkusernamepassword(String username, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT password FROM users WHERE username = ?", new String[]{username});
        if (cursor.moveToFirst()) {
            String hashedPassword = cursor.getString(0);
            //so sánh nếu giống mã hoá thì return
            return BCrypt.verifyer().verify(password.toCharArray(), hashedPassword).verified;
        }
        return false;
    }


}
