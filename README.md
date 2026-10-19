# Programming POE Part 1

## Chat Application

This project is a console-based Java chat application.

## Features

- User registration
- Username validation
- Password complexity validation
- Cell phone number validation
- User login
- Login status messages
- JUnit unit testing
- Maven automated testing

## Technologies Used

- Java 21
- Maven
- JUnit 5
- Git and GitHub

## Testing

The project uses JUnit tests to test valid and invalid usernames, passwords, cell phone numbers, registration and login.

Maven is used to run the automated tests using:

mvn test
## Author
# QuickChat Application — Programming POE Part 2

## 1. Project Overview

QuickChat is a Java console-based messaging application developed for the Programming POE Part 2 assignment.

The application builds on the registration and login functionality completed in Part 1. After successfully logging in, users can access the QuickChat menu and work with messages.

The project demonstrates object-oriented programming, input validation, loops, string handling, unit testing, JSON-formatted message storage, and automated testing using GitHub Actions.

## 2. Features

The application includes the following features:

* User registration and login.
* Login protection before accessing the messaging menu.
* A menu for sending messages, displaying the recently sent messages option, and quitting.
* Generation of ten-digit message IDs.
* Recipient cellphone number validation.
* Message length validation.
* Automatic message hash generation.
* Options to send, disregard, or store a message.
* Display of message details.
* Counting of successfully sent messages.
* JSON-formatted storage of messages.
* JUnit tests for login and messaging functionality.
* Automated Maven testing through GitHub Actions.

The recently sent messages menu option currently displays "Coming Soon." as required by the assignment.

## 3. Technologies Used

* Java 21
* Apache Maven
* JUnit 5
* Visual Studio Code
* Git and GitHub
* GitHub Actions
* JSON

## 4. Project Structure

```text
ProgrammingPOE_Part1/
├── .github/
│   └── workflows/
│       └── maven.yml
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── Main.java
│   │       ├── Login.java
│   │       └── Message.java
│   └── test/
│       └── java/
│           ├── LoginTest.java
│           └── MessageTest.java
├── messages.json
├── pom.xml
├── README.md
└── .gitignore
```

The `messages.json` file is created when a message is stored. It may not exist until the store-message feature has been used.

## 5. How to Run the Application

### Requirements

Install Java 21 and Apache Maven, and ensure that both are available from the terminal.

### Run the tests

Open a terminal in the project directory and run:

```bash
mvn clean test
```

Maven compiles the project and runs the automated unit tests.

### Compile the application

Run:

```bash
mvn compile
```

### Start QuickChat

Run:

```bash
java -cp target/classes Main
```

Follow the prompts to register, log in, and access the QuickChat menu.

## 6. QuickChat Menu

After successful login, the application displays the following menu:

1. Send Messages
2. Show recently sent messages
3. Quit

Users specify how many messages they want to enter. They can then enter recipient details, write a message, and choose an action.

The available message actions are:

* Send Message
* Disregard Message
* Store Message to send later

## 7. Message Validation and Storage

Each message receives a ten-digit message ID.

The application validates recipient cellphone numbers and checks that messages do not exceed 250 characters.

A message hash is created using the first two digits of the message ID, the message number, and the first and last words of the message.

Stored messages are written to `messages.json` in JSON format.

## 8. Unit Testing

JUnit tests are included for the login and messaging functionality.

The messaging tests cover:

* Message ID generation.
* Recipient number validation.
* Message length validation.
* Message hash generation.
* Sending messages.
* Disregarding messages.
* Storing messages.
* Invalid message actions.

Run all tests with:

```bash
mvn clean test
```

## 9. GitHub Actions

The project includes a GitHub Actions workflow located at:

`.github/workflows/maven.yml`

The workflow automatically runs the Maven tests when changes are pushed to the configured branches or when a pull request targets the configured main branch.

This helps identify errors and confirms whether the automated tests pass in the GitHub environment.

## 10. Conclusion

This project extends the original registration and login application by introducing messaging functionality.

It demonstrates the use of Java classes and methods, loops, string handling, input validation, unit testing, JSON-formatted storage, and automated testing.

The project was developed and managed using Git and GitHub.

Musa Mavuso

## Part 1 Status

Part 1 includes the completed registration, validation, login and automated JUnit testing functionality.
