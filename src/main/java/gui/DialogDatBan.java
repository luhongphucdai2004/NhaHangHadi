package gui;

import dao.DonDatBan_dao;
import dao.KhachHang_dao;
import dao.BanAn_dao;
// import dao.LoaiKhachHang_dao; // Không cần
import entity.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class DialogDatBan extends JDialog {

    // Components
    private JTextField txtSoDienThoai, txtTenKhach; // SỬA: Xóa txtTienCoc
    private JLabel lblBanChon, lblNhanVien;
    private JFormattedTextField txtNgayDat;
    private JSpinner spinGioDat;
    private JButton btnXacNhan, btnHuy;

    // DAO
    private KhachHang_dao khachHangDao = KhachHang_dao.getInstance();
    private DonDatBan_dao donDatBanDao = DonDatBan_dao.getInstance();
    private BanAn_dao banAnDao = BanAn_dao.getInstance();

    // Dữ liệu
    private List<BanAn> dsBanChon;
    private NhanVien nhanVien;
    private boolean isDatHen;
    private KhachHang khachHangDaTim = null;
    private boolean success = false;

    /**
     * @param owner     Frame cha
     * @param title     Tiêu đề
     * @param dsBanChon Danh sách bàn đã chọn
     * @param nhanVien  Nhân viên đang đăng nhập
     * @param isDatHen  true = Đặt hẹn (hiển thị ngày giờ), false = Đặt ngay (ẩn ngày giờ)
     */
    public DialogDatBan(Window owner, String title, List<BanAn> dsBanChon, NhanVien nhanVien, boolean isDatHen) {
        super(owner, title, ModalityType.DOCUMENT_MODAL);
        this.dsBanChon = dsBanChon;
        this.nhanVien = nhanVien;
        this.isDatHen = isDatHen;

        // --- SỬA KÍCH THƯỚC VÀ LAYOUT ---
        setSize(550, 470); // Giảm chiều cao do bỏ 1 hàng
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // --- Định nghĩa Font chữ ---
        Font labelFont = new Font("Tahoma", Font.PLAIN, 15);
        Font dataFont = new Font("Tahoma", Font.BOLD, 15);
        Font componentFont = new Font("Tahoma", Font.PLAIN, 15);

        // --- Panel Chính (Nội dung) ---
        JPanel pnlMain = new JPanel(new GridBagLayout());
        pnlMain.setBackground(Color.WHITE);
        pnlMain.setBorder(new EmptyBorder(20, 25, 20, 25)); // Tăng padding
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL; // Dãn theo chiều ngang
        gbc.anchor = GridBagConstraints.WEST; // Neo về bên trái
        gbc.insets = new Insets(8, 5, 8, 15); // Tăng khoảng cách (top, left, bottom, right)
        gbc.ipady = 0; // Reset padding nội bộ
        gbc.weightx = 0.0; // Mặc định cột 0 (label) không dãn

        // Tiêu đề
        JLabel lblTitle = new JLabel(title.toUpperCase(), SwingConstants.CENTER);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 22)); // Tăng font
        lblTitle.setForeground(new Color(52, 152, 219));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Gộp 2 cột
        gbc.insets = new Insets(5, 5, 20, 5); // Tăng khoảng cách dưới
        pnlMain.add(lblTitle, gbc);

        // Reset về mặc định
        gbc.gridwidth = 1;
        gbc.insets = new Insets(8, 5, 8, 15); // Label có 15px padding bên phải

        // --- Hàng 1: Số bàn ---
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.ipady = 0; // Label không cần cao
        gbc.weightx = 0.0; // Cột 0 không dãn
        JLabel lblSoBan = new JLabel("Số bàn:");
        lblSoBan.setFont(labelFont);
        pnlMain.add(lblSoBan, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0; // Cột 1 (dữ liệu) dãn ra
        gbc.insets = new Insets(8, 5, 8, 5); // Dữ liệu padding 5px
        String tenCacBan = dsBanChon.stream().map(BanAn::getTenBanAn).collect(Collectors.joining(", "));
        lblBanChon = new JLabel(tenCacBan);
        lblBanChon.setFont(dataFont);
        pnlMain.add(lblBanChon, gbc);


        // --- Hàng 2 & 3: Ngày Giờ (Chỉ hiển thị khi Đặt Hẹn) ---
        if (isDatHen) {
            // Ngày đặt
            gbc.gridy++;
            gbc.gridx = 0;
            gbc.ipady = 0;
            gbc.weightx = 0.0;
            gbc.insets = new Insets(8, 5, 8, 15);
            JLabel lblNgayDat = new JLabel("Ngày đặt:");
            lblNgayDat.setFont(labelFont);
            pnlMain.add(lblNgayDat, gbc);

            gbc.gridx = 1;
            gbc.weightx = 1.0;
            gbc.ipady = 8; // Tăng chiều cao component
            gbc.insets = new Insets(8, 5, 8, 5);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            txtNgayDat = new JFormattedTextField(formatter);
            // SỬA: Dùng setText() để ép đúng định dạng chuỗi
            txtNgayDat.setText(LocalDate.now().format(formatter)); 
            txtNgayDat.setFont(componentFont);
            txtNgayDat.setToolTipText("Định dạng dd/MM/yyyy");
            pnlMain.add(txtNgayDat, gbc);

            // Giờ đặt (Mặc định +1 tiếng)
            gbc.gridy++;
            gbc.gridx = 0;
            gbc.ipady = 0;
            gbc.weightx = 0.0;
            gbc.insets = new Insets(8, 5, 8, 15);
            JLabel lblGioDat = new JLabel("Giờ đặt:");
            lblGioDat.setFont(labelFont);
            pnlMain.add(lblGioDat, gbc);

            gbc.gridx = 1;
            gbc.weightx = 1.0;
            gbc.ipady = 8; // Tăng chiều cao component
            gbc.insets = new Insets(8, 5, 8, 5);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.HOUR_OF_DAY, 1);
            SpinnerDateModel timeModel = new SpinnerDateModel(calendar.getTime(), null, null, Calendar.MINUTE);
            spinGioDat = new JSpinner(timeModel);
            spinGioDat.setFont(componentFont); // Tăng font
            // Tăng font cho ô nhập text bên trong JSpinner
            JComponent editor = spinGioDat.getEditor();
            if (editor instanceof JSpinner.DateEditor) {
                ((JSpinner.DateEditor) editor).getTextField().setFont(componentFont);
            }
            spinGioDat.setEditor(new JSpinner.DateEditor(spinGioDat, "HH:mm"));
            pnlMain.add(spinGioDat, gbc);
        }

        // --- Hàng 4: SĐT ---
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.ipady = 0;
        gbc.weightx = 0.0;
        gbc.insets = new Insets(8, 5, 8, 15);
        JLabel lblSDT = new JLabel("SĐT Khách (Enter để tìm):");
        lblSDT.setFont(labelFont);
        pnlMain.add(lblSDT, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.ipady = 8; // Tăng chiều cao component
        gbc.insets = new Insets(8, 5, 8, 5);
        txtSoDienThoai = new JTextField();
        txtSoDienThoai.setFont(componentFont);
        pnlMain.add(txtSoDienThoai, gbc);

        // --- Hàng 5: Tên Khách ---
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.ipady = 0;
        gbc.weightx = 0.0;
        gbc.insets = new Insets(8, 5, 8, 15);
        JLabel lblTenKhach = new JLabel("Tên khách hàng:");
        lblTenKhach.setFont(labelFont);
        pnlMain.add(lblTenKhach, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.ipady = 8; // Tăng chiều cao component
        gbc.insets = new Insets(8, 5, 8, 5);
        txtTenKhach = new JTextField();
        txtTenKhach.setFont(componentFont);
        txtTenKhach.setToolTipText("Nhập tên nếu là khách mới");
        pnlMain.add(txtTenKhach, gbc);

        // --- SỬA: XÓA BỎ HÀNG 6 (TIỀN CỌC) ---

        // --- Hàng 7 (nay là Hàng 6): Nhân viên ---
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.ipady = 0;
        gbc.weightx = 0.0;
        gbc.insets = new Insets(8, 5, 8, 15);
        JLabel lblNV = new JLabel("Nhân viên:");
        lblNV.setFont(labelFont);
        pnlMain.add(lblNV, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(8, 5, 8, 5);
        lblNhanVien = new JLabel(nhanVien.getTenNhanVien());
        lblNhanVien.setFont(dataFont);
        pnlMain.add(lblNhanVien, gbc);

        add(pnlMain, BorderLayout.CENTER);

        // --- Panel Nút (Giữ nguyên) ---
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        pnlButtons.setBackground(Color.WHITE);
        pnlButtons.setBorder(new EmptyBorder(0, 10, 10, 25)); // Tăng padding

        // Nút Hủy
        btnHuy = new JButton("Hủy");
        btnHuy.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnHuy.setBackground(new Color(231, 76, 60));
        btnHuy.setForeground(Color.WHITE);
        btnHuy.setPreferredSize(new Dimension(120, 40)); // Tăng kích thước nút
        btnHuy.setFocusPainted(false);
        btnHuy.setBorder(null);

        // Nút Xác Nhận
        btnXacNhan = new JButton("Xác Nhận"); // SỬA TÊN NÚT
        btnXacNhan.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnXacNhan.setBackground(new Color(46, 204, 113));
        btnXacNhan.setForeground(Color.WHITE);
        btnXacNhan.setPreferredSize(new Dimension(120, 40)); // Tăng kích thước nút
        btnXacNhan.setFocusPainted(false);
        btnXacNhan.setBorder(null);

        pnlButtons.add(btnHuy);
        pnlButtons.add(btnXacNhan);
        add(pnlButtons, BorderLayout.SOUTH);

        // Add Listeners
        addListeners();
    }

    private void addListeners() {
        // Tìm khách hàng khi nhập SĐT
        txtSoDienThoai.addActionListener(e -> timKhachHang());
        txtSoDienThoai.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                // Chỉ tìm khi focus lost nếu SĐT không rỗng
                if (!txtSoDienThoai.getText().trim().isEmpty()) {
                    timKhachHang();
                }
            }
        });

        // Hủy
        btnHuy.addActionListener(e -> dispose());

        // Xác nhận
        btnXacNhan.addActionListener(e -> xacNhanDatBan());
    }

    private void timKhachHang() {
        String sdt = txtSoDienThoai.getText().trim();
        if (sdt.isEmpty()) {
            txtTenKhach.setText("");
            txtTenKhach.setEditable(true);
            khachHangDaTim = null;
            return;
        }

        khachHangDaTim = khachHangDao.timTheoSoDienThoai(sdt);
        if (khachHangDaTim != null) {
            txtTenKhach.setText(khachHangDaTim.getTenKhachHang());
            txtTenKhach.setEditable(false); // Khóa ô tên
            // SỬA: Focus vào nút xác nhận thay vì tiền cọc
            btnXacNhan.requestFocus();
        } else {
            txtTenKhach.setText("");
            txtTenKhach.setEditable(true); // Mở khóa ô tên
            txtTenKhach.requestFocus();
            khachHangDaTim = null;
            JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng. Vui lòng nhập tên để tạo khách hàng mới.", "Khách hàng mới", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void xacNhanDatBan() {
        // 1. Validate
        String sdt = txtSoDienThoai.getText().trim();
        String tenKhach = txtTenKhach.getText().trim();
        // SỬA: Xóa biến tiền cọc (double tienCoc)

        if (sdt.isEmpty() || tenKhach.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ SĐT và Tên khách hàng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validate SĐT (ví dụ đơn giản)
        if (!sdt.matches("0[0-9]{9}")) {
             JOptionPane.showMessageDialog(this, "Số điện thoại phải bắt đầu bằng 0 và có 10 chữ số.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // SỬA: XÓA BỎ BLOCK VALIDATE TIỀN CỌC
        
        // 2. Tạo hoặc lấy Khách Hàng (Giữ nguyên)
        KhachHang kh;
        if (khachHangDaTim != null) {
            kh = khachHangDaTim;
        } else {
            // Logic tạo khách hàng mới
            try {
                String maMoi = khachHangDao.phatSinhMaMoi();
                LoaiKhachHang loaiMacDinh = new LoaiKhachHang("LKH01", "Thường", "Khách hàng mới", 0);
                kh = new KhachHang(maMoi, tenKhach, sdt, 0, loaiMacDinh, ""); 
                
                try {
                    khachHangDao.them(kh);
                    JOptionPane.showMessageDialog(this, "Đã tạo khách hàng mới: " + tenKhach, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Lỗi khi tạo khách hàng mới: " + e.getMessage(), "Lỗi SQL", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi phát sinh mã khách hàng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        // 3. Tạo Đơn Đặt Bàn và Chi Tiết
        LocalDateTime ngayDat;
        if (isDatHen) {
            try {
                // (Logic lấy ngày giờ giữ nguyên)
                LocalDate date;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                try {
                    date = LocalDate.parse(txtNgayDat.getText(), formatter);
                } catch (Exception e) {
                    // SỬA: Dùng setText() để reset về ngày hôm nay
                    txtNgayDat.setText(LocalDate.now().format(formatter)); 
                    throw new Exception("Ngày đặt không hợp lệ. Vui lòng dùng định dạng dd/MM/yyyy.");
                }

                Date time = (Date) spinGioDat.getValue();
                LocalTime localTime = time.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
                ngayDat = LocalDateTime.of(date, localTime);

                if (ngayDat.isBefore(LocalDateTime.now().plusMinutes(30))) { // Phải đặt trước ít nhất 30 phút
                    JOptionPane.showMessageDialog(this, "Ngày đặt hẹn phải sau thời điểm hiện tại ít nhất 30 phút.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Ngày hoặc giờ đặt không hợp lệ: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            ngayDat = LocalDateTime.now(); // Đặt ngay
        }

        // Tạo đối tượng DonDatBan
        DonDatBan don = new DonDatBan(
                "DDB_" + System.currentTimeMillis(), // Cần hàm phát sinh mã chuẩn
                kh,
                nhanVien,
                ngayDat,
                LocalDateTime.now(), // ngayTaoDon
                null, // checkIn (sẽ null nếu là đặt hẹn)
                null  // dsChiTiet (sẽ thêm sau)
        );

        // SỬA: LOGIC TẠO CHI TIẾT ĐƠN ĐẶT BÀN
        List<ChiTietDonDatBan> dsChiTiet = dsBanChon.stream()
                .map(ban -> {
                    double tienCocCalc = 0; // Mặc định là 0
                    if (isDatHen) { // Chỉ tính cọc khi là ĐẶT HẸN
                        // Giả định 'loaiBan' trong entity BanAn là "VIP" hoặc "Thường"
                        if (ban.getLoaiBan() != null && ban.getLoaiBan().equalsIgnoreCase("VIP")) {
                            tienCocCalc = 200000;
                        } else {
                            tienCocCalc = 100000;
                        }
                    }
                    // Nếu 'isDatHen' là false (Đặt Ngay), 'tienCocCalc' sẽ là 0
                    return new ChiTietDonDatBan(don, ban, tienCocCalc);
                })
                .collect(Collectors.toList());


        // 4. Gọi DAO
        boolean result;
        if (isDatHen) {
            result = donDatBanDao.insert(don, dsChiTiet);
        } else {
            // Nếu là đặt ngay, set thời gian check-in
            don.setThoiGianCheckIn(LocalDateTime.now());
            result = donDatBanDao.insertAndCheckIn(don, dsChiTiet);
        }

        // 5. Đóng dialog và thông báo
        if (result) {
            JOptionPane.showMessageDialog(this, "Tạo đơn thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            this.success = true;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Tạo đơn thất bại. Vui lòng thử lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isSuccess() {
        return success;
    }
}