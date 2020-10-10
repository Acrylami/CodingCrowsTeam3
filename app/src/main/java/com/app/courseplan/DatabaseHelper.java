package com.app.courseplan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.sql.Date;


public class DatabaseHelper  extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "CourseLibrary.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_courses";
    private static final String COLUMN_ID ="_id";
    private static final String COLUMN_TITLE = "course_title";
    private static final String COLUMN_STARTDATE =" course_startdate";
    private static final String COLUMN_ENDDATE = "course_enddate";
    private static final String COLUMN_URL ="course_url";
    private static final String COLUMN_DESCRIPTION ="course_description";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                        " (" +  COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
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

    public void addCourse(String title, String startdate, String enddate, String url, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_STARTDATE, String.valueOf(startdate));
        cv.put(COLUMN_ENDDATE, String.valueOf(enddate));
        cv.put(COLUMN_URL, url);
        cv.put(COLUMN_DESCRIPTION, description);
        long result = db.insert(TABLE_NAME, null, cv);
//        If Execution to db fails
        if (result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Added Course", Toast.LENGTH_SHORT).show();
        }
    }

     public Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

//        If database is empty
        Cursor cursor = null;
        if (db!= null ){
            cursor = db.rawQuery(query, null);
        }
        return  cursor;
    }
}
