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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.quan_li_sinh_vien.adapter.adapterstudent;
import com.example.quan_li_sinh_vien.adapter.adaptersubject;
import com.example.quan_li_sinh_vien.database.database;
import com.example.quan_li_sinh_vien.model.Majors;
import com.example.quan_li_sinh_vien.model.Student;
import com.example.quan_li_sinh_vien.model.Subject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ActivityStudent extends AppCompatActivity {

    EditText searchEditTextStudent;
    Button searchButtonStudent,buttonRefreshStudent;
    Toolbar toolbar;
    ListView listViewStudent;

    ArrayList<Student> ArrayListStudent;
    ArrayList<Student> searchResultsStudent;
    com.example.quan_li_sinh_vien.database.database database;
    com.example.quan_li_sinh_vien.adapter.adapterstudent adapterstudent;

    int id_class = 0;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        searchEditTextStudent = findViewById(R.id.editTextSearchStudent);
        searchButtonStudent = findViewById(R.id.buttonEnterSearchStudent);
        buttonRefreshStudent = findViewById(R.id.buttonRefreshStudent);

        toolbar = findViewById(R.id.toolbarStudent);
        listViewStudent = findViewById(R.id.listviewStudent);

        Intent intent = getIntent();
        id_class = intent.getIntExtra("id_class",0);

        //toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        database = new database(this);

        ArrayListStudent = new ArrayList<>();
        searchResultsStudent = new ArrayList<>();

        ArrayListStudent.clear();

        Cursor cursor = database.getDataStudent(id_class);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String sex = cursor.getString(2);
            String code = cursor.getString(3);
            String birthday = cursor.getString(4);
            float processpoint = cursor.getFloat(5);
            float processmiddle = cursor.getFloat(6);
            float processfinal = cursor.getFloat(7);
            float avege = cursor.getFloat(8);
            int id_sub=cursor.getInt(9);
            ArrayListStudent.add(new Student(id,name,sex,code,birthday,processpoint,processmiddle,processfinal,avege,id_sub));

        }
        adapterstudent = new adapterstudent(ActivityStudent.this,ArrayListStudent);
        //hiển thị listview
        listViewStudent.setAdapter(adapterstudent);
        cursor.moveToFirst();
        cursor.close();

        //tìm kiếm môn học
        searchButtonStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchQuery = searchEditTextStudent.getText().toString();
                searchStudent(searchQuery);
            }
        });

        //Cập nhật lại môn học
        buttonRefreshStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Tải lại dữ liệu ban đầu
                ArrayListStudent.clear();
                Cursor cursor = database.getDataStudent(id_class);
                while (cursor.moveToNext()){
                    int id = cursor.getInt(0);
                    String name = cursor.getString(1);
                    String sex = cursor.getString(2);
                    String code = cursor.getString(3);
                    String birthday = cursor.getString(4);
                    float processpoint = cursor.getFloat(5);
                    float processmiddle = cursor.getFloat(6);
                    float processfinal = cursor.getFloat(7);
                    float avege = cursor.getFloat(8);
                    int id_sub=cursor.getInt(9);
                    ArrayListStudent.add(new Student(id,name,sex,code,birthday,processpoint,processmiddle,processfinal,avege,id_sub));

                }
                cursor.close();
                // Cập nhật lại adapter cho ListView
                adapterstudent = new adapterstudent(ActivityStudent.this, ArrayListStudent);
                listViewStudent.setAdapter(adapterstudent);

                //resetEditTextSubject
                searchEditTextStudent.setText("");
            }
        });

    }

    //hàm tìm kiếm sinh viên
    private void searchStudent(String searchQuery) {
        searchResultsStudent.clear();
        for (Student student : ArrayListStudent) {
            if (student.getStudent_code().toLowerCase().contains(searchQuery.toLowerCase())) {
                searchResultsStudent.add(student);
            }
        }
        if (searchResultsStudent.size() > 0) {
            adapterstudent = new adapterstudent(ActivityStudent.this, searchResultsStudent);
            listViewStudent.setAdapter(adapterstudent);
            adapterstudent.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "Không tìm thấy sinh viên", Toast.LENGTH_SHORT).show();
            adapterstudent.clear();
            listViewStudent.setAdapter(adapterstudent);
        }

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
                intent.putExtra("id_class",id_class);
                startActivity(intent);
                break;

                //nút back chuyển qua class activity
            default:
//                Intent intent1 = new Intent(ActivityStudent.this,ActivityClass.class);
                Intent intent1 = new Intent(ActivityStudent.this, ActivityMajor.class);
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
//            Intent intent = new Intent(ActivityStudent.this,ActivityClass.class);
            Intent intent = new Intent(ActivityStudent.this, ActivityMajor.class);
            startActivity(intent);
            finish();
        }
    }

    public void information(final int pos){
        Cursor cursor = database.getDataStudent(id_class);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);

            if(id == pos){
                Intent intent = new Intent(ActivityStudent.this,ActivityInformationStudent.class);

                intent.putExtra("id",pos);
                String name = cursor.getString(1);
                String sex = cursor.getString(2);
                String code = cursor.getString(3);
                String birth = cursor.getString(4);
                float processpoint = cursor.getFloat(5);
                float middlepoint = cursor.getFloat(6);
                float finalpoint = cursor.getFloat(7);
                float avegepoint = cursor.getFloat(8);
//                int id_class = cursor.getInt(9);

                intent.putExtra("name",name);
                intent.putExtra("sex",sex);
                intent.putExtra("code",code);
                intent.putExtra("birth",birth);
                intent.putExtra("processpoint",processpoint);
                intent.putExtra("middlepoint",middlepoint);
                intent.putExtra("finalpoint",finalpoint);
                intent.putExtra("avegepoint",avegepoint);
                startActivity(intent);
            }
        }
        cursor.close();
    }

    //update
    public void update(final int id_student){
        Cursor cursor=database.getDataStudent(id_class);

        while(cursor.moveToNext()){
            int id = cursor.getInt(0);

            if(id == id_student){
                Intent intent = new Intent(ActivityStudent.this,ActivityUpdateStudent.class);

                intent.putExtra("id",id_student);
                String name = cursor.getString(1);
                String sex = cursor.getString(2);
                String code = cursor.getString(3);
                String birth = cursor.getString(4);
                float processpoint = cursor.getFloat(5);
                float middlepoint = cursor.getFloat(6);
                float finalpoint = cursor.getFloat(7);
                float avegepoint = cursor.getFloat(8);
                int id_class = cursor.getInt(9);

                intent.putExtra("name",name);
                intent.putExtra("sex",sex);
                intent.putExtra("code",code);
                intent.putExtra("birth",birth);
                intent.putExtra("id_class",id_class);
                intent.putExtra("processpoint",processpoint);
                intent.putExtra("middlepoint",middlepoint);
                intent.putExtra("finalpoint",finalpoint);
                intent.putExtra("avegepoint",avegepoint);
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
                intent.putExtra("id_class",id_class);
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