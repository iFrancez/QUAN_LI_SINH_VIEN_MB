package com.example.quan_li_sinh_vien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ActivityInformationStudent extends AppCompatActivity {

    TextView txtName, txtSex, txtCode, txtBirth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_student);

        txtBirth = findViewById(R.id.txtStudentBirth);
        txtCode = findViewById(R.id.txtStudentCode);
        txtName = findViewById(R.id.txtStudentName);
        txtSex = findViewById(R.id.txtStudentSex);

        //nhận dữ liệu
        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
        String sex = intent.getStringExtra("sex");
        String code = intent.getStringExtra("code");
        String birth = intent.getStringExtra("birth");

        //gán lên textview tương ứng
        txtName.setText(name);
        txtCode.setText(code);
        txtBirth.setText(birth);
        txtSex.setText(sex);

    }
}