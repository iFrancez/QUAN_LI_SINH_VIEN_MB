package com.example.quan_li_sinh_vien;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quan_li_sinh_vien.database.database;
import com.example.quan_li_sinh_vien.model.Majors;
import com.example.quan_li_sinh_vien.model.Subject;

public class ActivityUpdateMajor extends AppCompatActivity {
    EditText editUpdateTitle,editUpdateCode,editUpdateSchool;

    Button btnUpdateMajor;
    com.example.quan_li_sinh_vien.database.database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_major);

        btnUpdateMajor = findViewById(R.id.buttonUpdateMajor);
        editUpdateCode = findViewById(R.id.EditTextUpdateMajorCode);
        editUpdateSchool = findViewById(R.id.EditTextUpdateMajorSchool);
        editUpdateTitle = findViewById(R.id.EditTextUpdateMajorTitle);

        //lấy dữ liệu intent
        Intent intent = getIntent();

        int id = intent.getIntExtra("id",0);
        String title = intent.getStringExtra("title");
        String code = intent.getStringExtra("code");
        String school = intent.getStringExtra("school");

        editUpdateTitle.setText(title);
        editUpdateCode.setText(code);
        editUpdateSchool.setText(school);

        database = new database(this);

        btnUpdateMajor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUpdateMajor(id);
            }
        });

    }

    private void DialogUpdateMajor(int id) {
        Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.dialogupdatemajor);

        dialog.setCanceledOnTouchOutside(false);

        Button btnY = dialog.findViewById(R.id.buttonYesUpdateMajor);
        Button btnN = dialog.findViewById(R.id.buttonNoUpdateMajor);

        btnY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String majortitle = editUpdateTitle.getText().toString().trim();
                String code = editUpdateCode.getText().toString().trim();
                String school = editUpdateSchool.getText().toString().trim();

                if(majortitle.equals("")||code.equals("")||school.equals("")){
                    Toast.makeText(ActivityUpdateMajor.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else{
                    Majors majors = updateMajor();
                    database.UpdateMajor(majors,id);

                    //update thành công thì qua majoractivity
                    Intent intent = new Intent(ActivityUpdateMajor.this,ActivityMajor.class);
                    startActivity(intent);
                    Toast.makeText(ActivityUpdateMajor.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //nếu không update thì out
        btnN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
    //lưu trữ dữ liệu edittext cập nhật
    private Majors updateMajor(){
        String majortitle = editUpdateTitle.getText().toString().trim();
        String code = editUpdateCode.getText().toString().trim();
        String school = editUpdateSchool.getText().toString().trim();

        Majors majors = new Majors(majortitle,code,school);
        return majors;
    }

}