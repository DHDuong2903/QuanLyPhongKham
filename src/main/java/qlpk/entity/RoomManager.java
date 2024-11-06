package qlpk.entity; 

import com.google.gson.Gson;  
import com.google.gson.reflect.TypeToken;  

import java.io.*;  
import java.lang.reflect.Type;  
import java.util.ArrayList;  
import java.util.List;  
import java.util.Set;

public class RoomManager {  
    private List<Room> rooms;  
    private final String filePath = "resources/rooms-data.json";  

    public RoomManager() {  
        rooms = new ArrayList<>();  
        loadRooms();  
    }  

    public List<Room> getRooms() {  
        return rooms;  
    }  

    public void loadRooms() {  
        try (Reader reader = new FileReader(filePath)) {  
            Type roomListType = new TypeToken<ArrayList<Room>>() {}.getType();  
            rooms = new Gson().fromJson(reader, roomListType);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  

    public void saveRooms() {  
        try (Writer writer = new FileWriter(filePath)) {  
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
