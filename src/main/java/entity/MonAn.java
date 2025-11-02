package entity;

public class MonAn {
    private String maMonAn;
    private String tenMonAn;
    private String donVi;
    private double gia;
    private String trangThai;
    private String anhMonAn;
    private LoaiMonAn loaiMonAn; // quan hệ 1-N

    public MonAn() {}

    public MonAn(String maMonAn, String tenMonAn, String donVi, double gia,
                 String trangThai, String anhMonAn, LoaiMonAn loaiMonAn) {
        this.maMonAn = maMonAn;
        this.tenMonAn = tenMonAn;
        this.donVi = donVi;
        this.gia = gia;
        this.trangThai = trangThai;
        this.anhMonAn = anhMonAn;
        this.loaiMonAn = loaiMonAn;
    }

    public String getMaMonAn() {
        return maMonAn;
    }

    public void setMaMonAn(String maMonAn) {
        this.maMonAn = maMonAn;
    }

    public String getTenMonAn() {
        return tenMonAn;
    }

    public void setTenMonAn(String tenMonAn) {
        this.tenMonAn = tenMonAn;
    }

    public String getDonVi() {
        return donVi;
    }

    public void setDonVi(String donVi) {
        this.donVi = donVi;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getAnhMonAn() {
        return anhMonAn;
    }

    public void setAnhMonAn(String anhMonAn) {
        this.anhMonAn = anhMonAn;
    }

    public LoaiMonAn getLoaiMonAn() {
        return loaiMonAn;
    }

    public void setLoaiMonAn(LoaiMonAn loaiMonAn) {
        this.loaiMonAn = loaiMonAn;
    }
}
