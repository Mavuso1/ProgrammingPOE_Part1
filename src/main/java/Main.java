import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("=================================");
        System.out.println("       QUICKCHAT APPLICATION");
        System.out.println("=================================");

        // Registration
        System.out.print("Enter your first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter your last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        System.out.print("Enter your cell phone number: ");
        String cellPhoneNumber = scanner.nextLine();

        Login login = new Login(
                firstName,
                lastName,
                username,
                password,
                cellPhoneNumber
        );

        String registrationMessage = login.registerUser();

        System.out.println();
        System.out.println(registrationMessage);

        if (!registrationMessage.equals("Registration successful.")) {
            scanner.close();
            return;
        }

        // Login
        System.out.println("\n---------- LOGIN ----------");

        System.out.print("Enter your username: ");
        String loginUsername = scanner.nextLine();

        System.out.print("Enter your password: ");
        String loginPassword = scanner.nextLine();

        Boolean loginSuccess = login.loginUser(
                loginUsername,
                loginPassword
        );

        System.out.println(login.returnLoginStatus(loginSuccess));

        if (!loginSuccess) {
            scanner.close();
            return;
        }

        System.out.println("\nWelcome to QuickChat.");

        // Message limit
        int messageLimit = readPositiveInteger(
                scanner,
                "How many messages would you like to enter? "
        );

        int messagesEntered = 0;

        boolean running = true;

        // Main menu
        while (running) {

            System.out.println("\n---------- QUICKCHAT MENU ----------");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.println("4) Stored Messages");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {

                case "1":

                    if (messagesEntered >= messageLimit) {
                        System.out.println(
                                "You have reached your message limit."
                        );
                        break;
                    }

                    while (messagesEntered < messageLimit) {

                        int messageNumber = messagesEntered;

                        System.out.println(
                                "\n---------- MESSAGE "
                                        + (messagesEntered + 1)
                                        + " ----------"
                        );

                        System.out.print(
                                "Enter recipient number (+27...): "
                        );
                        String recipient = scanner.nextLine();

                        System.out.print("Enter your message: ");
                        String messageText = scanner.nextLine();

                        Message message = new Message(
                                messageNumber,
                                recipient,
                                messageText
                        );

                        message.setSender(firstName + " " + lastName);

                        // Validate message ID
                        if (!message.checkMessageID()) {
                            System.out.println("Invalid message ID.");
                            continue;
                        }

                        // Validate recipient
                        String recipientResult =
                                message.checkRecipientCell();

                        if (!recipientResult.equals(
                                "Cell phone number successfully captured."
                        )) {
                            System.out.println(recipientResult);
                            continue;
                        }

                        // Validate message length
                        if (!message.checkMessageLength()) {

                            int excess = messageText.length() - 250;

                            System.out.println(
                                    "Message exceeds 250 characters by "
                                            + excess
                                            + "; please reduce the size."
                            );

                            continue;
                        }

                        System.out.println(
                                "Message ID generated: "
                                        + message.getMessageID()
                        );

                        String hash = message.createMessageHash();

                        System.out.println("Message Hash: " + hash);

                        System.out.println("\nChoose what to do:");
                        System.out.println("1) Send Message");
                        System.out.println("2) Disregard Message");
                        System.out.println("3) Store Message to send later");
                        System.out.print("Enter your choice: ");

                        int action;

                        try {
                            action = Integer.parseInt(
                                    scanner.nextLine()
                            );

                        } catch (NumberFormatException e) {

                            System.out.println(
                                    "Invalid option. Message not processed."
                            );

                            continue;
                        }

                        String actionResult = message.SentMessage(action);

                        System.out.println(actionResult);

                        if (action == 1 || action == 3) {

                            System.out.println(
                                    "\n---------- MESSAGE DETAILS ----------"
                            );

                            System.out.println(
                                    "Message ID: " + message.getMessageID()
                            );

                            System.out.println(
                                    "Message Hash: " + message.getMessageHash()
                            );

                            System.out.println(
                                    "Recipient: " + message.getRecipient()
                            );

                            System.out.println(
                                    "Message: " + message.getMessageText()
                            );

                            if (action == 3) {

                                System.out.println("\nJSON message data:");

                                System.out.println(message.storeMessage());
                            }
                        }

                        if (action == 1 || action == 2 || action == 3) {
                            messagesEntered++;
                        }

                        System.out.println(
                                "\nMessages entered: "
                                        + messagesEntered
                                        + "/" + messageLimit
                        );

                        if (messagesEntered >= messageLimit) {

                            System.out.println(
                                    "You have completed your message limit."
                            );

                            break;
                        }

                        System.out.print(
                                "\nWould you like to enter another message? (yes/no): "
                        );

                        String another = scanner.nextLine();

                        if (!another.equalsIgnoreCase("yes")) {
                            break;
                        }
                    }

                    break;

                case "2":

                    System.out.println("Coming Soon.");
                    break;

                case "3":

                    running = false;

                    System.out.println(
                            "Thank you for using QuickChat. Goodbye!"
                    );

                    break;

                case "4":

                    System.out.println(
                            Message.loadStoredMessages()
                    );

                    storedMessagesMenu(scanner);
                    break;

                default:

                    System.out.println(
                            "Invalid option. Please choose 1, 2, 3, or 4."
                    );
            }
        }

        System.out.println(
                "\nTotal messages successfully sent: "
                        + new Message(0, "+27718693002", "Count")
                                .returnTotalMessagess()
        );

        scanner.close();
    }

    // ==========================================
    // STORED MESSAGES MENU
    // ==========================================

    private static void storedMessagesMenu(Scanner scanner) {

        boolean menuRunning = true;

        while (menuRunning) {

            System.out.println("\n========== STORED MESSAGES ==========");
            System.out.println("A) Display sender and recipient");
            System.out.println("B) Display longest message");
            System.out.println("C) Search message by ID");
            System.out.println("D) Search messages by recipient");
            System.out.println("E) Delete message using hash");
            System.out.println("F) Display full message report");
            System.out.println("G) Return to main menu");

            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice.toUpperCase()) {

                case "A":
                    displaySendersAndRecipients();
                    break;

                case "B":
                    displayLongestMessage();
                    break;

                case "C":
                    searchMessageByID(scanner);
                    break;

                case "D":
                    searchMessagesByRecipient(scanner);
                    break;

                case "E":
                    deleteMessageByHash(scanner);
                    break;

                case "F":
                    displayMessageReport();
                    break;

                case "G":
                    menuRunning = false;
                    break;

                default:
                    System.out.println(
                            "Invalid option. Please try again."
                    );
            }
        }
    }

    // ==========================================
    // FUNCTION A: DISPLAY SENDERS AND RECIPIENTS
    // ==========================================

    private static void displaySendersAndRecipients() {

        Message[] storedMessages = Message.getStoredMessages();

        if (storedMessages.length == 0) {
            System.out.println("No stored messages available.");
            return;
        }

        System.out.println(
                "\n--- STORED MESSAGE SENDERS AND RECIPIENTS ---"
        );

        for (Message message : storedMessages) {

            System.out.println("Sender: " + message.getSender());

            System.out.println("Recipient: " + message.getRecipient());

            System.out.println("--------------------------------");
        }
    }

    // ==========================================
    // FUNCTION B: DISPLAY LONGEST MESSAGE
    // ==========================================

    private static void displayLongestMessage() {

        Message[] storedMessages = Message.getStoredMessages();

        if (storedMessages.length == 0) {
            System.out.println("No stored messages available.");
            return;
        }

        Message longestMessage = storedMessages[0];

        for (Message message : storedMessages) {

            if (message.getMessageText().length()
                    > longestMessage.getMessageText().length()) {

                longestMessage = message;
            }
        }

        System.out.println("\n--- LONGEST STORED MESSAGE ---");

        System.out.println(
                "Message: " + longestMessage.getMessageText()
        );

        System.out.println(
                "Length: "
                        + longestMessage.getMessageText().length()
                        + " characters"
        );

        System.out.println(
                "Recipient: " + longestMessage.getRecipient()
        );
    }

    // ==========================================
    // FUNCTION C: SEARCH MESSAGE BY ID
    // ==========================================

    private static void searchMessageByID(Scanner scanner) {

        System.out.print("Enter the message ID: ");

        String searchID = scanner.nextLine();

        Message[] storedMessages = Message.getStoredMessages();

        boolean found = false;

        for (Message message : storedMessages) {

            if (message.getMessageID().equals(searchID)) {

                System.out.println("\n--- MESSAGE FOUND ---");

                System.out.println(
                        "Message ID: " + message.getMessageID()
                );

                System.out.println(
                        "Recipient: " + message.getRecipient()
                );

                System.out.println(
                        "Message: " + message.getMessageText()
                );

                found = true;

                break;
            }
        }

        if (!found) {
            System.out.println("Message ID not found.");
        }
    }

    // ==========================================
    // FUNCTION D: SEARCH MESSAGES BY RECIPIENT
    // ==========================================

    private static void searchMessagesByRecipient(Scanner scanner) {

        System.out.print("Enter the recipient number: ");

        String searchRecipient = scanner.nextLine();

        Message[] storedMessages = Message.getStoredMessages();

        boolean found = false;

        System.out.println("\n--- MESSAGES FOR RECIPIENT ---");

        for (Message message : storedMessages) {

            if (message.getRecipient().equals(searchRecipient)) {

                System.out.println(
                        "Message ID: " + message.getMessageID()
                );

                System.out.println(
                        "Recipient: " + message.getRecipient()
                );

                System.out.println(
                        "Message: " + message.getMessageText()
                );

                System.out.println("-----------------------------");

                found = true;
            }
        }

        if (!found) {
            System.out.println(
                    "No messages found for this recipient."
            );
        }
    }

    // ==========================================
    // FUNCTION E: DELETE MESSAGE USING HASH
    // ==========================================

    private static void deleteMessageByHash(Scanner scanner) {

        System.out.print("Enter the message hash to delete: ");

        String searchHash = scanner.nextLine();

        boolean deleted =
                Message.deleteStoredMessageByHash(searchHash);

        if (deleted) {

            System.out.println("Message successfully deleted.");

        } else {

            System.out.println("Message hash not found.");
        }
    }

    // ==========================================
    // FUNCTION F: DISPLAY FULL MESSAGE REPORT
    // ==========================================

    private static void displayMessageReport() {

        Message[] storedMessages = Message.getStoredMessages();

        if (storedMessages.length == 0) {
            System.out.println("No stored messages available.");
            return;
        }

        System.out.println(
                "\n========== FULL STORED MESSAGE REPORT =========="
        );

        for (int i = 0; i < storedMessages.length; i++) {

            Message message = storedMessages[i];

            System.out.println("\nMessage Number: " + (i + 1));

            System.out.println(
                    "Message ID: " + message.getMessageID()
            );

            System.out.println(
                    "Message Hash: " + message.getMessageHash()
            );

            System.out.println(
                    "Sender: " + message.getSender()
            );

            System.out.println(
                    "Recipient: " + message.getRecipient()
            );

            System.out.println(
                    "Message: " + message.getMessageText()
            );

            System.out.println(
                    "Status: " + message.getMessageStatus()
            );

            System.out.println("-----------------------------------------------");
        }

        System.out.println(
                "Total stored messages: " + storedMessages.length
        );
    }

    // ==========================================
    // INPUT VALIDATION
    // ==========================================

    private static int readPositiveInteger(
            Scanner scanner,
            String prompt
    ) {

        while (true) {

            System.out.print(prompt);

            try {

                int number = Integer.parseInt(scanner.nextLine());

                if (number > 0) {
                    return number;
                }

                System.out.println(
                        "Please enter a number greater than zero."
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid input. Please enter a whole number."
                );
            }
        }
    }
}