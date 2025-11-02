package entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class HoaDon {

    // ============================================================
    // 🔹 THUỘC TÍNH
    // ============================================================
    private String maHoaDon;

    private String maBanAn;      // Mã bàn để lưu vào CSDL
    private String maNhanVien;   // Mã nhân viên lập hóa đơn
    private String maKhachHang;  // Mã khách hàng (nếu có)

    private BanAn banAn;
    private KhachHang khachHang;
    private NhanVien nhanVien;
    private KhuyenMai khuyenMai;

    private LocalDate ngayLap;
    private LocalDateTime ngayThanhToan;

    private int diemSuDung;
    private double tongTien;
    private double chietKhauVAT;
    private double tienGiam;
    private double tienNhan;
    private double tienThua;

    private String hinhThuc; // "Tiền mặt" / "Chuyển khoản"


    // ============================================================
    // 🔹 CONSTRUCTOR
    // ============================================================
    public HoaDon() {}

    public HoaDon(String maHoaDon, BanAn banAn, KhachHang khachHang, NhanVien nhanVien,
                  KhuyenMai khuyenMai, LocalDate ngayLap, int diemSuDung, double tongTien,
                  double chietKhauVAT, double tienGiam, double tienNhan, double tienThua,
                  String hinhThuc) {
        this.maHoaDon = maHoaDon;
        this.banAn = banAn;
        this.khachHang = khachHang;
        this.nhanVien = nhanVien;
        this.khuyenMai = khuyenMai;
        this.ngayLap = ngayLap;
        this.diemSuDung = diemSuDung;
        this.tongTien = tongTien;
        this.chietKhauVAT = chietKhauVAT;
        this.tienGiam = tienGiam;
        this.tienNhan = tienNhan;
        this.tienThua = tienThua;
        this.hinhThuc = hinhThuc;

        if (banAn != null)
            this.maBanAn = banAn.getMaBanAn();
        if (nhanVien != null)
            this.maNhanVien = nhanVien.getMaNhanVien();
        if (khachHang != null)
            this.maKhachHang = khachHang.getMaKhachHang();
    }

    // ============================================================
    // 🔹 GETTER / SETTER
    // ============================================================

    public String getMaHoaDon() { return maHoaDon; }
    public void setMaHoaDon(String maHoaDon) { this.maHoaDon = maHoaDon; }

    public String getMaBanAn() { return maBanAn; }
    public void setMaBanAn(String maBanAn) { this.maBanAn = maBanAn; }

    public String getMaNhanVien() { return maNhanVien; }
    public void setMaNhanVien(String maNhanVien) { this.maNhanVien = maNhanVien; }

    public String getMaKhachHang() { return maKhachHang; }
    public void setMaKhachHang(String maKhachHang) { this.maKhachHang = maKhachHang; }

    public BanAn getBanAn() { return banAn; }
    public void setBanAn(BanAn banAn) {
        this.banAn = banAn;
        if (banAn != null)
            this.maBanAn = banAn.getMaBanAn(); // đồng bộ
    }

    public KhachHang getKhachHang() { return khachHang; }
    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
        if (khachHang != null)
            this.maKhachHang = khachHang.getMaKhachHang(); // đồng bộ
    }

    public NhanVien getNhanVien() { return nhanVien; }
    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
        if (nhanVien != null)
            this.maNhanVien = nhanVien.getMaNhanVien(); // đồng bộ
    }

    public KhuyenMai getKhuyenMai() { return khuyenMai; }
    public void setKhuyenMai(KhuyenMai khuyenMai) { this.khuyenMai = khuyenMai; }

    public LocalDate getNgayLap() { return ngayLap; }
    public void setNgayLap(LocalDate ngayLap) { this.ngayLap = ngayLap; }

    public LocalDateTime getNgayThanhToan() { return ngayThanhToan; }
    public void setNgayThanhToan(LocalDateTime ngayThanhToan) { this.ngayThanhToan = ngayThanhToan; }

    public int getDiemSuDung() { return diemSuDung; }
    public void setDiemSuDung(int diemSuDung) { this.diemSuDung = diemSuDung; }

    public double getTongTien() { return tongTien; }
    public void setTongTien(double tongTien) { this.tongTien = tongTien; }

    public double getChietKhauVAT() { return chietKhauVAT; }
    public void setChietKhauVAT(double chietKhauVAT) { this.chietKhauVAT = chietKhauVAT; }

    public double getTienGiam() { return tienGiam; }
    public void setTienGiam(double tienGiam) { this.tienGiam = tienGiam; }

    public double getTienNhan() { return tienNhan; }
    public void setTienNhan(double tienNhan) { this.tienNhan = tienNhan; }

    public double getTienThua() { return tienThua; }
    public void setTienThua(double tienThua) { this.tienThua = tienThua; }

    public String getHinhThuc() { return hinhThuc; }
    public void setHinhThuc(String hinhThuc) { this.hinhThuc = hinhThuc; }

    // ============================================================
    //  TO STRING (dùng cho TableView / ComboBox)
    // ============================================================
    @Override
    public String toString() {
        String tenBan = (banAn != null) ? banAn.getTenBanAn() : maBanAn;
        String tenNV = (nhanVien != null) ? nhanVien.getTenNhanVien() : maNhanVien;
        return "Hóa đơn " + maHoaDon + " - Bàn: " + tenBan + " - NV: " + tenNV;
    }
}
