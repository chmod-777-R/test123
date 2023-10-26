package au.edu.federation.itech3107.studentattendance30395589;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddCourseActivity extends AppCompatActivity {
    private String startDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        // init views
        EditText etCourseName = findViewById(R.id.et_course_name);
        TextView etDate = findViewById(R.id.et_date);
        // click date button select date
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar instance = Calendar.getInstance();
                //show a date picker dialog to select date
                new DatePickerDialog(AddCourseActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        ArrayList<String> strings = new ArrayList<>();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
                        instance.set(Calendar.YEAR, year);
                        instance.set(Calendar.MONTH, month);
                        instance.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        startDate = dateFormat.format(instance.getTime());
                        try {
                            Date parse = dateFormat.parse(startDate);
                            Calendar instance = Calendar.getInstance();
                            instance.setTime(parse);
                            strings.add(startDate);
                            for (int i = 0; i < 11; i++) {
                                instance.add(Calendar.DAY_OF_WEEK, 7);
                                strings.add(dateFormat.format(instance.getTime()));
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        etDate.setText(startDate + " To " + strings.get(strings.size() - 1));
                    }
                }, instance.get(Calendar.YEAR), instance.get(Calendar.MONTH), instance.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        //click sure button
        findViewById(R.id.btn_sure)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (startDate.isEmpty()) {
                            Toast.makeText(AddCourseActivity.this, "Please select a time", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        String courseName = etCourseName.getText().toString();
                        if (courseName.isEmpty()) {
                            Toast.makeText(AddCourseActivity.this, "name is empty", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        new MyDBHelper(AddCourseActivity.this)
                                .addCourse(courseName, startDate);
                        setResult(RESULT_OK);
                        finish();
                    }
                });
    }
}