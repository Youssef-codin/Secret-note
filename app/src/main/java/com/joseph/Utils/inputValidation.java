package com.joseph.Utils;

import java.util.InputMismatchException;
import java.util.Scanner;

import static com.joseph.Main.sleepTime;

public abstract class inputValidation {

    public static Scanner scanner = new Scanner(System.in);

    public static String checkString(String prompt) throws InterruptedException {

        while (true) {
            System.out.print(prompt);
            String in = scanner.nextLine();
            if (in.isEmpty()) {
                System.out.println("String can't be empty.");
                Thread.sleep(sleepTime);
            } else {
                return in;
            }
        }
    }

    public static String checkStringNoSpaces(String prompt) throws InterruptedException {

        while (true) {
            System.out.print(prompt);
            String in = scanner.nextLine();
            if (in.isEmpty() || in.contains(" ")) {
                System.out.println("String can't be empty or contain spaces.");
                Thread.sleep(sleepTime);
            } else {
                return in;
            }
        }
    }

    public static int checkInt(String prompt) throws InterruptedException {

        while (true) {
            try {
                System.out.print(prompt);
                int in = scanner.nextInt();
                return in;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a number.");
                Thread.sleep(sleepTime);
            } finally {
                scanner.nextLine();
            }
        }
    }

    public static boolean checkBool(String prompt) throws InterruptedException {

        String choice = checkString(prompt);

        if (choice.startsWith("y")) {
            return true;
        } else {
            return false;
        }
    }
}
