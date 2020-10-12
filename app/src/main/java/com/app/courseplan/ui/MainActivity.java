package com.app.courseplan.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.app.courseplan.CourseAdapter;
import com.app.courseplan.DatabaseHelper;
import com.app.courseplan.R;
import com.app.courseplan.model.Course;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private DatabaseHelper myDB;
    private ArrayList<Course> mCourseList;
    private CourseAdapter mCourseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCourseList = new ArrayList<>();

        mRecyclerView = findViewById(R.id.recyclerview);
        FloatingActionButton addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CourseDetails.class);
                startActivity(intent);
            }
        });

        myDB = new DatabaseHelper(MainActivity.this);
        mCourseAdapter = new CourseAdapter(MainActivity.this, mCourseList);
        mRecyclerView.setAdapter(mCourseAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }


    @Override
    protected void onResume() {
        super.onResume();
        mCourseList.clear();
        StoreDataInArrays();
        mCourseAdapter.notifyDataSetChanged();
    }

    void StoreDataInArrays() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                Course course = new Course();
                course.setId(cursor.getInt(0));
                course.setCourseName(cursor.getString(1));
                course.setStartDate(cursor.getString(2));
                course.setEndDate(cursor.getString(3));
                course.setCourseUrl(cursor.getString(4));
                course.setDescription(cursor.getString(5));
                mCourseList.add(course);
            }
        }
    }
}