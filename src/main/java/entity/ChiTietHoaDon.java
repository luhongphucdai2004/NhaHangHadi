package entity;

public class ChiTietHoaDon {
    public HoaDon hoaDon;
    public MonAn monAn;
    public int soLuong;
    public double thanhTien;

    public ChiTietHoaDon() {
    }

    public ChiTietHoaDon(HoaDon hoaDon, MonAn monAn, int soLuong, double thanhTien) {
        this.hoaDon = hoaDon;
        this.monAn = monAn;
        this.soLuong = soLuong;
        this.thanhTien = thanhTien;
    }

    public HoaDon getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(HoaDon hoaDon) {
        this.hoaDon = hoaDon;
    }

    public MonAn getMonAn() {
        return monAn;
    }

    public void setMonAn(MonAn monAn) {
        this.monAn = monAn;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }
}
