package com.example.quan_li_sinh_vien;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quan_li_sinh_vien.database.database;
import com.example.quan_li_sinh_vien.model.Subject;

public class ActivityUpdateSubject extends AppCompatActivity {

    EditText editUpdateTitle,editUpdateCredit,editUpdateTime,editUpdatePlace;
    Button btnUpdateSubject;

    com.example.quan_li_sinh_vien.database.database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_subject);

        editUpdateCredit = findViewById(R.id.EditTextUpdateSubjectCredit);
        editUpdatePlace = findViewById(R.id.EditTextUpdateSubjectPlace);
        editUpdateTitle = findViewById(R.id.EditTextUpdateSubjectTitle);
        editUpdateTime = findViewById(R.id.EditTextUpdateSubjectTime);
        btnUpdateSubject = findViewById(R.id.buttonUpdateSubject);

        //lấy dữ liệu intent
        Intent intent = getIntent();

        int id = intent.getIntExtra("id",0);
        String title = intent.getStringExtra("title");
        int credit = intent.getIntExtra("credit",0);
        String time = intent.getStringExtra("time");
        String place = intent.getStringExtra("place");

        editUpdateTitle.setText(title);
        editUpdateCredit.setText(credit+"");
        editUpdateTime.setText(time);
        editUpdatePlace.setText(place);

        database = new database(this);

        btnUpdateSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUpdate(id);
            }
        });

    }

    private void DialogUpdate(int id) {
        Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.dialogupdatesubject);

        dialog.setCanceledOnTouchOutside(false);

        Button btnYes = dialog.findViewById(R.id.buttonYesUpdate);
        Button btnNo = dialog.findViewById(R.id.buttonNoUpdate);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subjecttitle = editUpdateTitle.getText().toString().trim();
                String credit = editUpdateCredit.getText().toString().trim();
                String time = editUpdateTime.getText().toString().trim();
                String place = editUpdatePlace.getText().toString().trim();

                if(subjecttitle.equals("")||credit.equals("")||time.equals("")||place.equals("")){
                    Toast.makeText(ActivityUpdateSubject.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else {
                    Subject subject = updatesubject();

                    if(database.UpdateSubject(subject,id)==true){
                        //update thành công thì qua activity subject
                        Intent intent = new Intent(ActivityUpdateSubject.this,ActivitySubject.class);
                        startActivity(intent);
                        Toast.makeText(ActivityUpdateSubject.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(ActivityUpdateSubject.this, "Môn học đã tồn tại ! Vui lòng thử lại", Toast.LENGTH_SHORT).show();

                    }


                }

            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        //show dialogupdate
        dialog.show();
    }

    //lưu trữ dữ liệu cập nhật
    private Subject updatesubject(){
        String subjecttitle = editUpdateTitle.getText().toString().trim();
        int credit = Integer.parseInt(editUpdateCredit.getText().toString().trim());
        String time = editUpdateTime.getText().toString().trim();
        String place = editUpdatePlace.getText().toString().trim();

        Subject subject = new Subject(subjecttitle,credit,time,place);
        return subject;
    }
}