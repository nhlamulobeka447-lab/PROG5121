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
            // REGISTRATION 
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
            }   //  LOGIN 
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
             // ===== QUICKCHAT (Part 2) =====
            // We only get here when the user has logged in successfully.
            runQuickChat(scanner);
        }
    }
 
    // Shows the welcome message and the menu. It repeats until the user quits.
    private static void runQuickChat(Scanner scanner) {
        System.out.println("\nWelcome to QuickChat.");
 
        boolean running = true;
 
        while (running) {
            System.out.println("\nPlease choose an option:");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();
 
            if (choice.equals("1")) {
                sendMessages(scanner);
            } else if (choice.equals("2")) {
                // This feature is still being built.
                System.out.println("Coming Soon.");
            } else if (choice.equals("3")) {
                System.out.println("Goodbye!");
                running = false;   // This stops the while loop
            } else {
                System.out.println("Invalid choice, please type 1, 2 or 3.");
            }
        }
    }
 
    // Asks how many messages the user wants and then captures each one.
    private static void sendMessages(Scanner scanner) {
 
        int howMany = askForNumber(scanner);
 
        // Repeat once for every message the user wants to enter.
        for (int i = 0; i < howMany; i++) {
            System.out.println("\n--- Message " + (i + 1) + " of " + howMany + " ---");
 
            Message newMessage;
            int messageCounter = 0;
 
            // Keep asking until the recipient and the message are both valid.
            while (true) {
                System.out.print("Enter the recipient's cell number (e.g. +27718669002): ");
                String recipient = scanner.nextLine();
 
                System.out.print("Enter your message (max 250 characters): ");
                String text = scanner.nextLine();
 
                newMessage = new Message(messageCounter, recipient, text);
 
                // Show the result of both checks.
                System.out.println(newMessage.checkRecipientCell());
                System.out.println(newMessage.checkMessageLength());
 
                if (newMessage.recipientIsValid() && newMessage.messageLengthIsValid()) {
                    break;   // Both are fine, so leave the loop
                }
                System.out.println("Please try again.\n");
            }
 
            // The message was accepted, so the next message gets the next number.
            messageCounter++;
 
            // Ask what the user wants to do with the message.
            int option = 0;
            while (option < 1 || option > 3) {
                System.out.println("\nWhat would you like to do with this message?");
                System.out.println("1) Send Message");
                System.out.println("2) Disregard Message");
                System.out.println("3) Store Message to send later");
                System.out.print("Enter your choice: ");
                option = readInt(scanner);
            }
 
            // Do what the user chose and show the result.
            System.out.println(newMessage.SentMessage(option));
 
            if (option == 1) {
                // Show the full details of the message that was sent.
                System.out.println("\nMessage ID: " + newMessage.getMessageID());
                System.out.println("Message Hash: " + newMessage.getMessageHash());
                System.out.println("Recipient: " + newMessage.getRecipient());
                System.out.println("Message: " + newMessage.getMessage());
            } else if (option == 2) {
                // Wait until the user presses 0 to delete the message.
                String key = "";
                while (!key.equals("0")) {
                    System.out.print("Enter 0: ");
                    key = scanner.nextLine();
                }
                System.out.println("Message deleted.");
            }
        }
 
        // Show the total when all the messages have been handled.
        System.out.println("\nTotal number of messages sent: " + Message.returnTotalMessages());
    }
 
    // Keeps asking until the user types a whole number bigger than 0.
    private static int askForNumber(Scanner scanner) {
        int number = 0;
 
        while (number < 1) {
            System.out.print("How many messages do you want to enter? ");
            number = readInt(scanner);
 
            if (number < 1) {
                System.out.println("Please enter a number of 1 or more.");
            }
        }
        return number;
    }
 
    // Reads a line and turns it into a number.
    // If the user types letters instead, we return 0 so the loops ask again.
    private static int readInt(Scanner scanner) {
        String input = scanner.nextLine();
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
 