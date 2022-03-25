package com.example.tueapp.loginhandling;


public class User {

    private String first_name;
    private String last_name;
    private String email;
    private Boolean admin;

    public User(String first_name, String last_name, String email, String password) {

        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email.trim();
        this.admin = email.contains("@tue.nl");;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getAdmin() {
        return admin;
    }
}
