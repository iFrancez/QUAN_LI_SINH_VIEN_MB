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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.quan_li_sinh_vien.adapter.adapterclass;
import com.example.quan_li_sinh_vien.adapter.adaptersubject;
import com.example.quan_li_sinh_vien.database.database;
import com.example.quan_li_sinh_vien.model.ClassSub;
import com.example.quan_li_sinh_vien.model.Majors;
import com.example.quan_li_sinh_vien.model.Student;
import com.example.quan_li_sinh_vien.model.Subject;

import java.util.ArrayList;

public class ActivityClass extends AppCompatActivity {


    Toolbar toolbar;
    ListView listViewClass;

    EditText searchEditTextClass;
    Button searchButtonClass,buttonRefreshClass;
    ArrayList<ClassSub> ArrayListClass;

    ArrayList<ClassSub> searchResultsClass;

    com.example.quan_li_sinh_vien.database.database database;
    adapterclass adapterclass;

    int id_subject = 0;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);

        searchEditTextClass = findViewById(R.id.editTextSearchClass);
        searchButtonClass = findViewById(R.id.buttonEnterSearchClass);
        buttonRefreshClass = findViewById(R.id.buttonRefreshClass);

        toolbar = findViewById(R.id.toolbarClass);
        listViewClass = findViewById(R.id.listviewClass);

        Intent intent = getIntent();
        id_subject = intent.getIntExtra("id_subject",0);

        //toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        database = new database(this);

        ArrayListClass = new ArrayList<>();
        searchResultsClass = new ArrayList<>();
//        ArrayListClass.clear();

        Cursor cursor = database.getDataClass(id_subject);

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name_class = cursor.getString(1);
            String code_class = cursor.getString(2);
            String name_teacher = cursor.getString(3);
            String sex_teacher = cursor.getString(4);
            String code_teacher = cursor.getString(5);
            String birth_teacher = cursor.getString(6);
            int id_sub = cursor.getInt(7);

            ArrayListClass.add(new ClassSub(id,name_class,code_class,name_teacher,sex_teacher,code_teacher,birth_teacher,id_sub));

        }
        adapterclass = new adapterclass(ActivityClass.this,ArrayListClass);

        //hiễn thị listview
        listViewClass.setAdapter(adapterclass);
        cursor.moveToFirst();
        cursor.close();

        //tìm kiếm môn học
        searchButtonClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchQuery = searchEditTextClass.getText().toString();
                searchClass(searchQuery);
            }
        });

        //Cập nhật lại môn học
        buttonRefreshClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Tải lại dữ liệu ban đầu
                ArrayListClass.clear();
                Cursor cursor = database.getDataClass(id_subject);
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(0);
                    String name_class = cursor.getString(1);
                    String code_class = cursor.getString(2);
                    String name_teacher = cursor.getString(3);
                    String sex_teacher = cursor.getString(4);
                    String code_teacher = cursor.getString(5);
                    String birth_teacher = cursor.getString(6);
                    int id_sub = cursor.getInt(7);

                    ArrayListClass.add(new ClassSub(id, name_class, code_class, name_teacher, sex_teacher,code_teacher,birth_teacher,id_sub));
                }
                cursor.close();

                // Cập nhật lại adapter cho ListView
                adapterclass = new adapterclass(ActivityClass.this, ArrayListClass);
                listViewClass.setAdapter(adapterclass);

                //resetEditTextSubject
                searchEditTextClass.setText("");
            }
        });

        //Tạo sự kiện click vào item student
        listViewClass.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ActivityClass.this, ActivityStudent.class);
                int id_class;
                if (searchResultsClass.isEmpty()) {
                    id_class = ArrayListClass.get(i).getId_class();
                } else {
                    id_class = searchResultsClass.get(i).getId_class();
                }
                //truyền dữ liệu vào
                intent.putExtra("id_class", id_class);
                startActivity(intent);
            }
        });
    }

    private void searchClass(String searchQuery) {
        searchResultsClass.clear();
        for (ClassSub classSub : ArrayListClass) {
            if (classSub.getClass_code().toLowerCase().contains(searchQuery.toLowerCase())) {
                searchResultsClass.add(classSub);
            }
        }
        if (searchResultsClass.size() > 0) {
            adapterclass = new adapterclass(ActivityClass.this, searchResultsClass);
            listViewClass.setAdapter(adapterclass);
            adapterclass.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "Không tìm thấy lớp học", Toast.LENGTH_SHORT).show();
            adapterclass.clear();
            listViewClass.setAdapter(adapterclass);
        }
    }

    //thêm icon menu add
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuaddclass, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            //Nếu click vào add thì chuyển qua màn hình add class
            case R.id.menuaddclass:
                Intent intent1 = new Intent(ActivityClass.this, ActivityAddClass.class);
                intent1.putExtra("id_subject",id_subject);
                startActivity(intent1);
                break;

            //Nếu click vào nút còn lại là nút back thì quay lại subject
            default:
//                Intent intent = new Intent(ActivityClass.this, ActivitySubject.class);
                Intent intent = new Intent(ActivityClass.this, ActivityMajor.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    //   Nếu click back ở điện thoại thì sẽ trở về Activitysubject
    @Override
    public void onBackPressed() {
        count++;
        if(count>=1) {
//            Intent intent = new Intent(ActivityClass.this, ActivitySubject.class);
            Intent intent = new Intent(ActivityClass.this, ActivityMajor.class);

            startActivity(intent);
            finish();

        }
    }

    public void informationClass(final int pos){
        Cursor cursor = database.getDataClass(id_subject);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);

            if(id==pos){
                Intent intent = new Intent(ActivityClass.this,ActivityInformationClass.class);

                intent.putExtra("id",pos);

                String name_class = cursor.getString(1);
                String code_class = cursor.getString(2);
                String name_teacher = cursor.getString(3);
                String sex_teacher = cursor.getString(4);
                String code_teacher = cursor.getString(5);
                String birth_teacher = cursor.getString(6);
                int id_sub = cursor.getInt(7);

                intent.putExtra("name_class",name_class);
                intent.putExtra("code_class",code_class);
                intent.putExtra("name_teacher",name_teacher);
                intent.putExtra("sex_teacher",sex_teacher);
                intent.putExtra("code_teacher",code_teacher);
                intent.putExtra("birth_teacher",birth_teacher);

                startActivity(intent);
            }
        }
        cursor.close();
    }

    //update
    public void updateClass(final int id_class){
        Cursor cursor = database.getDataClass(id_subject);

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);

            if(id == id_class){
                Intent intent = new Intent(ActivityClass.this,ActivityUpdateClass.class);

                intent.putExtra("id",id_class);

                String name_class = cursor.getString(1);
                String code_class = cursor.getString(2);
                String name_teacher = cursor.getString(3);
                String sex_teacher = cursor.getString(4);
                String code_teacher = cursor.getString(5);
                String birth_teacher = cursor.getString(6);
                int id_subject = cursor.getInt(7);

                intent.putExtra("name_class",name_class);
                intent.putExtra("code_class",code_class);
                intent.putExtra("name_teacher",name_teacher);
                intent.putExtra("sex_teacher",sex_teacher);
                intent.putExtra("code_teacher",code_teacher);
                intent.putExtra("birth_teacher",birth_teacher);
                intent.putExtra("id_subject",id_subject);

                startActivity(intent);
            }
        }
        cursor.close();
    }
    public void deleteClass(final int id_class){
        Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.dialogdeleteclass);

        dialog.setCanceledOnTouchOutside(false);

        Button btnY = dialog.findViewById(R.id.buttonYesDeleteClass);
        Button btnN = dialog.findViewById(R.id.buttonNoDeleteClass);

        btnY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //xoá lớp học trong csdl
                database.DeleteClass(id_class);

                Intent intent = new Intent(ActivityClass.this,ActivityClass.class);
                intent.putExtra("id_subject",id_subject);
                startActivity(intent);

            }
        });
        btnN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
}
