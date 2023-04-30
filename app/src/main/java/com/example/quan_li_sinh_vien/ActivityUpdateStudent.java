package com.example.quan_li_sinh_vien;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
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

public class ActivityUpdateStudent extends AppCompatActivity {

    EditText editTextUpdateName, editTextUpdateCode, editTextUpdateBirth, editTextProcessPoint, editTextMiddlePoint, editTextFinalPoint;
    RadioButton rMale, rFemale;
    Button btnUpdateStudent,btnCalculate;
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
        editTextProcessPoint = findViewById(R.id.EditTextUpdateStudentProcessPoint);
        editTextProcessPoint.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL); //số thực hoặc số nguyên
        editTextMiddlePoint = findViewById(R.id.EditTextUpdateStudentMiddlePoint);
        editTextMiddlePoint.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        editTextFinalPoint = findViewById(R.id.EditTextUpdateStudentFinalPoint);
        editTextFinalPoint.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        btnUpdateStudent = findViewById(R.id.buttonUpdateStudent);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        String name = intent.getStringExtra("name");
        String sex = intent.getStringExtra("sex");
        String code = intent.getStringExtra("code");
        String birth = intent.getStringExtra("birth");
        float processpoint = intent.getFloatExtra("processpoint", 0);
        float middlepoint = intent.getFloatExtra("middlepoint", 0);
        float finalpoint = intent.getFloatExtra("finalpoint", 0);

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
        editTextProcessPoint.setText(processpoint + "");
        editTextMiddlePoint.setText(middlepoint + "");
        editTextFinalPoint.setText(finalpoint + "");

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

                String processpoint = editTextProcessPoint.getText().toString().trim();
                String middlepoint = editTextMiddlePoint.getText().toString().trim();
                String finalpoint = editTextFinalPoint.getText().toString().trim();

                if (name.equals("") || code.equals("") || birth.equals("")||processpoint.equals("")||middlepoint.equals("")||finalpoint.equals("")) {
                    Toast.makeText(ActivityUpdateStudent.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else if (!isValidDate(birth)) {
                    Toast.makeText(ActivityUpdateStudent.this, "Ngày sinh không hợp lệ", Toast.LENGTH_SHORT).show();
                } else if (!isValidScore(Float.parseFloat(processpoint))||!isValidScore(Float.parseFloat(middlepoint))||!isValidScore(Float.parseFloat(finalpoint))) {
                    Toast.makeText(ActivityUpdateStudent.this, "Điểm không hợp lệ", Toast.LENGTH_SHORT).show();

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

        float processpoint = Float.parseFloat(editTextProcessPoint.getText().toString().trim());
        float middlepoint = Float.parseFloat(editTextMiddlePoint.getText().toString().trim());
        float finalpoint = Float.parseFloat(editTextFinalPoint.getText().toString().trim());
        float avegepoint = avegepoint(processpoint,middlepoint,finalpoint);

        Student student = new Student(name, sex, code, birth,processpoint,middlepoint,finalpoint,avegepoint, id_class);
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

    //kiểm tra xem số thực có hợp lệ
    private boolean isValidScore(float score) {
        return score >= 0.0f && score <= 10.0f; // trả về true nếu số thực đưa vào nằm trong khoảng từ 0 đến 10,
    }

    //tính điểm trung bình
    private static float avegepoint(float processpoint, float middlepoint, float finalpoint) {
        float avege = processpoint*0.2f + middlepoint*0.3f + finalpoint*0.5f;

        if (avege < 0 || avege > 10) {
            return -1;
        }
        return Float.parseFloat(String.format("%.2f", avege));
    }
}