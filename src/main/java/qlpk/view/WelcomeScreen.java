package qlpk.view;

import qlpk.app.Main;

import javax.swing.*;
import java.awt.*;

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
        patientButton.addActionListener(e -> {
            mainFrame.showPatientPanel();  // Gọi phương thức hiển thị DoctorPanel
            this.setVisible(false);
        });

        // ActionListener cho nút "QL Bác sĩ"
        doctorButton.addActionListener(e -> {
            mainFrame.showDoctorPanel();  // Gọi phương thức hiển thị DoctorPanel
            this.setVisible(false);       // Ẩn WelcomeScreen thay vì dispose()
        });

        // ActionListener cho nút "QL Phòng khám"
        clinicButton.addActionListener(e -> {
            mainFrame.showRoomPanel();  // Gọi phương thức hiển thị RoomPanel
            this.setVisible(false);       // Ẩn WelcomeScreen thay vì dispose()
        });
    }
}
