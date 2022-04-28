package com.example.habitapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class FirstRun extends AppCompatActivity {

    private EditText nameText;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_run);

        sharedPreferences = getSharedPreferences("usersName", MODE_PRIVATE);
        nameText = findViewById(R.id.username);
        Button button = findViewById(R.id.enter_button);
        button.setOnClickListener(view -> sendName());
        nameText.setOnKeyListener((view, keyCode, keyEvent) -> {
            if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                sendName();
                return true;
            }
            return false;
        });
    }

    public void sendName(){
        String name = nameText.getText().toString().trim();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name);
        editor.apply();

        SharedPreferences sharedTime = getSharedPreferences("isFirstTime", 0);
        sharedTime.edit().putBoolean("firstTime", false).apply();

        Intent intent = new Intent(FirstRun.this, MainActivity.class);
        startActivity(intent);
    }
}
