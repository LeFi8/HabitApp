package com.example.habitapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder>{

    private Context context;
    private ArrayList<String> idTask, taskName, taskDueDate, taskDetails;

    public TaskAdapter(Context context,
                       ArrayList<String> idTask,
                       ArrayList<String> taskName,
                       ArrayList<String> taskDueDate,
                       ArrayList<String> taskDetails) {
        this.idTask = idTask;
        this.context = context;
        this.taskName = taskName;
        this.taskDueDate = taskDueDate;
        this.taskDetails = taskDetails;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.task_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.taskName_txt.setText(String.valueOf(taskName.get(position)));
        holder.taskDueDate_txt.setText(String.valueOf(taskDueDate.get(position)));
        holder.taskDetails_txt.setText(String.valueOf(taskDetails.get(position)));
    }

    @Override
    public int getItemCount() {
        return idTask.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView taskName_txt, taskDueDate_txt, taskDetails_txt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            taskName_txt = itemView.findViewById(R.id.task_title);
            taskDueDate_txt = itemView.findViewById(R.id.task_date);
            taskDetails_txt = itemView.findViewById(R.id.task_details);
        }
    }
}
