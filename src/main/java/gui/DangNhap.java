package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

// Dùng JFrame vì đây là Swing (để WindowBuilder có thể đọc)
public class DangNhap extends JFrame {

    private JPanel contentPane;
    private JTextField txtMaNhanVien;
    private JPasswordField txtMatKhau;

    /**
     * Hàm main để chạy thử
     */
    public static void main(String[] args) {
        // Chạy trên luồng giao diện Swing
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    DangNhap frame = new DangNhap();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Hàm khởi tạo Frame
     */
    public DangNhap() {
        setTitle("Đăng nhập hệ thống");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Đưa ra giữa màn hình
        setResizable(false);

        // Panel chính
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0)); // Dùng BorderLayout

        // 1. Panel bên trái (Branding)
        JPanel leftPane = new JPanel();
        leftPane.setBackground(new Color(0, 123, 255)); // Màu xanh
        leftPane.setPreferredSize(new Dimension(338, 600));
        leftPane.setLayout(new BoxLayout(leftPane, BoxLayout.Y_AXIS)); // Dùng BoxLayout
        
        // Thêm khoảng đệm để đẩy chữ xuống
        leftPane.add(Box.createVerticalStrut(150)); 
        
        JLabel lblTitle = new JLabel("NHÀ HÀNG HADI");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT); // Căn giữa
        
        leftPane.add(lblTitle);
        contentPane.add(leftPane, BorderLayout.WEST); // Thêm vào bên trái

        // 2. Panel bên phải (Form đăng nhập)
        JPanel rightPane = new JPanel();
        rightPane.setBackground(Color.WHITE);
        rightPane.setPreferredSize(new Dimension(462, 600));
        contentPane.add(rightPane, BorderLayout.CENTER); // Thêm vào giữa
        
        // Dùng layout null để đặt vị trí tuyệt đối giống AnchorPane
        // WindowBuilder rất thích dùng layout này
        rightPane.setLayout(null); 

        JLabel lblLoginTitle = new JLabel("ĐĂNG NHẬP");
        lblLoginTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        // setBounds(x, y, width, height)
        lblLoginTitle.setBounds(165, 113, 150, 32); 
        rightPane.add(lblLoginTitle);

        JLabel lblUser = new JLabel("TÊN TÀI KHOẢN");
        lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblUser.setBounds(40, 212, 120, 20);
        rightPane.add(lblUser);

        txtMaNhanVien = new JTextField();
        txtMaNhanVien.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtMaNhanVien.setBounds(180, 209, 220, 26);
        rightPane.add(txtMaNhanVien);
        txtMaNhanVien.setColumns(10);

        JLabel lblPass = new JLabel("MẬT KHẨU");
        lblPass.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblPass.setBounds(40, 262, 120, 20);
        rightPane.add(lblPass);

        txtMatKhau = new JPasswordField();
        txtMatKhau.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtMatKhau.setBounds(180, 259, 220, 26);
        rightPane.add(txtMatKhau);
        
        JLabel lblForgot = new JLabel("Quên mật khẩu ?");
        lblForgot.setForeground(Color.BLUE);
        lblForgot.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblForgot.setBounds(315, 295, 100, 16);
        rightPane.add(lblForgot);

        JButton btnDangNhap = new JButton("ĐĂNG NHẬP");
        btnDangNhap.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnDangNhap.setBackground(new Color(0, 123, 255));
        btnDangNhap.setForeground(Color.WHITE);
        btnDangNhap.setBounds(90, 354, 255, 35);
        rightPane.add(btnDangNhap);

        // 3. Thêm sự kiện cho nút
        btnDangNhap.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Xử lý đăng nhập
                dangNhap();
            }
        });
    }

    /**
     * Xử lý logic đăng nhập
     */
    private void dangNhap() {
        String taiKhoan = txtMaNhanVien.getText();
        String matKhau = new String(txtMatKhau.getPassword());

        if (taiKhoan.equals("admin") && matKhau.equals("123")) {
            // Đăng nhập thành công
            JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");
            
            // Đóng cửa sổ đăng nhập
            this.setVisible(false);
            
            // Mở màn hình chờ
            showLoadingScreen();
            
            // Sau khi màn hình chờ xong, nó sẽ tự mở màn hình chính
            // (Xem code LoadingDialog)
            
        } else {
            // Đăng nhập thất bại
            JOptionPane.showMessageDialog(this, 
                "Tên tài khoản hoặc mật khẩu không chính xác!", 
                "Lỗi Đăng Nhập", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Mở màn hình chờ
     */
    private void showLoadingScreen() {
        // Tạo và hiển thị dialog chờ
        RunApplication loadingDialog = new RunApplication(this);
        
        // Tạo một "công nhân" SwingWorker để chạy tác vụ nền (tải dữ liệu)
        // Điều này giúp ProgressBar chạy mượt mà không làm đơ giao diện
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Giả lập tải dữ liệu trong 3 giây
                for (int i = 0; i <= 100; i++) {
                    loadingDialog.updateProgress(i, "Đang tải: " + i + "%");
                    Thread.sleep(30); // Ngủ 30ms
                }
                return null;
            }

            @Override
            protected void done() {
                // Sau khi doInBackground hoàn thành
                loadingDialog.closeDialog(); // Đóng dialog chờ
                
                // Mở màn hình chính (Giả lập)
                showMainApplication();
            }
        };

        worker.execute(); // Bắt đầu chạy worker
        loadingDialog.setVisible(true); // Hiển thị dialog chờ
    }
    
    private void showMainApplication() {
        // Gọi hàm main của lớp ManHinhChinh để khởi chạy
        // (Hàm main này đã bao gồm cả việc set LookAndFeel và setVisible)
        ManHinhChinh.main(null);
        
        // Đóng hẳn frame đăng nhập
        this.dispose(); 
    }
}