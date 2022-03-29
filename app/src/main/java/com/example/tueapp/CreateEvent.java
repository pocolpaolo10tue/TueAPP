package com.example.tueapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateEvent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateEvent extends Fragment {

    FirebaseDatabase database;
    DatabaseReference databaseRef;
    private TextView timefield2;
    private TextInputLayout timefield1;
    private DialogFragment datePicker;


    public CreateEvent() {
        // Required empty public constructor
    }

    public static CreateEvent newInstance() {
        return new CreateEvent();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_event, container, false);
        Button submit = view.findViewById(R.id.Submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText eventNameText = view.findViewById(R.id.textField);
                String eventName = eventNameText.getText().toString().trim();
            }
        });

        timefield2 = view.findViewById(R.id.timeField2);

        timefield2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker = new DatePickerFragment();
                datePicker.show(getParentFragmentManager(), "date");
            }
        });

/*
        EditText datepick = view.findViewById(R.id.timeField);
        datepick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getParentFragmentManager(), "datePicker");
            }
        });
        */

        // Inflate the layout for this fragment
        return view;
    }

    public void addToDatabase() {
        database = FirebaseDatabase.getInstance();
    }
    /*
    public void onDateSet(@NonNull DatePicker view, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        String currentDate = DateFormat.getDateInstance().format(c.getTime());

        EditText datepick = view.findViewById(R.id.timeField2);
        datepick.setText(day + "-" + month + "-" + year);
    }
     */
}
