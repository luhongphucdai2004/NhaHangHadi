package entity;

public class TaiKhoan {
    private NhanVien nhanVien;
    private String matKhau;
    private String quyenHan;

    public TaiKhoan(NhanVien nhanVien, String matKhau, String quyenHan) {
        this.nhanVien = nhanVien;
        this.matKhau = matKhau;
        this.quyenHan = quyenHan;
    }

    public TaiKhoan() {

    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public void setQuyenHan(String quyenHan) {
        this.quyenHan = quyenHan;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public String getQuyenHan() {
        return quyenHan;
    }

    public String getMatKhau() {
        return matKhau;
    }


}
