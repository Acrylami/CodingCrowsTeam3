package com.app.courseplan;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.courseplan.model.Course;
import com.app.courseplan.ui.CourseDetails;

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
        final Course course = mCourses.get(position);
        holder.courseId.setText(String.valueOf(course.getId()));
        holder.courseTitle.setText(String.valueOf(course.getCourseName()));
        holder.courseUrl.setText(String.valueOf(course.getCourseUrl()));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                this to to get the course Id and name and send them to the Course Details Class for the delete dialog
                Intent intent = new Intent(context, CourseDetails.class);
                intent.putExtra("id", String.valueOf(course.getId()));
                intent.putExtra("title", String.valueOf(course.getCourseName()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        //Added null pointer error handling
        if (mCourses != null) {
            return mCourses.size();
        }
        else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView courseId;
        private TextView courseTitle;
        private TextView courseUrl;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            courseTitle = itemView.findViewById(R.id.course_title_txt);
            courseId = itemView.findViewById(R.id.course_id_txt);
            courseUrl = itemView.findViewById(R.id.course_url_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(adapterPosition);
        }
    }
}
