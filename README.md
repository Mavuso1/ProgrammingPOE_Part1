# QuickChat – Java Messaging Application

## 1. Project Overview

QuickChat is a Java-based messaging application developed as part of the PROG5121 Programming Portfolio of Evidence (POE).

The application allows users to register, log in and send messages. Users can also store messages for later, disregard messages and manage stored messages.

The project demonstrates object-oriented programming, arrays, loops, string manipulation, file handling, JSON storage and unit testing.

## 2. Technologies Used

- Java 21
- Apache Maven
- JUnit 5
- Jackson Databind
- Visual Studio Code
- Git and GitHub

## 3. Application Features

### Part 1 – User Registration and Login

- User registration.
- Username validation.
- Password validation.
- South African cellphone number validation.
- User login authentication.

### Part 2 – Messaging System

- Send messages to recipients.
- Generate unique message IDs.
- Generate message hashes.
- Validate message length.
- Send, store or disregard messages.
- Display sent messages and total messages sent.

### Part 3 – Stored Messages

The application uses arrays to manage:

- Sent Messages.
- Disregarded Messages.
- Stored Messages.
- Message Hashes.
- Message IDs.

The Stored Messages menu provides the following functionality:

1. Display sender and recipient information.
2. Display the longest stored message.
3. Search for a message using its ID.
4. Search for messages using a recipient's number.
5. Delete a stored message using its hash.
6. Display a full report of stored messages.

Stored messages are saved in a JSON file and can be loaded when the application runs.

## 4. Project Structure

```text
ProgrammingPOE_Part1/
│
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── Main.java
│   │       ├── Login.java
│   │       └── Message.java
│   │
│   └── test/
│       └── java/
│           ├── LoginTest.java
│           └── MessageTest.java
│
├── messages.json
├── pom.xml
├── README.md
└── .gitignore
```

## 5. Requirements

To run the application, you need:

- Java Development Kit (JDK) 21.
- Apache Maven.
- Visual Studio Code or another Java-compatible IDE.

## 6. Running the Application

Open the project folder in Visual Studio Code.

Open the integrated terminal and run:

```bash
mvn clean test
```

To run the application:

```bash
mvn exec:java "-Dexec.mainClass=Main"
```

## 7. Running Unit Tests

The project uses JUnit 5 to test the application's functionality.

Run the following command:

```bash
mvn clean test
```

Maven will compile the project and execute the available unit tests.

A successful execution displays:

```text
BUILD SUCCESS
```

## 8. JSON Message Storage

The application uses the Jackson Databind library to read and update stored messages in `messages.json`.

Stored messages can be loaded into the application and managed through the Stored Messages menu.

## 9. GitHub Repository

The project is maintained using Git and GitHub.

The final Part 3 implementation is available in the `part3-final` branch.

Repository: https://github.com/Mavuso1/ProgrammingPOE_Part1

## 10. Conclusion

QuickChat demonstrates the development of a Java messaging application through three stages.

The final application combines user authentication, message management, JSON storage, arrays and automated testing.
