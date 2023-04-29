package com.example.quan_li_sinh_vien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ActivityInformationClass extends AppCompatActivity {

    TextView textViewClassName,textViewClassCode,textViewTeacherName,textViewTeacherCode,textViewTeacherSex,textViewTeacherBirth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_class);

        textViewClassCode = findViewById(R.id.txtClassCode);
        textViewClassName = findViewById(R.id.txtClassName);
        textViewTeacherName =findViewById(R.id.txtTeachername);
        textViewTeacherCode = findViewById(R.id.txtTeacherCode);
        textViewTeacherBirth = findViewById(R.id.txtTeacherBirth);
        textViewTeacherSex = findViewById(R.id.txtTeacherSex);


        //nhận dữ liệu
        Intent intent = getIntent();

        String name_class = intent.getStringExtra("name_class");
        String code_class = intent.getStringExtra("code_class");
        String name_teacher = intent.getStringExtra("name_teacher");
        String code_teacher = intent.getStringExtra("code_teacher");
        String sex_teacher = intent.getStringExtra("sex_teacher");
        String birth_teacher = intent.getStringExtra("birth_teacher");

        //gán lên textview tương ứng
        textViewClassName.setText(name_class);
        textViewClassCode.setText(code_class);
        textViewTeacherName.setText(name_teacher);
        textViewTeacherSex.setText(sex_teacher);
        textViewTeacherBirth.setText(birth_teacher);
        textViewTeacherCode.setText(code_teacher);


    }
}