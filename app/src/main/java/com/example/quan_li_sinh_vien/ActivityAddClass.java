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
import com.example.quan_li_sinh_vien.model.ClassSub;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ActivityAddClass extends AppCompatActivity {

    Button buttonAddClass;
    EditText editTextClassName, editTextClassCode, editTextTeacherName, editTextTeacherCode, editTextTeacherBirth;
    RadioButton radioButtonMaleTeacher, RadioButtonFeMaleTeacher;

    com.example.quan_li_sinh_vien.database.database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);

        buttonAddClass = findViewById(R.id.buttonAddClass);
        editTextClassCode = findViewById(R.id.EditTextClassCode);
        editTextClassName = findViewById(R.id.EditTextClassName);
        editTextTeacherCode = findViewById(R.id.EditTextTeacherCode);
        editTextTeacherName = findViewById(R.id.EditTextTeacherName);
        editTextTeacherBirth = findViewById(R.id.EditTextTeacherBirth);
        radioButtonMaleTeacher = findViewById(R.id.radiobuttonMaleTeacher);
        RadioButtonFeMaleTeacher = findViewById(R.id.radiobuttonFemaleTeacher);

        //lấy id subject
        Intent intent = getIntent();
        int id_subject = intent.getIntExtra("id_subject", 0);

        database = new database(this);
        buttonAddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogAddClass(id_subject);
            }
        });

        //date
        // Tạo DatePickerDialog
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        final EditText editTextTeacherBirth = findViewById(R.id.EditTextTeacherBirth);
        editTextTeacherBirth.setOnClickListener(view -> {
            @SuppressLint("SetTextI18n") DatePickerDialog datePickerDialog = new DatePickerDialog(ActivityAddClass.this, (view1, year1, monthOfYear, dayOfMonth1) -> {
                // Thiết lập giá trị cho EditText
                editTextTeacherBirth.setText(dayOfMonth1 + "/" + (monthOfYear + 1) + "/" + year1);
            }, year, month, dayOfMonth);
            datePickerDialog.show();
        });

    }

    //dialog add
    private void DialogAddClass(int id_subject) {
        Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.dialogaddclass);

        dialog.setCanceledOnTouchOutside(false);

        Button btnY = dialog.findViewById(R.id.buttonYesAddClass);
        Button btnN = dialog.findViewById(R.id.buttonNoAddClass);

        btnY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name_class = editTextClassName.getText().toString().trim();
                String code_class = editTextClassCode.getText().toString().trim();
                String name_teacher = editTextTeacherName.getText().toString().trim();
                String sex_teacher = "";
                if (radioButtonMaleTeacher.isChecked()) {
                    sex_teacher = "Nam";
                } else {
                    sex_teacher = "Nữ";
                }
                String code_teacher = editTextTeacherCode.getText().toString().trim();
                String birth_teacher = editTextTeacherBirth.getText().toString().trim();

                if (name_class.equals("") || code_class.equals("") || name_teacher.equals("") || sex_teacher.equals("") || birth_teacher.equals("") || code_teacher.equals("")) {
                    Toast.makeText(ActivityAddClass.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else if (!isValidDate(birth_teacher)) {
                    Toast.makeText(ActivityAddClass.this, "Ngày sinh không hợp lệ", Toast.LENGTH_SHORT).show();
                }
                else {
                    Boolean checkClass = database.checkClass(code_class);
                    if (!checkClass) {
                        ClassSub classSub = createClass(id_subject);
                        database.AddClass(classSub);
                        Intent intent = new Intent(ActivityAddClass.this, ActivityClass.class);
                        intent.putExtra("id_subject", id_subject);
                        startActivity(intent);
                        Toast.makeText(ActivityAddClass.this, "Thêm lớp học thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ActivityAddClass.this, "Mã lớp học đã tồn tại ! Vui lòng thử lại", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });
        btnN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    private ClassSub createClass(int id_subject) {
        String name_class = editTextClassName.getText().toString().trim();
        String code_class = editTextClassCode.getText().toString().trim();
        String name_teacher = editTextTeacherName.getText().toString().trim();
        String sex_teacher = "";
        if (radioButtonMaleTeacher.isChecked()) {
            sex_teacher = "Nam";
        } else {
            sex_teacher = "Nữ";
        }
        String code_teacher = editTextTeacherCode.getText().toString().trim();
        String birth_teacher = editTextTeacherBirth.getText().toString().trim();

        ClassSub classSub = new ClassSub(name_class, code_class, name_teacher, sex_teacher, code_teacher, birth_teacher, id_subject);
        return classSub;
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