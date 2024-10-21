/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package qlpk.entity; 

import com.google.gson.Gson;  
import com.google.gson.reflect.TypeToken;  

import java.io.*;  
import java.lang.reflect.Type;  
import java.util.ArrayList;  
import java.util.List;  

public class PatientManager {  
    private List<Patient> patients;  
    private final String filePath = "resources/patients-data.json";  

    public PatientManager() {  
        patients = new ArrayList<>();  
        loadPatients();  
    }  

    public List<Patient> getPatients() {  
        return patients;  
    }  

    public void loadPatients() {  
        try (Reader reader = new FileReader(filePath)) {  
            Type patientListType = new TypeToken<ArrayList<Patient>>() {}.getType();  
            patients = new Gson().fromJson(reader, patientListType);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  

    public void savePatients() {  
        try (Writer writer = new FileWriter(filePath)) {  
            new Gson().toJson(patients, writer);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  

    public void addPatient(Patient patient) {  
        patients.add(patient);  
        savePatients();  
    }  

    public void updatePatient(int index, Patient patient) {  
        patients.set(index, patient);  
        savePatients();  
    }  

    public void deletePatient(int index) {  
        patients.remove(index);  
        savePatients();  
    }  
}
