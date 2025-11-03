package entity;

import java.time.LocalDateTime;
import java.util.List;

public class DonDatBan {
    private String maDonDatBan;
    private KhachHang khachHang;
    private NhanVien nhanVien;
    private LocalDateTime ngayDat;
    private LocalDateTime ngayTaoDon;
    private LocalDateTime thoiGianCheckIn;
    
    // Mối quan hệ 1-N
    private List<ChiTietDonDatBan> dsChiTiet;

    public DonDatBan() {
        this.ngayTaoDon = LocalDateTime.now();
    }

    

    public DonDatBan(String maDonDatBan) {
		super();
		this.maDonDatBan = maDonDatBan;
	}



	public DonDatBan(String maDonDatBan, KhachHang khachHang, NhanVien nhanVien, LocalDateTime ngayDat,
			LocalDateTime ngayTaoDon, LocalDateTime thoiGianCheckIn, List<ChiTietDonDatBan> dsChiTiet) {
		super();
		this.maDonDatBan = maDonDatBan;
		this.khachHang = khachHang;
		this.nhanVien = nhanVien;
		this.ngayDat = ngayDat;
		this.ngayTaoDon = ngayTaoDon;
		this.thoiGianCheckIn = thoiGianCheckIn;
		this.dsChiTiet = dsChiTiet;
	}



	// ===== Getter - Setter =====
    
    public String getMaDonDatBan() { return maDonDatBan; }
    public void setMaDonDatBan(String maDonDatBan) { this.maDonDatBan = maDonDatBan; }

    public KhachHang getKhachHang() { return khachHang; }
    public void setKhachHang(KhachHang khachHang) { this.khachHang = khachHang; }

    public NhanVien getNhanVien() { return nhanVien; }
    public void setNhanVien(NhanVien nhanVien) { this.nhanVien = nhanVien; }

    public LocalDateTime getNgayDat() { return ngayDat; }
    public void setNgayDat(LocalDateTime ngayDat) { this.ngayDat = ngayDat; }

    public LocalDateTime getNgayTaoDon() { return ngayTaoDon; }
    public void setNgayTaoDon(LocalDateTime ngayTaoDon) { this.ngayTaoDon = ngayTaoDon; }
    
    public LocalDateTime getThoiGianCheckIn() { return thoiGianCheckIn; }
    public void setThoiGianCheckIn(LocalDateTime thoiGianCheckIn) { this.thoiGianCheckIn = thoiGianCheckIn; }

    public List<ChiTietDonDatBan> getDsChiTiet() { return dsChiTiet; }
    public void setDsChiTiet(List<ChiTietDonDatBan> dsChiTiet) { this.dsChiTiet = dsChiTiet; }
}