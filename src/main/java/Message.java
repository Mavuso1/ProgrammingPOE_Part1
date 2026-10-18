import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Message {

    private String messageID;
    private String recipient;
    private String messageText;
    private String messageHash;
    private int messageNumber;
    private String messageStatus;

    private static int totalMessagesSent = 0;
    private static final List<Message> messages = new ArrayList<>();

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

    public String SentMessage(int choice) {
        switch (choice) {
            case 1:
                messageStatus = "Sent";
                totalMessagesSent++;
                messages.add(this);
                return "Message successfully sent.";

            case 2:
                messageStatus = "Disregarded";
                return "Press 0 to delete the message.";

            case 3:
                messageStatus = "Stored";
                messages.add(this);
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

    public String storeMessage() {

    String jsonMessage = "{\"messageID\":\"" + escapeJson(messageID)
            + "\",\"messageHash\":\"" + escapeJson(messageHash)
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

    public static void resetMessages() {
        totalMessagesSent = 0;
        messages.clear();
    }
}