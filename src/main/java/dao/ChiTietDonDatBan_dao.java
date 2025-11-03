package dao;

import connect.ConnectDB;
import entity.BanAn;
import entity.ChiTietDonDatBan;
import entity.DonDatBan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChiTietDonDatBan_dao implements DAOInterface<ChiTietDonDatBan> {

    private BanAn_dao banAnDao = BanAn_dao.getInstance();
    // Giả sử có DonDatBan_dao để lấy DonDatBan
    // private DonDatBan_dao donDatBanDao = DonDatBan_dao.getInstance();

    @Override
    public int them(ChiTietDonDatBan ct) throws SQLException {
        String sql = "INSERT INTO ChiTietDonDatBan(maDonDatBan, maBanAn, tienCoc, ghiChu) VALUES (?, ?, ?, ?)";
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, ct.getMaDonDatBan());
            ps.setString(2, ct.getMaBanAn());
            ps.setDouble(3, ct.getTienCoc());
            ps.setString(4, ct.getGhiChu());
            return ps.executeUpdate();
        }
    }

    @Override
    public int capNhat(ChiTietDonDatBan ct) {
        String sql = "UPDATE ChiTietDonDatBan SET tienCoc = ?, ghiChu = ? WHERE maDonDatBan = ? AND maBanAn = ?";
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, ct.getTienCoc());
            ps.setString(2, ct.getGhiChu());
            ps.setString(3, ct.getMaDonDatBan());
            ps.setString(4, ct.getMaBanAn());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int xoa(ChiTietDonDatBan ct) {
        String sql = "DELETE FROM ChiTietDonDatBan WHERE maDonDatBan = ? AND maBanAn = ?";
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, ct.getMaDonDatBan());
            ps.setString(2, ct.getMaBanAn());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<ChiTietDonDatBan> getAllList() {
        List<ChiTietDonDatBan> list = new ArrayList<>();
        String sql = "SELECT * FROM ChiTietDonDatBan";
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                // Đây là cách làm đơn giản, nhưng sẽ tạo nhiều object DonDatBan/BanAn
                // Cách tốt hơn là JOIN, nhưng để đơn giản, ta dùng selectById
                
                // Lấy bàn
                BanAn ban = banAnDao.selectById(new BanAn(rs.getString("maBanAn")));
                
                // Lấy đơn (Tạm thời để null nếu không muốn truy vấn thêm)
                // DonDatBan don = donDatBanDao.selectById(new DonDatBan(rs.getString("maDonDatBan")));
                
                list.add(new ChiTietDonDatBan(
                        null, // Tạm để null DonDatBan
                        ban,
                        rs.getDouble("tienCoc"),
                        rs.getString("ghiChu")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<ChiTietDonDatBan> chon_AllList() {
        return getAllList();
    }

    @Override
    public ChiTietDonDatBan selectById(ChiTietDonDatBan ct) {
        String sql = "SELECT * FROM ChiTietDonDatBan WHERE maDonDatBan = ? AND maBanAn = ?";
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, ct.getMaDonDatBan());
            ps.setString(2, ct.getMaBanAn());
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    BanAn ban = banAnDao.selectById(new BanAn(rs.getString("maBanAn")));
                    // DonDatBan don = donDatBanDao.selectById(new DonDatBan(rs.getString("maDonDatBan")));

                    return new ChiTietDonDatBan(
                            null, // Tạm để null DonDatBan
                            ban,
                            rs.getDouble("tienCoc"),
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

    // Thêm (dùng connection có sẵn cho transaction)
    public int them(Connection con, ChiTietDonDatBan ct) throws SQLException {
        String sql = "INSERT INTO ChiTietDonDatBan(maDonDatBan, maBanAn, tienCoc, ghiChu) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, ct.getMaDonDatBan());
            ps.setString(2, ct.getMaBanAn());
            ps.setDouble(3, ct.getTienCoc());
            ps.setString(4, ct.getGhiChu());
            return ps.executeUpdate();
        }
    }

    // Lấy danh sách chi tiết theo mã đơn
    public List<ChiTietDonDatBan> getListTheoDon(String maDonDatBan) {
        List<ChiTietDonDatBan> list = new ArrayList<>();
        String sql = "SELECT * FROM ChiTietDonDatBan WHERE maDonDatBan = ?";
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maDonDatBan);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    BanAn ban = banAnDao.selectById(new BanAn(rs.getString("maBanAn")));

                    list.add(new ChiTietDonDatBan(
                            null, // Tạm thời để null DonDatBan để tránh lặp
                            ban,
                            rs.getDouble("tienCoc"),
                            rs.getString("ghiChu")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}