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

        // Only continue if registration succeeds
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

        // Only logged-in users may use QuickChat
        if (!loginSuccess) {
            scanner.close();
            return;
        }

        System.out.println("\nWelcome to QuickChat.");

        // Ask how many messages the user wants to enter
        int messageLimit = readPositiveInteger(
                scanner,
                "How many messages would you like to enter? "
        );

        int messagesEntered = 0;

        // Main menu
        boolean running = true;

        while (running) {

            System.out.println("\n---------- QUICKCHAT MENU ----------");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
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

                        // Validate message ID
                        if (!message.checkMessageID()) {
                            System.out.println(
                                    "Invalid message ID."
                            );
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

                        System.out.println(
                                "Message Hash: " + hash
                        );

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

                        String actionResult =
                                message.SentMessage(action);

                        System.out.println(actionResult);

                        if (action == 1 || action == 3) {

                            System.out.println("\n---------- MESSAGE DETAILS ----------");
                            System.out.println(
                                    "Message ID: "
                                            + message.getMessageID()
                            );
                            System.out.println(
                                    "Message Hash: "
                                            + message.getMessageHash()
                            );
                            System.out.println(
                                    "Recipient: "
                                            + message.getRecipient()
                            );
                            System.out.println(
                                    "Message: "
                                            + message.getMessageText()
                            );

                            if (action == 3) {
                                System.out.println(
                                        "\nJSON message data:"
                                );
                                System.out.println(
                                        message.storeMessage()
                                );
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

                default:
                    System.out.println(
                            "Invalid option. Please choose 1, 2, or 3."
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