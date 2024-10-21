package qlpk.entity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeScreen extends JFrame {
    private Main mainFrame;

    public WelcomeScreen(Main mainFrame) {
        this.mainFrame = mainFrame;
        
        setTitle("Chào mừng");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Chào mừng đến với hệ thống", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(welcomeLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        JButton patientButton = new JButton("QL Bệnh nhân");
        JButton doctorButton = new JButton("QL Bác sĩ");
        JButton clinicButton = new JButton("QL Phòng khám");

        buttonPanel.add(patientButton);
        buttonPanel.add(doctorButton);
        buttonPanel.add(clinicButton);
        add(buttonPanel, BorderLayout.CENTER);

        // ActionListener cho nút "QL Bệnh nhân"
        patientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setVisible(true); // Hiện giao diện quản lý bệnh nhân
                dispose(); // Đóng giao diện chào mừng
            }
        });

        // ActionListener cho nút "QL Bác sĩ" (tạm thời không có chức năng)
        doctorButton.addActionListener(e -> {
            // Bạn có thể thêm mã cho giao diện quản lý bác sĩ ở đây
            JOptionPane.showMessageDialog(this, "Chức năng QL Bác sĩ chưa được triển khai.");
        });

        // ActionListener cho nút "QL Phòng khám" (tạm thời không có chức năng)
        clinicButton.addActionListener(e -> {
            // Bạn có thể thêm mã cho giao diện quản lý phòng khám ở đây
            JOptionPane.showMessageDialog(this, "Chức năng QL Phòng khám chưa được triển khai.");
        });
    }
}
