package com.example.habitapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class TasksFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_tasks, container, false);

        Button addTask = rootView.findViewById(R.id.add_task_button);
        addTask.setOnClickListener( l -> openAddTaskActivity());

        return rootView;
    }

    private void openAddTaskActivity(){
        Intent intent = new Intent(this.getActivity(), AddTaskActivity.class);
        startActivity(intent);
    }
}