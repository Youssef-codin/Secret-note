package com.joseph;

import java.util.ArrayList;

public class User {

    private static ArrayList<User> users = new ArrayList<>();

    private String username;
    private String password;
    private String salt;
    private String saltKey;
    private SecretNote secretNote;

    public User(String username, String password, String salt, String saltKey) {

        this.username = username;
        this.password = password;
        this.salt = salt;
        this.saltKey = saltKey;
        addUser(this);
    }

    @Override
    public String toString() {
        return username;
    }

    // getters
    public static void printUsers() {

        for (int i = 0; i < users.size(); i++) {
            System.out.println(" " + (i + 1) + ") " + users.get(i));
        }
    }

    public static ArrayList<User> getUsersList() {

        return users;

    }

    public static ArrayList<String> getUserNames() {

        ArrayList<String> names = new ArrayList<>();
        for (User user : users) {
            names.add(user.username);
        }
        return names;
    }

    public String getName() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getSalt() {
        return this.salt;
    }

    public String getSaltKey() {
        return this.saltKey;
    }

    public static int getUsersSize() {

        return users.size();

    }

    public static User getUser(int i) {

        return users.get(i);

    }

    public SecretNote getNote() {

        return this.secretNote;

    }

    // setters
    public void setSecretNote(String title, String note) {

        this.secretNote = new SecretNote(title, note);

    }

    public void addUser(User user) {

        users.add(user);

    }

    public static void setUsers(ArrayList<User> newUsers) {

        users = newUsers;

    }

    public void deleteNote() {
        this.secretNote = null;
    }

    public void setPassword(String hashPass, String salt) {
        this.password = hashPass;
        this.salt = salt;
    }
}
