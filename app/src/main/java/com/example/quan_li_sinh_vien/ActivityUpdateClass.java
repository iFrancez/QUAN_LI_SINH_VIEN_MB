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
import com.example.quan_li_sinh_vien.model.Subject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ActivityUpdateClass extends AppCompatActivity {

    Button btnUpdateClass;
    RadioButton rMaleTeacher, rFeMaleTeacher;
    EditText editTextUpdateClassName, editTextUpdateClassCode, editTextUpdateTeacherName, editTextUpdateTeacherCode, editTextUpdateTeacherBirth;

    database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_class);

        editTextUpdateClassName = findViewById(R.id.EditTextUpdateClassName);
        editTextUpdateClassCode = findViewById(R.id.EditTextUpdateClassCode);
        editTextUpdateTeacherName = findViewById(R.id.EditTextUpdateTeacherName);
        editTextUpdateTeacherCode = findViewById(R.id.EditTextUpdateTeacherCode);
        editTextUpdateTeacherBirth = findViewById(R.id.EditTextUpdateTeacherBirth);

        btnUpdateClass = findViewById(R.id.buttonUpdateClass);
        rMaleTeacher = findViewById(R.id.radiobuttonUpdateMaleTeacher);
        rFeMaleTeacher = findViewById(R.id.radiobuttonUpdateFemaleTeacher);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        String name_class = intent.getStringExtra("name_class");
        String code_class = intent.getStringExtra("code_class");
        String name_teacher = intent.getStringExtra("name_teacher");
        String code_teacher = intent.getStringExtra("code_teacher");
        String sex_teacher = intent.getStringExtra("sex_teacher");
        String birth_teacher = intent.getStringExtra("birth_teacher");
        int id_subject = intent.getIntExtra("id_subject", 0);

        //Gán giá trị lên editText và radiobutton
        editTextUpdateClassName.setText(name_class);
        editTextUpdateClassCode.setText(code_class);
        editTextUpdateTeacherBirth.setText(birth_teacher);
        editTextUpdateTeacherCode.setText(code_teacher);
        editTextUpdateTeacherName.setText(name_teacher);


        if (sex_teacher.equals("Nam")) {
            rMaleTeacher.setChecked(true);
            rFeMaleTeacher.setChecked(false);
        } else {
            rMaleTeacher.setChecked(false);
            rFeMaleTeacher.setChecked(true);
        }
        database = new database(this);

        btnUpdateClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUpdateClass(id, id_subject);
            }
        });

        //date
        // Tạo DatePickerDialog
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        final EditText editTextTeacherBirth = findViewById(R.id.EditTextUpdateTeacherBirth);
        editTextTeacherBirth.setOnClickListener(view -> {
            @SuppressLint("SetTextI18n") DatePickerDialog datePickerDialog = new DatePickerDialog(ActivityUpdateClass.this, (view1, year1, monthOfYear, dayOfMonth1) -> {
                // Thiết lập giá trị cho EditText
                editTextTeacherBirth.setText(dayOfMonth1 + "/" + (monthOfYear + 1) + "/" + year1);
            }, year, month, dayOfMonth);
            datePickerDialog.show();
        });

    }

    private void DialogUpdateClass(int id, int id_subject) {
        Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.dialogupdateclass);

        dialog.setCanceledOnTouchOutside(false);

        Button btnY = dialog.findViewById(R.id.buttonYesUpdateClass);
        Button btnN = dialog.findViewById(R.id.buttonNoUpdateClass);

        //đồng ý thì update lớp học
        btnY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name_class = editTextUpdateClassName.getText().toString().trim();
                String code_class = editTextUpdateClassCode.getText().toString().trim();
                String name_teacher = editTextUpdateTeacherName.getText().toString().trim();
                String code_teacher = editTextUpdateTeacherCode.getText().toString().trim();
                String birth_teacher = editTextUpdateTeacherBirth.getText().toString().trim();



                if(name_class.equals("")||code_class.equals("")||name_teacher.equals("")||code_teacher.equals("")||birth_teacher.equals("")){
                    Toast.makeText(ActivityUpdateClass.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else if (!isValidDate(birth_teacher)) {
                    Toast.makeText(ActivityUpdateClass.this, "Ngày sinh không hợp lệ", Toast.LENGTH_SHORT).show();
                }
                else {
                    ClassSub classSub = createClass(id_subject);
                    //update
                    database.UpdateClass(classSub,id);

                    //chuyển qua activityclass
                    Intent intent = new Intent(ActivityUpdateClass.this,ActivityClass.class);
                    //gửi id của subject
                    intent.putExtra("id_subject",id_subject);
                    startActivity(intent);
                    Toast.makeText(ActivityUpdateClass.this, "Cập nhật lớp học thành công", Toast.LENGTH_SHORT).show();
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


    //create lớp học
    private ClassSub createClass(int id_subject) {
        String name_class = editTextUpdateClassName.getText().toString().trim();
        String code_class = editTextUpdateClassCode.getText().toString().trim();
        String name_teacher = editTextUpdateTeacherName.getText().toString().trim();
        String code_teacher = editTextUpdateTeacherCode.getText().toString().trim();
        String birth_teacher = editTextUpdateTeacherBirth.getText().toString().trim();
        String sex_teacher = "";
        if (rMaleTeacher.isChecked()) {
            sex_teacher = "Nam";
        } else {
            sex_teacher = "Nữ";
        }

        ClassSub classSub = new ClassSub(name_class,code_class,name_teacher,sex_teacher,code_teacher,birth_teacher,id_subject);
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