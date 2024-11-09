package qlpk.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RoomManager {
    private List<Room> rooms;
    private final String fileName = "rooms-data.json";  // Tên file JSON (dùng cả trong và ngoài JAR)

    public RoomManager() {
        rooms = new ArrayList<>();
        loadRooms();
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void loadRooms() {
        Path externalPath = Paths.get(fileName);

        try {
            if (Files.exists(externalPath)) {
                // Đọc từ file bên ngoài nếu tồn tại
                try (Reader reader = Files.newBufferedReader(externalPath)) {
                    Type roomListType = new TypeToken<ArrayList<Room>>() {}.getType();
                    rooms = new Gson().fromJson(reader, roomListType);
                }
            } else {
                // Đọc từ file trong classpath nếu file bên ngoài chưa được tạo
                try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
                    if (inputStream != null) {
                        try (Reader reader = new InputStreamReader(inputStream)) {
                            Type roomListType = new TypeToken<ArrayList<Room>>() {}.getType();
                            rooms = new Gson().fromJson(reader, roomListType);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveRooms() {
        try (Writer writer = Files.newBufferedWriter(Paths.get(fileName))) {
            new Gson().toJson(rooms, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateRoomStatuses(Set<Integer> bookedRooms) {
        for (Room room : rooms) {
            if (bookedRooms.contains(Integer.parseInt(room.getRoomNumber()))) {
                room.setStatus("Đã đặt");
            } else {
                room.setStatus("Còn trống");
            }
        }
        saveRooms();
    }

    public void addRoom(Room room) {
        rooms.add(room);
        saveRooms();
    }

    public void updateRoom(int index, Room room) {
        rooms.set(index, room);
        saveRooms();
    }

    public void deleteRoom(int index) {
        rooms.remove(index);
        saveRooms();
    }
}
