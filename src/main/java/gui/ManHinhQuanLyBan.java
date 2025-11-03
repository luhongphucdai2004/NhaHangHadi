package gui;

import dao.BanAn_dao;
import dao.DonDatBan_dao;
import dao.KhuVuc_dao;
import entity.BanAn;
import entity.KhuVuc;

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
    private BanAnCard banAnDaChon;

    // Màu sắc cho các button trạng thái
    private final Color COLOR_TAT_CA = Color.BLACK;
    private final Color COLOR_TRONG = new Color(46, 204, 113);
    private final Color COLOR_DA_DAT = new Color(52, 152, 219);
    private final Color COLOR_QUA_HAN = new Color(231, 76, 60);
    private final Color COLOR_DANG_DUNG = new Color(243, 156, 18);

    // Màu sắc cho các button chức năng
    private final Color[] COLOR_CHUC_NANG = {
        new Color(155, 89, 182),  // Đặt bàn ngay - Tím
        new Color(52, 152, 219),  // Tạo đơn hẹn - Xanh dương
        new Color(26, 188, 156),  // Tạo đơn nhiều bàn - Xanh lơ
        new Color(230, 126, 34),  // Xem đơn - Cam
        new Color(46, 204, 113),  // Check-in - Xanh lá
        new Color(241, 196, 15),  // Chuyển bàn - Vàng
        new Color(231, 76, 60)    // Hủy bàn - Đỏ
    };

    public ManHinhQuanLyBan() {
        banAnDao = BanAn_dao.getInstance();
        khuVucDao = KhuVuc_dao.getInstance();
        donDatBanDao = DonDatBan_dao.getInstance();
        
        dsBanAnMaster = new ArrayList<>();
        dsCardHienThi = new ArrayList<>();
        banAnDaChon = null;

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

        // Panel Danh Sách Bàn - Thay đổi từ 6 cột thành 5 cột
        pnlDanhSachBan = new JPanel();
        pnlDanhSachBan.setLayout(new GridLayout(0, 5, 15, 15)); // 5 cột thay vì 6
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
        updateTrangThaiNutChucNang(null);
    }

    // Tạo nút menu với màu sắc và bo góc
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
        
        // Bo tròn góc
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bgColor.darker(), 1, true),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        
        return button;
    }

    // Tạo nút filter với màu sắc và bo góc
    private JButton createFilterButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Tahoma", Font.BOLD, 13));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(130, 35));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Bo tròn góc
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
        banAnDaChon = null;
        updateTrangThaiNutChucNang(null);
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
        banAnDaChon = null;
        updateTrangThaiNutChucNang(null);

        for (BanAn ban : dsHienThi) {
            BanAnCard card = new BanAnCard(ban);
            card.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (banAnDaChon != null) {
                        banAnDaChon.setSelected(false);
                    }
                    banAnDaChon = card;
                    banAnDaChon.setSelected(true);
                    updateTrangThaiNutChucNang(card.getBanAn().getTrangThai());
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

    private void updateTrangThaiNutChucNang(String trangThai) {
        if (trangThai == null) {
            setNutChucNang(true, true, true, true, true, true, true);
            return;
        }

        switch (trangThai) {
            case "Trống":
                setNutChucNang(true, true, true, false, false, false, false);
                break;
            case "Đặt trước":
            case "Quá hạn":
                setNutChucNang(false, false, false, true, true, true, true);
                break;
            case "Đang dùng":
                setNutChucNang(false, false, false, true, false, true, true);
                break;
            default:
                setNutChucNang(false, false, false, false, false, false, false);
                break;
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

        btnDatBanNgay.addActionListener(e -> {
            if (banAnDaChon == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một bàn TRỐNG.");
                return;
            }
            JOptionPane.showMessageDialog(this, "Chức năng: Đặt bàn ngay cho " + banAnDaChon.getBanAn().getTenBanAn());
        });

        btnTaoDonHen.addActionListener(e -> {
            if (banAnDaChon == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một bàn TRỐNG.");
                return;
            }
            JOptionPane.showMessageDialog(this, "Chức năng: Tạo đơn đặt hẹn cho " + banAnDaChon.getBanAn().getTenBanAn());
        });

        btnTaoDonNhieuBan.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Chức năng: Tạo đơn nhiều bàn...");
        });

        btnCheckIn.addActionListener(e -> {
            if (banAnDaChon == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một bàn ĐÃ ĐẶT hoặc QUÁ HẠN.");
                return;
            }
            JOptionPane.showMessageDialog(this, "Chức năng: Check-in cho " + banAnDaChon.getBanAn().getTenBanAn());
        });

        btnHuyBan.addActionListener(e -> {
            if (banAnDaChon == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một bàn ĐÃ ĐẶT, QUÁ HẠN hoặc ĐANG DÙNG.");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Bạn có chắc muốn hủy/trả bàn " + banAnDaChon.getBanAn().getTenBanAn() + "?\n" +
                "Bàn sẽ được chuyển về trạng thái 'Trống'.", 
                "Xác nhận hủy bàn", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.WARNING_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                donDatBanDao.chuyenBanVeTrangThaiTrong(banAnDaChon.getBanAn().getMaBanAn());
                loadDataBanAn();
            }
        });

        btnXemDon.addActionListener(e -> {
            if (banAnDaChon == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một bàn ĐÃ ĐẶT, QUÁ HẠN hoặc ĐANG DÙNG.");
                return;
            }
            JOptionPane.showMessageDialog(this, "Chức năng: Xem đơn của bàn " + banAnDaChon.getBanAn().getTenBanAn());
        });

        btnChuyenBan.addActionListener(e -> {
            if (banAnDaChon == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một bàn ĐÃ ĐẶT, QUÁ HẠN hoặc ĐANG DÙNG.");
                return;
            }
            JOptionPane.showMessageDialog(this, "Chức năng: Chuyển bàn " + banAnDaChon.getBanAn().getTenBanAn());
        });
    }
}