package com.mycompany.mavenproject1.nhlamulos.chat.app;

// These imports give us the testing tools from JUnit.
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

// Each method marked with @Test is one unit test.
public class LoginTest {

    // ---------- USERNAME TESTS ----------

    @Test
    public void testUsernameCorrectlyFormatted() {
        Login login = new Login("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(login.checkUserName());
    }

    @Test
    public void testUsernameIncorrectlyFormatted() {
        Login login = new Login("Kyle", "Smith", "kyle!!!!!!!", "Ch&&sec@ke99!", "+27838968976");
        assertFalse(login.checkUserName());
    }

    // ---------- PASSWORD TESTS ----------

    @Test
    public void testPasswordMeetsComplexity() {
        Login login = new Login("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(login.checkPasswordComplexity());
    }

    @Test
    public void testPasswordDoesNotMeetComplexity() {
        Login login = new Login("Kyle", "Smith", "kyl_1", "password", "+27838968976");
        assertFalse(login.checkPasswordComplexity());
    }

    // ---------- CELL PHONE TESTS ----------

    @Test
    public void testCellPhoneCorrectlyFormatted() {
        Login login = new Login("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(login.checkCellPhoneNumber());
    }

    @Test
    public void testCellPhoneIncorrectlyFormatted() {
        Login login = new Login("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "08966553");
        assertFalse(login.checkCellPhoneNumber());
    }

    // ---------- REGISTRATION MESSAGE TESTS ----------

    @Test
    public void testRegisterUserSuccess() {
        Login login = new Login("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertEquals("Username successfully captured.\nPassword successfully captured.",
                login.registerUser());
    }

    @Test
    public void testRegisterUserBadUsername() {
        Login login = new Login("Kyle", "Smith", "kyle!!!!!!!", "Ch&&sec@ke99!", "+27838968976");
        assertEquals("Username is not correctly formatted; please ensure that your "
                + "username contains an underscore and is no more than five "
                + "characters in length.", login.registerUser());
    }

    @Test
    public void testRegisterUserBadPassword() {
        Login login = new Login("Kyle", "Smith", "kyl_1", "password", "+27838968976");
        assertEquals("Password is not correctly formatted; please ensure that the "
                + "password contains at least eight characters, a capital "
                + "letter, a number, and a special character.", login.registerUser());
    }

    // ---------- LOGIN TESTS ----------

    @Test
    public void testLoginSuccessful() {
        Login login = new Login("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(login.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    public void testLoginFailed() {
        Login login = new Login("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertFalse(login.loginUser("kyl_1", "wrongPassword"));
    }

    @Test
    public void testLoginStatusMessages() {
        Login login = new Login("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertEquals("Welcome Kyle, Smith it is great to see you again.",
                login.returnLoginStatus(true));
        assertEquals("Username or password incorrect, please try again.",
                login.returnLoginStatus(false));
    }
}