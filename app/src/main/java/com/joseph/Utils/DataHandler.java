package com.joseph.Utils;

import com.joseph.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;

public abstract class DataHandler {

    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void saveUsers() {

        try (FileWriter writer = new FileWriter("users.json")) {

            ArrayList<User> users = User.getUsersList();
            gson.toJson(users, writer);
            System.out.println("Users data has been saved!");

        } catch (IOException e) {
            System.out.println("Error 404 with saving users.");
        }
    }

    public static void loadUsers() {
        if (User.getUsersList() == null) {

            User.setUsers(new ArrayList<>());

        } else {

            try (FileReader reader = new FileReader("users.json")) {
                Type type = new TypeToken<ArrayList<User>>() {
                }.getType();
                User.setUsers(gson.fromJson(reader, type));
            } catch (FileNotFoundException e) {
                System.out.println("File not found.");
                User.setUsers(new ArrayList<>());
            } catch (IOException e) {
                System.out.println("Error with loading Users.");
                User.setUsers(new ArrayList<>());
            }
        }
    }
}
