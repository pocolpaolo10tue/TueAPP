package com.example.tueapp;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
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

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CancellationException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateEvent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateEvent extends Fragment {

    FirebaseDatabase database;
    DatabaseReference databaseRef;
    TextView timefield2;
    TextInputLayout timefield1;
    TextView eventName;
    TextView sDesc;
    TextView lDesc;
    DatePickerDialog.OnDateSetListener setListener;
    Button submit;


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

        timefield1 = view.findViewById(R.id.timeField);
        timefield2 = view.findViewById(R.id.timeField2);
        submit = view.findViewById(R.id.Submit);
        eventName = view.findViewById(R.id.textField_text);
        sDesc = view.findViewById(R.id.shortDesc_text);
        lDesc = view.findViewById(R.id.Descr_text);

        timefield2.setShowSoftInputOnFocus(false);
        timefield2.setInputType(InputType.TYPE_NULL);
        timefield2.setFocusable(false);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);


        timefield2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        , setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new
                        ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayofmonth) {
                month = month + 1;
                String date = day+"/"+month+"/"+year;
                timefield2.setText(date);
            }
        };

        submit.setOnClickListener(item ->{
            addToDatabase();
        });



        return view;
    }

    public void addToDatabase() {
        database = FirebaseDatabase.getInstance(
                "https://project2-bb61c-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseRef = database.getReference("Event");
        String name = eventName.getText().toString().trim();
        String desc = lDesc.getText().toString().trim();
        String sdesc = sDesc.getText().toString().trim();
        Event event = new Event(name,"test", desc, sdesc, true);
        String id = databaseRef.push().getKey();
        databaseRef.child(id).setValue(event);

    }

    public void onDateSet(@NonNull DatePicker view, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        String currentDate = DateFormat.getDateInstance().format(c.getTime());

        EditText datepick = view.findViewById(R.id.timeField2);
        datepick.setText(day + "-" + month + "-" + year);
    }

}
