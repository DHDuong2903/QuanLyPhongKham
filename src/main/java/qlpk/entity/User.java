package qlpk.entity;


public class User {
    // Thuộc tính
    private String name;
    private int age;
    private String address;
    private String phone;
    private String diseaseType;
    private String appointmentTime;
    
    // Phương thức
    public User(String name, int age, String address, String phone, String diseaseType, String appointmentTime) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.phone = phone;
        this.diseaseType = diseaseType;
        this.appointmentTime = appointmentTime;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
    public String getDiseaseType() { return diseaseType; }
    public String getAppointmentTime() { return appointmentTime; }
    
    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setAddress(String address) { this.address = address; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setDiseaseType(String diseaseType) { this.diseaseType = diseaseType; }
    public void setAppointmentTime(String appointmentTime) { this.appointmentTime = appointmentTime; }
}

