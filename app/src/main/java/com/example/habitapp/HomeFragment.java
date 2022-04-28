package com.example.habitapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SharedPreferences sharedPreferences;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sharedPreferences = this.requireActivity().getSharedPreferences("usersName", Context.MODE_PRIVATE);

        // Inflate the layout for this fragment
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