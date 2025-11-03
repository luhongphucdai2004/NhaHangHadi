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
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane; 
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import dao.NhanVien_dao; 
import entity.NhanVien; 

import java.awt.CardLayout; 
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 

public class ManHinhChinh {

    private JFrame frame;

    // --- Quản lý các màn hình ---
    private JPanel mainContentPane; // Panel chính dùng CardLayout
    private BackgroundImagePanel panelTrangChu; // Màn hình chính (ảnh nền)
    
    private ManHinhQuanLyBan panelQuanLyDatBan; 
    
    // THÊM: Panel Quản lý Bàn Ăn
    private QuanLyBanAn panelQuanLyBanAn;

    // Các biến field cho menu item
    private JMenuItem mntmDatBan; 
    private JMenuItem mntmTrangChu; 
    
    // THÊM: Menu item Bàn Ăn
    private JMenuItem mntmBanAn;

    // THÊM: Biến để lưu trữ nhân viên đã đăng nhập
    private NhanVien nhanVienHienTai;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Throwable e) {
                    e.printStackTrace();
                }

                try {
                    // === SỬA ĐỂ TEST ===
                    NhanVien_dao nvDao = NhanVien_dao.getInstance();
                    NhanVien nvTest = nvDao.selectById(new NhanVien("NV001")); 

                    if (nvTest == null) {
                        JOptionPane.showMessageDialog(null, "Không tìm thấy NV001 (Nguyễn Văn An) trong CSDL để test.\n" +
                                "Vui lòng kiểm tra data SQL.", "Lỗi Data Test", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    ManHinhChinh window = new ManHinhChinh(nvTest);
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * SỬA: Constructor
     */
    public ManHinhChinh(NhanVien nv) {
        this.nhanVienHienTai = nv; // Lưu nhân viên lại
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setTitle("Hệ thống Quản lý Đặt bàn - Xin chào, " + nhanVienHienTai.getTenNhanVien());
        //frame.setBounds(100, 100, 1280, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); 

        // --- THANH MENU ---
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu mnHeThong = new JMenu("Hệ thống");
        mnHeThong.setIcon(loadIcon("/img/menu/heThong.png"));
        menuBar.add(mnHeThong);

        mntmTrangChu = new JMenuItem("Trang chủ");
        mntmTrangChu.setIcon(loadIcon("/img/menu/heThong.png")); // Bạn có thể đổi icon
        mnHeThong.add(mntmTrangChu);

        JMenu mnDanhMuc = new JMenu("Danh mục");
        mnDanhMuc.setIcon(loadIcon("/img/menu/danhMuc.png"));
        menuBar.add(mnDanhMuc);
        // ... (thêm các item cho Danh mục)
        JMenuItem mntmKhuVuc = new JMenuItem("Khu vực");
        mntmKhuVuc.setIcon(loadIcon("/img/menu/khuVuc.png"));
        mnDanhMuc.add(mntmKhuVuc);
        
        // SỬA: Gán vào field
        mntmBanAn = new JMenuItem("Bàn ăn"); 
        mntmBanAn.setIcon(loadIcon("/img/menu/table.png"));
        mnDanhMuc.add(mntmBanAn);
        
        JMenuItem mntmMonAn = new JMenuItem("Món ăn");
        mntmMonAn.setIcon(loadIcon("/img/menu/man.png"));
        mnDanhMuc.add(mntmMonAn);
        JMenuItem mntmKhachHang = new JMenuItem("Khách hàng");
        mntmKhachHang.setIcon(loadIcon("/img/menu/Customer.png"));
        mnDanhMuc.add(mntmKhachHang);
        JMenuItem mntmNhanVien = new JMenuItem("Nhân viên");
        mntmNhanVien.setIcon(loadIcon("/img/menu/nhanVien.png")); // Sửa: Sửa lại tên icon
        mnDanhMuc.add(mntmNhanVien);
        JMenuItem mntmTaiKhoan = new JMenuItem("Tài khoản");
        mntmTaiKhoan.setIcon(loadIcon("/img/menu/themKhachHang.png"));
        mnDanhMuc.add(mntmTaiKhoan);


        JMenu mnTimKiem = new JMenu("Tìm kiếm");
        mnTimKiem.setIcon(loadIcon("/img/menu/find.png"));
        menuBar.add(mnTimKiem);
        // ... (thêm các item cho Tìm kiếm)
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
        mntmNhanVien2.setIcon(loadIcon("/img/menu/nhanVien.png")); // Sửa: Sửa lại tên icon
        mnTimKiem.add(mntmNhanVien2);


        JMenu mnXuLy = new JMenu("Xử lý");
        mnXuLy.setIcon(loadIcon("/img/menu/xuLy.png"));
        menuBar.add(mnXuLy);

        mntmDatBan = new JMenuItem("Đặt bàn"); 
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
        // ... (thêm các item cho Thống kê)
        JMenuItem mntmDoanhThu = new JMenuItem("Doanh thu");
        mntmDoanhThu.setIcon(loadIcon("/img/menu/doanhThu.png"));
        mnThongKe.add(mntmDoanhThu);
        JMenuItem mntmMon = new JMenuItem("Món ăn");
        mntmMon.setIcon(loadIcon("/img/menu/thongKeMonAn.png"));
        mnThongKe.add(mntmMon);

        
        // --- NỘI DUNG CHÍNH (DÙNG CARDLAYOUT) ---
        
        mainContentPane = new JPanel(new CardLayout());
        frame.setContentPane(mainContentPane); 

        // 2. Tạo Card 1: Màn hình Trang chủ (Ảnh nền)
        ImageIcon bgIcon = loadIcon("/img/bg/hadi-bg.png");
        panelTrangChu = new BackgroundImagePanel(bgIcon);
        mainContentPane.add(panelTrangChu, "TRANG_CHU"); // Thêm vào CardLayout
        
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{1};
        gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0};
        gbl_contentPane.columnWeights = new double[]{1.0};
        gbl_contentPane.rowWeights = new double[]{0.3, 0.0, 0.0, 0.7}; 
        panelTrangChu.setLayout(gbl_contentPane); 

        // --- TIÊU ĐỀ (Thêm vào panelTrangChu) ---
        JLabel lblTitle = new JLabel("HỆ THỐNG QUẢN LÝ ĐẶT BÀN");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 42));
        GridBagConstraints gbc_lblTitle = new GridBagConstraints();
        gbc_lblTitle.insets = new Insets(0, 0, 5, 0);
        gbc_lblTitle.gridx = 0;
        gbc_lblTitle.gridy = 0; 
        panelTrangChu.add(lblTitle, gbc_lblTitle);

        JLabel lblSubtitle = new JLabel("Nhà hàng Hadi");
        lblSubtitle.setForeground(new Color(220, 220, 220));
        lblSubtitle.setFont(new Font("Tahoma", Font.ITALIC, 28));
        GridBagConstraints gbc_lblSubtitle = new GridBagConstraints();
        gbc_lblSubtitle.insets = new Insets(10, 0, 50, 0);
        gbc_lblSubtitle.gridx = 0;
        gbc_lblSubtitle.gridy = 1; 
        panelTrangChu.add(lblSubtitle, gbc_lblSubtitle);

        // --- PANEL NÚT BẤM (Thêm vào panelTrangChu) ---
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); 
        FlowLayout flowLayout = (FlowLayout) buttonPanel.getLayout();
        flowLayout.setHgap(40); 
        flowLayout.setVgap(20);

        GridBagConstraints gbc_buttonPanel = new GridBagConstraints();
        gbc_buttonPanel.gridx = 0;
        gbc_buttonPanel.gridy = 2; 
        panelTrangChu.add(buttonPanel, gbc_buttonPanel);

        // --- CÁC NÚT BẤM CHÍNH (Thêm vào buttonPanel) ---
        JButton btnKhuVuc = new JButton("Khu vực");
        btnKhuVuc.setPreferredSize(new Dimension(160, 140));
        btnKhuVuc.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnKhuVuc.setHorizontalTextPosition(SwingConstants.CENTER);
        btnKhuVuc.setFont(new Font("Tahoma", Font.BOLD, 18));
        btnKhuVuc.setIcon(loadIcon("/img/menu/khuVuc.png"));
        btnKhuVuc.setBackground(new Color(255, 255, 255, 180)); 
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

        // --- THÊM SỰ KIỆN CHO MENU (Đã sửa) ---
        
        // Sự kiện click "Đặt bàn"
        mntmDatBan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (panelQuanLyDatBan == null) {
                    
                    panelQuanLyDatBan = new ManHinhQuanLyBan(nhanVienHienTai); 
                    
                    mainContentPane.add(panelQuanLyDatBan, "QUAN_LY_DAT_BAN");
                } else {
                    panelQuanLyDatBan.loadDataBanAn();
                }

                CardLayout cl = (CardLayout) (mainContentPane.getLayout());
                cl.show(mainContentPane, "QUAN_LY_DAT_BAN");
            }
        });

        // Sự kiện click "Trang chủ"
        mntmTrangChu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) (mainContentPane.getLayout());
                cl.show(mainContentPane, "TRANG_CHU");
            }
        });
        
        // ================================================================
        // THÊM SỰ KIỆN CLICK "BÀN ĂN" (TRONG DANH MỤC)
        // ================================================================
        mntmBanAn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 1. Kiểm tra xem panel đã được tạo chưa
                if (panelQuanLyBanAn == null) {
                    // Nếu chưa, tạo mới
                    panelQuanLyBanAn = new QuanLyBanAn();
                    // Thêm vào CardLayout với một tên duy nhất
                    mainContentPane.add(panelQuanLyBanAn, "QUAN_LY_BAN_AN");
                } 

                // 2. Lấy CardLayout và hiển thị panel
                CardLayout cl = (CardLayout) (mainContentPane.getLayout());
                cl.show(mainContentPane, "QUAN_LY_BAN_AN");
            }
        });
    }

    // SỬA LỖI 6: Xóa dấu '}' thừa ở đây (Đã xóa)

    /**
     * LỚP CON (INNER CLASS) ĐỂ VẼ ẢNH NỀN
     * (Giữ nguyên code này)
     */
    class BackgroundImagePanel extends JPanel {

        private Image backgroundImage;

        public BackgroundImagePanel() {
            super();
        }

        public BackgroundImagePanel(Image image) {
            super();
            this.backgroundImage = image;
        }

        public BackgroundImagePanel(ImageIcon icon) {
            super();
            if (icon != null) {
                this.backgroundImage = icon.getImage();
            }
        }

        public void setBackgroundImage(Image image) {
            this.backgroundImage = image;
            this.repaint(); 
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        }
    }

    // --- HÀM TRỢ GIÚP TẢI ICON AN TOÀN ---
    // (Giữ nguyên code này)
    private ImageIcon loadIcon(String path) {
        URL imgURL = ManHinhChinh.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            // Sửa: Thêm icon mặc định (placeholder) để tránh lỗi NullPointer
            // Tạo một icon vuông 16x16 màu xám
            ImageIcon placeholder = new ImageIcon(new java.awt.image.BufferedImage(16, 16, java.awt.image.BufferedImage.TYPE_INT_RGB));
            Graphics g = placeholder.getImage().getGraphics();
            g.setColor(Color.GRAY);
            g.fillRect(0, 0, 16, 16);
            g.dispose();
            return placeholder;
        }
    }
}