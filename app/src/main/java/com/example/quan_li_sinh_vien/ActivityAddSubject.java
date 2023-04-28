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
    EditText editSubjectTitle,editSubjectCredit,editSubjectTime,editSubjectPlace;
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

        database = new database(this);

        buttonAddSubject.setOnClickListener(new View.OnClickListener() {
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
        dialog.setContentView(R.layout.dialogadd);

        //ko thể bấm bên ngoài khung
        dialog.setCanceledOnTouchOutside(false);

        Button btnY = dialog.findViewById(R.id.buttonYes);
        Button btnN = dialog.findViewById(R.id.buttonNo);

        btnY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subjectTitle = editSubjectTitle.getText().toString().trim();
                String credit = editSubjectCredit.getText().toString().trim();
                String place = editSubjectPlace.getText().toString().trim();
                String time = editSubjectTime.getText().toString().trim();

                //Nếu dữ liệu chưa nhập đầy đủ
                if(subjectTitle.equals("")||credit.equals("")||time.equals("")||place.equals("")){
                    Toast.makeText(ActivityAddSubject.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else{
                    Boolean checkSub = database.checkSubject(subjectTitle);
                    if(!checkSub) {
                        //gán cho đối tượng subject giá trị được nhập vào
                        Subject subject = CreatSubject();

                        //add trong database
                        database.AddSubject(subject);

                        //add thành công thì chuyển qua giao diện subject
                        Intent intent = new Intent(ActivityAddSubject.this, ActivitySubject.class);
                        startActivity(intent);

                        Toast.makeText(ActivityAddSubject.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(ActivityAddSubject.this, "Môn học đã tồn tại ! Vui lòng thử lại", Toast.LENGTH_SHORT).show();
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
    private Subject CreatSubject(){
        String subjectTitle = editSubjectTitle.getText().toString().trim();
        int credit = Integer.parseInt(editSubjectCredit.getText().toString().trim());
        String place = editSubjectPlace.getText().toString().trim();
        String time = editSubjectTime.getText().toString().trim();

        Subject subject = new Subject(subjectTitle,credit,time,place);
        return subject;
    }
}