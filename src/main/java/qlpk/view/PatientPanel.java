package qlpk.view;

import qlpk.app.Main;
import qlpk.entity.Patient;
import qlpk.entity.PatientManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class PatientPanel extends JPanel {
    private Main mainFrame;
    private PatientManager patientManager;
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField nameField, phoneField, doctorField, diseaseTypeField, clinicRoomField, timeField;

    public PatientPanel(Main mainFrame) {
        this.mainFrame = mainFrame;
        patientManager = new PatientManager();
        setLayout(new BorderLayout());

        // Tạo JPanel để chứa cả nút "Thoát" và tiêu đề
        JPanel topPanel = new JPanel(new BorderLayout());

        // JPanel cho nút "Thoát" ở góc trên trái
        JPanel topLeftPanel = new JPanel();
        topLeftPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Căn trái
        JButton exitButton = new JButton("Thoát");
        exitButton.addActionListener(e -> exitToWelcomeScreen());
        topLeftPanel.add(exitButton); // Thêm nút "Thoát" vào panel

        // Thêm nút thoát vào topPanel
        topPanel.add(topLeftPanel, BorderLayout.WEST); 

        // Tạo và thêm tiêu đề vào giữa topPanel
        JLabel titleLabel = new JLabel("Bảng quản lý bệnh nhân", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        topPanel.add(titleLabel, BorderLayout.CENTER);

        // Thêm topPanel vào vị trí BorderLayout.NORTH của JPanel
        add(topPanel, BorderLayout.NORTH);

        // Tạo bảng dữ liệu
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Phone", "Doctor", "Disease Type", "Clinic Room", "Time"}, 0);
        table = new JTable(tableModel);
        loadTableData();

        nameField = new JTextField(10);
        phoneField = new JTextField(10);
        doctorField = new JTextField(10);
        diseaseTypeField = new JTextField(10);
        clinicRoomField = new JTextField(10);
        timeField = new JTextField(10);

        JButton addButton = new JButton("Thêm");
        JButton editButton = new JButton("Sửa");
        JButton deleteButton = new JButton("Xóa");
        JButton clearButton = new JButton("Xóa trường nhập");
        JButton searchByDiseaseTypeButton = new JButton("Tìm theo tên bệnh");
        JButton refreshButton = new JButton("Làm mới");

        addButton.addActionListener(e -> addPatient());
        editButton.addActionListener(e -> editPatient());
        deleteButton.addActionListener(e -> deletePatient());
        clearButton.addActionListener(e -> clearInputFields());
        searchByDiseaseTypeButton.addActionListener(e -> searchByDiseaseType());
        refreshButton.addActionListener(e -> loadTableData());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(nameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(phoneField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Doctor:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(doctorField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(new JLabel("Disease Type:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(diseaseTypeField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(new JLabel("Clinic Room:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(clinicRoomField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        inputPanel.add(new JLabel("Time:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(timeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(searchByDiseaseTypeButton);
        buttonPanel.add(refreshButton);
        inputPanel.add(buttonPanel, gbc);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(inputPanel, BorderLayout.WEST);
    }

    private void loadTableData() {
        tableModel.setRowCount(0);
        for (Patient patient : patientManager.getPatients()) {
            tableModel.addRow(new Object[]{
                    patient.getId(),
                    patient.getName(),
                    patient.getPhone(),
                    patient.getDoctor(),
                    patient.getDiseaseType(),
                    patient.getClinicRoom(),
                    patient.getTime()
            });
        }
    }

    private void addPatient() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String doctor = doctorField.getText();
        String clinicRoom = clinicRoomField.getText();
        String time = timeField.getText();
        String diseaseType = diseaseTypeField.getText();

        if (name.isEmpty() || phone.isEmpty() || doctor.isEmpty() ||
                clinicRoom.isEmpty() || time.isEmpty() || diseaseType.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Điền đầy đủ các trường.");
            return;
        }

        String id = String.valueOf(patientManager.getPatients().size() + 1);
        Patient newPatient = new Patient(id, name, phone, doctor, diseaseType, clinicRoom, time);
        patientManager.addPatient(newPatient);
        loadTableData();
        clearInputFields();
    }

    private void editPatient() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 dòng để sửa.");
            return;
        }

        String name = nameField.getText();
        String phone = phoneField.getText();
        String doctor = doctorField.getText();
        String clinicRoom = clinicRoomField.getText();
        String time = timeField.getText();
        String diseaseType = diseaseTypeField.getText();

        if (name.isEmpty() || phone.isEmpty() || doctor.isEmpty() ||
                clinicRoom.isEmpty() || time.isEmpty() || diseaseType.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Điền đầy đủ các trường.");
            return;
        }

        Patient updatedPatient = new Patient(
                patientManager.getPatients().get(selectedRow).getId(),
                name, phone, doctor, diseaseType, clinicRoom, time
        );

        patientManager.updatePatient(selectedRow, updatedPatient);
        loadTableData();
        clearInputFields();
    }

    private void deletePatient() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            patientManager.deletePatient(selectedRow);
            loadTableData();
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 dòng để xóa.");
        }
    }

    private void clearInputFields() {
        nameField.setText("");
        phoneField.setText("");
        doctorField.setText("");
        diseaseTypeField.setText("");
        clinicRoomField.setText("");
        timeField.setText("");
    }

    private void searchByDiseaseType() {
        String searchTerm = JOptionPane.showInputDialog(this, "Nhập tên bệnh cần tìm:");
        if (searchTerm == null || searchTerm.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên bệnh.");
            return;
        }

        List<Patient> filteredPatients = patientManager.getPatients().stream()
                .filter(patient -> patient.getDiseaseType().equalsIgnoreCase(searchTerm))
                .collect(Collectors.toList());

        if (filteredPatients.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy bệnh nhân với tên bệnh này.");
        } else {
            tableModel.setRowCount(0);
            for (Patient patient : filteredPatients) {
                tableModel.addRow(new Object[]{
                        patient.getId(),
                        patient.getName(),
                        patient.getPhone(),
                        patient.getDoctor(),
                        patient.getDiseaseType(),
                        patient.getClinicRoom(),
                        patient.getTime()
                });
            }
        }
    }

    private void exitToWelcomeScreen() {
        // Đóng giao diện hiện tại
        mainFrame.dispose();

        // Mở lại màn hình chào mừng
        WelcomeScreen welcomeScreen = new WelcomeScreen(mainFrame);
        welcomeScreen.setVisible(true);
    }

}
