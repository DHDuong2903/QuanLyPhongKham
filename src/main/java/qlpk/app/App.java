package qlpk.app;  

import qlpk.view.MainPanel;  
import qlpk.view.PatientPanel;  
import qlpk.view.DoctorPanel;  
import qlpk.view.RoomPanel;  
import qlpk.view.UserPanel;  

import javax.swing.*;  
import java.awt.*;  

public class App extends JFrame {  
    public App() {  
        setTitle("Quản lý phòng tư vấn khám bệnh");  
        setSize(800, 600);  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        setLocationRelativeTo(null);  
        setLayout(new BorderLayout());  

        // Ban đầu sẽ ẩn PatientPanel, chỉ hiện sau khi nhấn nút QL Bệnh nhân  
        PatientPanel patientPanel = new PatientPanel(this);  
        add(patientPanel, BorderLayout.CENTER);  
    }  

    // Phương thức xác thực người dùng  
    private static String authenticateUser() {  
        String adminUsername = "admin";  
        String adminPassword = "admin";  
        String userUsername = "user";  
        String userPassword = "user";  

        String username;  
        char[] password;  

        while (true) {  
            username = JOptionPane.showInputDialog(null, "Nhập Username:");  

            if (username == null) {  
                return null; // Nếu người dùng hủy bỏ, trả về null  
            }  

            JPasswordField passwordField = new JPasswordField();  
            int option = JOptionPane.showConfirmDialog(null, passwordField, "Nhập Password:", JOptionPane.OK_CANCEL_OPTION);  

            if (option == JOptionPane.OK_OPTION) {  
                password = passwordField.getPassword(); // Lấy mật khẩu từ trường nhập  
            } else {  
                return null; // Nếu người dùng hủy bỏ, trả về null  
            }  

            String passwordString = new String(password);  

            // Kiểm tra tên đăng nhập và mật khẩu cho người dùng  
            if (username.equals(userUsername) && passwordString.equals(userPassword)) {  
                JOptionPane.showMessageDialog(null, "Đăng nhập user thành công!");  
                return "user";  
            }  

            // Kiểm tra tên đăng nhập và mật khẩu cho admin  
            if (username.equals(adminUsername) && passwordString.equals(adminPassword)) {  
                JOptionPane.showMessageDialog(null, "Đăng nhập admin thành công!");  
                return "admin";  
            }  

            // Thông báo lỗi nếu tên đăng nhập hoặc mật khẩu sai  
            JOptionPane.showMessageDialog(null, "Sai tên hoặc mật khẩu.", "Error", JOptionPane.ERROR_MESSAGE);  
        }  
    }  
    
    public void showUserPanel() {  
        UserPanel userPanel = new UserPanel();  
        setContentPane(userPanel); // Thay đổi nội dung chính của frame thành UserPanel  
        revalidate(); // Cập nhật lại layout  
        repaint();    // Vẽ lại giao diện  
    }  
    
    public void showMainPanel() {  
        // Xóa panel hiện tại  
        getContentPane().removeAll();  
        setExtendedState(JFrame.NORMAL); // Trở về kích thước bình thường  
        // Thêm MainPanel vào JFrame  
        MainPanel mainPanel = new MainPanel(this);  
        setContentPane(mainPanel); // Thay đổi nội dung chính của frame thành MainPanel  
        // Cập nhật giao diện  
        revalidate(); // Cập nhật lại layout  
        repaint();    // Vẽ lại giao diện  
    }  

    public void showPatientPanel() {  
        PatientPanel patientPanel = new PatientPanel(this); // Truyền Main vào PatientPanel  
        setContentPane(patientPanel);  // Thay đổi nội dung chính của frame thành PatientPanel  
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Mở rộng toàn màn hình  
        revalidate();  // Cập nhật lại layout  
        repaint();     // Vẽ lại giao diện  
        setVisible(true);  // Hiển thị lại frame  
    }  

    public void showDoctorPanel() {  
        DoctorPanel doctorPanel = new DoctorPanel(this); // Truyền Main vào DoctorPanel  
        setContentPane(doctorPanel);  // Thay đổi nội dung chính của frame thành DoctorPanel  
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Mở rộng toàn màn hình  
        revalidate();  // Cập nhật lại layout  
        repaint();     // Vẽ lại giao diện  
        setVisible(true);  // Hiển thị lại frame  
    }  

    public void showRoomPanel() {  
        RoomPanel roomPanel = new RoomPanel(this); // Truyền Main vào RoomPanel  
        setContentPane(roomPanel);  // Thay đổi nội dung chính của frame thành RoomPanel  
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Mở rộng toàn màn hình  
        revalidate();  // Cập nhật lại layout  
        repaint();     // Vẽ lại giao diện  
        setVisible(true);  // Hiển thị lại frame  
    }  

    public static void main(String[] args) {  
        SwingUtilities.invokeLater(() -> {  
            String role = authenticateUser();  // Xác thực người dùng và nhận loại tài khoản  

            // Mở frame chính dựa trên vai trò người dùng  
            if ("admin".equals(role)) {  
                App mainFrame = new App();  
                mainFrame.showMainPanel();  
                mainFrame.setVisible(true);  
            } else if ("user".equals(role)) {  
                App mainFrame = new App();  
                mainFrame.showUserPanel();  
                mainFrame.setVisible(true);  
            } else {  
                JOptionPane.showMessageDialog(null, "Đăng nhập không thành công. Ứng dụng sẽ thoát.");  
                System.exit(0); // Thoát ứng dụng  
            }  
        });  
    }  
}