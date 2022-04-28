package com.example.habitapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        SharedPreferences sharedPreferences = this.requireActivity().getSharedPreferences("usersName", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "");
        createTopPanel(rootView, name);

        Button settingsButton = rootView.findViewById(R.id.settings_button);
        settingsButton.setOnClickListener( l -> {
            //TODO: Popup window to change name
        });
        return rootView;
    }

    private void createTopPanel(View rootView, String name){
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