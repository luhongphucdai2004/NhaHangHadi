package entity;

public class ChiTietDonDatMon {

    private DonDatMon donDatMon;  // khóa ngoại đến DonDatMon
    private MonAn monAn;          // khóa ngoại đến MonAn
    private int soLuong;
    private String ghiChu;

    public ChiTietDonDatMon() {}

    public ChiTietDonDatMon(DonDatMon donDatMon, MonAn monAn, int soLuong, String ghiChu) {
        this.donDatMon = donDatMon;
        this.monAn = monAn;
        this.soLuong = soLuong;
        this.ghiChu = ghiChu;
    }

    public DonDatMon getDonDatMon() {
        return donDatMon;
    }

    public void setDonDatMon(DonDatMon donDatMon) {
        this.donDatMon = donDatMon;
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

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    @Override
    public String toString() {
        return "ChiTietDonDatMon{" +
                "donDatMon=" + (donDatMon != null ? donDatMon.getMaDonDatMon() : "null") +
                ", monAn=" + (monAn != null ? monAn.getTenMonAn() : "null") +
                ", soLuong=" + soLuong +
                ", ghiChu='" + ghiChu + '\'' +
                '}';
    }
}
