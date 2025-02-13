package com.joseph.Utils;

import com.joseph.*;

import java.io.File;
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

    private static final String HOME_PATH = System.getProperty("user.home") + File.separator + "Documents"
            + File.separator + "Secret Note";
    private static final String FILE_LOCATION = HOME_PATH + File.separator + "users.json";

    public static void saveUsers() {

        File directory = new File(HOME_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try (FileWriter writer = new FileWriter(FILE_LOCATION)) {

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

            try (FileReader reader = new FileReader(FILE_LOCATION)) {
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
