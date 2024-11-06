
package qlpk.entity;  

public class Patient { 
    // Thuộc tính
    private String id;  
    private String name;  
    private String phone;  
    private String doctor;
    private String diseaseType;
    private String clinicRoom;  
    private String time;  
    
    // Phương thức
    public Patient(String id, String name, String phone, String doctor,String diseaseType, String clinicRoom, String time) {  
        this.id = id;  
        this.name = name;  
        this.phone = phone;  
        this.doctor = doctor;  
        this.diseaseType = diseaseType;
        this.clinicRoom = clinicRoom;  
        this.time = time;  
    }  
  
    public String getId() { return id; }  
    public String getName() { return name; }  
    public String getPhone() { return phone; }  
    public String getDoctor() { return doctor; } 
    public String getDiseaseType() { return diseaseType; }
    public String getClinicRoom() { return clinicRoom; }  
    public String getTime() { return time; }  

    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }  
    public void setPhone(String phone) { this.phone = phone; }  
    public void setDoctor(String doctor) { this.doctor = doctor; }
    public void setDiseaseType(String diseaseType) { this.diseaseType = diseaseType; }
    public void setClinicRoom(String clinicRoom) { this.clinicRoom = clinicRoom; }  
    public void setTime(String time) { this.time = time; }  
}
