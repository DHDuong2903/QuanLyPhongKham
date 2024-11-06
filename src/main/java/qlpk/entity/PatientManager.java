package qlpk.entity;   

import com.google.gson.Gson;  
import com.google.gson.reflect.TypeToken;  

import java.io.*;  
import java.lang.reflect.Type;  
import java.util.ArrayList;  
import java.util.List;  

public class PatientManager {  
    // Danh sách chứa các đối tượng bệnh nhân  
    private List<Patient> patients;  
    // Đường dẫn đến file JSON chứa thông tin bệnh nhân  
    private final String filePath = "resources/patients-data.json";  

    // Constructor: Khởi tạo danh sách bệnh nhân và nạp dữ liệu từ file  
    public PatientManager() {  
        patients = new ArrayList<>();  
        loadPatients();  
    }  
    
    // Phương thức thiết lập danh sách bệnh nhân  
    public void setPatients(List<Patient> patients) {  
        this.patients = patients;  
    }  
    
    // Phương thức lấy danh sách bệnh nhân  
    public List<Patient> getPatients() {  
        return patients;  
    }  

    // Phương thức nạp dữ liệu bệnh nhân từ file JSON  
    public void loadPatients() {  
        try (Reader reader = new FileReader(filePath)) {  
            // Chỉ định loại dữ liệu mà chúng ta sẽ chuyển đổi từ JSON  
            Type patientListType = new TypeToken<ArrayList<Patient>>() {}.getType();  
            // Sử dụng Gson để chuyển đổi JSON thành danh sách các đối tượng Patient  
            patients = new Gson().fromJson(reader, patientListType);  
        } catch (IOException e) {  
            e.printStackTrace();  // In ra lỗi nếu có  
        }  
    }  

    // Phương thức lưu dữ liệu danh sách bệnh nhân vào file JSON  
    public void savePatients() {  
        try (Writer writer = new FileWriter(filePath)) {  
            // Sử dụng Gson để chuyển đổi danh sách bệnh nhân thành JSON và ghi vào file  
            new Gson().toJson(patients, writer);  
        } catch (IOException e) {  
            e.printStackTrace();  // In ra lỗi nếu có  
        }  
    }  

    // Phương thức thêm một bệnh nhân mới vào danh sách  
    public void addPatient(Patient patient) {  
        patients.add(patient);  // Thêm bệnh nhân vào danh sách  
        savePatients();  // Lưu lại danh sách sau khi thêm  
    }  

    // Phương thức cập nhật thông tin của một bệnh nhân theo chỉ số  
    public void updatePatient(int index, Patient patient) {  
        patients.set(index, patient);  // Cập nhật bệnh nhân ở vị trí index  
        savePatients();  // Lưu lại danh sách sau khi cập nhật  
    }  

    // Phương thức xóa một bệnh nhân khỏi danh sách theo chỉ số  
    public void deletePatient(int index) {  
        patients.remove(index);  // Xóa bệnh nhân ở vị trí index  
        savePatients();  // Lưu lại danh sách sau khi xóa  
    }  
    
    // Phương thức lấy danh sách các phòng đã đặt từ bệnh nhân  
    public List<Integer> getBookedRooms() {  
        List<Integer> bookedRooms = new ArrayList<>();  // Danh sách lưu trữ các phòng đã đặt  
        for (Patient patient : patients) {  // Lặp qua từng bệnh nhân trong danh sách  
            try {  
                // Lấy số phòng từ thông tin bệnh nhân  
                int room = Integer.parseInt(patient.getClinicRoom());  
                // Kiểm tra xem phòng đã được thêm vào danh sách chưa  
                if (!bookedRooms.contains(room)) {  
                    bookedRooms.add(room);  // Nếu chưa có, thêm vào danh sách  
                }  
            } catch (NumberFormatException e) {  
                e.printStackTrace();  // In ra lỗi nếu số phòng không hợp lệ  
            }  
        }  
        return bookedRooms;  // Trả về danh sách các phòng đã đặt  
    }  
}