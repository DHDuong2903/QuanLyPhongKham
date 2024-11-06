package qlpk.view;

import qlpk.entity.User;
import qlpk.entity.UserManager;
// Các import khác
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import javax.swing.BorderFactory;
import com.toedter.calendar.JDateChooser; // This is for the JDateChooser if you're using an external library for date selection


public class UserPanel extends JPanel {
    private UserManager userManager;  // Quản lý người dùng

    public UserPanel() {
        userManager = new UserManager();  // Khởi tạo UserManager

        setLayout(new GridBagLayout());

        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel welcomeLabel = new JLabel("Form đăng ký thông tin khám bệnh", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        containerPanel.add(welcomeLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        formPanel.add(new JLabel("Tên:"));
        JTextField nameField = new JTextField(15);
        formPanel.add(nameField);

        formPanel.add(new JLabel("Tuổi:"));
        JTextField ageField = new JTextField(15);
        formPanel.add(ageField);

        formPanel.add(new JLabel("Địa chỉ:"));
        JTextField addressField = new JTextField(15);
        formPanel.add(addressField);

        formPanel.add(new JLabel("Số điện thoại:"));
        JTextField phoneField = new JTextField(15);
        formPanel.add(phoneField);

        formPanel.add(new JLabel("Loại bệnh muốn khám:"));
        JTextField diseaseField = new JTextField(15);
        formPanel.add(diseaseField);

        formPanel.add(new JLabel("Thời gian khám:"));
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd/MM/yyyy HH:mm");
        formPanel.add(dateChooser);

        containerPanel.add(formPanel, BorderLayout.CENTER);

        JButton submitButton = new JButton("Đăng ký");
        submitButton.setPreferredSize(new Dimension(80, 30));
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String ageStr = ageField.getText().trim();
                String address = addressField.getText().trim();
                String phone = phoneField.getText().trim();
                String disease = diseaseField.getText().trim();
                java.util.Date selectedDate = dateChooser.getDate();

                // Kiểm tra nếu có bất kỳ trường nào chưa điền
                if (name.isEmpty() || ageStr.isEmpty() || address.isEmpty() || phone.isEmpty() || disease.isEmpty() || selectedDate == null) {
                    JOptionPane.showMessageDialog(UserPanel.this, "Vui lòng điền đầy đủ thông tin.", "Thông báo", JOptionPane.WARNING_MESSAGE);
                } else {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    String formattedDate = formatter.format(selectedDate);

                    try {
                        int age = Integer.parseInt(ageStr); // Chuyển đổi chuỗi tuổi thành số nguyên
                        // Tạo đối tượng User và thêm vào UserManager
                        User user = new User(name, age, address, phone, disease, formattedDate);
                        userManager.addUser(user);  // Lưu vào file JSON

                        JOptionPane.showMessageDialog(UserPanel.this, "Thông tin đã được gửi:\nTên: " + name +
                                "\nTuổi: " + age + "\nĐịa chỉ: " + address + "\nSố điện thoại: " + phone +
                                "\nLoại bệnh: " + disease + "\nThời gian khám: " + formattedDate);

                        // Đặt lại các trường nhập liệu về mặc định trống
                        nameField.setText("");
                        ageField.setText("");
                        addressField.setText("");
                        phoneField.setText("");
                        diseaseField.setText("");
                        dateChooser.setDate(null);

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(UserPanel.this, "Tuổi phải là một số hợp lệ.", "Thông báo", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });



        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitButton);
        containerPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(containerPanel);
    }
}
