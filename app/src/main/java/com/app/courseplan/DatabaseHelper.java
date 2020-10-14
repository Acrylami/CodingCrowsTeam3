package com.app.courseplan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.app.courseplan.model.Course;


public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "CourseLibrary.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_courses";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "course_title";
    private static final String COLUMN_STARTDATE = " course_startdate";
    private static final String COLUMN_ENDDATE = "course_enddate";
    private static final String COLUMN_URL = "course_url";
    private static final String COLUMN_DESCRIPTION = "course_description";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_STARTDATE + " DATE, " +
                COLUMN_ENDDATE + " DATE, " +
                COLUMN_URL + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addCourse(String title, String startdate, String enddate, String url,
            String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_STARTDATE, startdate);
        cv.put(COLUMN_ENDDATE, enddate);
        cv.put(COLUMN_URL, url);
        cv.put(COLUMN_DESCRIPTION, description);
        long result = db.insert(TABLE_NAME, null, cv);
//        If Execution to db fails
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added Course", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ID, course.getId());
        cv.put(COLUMN_TITLE, course.getCourseName());
        cv.put(COLUMN_STARTDATE, course.getStartDate());
        cv.put(COLUMN_ENDDATE, course.getEndDate());
        cv.put(COLUMN_URL, course.getCourseUrl());
        cv.put(COLUMN_DESCRIPTION, course.getDescription());
        long result = db.update(TABLE_NAME, cv, "_id=" + course.getId(), null);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Updated Course", Toast.LENGTH_SHORT).show();
        }
    }

    //Read all data from database
    public Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

//        If database is empty
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    //    Delete course from database
    public void deleteOneRow(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        //Error handling
        if (result == -1) {
            Toast.makeText(context, "Failed to Delete", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Deleted", Toast.LENGTH_SHORT).show();
        }
    }
}
