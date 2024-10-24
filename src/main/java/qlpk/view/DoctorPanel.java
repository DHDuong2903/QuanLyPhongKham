package qlpk.view;

import qlpk.app.Main;
import qlpk.entity.Doctor;
import qlpk.entity.DoctorManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DoctorPanel extends JPanel {
    private DoctorManager doctorManager;
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField nameField, ageField, specialtyField;
    private Main mainFrame;
    
    public DoctorPanel(Main mainFrame) {
        this.mainFrame = mainFrame;
        doctorManager = new DoctorManager();
        setLayout(new BorderLayout());

        // Tiêu đề và nút thoát
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton exitButton = new JButton("Thoát");
        
        exitButton.addActionListener(e -> exitToWelcomeScreen());
        
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

        JButton addButton = new JButton("Thêm");
        JButton editButton = new JButton("Sửa");
        JButton deleteButton = new JButton("Xóa");
        JButton clearButton = new JButton("Xóa trường nhập");
        JButton refreshButton = new JButton("Làm mới");

        addButton.addActionListener(e -> addDoctor());
        editButton.addActionListener(e -> editDoctor());
        deleteButton.addActionListener(e -> deleteDoctor());
        clearButton.addActionListener(e -> clearInputFields());
        refreshButton.addActionListener(e -> loadTableData());

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
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(refreshButton);
        inputPanel.add(buttonPanel, gbc);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(inputPanel, BorderLayout.WEST);
    }
    
    private void exitToWelcomeScreen() {
        // Đóng giao diện hiện tại
        mainFrame.dispose();

        // Mở lại màn hình chào mừng
        WelcomeScreen welcomeScreen = new WelcomeScreen(mainFrame);
        welcomeScreen.setVisible(true);
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
            String id = String.valueOf(doctorManager.getDoctors().size() + 1);
            Doctor newDoctor = new Doctor(id, name, age, specialty);
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
            loadTableData();
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 dòng để xóa.");
        }
    }

    private void clearInputFields() {
        nameField.setText("");
        ageField.setText("");
        specialtyField.setText("");
    }
}
