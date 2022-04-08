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

public class AdapterRecyclerview extends RecyclerView.Adapter<AdapterRecyclerview.MyViewHolder> {

    Context context;
    ArrayList<Event> list;
    private OnEventClickListener mOnEventClickListener;
    private OnEventClickListener denyClickListener;

    /**
     * @param context Context for recyclerView
     * @param list list with events to display
     */
    public AdapterRecyclerview(Context context, ArrayList<Event> list, OnEventClickListener
            onEventClickListener, OnEventClickListener denyClickListener) {
        this.context = context;
        this.list = list;
        this.mOnEventClickListener = onEventClickListener;
        this.denyClickListener = denyClickListener;
    }

    /**
     * @param parent Pass on parent
     * @param viewType pass on viewType
     * @return new Viewholder
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.invite_item,
                parent, false);
        return new MyViewHolder(v, mOnEventClickListener, denyClickListener, list);
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
        Button accept_invite;
        FirebaseDatabase database;
        FirebaseAuth mAuth;
        Button acceptButton;
        Button denyButton;
        Button deleteButton;

        /**
         * @param itemView item to be passed
         */
        public MyViewHolder(@NonNull View itemView, OnEventClickListener onAcceptListener,
                            OnEventClickListener onDenyListener, ArrayList<Event> list) {
            super(itemView);

            eventTitle = itemView.findViewById(R.id.cardTitle);
            shortDescription = itemView.findViewById(R.id.cardShortDescr);
            longDescription = itemView.findViewById(R.id.cardLongDescr);
            accept_invite = itemView.findViewById(R.id.accept_event);
            acceptButton = itemView.findViewById(R.id.accept_event);
            denyButton = itemView.findViewById(R.id.deny_event);
            deleteButton = itemView.findViewById(R.id.delete_event);
            database = FirebaseDatabase.getInstance(
                    "https://project2-bb61c-default-rtdb.europe-west1.firebasedatabase.app/");
            mAuth = FirebaseAuth.getInstance();
            if (mAuth.getCurrentUser().getEmail().contains("@student.tue.nl")) {
                deleteButton.setVisibility(View.INVISIBLE);
            }
            acceptButton.setOnClickListener(item -> {
                Event event = list.get(getAdapterPosition());
                DatabaseReference event_ref = database.getReference("Event")
                        .child("All").child(String.valueOf(event.getEventID()));
                event.addAccepted(mAuth.getCurrentUser().getEmail());
                event_ref.setValue(event);
                list.remove(getAdapterPosition());
            });
            denyButton.setOnClickListener(item -> {
                Event event = list.get(getAdapterPosition());
                DatabaseReference event_ref = database.getReference("Event")
                        .child("All").child(String.valueOf(event.getEventID()));
                event.addDenied(mAuth.getCurrentUser().getEmail());
                event_ref.setValue(event);
                list.remove(getAdapterPosition());
            });
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
