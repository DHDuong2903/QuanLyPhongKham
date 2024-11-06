
package qlpk.entity;  

public class Doctor {  
    // Thuộc tính
    private String id;  
    private String name;  
    private int age;  
    private String specialty; 

    // Phương thức 
    public Doctor(String id, String name, int age,String specialty) {  
        this.id = id;  
        this.name = name;  
        this.age = age;    
        this.specialty = specialty;  
    }  

    public String getId() { return id; }  
    public String getName() { return name; }  
    public int getAge() { return age; }   
    public String getSpecialty() { return specialty; }    

    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }  
    public void setAge(int age) { this.age = age; }  
    public void setSpecialty(String specialty) { this.specialty = specialty; }  
}
