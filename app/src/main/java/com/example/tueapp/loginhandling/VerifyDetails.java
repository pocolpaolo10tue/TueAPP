package com.example.tueapp.loginhandling;

public class VerifyDetails {

    /**
     * validates email.
     * @param email the email to validate
     * @return boolean value if email is valid
     */
    public boolean isValidEmail(String email) {

        boolean faculty_email = email.contains("@tue.nl");
        boolean student_email = email.contains("@student.tue.nl");
        boolean valid_length = false;
        if (faculty_email) {
            valid_length = email.length() >= 8;
        } else if (student_email) {
            valid_length = email.length() >= 16;
        }

        return (faculty_email || student_email) && valid_length;
    }

    /**
     * validates name.
     * @param name the name to validate
     * @return boolean value if name is valid
     */
    public boolean isValidName(String name) {
        name = name.toLowerCase();

        boolean spaces = name.contains(" ");
        boolean min_length = 3 <= name.length();
        boolean max_length = 15 >= name.length();
        boolean only_letters = name.matches("[a-zA-Z]+");

        return !spaces && min_length && max_length && only_letters;
    }

    /**
     * validates password.
     * @param password the password to validate
     * @return boolean value if password is valid
     */
    public boolean isValidPassword(String password) {
        boolean correct_length = password.length() >= 6;
        boolean contains_number = password.matches(".*\\d+.*");

        return contains_number && correct_length;
    }

    /**
     * String why email is not valid.
     * @param email the email to give reasoning why not valid
     * @return String with information why email is not valid
     * @throws IllegalArgumentException if email is valid
     */
    public String whyEmailNotValid(String email) throws IllegalArgumentException {
        if (isValidEmail(email)) {
            throw new IllegalArgumentException("This is a valid email.");
        }
        boolean faculty_email = email.contains("@tue.nl");
        boolean student_email = email.contains("@student.tue.nl");

        if (!(faculty_email || student_email)) {
            return "Please use a tu/e email.";
        }
        return "Please enter a valid email.";
    }

    /**
     * String why password is not valid.
     * @param password the password to give reasoning why not valid
     * @return String with information why password is not valid
     * @throws IllegalArgumentException if password is valid
     */
    public String whyPassNotValid(String password) throws IllegalArgumentException {
        if (isValidPassword(password)) {
            throw new IllegalArgumentException("This is a valid password.");
        }
        boolean correct_length = password.length() >= 6;
        boolean contains_number = password.matches(".*\\d+.*");

        if (!correct_length) {
            return "Password must have at least 6 characters.";
        }
        if (contains_number) {
            return "Password must contain at least one number.";
        }
        else {
            return "password is not valid for unknown reason try another password";
        }

    }

    /**
     * String why name is not valid.
     * @param name the name to give reasoning why not valid
     * @return String with information why name is not valid
     * @throws IllegalArgumentException if name is not valid
     */
    public String whyNameNotValid(String name) throws IllegalArgumentException {
        if (isValidName(name)) {
            throw new IllegalArgumentException("This is a valid name.");
        }
        boolean spaces = name.contains(" ");
        boolean min_length = 4 <= name.length();
        boolean max_length = 15 >= name.length();
        boolean only_letters = name.matches("[a-zA-Z]+");

        if (spaces) {
            return "Name cannot contain spaces.";
        } else if (!min_length) {
            return "Name is too short.";
        } else if (!max_length) {
            return "Name is too long.";
        } else if (only_letters) {
            return "Name may only contain letters";
        }
        return "Name is not valid for unknown reason try another name.";
    }
}
