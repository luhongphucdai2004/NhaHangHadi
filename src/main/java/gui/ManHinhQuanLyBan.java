package gui;

import dao.BanAn_dao;
import dao.DonDatBan_dao;
import dao.KhuVuc_dao;
import entity.BanAn;
import entity.DonDatBan;
import entity.KhuVuc;
import entity.NhanVien;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ManHinhQuanLyBan extends JPanel {
    // DAO
    private BanAn_dao banAnDao;
    private KhuVuc_dao khuVucDao;
    private DonDatBan_dao donDatBanDao;

    // Components
    private JPanel pnlMenuTrai, pnlTopFilter, pnlDanhSachBan;
    private JScrollPane scrollPaneMain;
    private JButton btnDatBanNgay, btnTaoDonHen, btnTaoDonNhieuBan, btnXemDon, btnCheckIn, btnChuyenBan, btnHuyBan;
    private JButton btnTatCa, btnTrong, btnDaDat, btnQuaHan, btnDangDung;
    private JComboBox<Object> cmbKhuVuc, cmbLoaiBan;

    private List<BanAn> dsBanAnMaster;
    private List<BanAnCard> dsCardHienThi;

    private NhanVien nhanVienHienTai;

    // Màu sắc cho các button trạng thái
    private final Color COLOR_TAT_CA = Color.BLACK;
    private final Color COLOR_TRONG = new Color(46, 204, 113);
    private final Color COLOR_DA_DAT = new Color(52, 152, 219);
    private final Color COLOR_QUA_HAN = new Color(231, 76, 60);
    private final Color COLOR_DANG_DUNG = new Color(243, 156, 18);

    // Màu sắc cho các button chức năng
    private final Color[] COLOR_CHUC_NANG = {
            new Color(155, 89, 182), // Đặt bàn ngay - Tím
            new Color(52, 152, 219), // Tạo đơn hẹn - Xanh dương
            new Color(26, 188, 156), // Tạo đơn nhiều bàn - Xanh lơ
            new Color(230, 126, 34), // Xem đơn - Cam
            new Color(46, 204, 113), // Check-in - Xanh lá
            new Color(241, 196, 15), // Chuyển bàn - Vàng
            new Color(231, 76, 60) // Hủy bàn - Đỏ
    };

    public ManHinhQuanLyBan(NhanVien nhanVien) {
        this.nhanVienHienTai = nhanVien;

        banAnDao = BanAn_dao.getInstance();
        khuVucDao = KhuVuc_dao.getInstance();
        donDatBanDao = DonDatBan_dao.getInstance();

        dsBanAnMaster = new ArrayList<>();
        dsCardHienThi = new ArrayList<>();

        setLayout(new BorderLayout(5, 5));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // Panel Menu Trái
        pnlMenuTrai = new JPanel();
        pnlMenuTrai.setLayout(new BoxLayout(pnlMenuTrai, BoxLayout.Y_AXIS));
        pnlMenuTrai.setBorder(new TitledBorder(null, "Chức năng", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        pnlMenuTrai.setBackground(Color.WHITE);
        pnlMenuTrai.setPreferredSize(new Dimension(200, 0));

        btnDatBanNgay = createMenuButton("Đặt bàn ngay", COLOR_CHUC_NANG[0]);
        btnTaoDonHen = createMenuButton("Tạo đơn đặt hẹn", COLOR_CHUC_NANG[1]);
        btnTaoDonNhieuBan = createMenuButton("Tạo đơn nhiều bàn", COLOR_CHUC_NANG[2]);
        btnXemDon = createMenuButton("Xem đơn đặt bàn", COLOR_CHUC_NANG[3]);
        btnCheckIn = createMenuButton("Check-in", COLOR_CHUC_NANG[4]);
        btnChuyenBan = createMenuButton("Chuyển bàn", COLOR_CHUC_NANG[5]);
        btnHuyBan = createMenuButton("Hủy bàn", COLOR_CHUC_NANG[6]);

        pnlMenuTrai.add(btnDatBanNgay);
        pnlMenuTrai.add(Box.createVerticalStrut(10));
        pnlMenuTrai.add(btnTaoDonHen);
        pnlMenuTrai.add(Box.createVerticalStrut(10));
        pnlMenuTrai.add(btnTaoDonNhieuBan);
        pnlMenuTrai.add(Box.createVerticalStrut(10));
        pnlMenuTrai.add(btnXemDon);
        pnlMenuTrai.add(Box.createVerticalStrut(10));
        pnlMenuTrai.add(btnCheckIn);
        pnlMenuTrai.add(Box.createVerticalStrut(10));
        pnlMenuTrai.add(btnChuyenBan);
        pnlMenuTrai.add(Box.createVerticalStrut(10));
        pnlMenuTrai.add(btnHuyBan);
        pnlMenuTrai.add(Box.createVerticalGlue());

        add(pnlMenuTrai, BorderLayout.WEST);

        // Panel Nội dung
        JPanel pnlContent = new JPanel(new BorderLayout(5, 10));
        pnlContent.setBackground(Color.WHITE);

        // Panel Filter (Top)
        pnlTopFilter = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        pnlTopFilter.setBackground(Color.WHITE);

        pnlTopFilter.add(new JLabel("Loại Bàn:"));
        cmbLoaiBan = new JComboBox<>();
        cmbLoaiBan.setPreferredSize(new Dimension(120, 25));
        pnlTopFilter.add(cmbLoaiBan);

        pnlTopFilter.add(new JLabel("Khu Vực:"));
        cmbKhuVuc = new JComboBox<>();
        cmbKhuVuc.setPreferredSize(new Dimension(120, 25));
        pnlTopFilter.add(cmbKhuVuc);

        // Buttons Trạng thái với màu sắc
        btnTatCa = createFilterButton("Tất cả (0)", COLOR_TAT_CA);
        btnTrong = createFilterButton("Trống (0)", COLOR_TRONG);
        btnDaDat = createFilterButton("Đã đặt (0)", COLOR_DA_DAT);
        btnQuaHan = createFilterButton("Quá hạn (0)", COLOR_QUA_HAN);
        btnDangDung = createFilterButton("Đang dùng (0)", COLOR_DANG_DUNG);

        ButtonGroup groupTrangThai = new ButtonGroup();
        groupTrangThai.add(btnTatCa);
        groupTrangThai.add(btnTrong);
        groupTrangThai.add(btnDaDat);
        groupTrangThai.add(btnQuaHan);
        groupTrangThai.add(btnDangDung);
        btnTatCa.setSelected(true);

        pnlTopFilter.add(btnTatCa);
        pnlTopFilter.add(btnTrong);
        pnlTopFilter.add(btnDaDat);
        pnlTopFilter.add(btnQuaHan);
        pnlTopFilter.add(btnDangDung);

        pnlContent.add(pnlTopFilter, BorderLayout.NORTH);

        // Panel Danh Sách Bàn
        pnlDanhSachBan = new JPanel();
        pnlDanhSachBan.setLayout(new GridLayout(0, 5, 15, 15));
        pnlDanhSachBan.setBackground(new Color(240, 240, 240));

        scrollPaneMain = new JScrollPane(pnlDanhSachBan);
        scrollPaneMain.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        scrollPaneMain.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneMain.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPaneMain.getVerticalScrollBar().setUnitIncrement(16);

        pnlContent.add(scrollPaneMain, BorderLayout.CENTER);
        add(pnlContent, BorderLayout.CENTER);

        loadDataComboBoxes();
        loadDataBanAn();
        addListeners();
        updateTrangThaiNutChucNang();
    }

    private JButton createMenuButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Tahoma", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        button.setPreferredSize(new Dimension(180, 45));
        button.setMargin(new Insets(5, 15, 5, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bgColor.darker(), 1, true),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));

        return button;
    }

    private JButton createFilterButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Tahoma", Font.BOLD, 13));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(130, 35));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bgColor.darker(), 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        return button;
    }

    private void loadDataComboBoxes() {
        cmbLoaiBan.addItem("Tất cả");
        List<String> dsLoaiBan = banAnDao.getDsLoaiBan();
        for (String loai : dsLoaiBan) {
            cmbLoaiBan.addItem(loai);
        }

        cmbKhuVuc.addItem("Tất cả");
        List<KhuVuc> dsKhuVuc = khuVucDao.getAllList();
        for (KhuVuc kv : dsKhuVuc) {
            cmbKhuVuc.addItem(kv);
        }

        cmbKhuVuc.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof KhuVuc) {
                    setText(((KhuVuc) value).getTenKhuVuc());
                } else {
                    setText(value.toString());
                }
                return this;
            }
        });
    }

    public void loadDataBanAn() {
        donDatBanDao.capNhatBanQuaHan();
        dsBanAnMaster = banAnDao.getAllList();
        updateSoLuongButtonTrangThai();
        filterAndRenderBan();
        updateTrangThaiNutChucNang();
    }

    private void updateSoLuongButtonTrangThai() {
        long tong = dsBanAnMaster.size();
        long trong = dsBanAnMaster.stream().filter(b -> b.getTrangThai().equals("Trống")).count();
        long daDat = dsBanAnMaster.stream().filter(b -> b.getTrangThai().equals("Đặt trước")).count();
        long quaHan = dsBanAnMaster.stream().filter(b -> b.getTrangThai().equals("Quá hạn")).count();
        long dangDung = dsBanAnMaster.stream().filter(b -> b.getTrangThai().equals("Đang dùng")).count();

        btnTatCa.setText("Tất cả (" + tong + ")");
        btnTrong.setText("Trống (" + trong + ")");
        btnDaDat.setText("Đã đặt (" + daDat + ")");
        btnQuaHan.setText("Quá hạn (" + quaHan + ")");
        btnDangDung.setText("Đang dùng (" + dangDung + ")");
    }

    private List<BanAn> getDanhSachBanAnDaChon() {
        return dsCardHienThi.stream()
                .filter(BanAnCard::isSelected)
                .map(BanAnCard::getBanAn)
                .collect(Collectors.toList());
    }

    private void filterAndRenderBan() {
        String trangThaiFilter = getTrangThaiFilter();
        Object loaiBanFilter = cmbLoaiBan.getSelectedItem();
        Object khuVucFilter = cmbKhuVuc.getSelectedItem();

        List<BanAn> dsHienThi = dsBanAnMaster.stream()
                .filter(ban -> {
                    if (trangThaiFilter.equals("Tất cả")) return true;
                    return ban.getTrangThai().equals(trangThaiFilter);
                })
                .filter(ban -> {
                    if (loaiBanFilter.equals("Tất cả")) return true;
                    return ban.getLoaiBan().equals(loaiBanFilter);
                })
                .filter(ban -> {
                    if (khuVucFilter.equals("Tất cả")) return true;
                    if (khuVucFilter instanceof KhuVuc) {
                        return ban.getKhuVuc().getMaKhuVuc().equals(((KhuVuc) khuVucFilter).getMaKhuVuc());
                    }
                    return false;
                })
                .collect(Collectors.toList());

        pnlDanhSachBan.removeAll();
        dsCardHienThi.clear();
        updateTrangThaiNutChucNang();

        for (BanAn ban : dsHienThi) {
            BanAnCard card = new BanAnCard(ban);
            card.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    boolean isCtrlDown = (e.getModifiersEx() & MouseEvent.CTRL_DOWN_MASK) != 0;

                    if (!isCtrlDown) {
                        dsCardHienThi.forEach(c -> c.setSelected(false));
                        card.setSelected(true);
                    } else {
                        card.setSelected(!card.isSelected());
                    }

                    updateTrangThaiNutChucNang();
                }
            });
            dsCardHienThi.add(card);
            pnlDanhSachBan.add(card);
        }

        pnlDanhSachBan.revalidate();
        pnlDanhSachBan.repaint();
    }

    private String getTrangThaiFilter() {
        if (btnTrong.isSelected()) return "Trống";
        if (btnDaDat.isSelected()) return "Đặt trước";
        if (btnQuaHan.isSelected()) return "Quá hạn";
        if (btnDangDung.isSelected()) return "Đang dùng";
        return "Tất cả";
    }

    private void updateTrangThaiNutChucNang() {
        List<BanAn> dsChon = getDanhSachBanAnDaChon();
        int soLuongChon = dsChon.size();

        if (soLuongChon == 0) {
            setNutChucNang(false, false, false, false, false, false, false);
            return;
        }

        if (soLuongChon == 1) {
            String trangThai = dsChon.get(0).getTrangThai();
            switch (trangThai) {
                case "Trống":
                    setNutChucNang(true, true, true, false, false, false, false);
                    break;
                case "Đặt trước":
                case "Quá hạn":
                    setNutChucNang(false, false, true, true, true, true, true);
                    break;
                case "Đang dùng":
                    setNutChucNang(false, false, true, true, false, true, true);
                    break;
                default:
                    setNutChucNang(false, false, false, false, false, false, false);
                    break;
            }
        } else {
            boolean allTrong = dsChon.stream().allMatch(b -> b.getTrangThai().equals("Trống"));
            boolean allDat = dsChon.stream().allMatch(b -> b.getTrangThai().matches("Đặt trước|Quá hạn"));
            boolean allDung = dsChon.stream().allMatch(b -> b.getTrangThai().equals("Đang dùng"));

            if (allTrong) {
                setNutChucNang(false, false, true, false, false, false, false);
            } else if (allDat) {
                setNutChucNang(false, false, false, true, true, true, true);
            } else if (allDung) {
                setNutChucNang(false, false, false, true, false, true, true);
            } else {
                setNutChucNang(false, false, false, false, false, false, false);
            }
        }
    }

    private void setNutChucNang(boolean datNgay, boolean datHen, boolean datNhieu, boolean xemDon, boolean checkIn, boolean chuyen, boolean huy) {
        btnDatBanNgay.setEnabled(datNgay);
        btnTaoDonHen.setEnabled(datHen);
        btnTaoDonNhieuBan.setEnabled(datNhieu);
        btnXemDon.setEnabled(xemDon);
        btnCheckIn.setEnabled(checkIn);
        btnChuyenBan.setEnabled(chuyen);
        btnHuyBan.setEnabled(huy);
    }

    private void addListeners() {
        cmbLoaiBan.addActionListener(e -> filterAndRenderBan());
        cmbKhuVuc.addActionListener(e -> filterAndRenderBan());
        btnTatCa.addActionListener(e -> filterAndRenderBan());
        btnTrong.addActionListener(e -> filterAndRenderBan());
        btnDaDat.addActionListener(e -> filterAndRenderBan());
        btnQuaHan.addActionListener(e -> filterAndRenderBan());
        btnDangDung.addActionListener(e -> filterAndRenderBan());

        btnDatBanNgay.addActionListener(e -> xuLyDatBanNgay());
        btnTaoDonHen.addActionListener(e -> xuLyTaoDonHen());
        btnTaoDonNhieuBan.addActionListener(e -> xuLyTaoDonNhieuBan());
        btnCheckIn.addActionListener(e -> xuLyCheckIn());
        btnHuyBan.addActionListener(e -> xuLyHuyBan());
        btnXemDon.addActionListener(e -> xuLyXemDon());
        btnChuyenBan.addActionListener(e -> xuLyChuyenBan());
    }

    private void xuLyDatBanNgay() {
        List<BanAn> dsChon = getDanhSachBanAnDaChon();
        if (dsChon.size() != 1 || !dsChon.get(0).getTrangThai().equals("Trống")) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 bàn 'Trống' để đặt ngay.");
            return;
        }

        DialogDatBan dialog = new DialogDatBan(
                (Frame) SwingUtilities.getWindowAncestor(this),
                "Đặt Bàn Ngay",
                dsChon,
                nhanVienHienTai,
                false
        );
        dialog.setVisible(true);

        if (dialog.isSuccess()) {
            loadDataBanAn();
        }
    }

    private void xuLyTaoDonHen() {
        List<BanAn> dsChon = getDanhSachBanAnDaChon();
        if (dsChon.size() != 1 || !dsChon.get(0).getTrangThai().equals("Trống")) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 bàn 'Trống' để tạo đơn hẹn.");
            return;
        }

        DialogDatBan dialog = new DialogDatBan(
                (Frame) SwingUtilities.getWindowAncestor(this),
                "Tạo Đơn Đặt Hẹn",
                dsChon,
                nhanVienHienTai,
                true
        );
        dialog.setVisible(true);

        if (dialog.isSuccess()) {
            loadDataBanAn();
        }
    }

    private void xuLyTaoDonNhieuBan() {
        List<BanAn> dsChon = getDanhSachBanAnDaChon();
        if (dsChon.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ít nhất 1 bàn (giữ Ctrl để chọn).");
            return;
        }
        if (!dsChon.stream().allMatch(b -> b.getTrangThai().equals("Trống"))) {
            JOptionPane.showMessageDialog(this, "Tất cả các bàn được chọn phải 'Trống'.");
            return;
        }

        DialogDatBan dialog = new DialogDatBan(
                (Frame) SwingUtilities.getWindowAncestor(this),
                "Tạo Đơn Nhiều Bàn",
                dsChon,
                nhanVienHienTai,
                true
        );
        dialog.setVisible(true);

        if (dialog.isSuccess()) {
            loadDataBanAn();
        }
    }

    private void xuLyCheckIn() {
        List<BanAn> dsChon = getDanhSachBanAnDaChon();
        if (dsChon.size() != 1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 bàn để check-in.");
            return;
        }
        
        BanAn ban = dsChon.get(0);
        if (!ban.getTrangThai().matches("Đặt trước|Quá hạn")) {
            JOptionPane.showMessageDialog(this, "Chỉ có thể check-in cho bàn 'Đặt trước' hoặc 'Quá hạn'.");
            return;
        }
        
        DonDatBan don = donDatBanDao.getDonHienTaiCuaBan(ban.getMaBanAn());
        if (don == null) {
            JOptionPane.showMessageDialog(this, "Lỗi: Không tìm thấy đơn đặt bàn của bàn này.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (donDatBanDao.checkInDon(don.getMaDonDatBan())) {
            banAnDao.capNhatTrangThai(ban.getMaBanAn(), "Đang dùng");
            JOptionPane.showMessageDialog(this, "Check-in bàn " + ban.getTenBanAn() + " thành công!");
            loadDataBanAn();
        } else {
            JOptionPane.showMessageDialog(this, "Check-in thất bại do lỗi CSDL.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xuLyHuyBan() {
        List<BanAn> dsChon = getDanhSachBanAnDaChon();
        if (dsChon.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn (các) bàn muốn hủy.");
            return;
        }
        if (dsChon.stream().anyMatch(b -> b.getTrangThai().equals("Trống"))) {
             JOptionPane.showMessageDialog(this, "Không thể hủy bàn 'Trống'.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn HỦY " + dsChon.size() + " bàn đã chọn?\n" +
                        "Đơn đặt bàn của (các) bàn này sẽ bị vô hiệu hóa.\n" +
                        "(Các) bàn sẽ được chuyển về trạng thái 'Trống'.",
                "Xác nhận Hủy Bàn",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            int demThanhCong = 0;
            for (BanAn ban : dsChon) {
                DonDatBan don = donDatBanDao.getDonHienTaiCuaBan(ban.getMaBanAn());
                if (don != null) {
                    donDatBanDao.huyDon(don.getMaDonDatBan());
                    banAnDao.capNhatTrangThai(ban.getMaBanAn(), "Trống");
                    demThanhCong++;
                }
            }
            JOptionPane.showMessageDialog(this, "Đã hủy thành công " + demThanhCong + " bàn.");
            loadDataBanAn();
        }
    }

    private void xuLyXemDon() {
        List<BanAn> dsChon = getDanhSachBanAnDaChon();
        if (dsChon.size() != 1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 bàn để xem đơn.");
            return;
        }
        
        BanAn ban = dsChon.get(0);
         if (ban.getTrangThai().equals("Trống")) {
            JOptionPane.showMessageDialog(this, "Bàn 'Trống' không có đơn để xem.");
            return;
        }
        
        DonDatBan don = donDatBanDao.getDonHienTaiCuaBan(ban.getMaBanAn());
        if (don == null) {
            JOptionPane.showMessageDialog(this, "Lỗi: Không tìm thấy đơn đặt bàn của bàn này.\n" +
                    "Có thể đơn đã bị hủy hoặc thanh toán.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DialogXemDon dialog = new DialogXemDon((Frame) SwingUtilities.getWindowAncestor(this), don);
        dialog.setVisible(true);
    }

    private void xuLyChuyenBan() {
        List<BanAn> dsChon = getDanhSachBanAnDaChon();
        if (dsChon.size() != 1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 bàn để chuyển.");
            return;
        }
        
        BanAn banCu = dsChon.get(0);
        if (banCu.getTrangThai().equals("Trống")) {
            JOptionPane.showMessageDialog(this, "Không thể chuyển bàn 'Trống'.");
            return;
        }

        DonDatBan don = donDatBanDao.getDonHienTaiCuaBan(banCu.getMaBanAn());
        if (don == null) {
            JOptionPane.showMessageDialog(this, "Lỗi: Không tìm thấy đơn đặt bàn của bàn này.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DialogChuyenBan dialog = new DialogChuyenBan((Frame) SwingUtilities.getWindowAncestor(this), banCu, don);
        dialog.setVisible(true);

        if (dialog.isSuccess()) {
            // Reload toàn bộ data và UI
            loadDataBanAn();
            
            // Bỏ chọn tất cả các card
            dsCardHienThi.forEach(c -> c.setSelected(false));
            updateTrangThaiNutChucNang();
        }
    }
}