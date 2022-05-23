package com.example.habitapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private final Context context;
    private static final String DATABASE_NAME = "TasksAndHabits.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_TASK = "task";
    private static final String TABLE_HABIT = "habit";
    private static final String CREATE_TABLE_TASK =
            "CREATE TABLE " + TABLE_TASK + " (Id INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, DueDate INTEGER, Details TEXT, Status INTEGER);";
    private static final String CREATE_TABLE_HABIT =
            "CREATE TABLE " + TABLE_HABIT + " (Id INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, Time INTEGER, Details TEXT, Status INTEGER);";

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

    public void addTask(String name, String dueDate, String details, int status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("Name", name);
        cv.put("DueDate", dueDate);
        cv.put("Details", details);
        cv.put("Status", status);

        long result = db.insert(TABLE_TASK, null, cv);
        if (result == -1)
            Toast.makeText(context, "Failed to add new task", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Task added", Toast.LENGTH_SHORT).show();
        db.close();
    }

    public void deleteTask(String id){
        long result = deleteFromTable(TABLE_TASK, id);
        if (result == -1)
            Toast.makeText(context, "Failed to delete task", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Task deleted", Toast.LENGTH_SHORT).show();
    }

    public boolean changeTaskStatus(String id, int isDone) {
        if (!statusChanged(TABLE_TASK, id, isDone))
            return false;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("Status", isDone);
        long result = db.update(TABLE_TASK, cv, "Id=?", new String[]{id});
        db.close();

        return result != -1;

    }

    public void addHabit(String name, String time, String details, int status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("Name", name);
        cv.put("Time", time);
        cv.put("Details", details);
        cv.put("Status", status);

        long result = db.insert(TABLE_HABIT, null, cv);
        if (result == -1)
            Toast.makeText(context, "Failed to add new habit", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Habit added", Toast.LENGTH_SHORT).show();
        db.close();
    }

    public void deleteHabit(String id){
        long result = deleteFromTable(TABLE_HABIT, id);
        if (result == -1)
            Toast.makeText(context, "Failed to delete habit", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Habit deleted", Toast.LENGTH_SHORT).show();
    }

    public boolean changeHabitStatus(String id, int isDone) {
        if (!statusChanged(TABLE_HABIT, id, isDone))
            return false;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("Status", isDone);
        long result = db.update(TABLE_HABIT, cv, "Id=?", new String[]{id});
        db.close();

        return result != -1;
    }

    public boolean resetAllHabitsStatus(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("Status", 0);
        long result = db.update(TABLE_HABIT, cv, null, null);
        db.close();

        return result != -1;
    }

    private boolean statusChanged(String table, String id, int idDone){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + table + " WHERE id = " + id + " AND Status = " + idDone;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return true;
        }
        cursor.close();

        return false;
    }

    public long numOfTasks(int status){
        return numOfRowsInTable(TABLE_TASK, status);
    }

    public long numOfHabits(int status){
        return numOfRowsInTable(TABLE_HABIT, status);
    }

    private long numOfRowsInTable(String tableName, int status){
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, tableName, "Status=?", new String[]{String.valueOf(status)});
        db.close();

        return count;
    }

    private long deleteFromTable(String tableName, String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(tableName, "Id=?", new String[]{id});
    }

    public Cursor readAllTasks(){
        String query = "SELECT * FROM " + TABLE_TASK + " ORDER BY Status, DueDate";
        return readAllFromTable(query);
    }

    public Cursor readAllHabits(){
        String query = "SELECT * FROM " + TABLE_HABIT + " ORDER BY Status, Time";
        return readAllFromTable(query);
    }

    @SuppressLint("Recycle")
    private Cursor readAllFromTable(String query){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null)
            cursor = db.rawQuery(query, null);

        return cursor;
    }
}
