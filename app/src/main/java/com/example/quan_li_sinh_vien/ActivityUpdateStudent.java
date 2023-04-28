package com.example.quan_li_sinh_vien;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.quan_li_sinh_vien.database.database;
import com.example.quan_li_sinh_vien.model.Student;

import java.util.Calendar;

public class ActivityUpdateStudent extends AppCompatActivity {

    EditText editTextUpdateName, editTextUpdateCode, editTextUpdateBirth;
    RadioButton rMale, rFemale;
    Button btnUpdateStudent;
    com.example.quan_li_sinh_vien.database.database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student);

        editTextUpdateBirth = findViewById(R.id.EditTextUpdateStudentBirth);
        editTextUpdateCode = findViewById(R.id.EditTextUpdateStudentCode);
        editTextUpdateName = findViewById(R.id.EditTextUpdateStudentName);
        rFemale = findViewById(R.id.radiobuttonUpdateFemale);
        rMale = findViewById(R.id.radiobuttonUpdateMale);
        btnUpdateStudent = findViewById(R.id.buttonUpdateStudent);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        String name = intent.getStringExtra("name");
        String sex = intent.getStringExtra("sex");
        String code = intent.getStringExtra("code");
        String birth = intent.getStringExtra("birth");
        int id_subject = intent.getIntExtra("id_subject", 0);

        //Gán giá trị
        editTextUpdateName.setText(name);
        editTextUpdateCode.setText(code);
        editTextUpdateBirth.setText(birth);

        if (sex.equals("Nam")) {
            rMale.setChecked(true);
            rFemale.setChecked(false);
        } else {
            rMale.setChecked(false);
            rFemale.setChecked(true);
        }

        database = new database(this);

        btnUpdateStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUpdate(id, id_subject);
            }
        });

        //date
        // Tạo DatePickerDialog
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        final EditText editTextUpdateStudentBirth = findViewById(R.id.EditTextUpdateStudentBirth);
        editTextUpdateStudentBirth.setOnClickListener(view -> {
            @SuppressLint("SetTextI18n") DatePickerDialog datePickerDialog = new DatePickerDialog(ActivityUpdateStudent.this, (view1, year1, monthOfYear, dayOfMonth1) -> {
                // Thiết lập giá trị cho EditText
                editTextUpdateStudentBirth.setText(dayOfMonth1 + "/" + (monthOfYear + 1) + "/" + year1);
            }, year, month, dayOfMonth);
            datePickerDialog.show();
        });
    }

    private void DialogUpdate(int id, int id_subject) {
        Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.dialogupdatestudent);

        dialog.setCanceledOnTouchOutside(false);

        Button btnYes = dialog.findViewById(R.id.buttonYesUpdateStudent);
        Button btnNo = dialog.findViewById(R.id.buttonNoUpdateStudent);


        //nếu đồng ý update
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextUpdateName.getText().toString().trim();
                String code = editTextUpdateCode.getText().toString().trim();
                String birth = editTextUpdateBirth.getText().toString().trim();

                String sex;
                if (rMale.isChecked()) {
                    sex = "Nam";
                } else if (rFemale.isChecked()) {
                    sex = "Nữ";
                } else {
                    sex = ""; //không có RadioButton nào được chọn
                }


                if (name.equals("") || code.equals("") || birth.equals("")) {
                    Toast.makeText(ActivityUpdateStudent.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    Student student = createStudent();
                    // Thực hiện cập nhật thông tin học sinh
                    if (database.UpdateStudent(student, id) == true) {
                        // Hiển thị thông báo cập nhật thành công và chuyển đến màn hình danh sách học sinh
                        Intent intent = new Intent(ActivityUpdateStudent.this, ActivityStudent.class);
                        intent.putExtra("id_subject", id_subject);
                        startActivity(intent);
                        Toast.makeText(ActivityUpdateStudent.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ActivityUpdateStudent.this, "Học sinh đã tồn tại ! Vui lòng thử lại", Toast.LENGTH_SHORT).show();
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
        dialog.show();
    }

    //create student
    private Student createStudent() {
        String name = editTextUpdateName.getText().toString().trim();
        String code = editTextUpdateCode.getText().toString().trim();
        String birth = editTextUpdateBirth.getText().toString().trim();
        String sex = "";
        if (rMale.isChecked()) {
            sex = "Nam";
        } else {
            sex = "Nữ";
        }

        Student student = new Student(name, sex, code, birth);
        return student;
    }
}