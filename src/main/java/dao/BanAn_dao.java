package dao;

import connect.ConnectDB;
import entity.BanAn;
import entity.KhuVuc;
import entity.LoaiKhuVuc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BanAn_dao implements DAOInterface<BanAn> {
    private static BanAn_dao instance;

    public static BanAn_dao getInstance() {
        if (instance == null)
            instance = new BanAn_dao();
        return instance;
    }

    @Override
    public int them(BanAn banAn) throws SQLException {
        String sql = "INSERT INTO BanAn (maBanAn, tenBanAn, soChoNgoi, maKhuVuc, trangThai, loaiBan, ghiChu) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, banAn.getMaBanAn());
            pst.setString(2, banAn.getTenBanAn());
            pst.setInt(3, banAn.getSoChoNgoi());
            pst.setString(4, banAn.getKhuVuc().getMaKhuVuc());
            pst.setString(5, banAn.getTrangThai());
            pst.setString(6, banAn.getLoaiBan());
            pst.setString(7, banAn.getGhiChu());
            return pst.executeUpdate();
        }
    }

    @Override
    public int capNhat(BanAn banAn) {
        String sql = "UPDATE BanAn SET tenBanAn = ?, soChoNgoi = ?, maKhuVuc = ?, trangThai = ?, loaiBan = ?, ghiChu = ? WHERE maBanAn = ?";
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, banAn.getTenBanAn());
            pst.setInt(2, banAn.getSoChoNgoi());
            pst.setString(3, banAn.getKhuVuc().getMaKhuVuc());
            pst.setString(4, banAn.getTrangThai());
            pst.setString(5, banAn.getLoaiBan());
            pst.setString(6, banAn.getGhiChu());
            pst.setString(7, banAn.getMaBanAn());
            return pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int xoa(BanAn banAn) {
        String sql = "DELETE FROM BanAn WHERE maBanAn = ?";
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, banAn.getMaBanAn());
            return pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<BanAn> getAllList() {
        List<BanAn> list = new ArrayList<>();
        String sql = """
                SELECT b.maBanAn, b.tenBanAn, b.soChoNgoi, b.trangThai, b.loaiBan, b.ghiChu,
                       kv.maKhuVuc, kv.tenKhuVuc, kv.soLuongBan, kv.sucChua, kv.dienTich,
                       lkv.maLoaiKhuVuc, lkv.tenLoaiKhuVuc
                FROM BanAn b
                LEFT JOIN KhuVuc kv ON b.maKhuVuc = kv.maKhuVuc
                LEFT JOIN LoaiKhuVuc lkv ON kv.maLoaiKhuVuc = lkv.maLoaiKhuVuc
                ORDER BY b.maBanAn
                """;
        try (Connection con = ConnectDB.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                LoaiKhuVuc loai = new LoaiKhuVuc(
                        rs.getString("maLoaiKhuVuc"),
                        rs.getString("tenLoaiKhuVuc")
                );

                KhuVuc kv = new KhuVuc(
                        rs.getString("maKhuVuc"),
                        rs.getString("tenKhuVuc"),
                        rs.getInt("soLuongBan"),
                        loai,
                        rs.getInt("sucChua"),
                        rs.getDouble("dienTich")
                );

                BanAn ba = new BanAn(
                        rs.getString("maBanAn"),
                        rs.getString("tenBanAn"),
                        rs.getInt("soChoNgoi"),
                        kv,
                        rs.getString("trangThai"),
                        rs.getString("loaiBan"),
                        rs.getString("ghiChu")
                );
                list.add(ba);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<BanAn> chon_AllList() {
        return getAllList();
    }

    @Override
    public BanAn selectById(BanAn banAn) {
        String sql = """
                SELECT b.maBanAn, b.tenBanAn, b.soChoNgoi, b.trangThai, b.loaiBan, b.ghiChu,
                       kv.maKhuVuc, kv.tenKhuVuc, kv.soLuongBan, kv.sucChua, kv.dienTich,
                       lkv.maLoaiKhuVuc, lkv.tenLoaiKhuVuc
                FROM BanAn b
                LEFT JOIN KhuVuc kv ON b.maKhuVuc = kv.maKhuVuc
                LEFT JOIN LoaiKhuVuc lkv ON kv.maLoaiKhuVuc = lkv.maLoaiKhuVuc
                WHERE b.maBanAn = ?
                """;
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, banAn.getMaBanAn()); // Lấy mã từ object
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    LoaiKhuVuc loai = new LoaiKhuVuc(
                            rs.getString("maLoaiKhuVuc"),
                            rs.getString("tenLoaiKhuVuc")
                    );
                    KhuVuc kv = new KhuVuc(
                            rs.getString("maKhuVuc"),
                            rs.getString("tenKhuVuc"),
                            rs.getInt("soLuongBan"),
                            loai,
                            rs.getInt("sucChua"),
                            rs.getDouble("dienTich")
                    );
                    return new BanAn(
                            rs.getString("maBanAn"),
                            rs.getString("tenBanAn"),
                            rs.getInt("soChoNgoi"),
                            kv,
                            rs.getString("trangThai"),
                            rs.getString("loaiBan"),
                            rs.getString("ghiChu")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // --- Các hàm riêng ---
    public boolean capNhatTrangThai(String maBanAn, String trangThai) {
        String sql = "UPDATE BanAn SET trangThai = ? WHERE maBanAn = ?";
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, trangThai);
            pst.setString(2, maBanAn);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi cập nhật trạng thái bàn: " + e.getMessage());
        }
        return false;
    }
    
    public List<String> getDsLoaiBan() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT DISTINCT loaiBan FROM BanAn WHERE loaiBan IS NOT NULL";
        
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                list.add(rs.getString("loaiBan"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}