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
import com.example.quan_li_sinh_vien.model.Subject;

public class ActivityAddSubject extends AppCompatActivity {


    Button buttonAddSubject;
    EditText editSubjectTitle,editSubjectCredit,editSubjectTime,editSubjectPlace,editSubjectCode;
    com.example.quan_li_sinh_vien.database.database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);

        buttonAddSubject = findViewById(R.id.buttonAddSubject);
        editSubjectCredit = findViewById(R.id.EditTextSubjectCredit);
        editSubjectCredit.setInputType(InputType.TYPE_CLASS_NUMBER); //nhập được số thôi
        editSubjectTime = findViewById(R.id.EditTextSubjectTime);
        editSubjectPlace = findViewById(R.id.EditTextSubjectPlace);
        editSubjectTitle = findViewById(R.id.EditTextSubjectTitle);
        editSubjectCode = findViewById(R.id.EditTextSubjectCode);

        //lấy id major
        Intent intent = getIntent();
        int id_major = intent.getIntExtra("id_major",0);

        database = new database(this);

        buttonAddSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogAdd(id_major);
            }
        });
    }

    private void DialogAdd(int id_major) {

        //Tạo đối tượng cửa sổ
        Dialog dialog = new Dialog(this);

        //nạp layout
        dialog.setContentView(R.layout.dialogadd);

        //ko thể bấm bên ngoài khung
        dialog.setCanceledOnTouchOutside(false);

        Button btnY = dialog.findViewById(R.id.buttonYes);
        Button btnN = dialog.findViewById(R.id.buttonNo);

        btnY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subjectTitle = editSubjectTitle.getText().toString().trim();
                String subjectCode = editSubjectCode.getText().toString().trim();
                String credit = editSubjectCredit.getText().toString().trim();
                String place = editSubjectPlace.getText().toString().trim();
                String time = editSubjectTime.getText().toString().trim();

                //Nếu dữ liệu chưa nhập đầy đủ
                if(subjectTitle.equals("")||credit.equals("")||time.equals("")||place.equals("")||subjectCode.equals("")){
                    Toast.makeText(ActivityAddSubject.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else{
                    Boolean checkCodeSub = database.checkSubject(subjectTitle,subjectCode);
                    if(!checkCodeSub) {
                        //gán cho đối tượng subject giá trị được nhập vào
                        Subject subject = CreatSubject(id_major);

                        //add trong database
                        database.AddSubject(subject);

                        //add thành công thì chuyển qua giao diện subject
                        Intent intent = new Intent(ActivityAddSubject.this, ActivitySubject.class);
                        intent.putExtra("id_major",id_major);
                        startActivity(intent);

                        Toast.makeText(ActivityAddSubject.this, "Thêm môn học thành công", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(ActivityAddSubject.this, "Tên môn học hoặc mã học phần hoặc cả hai đã tồn tại ! Vui lòng thử lại", Toast.LENGTH_SHORT).show();
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

    //Tạo môn học
    private Subject CreatSubject(int id_major){
        String subjectTitle = editSubjectTitle.getText().toString().trim();
        String subjectCode = editSubjectCode.getText().toString().trim();
        int credit = Integer.parseInt(editSubjectCredit.getText().toString().trim());
        String place = editSubjectPlace.getText().toString().trim();
        String time = editSubjectTime.getText().toString().trim();

        Subject subject = new Subject(subjectTitle,subjectCode,credit,time,place,id_major);
        return subject;
    }
}