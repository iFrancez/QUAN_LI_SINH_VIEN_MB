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

import com.example.quan_li_sinh_vien.adapter.adaptermajor;
import com.example.quan_li_sinh_vien.adapter.adaptersubject;
import com.example.quan_li_sinh_vien.database.database;
import com.example.quan_li_sinh_vien.model.Majors;

import java.util.ArrayList;

public class ActivityMajor extends AppCompatActivity {

    EditText searchEditTextMajor;
    Button searchButtonMajor, buttonRefresh;
    Toolbar toolbar;
    ListView listViewMajor;
    ArrayList<Majors> ArrayListMajor;

    ArrayList<Majors> searchResultsMajor;

    database database;
    adaptermajor adaptermajor;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_major);

        searchEditTextMajor = findViewById(R.id.editTextSearchMajor);
        searchButtonMajor = findViewById(R.id.buttonEnterSearchMajor);
        buttonRefresh = findViewById(R.id.buttonRefreshMajor);

        toolbar = findViewById(R.id.toolbarMajor);
        listViewMajor = findViewById(R.id.listviewMajor);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        database = new database(this);

        ArrayListMajor = new ArrayList<>();
        searchResultsMajor = new ArrayList<>();

        Cursor cursor = database.getDataMajor();
        while (cursor.moveToNext()) {

            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String code = cursor.getString(2);
            String school = cursor.getString(3);

            ArrayListMajor.add(new Majors(id, title, code, school));

        }

        adaptermajor = new adaptermajor(ActivityMajor.this, ArrayListMajor);
        listViewMajor.setAdapter(adaptermajor);
        cursor.moveToFirst();
        cursor.close();

        //tìm kiếm ngành học
        searchButtonMajor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchQuery = searchEditTextMajor.getText().toString();
                searchMajor(searchQuery);
            }
        });

        //Nút Cập nhật lại ngành học
        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchResultsMajor.clear();
                // Tải lại dữ liệu ban đầu
                ArrayListMajor.clear();
                Cursor cursor = database.getDataMajor();
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(0);
                    String title = cursor.getString(1);
                    String code = cursor.getString(2);
                    String school = cursor.getString(3);

                    ArrayListMajor.add(new Majors(id, title, code, school));
                }
                cursor.close();

                // Cập nhật lại adapter cho ListView
                adaptermajor = new adaptermajor(ActivityMajor.this, ArrayListMajor);
                listViewMajor.setAdapter(adaptermajor);

                //resetEditTextSubject
                searchEditTextMajor.setText("");
            }
        });


        //Tạo sự kiện click vào item major
        listViewMajor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ActivityMajor.this, ActivitySubject.class);
                int id_major;
                if (searchResultsMajor.isEmpty()) {
                    id_major = ArrayListMajor.get(i).getId();
                } else {
                    id_major = searchResultsMajor.get(i).getId();
                }

                //truyền dữ liệu vào
                intent.putExtra("id_major", id_major);
                startActivity(intent);


            }
        });
    }

    //hàm tìm kiếm ngành học
    private void searchMajor(String searchQuery) {
        searchResultsMajor.clear();
        for (Majors majors : ArrayListMajor) {
            if (majors.getMajors_code().toLowerCase().contains(searchQuery.toLowerCase())) {
                searchResultsMajor.add(majors);
            }
        }
        if (searchResultsMajor.size() > 0) {
            adaptermajor = new adaptermajor(ActivityMajor.this, searchResultsMajor);
            listViewMajor.setAdapter(adaptermajor);
            adaptermajor.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "Không tìm thấy mã ngành học", Toast.LENGTH_SHORT).show();
            adaptermajor.clear();
            listViewMajor.setAdapter(adaptermajor);
        }

    }



    //thêm 1 menu là add vào toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuaddmajor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            //nếu click vào add major activity
            case R.id.menuaddmajor:
                Intent intent1 = new Intent(ActivityMajor.this, ActivityAddMajor.class);
                startActivity(intent1);
                break;

            //Nếu click vào nút quay lại về home
            default:
                Intent intent = new Intent(ActivityMajor.this, HomeActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //Nếu click back ở điện thoại sẽ trở về home
    @Override
    public void onBackPressed() {
        count++;
        if (count >= 1) {
            Intent intent = new Intent(ActivityMajor.this, HomeActivity.class);
            startActivity(intent);
            finish();

        }
    }

    //thông tin ngành học
    public void informationMajor(final int pos){
        Cursor cursor = database.getDataMajor();

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            if(id == pos){
                Intent intent = new Intent(ActivityMajor.this,ActitvityMajorInformation.class);

                intent.putExtra("id",id);
                String title = cursor.getString(1);
                String code = cursor.getString(2);
                String school = cursor.getString(3);

                intent.putExtra("title",title);
                intent.putExtra("code",code);
                intent.putExtra("school",school);

                startActivity(intent);

            }
        }
    }

    //Xoá thông tin ngành học
    public void deleteMajor(final int pos){
        //đối tượng cửa sổ
        Dialog dialog = new Dialog(this);

        //nạp layout vào dialog
        dialog.setContentView(R.layout.dialogdeletemajor);

        dialog.setCanceledOnTouchOutside(false);

        Button btnYes = dialog.findViewById(R.id.buttonYesDeleteMajor);
        Button btnNo = dialog.findViewById(R.id.buttonNoDeleteMajor);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //xoá ngành học trong csdl
                database.DeleteMajor(pos);
                //cập nhật lại activity major
                Intent intent = new Intent(ActivityMajor.this,ActivityMajor.class);
                startActivity(intent);

            }
        });

        //đóng nếu no
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    //cập nhật thông tin ngành học
    public void updateMajor(final int pos){
        Cursor cursor = database.getDataMajor();

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);

            if(id == pos){
                Intent intent = new Intent(ActivityMajor.this,ActivityUpdateMajor.class);

                String title = cursor.getString(1);
                String code = cursor.getString(2);
                String school = cursor.getString(3);

                //gửi dữ liệu qua activity update major
                intent.putExtra("id",id);
                intent.putExtra("title",title);
                intent.putExtra("code",code);
                intent.putExtra("school",school);

                startActivity(intent);
            }
        }

    }
}