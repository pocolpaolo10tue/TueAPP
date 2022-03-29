package com.example.tueapp.loginhandling;

import static org.junit.Assert.*;

import org.junit.Test;

public class VerifyDetailsTest {
    VerifyDetails verifier = new VerifyDetails();

    @Test
    public void isValidEmail() {
        String test_email1 = "test@tue.nl";
        assertEquals(true, verifier.isValidEmail(test_email1));
    }

    @Test
    public void isValidName() {
    }

    @Test
    public void isValidPassword() {
    }
}