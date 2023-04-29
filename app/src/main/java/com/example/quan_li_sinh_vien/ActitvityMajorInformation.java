package com.example.quan_li_sinh_vien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class ActitvityMajorInformation extends AppCompatActivity {

    TextView editTitle,editCode,editSchool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actitvity_major_information);

        editTitle = findViewById(R.id.txtMajorTitle);
        editCode = findViewById(R.id.txtMajorCode);
        editSchool = findViewById(R.id.txtMajorSchool);

        //lấy dữ liệu
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String code = intent.getStringExtra("code");
        String school = intent.getStringExtra("school");

        //gán giá trị lên
        editTitle.setText(title);
        editCode.setText(code);
        editSchool.setText(school);
    }
}