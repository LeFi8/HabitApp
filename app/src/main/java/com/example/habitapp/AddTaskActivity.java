package com.example.habitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity {

    private EditText taskTitle;
    private EditText taskDueDate;
    private EditText taskDetails;
    private Calendar calendar;
    private int day, month, year;

    DbHelper db;
    ArrayList<String> idTask, name, dueDate, details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        taskTitle = findViewById(R.id.add_title);
        taskDueDate = findViewById(R.id.set_due_date);
        taskDetails = findViewById(R.id.add_details);

        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        idTask = new ArrayList<>();
        name = new ArrayList<>();
        dueDate = new ArrayList<>();
        details = new ArrayList<>();

        taskDueDate.setOnClickListener( l -> setDatePicker());

        Button saveNewTaskButton = findViewById(R.id.save_button);
        saveNewTaskButton.setOnClickListener( l -> {
            if (taskTitle.getText().toString().isEmpty())
                Toast.makeText(this, "Task needs a title", Toast.LENGTH_SHORT).show();
            else {
                // TODO: save title, date and description, count number of ongoing tasks

                db = new DbHelper(AddTaskActivity.this);
                db.addTask(taskTitle.getText().toString(), taskDueDate.toString(), taskDetails.toString());
                storeDataInArrays();

                Toast.makeText(this, "Task added!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void setDatePicker(){
        DatePickerDialog.OnDateSetListener onDateSetListener = (datePicker, selectedYear, selectedMonth, selectedDay) -> {
            day = selectedDay;
            month = selectedMonth;
            year = selectedYear;
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);
            String date = DateFormat.getDateInstance().format(calendar.getTime());
            taskDueDate.setText(date);
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, onDateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        datePickerDialog.setTitle("Select date");
        datePickerDialog.show();
    }

    private void storeDataInArrays(){
        Cursor cursor = db.readAllTasks();
        if (cursor.getCount() != 0){
            while (cursor.moveToNext()){
                idTask.add(cursor.getString(0));
                name.add(cursor.getString(1));
                dueDate.add(cursor.getString(2));
                details.add(cursor.getString(3));
            }
        }
    }
}