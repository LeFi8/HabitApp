package com.example.habitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class AddHabitActivity extends AppCompatActivity {

    private EditText habitTitle;
    private EditText habitTime;
    private EditText habitDetails;
    private int hour, minute;
    private long timeInMilliseconds;

    private boolean checkIfTimeIsSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);

        habitTitle = findViewById(R.id.add_title);
        habitTime = findViewById(R.id.set_time);
        habitDetails = findViewById(R.id.add_details);

        checkIfTimeIsSet = false;

        habitTime.setOnClickListener(l -> setTimePicker());

        Button saveNewHabitButton = findViewById(R.id.save_button);
        saveNewHabitButton.setOnClickListener(l -> {
            if (habitTitle.getText().toString().isEmpty())
                Toast.makeText(this, "Habit needs a title", Toast.LENGTH_SHORT).show();
            else {
                DbHelper db = new DbHelper(AddHabitActivity.this);
                if (checkIfTimeIsSet)
                    db.addHabit(habitTitle.getText().toString(), String.valueOf(timeInMilliseconds), habitDetails.getText().toString(), 0);
                else
                    db.addHabit(habitTitle.getText().toString(), "", habitDetails.getText().toString(), 0);

                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("fragment", 2);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });
    }

    public void setTimePicker() {
        checkIfTimeIsSet = true;
        TimePickerDialog.OnTimeSetListener onTimeSetListener = (timePicker, selectedHour, selectedMinute) -> {
            hour = selectedHour;
            minute = selectedMinute;
            timeInMilliseconds = hour * 3600000L + minute * 60000L;
            habitTime.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour, minute, true);
        timePickerDialog.setTitle("Select time");
        timePickerDialog.show();
    }

}