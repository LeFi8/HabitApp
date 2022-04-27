package com.example.habitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private final User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(MainActivity.this, FirstRun.class);
        String timePreferencesName = "isFirstTime";
        SharedPreferences sharedTime = getSharedPreferences(timePreferencesName, 0);

        if (sharedTime.getBoolean("firstTime", true))
            startActivity(intent);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("usersName", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "");
        user.setName(name);
        createTopPanel();
    }

    public void createTopPanel(){
        TextView welcome_message = findViewById(R.id.welcome_message);
        String welcomeMessage;
        if (user.getName().isEmpty())
            welcomeMessage = "Hi!";
        else welcomeMessage = "Hi, " + user.getName() + "!";

        TextView current_date = findViewById(R.id.current_date);
        String currentDate = "Today " + new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());

        welcome_message.setText(welcomeMessage);
        current_date.setText(currentDate);
    }

}