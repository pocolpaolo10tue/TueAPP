package com.example.tueapp.loginhandling;

import static org.junit.Assert.*;

import org.junit.Test;

public class VerifyDetailsTest {
    VerifyDetails verifier = new VerifyDetails();

    @Test
    public void validEmailTest1() {
        String test_email1 = "test@tue.nl";
        assertEquals(true, verifier.isValidEmail(test_email1));
    }

    @Test
    public void validEmailTests2() {
        String test_email2 = "test@student.tue.nl";
        assertEquals(true, verifier.isValidEmail(test_email2));
    }

    @Test
    public void validEmailTests3() {
        String test_email3 = "t@tue.nl";
        assertEquals(true, verifier.isValidEmail(test_email3));
    }

    @Test
    public void validEmailTest4() {
        String test_email4 = "t@student.tue.nl";
        assertEquals(true, verifier.isValidEmail(test_email4));
    }

    @Test
    public void nonValidEmailTest1() {
        String test_email1 = "test@gmail.com";
        assertEquals(false, verifier.isValidEmail(test_email1));
    }

    @Test
    public void nonValidEmailTest2() {
        String test_email2 = "hello";
        assertEquals(false, verifier.isValidEmail(test_email2));
    }

    @Test
    public void nonValidEmailTest3() {
        String test_email3 = "@tue.nl";
        assertEquals(false, verifier.isValidEmail(test_email3));
    }

    @Test
    public void nonValidEmailTest4() {
        String test_email4 = "@student.tue.nl";
        assertEquals(false, verifier.isValidEmail(test_email4));
    }

    @Test
    public void validNameTest1() {
        String name1 = "omar";
        assertEquals(true, verifier.isValidName(name1));
    }

    @Test
    public void validNameTest2() {
        String name2 = "Omar";
        assertEquals(true, verifier.isValidName(name2));
    }

    @Test
    public void validNameTest3() {
        String name3 = "OMAR";
        assertEquals(true, verifier.isValidName(name3));
    }

    @Test
    public void validNameTest4() {
        String name4 = "omaromar";
        assertEquals(true, verifier.isValidName(name4));
    }

    @Test
    public void nonValidNameTests() {
        String name1 = "om ar";
        String name2 = "oma";
        String name3 = "#omar";
        String name4 = "omaromaromaromaromaromar";
        assertEquals(false, verifier.isValidName(name1));
        assertEquals(false, verifier.isValidName(name2));
        assertEquals(false, verifier.isValidName(name3));
        assertEquals(false, verifier.isValidName(name4));
    }

    @Test
    public void validPasswordTests() {
        String password1 = "admin1";
        String password2 = "123456a";
        String password3 = "#12345";
        String password4 = "ADMIN1";
        assertEquals(true, verifier.isValidPassword(password1));
        assertEquals(true, verifier.isValidPassword(password2));
        assertEquals(true, verifier.isValidPassword(password3));
        assertEquals(true, verifier.isValidPassword(password4));
    }

    @Test
    public void nonValidPasswordTests() {
        String password1 = "adminadmin";
        String password2 = "admin";
        String password3 = "admi1";
        String password4 = "";
        assertEquals(false, verifier.isValidPassword(password1));
        assertEquals(false, verifier.isValidPassword(password2));
        assertEquals(false, verifier.isValidPassword(password3));
        assertEquals(false, verifier.isValidPassword(password4));
    }
}