package qlpk.entity;  

public class Room { 
    // Thuộc tính
    private String id;
    private String roomNumber;  
    private String status;

    // Phương thức  
    public Room(String id, String roomNumber, String status) {  
        this.id = id;
        this.roomNumber = roomNumber;  
        this.status = status;
    }  
  
    public String getId() { return id; }
    public String getRoomNumber() { return roomNumber; }  
    public String getStatus() { return status; }  
    
    public void setId(String id) { this.id = id; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    public void  setStatus(String status) { this.status = status; }
}
