package com.example.tueapp;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
    TextView timefield2;
    TextView eventName;
    TextView sDesc;
    TextView lDesc;
    DatePickerDialog.OnDateSetListener setListener;
    Button submit;


    /**
     * Required empty public constructor
     */
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

    /**
     * @param inflater inflater
     * @param container container which to inflate in
     * @param savedInstanceState instance that is already present
     * @return view with onclicklistener to open calendar
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_event, container, false);
        //can these 2 submit lines be removed? Already declared and other listener attached
        Button submit = view.findViewById(R.id.Submit);
        submit.setOnClickListener(view1 -> {
            EditText eventNameText = view1.findViewById(R.id.textField);
            String eventName = eventNameText.getText().toString().trim();
        });

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
            /** On click show date selector.
             * @param view view
             */
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
            /** fill in the selected date in the text field.
             * @param view view
             * @param year year to be set
             * @param month month to be set
             * @param dayofmonth day of month to be set
             */
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayofmonth) {
                //increase month by 1 since january is set as 0
                month = month + 1;
                //make date string
                String date = day+"/"+month+"/"+year;
                //set date string
                timefield2.setText(date);
            }
        };

        //when submit is pressed add the event to the database
        submit.setOnClickListener(item -> addToDatabase());



        return view;
    }

    /**
     * add item (event) to database.
     */
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

    /**
     *
     * @param view view
     * @param year year
     * @param month month
     * @param day day
     */
    public void onDateSet(@NonNull DatePicker view, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        String currentDate = DateFormat.getDateInstance().format(c.getTime());

        EditText datepick = view.findViewById(R.id.timeField2);
        datepick.setText(new StringBuilder().append(day).append("-").append(month).append("-").append(year).toString());
    }

}
