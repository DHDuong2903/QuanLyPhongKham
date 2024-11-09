package qlpk.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private List<User> users;
    private final String fileName = "users-data.json";  // Tên file JSON dùng cả trong và ngoài JAR

    public UserManager() {
        users = new ArrayList<>();
        loadUsers();
    }

    public List<User> getUsers() {
        return users;
    }

    public void loadUsers() {
        Path externalPath = Paths.get(fileName);

        try {
            if (Files.exists(externalPath)) {
                // Đọc từ file bên ngoài nếu tồn tại
                try (Reader reader = Files.newBufferedReader(externalPath)) {
                    Type userListType = new TypeToken<ArrayList<User>>() {}.getType();
                    users = new Gson().fromJson(reader, userListType);
                }
            } else {
                // Đọc từ file trong classpath nếu file bên ngoài chưa được tạo
                try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
                    if (inputStream != null) {
                        try (Reader reader = new InputStreamReader(inputStream)) {
                            Type userListType = new TypeToken<ArrayList<User>>() {}.getType();
                            users = new Gson().fromJson(reader, userListType);
                        }
                    }
                }
            }

            if (users == null) {
                users = new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            users = new ArrayList<>();
        }
    }

    public void saveUsers() {
        try (Writer writer = Files.newBufferedWriter(Paths.get(fileName))) {
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
