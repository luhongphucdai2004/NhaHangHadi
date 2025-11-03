package gui;

import dao.BanAn_dao;
import dao.DonDatBan_dao;
import entity.BanAn;
import entity.DonDatBan;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class DialogChuyenBan extends JDialog {

    private final BanAn banCu;
    private final DonDatBan donDatBan;
    private final BanAn_dao banAnDao = BanAn_dao.getInstance();
    private final DonDatBan_dao donDatBanDao = DonDatBan_dao.getInstance();

    private JComboBox<BanAn> cmbBanMoi;
    private JButton btnXacNhan, btnHuy;
    private boolean success = false;

    public DialogChuyenBan(Window owner, BanAn banCu, DonDatBan donDatBan) {
        super(owner, "Chuyển Bàn", ModalityType.DOCUMENT_MODAL);
        this.banCu = banCu;
        this.donDatBan = donDatBan;

        setSize(450, 250);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // Panel chính
        JPanel pnlMain = new JPanel(new GridBagLayout());
        pnlMain.setBackground(Color.WHITE);
        pnlMain.setBorder(new EmptyBorder(20, 25, 20, 25));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 5, 8, 15);

        Font labelFont = new Font("Tahoma", Font.PLAIN, 15);
        Font dataFont = new Font("Tahoma", Font.BOLD, 15);

        // Tiêu đề
        JLabel lblTitle = new JLabel("CHUYỂN BÀN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 22));
        lblTitle.setForeground(new Color(241, 196, 15)); // màu vàng
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 5, 20, 5);
        pnlMain.add(lblTitle, gbc);

        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 5, 10, 15);

        // Hàng 1: Bàn hiện tại
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.weightx = 0.4;
        JLabel lblTuBan = new JLabel("Từ bàn:");
        lblTuBan.setFont(labelFont);
        pnlMain.add(lblTuBan, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.6;
        JLabel lblBanCu = new JLabel(banCu.getTenBanAn() + " (" + banCu.getLoaiBan() + ")");
        lblBanCu.setFont(dataFont);
        pnlMain.add(lblBanCu, gbc);

        // Hàng 2: Chuyển đến bàn
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel lblDenBan = new JLabel("Đến bàn (VIP Trống):");
        lblDenBan.setFont(labelFont);
        pnlMain.add(lblDenBan, gbc);

        gbc.gridx = 1;
        cmbBanMoi = new JComboBox<>();
        cmbBanMoi.setFont(dataFont);
        pnlMain.add(cmbBanMoi, gbc);

        add(pnlMain, BorderLayout.CENTER);

        // Panel nút
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        pnlButtons.setBackground(Color.WHITE);
        pnlButtons.setBorder(new EmptyBorder(0, 10, 10, 25));

        btnHuy = new JButton("Hủy");
        btnHuy.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnHuy.setBackground(new Color(231, 76, 60));
        btnHuy.setForeground(Color.WHITE);
        btnHuy.setPreferredSize(new Dimension(120, 40));
        btnHuy.setFocusPainted(false);
        btnHuy.setBorder(null);

        btnXacNhan = new JButton("Xác Nhận");
        btnXacNhan.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnXacNhan.setBackground(new Color(46, 204, 113));
        btnXacNhan.setForeground(Color.WHITE);
        btnXacNhan.setPreferredSize(new Dimension(120, 40));
        btnXacNhan.setFocusPainted(false);
        btnXacNhan.setBorder(null);

        pnlButtons.add(btnHuy);
        pnlButtons.add(btnXacNhan);
        add(pnlButtons, BorderLayout.SOUTH);

        loadDataComboBox();
        addListeners();
    }

    /**
     * Chỉ hiển thị các bàn "VIP" có trạng thái "Trống"
     */
    private void loadDataComboBox() {
        List<BanAn> dsBanTrong = banAnDao.getListByTrangThai("Trống");

        // Lọc theo loại bàn VIP
        List<BanAn> dsVipTrong = dsBanTrong.stream()
                .filter(banMoi -> banMoi.getLoaiBan().equalsIgnoreCase("VIP"))
                .collect(Collectors.toList());

        if (dsVipTrong.isEmpty()) {
            cmbBanMoi.setModel(new DefaultComboBoxModel<>(new Vector<>()));
            cmbBanMoi.addItem(null);
            cmbBanMoi.setEnabled(false);
            btnXacNhan.setEnabled(false);
            JOptionPane.showMessageDialog(this, "Không có bàn VIP trống để chuyển!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        cmbBanMoi.setModel(new DefaultComboBoxModel<>(new Vector<>(dsVipTrong)));

        cmbBanMoi.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof BanAn b) {
                    setText(b.getTenBanAn() + " (" + b.getLoaiBan() + ")");
                }
                return this;
            }
        });
    }

    private void addListeners() {
        btnHuy.addActionListener(e -> dispose());

        btnXacNhan.addActionListener(e -> {
            BanAn banMoi = (BanAn) cmbBanMoi.getSelectedItem();
            if (banMoi == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn bàn muốn chuyển đến.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    String.format("Xác nhận chuyển từ bàn %s sang bàn %s (VIP)?", banCu.getTenBanAn(), banMoi.getTenBanAn()),
                    "Xác nhận chuyển bàn", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                String trangThaiCu = banCu.getTrangThai();

                boolean rs = donDatBanDao.chuyenBan(
                        donDatBan.getMaDonDatBan(),
                        banCu.getMaBanAn(),
                        banMoi,
                        trangThaiCu
                );

                if (rs) {
                    // CẬP NHẬT: Refresh lại object banCu từ database
                    BanAn banCuMoi = banAnDao.selectById(banCu);
                    if (banCuMoi != null) {
                        banCu.setTrangThai(banCuMoi.getTrangThai()); // Update trạng thái
                    }
                    
                    JOptionPane.showMessageDialog(this, "Chuyển bàn thành công!");
                    success = true;
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Chuyển bàn thất bại. Vui lòng thử lại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public boolean isSuccess() {
        return success;
    }
}
