package com.example.tueapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EventFragment extends Fragment implements AdapterRecyclerview.OnEventClickListener {

    RecyclerView recyclerView;
    RecyclerView recyclerViewAccepted;
    DatabaseReference database;
    AdapterRecyclerview adapterRecyclerview;
    AdapterRecyclerview adapterRecyclerviewAccepted;
    ArrayList<Event> list;
    ArrayList<Event> list_accepted;
    FirebaseAuth mAuth;

    public EventFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event, container, false);

        //create instance of firebase
        mAuth = FirebaseAuth.getInstance();

        //create instance of database
        database = FirebaseDatabase.getInstance(
                "https://project2-bb61c-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("Event");
        //link recyclerView with xml
        recyclerView = view.findViewById(R.id.invitedEventsView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        recyclerViewAccepted = view.findViewById(R.id.acceptedEventsView);
        recyclerViewAccepted.setHasFixedSize(true);
        recyclerViewAccepted.setLayoutManager(new LinearLayoutManager(view.getContext()));

        //depending on users admin show add event button
        FloatingActionButton fab = view.findViewById(R.id.floatingActionButton);

        if(mAuth.getCurrentUser() != null) {
            if (mAuth.getCurrentUser().getEmail().contains("@student.tue.nl")) {
                fab.setVisibility(View.INVISIBLE);
            }
        } else {
            fab.setVisibility(View.INVISIBLE);
        }


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction replace = getParentFragmentManager().beginTransaction();
                replace.replace(R.id.frame_layout, new CreateEvent());
                replace.addToBackStack(null);
                replace.commit();
            }
        });

        //create list of events
        list = new ArrayList<>();
        list_accepted = new ArrayList<>();
        //create view and adapter
        adapterRecyclerview = new AdapterRecyclerview(getActivity(),list, this);
        adapterRecyclerviewAccepted = new AdapterRecyclerview(getActivity(),list_accepted, this);
        recyclerView.setAdapter(adapterRecyclerview);
        recyclerViewAccepted.setAdapter(adapterRecyclerview);

        //add listener if database changes
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Event event = dataSnapshot.getValue(Event.class);
                    if (event.getEmail() != null) {
                        if (event.getEmail().contains(mAuth.getCurrentUser().getEmail()))
                            list.add(event);
                    }
                }
                adapterRecyclerview.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return view;
    }

    @Override
    public void onEventClick(int position) {
        Event event;
        event = list.get(position);
        event.removeEmail(mAuth.getCurrentUser().getEmail());
        event.addAccepted(mAuth.getCurrentUser().getEmail());
        list.set(position, event);
    }
}