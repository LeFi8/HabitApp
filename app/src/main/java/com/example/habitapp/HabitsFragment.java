package com.example.habitapp;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HabitsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HabitsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    public HabitsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HabitsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HabitsFragment newInstance(String param1, String param2) {
        HabitsFragment fragment = new HabitsFragment();
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
        final View rootView = inflater.inflate(R.layout.fragment_habits, container, false);

        Button addHabit = rootView.findViewById(R.id.add_habit_button);

        addHabit.setOnClickListener(l -> {
            dialogBuilder = new AlertDialog.Builder(this.getContext());
            final View popupView = inflater.inflate(R.layout.add_habit, null);

            dialogBuilder.setView(popupView);
            dialog = dialogBuilder.create();
            dialog.show();

            final EditText habitTitle = popupView.findViewById(R.id.add_title);
            final EditText habitTime = popupView.findViewById(R.id.set_time);
            final EditText habitDescription = popupView.findViewById(R.id.add_description);
            final Button saveButton = popupView.findViewById(R.id.save_button);

            saveButton.setOnClickListener(view -> {
                dialog.dismiss();
            });

        });

        return rootView;
    }
}