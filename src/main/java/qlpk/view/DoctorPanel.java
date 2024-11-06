package qlpk.view;

import qlpk.app.App;
import qlpk.entity.Doctor;
import qlpk.entity.DoctorManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class DoctorPanel extends JPanel {
    private DoctorManager doctorManager;
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField nameField, ageField, specialtyField, searchSpecialtyField;
    private App mainFrame;

    public DoctorPanel(App mainFrame) {
        this.mainFrame = mainFrame;
        doctorManager = new DoctorManager();
        setLayout(new BorderLayout());

        // Tiêu đề và nút thoát
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton exitButton = new JButton("Thoát");
        
        exitButton.addActionListener(e -> exitToMainPanel());
        
        topLeftPanel.add(exitButton);
        topPanel.add(topLeftPanel, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("Bảng quản lý bác sĩ", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        topPanel.add(titleLabel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // Tạo bảng dữ liệu
        tableModel = new DefaultTableModel(new String[]{"ID", "Tên", "Tuổi", "Chuyên khoa"}, 0);
        table = new JTable(tableModel);
        loadTableData();

        nameField = new JTextField(10);
        ageField = new JTextField(10);
        specialtyField = new JTextField(10);
        searchSpecialtyField = new JTextField(10);

        JButton addButton = new JButton("Thêm");
        JButton editButton = new JButton("Sửa");
        JButton deleteButton = new JButton("Xóa");
        JButton clearButton = new JButton("Xóa trường nhập");
        JButton refreshButton = new JButton("Làm mới");
        JButton searchButton = new JButton("Tìm kiếm theo khoa");

        addButton.addActionListener(e -> addDoctor());
        editButton.addActionListener(e -> editDoctor());
        deleteButton.addActionListener(e -> deleteDoctor());
        clearButton.addActionListener(e -> clearInputFields());
        refreshButton.addActionListener(e -> loadTableData());
        searchButton.addActionListener(e -> searchBySpecialty());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Tên:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(nameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Tuổi:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(ageField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Chuyên khoa:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(specialtyField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(new JLabel("Tìm chuyên khoa:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(searchSpecialtyField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(searchButton);
        inputPanel.add(buttonPanel, gbc);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(inputPanel, BorderLayout.WEST);
    }
    
    private void loadTableData() {
        tableModel.setRowCount(0);
        for (Doctor doctor : doctorManager.getDoctors()) {
            tableModel.addRow(new Object[]{
                    doctor.getId(),
                    doctor.getName(),
                    doctor.getAge(),
                    doctor.getSpecialty()
            });
        }
    }

    private void searchBySpecialty() {
        String specialty = searchSpecialtyField.getText().trim(); // Xóa khoảng trắng thừa
        if (specialty.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập chuyên khoa cần tìm.");
            return;
        }

        // Lọc danh sách bác sĩ theo chuyên khoa
        List<Doctor> filteredDoctors = doctorManager.getDoctors().stream()
                .filter(d -> d.getSpecialty().equalsIgnoreCase(specialty))
                .collect(Collectors.toList());

        // Xóa bảng dữ liệu hiện tại và hiển thị kết quả tìm kiếm
        tableModel.setRowCount(0);
        if (filteredDoctors.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy bác sĩ có chuyên khoa: " + specialty);
        } else {
            for (Doctor doctor : filteredDoctors) {
                tableModel.addRow(new Object[]{
                        doctor.getId(),
                        doctor.getName(),
                        doctor.getAge(),
                        doctor.getSpecialty()
                });
            }
        }
    }


    private void addDoctor() {  
        String name = nameField.getText();  
        String ageText = ageField.getText();  
        String specialty = specialtyField.getText();  

        if (name.isEmpty() || ageText.isEmpty() || specialty.isEmpty()) {  
            JOptionPane.showMessageDialog(this, "Điền đầy đủ các trường.");  
            return;  
        }  

        try {  
            int age = Integer.parseInt(ageText);  

            // Tính toán ID mới dựa trên ID của bác sĩ hiện tại  
            int newId = doctorManager.getDoctors().stream()  
                            .mapToInt(d -> Integer.parseInt(d.getId()))  
                            .max().orElse(0) + 1;  

            Doctor newDoctor = new Doctor(String.valueOf(newId), name, age, specialty);  
            doctorManager.addDoctor(newDoctor);  
            loadTableData();  
            clearInputFields();  
        } catch (NumberFormatException e) {  
            JOptionPane.showMessageDialog(this, "Tuổi phải là số.");  
        }  
    }  

    private void editDoctor() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 dòng để sửa.");
            return;
        }

        String name = nameField.getText();
        String ageText = ageField.getText();
        String specialty = specialtyField.getText();

        if (name.isEmpty() || ageText.isEmpty() || specialty.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Điền đầy đủ các trường.");
            return;
        }

        try {
            int age = Integer.parseInt(ageText);
            Doctor updatedDoctor = new Doctor(
                    doctorManager.getDoctors().get(selectedRow).getId(),
                    name, age, specialty
            );
            doctorManager.updateDoctor(selectedRow, updatedDoctor);
            loadTableData();
            clearInputFields();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Tuổi phải là số.");
        }
    }

    private void deleteDoctor() {  
        int selectedRow = table.getSelectedRow();  
        if (selectedRow != -1) {  
            doctorManager.deleteDoctor(selectedRow);  

            // Gán lại ID sau khi xóa  
            reassignIds();  

            loadTableData();  
        } else {  
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 dòng để xóa.");  
        }  
    }  

    private void reassignIds() {  
        List<Doctor> doctors = doctorManager.getDoctors();  
        for (int i = 0; i < doctors.size(); i++) {  
            Doctor doctor = doctors.get(i);  
            doctor.setId(String.valueOf(i + 1)); // Gán lại ID bắt đầu từ 1  
        }  
    }  

    private void clearInputFields() {
        nameField.setText("");
        ageField.setText("");
        specialtyField.setText("");
        searchSpecialtyField.setText("");
    }
    
    private void exitToMainPanel() {
        mainFrame.showMainPanel();
    }
}
