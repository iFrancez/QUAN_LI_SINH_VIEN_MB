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
        int id_class = intent.getIntExtra("id_class", 0);

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
                DialogUpdate(id, id_class);
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

    private void DialogUpdate(int id, int id_class) {
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
                } else if (!isValidDate(birth)) {
                    Toast.makeText(ActivityUpdateStudent.this, "Ngày sinh không hợp lệ", Toast.LENGTH_SHORT).show();
                } else {
                    Student student = createStudent(id_class);
                    // Thực hiện cập nhật thông tin học sinh
                    database.UpdateStudent(student, id);

                    // Hiển thị thông báo cập nhật thành công và chuyển đến màn hình danh sách học sinh
                    Intent intent = new Intent(ActivityUpdateStudent.this, ActivityStudent.class);
                    intent.putExtra("id_class", id_class);
                    startActivity(intent);
                    Toast.makeText(ActivityUpdateStudent.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();

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
    private Student createStudent(int id_class) {
        String name = editTextUpdateName.getText().toString().trim();
        String code = editTextUpdateCode.getText().toString().trim();
        String birth = editTextUpdateBirth.getText().toString().trim();
        String sex = "";
        if (rMale.isChecked()) {
            sex = "Nam";
        } else {
            sex = "Nữ";
        }

        Student student = new Student(name, sex, code, birth,id_class);
        return student;
    }

    //kiểm tra xem ngày sinh hợp lệ hay không
    private boolean isValidDate(String input) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        format.setLenient(false);
        try {
            Date date = format.parse(input);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH) + 1;
            int year = calendar.get(Calendar.YEAR);
            if (year < 0) {
                return false;
            }
            if (month < 1 || month > 12) {
                return false;
            }
            if (day < 1 || day > 31) {
                return false;
            }
            if (month == 4 || month == 6 || month == 9 || month == 11) {
                return (day <= 30);
            }
            if (month == 2) {
                if (year % 4 == 0) {
                    return (day <= 29);
                } else {
                    return (day <= 28);
                }
            }
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

}