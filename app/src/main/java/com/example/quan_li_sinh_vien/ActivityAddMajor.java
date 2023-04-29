package com.example.quan_li_sinh_vien;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quan_li_sinh_vien.database.database;
import com.example.quan_li_sinh_vien.model.Majors;
import com.example.quan_li_sinh_vien.model.Subject;

public class ActivityAddMajor extends AppCompatActivity {

    Button buttonAddMajor;
    EditText editMajorTitle, editMajorCode, editMajorSchool;
    com.example.quan_li_sinh_vien.database.database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_major);
        buttonAddMajor = findViewById(R.id.buttonAddMajor);
        editMajorTitle = findViewById(R.id.EditTextMajorTitle);
        editMajorCode = findViewById(R.id.EditTextMajorCode);
        editMajorSchool = findViewById(R.id.EditTextMajorSchool);

        database = new database(this);

        buttonAddMajor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogAdd();
            }
        });
    }

    private void DialogAdd() {

        //Tạo đối tượng cửa sổ
        Dialog dialog = new Dialog(this);

        //nạp layout
        dialog.setContentView(R.layout.dialogaddmajor);

        //ko thể bấm bên ngoài khung
        dialog.setCanceledOnTouchOutside(false);

        Button btnY = dialog.findViewById(R.id.buttonYesAddMajor);
        Button btnN = dialog.findViewById(R.id.buttonNoAddMajor);

        btnY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String majortitle = editMajorTitle.getText().toString().trim();
                String school = editMajorSchool.getText().toString().trim();
                String code = editMajorCode.getText().toString().trim();


                //Nếu dữ liệu chưa nhập đầy đủ
                if (majortitle.equals("") || school.equals("") || code.equals("")) {
                    Toast.makeText(ActivityAddMajor.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkMj = database.checkMajor(code);
                    if (!checkMj) {
                        //gán cho đối tượng subject giá trị được nhập vào
                        Majors majors = CreatMajor();

                        //add trong database
                        database.AddMajor(majors);

                        //add thành công thì chuyển qua giao diện subject
                        Intent intent = new Intent(ActivityAddMajor.this, ActivityMajor.class);
                        startActivity(intent);

                        Toast.makeText(ActivityAddMajor.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ActivityAddMajor.this, "Ngành học đã tồn tại ! Vui lòng thử lại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //nếu không add thì out
        btnN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    //Tạo ngành học
    private Majors CreatMajor() {
        String majortitle = editMajorTitle.getText().toString().trim();
        String school = editMajorSchool.getText().toString().trim();
        String code = editMajorCode.getText().toString().trim();

        Majors major = new Majors(majortitle, code, school);
        return major;
    }
}