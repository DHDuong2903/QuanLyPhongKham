
package qlpk.entity; 

import com.google.gson.Gson;  
import com.google.gson.reflect.TypeToken;  

import java.io.*;  
import java.lang.reflect.Type;  
import java.util.ArrayList;  
import java.util.List;  

public class DoctorManager {  
    private List<Doctor> doctors;  
    private final String filePath = "resources/doctors-data.json";  

    public DoctorManager() {  
        doctors = new ArrayList<>();  
        loadDoctors();  
    }  

    public List<Doctor> getDoctors() {  
        return doctors;  
    }  

    public void loadDoctors() {  
        try (Reader reader = new FileReader(filePath)) {  
            Type doctorListType = new TypeToken<ArrayList<Doctor>>() {}.getType();  
            doctors = new Gson().fromJson(reader, doctorListType);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  

    public void saveDoctors() {  
        try (Writer writer = new FileWriter(filePath)) {  
            new Gson().toJson(doctors, writer);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  

    public void addDoctor(Doctor doctor) {  
        doctors.add(doctor);  
        saveDoctors();  
    }  

    public void updateDoctor(int index, Doctor doctor) {  
        doctors.set(index, doctor);  
        saveDoctors();  
    }  

    public void deleteDoctor(int index) {  
        doctors.remove(index);  
        saveDoctors();  
    }  
}
