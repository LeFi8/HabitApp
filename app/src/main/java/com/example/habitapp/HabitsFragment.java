package com.example.habitapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

public class HabitsFragment extends Fragment{

    private RecyclerView recyclerView;

    private DbHelper db;
    private ArrayList<String> idHabit, name, time, details, status;
    private HabitAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_habits, container, false);

        recyclerView = rootView.findViewById(R.id.recycler_view_habits);

        db = new DbHelper(this.getActivity());
        idHabit = new ArrayList<>();
        name = new ArrayList<>();
        time = new ArrayList<>();
        details = new ArrayList<>();
        status = new ArrayList<>();

        displayHabits();

        View addHabit = rootView.findViewById(R.id.add_habit_button);
        addHabit.setOnClickListener(l -> openAddHabitActivity());

        return rootView;
    }

    private void openAddHabitActivity(){
        Intent intent = new Intent(this.getActivity(), AddHabitActivity.class);
        startActivity(intent);
    }

    private void storeDataInArrays(){
        Cursor cursor = db.readAllHabits();
        if (cursor.getCount() != 0){
            while (cursor.moveToNext()){
                idHabit.add(cursor.getString(0));
                name.add(cursor.getString(1));
                time.add(cursor.getString(2));
                details.add(cursor.getString(3));
                status.add(cursor.getString(4));
            }
        }
    }

    private void displayHabits(){
        storeDataInArrays();
        adapter = new HabitAdapter(this.getActivity(), idHabit, name, time, details, status);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
    }
}