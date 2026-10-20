package com.mycompany.mavenproject1.nhlamulos.chat.app;

// These imports give us the testing tools from JUnit.
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Tests for the Message class, using the test data from the assignment.
public class MessageTest {

    // @BeforeEach runs before every test. It empties the lists
    // so one test cannot mess up the next test.
    @BeforeEach
    public void setUp() {
        Message.clearAllMessages();
    }

    // ===== TEST DATA 1 (message 0) =====
    // Recipient: +27718669002
    // Message: Hi Mike, can you join us for dinner tonight?

    // ===== TEST DATA 2 (message 1) =====
    // Recipient: 08575975889
    // Message: Hi Keegan, did you receive the payment?

    // ===== MESSAGE LENGTH TESTS =====

    @Test
    public void testMessageLengthSuccess() {
        Message msg = new Message(0, "+27718669002",
                "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Message ready to send.", msg.checkMessageLength());
    }

    @Test
    public void testMessageLengthFailure() {
        // "a".repeat(260) makes a message that is 260 characters long.
        String longText = "a".repeat(260);
        Message msg = new Message(0, "+27718669002", longText);
        // 260 - 250 = 10 characters too many.
        assertEquals("Message exceeds 250 characters by 10, please reduce the size.",
                msg.checkMessageLength());
    }

    // ===== RECIPIENT NUMBER TESTS =====

    @Test
    public void testRecipientNumberSuccess() {
        Message msg = new Message(0, "+27718669002",
                "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Cell phone number successfully captured.",
                msg.checkRecipientCell());
    }

    @Test
    public void testRecipientNumberFailure() {
        Message msg = new Message(1, "08575975889",
                "Hi Keegan, did you receive the payment?");
        assertEquals("Cell phone number is incorrectly formatted or does not contain "
                + "an international code. Please correct the number and try again.",
                msg.checkRecipientCell());
    }

    // ===== MESSAGE HASH TESTS =====

    @Test
    public void testMessageHashTestData1() {
        Message msg = new Message(0, "+27718669002",
                "Hi Mike, can you join us for dinner tonight?");

        // The ID is random, so we build the expected hash using the first
        // two numbers of the real ID. The rest must be :0:HITONIGHT
        String firstTwo = msg.getMessageID().substring(0, 2);
        assertEquals(firstTwo + ":0:HITONIGHT", msg.createMessageHash());
    }

    @Test
    public void testMessageHashTestData2() {
        Message msg = new Message(1, "08575975889",
                "Hi Keegan, did you receive the payment?");

        String firstTwo = msg.getMessageID().substring(0, 2);
        assertEquals(firstTwo + ":1:HIPAYMENT", msg.createMessageHash());
    }

    @Test
    public void testMessageHashIsAllCapitals() {
        Message msg = new Message(0, "+27718669002",
                "Hi Mike, can you join us for dinner tonight?");
        assertEquals(msg.getMessageHash().toUpperCase(), msg.getMessageHash());
    }

    // ===== MESSAGE ID TESTS =====

    @Test
    public void testMessageIDCreated() {
        Message msg = new Message(0, "+27718669002",
                "Hi Mike, can you join us for dinner tonight?");
        assertTrue(msg.checkMessageID());                  // not more than 10 characters
        assertEquals(10, msg.getMessageID().length());     // exactly 10 characters
        assertTrue(msg.getMessageID().matches("\\d{10}")); // only digits
    }

    // ===== SENT MESSAGE TESTS =====

    @Test
    public void testSendMessage() {
        Message msg = new Message(0, "+27718669002",
                "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Message successfully sent.", msg.SentMessage(1));
    }

    @Test
    public void testDisregardMessage() {
        Message msg = new Message(1, "08575975889",
                "Hi Keegan, did you receive the payment?");
        assertEquals("Press 0 to delete the message.", msg.SentMessage(2));
    }

    @Test
    public void testStoreMessage() {
        Message msg = new Message(2, "+27718669002",
                "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Message successfully stored.", msg.SentMessage(3));
    }

    // ===== TOTAL MESSAGES TESTS =====

    @Test
    public void testReturnTotalMessages() {
        Message msg1 = new Message(0, "+27718669002",
                "Hi Mike, can you join us for dinner tonight?");
        Message msg2 = new Message(1, "+27718669002",
                "Hi Keegan, did you receive the payment?");

        // Nothing has been sent yet.
        assertEquals(0, Message.returnTotalMessages());

        msg1.SentMessage(1);   // send the first message
        msg2.SentMessage(2);   // disregard the second one (it is NOT sent)

        // Only one message was actually sent.
        assertEquals(1, Message.returnTotalMessages());
    }

    @Test
    public void testPrintMessages() {
        Message msg = new Message(0, "+27718669002",
                "Hi Mike, can you join us for dinner tonight?");
        msg.SentMessage(1);

        String printed = Message.printMessages();
        assertTrue(printed.contains(msg.getMessageID()));
        assertTrue(printed.contains(msg.getMessageHash()));
        assertTrue(printed.contains("+27718669002"));
        assertTrue(printed.contains("Hi Mike, can you join us for dinner tonight?"));
    }

    @Test
    public void testStoredMessageNotCountedAsSent() {
        Message msg = new Message(0, "+27718669002",
                "Hi Mike, can you join us for dinner tonight?");
        msg.SentMessage(3);   // store it
        assertFalse(Message.returnTotalMessages() > 0);
    }
}

