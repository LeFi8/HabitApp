package com.example.habitapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<String> idTask, taskName, taskDueDate, taskDetails, taskDone;

    public TaskAdapter(Context context,
                       ArrayList<String> idTask,
                       ArrayList<String> taskName,
                       ArrayList<String> taskDueDate,
                       ArrayList<String> taskDetails,
                       ArrayList<String> taskDone) {
        this.idTask = idTask;
        this.context = context;
        this.taskName = taskName;
        this.taskDueDate = convertToDate(taskDueDate);
        this.taskDetails = taskDetails;
        this.taskDone = taskDone;
    }

    private ArrayList<String> convertToDate(ArrayList<String> taskDueDate){
        ArrayList<String> tmp = new ArrayList<>();
        for (String date : taskDueDate) {
            if (date.equals("")){
                tmp.add(date);
                continue;
            }
            tmp.add(DateFormat.getDateInstance().format(Long.parseLong(date)));
        }
        return tmp;
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
        holder.checkTaskStatus.setChecked(Integer.parseInt(taskDone.get(position)) == 1);
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
        private CheckBox checkTaskStatus;

        private AlertDialog.Builder dialogBuilder;
        private AlertDialog dialog;

        Activity activity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            activity = (Activity) itemView.getContext();

            taskId_txt = itemView.findViewById(R.id.id_task);
            taskName_txt = itemView.findViewById(R.id.task_title);
            taskDueDate_txt = itemView.findViewById(R.id.task_date);
            taskDetails_txt = itemView.findViewById(R.id.task_details);
            deleteButton = itemView.findViewById(R.id.delete_task);
            checkTaskStatus = itemView.findViewById(R.id.task_checkBox);

            final DbHelper db = new DbHelper(activity);

            // TODO: database is updating everytime holder is setting checkbox status
            checkTaskStatus.setOnCheckedChangeListener(((compoundButton, isChecked) -> {
                String idTask = taskId_txt.getText().toString().trim();
                if (isChecked)
                    db.changeTaskStatus(idTask, 1);
                else
                    db.changeTaskStatus(idTask, 0);
            }));

            deleteButton.setOnClickListener(l -> {
                dialogBuilder = new AlertDialog.Builder(activity);
                final View popupView = LayoutInflater.from(activity).inflate(R.layout.delete_task_popup, null);

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
                    db.deleteTask(taskId_txt.getText().toString().trim());

                    Intent intent = new Intent(activity, MainActivity.class);
                    intent.putExtra("fragment", 1);
                    activity.startActivity(intent);
                    activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                });

                dialog.show();
            });
        }
    }
}
