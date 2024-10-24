package qlpk.app;

import qlpk.view.WelcomeScreen;
import qlpk.view.PatientPanel;
import qlpk.view.DoctorPanel;
import qlpk.view.RoomPanel;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    public Main() {
        setTitle("Quản lý phòng tư vấn khám bệnh");
        setSize(800, 600);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Mở rộng toàn màn hình
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Ban đầu sẽ ẩn PatientPanel, chỉ hiện sau khi nhấn nút QL Bệnh nhân
        PatientPanel patientPanel = new PatientPanel(this);
        add(patientPanel, BorderLayout.CENTER);
    }

    // Phương thức đăng nhập
    private static boolean authenticateUser() {
        String correctUsername = "admin";
        String correctPassword = "admin";

        String username;
        char[] password;

        while (true) {
            username = JOptionPane.showInputDialog(null, "Nhập Username:");

            if (username == null) {
                // Người dùng bấm Cancel
                return false;
            }

            JPasswordField passwordField = new JPasswordField();
            int option = JOptionPane.showConfirmDialog(null, passwordField, "Nhập Password:", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                password = passwordField.getPassword();
            } else {
                // Người dùng bấm Cancel
                return false;
            }

            String passwordString = new String(password);

            if (username.equals(correctUsername) && passwordString.equals(correctPassword)) {
                JOptionPane.showMessageDialog(null, "Đăng nhập thành công!");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Sai tên hoặc mật khẩu.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public void showWelcomeScreen() {
        WelcomeScreen welcomeScreen = new WelcomeScreen(this);  // Tạo WelcomeScreen mới
        setContentPane(welcomeScreen);  // Thay đổi nội dung chính thành JPanel
        revalidate();  // Cập nhật lại layout
        repaint();  // Vẽ lại giao diện
    }

    public void showPatientPanel() {
        PatientPanel patientPanel = new PatientPanel(this); // Truyền Main vào PatientPanel
        setContentPane(patientPanel);  // Thay đổi nội dung chính của frame thành PatientPanel
        revalidate();  // Cập nhật lại layout
        repaint();     // Vẽ lại giao diện
        setVisible(true);  // Hiển thị lại frame
    }

    public void showDoctorPanel() {
        DoctorPanel doctorPanel = new DoctorPanel(this); // Truyền Main vào DoctorPanel
        setContentPane(doctorPanel);  // Thay đổi nội dung chính của frame thành DoctorPanel
        revalidate();  // Cập nhật lại layout
        repaint();     // Vẽ lại giao diện
        setVisible(true);  // Hiển thị lại frame
    }

    public void showRoomPanel() {
        RoomPanel roomPanel = new RoomPanel(this); // Truyền Main vào RoomPanel
        setContentPane(roomPanel);  // Thay đổi nội dung chính của frame thành RoomPanel
        revalidate();  // Cập nhật lại layout
        repaint();     // Vẽ lại giao diện
        setVisible(true);  // Hiển thị lại frame
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            if (authenticateUser()) { // Gọi hàm xác thực người dùng
                Main mainFrame = new Main();
                WelcomeScreen welcomeScreen = new WelcomeScreen(mainFrame);
                welcomeScreen.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Đăng nhập không thành công. Ứng dụng sẽ thoát.");
                System.exit(0); // Thoát chương trình nếu đăng nhập thất bại
            }
        });
    }
}
