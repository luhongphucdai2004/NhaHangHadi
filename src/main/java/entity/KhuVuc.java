package entity;

import java.util.List;

public class KhuVuc {
    private String maKhuVuc;
    private String tenKhuVuc;
    private int soLuongBan;
    private LoaiKhuVuc loaiKhuVuc;
    private int sucChua;
    private double dienTich;

    // Mối quan hệ 1 - N: Một khu vực có nhiều bàn ăn
    private List<BanAn> danhSachBanAn;

    // ===== Constructors =====
    public KhuVuc() {}

    public KhuVuc(String maKhuVuc, String tenKhuVuc, int soLuongBan, LoaiKhuVuc loaiKhuVuc, int sucChua, double dienTich) {
        this.maKhuVuc = maKhuVuc;
        this.tenKhuVuc = tenKhuVuc;
        this.soLuongBan = soLuongBan;
        this.loaiKhuVuc = loaiKhuVuc;
        this.sucChua = sucChua;
        this.dienTich = dienTich;
    }

    // ===== Getter - Setter =====
    public String getMaKhuVuc() {
        return maKhuVuc;
    }

    public void setMaKhuVuc(String maKhuVuc) {
        this.maKhuVuc = maKhuVuc;
    }

    public String getTenKhuVuc() {
        return tenKhuVuc;
    }

    public void setTenKhuVuc(String tenKhuVuc) {
        this.tenKhuVuc = tenKhuVuc;
    }

    public int getSoLuongBan() {
        return soLuongBan;
    }

    public void setSoLuongBan(int soLuongBan) {
        this.soLuongBan = soLuongBan;
    }

    public LoaiKhuVuc getLoaiKhuVuc() {
        return loaiKhuVuc;
    }

    public void setLoaiKhuVuc(LoaiKhuVuc loaiKhuVuc) {
        this.loaiKhuVuc = loaiKhuVuc;
    }

    public int getSucChua() {
        return sucChua;
    }

    public void setSucChua(int sucChua) {
        this.sucChua = sucChua;
    }

    public double getDienTich() {
        return dienTich;
    }

    public void setDienTich(double dienTich) {
        this.dienTich = dienTich;
    }

    public List<BanAn> getDanhSachBanAn() {
        return danhSachBanAn;
    }

    public void setDanhSachBanAn(List<BanAn> danhSachBanAn) {
        this.danhSachBanAn = danhSachBanAn;
    }

    // ===== Override toString() để hiển thị đẹp khi đưa vào ComboBox chẳng hạn =====
    @Override
    public String toString() {
        return tenKhuVuc; // Hiển thị tên khu vực trong ComboBox
    }

}
