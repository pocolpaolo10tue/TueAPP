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
    public void nonValidNameTest1() {
        String name1 = "om ar";
        assertEquals(false, verifier.isValidName(name1));
    }

    @Test
    public void nonValidNameTest2() {
        String name2 = "oma";
        assertEquals(false, verifier.isValidName(name2));
    }

    @Test
    public void nonValidNameTest3() {
        String name3 = "#omar";
        assertEquals(false, verifier.isValidName(name3));
    }

    @Test
    public void nonValidNameTest4() {
        String name4 = "omaromaromaromaromaromar";
        assertEquals(false, verifier.isValidName(name4));
    }

    @Test
    public void validPasswordTest1() {
        String password1 = "admin1";
        assertEquals(true, verifier.isValidPassword(password1));
    }

    @Test
    public void validPasswordTest2() {
        String password2 = "123456a";
        assertEquals(true, verifier.isValidPassword(password2));
    }

    @Test
    public void validPasswordTest3() {
        String password3 = "#12345";
        assertEquals(true, verifier.isValidPassword(password3));
    }

    @Test
    public void validPasswordTest4() {
        String password4 = "ADMIN1";
        assertEquals(true, verifier.isValidPassword(password4));
    }

    @Test
    public void nonValidPasswordTest1() {
        String password1 = "adminadmin";
        assertEquals(false, verifier.isValidPassword(password1));
    }

    @Test
    public void nonValidPasswordTest2() {
        String password2 = "admin";
        assertEquals(false, verifier.isValidPassword(password2));
    }

    @Test
    public void nonValidPasswordTest3() {
        String password3 = "admi1";
        assertEquals(false, verifier.isValidPassword(password3));
    }

    @Test
    public void nonValidPasswordTest4() {
        String password4 = "";
        assertEquals(false, verifier.isValidPassword(password4));
    }

    @Test
    public void whyValidEmailTest1() {
        String test_email1 = "test@gmail.com";
        assertEquals("Please use a tu/e email.", verifier.whyEmailNotValid(test_email1));
    }

    @Test
    public void whyValidEmailTest2() {
        String test_email2 = "hello";
        assertEquals("Please use a tu/e email.", verifier.whyEmailNotValid(test_email2));
    }

    @Test
    public void whyValidEmailTest3() {
        String test_email3 = "@tue.nl";
        assertEquals("Please enter a valid email.", verifier.whyEmailNotValid(test_email3));
    }

    @Test
    public void whyValidEmailTest4() {
        String test_email4 = "@student.tue.nl";
        assertEquals("Please enter a valid email.", verifier.whyEmailNotValid(test_email4));
    }

    @Test
    public void whyValidNameTest1() {
        String name1 = "om ar";
        assertEquals("Name cannot contain spaces.", verifier.whyNameNotValid(name1));
    }

    @Test
    public void whyValidNameTest2() {
        String name2 = "om";
        assertEquals("Name is too short.", verifier.whyNameNotValid(name2));
    }

    @Test
    public void whyValidNameTest3() {
        String name3 = "#omar";
        assertEquals("Name can only contain letters.", verifier.whyNameNotValid(name3));
    }

    @Test
    public void whyValidNameTest4() {
        String name4 = "omaromaromaromaromaromar";
        assertEquals("Name is too long.", verifier.whyNameNotValid(name4));
    }

    @Test
    public void whyValidPasswordTest1() {
        String password1 = "adminadmin";
        assertEquals("Password must contain atleast one number.", verifier.whyPassNotValid(password1));
    }

    @Test
    public void whyValidPasswordTest2() {
        String password2 = "admin";
        assertEquals("Password must have at least 6 characters.", verifier.whyPassNotValid(password2));
    }

    @Test
    public void whyValidPasswordTest3() {
        String password3 = "admi1";
        assertEquals("Password must have at least 6 characters.", verifier.whyPassNotValid(password3));
    }

    @Test
    public void whyValidPasswordTest4() {
        String password4 = "";
        assertEquals("Password must have at least 6 characters.", verifier.whyPassNotValid(password4));
    }
}