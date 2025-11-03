package entity;

public class LoaiKhachHang {

    private String maLoaiKhachHang;
    private String tenLoaiKhachHang;
    private String moTa;
    private double diemToiThieu;

    public LoaiKhachHang() {}

    public LoaiKhachHang(String maLoaiKhachHang, String tenLoaiKhachHang, String moTa, double diemToiThieu) {
        this.maLoaiKhachHang = maLoaiKhachHang;
        this.tenLoaiKhachHang = tenLoaiKhachHang;
        this.moTa = moTa;
        this.diemToiThieu = diemToiThieu;
    }

    public String getMaLoaiKhachHang() {
        return maLoaiKhachHang;
    }

    public void setMaLoaiKhachHang(String maLoaiKhachHang) {
        this.maLoaiKhachHang = maLoaiKhachHang;
    }

    public String getTenLoaiKhachHang() {
        return tenLoaiKhachHang;
    }

    public void setTenLoaiKhachHang(String tenLoaiKhachHang) {
        this.tenLoaiKhachHang = tenLoaiKhachHang;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public double getDiemToiThieu() {
        return diemToiThieu;
    }

    public void setDiemToiThieu(double diemToiThieu) {
        this.diemToiThieu = diemToiThieu;
    }

    // ======= HÀM HỖ TRỢ =======
    @Override
    public String toString() {
        return tenLoaiKhachHang != null ? tenLoaiKhachHang : "Không xác định";
    }
}
