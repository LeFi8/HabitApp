package com.example.habitapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HabitsFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_habits, container, false);

        Button addHabit = rootView.findViewById(R.id.add_habit_button);
        addHabit.setOnClickListener(l -> openAddHabitActivity());

        return rootView;
    }

    private void openAddHabitActivity(){
        Intent intent = new Intent(this.getActivity(), AddHabitActivity.class);
        startActivity(intent);
    }
}