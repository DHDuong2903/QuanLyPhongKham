package qlpk.view;

import qlpk.app.App;
import qlpk.entity.Patient;
import qlpk.entity.PatientManager;
import qlpk.entity.User;
import qlpk.entity.UserManager;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Comparator;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

public class PatientPanel extends JPanel {
    private App mainFrame;
    private PatientManager patientManager;
    private UserManager userManager;
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField nameField, phoneField, doctorField, diseaseTypeField;
    private JDateChooser timeChooser;
    private JComboBox<Integer> clinicRoomComboBox;
    private Set<Integer> usedRooms;
    private JButton deleteButtonLowerTable;

    
    // Thuộc tính cho bảng mới để hiển thị dữ liệu `User`
    private DefaultTableModel userDataTableModel;
    private JTable userDataTable;

    public PatientPanel(App mainFrame) {
        this.mainFrame = mainFrame;
        patientManager = new PatientManager();
        userManager = new UserManager();
        usedRooms = new HashSet<>(patientManager.getBookedRooms());
        setLayout(new BorderLayout());

        initializeComponents();
    }

    private void initializeComponents() {
        JPanel topPanel = createTopPanel();
        add(topPanel, BorderLayout.NORTH);

        // Create patient data table
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Phone", "Doctor", "Disease Type", "Clinic Room", "Time"}, 0);
        table = new JTable(tableModel);
        loadTableData();

        // Create user data table
        userDataTableModel = new DefaultTableModel(new String[]{"Name", "Age", "Address", "Phone", "Disease Type", "Appointment Time"}, 0);
        userDataTable = new JTable(userDataTableModel);
        loadUserDataTable();

        // Create panel to contain both tables
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(new JScrollPane(table), BorderLayout.NORTH);

        // Add title and table for "Danh sách đăng ký khám"
        JPanel userTablePanel = new JPanel(new BorderLayout());

        // Title label
        JLabel userTableTitle = new JLabel("Danh sách đăng ký khám", JLabel.CENTER);
        userTableTitle.setFont(new Font("Arial", Font.BOLD, 16));
        userTablePanel.add(userTableTitle, BorderLayout.NORTH);

        // Ensure the userDataTable appears below the title
        JScrollPane userTableScrollPane = new JScrollPane(userDataTable);
        userTablePanel.add(userTableScrollPane, BorderLayout.CENTER);
        
        // Initialize delete button for the lower table
        deleteButtonLowerTable = new JButton("Xóa (Danh sách đăng ký khám)");
        deleteButtonLowerTable.addActionListener(e -> {
            int selectedRow = userDataTable.getSelectedRow();
            if (selectedRow != -1) {
                userDataTableModel.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một hàng để xóa.");
            }
        });
        
        // Add the delete button to the bottom of the userTablePanel
        userTablePanel.add(deleteButtonLowerTable, BorderLayout.SOUTH);
        
        // Add userTablePanel to tablePanel
        tablePanel.add(userTablePanel, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.CENTER);

        JPanel inputPanel = createInputPanel();
        add(inputPanel, BorderLayout.WEST);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton exitButton = new JButton("Thoát");
        exitButton.addActionListener(e -> exitToMainPanel());
        topLeftPanel.add(exitButton);

        topPanel.add(topLeftPanel, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("Bảng quản lý bệnh nhân", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        topPanel.add(titleLabel, BorderLayout.CENTER);

        return topPanel;
    }

    private JPanel createInputPanel() {
        nameField = new JTextField(10);
        phoneField = new JTextField(10);
        doctorField = new JTextField(10);
        diseaseTypeField = new JTextField(10);
        
        timeChooser = new JDateChooser();
        timeChooser.setDateFormatString("dd/MM/yyyy HH:mm");

        clinicRoomComboBox = new JComboBox<>();
        updateClinicRoomComboBox();

        JButton addButton = new JButton("Thêm");
        JButton editButton = new JButton("Sửa");
        JButton deleteButton = new JButton("Xóa");
        JButton clearButton = new JButton("Xóa trường nhập");
        JButton searchByDiseaseTypeButton = new JButton("Tìm theo tên bệnh");
        JButton sortByTimeButton = new JButton("Sắp xếp theo thời gian");
        JButton refreshButton = new JButton("Làm mới");
        
        addButton.addActionListener(e -> addPatient());
        editButton.addActionListener(e -> editPatient());
        deleteButton.addActionListener(e -> deletePatient());
        clearButton.addActionListener(e -> clearInputFields());
        searchByDiseaseTypeButton.addActionListener(e -> searchByDiseaseType());
        sortByTimeButton.addActionListener(e -> sortByTime());
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
        inputPanel.add(clinicRoomComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        inputPanel.add(new JLabel("Time:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(timeChooser, gbc);

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
        buttonPanel.add(sortByTimeButton);
        buttonPanel.add(refreshButton);
        inputPanel.add(buttonPanel, gbc);

        return inputPanel;
    }
    
    // Phương thức không tham số, sẽ gọi phương thức có tham số với `null`
    private void updateClinicRoomComboBox() {
        updateClinicRoomComboBox(null);
    }
    
    // Phương thức có tham số để bỏ qua phòng cụ thể khi cần thiết
    private void updateClinicRoomComboBox(Integer ignoreRoom) {
        clinicRoomComboBox.removeAllItems();
        for (int i = 1; i <= 10; i++) {
            if (!usedRooms.contains(i) || (ignoreRoom != null && ignoreRoom == i)) {
                clinicRoomComboBox.addItem(i);
            }
        }
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

    private void loadUserDataTable() {
        userDataTableModel.setRowCount(0);
        List<User> users = userManager.getUsers();
        for (User user : users) {
            userDataTableModel.addRow(new Object[]{
                user.getName(),
                user.getAge(),
                user.getAddress(),
                user.getPhone(),
                user.getDiseaseType(),
                user.getAppointmentTime()
            });
        }
    }

    private void addPatient() {
        // Mã hiện tại  
        String name = nameField.getText();  
        String phone = phoneField.getText();  
        String doctor = doctorField.getText();  
        int clinicRoom = (Integer) clinicRoomComboBox.getSelectedItem();   
        String diseaseType = diseaseTypeField.getText();  

        String time = ((JTextField) timeChooser.getDateEditor().getUiComponent()).getText();

        if (name.isEmpty() || phone.isEmpty() || doctor.isEmpty() || time.isEmpty() || diseaseType.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Điền đầy đủ các trường.");
            return;
        }

        // Tính toán ID mới dựa trên ID của bệnh nhân hiện tại  
        int newId = patientManager.getPatients().stream()  
                        .mapToInt(p -> Integer.parseInt(p.getId()))  
                        .max().orElse(0) + 1;  

        Patient newPatient = new Patient(String.valueOf(newId), name, phone, doctor, diseaseType, String.valueOf(clinicRoom), time);  
        patientManager.addPatient(newPatient);  
        usedRooms.add(clinicRoom);  
        loadTableData();  
        updateClinicRoomComboBox();  
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
        int clinicRoom = (Integer) clinicRoomComboBox.getSelectedItem();
        String diseaseType = diseaseTypeField.getText();

        String time = ((JTextField) timeChooser.getDateEditor().getUiComponent()).getText();
        
        if (name.isEmpty() || phone.isEmpty() || doctor.isEmpty() || time.isEmpty() || diseaseType.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Điền đầy đủ các trường.");
            return;
        }

        Patient oldPatient = patientManager.getPatients().get(selectedRow);
        int previousRoom = Integer.parseInt(oldPatient.getClinicRoom());

        // Cập nhật thông tin bệnh nhân
        Patient updatedPatient = new Patient(
            oldPatient.getId(), name, phone, doctor, diseaseType, String.valueOf(clinicRoom), time
        );

        patientManager.updatePatient(selectedRow, updatedPatient);

        // Cập nhật danh sách phòng đã dùng
        usedRooms.remove(previousRoom);
        usedRooms.add(clinicRoom);

        loadTableData();
        clearInputFields();
        updateClinicRoomComboBox(null); // Reset lại danh sách phòng
    }

    private void deletePatient() {
        int selectedRow = table.getSelectedRow();  
        if (selectedRow != -1) {  
            int clinicRoom = Integer.parseInt((String) table.getValueAt(selectedRow, 5));  
            patientManager.deletePatient(selectedRow);  
            usedRooms.remove(clinicRoom);  

            // Gán lại ID sau khi xóa  
            reassignIds();  

            loadTableData();  
            updateClinicRoomComboBox();  
        } else {  
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 dòng để xóa.");  
        }  
    }
    
    private void reassignIds() {  
        List<Patient> patients = patientManager.getPatients();  
        for (int i = 0; i < patients.size(); i++) {  
            Patient patient = patients.get(i);  
            patient.setId(String.valueOf(i + 1)); // Gán lại ID bắt đầu từ 1  
        }  
    }  
    
    private void clearInputFields() {
        nameField.setText("");
        phoneField.setText("");
        doctorField.setText("");
        diseaseTypeField.setText("");
        timeChooser.setDate(null);
        clinicRoomComboBox.setSelectedIndex(0);
    }

    private void searchByDiseaseType() {
        String diseaseType = JOptionPane.showInputDialog(this, "Nhập tên bệnh:");
        if (diseaseType == null || diseaseType.isEmpty()) {
            return;
        }

        List<Patient> patientsWithDiseaseType = patientManager.getPatients().stream()
                .filter(patient -> patient.getDiseaseType().equalsIgnoreCase(diseaseType))
                .collect(Collectors.toList());

        tableModel.setRowCount(0);
        for (Patient patient : patientsWithDiseaseType) {
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

    private void sortByTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        List<Patient> sortedPatients = patientManager.getPatients().stream()
                .sorted(Comparator.comparing(patient -> LocalDateTime.parse(patient.getTime(), formatter)))
                .collect(Collectors.toList());

        patientManager.setPatients(sortedPatients); // Cập nhật danh sách bệnh nhân trong patientManager
        loadTableData(); // Làm mới dữ liệu bảng
    }

    private void exitToMainPanel() {
        mainFrame.showMainPanel();
    }
}
