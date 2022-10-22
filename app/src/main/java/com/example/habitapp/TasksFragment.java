package com.example.habitapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.ArrayList;

public class TasksFragment extends Fragment {

    private RecyclerView recyclerView;

    private DbHelper db;
    private ArrayList<String> idTask, name, dueDate, details, status;

    private CheckBox taskHideStatus;
    private boolean hideTasks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_tasks, container, false);

        recyclerView = rootView.findViewById(R.id.recycler_view_tasks);
        taskHideStatus = rootView.findViewById(R.id.hide_completed_tasks_checkbox);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());

        db = new DbHelper(this.getActivity());
        idTask = new ArrayList<>();
        name = new ArrayList<>();
        dueDate = new ArrayList<>();
        details = new ArrayList<>();
        status = new ArrayList<>();

        hideTasks = sharedPreferences.getBoolean("hideTasks", false);
        taskHideStatus.setChecked(hideTasks);
        taskHideStatus.setOnClickListener(l -> {
            hideTasks = taskHideStatus.isChecked();
            sharedPreferences.edit().putBoolean("hideTasks", hideTasks).apply();
        });

        displayTasks();

        View addTask = rootView.findViewById(R.id.add_task_button);
        addTask.setOnClickListener( l -> openAddTaskActivity());

        return rootView;
    }

    private void openAddTaskActivity(){
        Intent intent = new Intent(this.getActivity(), AddTaskActivity.class);
        startActivity(intent);
    }

    private void storeDataInArrays(){
        Cursor cursor = db.readAllTasks();
        if (cursor.getCount() != 0){
            while (cursor.moveToNext()){
                idTask.add(cursor.getString(0));
                name.add(cursor.getString(1));
                dueDate.add(cursor.getString(2));
                details.add(cursor.getString(3));
                status.add(cursor.getString(4));
            }
        }
    }

    private void displayTasks(){
        storeDataInArrays();
        TaskAdapter adapter = new TaskAdapter(this.getActivity(), idTask, name, dueDate, details, status);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
    }

}