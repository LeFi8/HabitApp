package com.example.habitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class AddHabitActivity extends AppCompatActivity {

    private EditText habitTitle;
    private EditText habitTime;
    private EditText habitDescription;
    private int hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);

        habitTitle = findViewById(R.id.add_title);
        habitTime = findViewById(R.id.set_time);
        habitDescription = findViewById(R.id.add_description);

        habitTime.setOnClickListener( l -> setTimePicker());

        Button saveNewHabitButton = findViewById(R.id.save_button);
        saveNewHabitButton.setOnClickListener( l -> {
            if (habitTitle.getText().toString().isEmpty())
                Toast.makeText(this, "Habit needs a title", Toast.LENGTH_SHORT).show();
            else {
                // TODO: save info, count number of habits
                Toast.makeText(this, "Habit added!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    public void setTimePicker() {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = (timePicker, selectedHour, selectedMinute) -> {
            hour = selectedHour;
            minute = selectedMinute;
            habitTime.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour, minute, true);
        timePickerDialog.setTitle("Select time");
        timePickerDialog.show();
    }
}