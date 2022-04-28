package com.example.habitapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.homeFragment);

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
            }
            return true;
        });
    }

    private void setFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, fragment).commit();
    }

}