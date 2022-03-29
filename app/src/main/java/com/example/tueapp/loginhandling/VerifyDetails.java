package com.example.tueapp.loginhandling;

import java.util.Locale;

public class VerifyDetails {

    public boolean isValidEmail(String email) {
        Boolean result;

        Boolean faculty_email = email.contains("@tue.nl");
        Boolean student_email = email.contains("@student.tue.nl");
        Boolean valid_length = false;
        if (faculty_email) {
            valid_length = email.length() >= 8;
        } else if (student_email) {
            valid_length = email.length() >= 16;
        }

        return (faculty_email || student_email) && valid_length;
    }

    public boolean isValidName(String name) {
        name = name.toLowerCase();

        Boolean spaces = name.contains(" ");
        Boolean min_length = 3 <= name.length();
        Boolean max_length = 15 >= name.length();
        Boolean only_letters = name.matches("[a-zA-Z]+");

        return !spaces && min_length && max_length && only_letters;
    }

    public boolean isValidPassword(String password) {
        Boolean correct_length = password.length() >= 6;
        Boolean contains_number = password.matches(".*\\d+.*");

        return contains_number && correct_length;
    }

    public String whyEmailNotValid(String email) throws IllegalArgumentException {
        if (isValidEmail(email)) {
            throw new IllegalArgumentException("This is a valid email.");
        }
        Boolean faculty_email = email.contains("@tue.nl");
        Boolean student_email = email.contains("@student.tue.nl");

        if (!(faculty_email || student_email)) {
            return "Please use a tu/e email.";
        }
        return "Please enter a valid email.";
    }

    public String whyPassNotValid(String password) throws IllegalArgumentException {
        if (isValidPassword(password)) {
            throw new IllegalArgumentException("This is a valid password.");
        }
        Boolean correct_length = password.length() >= 6;
        Boolean contains_number = password.matches(".*\\d+.*");

        if (!correct_length) {
            return "Password must have at least 6 characters.";
        }
        return "Password must contain atleast one number.";
    }

    public String whyNameNotValid(String name) throws IllegalArgumentException {
        if (isValidName(name)) {
            throw new IllegalArgumentException("This is a valid name.");
        }
        Boolean spaces = name.contains(" ");
        Boolean min_length = 4 <= name.length();
        Boolean max_length = 15 >= name.length();
        Boolean only_letters = name.matches("[a-zA-Z]+");

        if (spaces) {
            return "Name cannot contain spaces.";
        } else if (!min_length) {
            return "Name is too short.";
        } else if (!max_length) {
            return "Name is too long.";
        }
        return "Name can only contain letters.";
    }
}
