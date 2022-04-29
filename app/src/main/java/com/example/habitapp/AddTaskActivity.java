package com.example.habitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity {

    private EditText taskTitle;
    private EditText taskDueDate;
    private EditText taskDescription;
    private Calendar calendar;
    private int day, month, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        taskTitle = findViewById(R.id.add_title);
        taskDueDate = findViewById(R.id.set_due_date);
        taskDescription = findViewById(R.id.add_description);

        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        taskDueDate.setOnClickListener( l -> setDatePicker());

        Button saveNewTaskButton = findViewById(R.id.save_button);
        saveNewTaskButton.setOnClickListener( l -> {
            if (taskTitle.getText().toString().isEmpty())
                Toast.makeText(this, "Task needs a title", Toast.LENGTH_SHORT).show();
            else {
                // TODO: save title, date and description, count number of ongoing tasks
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
}