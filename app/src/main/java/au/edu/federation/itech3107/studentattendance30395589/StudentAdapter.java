package au.edu.federation.itech3107.studentattendance30395589;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.CourseViewHolder> {
    public List<Student> studentList;
    private String courseName;

    public StudentAdapter(List<Student> studentList, String courseName, String date) {
        this.studentList = studentList;
        this.courseName = courseName;
        this.date = date;
    }

    private String date;


    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CourseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvName.setText("ID:" + studentList.get(position).getId() + "\nName:" + studentList.get(position).getName());
        holder.cb.setOnCheckedChangeListener(null);
        holder.cb.setChecked(new MyDBHelper(holder.itemView.getContext()).getAttendance(studentList.get(position).getId(), courseName, date));
        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                new MyDBHelper(buttonView.getContext()).addAttendance(studentList.get(position).getId(), courseName, date, isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public void setDate(String date) {
        this.date = date;
        notifyDataSetChanged();
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public CheckBox cb;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            cb = itemView.findViewById(R.id.cb);
        }
    }
}
