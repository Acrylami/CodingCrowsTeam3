package com.app.courseplan;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
        //Instead of displaying ID, display num in list for current month
        holder.courseId.setText(String.valueOf(position+1));
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
            }
            else {
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
        private ImageButton linkButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            courseTitle = itemView.findViewById(R.id.course_title_txt);
            courseId = itemView.findViewById(R.id.course_id_txt);
            courseUrl = itemView.findViewById(R.id.course_url_txt);
            linkButton = itemView.findViewById(R.id.linkButton);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(adapterPosition);
        }
    }
}
