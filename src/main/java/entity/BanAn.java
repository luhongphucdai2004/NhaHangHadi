package entity;

public class BanAn {
    private String maBanAn;     // Mã bàn ăn (PK)
    private String tenBanAn;    // Tên bàn ăn (ví dụ: "Bàn 1")
    private int soChoNgoi;      // Số chỗ ngồi
    private String trangThai;   // Trạng thái: "Trống", "Đang phục vụ", ...
    private String loaiBan;     // Loại bàn: "Tròn", "Chữ nhật", ...
    private String ghiChu;      // Ghi chú (nếu có)

    // Liên kết 1-N: mỗi bàn thuộc 1 khu vực
    private KhuVuc khuVuc;

    // ===== Constructors =====
    public BanAn() {}

    public BanAn(String maBanAn, String tenBanAn, int soChoNgoi, KhuVuc khuVuc, String trangThai, String loaiBan, String ghiChu) {
        this.maBanAn = maBanAn;
        this.tenBanAn = tenBanAn;
        this.soChoNgoi = soChoNgoi;
        this.khuVuc = khuVuc;
        this.trangThai = trangThai;
        this.loaiBan = loaiBan;
        this.ghiChu = ghiChu;
    }

    // ===== Getter - Setter =====
    public String getMaBanAn() {
        return maBanAn;
    }

    public void setMaBanAn(String maBanAn) {
        this.maBanAn = maBanAn;
    }

    public String getTenBanAn() {
        return tenBanAn;
    }

    public void setTenBanAn(String tenBanAn) {
        this.tenBanAn = tenBanAn;
    }

    public int getSoChoNgoi() {
        return soChoNgoi;
    }

    public void setSoChoNgoi(int soChoNgoi) {
        this.soChoNgoi = soChoNgoi;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getLoaiBan() {
        return loaiBan;
    }

    public void setLoaiBan(String loaiBan) {
        this.loaiBan = loaiBan;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public KhuVuc getKhuVuc() {
        return khuVuc;
    }

    public void setKhuVuc(KhuVuc khuVuc) {
        this.khuVuc = khuVuc;
    }

    public String getTenKhuVucHienThi() {
        if (khuVuc != null) {
            return khuVuc.getTenKhuVuc();
        }
        return "";
    }

    @Override
    public String toString() {
        return tenBanAn + " - " + khuVuc.getTenKhuVuc();
    }
}
