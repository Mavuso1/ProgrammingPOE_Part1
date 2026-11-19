import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Message {

    private String messageID;
    private String recipient;
    private String messageText;
    private String sender;
    private String messageHash;
    private int messageNumber;
    private String messageStatus;

    private static int totalMessagesSent = 0;

// General collection of all processed messages
private static final List<Message> messages = new ArrayList<>();

// Part 3 required arrays
private static Message[] sentMessages = new Message[0];

private static Message[] disregardedMessages = new Message[0];

private static Message[] storedMessages = new Message[0];

private static String[] messageHashes = new String[0];

private static String[] messageIDs = new String[0];

    public Message(int messageNumber, String recipient, String messageText) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateMessageID();
        this.messageStatus = "Pending";
    }

    private String generateMessageID() {
        Random random = new Random();
        long number = 1_000_000_000L
                + (long) (random.nextDouble() * 9_000_000_000L);

        return String.valueOf(number);
    }

    public Boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    public String checkRecipientCell() {
        if (recipient != null && recipient.matches("\\+27[0-9]{9}")) {
            return "Cell phone number successfully captured.";
        }

        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    public boolean checkMessageLength() {
        return messageText != null && messageText.length() <= 250;
    }

  public String createMessageHash() {

    String cleanedMessage = messageText.trim()
            .replaceAll("[^a-zA-Z0-9\\s]", "");

    String[] words = cleanedMessage.split("\\s+");

    String firstWord = words.length > 0 ? words[0] : "";
    String lastWord = words.length > 0
            ? words[words.length - 1] : "";

    String firstTwoDigits = messageID.substring(0, 2);

    messageHash = (firstTwoDigits + ":"
            + messageNumber + ":"
            + firstWord + lastWord).toUpperCase();

    return messageHash;
}

    // Add a message to the sent messages array
private static void addSentMessage(Message message) {
    sentMessages = java.util.Arrays.copyOf(
            sentMessages, sentMessages.length + 1
    );

    sentMessages[sentMessages.length - 1] = message;
}

// Add a message to the disregarded messages array
private static void addDisregardedMessage(Message message) {
    disregardedMessages = java.util.Arrays.copyOf(
            disregardedMessages, disregardedMessages.length + 1
    );

    disregardedMessages[disregardedMessages.length - 1] = message;
}

// Add a message to the stored messages array
private static void addStoredMessage(Message message) {
    storedMessages = java.util.Arrays.copyOf(
            storedMessages, storedMessages.length + 1
    );

    storedMessages[storedMessages.length - 1] = message;
}

// Add message hash to the hash array
private static void addMessageHash(String hash) {
    messageHashes = java.util.Arrays.copyOf(
            messageHashes, messageHashes.length + 1
    );

    messageHashes[messageHashes.length - 1] = hash;
}

// Add message ID to the ID array
private static void addMessageID(String id) {
    messageIDs = java.util.Arrays.copyOf(
            messageIDs, messageIDs.length + 1
    );

    messageIDs[messageIDs.length - 1] = id;
}

    public String SentMessage(int choice) {

    switch (choice) {

        case 1:
            messageStatus = "Sent";

            totalMessagesSent++;

            messages.add(this);

            addSentMessage(this);

            addMessageHash(messageHash);

            addMessageID(messageID);

            return "Message successfully sent.";

        case 2:
            messageStatus = "Disregarded";

            messages.add(this);

            addDisregardedMessage(this);

            addMessageHash(messageHash);

            addMessageID(messageID);

            return "Press 0 to delete the message.";

        case 3:
            messageStatus = "Stored";

            messages.add(this);

            addStoredMessage(this);

            addMessageHash(messageHash);

            addMessageID(messageID);

            return "Message successfully stored.";

        default:
            return "Invalid option. Please choose 1, 2, or 3.";
    }
}

    public String printMessages() {
        StringBuilder output = new StringBuilder();

        for (Message message : messages) {
            output.append("Message ID: ")
                    .append(message.messageID)
                    .append("\nMessage Hash: ")
                    .append(message.messageHash)
                    .append("\nRecipient: ")
                    .append(message.recipient)
                    .append("\nMessage: ")
                    .append(message.messageText)
                    .append("\nStatus: ")
                    .append(message.messageStatus)
                    .append("\n\n");
        }

        return output.toString();
    }

    public int returnTotalMessagess() {
        return totalMessagesSent;
    }

    public static String loadStoredMessages() {

    Path filePath = Path.of("messages.json");

    if (!Files.exists(filePath)) {
        return "No stored messages found.";
    }

    try {
        ObjectMapper mapper = new ObjectMapper();

        JsonNode root = mapper.readTree(filePath.toFile());

        if (root == null || !root.isArray()) {
            return "Invalid messages.json file.";
        }

        for (JsonNode node : root) {

        String messageID = node.path("messageID").asText();
        String messageHash = node.path("messageHash").asText();
        String sender = node.path("sender").asText();
        String recipient = node.path("recipient").asText();
        String messageText = node.path("message").asText();
        String status = node.path("status").asText();
            
            // Prevent loading the same message more than once.
            boolean alreadyLoaded = false;

            for (Message existing : storedMessages) {
                if (existing.messageID.equals(messageID)) {
                    alreadyLoaded = true;
                    break;
                }
            }

            if (!alreadyLoaded && status.equals("Stored")) {

                Message loadedMessage = new Message(
                        0,
                        recipient,
                        messageText
                );

                // Restore the original message information.
                loadedMessage.messageID = messageID;
                loadedMessage.messageHash = messageHash;
                loadedMessage.sender = sender;
                loadedMessage.messageStatus = status;

                addStoredMessage(loadedMessage);
                addMessageHash(messageHash);
                addMessageID(messageID);

                messages.add(loadedMessage);
            }
        }

        return "Stored messages loaded successfully.";

    } catch (IOException e) {
        return "Unable to load messages: " + e.getMessage();
    }
}

    public String storeMessage() {

    String jsonMessage = "{\"messageID\":\"" + escapeJson(messageID)
        + "\",\"messageHash\":\"" + escapeJson(messageHash)
        + "\",\"sender\":\"" + escapeJson(sender)
        + "\",\"recipient\":\"" + escapeJson(recipient)
        + "\",\"message\":\"" + escapeJson(messageText)
        + "\",\"status\":\"" + escapeJson(messageStatus)
        + "\"}";

    Path filePath = Path.of("messages.json");

    try {
        String existingJson;

        if (Files.exists(filePath)) {
            existingJson = Files.readString(filePath).trim();
        } else {
            existingJson = "[]";
        }

        if (existingJson.isEmpty()) {
            existingJson = "[]";
        }

        if (!existingJson.startsWith("[") || !existingJson.endsWith("]")) {
            return "Unable to store message: invalid JSON file.";
        }

        String contents = existingJson.substring(
                1, existingJson.length() - 1
        ).trim();

        String updatedJson;

        if (contents.isEmpty()) {
            updatedJson = "[" + jsonMessage + "]";
        } else {
            updatedJson = "[" + contents + "," + jsonMessage + "]";
        }

        Files.writeString(filePath, updatedJson);

        return "Message saved to messages.json.";

    } catch (IOException e) {
        return "Unable to store message: " + e.getMessage();
    }
}

    // Find the longest stored message
public static Message getLongestStoredMessage() {

    if (storedMessages.length == 0) {
        return null;
    }

    Message longest = storedMessages[0];

    for (Message message : storedMessages) {

        if (message.getMessageText().length()
                > longest.getMessageText().length()) {

            longest = message;
        }
    }

    return longest;
}


// Search stored messages using message ID
public static Message findStoredMessageByID(String id) {

    for (Message message : storedMessages) {

        if (message.getMessageID().equals(id)) {
            return message;
        }
    }

    return null;
}


// Search stored messages using recipient
public static Message[] findStoredMessagesByRecipient(String recipient) {

    return java.util.Arrays.stream(storedMessages)
            .filter(message -> message.getRecipient().equals(recipient))
            .toArray(Message[]::new);
}


// Generate full stored message report
public static String getStoredMessagesReport() {

    StringBuilder report = new StringBuilder();

    for (Message message : storedMessages) {

        report.append("Message ID: ")
                .append(message.getMessageID())
                .append("\nMessage Hash: ")
                .append(message.getMessageHash())
                .append("\nSender: ")
                .append(message.getSender())
                .append("\nRecipient: ")
                .append(message.getRecipient())
                .append("\nMessage: ")
                .append(message.getMessageText())
                .append("\nStatus: ")
                .append(message.getMessageStatus())
                .append("\n-------------------------\n");
    }

    return report.toString();
}

 public static boolean deleteStoredMessageByHash(String hash) {

    int index = -1;

    // Find the message in the stored messages array
    for (int i = 0; i < storedMessages.length; i++) {

        if (storedMessages[i].getMessageHash().equals(hash)) {
            index = i;
            break;
        }
    }

    if (index == -1) {
        return false;
    }

    String deletedID = storedMessages[index].getMessageID();

    // Update the JSON file first
    Path filePath = Path.of("messages.json");

    try {

        if (Files.exists(filePath)) {

            ObjectMapper mapper = new ObjectMapper();

            JsonNode root = mapper.readTree(filePath.toFile());

            if (root == null || !root.isArray()) {
                return false;
            }

            com.fasterxml.jackson.databind.node.ArrayNode updatedJson =
                    mapper.createArrayNode();

            for (JsonNode node : root) {

                if (!node.path("messageHash").asText().equals(hash)) {
                    updatedJson.add(node);
                }
            }

            mapper.writeValue(filePath.toFile(), updatedJson);
        }

    } catch (IOException e) {

        System.out.println("Unable to update JSON file: " + e.getMessage());
        return false;
    }

    // Remove message from stored messages array
    Message[] updatedStoredMessages =
            new Message[storedMessages.length - 1];

    for (int i = 0, j = 0; i < storedMessages.length; i++) {

        if (i != index) {
            updatedStoredMessages[j++] = storedMessages[i];
        }
    }

    storedMessages = updatedStoredMessages;

    // Remove from general messages collection
    messages.removeIf(message ->
            message.getMessageID().equals(deletedID));

    // Remove matching hash
    messageHashes = java.util.Arrays.stream(messageHashes)
            .filter(existingHash -> !existingHash.equals(hash))
            .toArray(String[]::new);

    // Remove matching ID
    messageIDs = java.util.Arrays.stream(messageIDs)
            .filter(existingID -> !existingID.equals(deletedID))
            .toArray(String[]::new);

    return true;
}
 
    private String escapeJson(String text) {
        if (text == null) {
            return "";
        }

        return text.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    public String getMessageID() {
        return messageID;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSender() {
     return sender;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public String getMessageStatus() {
        return messageStatus;
    }
    
    // Return sent messages
public static Message[] getSentMessages() {
    return sentMessages;
}

// Return disregarded messages
public static Message[] getDisregardedMessages() {
    return disregardedMessages;
}

// Return stored messages
public static Message[] getStoredMessages() {
    return storedMessages;
}

// Return message hashes
public static String[] getMessageHashes() {
    return messageHashes;
}

// Return message IDs
public static String[] getMessageIDs() {
    return messageIDs;
}

    public static void resetMessages() {

    totalMessagesSent = 0;

    messages.clear();

    sentMessages = new Message[0];

    disregardedMessages = new Message[0];

    storedMessages = new Message[0];

    messageHashes = new String[0];

    messageIDs = new String[0];
}
}