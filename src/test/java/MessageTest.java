import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @BeforeEach
    public void setUp() {
        Message.resetMessages();
    }

    @Test
    public void testMessageIDHasTenDigits() {
        Message message = new Message(
                0,
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );

        assertTrue(message.checkMessageID());
        assertEquals(10, message.getMessageID().length());
        assertTrue(message.getMessageID().matches("[0-9]{10}"));
    }

    @Test
    public void testValidRecipientNumber() {
        Message message = new Message(
                0,
                "+27718693002",
                "Hello"
        );

        assertEquals(
                "Cell phone number successfully captured.",
                message.checkRecipientCell()
        );
    }

    @Test
    public void testInvalidRecipientNumber() {
        Message message = new Message(
                0,
                "08575975889",
                "Hello"
        );

        assertEquals(
                "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                message.checkRecipientCell()
        );
    }

    @Test
    public void testMessageWithin250Characters() {
        Message message = new Message(
                0,
                "+27718693002",
                "Hi Mike, how are you?"
        );

        assertTrue(message.checkMessageLength());
    }

    @Test
    public void testMessageExceeds250Characters() {
        String longText = "A".repeat(251);

        Message message = new Message(
                0,
                "+27718693002",
                longText
        );

        assertFalse(message.checkMessageLength());
    }

    @Test
    public void testMessageHash() {
        Message message = new Message(
                0,
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );

        String expectedHash = message.getMessageID()
                .substring(0, 2)
                + ":0:HITONIGHT";

        assertEquals(expectedHash, message.createMessageHash());
    }

    @Test
    public void testSendMessage() {
        Message message = new Message(
                0,
                "+27718693002",
                "Hello"
        );

        message.createMessageHash();

        assertEquals(
                "Message successfully sent.",
                message.SentMessage(1)
        );

        assertEquals(1, message.returnTotalMessagess());
    }

    @Test
    public void testDisregardMessage() {
        Message message = new Message(
                0,
                "+27718693002",
                "Hello"
        );

        assertEquals(
                "Press 0 to delete the message.",
                message.SentMessage(2)
        );

        assertEquals(0, message.returnTotalMessagess());
    }

    @Test
    public void testStoreMessage() {
        Message message = new Message(
                0,
                "+27718693002",
                "Hello"
        );

        message.createMessageHash();

        assertEquals(
                "Message successfully stored.",
                message.SentMessage(3)
        );

        assertEquals(0, message.returnTotalMessagess());
    }

    @Test
    public void testInvalidMessageAction() {
        Message message = new Message(
                0,
                "+27718693002",
                "Hello"
        );

        assertTrue(message.SentMessage(4).contains("Invalid option"));
    }
}