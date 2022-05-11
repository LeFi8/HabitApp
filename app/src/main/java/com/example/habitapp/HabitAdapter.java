package com.example.habitapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.ArrayList;

public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.MyViewHolder> {

    private final Context context;
    private ArrayList<String> idHabit, habitName, habitTime, habitDetails, habitStatus;

    public HabitAdapter(Context context,
                        ArrayList<String> idHabit,
                        ArrayList<String> habitName,
                        ArrayList<String> habitTime,
                        ArrayList<String> habitDetails,
                        ArrayList<String> habitStatus) {
        this.idHabit = idHabit;
        this.context = context;
        this.habitName = habitName;
        this.habitTime = convertToTime(habitTime);
        this.habitDetails = habitDetails;
        this.habitStatus = habitStatus;
    }

    private ArrayList<String> convertToTime(ArrayList<String> timeOfHabit) {
        ArrayList<String> tmp = new ArrayList<>();
        for (String time : timeOfHabit) {
            if (time.equals("")) {
                tmp.add(time);
                continue;
            }
            tmp.add(convertFromMilliseconds(time));
        }
        return tmp;
    }

    private String convertFromMilliseconds(String time){
        long timeInMilliSeconds = Long.parseLong(time);
        int minutes = (int) ((timeInMilliSeconds / (1000*60)) % 60);
        int hours = (int) ((timeInMilliSeconds / (1000*60*60)) % 24);

        String strHours;
        String strMinutes;

        if (hours < 10)
            strHours = 0 + String.valueOf(hours);
        else
            strHours = String.valueOf(hours);

        if (minutes < 10)
            strMinutes = 0 + String.valueOf(minutes);
        else
            strMinutes = String.valueOf(minutes);


        return strHours + ":" + strMinutes;
    }

    @NonNull
    @Override
    public HabitAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.habit_row, parent, false);

        return new HabitAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitAdapter.MyViewHolder holder, int position) {
        holder.habitId_txt.setText(String.valueOf(idHabit.get(position)));
        holder.habitName_txt.setText(String.valueOf(habitName.get(position)));
        holder.habitTime_txt.setText(String.valueOf(habitTime.get(position)));
        holder.habitDetails_txt.setText(String.valueOf(habitDetails.get(position)));
        holder.checkHabitStatus.setChecked(Integer.parseInt(habitStatus.get(position)) == 1);
    }

    @Override
    public int getItemCount() {
        return idHabit.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView habitId_txt, habitName_txt, habitTime_txt, habitDetails_txt;
        private TextView deleteButton, deleteTextConfirmation;
        private Button confirmDelete;
        private Button cancelDelete;
        private CheckBox checkHabitStatus;

        private AlertDialog.Builder dialogBuilder;
        private AlertDialog dialog;

        Activity activity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            activity = (Activity) itemView.getContext();

            habitId_txt = itemView.findViewById(R.id.id_habit);
            habitName_txt = itemView.findViewById(R.id.habit_title);
            habitTime_txt = itemView.findViewById(R.id.habit_time);
            habitDetails_txt = itemView.findViewById(R.id.habit_details);
            deleteButton = itemView.findViewById(R.id.delete_habit);
            checkHabitStatus = itemView.findViewById(R.id.habit_checkBox);

            final DbHelper db = new DbHelper(activity);

            checkHabitStatus.setOnCheckedChangeListener(((compoundButton, isChecked) -> {
                String idHabit = habitId_txt.getText().toString().trim();

                if (isChecked) {
                    habitName_txt.setPaintFlags(habitName_txt.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    if (db.changeHabitStatus(idHabit, 1))
                        Toast.makeText(activity, "Habit done!", Toast.LENGTH_SHORT).show();
                } else {
                    db.changeHabitStatus(idHabit, 0);
                    habitName_txt.setPaintFlags(habitName_txt.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                }
            }));

            deleteButton.setOnClickListener(l -> {
                dialogBuilder = new AlertDialog.Builder(activity);
                final View popupView = LayoutInflater.from(activity).inflate(R.layout.delete_habit_popup, null);

                deleteTextConfirmation = popupView.findViewById(R.id.deleteTextConfirmation);
                confirmDelete = popupView.findViewById(R.id.delete_habit_yes);
                cancelDelete = popupView.findViewById(R.id.delete_habit_cancel);

                StringBuilder deleteHeader = new StringBuilder();
                deleteHeader.append(deleteTextConfirmation.getText().toString().trim());
                deleteHeader.append("\n\n");
                deleteHeader.append(habitName_txt.getText().toString().trim());
                deleteTextConfirmation.setText(deleteHeader);

                dialogBuilder.setView(popupView);
                dialog = dialogBuilder.create();

                cancelDelete.setOnClickListener(x -> {
                    dialog.dismiss();
                });

                confirmDelete.setOnClickListener(x -> {
                    db.deleteHabit(habitId_txt.getText().toString().trim());

                    Intent intent = new Intent(activity, MainActivity.class);
                    intent.putExtra("fragment", 2);
                    activity.startActivity(intent);
                    activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                });

                dialog.show();
            });
        }
    }
}

