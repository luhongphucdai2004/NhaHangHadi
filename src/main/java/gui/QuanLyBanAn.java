package gui; // Hoặc package của bạn

import dao.BanAn_dao;   // THÊM DAO
import dao.KhuVuc_dao;  // THÊM DAO
import entity.BanAn;    // THÊM ENTITY
import entity.KhuVuc;   // THÊM ENTITY

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader; 
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector; 

public class QuanLyBanAn extends JPanel {

    // --- Components ---
    private JTextField txtMaBan;
    private JTextField txtTenBan;
    private JTextField txtSoChoNgoi;
    private JComboBox<KhuVuc> comboKhuVuc; 
    private JComboBox<String> comboTrangThai;
    private JComboBox<String> comboLoaiBan;
    private JTextField txtGhiChu; 

    private JButton btnThem;
    private JButton btnCapNhat;
    private JButton btnXoa;
    private JButton btnLamMoi; 

    private JTable tableBanAn;
    private DefaultTableModel modelBanAn;

    // --- DAO (Đã kết nối) ---
    private BanAn_dao banAnDao;
    private KhuVuc_dao khuVucDao;
    
    // THÊM: Màu xanh dương nhạt mới
    private final Color LIGHT_BLUE = new Color(173, 216, 230); // LightBlue
    private final Color HEADER_TEXT_COLOR = Color.BLACK; // Màu chữ đen cho header

    public QuanLyBanAn() {
        // Khởi tạo DAO
        banAnDao = BanAn_dao.getInstance();
        khuVucDao = KhuVuc_dao.getInstance();

        initComponents();
        addListeners();
        
        loadDataToComboBoxes(); 
        loadDataToTable();      
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // --- Panel Nội dung Phía Trên (Title, Form, Buttons) ---
        JPanel pnlTop = new JPanel(new BorderLayout(10, 15));
        pnlTop.setBackground(Color.WHITE);

        // 1. Tiêu đề
        JLabel lblTitle = new JLabel("THÔNG TIN BÀN ĂN");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        pnlTop.add(lblTitle, BorderLayout.NORTH);

        // 2. Panel Form Nhập liệu (Giữ nguyên)
        JPanel pnlInput = createInputPanel();
        pnlTop.add(pnlInput, BorderLayout.CENTER);

        // 3. Panel Chức năng (Buttons) - SỬA LẠI
        JPanel pnlButtons = createButtonPanel();
        pnlTop.add(pnlButtons, BorderLayout.SOUTH);

        add(pnlTop, BorderLayout.NORTH);

        // --- Panel Dữ liệu Phía Dưới (Table) ---
        JPanel pnlTable = createTablePanel();
        add(pnlTable, BorderLayout.CENTER);
    }

    private JPanel createInputPanel() {
        JPanel pnlInput = new JPanel(new GridBagLayout());
        pnlInput.setBackground(Color.WHITE);
        pnlInput.setBorder(new TitledBorder(null, "Thông tin chi tiết", TitledBorder.LEADING, TitledBorder.TOP, null, null));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8); 
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.anchor = GridBagConstraints.WEST; 

        Font labelFont = new Font("SansSerif", Font.PLAIN, 14);
        Font textFont = new Font("SansSerif", Font.PLAIN, 14);

        // Hàng 0
        // --- Khu vực ---
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.0;
        JLabel lblKhuVuc = new JLabel("Khu vực:");
        lblKhuVuc.setFont(labelFont);
        pnlInput.add(lblKhuVuc, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0; 
        comboKhuVuc = new JComboBox<>();
        comboKhuVuc.setFont(textFont);
        pnlInput.add(comboKhuVuc, gbc);

        // --- Tên bàn ăn ---
        gbc.gridx = 2; gbc.weightx = 0.0; 
        JLabel lblTenBan = new JLabel("Tên bàn ăn:");
        lblTenBan.setFont(labelFont);
        pnlInput.add(lblTenBan, gbc);

        gbc.gridx = 3; gbc.weightx = 1.0;
        txtTenBan = new JTextField();
        txtTenBan.setFont(textFont);
        pnlInput.add(txtTenBan, gbc);

        // --- Trạng thái ---
        gbc.gridx = 4; gbc.weightx = 0.0;
        JLabel lblTrangThai = new JLabel("Trạng thái:");
        lblTrangThai.setFont(labelFont);
        pnlInput.add(lblTrangThai, gbc);

        gbc.gridx = 5; gbc.weightx = 1.0;
        comboTrangThai = new JComboBox<>();
        comboTrangThai.setFont(textFont);
        pnlInput.add(comboTrangThai, gbc);

        // Hàng 1
        // --- Mã bàn ăn ---
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.0;
        JLabel lblMaBan = new JLabel("Mã bàn ăn:");
        lblMaBan.setFont(labelFont);
        pnlInput.add(lblMaBan, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        txtMaBan = new JTextField();
        txtMaBan.setFont(textFont);
        pnlInput.add(txtMaBan, gbc);

        // --- Số chỗ ngồi ---
        gbc.gridx = 2; gbc.weightx = 0.0;
        JLabel lblSoChoNgoi = new JLabel("Số chỗ ngồi:");
        lblSoChoNgoi.setFont(labelFont);
        pnlInput.add(lblSoChoNgoi, gbc);

        gbc.gridx = 3; gbc.weightx = 1.0;
        txtSoChoNgoi = new JTextField();
        txtSoChoNgoi.setFont(textFont);
        pnlInput.add(txtSoChoNgoi, gbc);

        // --- Loại bàn ---
        gbc.gridx = 4; gbc.weightx = 0.0;
        JLabel lblLoaiBan = new JLabel("Loại bàn:");
        lblLoaiBan.setFont(labelFont);
        pnlInput.add(lblLoaiBan, gbc);

        gbc.gridx = 5; gbc.weightx = 1.0;
        comboLoaiBan = new JComboBox<>();
        comboLoaiBan.setFont(textFont);
        pnlInput.add(comboLoaiBan, gbc);
        
        // Hàng 2 (Ghi chú)
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.0;
        JLabel lblGhiChu = new JLabel("Ghi chú:");
        lblGhiChu.setFont(labelFont);
        pnlInput.add(lblGhiChu, gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 5; 
        txtGhiChu = new JTextField();
        txtGhiChu.setFont(textFont);
        pnlInput.add(txtGhiChu, gbc);

        return pnlInput;
    }

    private JPanel createButtonPanel() {
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        pnlButtons.setBackground(Color.WHITE);

        Font buttonFont = new Font("SansSerif", Font.BOLD, 14);
        Dimension buttonSize = new Dimension(120, 40);

        // SỬA: Đặt màu nền xanh dương nhạt cho tất cả các nút
        btnThem = new JButton("THÊM");
        btnThem.setFont(buttonFont);
        btnThem.setPreferredSize(buttonSize);
        btnThem.setBackground(LIGHT_BLUE); 
        btnThem.setForeground(HEADER_TEXT_COLOR); // Chữ đen
        pnlButtons.add(btnThem);

        btnCapNhat = new JButton("CẬP NHẬT");
        btnCapNhat.setFont(buttonFont);
        btnCapNhat.setPreferredSize(buttonSize);
        btnCapNhat.setBackground(LIGHT_BLUE); 
        btnCapNhat.setForeground(HEADER_TEXT_COLOR); // Chữ đen
        pnlButtons.add(btnCapNhat);

        btnXoa = new JButton("XÓA");
        btnXoa.setFont(buttonFont);
        btnXoa.setPreferredSize(buttonSize);
        btnXoa.setBackground(LIGHT_BLUE); 
        btnXoa.setForeground(HEADER_TEXT_COLOR); // Chữ đen
        pnlButtons.add(btnXoa);

        btnLamMoi = new JButton("XÓA RỖNG"); 
        btnLamMoi.setFont(buttonFont);
        btnLamMoi.setPreferredSize(buttonSize);
        btnLamMoi.setBackground(LIGHT_BLUE); 
        btnLamMoi.setForeground(HEADER_TEXT_COLOR); // Chữ đen
        pnlButtons.add(btnLamMoi);

        return pnlButtons;
    }

    private JPanel createTablePanel() {
        JPanel pnlTable = new JPanel(new BorderLayout());
        pnlTable.setBackground(Color.WHITE);
        pnlTable.setBorder(new TitledBorder(null, "Danh sách bàn ăn", TitledBorder.LEADING, TitledBorder.TOP, null, null));

        String[] columnNames = {"Mã bàn ăn", "Tên bàn ăn", "Số chỗ ngồi", "Khu vực", "Loại bàn", "Trạng thái", "Ghi chú"};
        modelBanAn = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        tableBanAn = new JTable(modelBanAn);
        tableBanAn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tableBanAn.setRowHeight(28);
        tableBanAn.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // SỬA: Đặt màu nền xanh dương nhạt cho Header và màu chữ đen
        JTableHeader header = tableBanAn.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 14));
        header.setBackground(LIGHT_BLUE); // Màu nền xanh dương nhạt
        header.setForeground(HEADER_TEXT_COLOR); // Màu chữ đen
        header.setPreferredSize(new Dimension(100, 30));
        
        tableBanAn.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS); 
        
        tableBanAn.getColumnModel().getColumn(0).setPreferredWidth(100); 
        tableBanAn.getColumnModel().getColumn(1).setPreferredWidth(150); 
        tableBanAn.getColumnModel().getColumn(2).setPreferredWidth(100); 
        tableBanAn.getColumnModel().getColumn(3).setPreferredWidth(150); 
        tableBanAn.getColumnModel().getColumn(4).setPreferredWidth(100); 
        tableBanAn.getColumnModel().getColumn(5).setPreferredWidth(100); 
        tableBanAn.getColumnModel().getColumn(6).setPreferredWidth(200); 

        JScrollPane scrollPane = new JScrollPane(tableBanAn);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        pnlTable.add(scrollPane, BorderLayout.CENTER);
        return pnlTable;
    }

    private void addListeners() {
        // --- Sự kiện cho Bảng ---
        tableBanAn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = tableBanAn.getSelectedRow();
                if (selectedRow >= 0) {
                    txtMaBan.setText(modelBanAn.getValueAt(selectedRow, 0).toString());
                    txtTenBan.setText(modelBanAn.getValueAt(selectedRow, 1).toString());
                    txtSoChoNgoi.setText(modelBanAn.getValueAt(selectedRow, 2).toString());
                    
                    String tenKhuVuc = modelBanAn.getValueAt(selectedRow, 3).toString();
                    for (int i = 0; i < comboKhuVuc.getItemCount(); i++) {
                        if (comboKhuVuc.getItemAt(i).getTenKhuVuc().equals(tenKhuVuc)) {
                            comboKhuVuc.setSelectedIndex(i);
                            break;
                        }
                    }
                    
                    comboLoaiBan.setSelectedItem(modelBanAn.getValueAt(selectedRow, 4));
                    comboTrangThai.setSelectedItem(modelBanAn.getValueAt(selectedRow, 5));
                    
                    Object ghiChuObj = modelBanAn.getValueAt(selectedRow, 6);
                    txtGhiChu.setText(ghiChuObj != null ? ghiChuObj.toString() : "");

                    txtMaBan.setEditable(false); 
                    btnThem.setEnabled(false);
                }
            }
        });

        // --- Sự kiện cho Nút ---
        btnLamMoi.addActionListener(e -> lamMoi()); 
        btnThem.addActionListener(e -> themBanAn());
        btnCapNhat.addActionListener(e -> capNhatBanAn());
        btnXoa.addActionListener(e -> xoaBanAn());
    }

    // --- CÁC HÀM XỬ LÝ NGHIỆP VỤ (Giữ nguyên) ---
    
    public void loadDataToTable() {
        modelBanAn.setRowCount(0); 
        List<BanAn> dsBan = banAnDao.getAllList();
        
        for (BanAn ban : dsBan) {
            modelBanAn.addRow(new Object[]{
                ban.getMaBanAn(),
                ban.getTenBanAn(),
                ban.getSoChoNgoi(),
                ban.getKhuVuc().getTenKhuVuc(), 
                ban.getLoaiBan(),
                ban.getTrangThai(),
                ban.getGhiChu() 
            });
        }
    }

    public void loadDataToComboBoxes() {
        comboKhuVuc.removeAllItems();
        List<KhuVuc> dsKV = khuVucDao.getAllList();
        Vector<KhuVuc> vecKhuVuc = new Vector<>(dsKV);
        comboKhuVuc.setModel(new DefaultComboBoxModel<>(vecKhuVuc));
        
        comboKhuVuc.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof KhuVuc) {
                    setText(((KhuVuc) value).getTenKhuVuc());
                }
                return this;
            }
        });

        comboLoaiBan.removeAllItems();
        List<String> dsLoaiBan = banAnDao.getDsLoaiBan();
        for (String loai : dsLoaiBan) {
            comboLoaiBan.addItem(loai);
        }
        if (!dsLoaiBan.contains("Thường")) comboLoaiBan.addItem("Thường");
        if (!dsLoaiBan.contains("VIP")) comboLoaiBan.addItem("VIP");


        comboTrangThai.removeAllItems();
        comboTrangThai.addItem("Trống");
        comboTrangThai.addItem("Đang dùng");
        comboTrangThai.addItem("Đặt trước");
        comboTrangThai.addItem("Quá hạn");
    }

    private void lamMoi() {
        txtMaBan.setText("");
        txtTenBan.setText("");
        txtSoChoNgoi.setText("");
        txtGhiChu.setText(""); 
        
        if (comboKhuVuc.getItemCount() > 0) comboKhuVuc.setSelectedIndex(0);
        if (comboLoaiBan.getItemCount() > 0) comboLoaiBan.setSelectedIndex(0);
        if (comboTrangThai.getItemCount() > 0) comboTrangThai.setSelectedIndex(0);
        
        tableBanAn.clearSelection();
        txtMaBan.setEditable(true);
        btnThem.setEnabled(true);
    }

    private void themBanAn() {
        // 1. Lấy dữ liệu từ form
        String ma = txtMaBan.getText().trim();
        String ten = txtTenBan.getText().trim();
        String soChoStr = txtSoChoNgoi.getText().trim();
        KhuVuc khuVuc = (KhuVuc) comboKhuVuc.getSelectedItem(); 
        String loaiBan = comboLoaiBan.getSelectedItem().toString();
        String trangThai = comboTrangThai.getSelectedItem().toString();
        String ghiChu = txtGhiChu.getText().trim();

        // 2. Validate
        if (ma.isEmpty() || ten.isEmpty() || soChoStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ Mã, Tên, Số chỗ ngồi.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int soCho;
        try {
            soCho = Integer.parseInt(soChoStr);
            if (soCho <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số chỗ ngồi phải là một số nguyên dương.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 3. Tạo đối tượng
        BanAn ban = new BanAn(ma, ten, soCho, khuVuc, trangThai, loaiBan, ghiChu);

        // 4. Gọi DAO để thêm
        try {
            if (banAnDao.them(ban) > 0) {
                JOptionPane.showMessageDialog(this, "Thêm bàn ăn thành công.");
                loadDataToTable(); 
                lamMoi();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            String message = "Thêm thất bại (Lỗi SQL).";
            if (e.getMessage().contains("PRIMARY KEY")) {
                message = "Thêm thất bại (Trùng mã bàn ăn).";
            }
            JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void capNhatBanAn() {
        int selectedRow = tableBanAn.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một bàn trong bảng để cập nhật.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 1. Lấy dữ liệu từ form
        String ma = txtMaBan.getText().trim(); 
        String ten = txtTenBan.getText().trim();
        String soChoStr = txtSoChoNgoi.getText().trim();
        KhuVuc khuVuc = (KhuVuc) comboKhuVuc.getSelectedItem();
        String loaiBan = comboLoaiBan.getSelectedItem().toString();
        String trangThai = comboTrangThai.getSelectedItem().toString();
        String ghiChu = txtGhiChu.getText().trim();

        // 2. Validate
        if (ten.isEmpty() || soChoStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên và Số chỗ ngồi không được để trống.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int soCho;
        try {
            soCho = Integer.parseInt(soChoStr);
            if (soCho <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số chỗ ngồi phải là một số nguyên dương.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 3. Tạo đối tượng và gọi DAO
        BanAn ban = new BanAn(ma, ten, soCho, khuVuc, trangThai, loaiBan, ghiChu);
        
        if (banAnDao.capNhat(ban) > 0) {
            JOptionPane.showMessageDialog(this, "Cập nhật thành công.");
            loadDataToTable(); 
            lamMoi();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xoaBanAn() {
        int selectedRow = tableBanAn.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một bàn trong bảng để xóa.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String maBan = txtMaBan.getText();
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc muốn xóa bàn " + maBan + "?", 
            "Xác nhận xóa", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            // 1. Tạo đối tượng BanAn chỉ với mã
            BanAn banXoa = new BanAn(maBan);
            
            // 2. Gọi DAO
            if (banAnDao.xoa(banXoa) > 0) {
                JOptionPane.showMessageDialog(this, "Xóa thành công.");
                loadDataToTable();
                lamMoi();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại (có thể do bàn đang được đặt hoặc liên kết với hóa đơn).", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}