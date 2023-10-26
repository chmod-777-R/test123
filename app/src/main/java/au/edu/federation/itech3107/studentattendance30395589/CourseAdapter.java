package au.edu.federation.itech3107.studentattendance30395589;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    public List<Course> courseList;
    private boolean first = true;

    public CourseAdapter(List<Course> courseList) {
        this.courseList = courseList;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CourseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (first && position == 0) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(holder.itemView, "translationX",  -300,0);
            animator.setRepeatCount(3);
            animator.setDuration(1500);
            animator.setRepeatMode(ValueAnimator.RESTART);
            animator.start();

            first = false;
        }
        holder.tvName.setText(courseList.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, CourseActivity.class);
                intent.putExtra("courseName", courseList.get(position).getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public Course deleteItem(int position) {

        return courseList.remove(position);

    }

    public class CourseViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = (TextView) itemView;
        }
    }
}
