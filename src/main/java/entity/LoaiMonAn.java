package entity;

public class LoaiMonAn {
    private String maLoaiMonAn;
    private String tenLoaiMonAn;

    public LoaiMonAn() {}

    public LoaiMonAn(String maLoaiMonAn, String tenLoaiMonAn) {
        this.maLoaiMonAn = maLoaiMonAn;
        this.tenLoaiMonAn = tenLoaiMonAn;
    }

    public String getMaLoaiMonAn() {
        return maLoaiMonAn;
    }

    public void setMaLoaiMonAn(String maLoaiMonAn) {
        this.maLoaiMonAn = maLoaiMonAn;
    }

    public String getTenLoaiMonAn() {
        return tenLoaiMonAn;
    }

    public void setTenLoaiMonAn(String tenLoaiMonAn) {
        this.tenLoaiMonAn = tenLoaiMonAn;
    }

    @Override
    public String toString() {
        return tenLoaiMonAn; // dùng cho ComboBox
    }
}
