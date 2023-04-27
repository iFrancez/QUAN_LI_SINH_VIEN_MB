package com.example.quan_li_sinh_vien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class ActivitySubjectInformation extends AppCompatActivity {

    TextView editTitle, editCredit, editTime, editPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_information);

        editCredit = findViewById(R.id.txtSubjectCredit);
        editPlace = findViewById(R.id.txtSubjectPlace);
        editTitle = findViewById(R.id.txtSubjectTitle);
        editTime = findViewById(R.id.txtSubjectTime);

        //lấy dữ liệu
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        int credit = intent.getIntExtra("credit", 0);
        String time = intent.getStringExtra("time");
        String place = intent.getStringExtra("place");

        //Gán giá trị lên
        editTitle.setText(title);
        editTime.setText(time);
        editPlace.setText(place);
        editCredit.setText(credit + "");

    }
}