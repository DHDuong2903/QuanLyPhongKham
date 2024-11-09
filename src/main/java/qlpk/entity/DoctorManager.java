package qlpk.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorManager {
    private List<Doctor> doctors;
    private final String fileName = "doctors-data.json";  // Tên file JSON (dùng cả trong và ngoài JAR)

    public DoctorManager() {
        doctors = new ArrayList<>();
        loadDoctors();
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void loadDoctors() {
        Path externalPath = Paths.get(fileName);

        try {
            if (Files.exists(externalPath)) {
                // Đọc từ file bên ngoài nếu tồn tại
                try (Reader reader = Files.newBufferedReader(externalPath)) {
                    Type doctorListType = new TypeToken<ArrayList<Doctor>>() {}.getType();
                    doctors = new Gson().fromJson(reader, doctorListType);
                }
            } else {
                // Đọc từ file trong classpath nếu file bên ngoài chưa được tạo
                try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
                    if (inputStream != null) {
                        try (Reader reader = new InputStreamReader(inputStream)) {
                            Type doctorListType = new TypeToken<ArrayList<Doctor>>() {}.getType();
                            doctors = new Gson().fromJson(reader, doctorListType);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveDoctors() {
        try (Writer writer = Files.newBufferedWriter(Paths.get(fileName))) {
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
