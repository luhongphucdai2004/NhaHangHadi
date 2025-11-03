package dao;

import connect.ConnectDB;
import entity.KhachHang;
import entity.LoaiKhachHang;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KhachHang_dao implements DAOInterface<KhachHang> {

    private static KhachHang_dao instance;

    public static KhachHang_dao getInstance() {
        if (instance == null)
            instance = new KhachHang_dao();
        return instance;
    }

    // ==================== THÊM KHÁCH HÀNG ====================
    @Override
    public int them(KhachHang kh) throws SQLException {
        // Tạm bỏ phát sinh mã tự động ở đây để tuân thủ interface
        // Nếu muốn, mã phải được phát sinh ở tầng Business (BUS)
        // String maKH = phatSinhMaMoi();
        // kh.setMaKhachHang(maKH);

        String sql = """
                INSERT INTO KhachHang (maKhachHang, tenKhachHang, soDienThoai, diemTichLuy, maLoaiKhachHang, email)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, kh.getMaKhachHang());
            pst.setString(2, kh.getTenKhachHang());
            pst.setString(3, kh.getSoDienThoai());
            pst.setDouble(4, kh.getDiemTichLuy());
            pst.setString(5, kh.getLoaiKhachHang().getMaLoaiKhachHang());
            pst.setString(6, kh.getEmail()); // Sửa: Thêm email

            return pst.executeUpdate();
        }
    }

    // ==================== CẬP NHẬT KHÁCH HÀNG ====================
    @Override
    public int capNhat(KhachHang kh) {
        String sql = """
                UPDATE KhachHang
                SET tenKhachHang = ?, soDienThoai = ?, diemTichLuy = ?, maLoaiKhachHang = ?, email = ?
                WHERE maKhachHang = ?
                """;

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, kh.getTenKhachHang());
            pst.setString(2, kh.getSoDienThoai());
            pst.setDouble(3, kh.getDiemTichLuy());
            pst.setString(4, kh.getLoaiKhachHang().getMaLoaiKhachHang());
            pst.setString(5, kh.getEmail()); // Sửa: Thêm email
            pst.setString(6, kh.getMaKhachHang()); // Sửa: email là 5, mã là 6

            return pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // ==================== XÓA KHÁCH HÀNG ====================
    @Override
    public int xoa(KhachHang kh) {
        String sql = "DELETE FROM KhachHang WHERE maKhachHang = ?";
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, kh.getMaKhachHang());
            return pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // ==================== LẤY TẤT CẢ DANH SÁCH KHÁCH HÀNG ====================
    @Override
    public List<KhachHang> getAllList() {
        List<KhachHang> list = new ArrayList<>();
        String sql = """
                SELECT KH.*, LKH.tenLoaiKhachHang, LKH.moTa, LKH.diemToiThieu
                FROM KhachHang KH
                JOIN LoaiKhachHang LKH ON KH.maLoaiKhachHang = LKH.maLoaiKhachHang
                ORDER BY KH.maKhachHang
                """;

        try (Connection con = ConnectDB.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                LoaiKhachHang loai = new LoaiKhachHang(
                        rs.getString("maLoaiKhachHang"),
                        rs.getString("tenLoaiKhachHang"),
                        rs.getString("moTa"),
                        rs.getDouble("diemToiThieu")
                );

                KhachHang kh = new KhachHang(
                        rs.getString("maKhachHang"),
                        rs.getString("tenKhachHang"),
                        rs.getString("soDienThoai"),
                        rs.getDouble("diemTichLuy"),
                        loai,
                        rs.getString("email")
                );

                list.add(kh);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<KhachHang> chon_AllList() {
        return getAllList();
    }

    // ==================== TÌM KHÁCH HÀNG THEO MÃ ====================
    @Override
    public KhachHang selectById(KhachHang kh) {
        String sql = """
                SELECT KH.*, LKH.tenLoaiKhachHang, LKH.moTa, LKH.diemToiThieu
                FROM KhachHang KH
                JOIN LoaiKhachHang LKH ON KH.maLoaiKhachHang = LKH.maLoaiKhachHang
                WHERE KH.maKhachHang = ?
                """;

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, kh.getMaKhachHang());
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                LoaiKhachHang loai = new LoaiKhachHang(
                        rs.getString("maLoaiKhachHang"),
                        rs.getString("tenLoaiKhachHang"),
                        rs.getString("moTa"),
                        rs.getDouble("diemToiThieu")
                );
                return new KhachHang(
                        rs.getString("maKhachHang"),
                        rs.getString("tenKhachHang"),
                        rs.getString("soDienThoai"),
                        rs.getDouble("diemTichLuy"),
                        loai,
                        rs.getString("email")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ==================== CÁC HÀM TÌM KIẾM RIÊNG ====================
    public KhachHang timTheoSoDienThoai(String sdt) {
        String sql = """
                SELECT KH.*, LKH.tenLoaiKhachHang, LKH.moTa, LKH.diemToiThieu
                FROM KhachHang KH
                JOIN LoaiKhachHang LKH ON KH.maLoaiKhachHang = LKH.maLoaiKhachHang
                WHERE KH.soDienThoai = ?
                """;
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, sdt);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                LoaiKhachHang loai = new LoaiKhachHang(
                        rs.getString("maLoaiKhachHang"),
                        rs.getString("tenLoaiKhachHang"),
                        rs.getString("moTa"),
                        rs.getDouble("diemToiThieu")
                );
                return new KhachHang(
                        rs.getString("maKhachHang"),
                        rs.getString("tenKhachHang"),
                        rs.getString("soDienThoai"),
                        rs.getDouble("diemTichLuy"),
                        loai,
                        rs.getString("email")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String phatSinhMaMoi() {
        String sql = "SELECT MAX(maKhachHang) AS maxMa FROM KhachHang";
        try (Connection con = ConnectDB.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) {
                String maxMa = rs.getString("maxMa");
                if (maxMa == null) return "KH0001";
                int num = Integer.parseInt(maxMa.substring(2)) + 1;
                return String.format("KH%04d", num);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "KH0001";
    }

    public KhachHang timTheoTen(String ten) {
        String sql = """
                SELECT KH.*, LKH.tenLoaiKhachHang, LKH.moTa, LKH.diemToiThieu
                FROM KhachHang KH
                JOIN LoaiKhachHang LKH ON KH.maLoaiKhachHang = LKH.maLoaiKhachHang
                WHERE KH.tenKhachHang = ?
                """;
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, ten);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                LoaiKhachHang loai = new LoaiKhachHang(
                        rs.getString("maLoaiKhachHang"),
                        rs.getString("tenLoaiKhachHang"),
                        rs.getString("moTa"),
                        rs.getDouble("diemToiThieu")
                );
                return new KhachHang(
                        rs.getString("maKhachHang"),
                        rs.getString("tenKhachHang"),
                        rs.getString("soDienThoai"),
                        rs.getDouble("diemTichLuy"),
                        loai,
                        rs.getString("email")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}