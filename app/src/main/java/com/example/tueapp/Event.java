package com.example.tueapp;

import java.time.LocalDateTime;

public class Event {
    //string to store event name
    private String eventName;
    //string to store location
    private String location;
    //time object to store the time of the event
    private LocalDateTime time;
    //string to store the "long" description of the event
    private String description;
    //string to store the short description of the event
    private String shortDescription;
    //Hashset to store the invited email addresses
    private String email;
    //string to store the accepted invites
    private String accepted;
    //boolean to send notification
    private boolean notify;

    public void Event(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Event(String eventName, String location, String description,
                 String shortDescription, String email, boolean notify) {
        this.eventName = eventName;
        this.location = location;
//        this.time = time;
        this.description = description;
        this.shortDescription = shortDescription;
        this.email = email;
        this.accepted = accepted;
        this.notify = notify;
    }

    public Event() {}

    //getter and setter methods
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void addEmail(String email) {
        this.email = this.email + ", " + email;
    }

    public void removeEmail(String email) {
        this.email = this.email.replace(email + ", ", "");
    }

    public String getEmail() {
        return this.email;
    }

    public String getAccepted() {
        return this.accepted;
    }

    public void addAccepted(String email) {
        this.email = this.email + ", " + email;
    }

    public void removeAccepted(String email) {
        this.email = this.email.replace(email + ", ", "");
    }
}
