package entity;

/**
 * Entity: KhachHang
 * Bảng: KhachHang
 * Quan hệ: Nhiều khách hàng thuộc 1 LoaiKhachHang (N-1)
 */
public class KhachHang {

    private String maKhachHang;
    private String tenKhachHang;
    private String soDienThoai;
    private double diemTichLuy;
    private LoaiKhachHang loaiKhachHang; // FK object
    private String email;

    // ===== CONSTRUCTORS =====
    public KhachHang() {}

    public KhachHang(String maKhachHang) {
		super();
		this.maKhachHang = maKhachHang;
	}

	public KhachHang(String maKhachHang, String tenKhachHang, String soDienThoai,
                     double diemTichLuy, LoaiKhachHang loaiKhachHang, String email) {
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.soDienThoai = soDienThoai;
        this.diemTichLuy = diemTichLuy;
        this.loaiKhachHang = loaiKhachHang;
        this.email = email;
    }

    // ===== GETTERS & SETTERS =====
    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public double getDiemTichLuy() {
        return diemTichLuy;
    }

    public void setDiemTichLuy(double diemTichLuy) {
        this.diemTichLuy = diemTichLuy;
    }

    public LoaiKhachHang getLoaiKhachHang() {
        return loaiKhachHang;
    }

    public void setLoaiKhachHang(LoaiKhachHang loaiKhachHang) {
        this.loaiKhachHang = loaiKhachHang;
    }
    
    

    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	// ======= HÀM HỖ TRỢ =======
    @Override
    public String toString() {
        return String.format("[%s] %s - %s - %.0f điểm - %s",
                maKhachHang,
                tenKhachHang,
                soDienThoai,
                diemTichLuy,
                (loaiKhachHang != null ? loaiKhachHang.getTenLoaiKhachHang() : "Chưa xác định"));
    }
}
