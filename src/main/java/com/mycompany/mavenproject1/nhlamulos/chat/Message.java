/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1.nhlamulos.chat.app;

// These imports give us the tools we need for files, lists and random numbers.
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

// This class stores ONE message and has the methods that check and send it.
public final class Message {

    // These variables store the details of one message.
    private final String messageID;      // random 10 digit number
    private final int messageNumber;     // the number of the message (starts at 0)
    private final String recipient;      // the cell number we are sending to
    private final String message;        // the text of the message
    private final String messageHash;    // the hash made from the ID, number and words

    // These lists are "static" so every message shares them.
    // sentMessages keeps all the messages that were sent.
    // storedMessages keeps all the messages saved to send later.
    private static final ArrayList<Message> sentMessages = new ArrayList<>();
    private static final ArrayList<Message> storedMessages = new ArrayList<>();

    // This is the constructor. It runs when we write: new Message(...)
    public Message(int messageNumber, String recipient, String message) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.message = message;
        this.messageID = makeMessageID();          // make a random ID
        this.messageHash = createMessageHash();    // make the hash (needs the ID)
    }

    // Makes a random ten digit number and returns it as text.
    private String makeMessageID() {
        Random random = new Random();
        String id = "";

        // Add one random digit (0 to 9) ten times.
        for (int i = 0; i < 10; i++) {
            id = id + random.nextInt(10);
        }
        return id;
    }

    // Checks that the message ID is not more than ten characters.
    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    // Returns true if the recipient number is OK, false if not.
    // It uses the same pattern as Part 1: a + sign, a country code
    // and then the rest of the number.
    public boolean recipientIsValid() {
        String pattern = "\\+\\d{1,3}\\d{1,10}";
        return recipient.matches(pattern);
    }

    // Returns the message to show for the recipient number.
    public String checkRecipientCell() {
        if (recipientIsValid()) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain "
                    + "an international code. Please correct the number and try again.";
        }
    }

    // Returns true if the message is 250 characters or less.
    public boolean messageLengthIsValid() {
        return message.length() <= 250;
    }

    // Returns the message to show for the message length.
    public String checkMessageLength() {
        if (messageLengthIsValid()) {
            return "Message ready to send.";
        } else {
            // Work out how many characters we are over the limit.
            int extra = message.length() - 250;
            return "Message exceeds 250 characters by " + extra
                    + ", please reduce the size.";
        }
    }

    // Makes the message hash.
    // Example: 00:0:HITONIGHT
    // (first two numbers of the ID : message number : first word + last word)
    public String createMessageHash() {
        // Get the first two characters of the message ID.
        String firstTwo = messageID.substring(0, 2);

        // Split the message into words. "\\s+" means one or more spaces.
        String[] words = message.trim().split("\\s+");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];

        // Remove anything that is not a letter or number (like , or ? or !).
        firstWord = firstWord.replaceAll("[^A-Za-z0-9]", "");
        lastWord = lastWord.replaceAll("[^A-Za-z0-9]", "");

        // Join everything together and make it all capital letters.
        String hash = firstTwo + ":" + messageNumber + ":" + firstWord + lastWord;
        return hash.toUpperCase();
    }

    // Lets the user choose what to do with the message.
    // 1 = send, 2 = disregard, 3 = store to send later.
    public String SentMessage(int choice) {
        if (choice == 1) {
            sentMessages.add(this);    // "this" means this message
            return "Message successfully sent.";
        } else if (choice == 2) {
            return "Press 0 to delete the message.";
        } else if (choice == 3) {
            storeMessage();
            return "Message successfully stored.";
        } else {
            return "Invalid choice, please pick 1, 2 or 3.";
        }
    }

    // Returns all the messages that were sent so far as one piece of text.
    public static String printMessages() {
        String allMessages = "";

        // Go through the list one message at a time.
        for (int i = 0; i < sentMessages.size(); i++) {
            Message m = sentMessages.get(i);
            allMessages = allMessages + "Message ID: " + m.messageID + "\n"
                    + "Message Hash: " + m.messageHash + "\n"
                    + "Recipient: " + m.recipient + "\n"
                    + "Message: " + m.message + "\n\n";
        }
        return allMessages;
    }

    // Returns how many messages have been sent so far.
    public static int returnTotalMessages() {
        return sentMessages.size();
    }

    // Saves the message into a JSON file so it can be sent later.
    // JSON is a way to write data as text, like: { "name": "value" }
    // I wrote the JSON by hand with Strings to keep it simple.
    public void storeMessage() {
        // Add this message to the list of stored messages.
        storedMessages.add(this);

        // Start the JSON text. A list in JSON starts with [ and ends with ].
        String json = "[\n";

        for (int i = 0; i < storedMessages.size(); i++) {
            Message m = storedMessages.get(i);
            json = json + "  {\n";
            json = json + "    \"messageID\": \"" + makeSafe(m.messageID) + "\",\n";
            json = json + "    \"messageHash\": \"" + makeSafe(m.messageHash) + "\",\n";
            json = json + "    \"recipient\": \"" + makeSafe(m.recipient) + "\",\n";
            json = json + "    \"message\": \"" + makeSafe(m.message) + "\"\n";
            json = json + "  }";

            // Put a comma after every message except the last one.
            if (i < storedMessages.size() - 1) {
                json = json + ",";
            }
            json = json + "\n";
        }
        json = json + "]";

        // Write the text into a file. If something goes wrong, show an error.
        try (FileWriter writer = new FileWriter("storedMessages.json")) {
            writer.write(json);
        } catch (IOException e) {
            System.out.println("Could not save the message to the file.");
        }
    }

    // A quote or backslash inside the message would break the JSON,
    // so we put a backslash in front of them.
    private String makeSafe(String text) {
        text = text.replace("\\", "\\\\");
        text = text.replace("\"", "\\\"");
        return text;
    }

    // Empties both lists. I use this in the tests so each test starts fresh.
    public static void clearAllMessages() {
        sentMessages.clear();
        storedMessages.clear();
    }

    // Getters let other classes read the private variables.
    public String getMessageID() {
        return messageID;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }
}

