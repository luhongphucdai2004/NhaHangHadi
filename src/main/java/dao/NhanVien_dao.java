package dao;

import connect.ConnectDB;
import entity.NhanVien;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NhanVien_dao implements DAOInterface<NhanVien> {
    public static NhanVien_dao getInstance() {
        return new NhanVien_dao();
    }

    @Override
    public List<NhanVien> getAllList() {
        List<NhanVien> list = new ArrayList<>();
        String sql = """
                SELECT nv.maNhanVien, nv.tenNhanVien, nv.soDienThoai, nv.email, nv.canCuocCongDan,
                       nv.gioiTinh, nv.ngayVaoLam, nv.luongNhanVien, nv.trangThai, nv.anhNhanVien,
                       tk.quyenHan
                FROM NhanVien nv
                LEFT JOIN TaiKhoan tk ON nv.maNhanVien = tk.maNhanVien
                """;

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                NhanVien nv = new NhanVien(
                        rs.getString("maNhanVien"),
                        rs.getString("tenNhanVien"),
                        rs.getString("soDienThoai"),
                        rs.getString("email"),
                        rs.getString("canCuocCongDan"),
                        rs.getBoolean("gioiTinh"),
                        rs.getDate("ngayVaoLam"),
                        rs.getDouble("luongNhanVien"),
                        rs.getBoolean("trangThai"),
                        rs.getString("anhNhanVien")
                );

                String quyen = rs.getString("quyenHan");
                nv.setChucVu(quyen);
                list.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int them(NhanVien o) throws SQLException {
        // Sửa: Dùng try-with-resources và đúng cú pháp SQL
        String sql = """
                INSERT INTO NhanVien(maNhanVien, tenNhanVien, soDienThoai, email, canCuocCongDan, 
                                     gioiTinh, ngayVaoLam, luongNhanVien, trangThai, anhNhanVien)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, o.getMaNhanVien());
            pst.setString(2, o.getTenNhanVien());
            pst.setString(3, o.getSoDienThoai());
            pst.setString(4, o.getEmail());
            pst.setString(5, o.getCanCuocCongDan()); // Sửa: Tên cột
            pst.setBoolean(6, o.isGioiTinh());
            pst.setDate(7, o.getNgayVaoLam());
            pst.setDouble(8, o.getLuongNhanVien());
            pst.setBoolean(9, o.isTrangThai());
            pst.setString(10, o.getAnhNhanVien());

            return pst.executeUpdate();
        }
        // Không cần disconnect() khi dùng try-with-resources
    }

    @Override
    public int capNhat(NhanVien o) {
        // Sửa: Dùng try-with-resources và đúng cú pháp SQL (UPDATE ... SET ... WHERE ...)
        String sql = """
                UPDATE NhanVien 
                SET tenNhanVien = ?, soDienThoai = ?, email = ?, canCuocCongDan = ?, 
                    gioiTinh = ?, ngayVaoLam = ?, luongNhanVien = ?, trangThai = ?, anhNhanVien = ?
                WHERE maNhanVien = ?
                """;
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, o.getTenNhanVien());
            pst.setString(2, o.getSoDienThoai());
            pst.setString(3, o.getEmail());
            pst.setString(4, o.getCanCuocCongDan()); // Sửa: Tên cột
            pst.setBoolean(5, o.isGioiTinh());
            pst.setDate(6, o.getNgayVaoLam());
            pst.setDouble(7, o.getLuongNhanVien());
            pst.setBoolean(8, o.isTrangThai());
            pst.setString(9, o.getAnhNhanVien());
            pst.setString(10, o.getMaNhanVien()); // WHERE

            return pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int xoa(NhanVien o) {
        // Cần cân nhắc về việc xóa nhân viên (có thể chỉ cập nhật trạng thái)
        String sql = "DELETE FROM NhanVien WHERE maNhanVien = ?";
         try (Connection con = ConnectDB.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
             pst.setString(1, o.getMaNhanVien());
             return pst.executeUpdate();
         } catch (SQLException e) {
            e.printStackTrace();
            return 0;
         }
    }

    @Override
    public List<NhanVien> chon_AllList() {
        return getAllList(); // Sửa: trả về getAllList thay vì null
    }

    @Override
    public NhanVien selectById(NhanVien o) {
        // Sửa: Implement hàm này
        String sql = """
                SELECT nv.*, tk.quyenHan
                FROM NhanVien nv
                LEFT JOIN TaiKhoan tk ON nv.maNhanVien = tk.maNhanVien
                WHERE nv.maNhanVien = ?
                """;
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            
            pst.setString(1, o.getMaNhanVien());
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    NhanVien nv = new NhanVien(
                            rs.getString("maNhanVien"),
                            rs.getString("tenNhanVien"),
                            rs.getString("soDienThoai"),
                            rs.getString("email"),
                            rs.getString("canCuocCongDan"),
                            rs.getBoolean("gioiTinh"),
                            rs.getDate("ngayVaoLam"),
                            rs.getDouble("luongNhanVien"),
                            rs.getBoolean("trangThai"),
                            rs.getString("anhNhanVien")
                    );
                    nv.setChucVu(rs.getString("quyenHan"));
                    return nv;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}