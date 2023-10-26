package au.edu.federation.itech3107.studentattendance30395589;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddStudentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        EditText etStudentName = findViewById(R.id.et_student_name);
        EditText etStudentId= findViewById(R.id.et_student_id);
        // get course name
        String courseName = getIntent().getStringExtra("courseName");
        findViewById(R.id.btn_sure)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // get input student name
                        String studentName = etStudentName.getText().toString();
                        String studentId = etStudentId.getText().toString();
                        if (studentName.isEmpty()) {
                            Toast.makeText(AddStudentActivity.this, "name is empty", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (new MyDBHelper(AddStudentActivity.this)
                                .addStudent(studentName, studentId,courseName)) {
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            Toast.makeText(AddStudentActivity.this, "id already exists", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}