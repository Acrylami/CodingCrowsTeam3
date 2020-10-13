package com.app.courseplan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.courseplan.model.Course;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.MyViewHolder> {

    private Context context;
    private List<Course> mCourses;
    private final CourseAdapterOnClickHandler mClickHandler;

    public interface CourseAdapterOnClickHandler {
        void onClick(int position);
    }

    public CourseAdapter(Context context, List<Course> courses,
            CourseAdapterOnClickHandler clickHandler) {
        this.context = context;
        mCourses = courses;
        mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Course course = mCourses.get(position);
        holder.courseId.setText(String.valueOf(course.getId()));
        holder.courseTitle.setText(String.valueOf(course.getCourseName()));
        holder.courseUrl.setText(String.valueOf(course.getCourseUrl()));
    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView courseId;
        private TextView courseTitle;
        private TextView courseUrl;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            courseTitle = itemView.findViewById(R.id.course_title_txt);
            courseId = itemView.findViewById(R.id.course_id_txt);
            courseUrl = itemView.findViewById(R.id.course_url_txt);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(adapterPosition);
        }
    }
}
