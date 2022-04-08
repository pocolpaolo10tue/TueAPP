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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterRecyclerViewAccepted extends RecyclerView.Adapter
        <AdapterRecyclerViewAccepted.MyViewHolder> {

    Context context;
    ArrayList<Event> list;

    /**
     * @param context Context for recyclerView
     * @param list list with events to display
     */
    public AdapterRecyclerViewAccepted(Context context, ArrayList<Event> list) {
        this.context = context;
        this.list = list;
    }

    /**
     * @param parent Pass on parent
     * @param viewType pass on viewType
     * @return new Viewholder
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.invite_item_accepted,
                parent, false);
        return new MyViewHolder(v, list);
    }

    /**
     * @param holder holder to set text to
     * @param position position of event to get from list
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Event event = list.get(position);
        holder.eventTitle.setText(event.getEventName());
        holder.shortDescription.setText(event.getShortDescription());
        holder.longDescription.setText(event.getDescription());
    }

    /** returns the list size.
     * @return returns list size
     */
    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView eventTitle, shortDescription, longDescription;
        Button deleteButton;
        FirebaseDatabase database;
        FirebaseAuth mAuth;

        /**
         * @param itemView item to be passed
         */
        public MyViewHolder(@NonNull View itemView, ArrayList<Event> list) {
            super(itemView);
            eventTitle = itemView.findViewById(R.id.cardTitle);
            shortDescription = itemView.findViewById(R.id.cardShortDescr);
            longDescription = itemView.findViewById(R.id.cardLongDescr);
            deleteButton = itemView.findViewById(R.id.delete_event);
            mAuth = FirebaseAuth.getInstance();
            if (mAuth.getCurrentUser().getEmail().contains("@student.tue.nl")) {
                deleteButton.setVisibility(View.INVISIBLE);
            }
            database = FirebaseDatabase.getInstance(
                    "https://project2-bb61c-default-rtdb.europe-west1.firebasedatabase.app/");
            deleteButton.setOnClickListener(item -> {
                Event event = list.get(getAdapterPosition());
                DatabaseReference event_ref = database.getReference("Event")
                        .child("All").child(String.valueOf(event.getEventID()));
                event_ref.removeValue();
                list.remove(getAdapterPosition());
            });
        }
    }
    public interface OnEventClickListener {
        void onEventClick(int position);
        void onDenyClick(int position);
    }
}
