package com.app.courseplan.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.app.courseplan.DatabaseHelper;
import com.app.courseplan.R;

public class CourseDetails extends AppCompatActivity {

    Button cancelButton;
    Button saveButton;
    EditText courseTitle, courseURL, courseDescription, startDate, endDate;


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





        cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseDetails.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


}