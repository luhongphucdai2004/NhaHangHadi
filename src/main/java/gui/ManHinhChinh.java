package gui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.net.URL; // <-- THÊM IMPORT NÀY

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class ManHinhChinh {

    private JFrame frame;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Sử dụng Look and Feel của hệ thống cho đẹp hơn
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Throwable e) {
                    e.printStackTrace();
                }
                
                try {
                    ManHinhChinh window = new ManHinhChinh();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public ManHinhChinh() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Hệ thống Quản lý Đặt bàn - Nhà hàng Hadi");
        frame.setBounds(100, 100, 1280, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Hiển thị giữa màn hình

        // --- THANH MENU ---
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        // SỬ DỤNG HÀM loadIcon AN TOÀN
        JMenu mnHeThong = new JMenu("Hệ thống");
        mnHeThong.setIcon(loadIcon("/img/menu/heThong.png"));
        menuBar.add(mnHeThong);

        JMenu mnDanhMuc = new JMenu("Danh mục");
        mnDanhMuc.setIcon(loadIcon("/img/menu/danhMuc.png"));
        menuBar.add(mnDanhMuc);

        JMenuItem mntmKhuVuc = new JMenuItem("Khu vực");
        mntmKhuVuc.setIcon(loadIcon("/img/menu/khuVuc.png"));
        mnDanhMuc.add(mntmKhuVuc);

        JMenuItem mntmBanAn = new JMenuItem("Bàn ăn");
        mntmBanAn.setIcon(loadIcon("/img/menu/table.png"));
        mnDanhMuc.add(mntmBanAn);

        JMenuItem mntmMonAn = new JMenuItem("Món ăn");
        mntmMonAn.setIcon(loadIcon("/img/menu/man.png"));
        mnDanhMuc.add(mntmMonAn);
        
        JMenuItem mntmKhachHang = new JMenuItem("Khách hàng");
        mntmKhachHang.setIcon(loadIcon("/img/menu/Customer.png"));
        mnDanhMuc.add(mntmKhachHang);
        
        JMenuItem mntmNhanVien = new JMenuItem("Nhân viên");
        mntmNhanVien.setIcon(loadIcon("/img/menu/nhanVien.png"));
        mnDanhMuc.add(mntmNhanVien);
        
        JMenuItem mntmTaiKhoan = new JMenuItem("Tài khoản");
        mntmTaiKhoan.setIcon(loadIcon("/img/menu/themKhachHang.png"));
        mnDanhMuc.add(mntmTaiKhoan);

        JMenu mnTimKiem = new JMenu("Tìm kiếm");
        mnTimKiem.setIcon(loadIcon("/img/menu/find.png"));
        menuBar.add(mnTimKiem);
        
        JMenuItem mntmKhuVuc2 = new JMenuItem("Khu vực");
        mntmKhuVuc2.setIcon(loadIcon("/img/menu/khuVuc.png"));
        mnTimKiem.add(mntmKhuVuc2);

        JMenuItem mntmBanAn2 = new JMenuItem("Bàn ăn");
        mntmBanAn2.setIcon(loadIcon("/img/menu/table.png"));
        mnTimKiem.add(mntmBanAn2);

        JMenuItem mntmMonAn2 = new JMenuItem("Món ăn");
        mntmMonAn2.setIcon(loadIcon("/img/menu/man.png"));
        mnTimKiem.add(mntmMonAn2);
        
        JMenuItem mntmKhachHang2 = new JMenuItem("Khách hàng");
        mntmKhachHang2.setIcon(loadIcon("/img/menu/Customer.png"));
        mnTimKiem.add(mntmKhachHang2);
        
        JMenuItem mntmNhanVien2 = new JMenuItem("Nhân viên");
        mntmNhanVien2.setIcon(loadIcon("/img/menu/nhanVien.png"));
        mnTimKiem.add(mntmNhanVien2);

        JMenu mnXuLy = new JMenu("Xử lý");
        mnXuLy.setIcon(loadIcon("/img/menu/xuLy.png"));
        menuBar.add(mnXuLy);
        
        JMenuItem mntmDatBan = new JMenuItem("Đặt bàn");
        mntmDatBan.setIcon(loadIcon("/img/menu/Bell.png"));
        mnXuLy.add(mntmDatBan);
        
        JMenuItem mntmThemMon = new JMenuItem("Thêm món");
        mntmThemMon.setIcon(loadIcon("/img/menu/datMon.png"));
        mnXuLy.add(mntmThemMon);
        
        JMenuItem mntmLapHoaDon = new JMenuItem("Lập hóa đơn");
        mntmLapHoaDon.setIcon(loadIcon("/img/menu/hoadon.png"));
        mnXuLy.add(mntmLapHoaDon);

        JMenu mnThongKe = new JMenu("Thống kê");
        mnThongKe.setIcon(loadIcon("/img/menu/thongKe.png"));
        menuBar.add(mnThongKe);
        
        JMenuItem mntmDoanhThu = new JMenuItem("Doanh thu");
        mntmDoanhThu.setIcon(loadIcon("/img/menu/doanhThu.png"));
        mnThongKe.add(mntmDoanhThu);
        
        JMenuItem mntmMon = new JMenuItem("Món ăn");
        // SỬA LỖI LOGIC: Gán icon cho mntmMon, không phải mntmDoanhThu
        mntmMon.setIcon(loadIcon("/img/menu/thongKeMonAn.png"));
        mnThongKe.add(mntmMon);
        
        

        // --- NỘI DUNG CHÍNH (VỚI ẢNH NỀN) ---
        // Sử dụng panel tùy chỉnh (lớp con) của chúng ta
        ImageIcon bgIcon = loadIcon("/img/bg/hadi-bg.png");
        BackgroundImagePanel contentPane = new BackgroundImagePanel(bgIcon);
        frame.setContentPane(contentPane);
        
        // Dùng GridBagLayout để căn giữa mọi thứ
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{1};
        gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0};
        gbl_contentPane.columnWeights = new double[]{1.0};
        gbl_contentPane.rowWeights = new double[]{0.3, 0.0, 0.0, 0.7}; // 30% lề trên, 70% lề dưới
        contentPane.setLayout(gbl_contentPane);

        // --- TIÊU ĐỀ ---
        JLabel lblTitle = new JLabel("HỆ THỐNG QUẢN LÝ ĐẶT BÀN");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 42));
        GridBagConstraints gbc_lblTitle = new GridBagConstraints();
        gbc_lblTitle.insets = new Insets(0, 0, 5, 0);
        gbc_lblTitle.gridx = 0;
        gbc_lblTitle.gridy = 0; // Hàng đầu tiên
        contentPane.add(lblTitle, gbc_lblTitle);

        JLabel lblSubtitle = new JLabel("Nhà hàng Hadi");
        lblSubtitle.setForeground(new Color(220, 220, 220)); // Màu trắng xám
        lblSubtitle.setFont(new Font("Tahoma", Font.ITALIC, 28));
        GridBagConstraints gbc_lblSubtitle = new GridBagConstraints();
        gbc_lblSubtitle.insets = new Insets(10, 0, 50, 0); // Cách xa tiêu đề
        gbc_lblSubtitle.gridx = 0;
        gbc_lblSubtitle.gridy = 1; // Hàng thứ hai
        contentPane.add(lblSubtitle, gbc_lblSubtitle);

        // --- PANEL CHỨA CÁC NÚT BẤM CHÍNH ---
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Quan trọng: Làm cho panel trong suốt
        FlowLayout flowLayout = (FlowLayout) buttonPanel.getLayout();
        flowLayout.setHgap(40); // Khoảng cách ngang giữa các nút
        flowLayout.setVgap(20);
        
        GridBagConstraints gbc_buttonPanel = new GridBagConstraints();
        gbc_buttonPanel.gridx = 0;
        gbc_buttonPanel.gridy = 2; // Hàng thứ ba
        contentPane.add(buttonPanel, gbc_buttonPanel);

        // --- CÁC NÚT BẤM CHÍNH ---
        JButton btnKhuVuc = new JButton("Khu vực");
        btnKhuVuc.setPreferredSize(new Dimension(160, 140));
        btnKhuVuc.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnKhuVuc.setHorizontalTextPosition(SwingConstants.CENTER);
        btnKhuVuc.setFont(new Font("Tahoma", Font.BOLD, 18));
        btnKhuVuc.setIcon(loadIcon("/img/menu/khuVuc.png"));
        btnKhuVuc.setBackground(new Color(255, 255, 255, 180)); // Trắng hơi trong suốt
        buttonPanel.add(btnKhuVuc);

        JButton btnBanAn = new JButton("Bàn ăn");
        btnBanAn.setPreferredSize(new Dimension(160, 140));
        btnBanAn.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnBanAn.setHorizontalTextPosition(SwingConstants.CENTER);
        btnBanAn.setFont(new Font("Tahoma", Font.BOLD, 18));
        btnBanAn.setIcon(loadIcon("/img/menu/table.png"));
        btnBanAn.setBackground(new Color(255, 255, 255, 180));
        buttonPanel.add(btnBanAn);

        JButton btnMonAn = new JButton("Món ăn");
        btnMonAn.setPreferredSize(new Dimension(160, 140));
        btnMonAn.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnMonAn.setHorizontalTextPosition(SwingConstants.CENTER);
        btnMonAn.setFont(new Font("Tahoma", Font.BOLD, 18));
        btnMonAn.setIcon(loadIcon("/img/menu/man.png"));
        btnMonAn.setBackground(new Color(255, 255, 255, 180));
        buttonPanel.add(btnMonAn);

        JButton btnKhachHang = new JButton("Khách hàng");
        btnKhachHang.setPreferredSize(new Dimension(160, 140));
        btnKhachHang.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnKhachHang.setHorizontalTextPosition(SwingConstants.CENTER);
        btnKhachHang.setFont(new Font("Tahoma", Font.BOLD, 18));
        btnKhachHang.setIcon(loadIcon("/img/menu/Customer.png"));
        btnKhachHang.setBackground(new Color(255, 255, 255, 180));
        buttonPanel.add(btnKhachHang);
    }
    
    
    /**
     * LỚP CON (INNER CLASS) ĐỂ VẼ ẢNH NỀN
     * Một JPanel tùy chỉnh có khả năng vẽ một ảnh nền
     * Ảnh sẽ được co giãn để vừa với kích thước của panel.
     */
    class BackgroundImagePanel extends JPanel {

        private Image backgroundImage;

        /**
         * Constructor mặc định (cần cho WindowBuilder).
         */
        public BackgroundImagePanel() {
            super();
        }

        /**
         * Constructor để truyền ảnh vào.
         * @param image
         */
        public BackgroundImagePanel(Image image) {
            super();
            this.backgroundImage = image;
        }
        
        /**
         * Constructor để truyền ImageIcon vào.
         * @param icon
         */
        public BackgroundImagePanel(ImageIcon icon) {
            super();
            if (icon != null) {
                this.backgroundImage = icon.getImage();
            }
        }

        /**
         * Thiết lập ảnh nền
         * @param image
         */
        public void setBackgroundImage(Image image) {
            this.backgroundImage = image;
            this.repaint(); // Vẽ lại panel khi có ảnh mới
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Vẽ ảnh nền
            if (backgroundImage != null) {
                // Vẽ ảnh sao cho nó vừa với kích thước của panel
                g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        }
    }
    
    // --- HÀM TRỢ GIÚP MỚI ĐỂ TẢI ICON AN TOÀN ---
    /**
     * Tải một ImageIcon từ đường dẫn resource.
     * Trả về null nếu không tìm thấy resource thay vì ném NullPointerException.
     * @param path Đường dẫn đến resource (ví dụ: "/img/menu/icon.png")
     * @return ImageIcon hoặc null
     */
    private ImageIcon loadIcon(String path) {
        URL imgURL = ManHinhChinh.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            // In ra lỗi để dễ dàng gỡ rối
            System.err.println("Couldn't find file: " + path);
            return null; // Trả về null, không bị crash
        }
    }
}