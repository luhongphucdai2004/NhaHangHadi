package gui;

import entity.BanAn;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font; // SỬA: Đổi Font
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class BanAnCard extends JPanel {
    private JLabel lblTenBan;
    private JLabel lblTrangThai;
    private JLabel lblChiTiet;
    private BanAn banAn;
    private boolean isSelected = false;

    // Màu sắc
    private static final Color COLOR_TRONG = new Color(46, 204, 113);
    private static final Color COLOR_DA_DAT = new Color(52, 152, 219);
    private static final Color COLOR_DANG_DUNG = new Color(243, 156, 18);
    private static final Color COLOR_QUA_HAN = new Color(231, 76, 60);
    private static final Color COLOR_TEXT_LIGHT = Color.WHITE;
    private static final Color COLOR_TEXT_DARK = new Color(50, 50, 50);
    
    private Color currentBackgroundColor;

    public BanAnCard(BanAn banAn) {
        this.banAn = banAn;
        
        setPreferredSize(new Dimension(200, 130));
        setLayout(new BorderLayout(5, 5));
        setOpaque(false); // Để vẽ bo tròn
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        lblTenBan = new JLabel(banAn.getTenBanAn());
        // SỬA: Dùng "SansSerif"
        lblTenBan.setFont(new Font("SansSerif", Font.BOLD, 18)); 
        lblTenBan.setHorizontalAlignment(SwingConstants.CENTER);
        lblTenBan.setBorder(BorderFactory.createEmptyBorder(8, 5, 0, 5));
        add(lblTenBan, BorderLayout.NORTH);

        lblTrangThai = new JLabel(banAn.getTrangThai());
        // SỬA: Dùng "SansSerif"
        lblTrangThai.setFont(new Font("SansSerif", Font.BOLD, 20)); 
        lblTrangThai.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTrangThai, BorderLayout.CENTER);

        lblChiTiet = new JLabel(banAn.getLoaiBan() + " - " + banAn.getSoChoNgoi() + " chỗ");
        // SỬA: Dùng "SansSerif"
        lblChiTiet.setFont(new Font("SansSerif", Font.ITALIC, 13)); 
        lblChiTiet.setHorizontalAlignment(SwingConstants.CENTER);
        lblChiTiet.setBorder(BorderFactory.createEmptyBorder(0, 5, 8, 5));
        add(lblChiTiet, BorderLayout.SOUTH);

        setTrangThai(banAn.getTrangThai());
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Vẽ nền bo tròn
        g2d.setColor(currentBackgroundColor);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

        // Vẽ viền khi được chọn
        if (isSelected) {
            g2d.setColor(Color.BLACK); 
            g2d.setStroke(new java.awt.BasicStroke(4));
            g2d.drawRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 20, 20);
        } else {
            g2d.setColor(currentBackgroundColor.darker());
            g2d.setStroke(new java.awt.BasicStroke(2));
            g2d.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 20, 20);
        }

        g2d.dispose();
        
        super.paintComponent(g); 
    }

    public BanAn getBanAn() {
        return banAn;
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setTrangThai(String trangThai) {
        banAn.setTrangThai(trangThai);
        lblTrangThai.setText(trangThai);

        switch (trangThai) {
            case "Trống":
                currentBackgroundColor = COLOR_TRONG;
                setColors(COLOR_TEXT_LIGHT);
                break;
            case "Đặt trước":
                currentBackgroundColor = COLOR_DA_DAT;
                setColors(COLOR_TEXT_LIGHT);
                break;
            case "Đang dùng":
                currentBackgroundColor = COLOR_DANG_DUNG;
                setColors(COLOR_TEXT_LIGHT);
                break;
            case "Quá hạn":
                currentBackgroundColor = COLOR_QUA_HAN;
                setColors(COLOR_TEXT_LIGHT);
                break;
            default: // Nếu nhận "Tr?ng" nó sẽ chạy vào đây
            	currentBackgroundColor = COLOR_TRONG;
                setColors(COLOR_TEXT_LIGHT);
                break;
        }
        repaint();
    }

    private void setColors(Color color) {
        lblTenBan.setForeground(color);
        lblTrangThai.setForeground(color);
        lblChiTiet.setForeground(color);
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
        repaint(); 
    }
}