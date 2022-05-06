package com.example.habitapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "TasksAndHabits.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_TASK = "task";
    private static final String TABLE_HABIT = "habit";
    private static final String CREATE_TABLE_TASK =
            "CREATE TABLE " + TABLE_TASK + " (IdTask INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, DueDate TEXT, Details TEXT);";
    private static final String CREATE_TABLE_HABIT =
            "CREATE TABLE " + TABLE_HABIT + " (IdHabit INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, Time TEXT, Details TEXT);";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TASK);
        db.execSQL(CREATE_TABLE_HABIT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HABIT);
        onCreate(db);
    }

    public void addTask(String name, String dueDate, String details){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("Name", name);
        cv.put("DueDate", dueDate);
        cv.put("Details", details);

        long result = db.insert(TABLE_TASK, null, cv);
        if (result == -1)
            Toast.makeText(context, "Failed to add task", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Task added", Toast.LENGTH_SHORT).show();
    }

    public void deleteTask(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_TASK, "IdTask=?", new String[]{id});

        if (result == -1)
            Toast.makeText(context, "Failed to delete task", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Task deleted", Toast.LENGTH_SHORT).show();
    }

    public void addHabit(String name, String time, String details){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("Name", name);
        cv.put("Time", time);
        cv.put("Details", details);
        db.insert(TABLE_TASK, null, cv);
    }

    public void countTasks(){
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @SuppressLint("Recycle")
    public Cursor readAllTasks(){
        String query = "SELECT * FROM " + TABLE_TASK;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null)
            cursor = db.rawQuery(query, null);

        return cursor;
    }
}
