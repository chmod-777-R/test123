package au.edu.federation.itech3107.studentattendance30395589;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CourseActivity extends AppCompatActivity {
    private List<Student> studentList;
    private MyDBHelper myDBHelper;
    private String courseName;
    private StudentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        Spinner spinner = findViewById(R.id.spinner);
        RecyclerView rvStudent = findViewById(R.id.rv_student);
        //Accept course Name
        courseName = getIntent().getStringExtra("courseName");
        //init database
        myDBHelper = new MyDBHelper(this);
        //get course
        Course course = myDBHelper.getCourse(courseName);
        //set time table adapter
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, course.getTimetable()));
        //get student list by course name
        studentList = myDBHelper.getStudentList(courseName);
        adapter = new StudentAdapter(studentList, courseName, (String) spinner.getSelectedItem());
        rvStudent.setAdapter(adapter);
        //Set a new date for the adapter when the date changes
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adapter.setDate((String) spinner.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(CourseActivity.this, AddStudentActivity.class).putExtra("courseName", courseName), 100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            studentList.clear();
            studentList.addAll(myDBHelper.getStudentList(courseName));
            adapter.notifyDataSetChanged();
        }
    }
}