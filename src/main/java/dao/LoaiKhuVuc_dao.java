package dao;

import connect.ConnectDB;
import entity.LoaiKhuVuc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoaiKhuVuc_dao implements DAOInterface<LoaiKhuVuc> {

    public static LoaiKhuVuc_dao getInstance() {
        return new LoaiKhuVuc_dao();
    }

    @Override
    public int them(LoaiKhuVuc lkv) throws SQLException {
        String sql = "INSERT INTO LoaiKhuVuc (maLoaiKhuVuc, tenLoaiKhuVuc) VALUES (?, ?)";
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, lkv.getMaLoaiKhuVuc());
            ps.setString(2, lkv.getTenLoaiKhuVuc());
            return ps.executeUpdate();
        }
    }

    @Override
    public int capNhat(LoaiKhuVuc lkv) {
        String sql = "UPDATE LoaiKhuVuc SET tenLoaiKhuVuc = ? WHERE maLoaiKhuVuc = ?";
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, lkv.getTenLoaiKhuVuc());
            ps.setString(2, lkv.getMaLoaiKhuVuc());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int xoa(LoaiKhuVuc lkv) {
        String sql = "DELETE FROM LoaiKhuVuc WHERE maLoaiKhuVuc = ?";
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, lkv.getMaLoaiKhuVuc());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<LoaiKhuVuc> getAllList() {
        List<LoaiKhuVuc> ds = new ArrayList<>();
        String sql = "SELECT * FROM LoaiKhuVuc ORDER BY maLoaiKhuVuc ASC";
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                LoaiKhuVuc lkv = new LoaiKhuVuc(
                        rs.getString("maLoaiKhuVuc"),
                        rs.getString("tenLoaiKhuVuc")
                );
                ds.add(lkv);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    @Override
    public List<LoaiKhuVuc> chon_AllList() {
        return getAllList();
    }

    @Override
    public LoaiKhuVuc selectById(LoaiKhuVuc lkv) {
        String sql = "SELECT * FROM LoaiKhuVuc WHERE maLoaiKhuVuc = ?";
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, lkv.getMaLoaiKhuVuc());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new LoaiKhuVuc(
                        rs.getString("maLoaiKhuVuc"),
                        rs.getString("tenLoaiKhuVuc")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String phatSinhMaMoi() {
        String prefix = "LKV";
        int so = 1;

        String sql = "SELECT MAX(maLoaiKhuVuc) AS maCuoi FROM LoaiKhuVuc";
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next() && rs.getString("maCuoi") != null) {
                String maCuoi = rs.getString("maCuoi");
                so = Integer.parseInt(maCuoi.substring(3)) + 1;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return String.format("%s%04d", prefix, so);
    }
}