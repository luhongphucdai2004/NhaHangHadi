package entity;

import java.time.LocalDateTime;

public class DonDatBan {
    private String maDonDatBan;
    private KhachHang khachHang;
    private BanAn banAn;
    private NhanVien nhanVien;
    private double tienCoc;
    private LocalDateTime ngayDat;     // dùng DATETIME
    private LocalDateTime ngayTaoDon;  // dùng DATETIME DEFAULT GETDATE()
    private LocalDateTime thoiGianCheckIn;

    public LocalDateTime getThoiGianCheckIn() { return thoiGianCheckIn; }
    public void setThoiGianCheckIn(LocalDateTime thoiGianCheckIn) { this.thoiGianCheckIn = thoiGianCheckIn; }

    public DonDatBan() {}

    // Constructor đầy đủ
    public DonDatBan(String maDonDatBan, KhachHang khachHang, BanAn banAn,
                     NhanVien nhanVien, double tienCoc, LocalDateTime ngayDat) {
        this.maDonDatBan = maDonDatBan;
        this.khachHang = khachHang;
        this.banAn = banAn;
        this.nhanVien = nhanVien;
        this.tienCoc = tienCoc;
        this.ngayDat = ngayDat;
        this.ngayTaoDon = LocalDateTime.now(); // auto theo thời điểm tạo
    }

    // Getter - Setter
    public String getMaDonDatBan() { return maDonDatBan; }
    public void setMaDonDatBan(String maDonDatBan) { this.maDonDatBan = maDonDatBan; }

    public KhachHang getKhachHang() { return khachHang; }
    public void setKhachHang(KhachHang khachHang) { this.khachHang = khachHang; }

    public BanAn getBanAn() { return banAn; }
    public void setBanAn(BanAn banAn) { this.banAn = banAn; }

    public NhanVien getNhanVien() { return nhanVien; }
    public void setNhanVien(NhanVien nhanVien) { this.nhanVien = nhanVien; }

    public double getTienCoc() { return tienCoc; }
    public void setTienCoc(double tienCoc) { this.tienCoc = tienCoc; }

    public LocalDateTime getNgayDat() { return ngayDat; }
    public void setNgayDat(LocalDateTime ngayDat) { this.ngayDat = ngayDat; }

    public LocalDateTime getNgayTaoDon() { return ngayTaoDon; }
    public void setNgayTaoDon(LocalDateTime ngayTaoDon) { this.ngayTaoDon = ngayTaoDon; }
}
