package com.example.tueapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import java.util.HashMap;
import java.util.HashSet;

public class EventFragment extends Fragment implements AdapterRecyclerview.OnEventClickListener {

    RecyclerView recyclerView;
    RecyclerView recyclerViewAccepted;
    DatabaseReference database;
    AdapterRecyclerview adapterRecyclerview;
    AdapterRecyclerViewAccepted adapterRecyclerviewAccepted;
    ArrayList<Event> list;
    ArrayList<Event> list_accepted;
    ArrayList<Event> list_denied;
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
                replace.addToBackStack(EventFragment.class.getName());
                replace.commit();
            }
        });

        //create list of events
        list = new ArrayList<>();
        list_accepted = new ArrayList<>();
        list_denied = new ArrayList<>();
        //create view and adapter
        adapterRecyclerview = new AdapterRecyclerview(getActivity()
                ,list, this, this);
        adapterRecyclerviewAccepted = new AdapterRecyclerViewAccepted(getActivity(),list_accepted);
        recyclerView.setAdapter(adapterRecyclerview);
        recyclerViewAccepted.setAdapter(adapterRecyclerviewAccepted);

        //add listener if database changes
        database.child("All").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Event event = dataSnapshot.getValue(Event.class);
                    String email = mAuth.getCurrentUser().getEmail();
                    if (event.getAccepted().contains(email) && !containsID(list_accepted,event)) {
                        list_accepted.add(event);
                    } else if (!event.getDenied().contains(email) && !containsID(list,event) && !containsID(list_accepted,event) && event.getEmail().contains(email)) {
                        list.add(event);
                    }
                }
                adapterRecyclerview.notifyDataSetChanged();
                adapterRecyclerviewAccepted.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                adapterRecyclerview.notifyDataSetChanged();
                adapterRecyclerviewAccepted.notifyDataSetChanged();
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
        Toast.makeText(getActivity(), "accepted event",Toast.LENGTH_LONG).show();
    }
    @Override
    public void onDenyClick(int position) {
        Event event;
        event = list.get(position);
        event.removeEmail(mAuth.getCurrentUser().getEmail());
        list.set(position, event);
        Toast.makeText(getActivity(), "denied event",Toast.LENGTH_LONG).show();
    }

    private boolean containsID(ArrayList<Event> list, Event event) {
        for (Event event2 : list) {
            if (event.getEventID().equals(event2.getEventID())) {
                return true;
            }
        }
        return false;
    }
}