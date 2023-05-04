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
import java.util.Locale;

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
        int id_class = intent.getIntExtra("id_class", 0);


        database = new database(this);
        buttonAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogAdd(id_class);
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
    private void DialogAdd(int id_class) {

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
                }  else if (!isValidDate(birthday)) {
                    Toast.makeText(ActivityAddStudent.this, "Ngày sinh không hợp lệ", Toast.LENGTH_SHORT).show();
                }else {
                    boolean checkStudents = database.checkStudentExistsInClass(id_class, code); // kiểm tra sinh viên đã tồn tại trong lớp hay chưa
                                                                                                // có thể cho học sinh đó vào lớp khác khi học sinh muốn chuyển lớp
                                                                                                // nhưng phải xoá học sinh đó ở lớp trước
                    if (!checkStudents) {
                        Student student = CreateStudent(id_class);

                        String code_student = database.AddStudent(student);

                        database.addClassStudent(id_class, code_student);

                        Intent intent = new Intent(ActivityAddStudent.this, ActivityStudent.class);
                        intent.putExtra("id_class", id_class);
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

    private Student CreateStudent(int id_class) {
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

        Student student = new Student(name, sex, code, birthday, id_class);
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