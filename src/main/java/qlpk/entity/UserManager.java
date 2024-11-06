package qlpk.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private List<User> users;
    private final String filePath = "resources/users-data.json";

    public UserManager() {
        users = new ArrayList<>(); 
        loadUsers();
    }

    public List<User> getUsers() {
        return users;
    }

    public void loadUsers() {
        try (Reader reader = new FileReader(filePath)) {
            Type userListType = new TypeToken<ArrayList<User>>() {}.getType();
            users = new Gson().fromJson(reader, userListType);
            if (users == null) { 
                users = new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            users = new ArrayList<>();
        }
    }

    public void saveUsers() {
        try (Writer writer = new FileWriter(filePath)) {
            new Gson().toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addUser(User user) {
        users.add(user);
        saveUsers();  
    }
}
