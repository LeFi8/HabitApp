package com.example.habitapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        holder.taskId_txt.setText(String.valueOf(idTask.get(position)));
        holder.taskName_txt.setText(String.valueOf(taskName.get(position)));
        holder.taskDueDate_txt.setText(String.valueOf(taskDueDate.get(position)));
        holder.taskDetails_txt.setText(String.valueOf(taskDetails.get(position)));
    }

    @Override
    public int getItemCount() {
        return idTask.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView taskId_txt, taskName_txt, taskDueDate_txt, taskDetails_txt;
        private TextView deleteButton, deleteTextConfirmation;
        private Button confirmDelete;
        private Button cancelDelete;

        private AlertDialog.Builder dialogBuilder;
        private AlertDialog dialog;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            taskId_txt = itemView.findViewById(R.id.id_task);
            taskName_txt = itemView.findViewById(R.id.task_title);
            taskDueDate_txt = itemView.findViewById(R.id.task_date);
            taskDetails_txt = itemView.findViewById(R.id.task_details);
            deleteButton = itemView.findViewById(R.id.delete_task);

            deleteButton.setOnClickListener(l -> {
                dialogBuilder = new AlertDialog.Builder(itemView.getContext());
                final View popupView = LayoutInflater.from(itemView.getContext()).inflate(R.layout.delete_popup, null);

                deleteTextConfirmation = popupView.findViewById(R.id.deleteTextConfirmation);
                confirmDelete = popupView.findViewById(R.id.delete_task_yes);
                cancelDelete = popupView.findViewById(R.id.delete_task_cancel);

                StringBuilder deleteHeader = new StringBuilder();
                deleteHeader.append(deleteTextConfirmation.getText().toString().trim());
                deleteHeader.append("\n\n");
                deleteHeader.append(taskName_txt.getText().toString().trim());
                deleteTextConfirmation.setText(deleteHeader);

                dialogBuilder.setView(popupView);
                dialog = dialogBuilder.create();

                cancelDelete.setOnClickListener(x -> {
                    dialog.dismiss();
                });

                confirmDelete.setOnClickListener(x -> {
                    DbHelper db = new DbHelper(itemView.getContext());
                    db.deleteTask(taskId_txt.getText().toString().trim());

                    Intent intent = new Intent(itemView.getContext(), MainActivity.class);
                    intent.putExtra("fragment", 1);
                    itemView.getContext().startActivity(intent);
                });

                dialog.show();
            });
        }
    }
}
