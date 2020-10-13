package com.app.courseplan.ui;


import android.content.Intent;
import android.database.Cursor;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.courseplan.CourseAdapter;
import com.app.courseplan.DatabaseHelper;
import com.app.courseplan.R;
import com.app.courseplan.model.Course;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements
        CourseAdapter.CourseAdapterOnClickHandler {


    private RecyclerView mRecyclerView;
    private DatabaseHelper myDB;
    private ArrayList<Course> mCourseList;
    private ArrayList<Course> thisMonthCourses = new ArrayList<Course>();
    private CourseAdapter mCourseAdapter;

    public Calendar displayCal;
    public Date currentDate;
    public String currentMonth;

    private void updateCurrentMonthText() {
        //Display current month as text e.g. October
        currentDate = displayCal.getTime();
        SimpleDateFormat dfMonthText = new SimpleDateFormat("MMMM YYYY", Locale.getDefault());
        String formattedDate = dfMonthText.format(currentDate);
        TextView tv1 = (TextView)findViewById(R.id.monthName);
        tv1.setText(formattedDate);
    }

    private void filterCourses() {
        //Filters the courses on the home screen depending on the month selected
        for (Course eachCourse: mCourseList) {
            Date startDate;
            Date endDate;
            //convert dates to real Date
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
            try {
                startDate = formatter.parse(eachCourse.getStartDate());
                endDate = formatter.parse(eachCourse.getEndDate());
                Date firstOfMonth = currentDate;

                //Check if either the start date or end date is current month, or any date in between it
                Log.e("=================", "Course: " + eachCourse.getCourseName() + ",  date is: " + currentDate + " and the course date is: " + startDate + "-" + endDate);
                if ((currentDate.after(startDate) || startDate.equals(currentDate) || (currentDate.getMonth()==startDate.getMonth() && currentDate.getYear()==startDate.getYear())) && (currentDate.before(endDate) || currentDate.equals(endDate) || (currentDate.getMonth()==endDate.getMonth() && currentDate.getYear()==endDate.getYear()))) {
                    //Add the course if it is for the current month
                    thisMonthCourses.add(eachCourse);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get current date from phone
        displayCal = Calendar.getInstance();
        //, set it to the first of the month for better course filtering
        displayCal.set(Calendar.DAY_OF_MONTH, 1);

        updateCurrentMonthText();

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

        //Next month
        ImageButton nextMonthButton = findViewById(R.id.nextMonthButton);
        nextMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //switch to next month's view
                displayCal.add(Calendar.MONTH, 1); //Add one month to the calendar
                //Intent intent = new Intent(MainActivity.this, CourseDetails.class);
                //startActivity(intent);
                onResume();
            }
        });

        //Previous month
        ImageButton previousMonthButton = findViewById(R.id.previousMonthButton);
        previousMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //switch to next month's view
                displayCal.add(Calendar.MONTH, -1); //Go back one month in the calendar
                //Intent intent = new Intent(MainActivity.this, CourseDetails.class);
                //startActivity(intent);
                onResume();
            }
        });


        myDB = new DatabaseHelper(MainActivity.this);
        mCourseAdapter = new CourseAdapter(MainActivity.this, mCourseList, this);
        mRecyclerView.setAdapter(mCourseAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }



    @Override
    protected void onResume() {
        super.onResume();
        //Error handling for clearing course list for refreshing view
        if (mCourseList != null) {
            mCourseList.clear();
        }
        if (thisMonthCourses != null) {
            thisMonthCourses.clear();
        }

        updateCurrentMonthText(); //Update the date with current month in case a next/previous month button pressed
        StoreDataInArrays(); //Gets data from database and stores it in mCourseList
        filterCourses(); //Only add courses for the current month to be displayed
        mCourseAdapter.notifyDataSetChanged(); //Finally display new view
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

    @Override
    public void onClick(int position) {
        Course course = mCourseList.get(position);
        Intent intent = new Intent(this, CourseDetails.class);
        intent.putExtra("selected_course", course);
        startActivity(intent);
    }
}