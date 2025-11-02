package entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DonDatMon {
    public String maDonDatMon;
    public BanAn banAn;
    public LocalDateTime ngayGoi;
    public String trangThai;

    public DonDatMon() {
    }

    public DonDatMon(String maDonDatMon, BanAn banAn, LocalDateTime ngayGoi, String trangThai) {
        this.maDonDatMon = maDonDatMon;
        this.banAn = banAn;
        this.ngayGoi = ngayGoi;
        this.trangThai = trangThai;
    }

    public String getMaDonDatMon() {
        return maDonDatMon;
    }

    public void setMaDonDatMon(String maDonDatMon) {
        this.maDonDatMon = maDonDatMon;
    }

    public BanAn getBanAn() {
        return banAn;
    }

    public void setBanAn(BanAn banAn) {
        this.banAn = banAn;
    }

    public LocalDateTime getNgayGoi() {
        return ngayGoi;
    }

    public void setNgayGoi(LocalDateTime ngayGoi) {
        this.ngayGoi = ngayGoi;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
