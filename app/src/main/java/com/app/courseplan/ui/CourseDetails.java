package com.app.courseplan.ui;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.app.courseplan.DatabaseHelper;
import com.app.courseplan.R;
import com.app.courseplan.model.Course;

public class CourseDetails extends AppCompatActivity {

    private EditText courseTitle, courseURL, courseDescription, startDate, endDate;
    private DatePickerDialog.OnDateSetListener startDateSetListener, endDateSetListener;
    private Course mCourseToEdit;
    private DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        //EditText fields
        courseTitle = findViewById(R.id.courseTitle);
        startDate = findViewById(R.id.startDate);
        endDate = findViewById(R.id.endDate);
        courseURL = findViewById(R.id.courseURL);
        courseDescription = findViewById(R.id.courseDescription);

        Intent intent = getIntent();
        if (intent.getParcelableExtra("selected_course") != null) {
            mCourseToEdit = intent.getParcelableExtra("selected_course");
            fillFields();
        }

        myDB = new DatabaseHelper(CourseDetails.this);

        // Save functionality
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            //We'll need some way to differentiate between editing an existing course and adding
            // a new one
            //to prevent duplicate database entries...
            @Override
            public void onClick(View view) {
                //Create a new course
                Course newCourse = new Course(courseTitle.getText().toString(),
                        startDate.getText().toString(), endDate.getText().toString(),
                        courseDescription.getText().toString(), courseURL.getText().toString());

                if (mCourseToEdit == null) {
                    //Save to database
                    myDB.addCourse(
                            newCourse.getCourseName().trim(),
                            newCourse.getStartDate().trim(),
                            newCourse.getEndDate().trim(),
                            newCourse.getCourseUrl().trim(),
                            newCourse.getDescription().trim());
                } else {
                    Course savedCourse = new Course(
                            mCourseToEdit.getId(),
                            courseTitle.getText().toString(),
                            startDate.getText().toString(),
                            endDate.getText().toString(),
                            courseDescription.getText().toString(),
                            courseURL.getText().toString());
                    myDB.updateCourse(savedCourse);
                }
                finish();
            }
        });

        //  Delete Button functionality
        Button deleteButton = findViewById(R.id.deleteButton);
        if (mCourseToEdit == null) {
            deleteButton.setVisibility(View.GONE);
        }
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });

        // Cancel Button
        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseDetails.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //Date Picker Dialogs and Listeners
        // Start Date
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        CourseDetails.this,
                        R.style.MyDatePickerStyle,
                        startDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.show();
            }
        });

        // End Date
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        CourseDetails.this,
                        R.style.MyDatePickerStyle,
                        endDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.show();
            }
        });

        startDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // months counts from zero ie January = 0 & Dec = 11
                month = month + 1;
                String date = month + "/" + day + "/" + year;
                startDate.setText(date);
            }
        };
        endDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // months counts from zero ie January = 0 & Dec = 11
                month = month + 1;
                String date = month + "/" + day + "/" + year;
                endDate.setText(date);
            }
        };
    }

    private void fillFields() {
        courseTitle.setText(mCourseToEdit.getCourseName());
        courseDescription.setText(mCourseToEdit.getDescription());
        courseURL.setText(mCourseToEdit.getCourseUrl());
        startDate.setText(mCourseToEdit.getStartDate());
        endDate.setText(mCourseToEdit.getEndDate());
    }

    // Confirm Dialog for delete button
    void confirmDialog() {
        final int id = mCourseToEdit.getId();
        String title = mCourseToEdit.getCourseName();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + title + " ?");
        builder.setMessage("Are you sure you want to delete " + title + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                myDB.deleteOneRow(Integer.toString(id));
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                // Nothing will happen when no is clicked apart from the dialog being dismissed
            }
        });
        builder.create().show();
    }
}