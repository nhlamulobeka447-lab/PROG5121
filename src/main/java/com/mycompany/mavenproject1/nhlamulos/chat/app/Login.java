/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1.nhlamulos.chat.app;


// This class stores the user's details and checks that they are valid.
public class Login {

    // These are the "variables" that store the user's details.
    // "private" means only this class can use them directly.
    private final String firstName;
    private final String lastName;
    private final String username;
    private final String password;
    private final String cellNumber;

    // This is the constructor. It runs when we write: new Login(...)
    // It takes the details from Main and saves them in the variables above.
    public Login(String firstName, String lastName, String username,
                 String password, String cellNumber) {
        this.firstName = firstName;   // "this." means the variable of this class
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.cellNumber = cellNumber;
    }

    // Checks the username: must contain an underscore and be 5 characters or fewer.
    // Returns true if the username is OK, false if not.
    public boolean checkUserName() {
        return username.contains("_") && username.length() <= 5;
    }

    // Checks the password: at least 8 characters, a capital letter,
    // a number and a special character.
    public boolean checkPasswordComplexity() {

        // Start by assuming the password has none of these things.
        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        // Rule 1: the password must be at least 8 characters long.
        if (password.length() < 8) {
            return false;   // Too short, so stop here.
        }

        // Look at each character in the password, one at a time (a loop).
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);   // Get the character at position i

            if (Character.isUpperCase(c)) {
                hasCapital = true;          // Found a capital letter
            } else if (Character.isDigit(c)) {
                hasNumber = true;           // Found a number
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecial = true;          // Not a letter or number, so it is special
            }
        }

        // The password is only valid if all three were found.
        return hasCapital && hasNumber && hasSpecial;
    }

    // Checks the cell phone number using a regular expression (regex).
    // A regex is a pattern that text must match.
    // TODO: add your reference for the regex here, for example:
    // Author. (Year). Title. Website. URL [Accessed: date].
    public boolean checkCellPhoneNumber() {
        // \\+        = the number must start with a + sign
        // \\d{1,3}   = then 1 to 3 digits (the country code, like 27)
        // \\d{1,10}  = then 1 to 10 digits (the rest of the number)
        String pattern = "\\+\\d{1,3}\\d{1,10}";

        // matches() returns true if the whole number fits the pattern.
        return cellNumber.matches(pattern);
    }

    // Returns the message to show after the user tries to register.
    public String registerUser() {
        if (!checkUserName()) {
            // The ! means "not", so this runs when the username check FAILED.
            return "Username is not correctly formatted; please ensure that your "
                    + "username contains an underscore and is no more than five "
                    + "characters in length.";
        }

        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted; please ensure that the "
                    + "password contains at least eight characters, a capital "
                    + "letter, a number, and a special character.";
        }

        // Both checks passed.
        return "Username successfully captured.\nPassword successfully captured.";
    }

    // Returns the message for the cell phone number check.
    public String returnCellPhoneStatus() {
        if (checkCellPhoneNumber()) {
            return "Cell phone number successfully added.";
        } else {
            return "Cell phone number incorrectly formatted or does not contain "
                    + "international code.";
        }
    }

    // Returns true only if the username, password AND cell number are all valid.
    public boolean isRegistrationValid() {
        return checkUserName() && checkPasswordComplexity() && checkCellPhoneNumber();
    }

    // Checks if the username and password entered at login match the registered ones.
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        // .equals() compares two Strings. Never use == for Strings in Java.
        return username.equals(enteredUsername) && password.equals(enteredPassword);
    }

    // Returns the message to show after a login attempt.
    public String returnLoginStatus(boolean loginSuccessful) {
        if (loginSuccessful) {
            return "Welcome " + firstName + ", " + lastName
                    + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}