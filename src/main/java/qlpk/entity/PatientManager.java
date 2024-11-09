package qlpk.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class PatientManager {
    private List<Patient> patients;
    private final String fileName = "patients-data.json";  // Tên file JSON (dùng cả trong và ngoài JAR)

    public PatientManager() {
        patients = new ArrayList<>();
        loadPatients();
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void loadPatients() {
        Path externalPath = Paths.get(fileName);
        
        try {
            if (Files.exists(externalPath)) {
                // Đọc từ file bên ngoài nếu tồn tại
                try (Reader reader = Files.newBufferedReader(externalPath)) {
                    Type patientListType = new TypeToken<ArrayList<Patient>>() {}.getType();
                    patients = new Gson().fromJson(reader, patientListType);
                }
            } else {
                // Đọc từ file trong classpath nếu file bên ngoài chưa được tạo
                try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
                    if (inputStream != null) {
                        try (Reader reader = new InputStreamReader(inputStream)) {
                            Type patientListType = new TypeToken<ArrayList<Patient>>() {}.getType();
                            patients = new Gson().fromJson(reader, patientListType);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void savePatients() {
        try (Writer writer = Files.newBufferedWriter(Paths.get(fileName))) {
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

    public List<Integer> getBookedRooms() {
        List<Integer> bookedRooms = new ArrayList<>();
        for (Patient patient : patients) {
            try {
                int room = Integer.parseInt(patient.getClinicRoom());
                if (!bookedRooms.contains(room)) {
                    bookedRooms.add(room);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return bookedRooms;
    }
}
