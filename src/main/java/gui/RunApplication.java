package gui;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;

// Dùng JDialog để làm cửa sổ chờ
public class RunApplication extends JDialog {

    private JProgressBar progressBar;
    private JLabel lblStatus;

    public RunApplication(JFrame owner) {
        // Gọi constructor của JDialog
        // true (modal) -> Nó sẽ khóa cửa sổ cha (cửa sổ đăng nhập)
        super(owner, "Đang xử lý", true); 
        
        setSize(400, 150);
        setLocationRelativeTo(owner); // Hiển thị giữa cửa sổ cha
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE); // Không cho tắt ngang
        setLayout(new BorderLayout(20, 20));
        
        // Panel chứa nội dung
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        lblStatus = new JLabel("Đang khởi tạo, vui lòng chờ...");
        lblStatus.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true); // Hiển thị %

        panel.add(lblStatus);
        panel.add(Box.createVerticalStrut(10)); // Khoảng cách
        panel.add(progressBar);
        
        add(panel, BorderLayout.CENTER);
    }

    /**
     * Cập nhật tiến trình từ bên ngoài
     */
    public void updateProgress(int value, String status) {
        // Cập nhật trên luồng giao diện Swing
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                progressBar.setValue(value);
                lblStatus.setText(status);
            }
        });
    }

    /**
     * Đóng dialog
     */
    public void closeDialog() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                setVisible(false);
                dispose();
            }
        });
    }
}