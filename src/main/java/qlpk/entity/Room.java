package qlpk.entity;  

public class Room { 
    private String id;
    private String roomNumber;  
    private String status;

    // Constructor  
    public Room(String id, String roomNumber, String status) {  
        this.id = id;
        this.roomNumber = roomNumber;  
        this.status = status;
    }  

    // Getters and Setters  
    public String getId() { return id; }
    public String getRoomNumber() { return roomNumber; }  
    public String getStatus() { return status; }  
    
    public void setId(String id) { this.id = id; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    public void  setStatus(String status) { this.status = status; }
}
