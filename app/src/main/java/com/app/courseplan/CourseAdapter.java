package com.app.courseplan;

import android.content.Context;


import android.content.Intent;
import android.net.Uri;

import android.graphics.Color;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.app.courseplan.model.Course;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.MyViewHolder> {
    private int colour = 0xFFFFFFFF;
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
        //Instead of displaying ID, display num in list for current month
        holder.courseId.setText(String.valueOf(position + 1));
        holder.courseTitle.setText(String.valueOf(course.getCourseName()));
        holder.courseUrl.setText(String.valueOf(course.getCourseUrl()));

        //LINK BUTTON CODE
        //Check if url is not set, if not disable button
        if (course.getCourseUrl().length() < 1) {
            //Hides the link button if no link
            holder.linkButton.setEnabled(false);
            holder.linkButton.setVisibility(View.GONE);
        }
        //If there is a link, then process it and set up click event
        else {
            //URL handler
            //Check if it has http:// at start
            String urlToCheck = String.valueOf(holder.courseUrl.getText());
            if (!(urlToCheck.startsWith("http://") || urlToCheck.startsWith(("https://")))) {
                urlToCheck = "http://" + String.valueOf(holder.courseUrl.getText());
            } else {
                urlToCheck = String.valueOf(holder.courseUrl.getText()); //quick hack to add http://
            }
            final String urlToOpen = urlToCheck;

            //Set on click event
            holder.linkButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Open link in browser
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlToOpen));
                    context.startActivity(browserIntent);
                }
            });
        }

        //Find colour
        if (course.getId() % 10 == 1 || course.getId() % 10 == 6) {
            colour = 0XFFFFA69E;
        } else if (course.getId() % 10 == 2 || course.getId() % 10 == 7) {
            colour = 0XFFFAF3DD;
        } else if (course.getId() % 10 == 3 || course.getId() % 10 == 8) {
            colour = 0XFFB8F2E6;
        } else if (course.getId() % 10 == 4 || course.getId() % 10 == 9) {
            colour = 0XFFAED9E0;
        } else if (course.getId() % 10 == 5 || course.getId() % 10 == 0) {
            colour = 0XFF5E6472;
            //Also set text colour
            holder.courseTitle.setTextColor(Color.WHITE);
            holder.courseId.setTextColor(Color.WHITE);
            holder.courseUrl.setTextColor(0xffeeeeee);
        }
        //Background colour
        //itemView.findViewById(R.id.constraint_course).setBackgroundColor(colour);
        holder.courseContainer.setBackgroundColor(colour);
    }

    @Override
    public int getItemCount() {
        //Added null pointer error handling
        if (mCourses != null) {
            return mCourses.size();
        } else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView courseId;
        private TextView courseTitle;
        private TextView courseUrl;
        private ImageButton linkButton;

        ConstraintLayout courseContainer;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            courseTitle = itemView.findViewById(R.id.course_title_txt);
            courseId = itemView.findViewById(R.id.course_id_txt);
            courseUrl = itemView.findViewById(R.id.course_url_txt);
            linkButton = itemView.findViewById(R.id.linkButton);
            courseContainer = itemView.findViewById(R.id.constraint_course);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(adapterPosition);
        }
    }
}
