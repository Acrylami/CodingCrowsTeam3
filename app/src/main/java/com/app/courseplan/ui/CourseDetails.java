package com.app.courseplan.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.app.courseplan.DatabaseHelper;
import com.app.courseplan.R;

public class CourseDetails extends AppCompatActivity {

    Button cancelButton;
    Button saveButton;
    EditText courseTitle, courseURL, courseDescription, startDate, endDate;
    //    Date Picker
    private DatePickerDialog.OnDateSetListener startDateSetListener, endDateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        courseTitle = findViewById(R.id.courseTitle);
        startDate = findViewById(R.id.startDate);
        endDate = findViewById(R.id.endDate);
        courseURL = findViewById(R.id.courseURL);
        courseDescription = findViewById(R.id.courseDescription);
        saveButton = findViewById(R.id.saveButton);
// Save functionality
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper myDB = new DatabaseHelper(CourseDetails.this);
                myDB.addCourse(courseTitle.getText().toString().trim(),
                        startDate.getText().toString().trim(),
                        endDate.getText().toString().trim(),
                        courseURL.getText().toString().trim(),
                        courseDescription.getText().toString().trim());
            }
        });

//        Cancel Button
        cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseDetails.this, MainActivity.class);
                startActivity(intent);
            }
        });


//Date Picker Dialogs and Listeners
//        Start Date
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
//        End Date
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
//              months counts from zero ie January = 0 & Dec = 11
                month = month + 1;
                String date = month + "/" + day + "/" + year;
                startDate.setText(date);
            }
        };
        endDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//              months counts from zero ie January = 0 & Dec = 11
                month = month + 1;
                String date = month + "/" + day + "/" + year;
                endDate.setText(date);
            }
        };

        Intent intent = getIntent();
        if(intent.getParcelableExtra("selected_course") != null) {

        }

    }


}