package entity;

import java.sql.Date;

public class NhanVien {
    private String maNhanVien;
    private String tenNhanVien;
    private String soDienThoai;
    private String email;
    private String canCuocCongDan;
    private boolean gioiTinh;
    private Date ngayVaoLam;
    private double luongNhanVien;
    public boolean trangThai;
    private String anhNhanVien;

    private String chucVu; //

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public String getGioiTinhString() {
        return gioiTinh ? "Nam" : "Nữ";
    }

    public String getTrangThaiString() {
        return trangThai ? "Đang làm" : "Đã nghỉ";
    }

    public NhanVien() {
    }

    public NhanVien(String maNhanVien,
                    String tenNhanVien,
                    String soDienThoai,
                    String email,
                    String canCuocCongDan,
                    boolean gioiTinh,
                    Date ngayVaoLam,
                    double luongNhanVien,
                    boolean trangThai,
                    String anhNhanVien) {
        this.maNhanVien = maNhanVien;
        this.tenNhanVien = tenNhanVien;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.canCuocCongDan = canCuocCongDan;
        this.gioiTinh = gioiTinh;
        this.ngayVaoLam = ngayVaoLam;
        this.luongNhanVien = luongNhanVien;
        this.trangThai = trangThai;
        this.anhNhanVien = anhNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCanCuocCongDan(String canCuocCongDan) {
        this.canCuocCongDan = canCuocCongDan;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public void setNgayVaoLam(Date ngayVaoLam) {
        this.ngayVaoLam = ngayVaoLam;
    }

    public void setLuongNhanVien(double luongNhanVien) {
        this.luongNhanVien = luongNhanVien;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public void setAnhNhanVien(String anhNhaVien) {
        this.anhNhanVien = anhNhanVien;
    }

    public double getLuongNhanVien() {
        return luongNhanVien;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public String getTenNhanVien() {
        return tenNhanVien;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public String getEmail() {
        return email;
    }

    public String getCanCuocCongDan() {
        return canCuocCongDan;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public Date getNgayVaoLam() {
        return ngayVaoLam;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public String getAnhNhanVien() {
        return anhNhanVien;
    }

    @Override
    public String toString() {
        return "NhanVien{" +
                "maNhanVien='" + maNhanVien + '\'' +
                ", tenNhanVien='" + tenNhanVien + '\'' +
                ", soDienThoai='" + soDienThoai + '\'' +
                ", email='" + email + '\'' +
                ", canCuocCongDan='" + canCuocCongDan + '\'' +
                ", gioiTinh=" + gioiTinh +
                ", ngayVaoLam=" + ngayVaoLam +
                ", luongNhanVien=" + luongNhanVien +
                ", trangThai=" + trangThai +
                ", anhNhaVien='" + anhNhanVien + '\'' +
                '}';
    }

}
