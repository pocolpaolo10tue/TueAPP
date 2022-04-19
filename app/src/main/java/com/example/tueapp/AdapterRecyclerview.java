package com.example.tueapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AdapterRecyclerview extends RecyclerView.Adapter<AdapterRecyclerview.MyViewHolder> {

    Context context;
    ArrayList<Event> list;
    Boolean accepted;
    private OnEventClickListener mOnEventClickListener;
    private OnEventClickListener denyClickListener;

    /**
     * @param context Context for recyclerView
     * @param list list with events to display
     */
    public AdapterRecyclerview(Context context, ArrayList<Event> list, OnEventClickListener
            onEventClickListener, OnEventClickListener denyClickListener, Boolean accepted) {
        this.accepted = accepted;
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
        View v;

        if (accepted) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.invite_item_accepted,
                    parent, false);
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.invite_item,
                    parent, false);
        }
        return new MyViewHolder(v, list, accepted);
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
        Event event;
        DatabaseReference event_ref;

        /**
         * @param itemView item to be passed
         */
        public MyViewHolder(@NonNull View itemView, ArrayList<Event> list, Boolean accepted) {
            super(itemView);

            eventTitle = itemView.findViewById(R.id.cardTitle);
            shortDescription = itemView.findViewById(R.id.cardShortDescr);
            longDescription = itemView.findViewById(R.id.cardLongDescr);
            accept_invite = itemView.findViewById(R.id.accept_event);
            deleteButton = itemView.findViewById(R.id.delete_event);
            database = FirebaseDatabase.getInstance(
                    "https://project2-bb61c-default-rtdb.europe-west1.firebasedatabase.app/");
            mAuth = FirebaseAuth.getInstance();

            if (mAuth.getCurrentUser().getEmail().contains("@student.tue.nl")) {
                deleteButton.setVisibility(View.INVISIBLE);
            }

            if (0 <= getAdapterPosition() && getAdapterPosition() < list.size()) {
                event = list.get(getAdapterPosition());
                event_ref = event_ref = database.getReference("Event")
                        .child("All").child(String.valueOf(event.getEventID()));
                acceptButton.setVisibility(View.INVISIBLE);
            }

            itemView.setOnClickListener(item -> {
                if (mAuth.getCurrentUser().getEmail().contains("@tue.nl")) {
                    Fragment newFragment = new CreateEvent(list.get(getAdapterPosition())) ;
                    AppCompatActivity activity = (AppCompatActivity) itemView.getContext();
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout,newFragment).addToBackStack(null).commit();
                }
            });

            deleteButton.setOnClickListener(item -> {
                addEvent(list);
                event_ref.removeValue();
                list.remove(getAdapterPosition());
            });

            if (!accepted) {
                acceptButton = itemView.findViewById(R.id.accept_event);
                denyButton = itemView.findViewById(R.id.deny_event);
                acceptButton.setOnClickListener(item -> {
                    addEvent(list);
                    event.addAccepted(mAuth.getCurrentUser().getEmail());
                    event_ref.setValue(event);
                    list.remove(getAdapterPosition());
                });
                denyButton.setOnClickListener(item -> {
                    addEvent(list);
                    event.addDenied(mAuth.getCurrentUser().getEmail());
                    event_ref.setValue(event);
                    list.remove(getAdapterPosition());
                });
            }
        }
        public void addEvent(ArrayList<Event> list) {
            event = list.get(getAdapterPosition());
            event_ref = event_ref = database.getReference("Event")
                    .child("All").child(String.valueOf(event.getEventID()));
        }
    }
    public interface OnEventClickListener {
        void onEventClick(int position);
        void onDenyClick(int position);
    }
}
