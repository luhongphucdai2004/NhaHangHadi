package entity;

import java.time.LocalDate;

public class KhuyenMai {
    private String maKM;             // Mã khuyến mãi
    private String tenKM;            // Tên chương trình
    private String maCode;           // Mã code mà khách nhập/scan
    private LocalDate ngayBatDau;    // Ngày bắt đầu hiệu lực
    private LocalDate ngayKetThuc;   // Ngày kết thúc hiệu lực
    private double giaTriKM;         // Giá trị khuyến mãi (VD: 0.1 = 10%)
    private String loaiKM;           // Loại: "phantram", "tienmat", "voucher"
    private int soLanSuDungToiDa;    // Số lần sử dụng tối đa
    private boolean trangThai;       // 1 = hoạt động, 0 = hết hạnnnn
    private KhachHang khachHang;     // Khách hàng được áp dụng (nếu có)

    // ===== Constructors =====
    public KhuyenMai() {}

    public KhuyenMai(String maKM, String tenKM, String maCode, LocalDate ngayBatDau,
                     LocalDate ngayKetThuc, double giaTriKM, String loaiKM,
                     int soLanSuDungToiDa, boolean trangThai, KhachHang khachHang) {
        this.maKM = maKM;
        this.tenKM = tenKM;
        this.maCode = maCode;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.giaTriKM = giaTriKM;
        this.loaiKM = loaiKM;
        this.soLanSuDungToiDa = soLanSuDungToiDa;
        this.trangThai = trangThai;
        this.khachHang = khachHang;
    }

    // ===== Getters & Setters =====
    public String getMaKM() {
        return maKM;
    }

    public void setMaKM(String maKM) {
        this.maKM = maKM;
    }

    public String getTenKM() {
        return tenKM;
    }

    public void setTenKM(String tenKM) {
        this.tenKM = tenKM;
    }

    public String getMaCode() {
        return maCode;
    }

    public void setMaCode(String maCode) {
        this.maCode = maCode;
    }

    public LocalDate getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(LocalDate ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public LocalDate getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(LocalDate ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public double getGiaTriKM() {
        return giaTriKM;
    }

    public void setGiaTriKM(double giaTriKM) {
        this.giaTriKM = giaTriKM;
    }

    public String getLoaiKM() {
        return loaiKM;
    }

    public void setLoaiKM(String loaiKM) {
        this.loaiKM = loaiKM;
    }

    public int getSoLanSuDungToiDa() {
        return soLanSuDungToiDa;
    }

    public void setSoLanSuDungToiDa(int soLanSuDungToiDa) {
        this.soLanSuDungToiDa = soLanSuDungToiDa;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    @Override
    public String toString() {
        return "KhuyenMai{" +
                "maKM='" + maKM + '\'' +
                ", tenKM='" + tenKM + '\'' +
                ", maCode='" + maCode + '\'' +
                ", ngayBatDau=" + ngayBatDau +
                ", ngayKetThuc=" + ngayKetThuc +
                ", giaTriKM=" + giaTriKM +
                ", loaiKM='" + loaiKM + '\'' +
                ", soLanSuDungToiDa=" + soLanSuDungToiDa +
                ", trangThai=" + trangThai +
                ", khachHang=" + (khachHang != null ? khachHang.getMaKhachHang() : "null") +
                '}';
    }
}
