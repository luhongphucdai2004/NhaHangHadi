package dao;

import connect.ConnectDB;
import entity.KhuVuc;
import entity.LoaiKhuVuc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KhuVuc_dao implements DAOInterface<KhuVuc> {

    private static KhuVuc_dao instance;
    public static KhuVuc_dao getInstance() {
        if (instance == null)
            instance = new KhuVuc_dao();
        return instance;
    }

    @Override
    public int them(KhuVuc kv) throws SQLException {
        String sql = "INSERT INTO KhuVuc (maKhuVuc, tenKhuVuc, soLuongBan, maLoaiKhuVuc, sucChua, dienTich) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kv.getMaKhuVuc());
            ps.setString(2, kv.getTenKhuVuc());
            ps.setInt(3, kv.getSoLuongBan());
            ps.setString(4, kv.getLoaiKhuVuc() != null ? kv.getLoaiKhuVuc().getMaLoaiKhuVuc() : null);
            ps.setInt(5, kv.getSucChua());
            ps.setDouble(6, kv.getDienTich());
            return ps.executeUpdate();
        }
    }

    @Override
    public int capNhat(KhuVuc kv) {
        String sql = "UPDATE KhuVuc SET tenKhuVuc=?, soLuongBan=?, maLoaiKhuVuc=?, sucChua=?, dienTich=? WHERE maKhuVuc=?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kv.getTenKhuVuc());
            ps.setInt(2, kv.getSoLuongBan());
            ps.setString(3, kv.getLoaiKhuVuc() != null ? kv.getLoaiKhuVuc().getMaLoaiKhuVuc() : null);
            ps.setInt(4, kv.getSucChua());
            ps.setDouble(5, kv.getDienTich());
            ps.setString(6, kv.getMaKhuVuc());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int xoa(KhuVuc kv) {
        String sql = "DELETE FROM KhuVuc WHERE maKhuVuc=?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kv.getMaKhuVuc());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<KhuVuc> getAllList() {
        List<KhuVuc> ds = new ArrayList<>();
        String sql = "SELECT * FROM KhuVuc kv JOIN LoaiKhuVuc lkv ON kv.maLoaiKhuVuc = lkv.maLoaiKhuVuc";
        try (Connection conn = ConnectDB.getConnection();
             Statement st = conn.createStatement();
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
                ds.add(kv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    @Override
    public List<KhuVuc> chon_AllList() {
        return getAllList();
    }

    @Override
    public KhuVuc selectById(KhuVuc t) {
        String sql = "SELECT * FROM KhuVuc kv JOIN LoaiKhuVuc lkv ON kv.maLoaiKhuVuc = lkv.maLoaiKhuVuc WHERE kv.maKhuVuc = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, t.getMaKhuVuc());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                LoaiKhuVuc loai = new LoaiKhuVuc(
                        rs.getString("maLoaiKhuVuc"),
                        rs.getString("tenLoaiKhuVuc")
                );
                return new KhuVuc(
                        rs.getString("maKhuVuc"),
                        rs.getString("tenKhuVuc"),
                        rs.getInt("soLuongBan"),
                        loai,
                        rs.getInt("sucChua"),
                        rs.getDouble("dienTich")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}