package com.example.quan_li_sinh_vien;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    Button btnSubject, btnAuthor, btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnSubject = findViewById(R.id.buttonSubject);
        btnAuthor = findViewById(R.id.buttonAuthor);
        btnExit = findViewById(R.id.buttonExit);

        //Click tác giả
        btnAuthor.setOnClickListener(view -> DialogAuthor());

        //click subject
        btnSubject.setOnClickListener(view -> {
            //chuyển qua activity subject
            Intent intent = new Intent(HomeActivity.this,ActivitySubject.class);
            startActivity(intent);
        });

        //click đăng xuất
        btnExit.setOnClickListener(view -> DialogExit());
    }

    //Hiện thị thông tin tác giả
    private void DialogAuthor() {
        //Tạo đối tượng cửa sổ dialog
        Dialog dialog = new Dialog(this);


        //nạp layout vào dialog
        dialog.setContentView(R.layout.dialoginformation);
        dialog.show();
    }

    //Hiện thị thông báo đăng xuất
    private void DialogExit(){
        Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.dialogexit);

        //Tắt click ngoài là thoát khung
        dialog.setCanceledOnTouchOutside(false);

        Button btnY = dialog.findViewById(R.id.buttonYes);
        Button btnN = dialog.findViewById(R.id.buttonNo);

        //hiện thị click yes
        btnY.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this,HomeActivity.class);
            startActivity(intent);

            //Đăng xuất
            Intent intent1 = new Intent(HomeActivity.this,LoginActivity.class);
            startActivity(intent1);
        });


        //hiện thị click no thì đóng cửa sổ
        btnN.setOnClickListener(view -> dialog.cancel());

        dialog.show();
    }
}