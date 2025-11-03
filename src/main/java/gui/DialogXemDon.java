package gui;

import entity.ChiTietDonDatBan;
import entity.DonDatBan;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
// Xóa import JTable
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class DialogXemDon extends JDialog {

    private DonDatBan donDatBan;

    public DialogXemDon(Window owner, DonDatBan donDatBan) {
        super(owner, "Thông Tin Đơn Đặt Bàn", ModalityType.DOCUMENT_MODAL);
        this.donDatBan = donDatBan;

        // SỬA: Cho phép dialog tự điều chỉnh kích thước
        setSize(450, 550); // Set kích thước ban đầu, nhưng sẽ gọi pack() sau
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // Định dạng
        DateTimeFormatter dtfNgay = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter dtfGio = DateTimeFormatter.ofPattern("HH:mm");

        // Panel chính
        JPanel pnlMain = new JPanel(new GridBagLayout());
        pnlMain.setBackground(Color.WHITE);
        pnlMain.setBorder(new EmptyBorder(20, 25, 20, 25));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(8, 5, 8, 15);
        gbc.weightx = 0.4; // Cột 0 (label)

        Font labelFont = new Font("Tahoma", Font.PLAIN, 15);
        Font dataFont = new Font("Tahoma", Font.BOLD, 15);

        // Tiêu đề
        JLabel lblTitle = new JLabel("THÔNG TIN ĐƠN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 22));
        lblTitle.setForeground(new Color(230, 126, 34)); // Màu cam (xem đơn)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 5, 20, 5);
        pnlMain.add(lblTitle, gbc);

        // Reset
        gbc.gridwidth = 1;
        gbc.weightx = 1.0; // Cột 1 (dữ liệu)
        gbc.insets = new Insets(8, 5, 8, 5);

        // --- Hàng 1: Khách hàng ---
        gbc.gridy++;
        gbc.gridx = 0; gbc.weightx = 0.4;
        gbc.insets = new Insets(8, 5, 8, 15);
        pnlMain.add(new JLabel("Khách hàng:"), gbc);
        
        gbc.gridx = 1; gbc.weightx = 0.6;
        gbc.insets = new Insets(8, 5, 8, 5);
        JLabel lblKH = new JLabel(donDatBan.getKhachHang().getTenKhachHang());
        lblKH.setFont(dataFont);
        pnlMain.add(lblKH, gbc);

        // --- Hàng 2: Số điện thoại ---
        gbc.gridy++;
        gbc.gridx = 0; gbc.weightx = 0.4;
        gbc.insets = new Insets(8, 5, 8, 15);
        pnlMain.add(new JLabel("Số điện thoại:"), gbc);
        
        gbc.gridx = 1; gbc.weightx = 0.6;
        gbc.insets = new Insets(8, 5, 8, 5);
        JLabel lblSDT = new JLabel(donDatBan.getKhachHang().getSoDienThoai());
        lblSDT.setFont(dataFont);
        pnlMain.add(lblSDT, gbc);


        // --- Hàng 3: Nhân viên ---
        gbc.gridy++;
        gbc.gridx = 0; gbc.weightx = 0.4; gbc.insets = new Insets(8, 5, 8, 15);
        pnlMain.add(new JLabel("Nhân viên:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.6; gbc.insets = new Insets(8, 5, 8, 5);
        JLabel lblNV = new JLabel(donDatBan.getNhanVien().getTenNhanVien());
        lblNV.setFont(dataFont);
        pnlMain.add(lblNV, gbc);

        // --- Hàng 4: Ngày đặt ---
        gbc.gridy++;
        gbc.gridx = 0; gbc.weightx = 0.4; gbc.insets = new Insets(8, 5, 8, 15);
        pnlMain.add(new JLabel("Ngày đặt hẹn:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.6; gbc.insets = new Insets(8, 5, 8, 5);
        JLabel lblNgayDat = new JLabel(donDatBan.getNgayDat().format(dtfNgay));
        lblNgayDat.setFont(dataFont);
        pnlMain.add(lblNgayDat, gbc);

        // --- Hàng 5: Giờ đặt ---
        gbc.gridy++;
        gbc.gridx = 0; gbc.weightx = 0.4; gbc.insets = new Insets(8, 5, 8, 15);
        pnlMain.add(new JLabel("Giờ đặt hẹn:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.6; gbc.insets = new Insets(8, 5, 8, 5);
        JLabel lblGioDat = new JLabel(donDatBan.getNgayDat().format(dtfGio));
        lblGioDat.setFont(dataFont);
        pnlMain.add(lblGioDat, gbc);

        // --- Hàng 6: Check-in ---
        gbc.gridy++;
        gbc.gridx = 0; gbc.weightx = 0.4; gbc.insets = new Insets(8, 5, 8, 15);
        pnlMain.add(new JLabel("Check-in lúc:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.6; gbc.insets = new Insets(8, 5, 8, 5);
        String checkIn = (donDatBan.getThoiGianCheckIn() != null)
                ? donDatBan.getThoiGianCheckIn().format(dtfGio)
                : "(Chưa check-in)";
        JLabel lblCheckIn = new JLabel(checkIn);
        lblCheckIn.setFont(dataFont);
        pnlMain.add(lblCheckIn, gbc);
        
        // --- Hàng 7: Dấu gạch ngang ---
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 5, 5, 5);
        pnlMain.add(new JSeparator(), gbc);

        // --- Hàng 8: Tiêu đề "Chi Tiết Đơn Đặt Bàn" ---
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 5, 5, 5);
        JLabel lblTitleBan = new JLabel("Chi Tiết Đơn Đặt Bàn", SwingConstants.CENTER);
        lblTitleBan.setFont(new Font("Tahoma", Font.BOLD, 16));
        pnlMain.add(lblTitleBan, gbc);

        
        // ================================================================
        // SỬA: Thay thế JScrollPane bằng JPanel
        // ================================================================

        // Tạo một panel để chứa danh sách các bàn
        JPanel pnlDanhSachBan = new JPanel();
        pnlDanhSachBan.setBackground(Color.WHITE);
        pnlDanhSachBan.setLayout(new BoxLayout(pnlDanhSachBan, BoxLayout.Y_AXIS)); // Xếp dọc
        pnlDanhSachBan.setBorder(new EmptyBorder(5, 10, 10, 10)); // Thêm padding

        double tongCoc = 0;
        Font banFont = new Font("Tahoma", Font.PLAIN, 15); // Font cho chi tiết

        // Thêm hàng tiêu đề
        JPanel pnlHeader = new JPanel(new GridLayout(1, 3, 10, 0));
        pnlHeader.setBackground(Color.WHITE);
        Font headerFont = new Font("Tahoma", Font.BOLD, 15);
        
        JLabel h1 = new JLabel("Bàn");
        h1.setFont(headerFont);
        JLabel h2 = new JLabel("Loại Bàn");
        h2.setFont(headerFont);
        h2.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel h3 = new JLabel("Tiền Cọc");
        h3.setFont(headerFont);
        h3.setHorizontalAlignment(SwingConstants.RIGHT);
        
        pnlHeader.add(h1);
        pnlHeader.add(h2);
        pnlHeader.add(h3);
        pnlDanhSachBan.add(pnlHeader);
        // Thêm gạch ngang dưới tiêu đề
        pnlDanhSachBan.add(new JSeparator());

        for (ChiTietDonDatBan ct : donDatBan.getDsChiTiet()) {
            // Mỗi bàn là một panel riêng
            JPanel pnlMotBan = new JPanel(new GridLayout(1, 3, 10, 0)); // 1 hàng, 3 cột
            pnlMotBan.setBackground(Color.WHITE);
            pnlMotBan.setBorder(new EmptyBorder(5, 0, 5, 0)); // Padding (top/bottom)

            // Cột 1: Tên bàn
            JLabel lblTenBan = new JLabel(ct.getBanAn().getTenBanAn());
            lblTenBan.setFont(banFont);
            
            // Cột 2: Loại bàn
            JLabel lblLoaiBan = new JLabel(ct.getBanAn().getLoaiBan());
            lblLoaiBan.setFont(banFont);
            lblLoaiBan.setHorizontalAlignment(SwingConstants.CENTER); // Canh giữa
            
            // Cột 3: Tiền cọc
            JLabel lblTienCoc = new JLabel(String.format("%,.0f", ct.getTienCoc()));
            lblTienCoc.setFont(banFont);
            lblTienCoc.setHorizontalAlignment(SwingConstants.RIGHT); // Canh phải

            pnlMotBan.add(lblTenBan);
            pnlMotBan.add(lblLoaiBan);
            pnlMotBan.add(lblTienCoc);
            
            pnlDanhSachBan.add(pnlMotBan); // Thêm panel của bàn vào danh sách

            tongCoc += ct.getTienCoc();
        }

        // Thêm panel danh sách bàn vào layout chính
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 0; // SỬA: Không cho co dãn dọc nữa
        
        // SỬA: Thêm pnlDanhSachBan trực tiếp, BỎ JScrollPane
        pnlMain.add(pnlDanhSachBan, gbc);

        // ================================================================
        // Hết phần sửa
        // ================================================================
        
        // Tổng cọc
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0; // Không co dãn
        gbc.insets = new Insets(10, 5, 5, 5); // Tăng padding trên
        pnlMain.add(new JSeparator(), gbc); // Thêm 1 gạch ngang nữa

        gbc.gridy++;
        gbc.insets = new Insets(5, 5, 5, 5);
        JLabel lblTongCoc = new JLabel("Tổng tiền cọc: " + String.format("%,.0f VND", tongCoc));
        lblTongCoc.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTongCoc.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTongCoc.setBorder(new EmptyBorder(5, 0, 0, 5));
        pnlMain.add(lblTongCoc, gbc);


        add(pnlMain, BorderLayout.CENTER);

        // Nút Đóng
        JButton btnDong = new JButton("Đóng");
        btnDong.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnDong.setPreferredSize(new Dimension(100, 40));
        btnDong.addActionListener(e -> dispose());
        JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlButton.setBorder(new EmptyBorder(0, 10, 10, 10));
        pnlButton.setBackground(Color.WHITE);
        pnlButton.add(btnDong);

        add(pnlButton, BorderLayout.SOUTH);

        // SỬA: Gọi pack() để dialog tự điều chỉnh chiều cao
        pack();
        // Sau khi pack(), set lại vị trí
        setLocationRelativeTo(owner);
    }
}