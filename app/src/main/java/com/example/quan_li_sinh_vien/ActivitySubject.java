package com.example.quan_li_sinh_vien;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.quan_li_sinh_vien.adapter.adaptersubject;
import com.example.quan_li_sinh_vien.database.database;
import com.example.quan_li_sinh_vien.model.Subject;

import java.util.ArrayList;
import java.util.List;

public class ActivitySubject extends AppCompatActivity {

    EditText searchEditTextSubject;
    Button searchButtonSubject,buttonRefresh;
    Toolbar toolbar;
    ListView listViewSubject;
    ArrayList<Subject> ArrayListSubject;
    ArrayList<Subject> searchResults;

    com.example.quan_li_sinh_vien.database.database database;
    com.example.quan_li_sinh_vien.adapter.adaptersubject adaptersubject;
    int count = 0;
    int id_major = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        searchEditTextSubject = findViewById(R.id.editTextSearchSubject);
        searchButtonSubject = findViewById(R.id.buttonEnterSearchSubject);
        buttonRefresh = findViewById(R.id.buttonRefreshSubject);

        toolbar = findViewById(R.id.toolbarSubject);
        listViewSubject = findViewById(R.id.listviewSubject);

        Intent intent = getIntent();
        id_major = intent.getIntExtra("id_major",0);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        database = new database(this);

        ArrayListSubject = new ArrayList<>();
        searchResults = new ArrayList<>();


        Cursor cursor = database.getDataSubject(id_major);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            int credit = cursor.getInt(2);
            String time = cursor.getString(3);
            String place = cursor.getString(4);
            int id_mj = cursor.getInt(5);

            ArrayListSubject.add(new Subject(id, title, credit, time, place,id_mj));

        }

        adaptersubject = new adaptersubject(ActivitySubject.this, ArrayListSubject);
        listViewSubject.setAdapter(adaptersubject);
        cursor.moveToFirst();
        cursor.close();

        //tìm kiếm môn học
        searchButtonSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchQuery = searchEditTextSubject.getText().toString();
                searchSubjects(searchQuery);
            }
        });

        //Cập nhật lại môn học
        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Tải lại dữ liệu ban đầu
                ArrayListSubject.clear();
                Cursor cursor = database.getDataSubject(id_major);
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(0);
                    String title = cursor.getString(1);
                    int credit = cursor.getInt(2);
                    String time = cursor.getString(3);
                    String place = cursor.getString(4);
                    int id_mj = cursor.getInt(5);

                    ArrayListSubject.add(new Subject(id, title, credit, time, place,id_mj));
                }
                cursor.close();

                // Cập nhật lại adapter cho ListView
                adaptersubject = new adaptersubject(ActivitySubject.this, ArrayListSubject);
                listViewSubject.setAdapter(adaptersubject);

                //resetEditTextSubject
                searchEditTextSubject.setText("");
            }
        });


        //Tạo sự kiện click vào item subject
        listViewSubject.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ActivitySubject.this, ActivityClass.class);
                int id_subject;
                if (searchResults.isEmpty()) {
                    id_subject = ArrayListSubject.get(i).getId();
                } else {
                    id_subject = searchResults.get(i).getId();
                }
                //truyền dữ liệu vào
                intent.putExtra("id_subject", id_subject);
                startActivity(intent);
            }
        });
    }



    //hàm tìm kiếm môn học
    private void searchSubjects(String searchQuery) {
        searchResults.clear();
        for (Subject subject : ArrayListSubject) {
            if (subject.getSubject_title().toLowerCase().contains(searchQuery.toLowerCase())) {
                searchResults.add(subject);
            }
        }
        if (searchResults.size() > 0) {
            adaptersubject = new adaptersubject(ActivitySubject.this, searchResults);
            listViewSubject.setAdapter(adaptersubject);
            adaptersubject.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "Không tìm thấy tên môn học", Toast.LENGTH_SHORT).show();
            adaptersubject.clear();
            listViewSubject.setAdapter(adaptersubject);
        }

    }



    //thêm 1 menu là add vào toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuadd, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            //Nếu click vào add thì chuyển qua màn hình add subject
            case R.id.menuadd:
                Intent intent1 = new Intent(ActivitySubject.this, ActivityAddSubject.class);
                intent1.putExtra("id_major",id_major);
                startActivity(intent1);
                break;

            //Nếu click vào nút còn lại là nút back thì quay lại ActivityMajor
            default:
                Intent intent = new Intent(ActivitySubject.this, ActivityMajor.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    //   Nếu click back ở điện thoại thì sẽ trở về ActivityMajor
    @Override
    public void onBackPressed() {
        count++;
        if (count >= 1) {
            Intent intent = new Intent(ActivitySubject.this, ActivityMajor.class);
            startActivity(intent);
            finish();
        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        // Lấy lại danh sách môn học và cập nhật lại danh sách trên ListView
//        Cursor cursor = database.getDataSubject(id_major);
//        ArrayListSubject.clear();
//        while (cursor.moveToNext()) {
//            int id = cursor.getInt(0);
//            String title = cursor.getString(1);
//            int credit = cursor.getInt(2);
//            String time = cursor.getString(3);
//            String place = cursor.getString(4);
//            int id_mj = cursor.getInt(5);
//
//            ArrayListSubject.add(new Subject(id, title, credit, time, place, id_mj));
//        }
//        cursor.moveToFirst();
//        cursor.close();
//
//        adaptersubject.notifyDataSetChanged(); // Cập nhật lại danh sách hiển thị trên ListView
//    }





    public void information(final int pos) {
        Cursor cursor = database.getDataSubject(id_major);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            if (id == pos) {
                Intent intent = new Intent(ActivitySubject.this, ActivitySubjectInformation.class);

                intent.putExtra("id", id);
                String title = cursor.getString(1);
                int credit = cursor.getInt(2);
                String time = cursor.getString(3);
                String place = cursor.getString(4);


                intent.putExtra("title", title);
                intent.putExtra("credit", credit);
                intent.putExtra("time", time);
                intent.putExtra("place", place);


                startActivity(intent);
            }
        }
    }

    //Phương thức xoá subject
    public void deleteSubject(final int position) {
        //Đối tượng cửa sổ
        Dialog dialog = new Dialog(this);


        //Nạp layout vào dialog
        dialog.setContentView(R.layout.dialogdeletesubject);

        dialog.setCanceledOnTouchOutside(false);

        Button btnYes = dialog.findViewById(R.id.buttonYesDeleteSubject);
        Button btnNo = dialog.findViewById(R.id.buttonNoDeleteSubject);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //xoá môn học trong csdl
                database.DeleteSubject(position);

                //xoá lớp học trong csdl
                database.DeleteClass(position);

                //xoá học sinh trong csdl
                database.DeleteStudent(position);
                //cập nhật lại activity subject
                Intent intent = new Intent(ActivitySubject.this, ActivitySubject.class);
                intent.putExtra("id_major",id_major);

                startActivity(intent);

                Toast.makeText(ActivitySubject.this, "Xoá môn học thành công", Toast.LENGTH_SHORT).show();

            }

        });

        //đóng dialog nếu no
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        //show dialog
        dialog.show();
    }

    public void update(final int pos) {
        Cursor cursor = database.getDataSubject(id_major);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);

            if (id == pos) {
                Intent intent = new Intent(ActivitySubject.this, ActivityUpdateSubject.class);
                String title = cursor.getString(1);
                int credit = cursor.getInt(2);
                String time = cursor.getString(3);
                String place = cursor.getString(4);
                int id_mj = cursor.getInt(5);

                //Gửi dữ liệu qua activity update
                intent.putExtra("id", id);
                intent.putExtra("title", title);
                intent.putExtra("credit", credit);
                intent.putExtra("time", time);
                intent.putExtra("place", place);
                intent.putExtra("id_major",id_mj);

                startActivity(intent);
            }
        }
    }
}