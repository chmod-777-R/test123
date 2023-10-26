package au.edu.federation.itech3107.studentattendance30395589;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Course> courseList = new ArrayList<>();
    CourseAdapter adapter = new CourseAdapter(courseList);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rvCourse = findViewById(R.id.rv_course);
        rvCourse.setAdapter(adapter);
        courseList.addAll(new MyDBHelper(this).getCourse());
        adapter.notifyDataSetChanged();
        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, AddCourseActivity.class), 100);
            }
        });
        new ItemTouchHelper(new SwipeToDeleteCallback()).attachToRecyclerView(rvCourse);
        Toast.makeText(this, "You can drag list items to delete ", Toast.LENGTH_LONG).show();
        }


    public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {


        public SwipeToDeleteCallback() {
            super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);

        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            new MyDBHelper(MainActivity.this).deleteCourse(adapter.deleteItem(position).getName());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            courseList.clear();
            courseList.addAll(new MyDBHelper(this).getCourse());
            adapter.notifyDataSetChanged();
        }
    }

}