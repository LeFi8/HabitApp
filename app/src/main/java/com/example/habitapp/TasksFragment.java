package com.example.habitapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Objects;

public class TasksFragment extends Fragment {

    private RecyclerView recyclerView;

    private DbHelper db;
    private ArrayList<String> idTask, name, dueDate, details, done;
    private TaskAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_tasks, container, false);

        recyclerView = rootView.findViewById(R.id.recycler_view_tasks);

        db = new DbHelper(this.getActivity());
        idTask = new ArrayList<>();
        name = new ArrayList<>();
        dueDate = new ArrayList<>();
        details = new ArrayList<>();
        done = new ArrayList<>();

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
                done.add(cursor.getString(4));
            }
        }
    }

    private void displayTasks(){
        storeDataInArrays();
        adapter = new TaskAdapter(this.getActivity(), idTask, name, dueDate, details, done);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
    }

}