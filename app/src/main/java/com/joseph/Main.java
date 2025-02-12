package com.joseph;

import com.joseph.Utils.*;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class Main {

    public static final int sleepTime = 650;

    public static void main(String[] args)
            throws InterruptedException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        DataHandler.loadUsers();

        boolean is_running = true;
        while (is_running) {

            System.out.println("|-------------|");
            System.out.println("  Secret Note");
            System.out.println("    Welcome! ");
            System.out.println("|-------------|");
            System.out.println(" 1) Login.");
            System.out.println(" 2) Register");
            System.out.println(" 0) Save & Exit.");
            System.out.println("|-------------|");

            int choice = inputValidation.checkInt("Choose one: ");
            switch (choice) {
                case 1 -> login();
                case 2 -> Register();
                case 0 -> is_running = false;
                default -> {
                    System.out.println("Please pick a valid option.");
                    Thread.sleep(sleepTime);
                }
            }
        }
        DataHandler.saveUsers();
        inputValidation.scanner.close();
    }

    private static void login() throws InterruptedException, NoSuchAlgorithmException, InvalidKeySpecException,
            InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {

        while (true) {

            System.out.println("|-------------|");
            System.out.println("  Secret Note");
            System.out.println("     Login ");
            System.out.println("|-------------|");
            System.out.println(" Select a user:");
            if (User.getUsersSize() > 0) {
                User.printUsers();
            } else {
                System.out.println(" > No users available.");
                System.out.println(" > You need to register");
            }
            System.out.println(" 0) quit.");
            System.out.println("|-------------|");

            int choice = inputValidation.checkInt("Choose one: ");
            if (choice == 0) {
                break;
            } else if (choice > User.getUsersSize()) {
                System.out.println("Enter a valid number please.");
                continue;
            } else {
                User user = User.getUser(choice - 1);
                System.out.println("Username: " + user.getName());

                String checkPassword = inputValidation.checkString("Enter your password: ");

                if (!Cryptography.toHashString(checkPassword, user.getSalt()).equals(user.getPassword())) {
                    System.out.println("Wrong password.");
                    Thread.sleep(sleepTime);
                    continue;

                } else {
                    loginHelper(user, checkPassword);
                }
            }
        }
    }

    private static void loginHelper(User user, String correctPass)
            throws InterruptedException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {

        boolean is_running = true;
        while (is_running) {

            System.out.println("|-------------|");
            System.out.println(" Logged in as  ");
            System.out.println("     " + user.getName());
            System.out.println("|-------------|");
            System.out.println(" 1) Show note.");
            System.out.println(" 2) Change note.");
            System.out.println(" 3) Delete note.");
            System.out.println(" 4) Change password.");
            System.out.println(" 0) quit.");
            System.out.println("|-------------|");

            int choice = inputValidation.checkInt("Choose one: ");
            switch (choice) {
                case 1 -> showNote(user, correctPass);
                case 2 -> changeNote(user, correctPass);
                case 3 -> deleteNote(user, correctPass);
                case 4 -> changePass(user, correctPass);
                case 0 -> {
                    System.out.println();
                    is_running = false;
                }
                default -> {
                    System.out.println("Please pick a valid option.");
                    Thread.sleep(sleepTime);
                }
            }
        }
    }

    private static void showNote(User user, String correctPass)
            throws InterruptedException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {

        String prompt = " ";
        if (user.getNote() == null) {

            System.out.println("No note available.");
            prompt = "Would you like to add one? (y/n)";

        } else {

            SecretKey key = Cryptography.makeKey(correctPass, user.getSaltKey());
            String decryptedTitle = Cryptography.Decipher(user.getNote().title, key);
            String decryptedNote = Cryptography.Decipher(user.getNote().note, key);

            System.out.println("|-------------|");
            System.out.println(decryptedTitle);
            System.out.println(decryptedNote);
            System.out.println(user.getNote().dateStamp);
            System.out.println("|-------------|");
            prompt = "Would you like to change the note? (y/n)";
        }
        boolean choice = inputValidation.checkBool(prompt);
        if (choice) {
            changeNote(user, correctPass);
        }
    }

    private static void changeNote(User user, String correctPass)
            throws InterruptedException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {

        String title = inputValidation.checkString("Please enter the title: ");
        String note = inputValidation.checkString("Please enter the note: ");

        SecretKey key = Cryptography.makeKey(correctPass, user.getSaltKey());
        String encryptedTitle = Cryptography.Cipher(title, key);
        String encryptedTitleNote = Cryptography.Cipher(note, key);

        user.setSecretNote(encryptedTitle, encryptedTitleNote);
        System.out.println("Note successfully set.");
        System.out.print("Press enter to continue..");
        inputValidation.scanner.nextLine();
    }

    private static void deleteNote(User user, String correctPass)
            throws InterruptedException, NoSuchAlgorithmException, InvalidKeySpecException {

        while (true) {
            boolean confirm = inputValidation.checkBool("Are you sure you would like to delete the note? (y/n)");
            if (confirm) {

                String checkPassword = inputValidation.checkString("Enter your password: ");
                if (!Cryptography.toHashString(checkPassword, user.getSalt()).equals(user.getPassword())) {
                    System.out.println("Wrong password.");
                    Thread.sleep(sleepTime);
                    continue;
                } else {
                    user.deleteNote();
                    System.out.println("Note has been deleted.");
                    System.out.print("Press enter to continue..");
                    inputValidation.scanner.nextLine();
                    break;
                }

            } else {
                break;
            }
        }
    }

    private static void changePass(User user, String correctPass)
            throws NoSuchAlgorithmException, InvalidKeySpecException, InterruptedException {

        while (true) {
            String password = inputValidation.checkStringNoSpaces("Enter new password: ");
            String passwordConfirm = inputValidation.checkStringNoSpaces("Enter new password again: ");

            if (!password.equals(passwordConfirm)) {

                System.out.println("Passwords do not match.");
                Thread.sleep(sleepTime);

            } else {
                String salt = Cryptography.makeSalt();
                String hashedPass = Cryptography.toHashString(password, salt);

                user.setPassword(hashedPass, salt);
                System.out.println("Password successfully changed.");
                System.out.print("Press enter to continue..");
                inputValidation.scanner.nextLine();
                break;
            }
        }
    }

    private static void Register() throws InterruptedException, NoSuchAlgorithmException, InvalidKeySpecException {
        System.out.println("|-------------|");
        System.out.println("  Secret Note");
        System.out.println("    Register ");
        System.out.println("|-------------|");

        boolean is_running = true;
        String username = null;
        while (is_running) {

            boolean duplicate = false;

            username = inputValidation.checkStringNoSpaces("Enter your username: ");
            for (int i = 0; i < User.getUsersSize(); i++) {
                if (User.getUsersList().get(i).getName().equals(username)) {
                    System.out.println("This username already exists.");
                    Thread.sleep(sleepTime);
                    duplicate = true;
                    break;
                }
            }

            if (!duplicate) {
                is_running = false;
            }
        }

        while (true) {
            String password = inputValidation.checkStringNoSpaces("Enter your password: ");
            String passwordConfirm = inputValidation.checkStringNoSpaces("Enter your password again: ");

            if (!password.equals(passwordConfirm)) {

                System.out.println("Passwords do not match.");
                Thread.sleep(sleepTime);

            } else {
                String salt = Cryptography.makeSalt();
                String saltKey = Cryptography.makeSalt();
                String hashedPass = Cryptography.toHashString(password, salt);

                new User(username, hashedPass, salt, saltKey);
                System.out.println("Added new user " + username + ".");
                System.out.print("Press enter to continue..");
                inputValidation.scanner.nextLine();
                break;
            }
        }
    }
}
