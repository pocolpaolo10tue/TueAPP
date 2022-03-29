package com.example.tueapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AdapterRecyclerview extends RecyclerView.Adapter<AdapterRecyclerview.MyViewHolder> {

    Context context;
    ArrayList<Event> list;
    Button accept_event;

    public AdapterRecyclerview(Context context, ArrayList<Event> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.invite_item,
                parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Event event = list.get(position);
        holder.eventTitle.setText(event.getEventName());
        holder.shortDescription.setText(event.getShortDescription());
        holder.longDescription.setText(event.getDescription());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView eventTitle, shortDescription, longDescription;
        Button accept_invite;
        FirebaseDatabase database;
        FirebaseAuth mAuth;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            eventTitle = itemView.findViewById(R.id.cardTitle);
            shortDescription = itemView.findViewById(R.id.cardShortDescr);
            longDescription = itemView.findViewById(R.id.cardLongDescr);
            accept_invite = itemView.findViewById(R.id.accept_event);
            database = FirebaseDatabase.getInstance(
                    "https://project2-bb61c-default-rtdb.europe-west1.firebasedatabase.app/");
            mAuth = FirebaseAuth.getInstance();

        }
    }
}
