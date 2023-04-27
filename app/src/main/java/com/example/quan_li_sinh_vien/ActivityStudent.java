package com.example.quan_li_sinh_vien;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.quan_li_sinh_vien.adapter.adapterstudent;
import com.example.quan_li_sinh_vien.database.database;
import com.example.quan_li_sinh_vien.model.Student;

import java.util.ArrayList;

public class ActivityStudent extends AppCompatActivity {

    Toolbar toolbar;
    ListView listViewStudent;

    ArrayList<Student> ArrayListStudent;
    com.example.quan_li_sinh_vien.database.database database;
    com.example.quan_li_sinh_vien.adapter.adapterstudent adapterstudent;

    int id_subject = 0;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        toolbar = findViewById(R.id.toolbarStudent);
        listViewStudent = findViewById(R.id.listviewStudent);

        Intent intent = getIntent();
        id_subject = intent.getIntExtra("id_subject",0);

        //toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        database = new database(this);

        ArrayListStudent = new ArrayList<>();
        ArrayListStudent.clear();

        Cursor cursor = database.getDataStudent(id_subject);
        while (cursor.moveToNext()){
            int id_sub=cursor.getInt(5);
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String sex = cursor.getString(2);
            String code = cursor.getString(3);
            String birthday = cursor.getString(4);

            ArrayListStudent.add(new Student(id,name,sex,code,birthday,id_sub));

        }
        adapterstudent = new adapterstudent(ActivityStudent.this,ArrayListStudent);
        //hiển thị listview
        listViewStudent.setAdapter(adapterstudent);
        cursor.moveToFirst();
        cursor.close();
    }

    //Thêm icon add
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuaddstudent,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            //Chuyển qua màn hình add student
            case R.id.menuaddstudent:
                Intent intent = new Intent(ActivityStudent.this,ActivityAddStudent.class);
                intent.putExtra("id_subject",id_subject);
                startActivity(intent);
                break;

                //nút back chuyển qua subject activity
            default:
                Intent intent1 = new Intent(ActivityStudent.this,ActivitySubject.class);
                startActivity(intent1);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    //back trên điện thoại ra subject activity
    @Override
    public void onBackPressed() {
        count++;
        if(count>=1){
            Intent intent = new Intent(this,ActivitySubject.class);
            startActivity(intent);
            finish();
        }
    }

    public void information(final int pos){
        Cursor cursor = database.getDataStudent(id_subject);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);

            if(id == pos){
                Intent intent = new Intent(ActivityStudent.this,ActivityInformationStudent.class);

                intent.putExtra("id",pos);
                String name = cursor.getString(1);
                String sex = cursor.getString(2);
                String code = cursor.getString(3);
                String birth = cursor.getString(4);
                int id_subject = cursor.getInt(5);

                intent.putExtra("name",name);
                intent.putExtra("sex",sex);
                intent.putExtra("code",code);
                intent.putExtra("birth",birth);
                startActivity(intent);
            }
        }
        cursor.close();
    }

    //update
    public void update(final int id_student){
        Cursor cursor=database.getDataStudent(id_subject);

        while(cursor.moveToNext()){
            int id = cursor.getInt(0);

            if(id == id_student){
                Intent intent = new Intent(ActivityStudent.this,ActivityUpdateStudent.class);

                intent.putExtra("id",id_student);
                String name = cursor.getString(1);
                String sex = cursor.getString(2);
                String code = cursor.getString(3);
                String birth = cursor.getString(4);
                int id_subject = cursor.getInt(5);

                intent.putExtra("name",name);
                intent.putExtra("sex",sex);
                intent.putExtra("code",code);
                intent.putExtra("birth",birth);
                intent.putExtra("id_subject",id_subject);
                startActivity(intent);
            }
        }
        cursor.close();
    }

    //Xoá
    public void deleteStudent(final int id_student){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialogdeletestudent);
        dialog.setCanceledOnTouchOutside(false);
        Button btnYes = dialog.findViewById(R.id.buttonYesDeleteStudent);
        Button btnNo = dialog.findViewById(R.id.buttonNoDeleteStudent);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //xoá student trong database
                database.DeleteStudent(id_student);

                //mở lại activity student
                Intent intent = new Intent(ActivityStudent.this,ActivityStudent.class);
                intent.putExtra("id_subject",id_subject);
                startActivity(intent);
                Toast.makeText(ActivityStudent.this, "Xoá sinh viên thành công", Toast.LENGTH_SHORT).show();
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
}