/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mavenproject1.nhlamulos.chat.app;


// Scanner lets us read what the user types in the console.
import java.util.Scanner;

// This is the main class. The program starts running in the main method below.
public class Mavenproject1NhlamulosChatApp {

    public static void main(String[] args) {

        // This will hold the user's details once they register.
        try ( // Create a Scanner to read the user's input from the keyboard.
                Scanner scanner = new Scanner(System.in)) {
            // This will hold the user's details once they register.
            Login login;
            // ---------- REGISTRATION ----------
            System.out.println("=== REGISTRATION ===");
            // Keep asking until the user enters valid details.
            // "while (true)" repeats forever until we use "break".
            while (true) {
                
                // Ask for each detail and save what the user types.
                System.out.print("Enter your first name: ");
                String firstName = scanner.nextLine();
                
                System.out.print("Enter your last name: ");
                String lastName = scanner.nextLine();
                
                System.out.print("Enter a username: ");
                String username = scanner.nextLine();
                
                System.out.print("Enter a password: ");
                String password = scanner.nextLine();
                
                System.out.print("Enter your cell phone number (e.g. +27831234567): ");
                String cell = scanner.nextLine();
                
                // Create a Login object with the details the user typed.
                login = new Login(firstName, lastName, username, password, cell);
                
                // Show the messages for the username/password and the cell number.
                System.out.println(login.registerUser());
                System.out.println(login.returnCellPhoneStatus());
                
                // If everything is valid, leave the loop.
                if (login.isRegistrationValid()) {
                    System.out.println("User has been registered successfully.\n");
                    break;
                }
                
                // Otherwise the loop goes around again.
                System.out.println("\nPlease try registering again.\n");
            }   // ---------- LOGIN ----------
            System.out.println("=== LOGIN ===");
            boolean loggedIn = false;   // Starts as false because they haven't logged in yet
            // Keep asking until the login is correct.
            while (!loggedIn) {
                System.out.print("Enter your username: ");
                String enteredUser = scanner.nextLine();
                
                System.out.print("Enter your password: ");
                String enteredPass = scanner.nextLine();
                
                // Check the details and save the result (true or false).
                loggedIn = login.loginUser(enteredUser, enteredPass);
                
                // Show the welcome message or the error message.
                System.out.println(login.returnLoginStatus(loggedIn));
            }
            // Close the Scanner when we are done.
        }
    }
}