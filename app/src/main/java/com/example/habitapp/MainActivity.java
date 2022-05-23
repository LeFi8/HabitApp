package com.example.habitapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(MainActivity.this, FirstRun.class);
        String timePreferencesName = "isFirstTime";
        SharedPreferences sharedTime = getSharedPreferences(timePreferencesName, 0);

        if (sharedTime.getBoolean("firstTime", true))
            startActivity(intent);

        setUpService();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.homeFragment);

        if (getIntent().getIntExtra("fragment", 0) == 1) {
            setFragment(new TasksFragment());
            bottomNavigationView.setSelectedItemId(R.id.tasksFragment);
        } else if (getIntent().getIntExtra("fragment", 0) == 2) {
            setFragment(new HabitsFragment());
            bottomNavigationView.setSelectedItemId(R.id.habitsFragment);
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.homeFragment:
                    setFragment(new HomeFragment());
                    break;
                case R.id.tasksFragment:
                    setFragment(new TasksFragment());
                    break;
                case R.id.habitsFragment:
                    setFragment(new HabitsFragment());
                    break;
                case R.id.notesFragment:
                    setFragment(new NotesFragment());
            }
            return true;
        });
    }

    private void setFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, fragment).commit();
    }

    private void setUpService(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);

        @SuppressLint("UnspecifiedImmutableFlag")
        PendingIntent pendingIntent = PendingIntent.getService(this, 0,
                new Intent(this, HabitsResetService.class), PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);
    }

}