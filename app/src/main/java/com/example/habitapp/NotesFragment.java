package com.example.habitapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class NotesFragment extends Fragment {

    private EditText notes;
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_notes, container, false);
        notes = rootView.findViewById(R.id.notes);
        sharedPreferences = requireActivity().getSharedPreferences("notes", Context.MODE_PRIVATE);
        String note = sharedPreferences.getString("notes", "");

        notes.setText(note);

        notes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("notes", charSequence.toString());
                editor.apply();
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        return rootView;
    }
}