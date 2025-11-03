package dao;

import connect.ConnectDB;
import entity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DonDatBan_dao implements DAOInterface<DonDatBan> {

    private static DonDatBan_dao instance;
    public static DonDatBan_dao getInstance() {
        if (instance == null)
            instance = new DonDatBan_dao();
        return instance;
    }

    private ChiTietDonDatBan_dao chiTietDao = new ChiTietDonDatBan_dao();
    private BanAn_dao banAnDao = BanAn_dao.getInstance();
    private KhachHang_dao khachHangDao = KhachHang_dao.getInstance();
    private NhanVien_dao nhanVienDao = NhanVien_dao.getInstance();

    // --- Triển khai Interface (CRUD cơ bản) ---
    // Lưu ý: hàm them, capNhat, xoa này là CRUD RẤT CƠ BẢN,
    // nó không xử lý các chi tiết (ChiTietDonDatBan)
    // Các hàm insert và insertAndCheckIn của bạn là đúng cho logic nghiệp vụ

    @Override
    public int them(DonDatBan don) throws SQLException {
        String sql = "INSERT INTO DonDatBan (maDonDatBan, maKhachHang, maNhanVien, ngayDat, ngayTaoDon) VALUES (?, ?, ?, ?, GETDATE())";
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, don.getMaDonDatBan());
            ps.setString(2, don.getKhachHang().getMaKhachHang());
            ps.setString(3, don.getNhanVien().getMaNhanVien());
            ps.setTimestamp(4, Timestamp.valueOf(don.getNgayDat()));
            return ps.executeUpdate();
        }
    }

    @Override
    public int capNhat(DonDatBan don) {
        String sql = "UPDATE DonDatBan SET maKhachHang = ?, maNhanVien = ?, ngayDat = ?, thoiGianCheckIn = ? WHERE maDonDatBan = ?";
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, don.getKhachHang().getMaKhachHang());
            ps.setString(2, don.getNhanVien().getMaNhanVien());
            ps.setTimestamp(3, Timestamp.valueOf(don.getNgayDat()));
            ps.setTimestamp(4, don.getThoiGianCheckIn() != null ? Timestamp.valueOf(don.getThoiGianCheckIn()) : null);
            ps.setString(6, don.getMaDonDatBan());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int xoa(DonDatBan don) {
        // Cảnh báo: Xóa đơn cần phải xóa chi tiết trước (Transaction)
        // Hàm này chỉ xóa đơn, có thể lỗi nếu có khóa ngoại
        String sql = "DELETE FROM DonDatBan WHERE maDonDatBan = ?";
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, don.getMaDonDatBan());
            return ps.executeUpdate();
        } catch (SQLException e) {
            // e.printStackTrace(); // Lỗi khóa ngoại sẽ xảy ra ở đây
            System.err.println("Lỗi xóa đơn: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public List<DonDatBan> getAllList() {
        List<DonDatBan> list = new ArrayList<>();
        String sql = "SELECT * FROM DonDatBan";
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                DonDatBan don = mapDonDatBan(rs);
                list.add(don);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<DonDatBan> chon_AllList() {
        return getAllList();
    }

    @Override
    public DonDatBan selectById(DonDatBan don) {
        String sql = "SELECT * FROM DonDatBan WHERE maDonDatBan = ?";
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, don.getMaDonDatBan());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapDonDatBan(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Hàm helper để map ResultSet sang DonDatBan (bao gồm cả chi tiết)
    private DonDatBan mapDonDatBan(ResultSet rs) throws SQLException {
        String maDon = rs.getString("maDonDatBan");

        // Lấy KhachHang, NhanVien
        KhachHang kh = khachHangDao.selectById(new KhachHang(rs.getString("maKhachHang")));
        NhanVien nv = nhanVienDao.selectById(new NhanVien(rs.getString("maNhanVien")));
        
        // Lấy danh sách chi tiết
        List<ChiTietDonDatBan> dsChiTiet = chiTietDao.getListTheoDon(maDon);

        DonDatBan don = new DonDatBan(
                maDon,
                kh,
                nv,
                rs.getTimestamp("ngayDat").toLocalDateTime(),
                rs.getTimestamp("ngayTaoDon").toLocalDateTime(),
                rs.getTimestamp("thoiGianCheckIn") != null ? rs.getTimestamp("thoiGianCheckIn").toLocalDateTime() : null,
                dsChiTiet // Gán ds chi tiết
        );
        
        // Set lại đơn cha cho các chi tiết
        for(ChiTietDonDatBan ct : dsChiTiet) {
            ct.setDonDatBan(don);
        }
        
        return don;
    }

    // --- Các hàm LOGIC nghiệp vụ (Giữ nguyên) ---

    /**
     * Thêm đơn đặt bàn và các chi tiết (bàn + tiền cọc)
     */
    public boolean insert(DonDatBan don, List<ChiTietDonDatBan> dsChiTiet) {
        String sqlInsert = """
                INSERT INTO DonDatBan (
                    maDonDatBan, maKhachHang, maNhanVien,
                    ngayDat, ngayTaoDon
                )
                VALUES (?, ?, ?, ?, GETDATE())
                """;

        try (Connection con = ConnectDB.getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement ps1 = con.prepareStatement(sqlInsert)) {

                if (don.getKhachHang() == null || don.getKhachHang().getMaKhachHang() == null)
                    throw new SQLException("Thiếu thông tin khách hàng!");

                ps1.setString(1, don.getMaDonDatBan());
                ps1.setString(2, don.getKhachHang().getMaKhachHang());
                ps1.setString(3, don.getNhanVien().getMaNhanVien());
                ps1.setTimestamp(4, Timestamp.valueOf(don.getNgayDat()));
                ps1.executeUpdate();

                for (ChiTietDonDatBan ct : dsChiTiet) {
                    ct.setDonDatBan(don);
                    chiTietDao.them(con, ct); // Dùng connection chung
                    banAnDao.capNhatTrangThai(ct.getMaBanAn(), "Đặt trước");
                }

                con.commit();
                return true;

            } catch (SQLException e) {
                con.rollback();
                System.err.println("❌ Lỗi khi thêm đơn đặt trước: " + e.getMessage());
                return false;
            } finally {
                con.setAutoCommit(true);
            }

        } catch (SQLException e) {
            System.err.println("❌ Lỗi kết nối CSDL: " + e.getMessage());
            return false;
        }
    }

    /**
     * Đặt ngay (Check-in)
     */
    public boolean insertAndCheckIn(DonDatBan don, List<ChiTietDonDatBan> dsChiTiet) {
        String sqlInsert = """
                INSERT INTO DonDatBan (
                    maDonDatBan, maKhachHang, maNhanVien,
                    ngayDat, ngayTaoDon, thoiGianCheckIn
                )
                VALUES (?, ?, ?, GETDATE(), GETDATE(), GETDATE())
                """;

        try (Connection con = ConnectDB.getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement ps1 = con.prepareStatement(sqlInsert)) {

                ps1.setString(1, don.getMaDonDatBan());
                ps1.setString(2, don.getKhachHang().getMaKhachHang());
                ps1.setString(3, don.getNhanVien().getMaNhanVien());
                ps1.executeUpdate();

                for (ChiTietDonDatBan ct : dsChiTiet) {
                    ct.setDonDatBan(don);
                    chiTietDao.them(con, ct);
                    banAnDao.capNhatTrangThai(ct.getMaBanAn(), "Đang dùng");
                }

                con.commit();
                return true;

            } catch (SQLException e) {
                con.rollback();
                System.err.println("❌ Lỗi khi thêm đơn đặt ngay: " + e.getMessage());
                return false;
            } finally {
                con.setAutoCommit(true);
            }

        } catch (SQLException e) {
            System.err.println("❌ Lỗi kết nối CSDL: " + e.getMessage());
            return false;
        }
    }

    public double getTienCocTheoBan(String maBanAn) {
        String sql = """
                SELECT TOP 1 ct.tienCoc
                FROM ChiTietDonDatBan ct
                JOIN DonDatBan ddb ON ct.maDonDatBan = ddb.maDonDatBan
                WHERE ct.maBanAn = ? AND ddb.thoiGianCheckOut IS NULL
                ORDER BY ddb.ngayTaoDon DESC
                """;

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maBanAn);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return rs.getDouble("tienCoc");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public void capNhatBanQuaHan() {
        String sql = """
            UPDATE BanAn
            SET trangThai = 'Quá hạn'
            WHERE maBanAn IN (
                SELECT DISTINCT ct.maBanAn
                FROM ChiTietDonDatBan ct
                JOIN DonDatBan ddb ON ct.maDonDatBan = ddb.maDonDatBan
                WHERE ddb.thoiGianCheckIn IS NULL       -- Chưa check-in
                  AND ddb.thoiGianCheckOut IS NULL      -- Đơn chưa thanh toán
                  AND GETDATE() > DATEADD(hour, 1, ddb.ngayDat) -- Đã trễ 1 tiếng
            )
            AND trangThai = 'Đặt trước' -- Chỉ cập nhật bàn đang 'Đặt trước'
        """;
        
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Đã cập nhật " + rowsAffected + " bàn sang trạng thái 'Quá hạn'.");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật bàn quá hạn: " + e.getMessage());
        }
    }

    /**
     * Chuyển bàn về trạng thái Trống (thường dùng khi Hủy đơn)
     * @param maBanAn
     */
    public void chuyenBanVeTrangThaiTrong(String maBanAn) {
        // Hàm này chỉ là một ví dụ, bạn có thể cần logic phức tạp hơn
        // để đảm bảo bàn này không còn liên kết với đơn nào khác
        banAnDao.capNhatTrangThai(maBanAn, "Trống");
    }
    
    public DonDatBan getDonHienTaiCuaBan(String maBanAn) {
        DonDatBan don = null;
        String sql = """
                SELECT TOP 1 ddb.maDonDatBan
                FROM DonDatBan ddb
                JOIN ChiTietDonDatBan ct ON ddb.maDonDatBan = ct.maDonDatBan
                WHERE ct.maBanAn = ?
                  AND ddb.thoiGianCheckOut IS NULL -- Đơn chưa thanh toán
                  AND ddb.isActivate = 'ACTIVATE'   -- Đơn chưa bị hủy
                ORDER BY ddb.ngayTaoDon DESC
                """;

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maBanAn);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Lấy đầy đủ thông tin đơn
                    don = this.selectById(new DonDatBan(rs.getString("maDonDatBan")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return don;
    }

    /**
     * Ghi nhận thời gian check-in cho một đơn
     */
    public boolean checkInDon(String maDonDatBan) {
        String sql = "UPDATE DonDatBan SET thoiGianCheckIn = GETDATE() WHERE maDonDatBan = ?";
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maDonDatBan);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Hủy đơn (ẩn đơn đi bằng cờ isActivate)
     */
    public boolean huyDon(String maDonDatBan) {
        String sql = "UPDATE DonDatBan SET isActivate = 'DEACTIVATE' WHERE maDonDatBan = ?";
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maDonDatBan);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Xử lý nghiệp vụ chuyển bàn (dùng transaction)
     * @param maDonDatBan Mã đơn
     * @param maBanCu Mã bàn cũ
     * @param banMoi Thông tin bàn mới (để lấy tiền cọc mới)
     * @param trangThaiCu Trạng thái của bàn cũ (ví dụ: "Đang dùng")
     */
    public boolean chuyenBan(String maDonDatBan, String maBanCu, BanAn banMoi, String trangThaiCu) {
        Connection con = ConnectDB.getConnection();
        String sqlUpdateCT = "UPDATE ChiTietDonDatBan SET maBanAn = ?, tienCoc = ? WHERE maDonDatBan = ? AND maBanAn = ?";
        String sqlUpdateBanCu = "UPDATE BanAn SET trangThai = 'Trống' WHERE maBanAn = ?";
        String sqlUpdateBanMoi = "UPDATE BanAn SET trangThai = ? WHERE maBanAn = ?";

        try {
            con.setAutoCommit(false); // Bắt đầu Transaction

            // 1. Tính lại tiền cọc mới (nếu là đơn đặt hẹn)
            double tienCocMoi = 0;
            // Giả sử chỉ đơn "Đặt trước" mới có cọc, đơn "Đang dùng" không cần tính lại cọc
            if (trangThaiCu.equals("Đặt trước") || trangThaiCu.equals("Quá hạn")) {
                tienCocMoi = (banMoi.getLoaiBan().equalsIgnoreCase("VIP")) ? 200000 : 100000;
            }

            // 2. Cập nhật ChiTietDonDatBan: đổi mã bàn, cập nhật tiền cọc
            try (PreparedStatement ps1 = con.prepareStatement(sqlUpdateCT)) {
                ps1.setString(1, banMoi.getMaBanAn());
                ps1.setDouble(2, tienCocMoi);
                ps1.setString(3, maDonDatBan);
                ps1.setString(4, maBanCu);
                ps1.executeUpdate();
            }

            // 3. Cập nhật Bàn Cũ về "Trống"
            try (PreparedStatement ps2 = con.prepareStatement(sqlUpdateBanCu)) {
                ps2.setString(1, maBanCu);
                ps2.executeUpdate();
            }

            // 4. Cập nhật Bàn Mới về trạng thái của bàn cũ
            try (PreparedStatement ps3 = con.prepareStatement(sqlUpdateBanMoi)) {
                ps3.setString(1, trangThaiCu); // (ví dụ: "Đang dùng" hoặc "Đặt trước")
                ps3.setString(2, banMoi.getMaBanAn());
                ps3.executeUpdate();
            }

            con.commit(); // Hoàn tất
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                con.rollback(); // Hoàn tác nếu có lỗi
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            return false;
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
}