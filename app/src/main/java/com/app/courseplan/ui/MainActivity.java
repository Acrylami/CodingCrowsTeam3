package com.app.courseplan.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.app.courseplan.CustomAdapter;
import com.app.courseplan.DatabaseHelper;
import com.app.courseplan.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_button;

    DatabaseHelper myDB;
    ArrayList<String> course_id, course_title, course_startdate, course_enddate, course_url, course_description;
    CustomAdapter customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CourseDetails.class);
                startActivity(intent);
            }
        });

        myDB = new DatabaseHelper(MainActivity.this);
        course_id = new ArrayList<>();
        course_title = new ArrayList<>();
        course_startdate = new ArrayList<>();
        course_enddate = new ArrayList<>();
        course_url = new ArrayList<>();
        course_description = new ArrayList<>();

        StoreDataInArrays();
        customAdapter = new CustomAdapter(MainActivity.this, course_id, course_title, course_url);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    void StoreDataInArrays(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT ).show();
        }
        else{
            while(cursor.moveToNext()){
                course_id.add(cursor.getString(0));
                course_title.add(cursor.getString(1));
                course_startdate.add(cursor.getString(2));
                course_enddate.add(cursor.getString(3));
                course_url.add(cursor.getString(4));
                course_description.add(cursor.getString(5));

            }
        }
    }


}