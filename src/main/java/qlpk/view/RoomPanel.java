package qlpk.view;

import qlpk.app.Main;
import qlpk.entity.Room;
import qlpk.entity.RoomManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RoomPanel extends JPanel {
    private RoomManager roomManager;
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField roomNumberField, statusField;
    private Main mainFrame;

    public RoomPanel(Main mainFrame) {
        this.mainFrame = mainFrame;
        roomManager = new RoomManager();
        setLayout(new BorderLayout());

        // Tiêu đề và nút thoát
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton exitButton = new JButton("Thoát");

        exitButton.addActionListener(e -> exitToWelcomeScreen());

        topLeftPanel.add(exitButton);
        topPanel.add(topLeftPanel, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("Bảng quản lý phòng", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        topPanel.add(titleLabel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // Tạo bảng dữ liệu
        tableModel = new DefaultTableModel(new String[]{"Số phòng", "Trạng thái"}, 0);
        table = new JTable(tableModel);
        loadTableData();

        roomNumberField = new JTextField(10);
        statusField = new JTextField(10);

        JButton addButton = new JButton("Thêm");
        JButton editButton = new JButton("Sửa");
        JButton deleteButton = new JButton("Xóa");
        JButton clearButton = new JButton("Xóa trường nhập");
        JButton refreshButton = new JButton("Làm mới");
        JButton searchButton = new JButton("Tìm kiếm"); // Thêm nút tìm kiếm

        addButton.addActionListener(e -> addRoom());
        editButton.addActionListener(e -> editRoom());
        deleteButton.addActionListener(e -> deleteRoom());
        clearButton.addActionListener(e -> clearInputFields());
        refreshButton.addActionListener(e -> loadTableData());
        searchButton.addActionListener(e -> searchRoom()); // Thêm sự kiện tìm kiếm

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Số phòng:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(roomNumberField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Trạng thái:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(statusField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(searchButton);
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
        for (Room room : roomManager.getRooms()) {
            tableModel.addRow(new Object[]{
                    room.getRoomNumber(),
                    room.getStatus()
            });
        }
    }

    private void addRoom() {
        String roomNumber = roomNumberField.getText();
        String status = statusField.getText();

        if (roomNumber.isEmpty() || status.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Điền đầy đủ các trường.");
            return;
        }

        String id = String.valueOf(roomManager.getRooms().size() + 1);
        Room newRoom = new Room(id, roomNumber, status);
        roomManager.addRoom(newRoom);
        loadTableData();
        clearInputFields();
    }

    private void editRoom() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 dòng để sửa.");
            return;
        }

        String roomNumber = roomNumberField.getText();
        String status = statusField.getText();

        if (roomNumber.isEmpty() || status.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Điền đầy đủ các trường.");
            return;
        }

        Room updatedRoom = new Room(
                roomManager.getRooms().get(selectedRow).getId(),
                roomNumber,
                status
        );
        roomManager.updateRoom(selectedRow, updatedRoom);
        loadTableData();
        clearInputFields();
    }

    private void deleteRoom() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            roomManager.deleteRoom(selectedRow);
            loadTableData();
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 dòng để xóa.");
        }
    }

    private void clearInputFields() {
        roomNumberField.setText("");
        statusField.setText("");
    }

    private void searchRoom() {
        // Hiển thị hộp thoại để chọn loại tìm kiếm
        String[] options = {"Phòng còn trống", "Phòng đã đặt"};
        int choice = JOptionPane.showOptionDialog(this, "Chọn loại tìm kiếm:", "Tìm kiếm phòng",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (choice == 0) {
            // Tìm phòng còn trống
            searchByStatus("Còn trống");
        } else if (choice == 1) {
            // Tìm phòng đã đặt
            searchByStatus("Đã đặt");
        }
    }

    private void searchByStatus(String status) {
        // Xóa bảng hiện tại
        tableModel.setRowCount(0);

        // Lấy danh sách phòng và lọc theo trạng thái
        for (Room room : roomManager.getRooms()) {
            if (room.getStatus().equalsIgnoreCase(status)) {
                tableModel.addRow(new Object[]{
                        room.getRoomNumber(),
                        room.getStatus()
                });
            }
        }
    }
}
