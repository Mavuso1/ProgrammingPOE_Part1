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

    // PART 3 TEST 1: Sent Messages array
@Test
public void testSentMessagesArrayPopulated() {

    Message message = new Message(
            0,
            "+27718693002",
            "Hello"
    );

    message.createMessageHash();
    message.SentMessage(1);

    assertEquals(1, Message.getSentMessages().length);
    assertEquals("Sent",
            Message.getSentMessages()[0].getMessageStatus());
}


// PART 3 TEST 2: Longest stored message
@Test
public void testLongestStoredMessage() {

    Message first = new Message(
            0,
            "+27718693002",
            "Hello"
    );

    Message second = new Message(
            1,
            "+27718693002",
            "This is the longest stored message."
    );

    first.createMessageHash();
    second.createMessageHash();

    first.SentMessage(3);
    second.SentMessage(3);

    assertEquals(
            second.getMessageText(),
            Message.getLongestStoredMessage().getMessageText()
    );
}


// PART 3 TEST 3: Search message by ID
@Test
public void testSearchMessageByID() {

    Message message = new Message(
            0,
            "+27718693002",
            "Hello"
    );

    message.createMessageHash();
    message.SentMessage(3);

    Message result = Message.findStoredMessageByID(
            message.getMessageID()
    );

    assertEquals(message.getMessageID(), result.getMessageID());
}


// PART 3 TEST 4: Search messages by recipient
@Test
public void testSearchMessagesByRecipient() {

    Message message = new Message(
            0,
            "+27718693002",
            "Hello"
    );

    message.createMessageHash();
    message.SentMessage(3);

    Message[] results =
            Message.findStoredMessagesByRecipient("+27718693002");

    assertEquals(1, results.length);
    assertEquals("+27718693002", results[0].getRecipient());
}


// PART 3 TEST 5: Delete message using hash
@Test
public void testDeleteMessageByHash() {

    Message message = new Message(
            0,
            "+27718693002",
            "Hello"
    );

    message.createMessageHash();
    message.SentMessage(3);

    boolean deleted = Message.deleteStoredMessageByHash(
            message.getMessageHash()
    );

    assertEquals(true, deleted);
    assertEquals(0, Message.getStoredMessages().length);
}


// PART 3 TEST 6: Display full message report
@Test
public void testDisplayMessageReport() {

    Message message = new Message(
            0,
            "+27718693002",
            "Hello"
    );

    message.setSender("Test User");
    message.createMessageHash();
    message.SentMessage(3);

    String report = Message.getStoredMessagesReport();

    assertEquals(true, report.contains(message.getMessageID()));
    assertEquals(true, report.contains("Test User"));
    assertEquals(true, report.contains("Hello"));
}
}