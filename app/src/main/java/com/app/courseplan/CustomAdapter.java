package com.app.courseplan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList course_id, course_title, course_startdate, course_enddate, course_url, course_description;

    public CustomAdapter(Context context,
                         ArrayList course_id,
                         ArrayList course_title,
                         ArrayList course_url){
        this.context = context;
        this.course_id = course_id;
        this.course_title = course_title;
        this.course_url = course_url;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.course_id_txt.setText(String.valueOf(course_id.get(position)));
        holder.course_title_txt.setText(String.valueOf(course_title.get(position)));
        holder.course_url_txt.setText(String.valueOf(course_url.get(position)));

    }

    @Override
    public int getItemCount() {
        return course_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView course_id_txt;
        TextView course_title_txt;
        TextView course_url_txt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            course_title_txt = itemView.findViewById(R.id.course_title_txt);
            course_id_txt = itemView.findViewById(R.id.course_id_txt);
            course_url_txt = itemView.findViewById(R.id.course_url_txt);
        }
    }
}
