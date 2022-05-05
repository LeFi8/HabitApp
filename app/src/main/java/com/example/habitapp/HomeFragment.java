package com.example.habitapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sharedPreferences = this.requireActivity().getSharedPreferences("usersName", Context.MODE_PRIVATE);

        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        createTopPanel(rootView);

        Button settingsButton = rootView.findViewById(R.id.settings_button);

        settingsButton.setOnClickListener(l -> {
            dialogBuilder = new AlertDialog.Builder(this.getContext());
            final View popupView = inflater.inflate(R.layout.change_name, null);

            dialogBuilder.setView(popupView);
            dialog = dialogBuilder.create();
            dialog.show();

            final EditText editName = popupView.findViewById(R.id.edit_name);
            final Button button = popupView.findViewById(R.id.enter_button_change_name);
            button.setOnClickListener(view -> {
                modifyName(editName, rootView);
                dialog.dismiss();
            });

            editName.setOnKeyListener((view, keyCode, keyEvent) -> {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    modifyName(editName, rootView);
                    dialog.dismiss();
                    return true;
                }
                return false;
            });

        });



        return rootView;
    }

    private void modifyName(EditText text, View view){
        String name = text.getText().toString().trim();
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor preferencesEditor= sharedPreferences.edit();
        preferencesEditor.putString("name", name);
        preferencesEditor.apply();
        createTopPanel(view);
    }

    private void createTopPanel(View rootView){
        String name = sharedPreferences.getString("name", "");
        TextView welcome_message = rootView.findViewById(R.id.welcome_message);
        String welcomeMessage;
        if (name.isEmpty())
            welcomeMessage = "Hi!";
        else welcomeMessage = "Hi, " + name + "!";

        TextView current_date = rootView.findViewById(R.id.current_date);
        String currentDate = "Today " + new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());

        welcome_message.setText(welcomeMessage);
        current_date.setText(currentDate);
    }
}