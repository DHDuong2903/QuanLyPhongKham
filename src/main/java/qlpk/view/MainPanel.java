package qlpk.view;

import qlpk.app.App;
import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    private App mainFrame;

    public MainPanel(App mainFrame) {
        this.mainFrame = mainFrame;

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
        patientButton.addActionListener(e -> mainFrame.showPatientPanel());

        // ActionListener cho nút "QL Bác sĩ"
        doctorButton.addActionListener(e -> mainFrame.showDoctorPanel());

        // ActionListener cho nút "QL Phòng khám"
        clinicButton.addActionListener(e -> mainFrame.showRoomPanel());
    }
}
