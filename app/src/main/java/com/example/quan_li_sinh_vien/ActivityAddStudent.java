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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ActivityAddStudent extends AppCompatActivity {

    Button buttonAddStudent;
    EditText editTextStudentName, editTextStudentCode, editTextStudentBirth;
    RadioButton radioButtonMale, radioButtonFemale;
    com.example.quan_li_sinh_vien.database.database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        buttonAddStudent = findViewById(R.id.buttonAddStudent);
        editTextStudentName = findViewById(R.id.EditTextStudentName);
        editTextStudentCode = findViewById(R.id.EditTextStudentCode);
        editTextStudentBirth = findViewById(R.id.EditTextStudentBirth);
        radioButtonMale = findViewById(R.id.radiobuttonMale);
        radioButtonFemale = findViewById(R.id.radiobuttonFemale);

        //lấy id subject
        Intent intent = getIntent();
        int id_subject = intent.getIntExtra("id_subject", 0);

        database = new database(this);
        buttonAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogAdd(id_subject);
            }
        });

        //date
        // Tạo DatePickerDialog
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        final EditText editTextStudentBirth = findViewById(R.id.EditTextStudentBirth);
        editTextStudentBirth.setOnClickListener(view -> {
            @SuppressLint("SetTextI18n") DatePickerDialog datePickerDialog = new DatePickerDialog(ActivityAddStudent.this, (view1, year1, monthOfYear, dayOfMonth1) -> {
                // Thiết lập giá trị cho EditText
                editTextStudentBirth.setText(dayOfMonth1 + "/" + (monthOfYear + 1) + "/" + year1);
            }, year, month, dayOfMonth);
            datePickerDialog.show();
        });
    }

    //dialog add
    private void DialogAdd(int id_subject) {

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialogaddstudent);
        dialog.setCanceledOnTouchOutside(false);

        Button btnYes = dialog.findViewById(R.id.buttonYesAddStudent);
        Button btnNo = dialog.findViewById(R.id.buttonNoAddStudent);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextStudentName.getText().toString().trim();
                String code = editTextStudentCode.getText().toString().trim();
                String birthday = editTextStudentBirth.getText().toString().trim();
                String sex = "";

                //Kiểm tra radiobtn true tại đâu thì lấy giá trị nam hay nữ gán vào sex
                if (radioButtonMale.isChecked()) {
                    sex = "Nam";
                } else {
                    sex = "Nữ";
                }

                if (name.equals("") || code.equals("") || birthday.equals("") || sex.equals("")) {
                    Toast.makeText(ActivityAddStudent.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkStudents = database.checkStudent(code);
                    if (!checkStudents) {
                        Student student = CreateStudent(id_subject);

                        database.AddStudent(student);

                        Intent intent = new Intent(ActivityAddStudent.this, ActivityStudent.class);
                        intent.putExtra("id_subject", id_subject);
                        startActivity(intent);

                        Toast.makeText(ActivityAddStudent.this, "Thêm học sinh thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ActivityAddStudent.this, "Học sinh đã tồn tại ! Vui lòng thử lại", Toast.LENGTH_SHORT).show();
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

    private Student CreateStudent(int id_subject) {
        String name = editTextStudentName.getText().toString().trim();
        String code = editTextStudentCode.getText().toString().trim();
        String birthday = editTextStudentBirth.getText().toString().trim();
        String sex = "";

        //Kiểm tra radiobtn true tại đâu thì lấy giá trị nam hay nữ gán vào sex
        if (radioButtonMale.isChecked()) {
            sex = "Nam";
        } else {
            sex = "Nữ";
        }

        Student student = new Student(name, sex, code, birthday, id_subject);
        return student;
    }


}