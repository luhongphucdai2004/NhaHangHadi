package entity;

public class ChiTietDonDatBan {
    private DonDatBan donDatBan;
    private BanAn banAn;
    private double tienCoc; // <-- TIỀN CỌC ĐÃ VỀ ĐÂY

    public ChiTietDonDatBan() {}

    public ChiTietDonDatBan(DonDatBan donDatBan, BanAn banAn, double tienCoc) {
        this.donDatBan = donDatBan;
        this.banAn = banAn;
        this.tienCoc = tienCoc;

    }
    
    // ===== Getter - Setter =====

    public DonDatBan getDonDatBan() { return donDatBan; }
    public void setDonDatBan(DonDatBan donDatBan) { this.donDatBan = donDatBan; }

    public BanAn getBanAn() { return banAn; }
    public void setBanAn(BanAn banAn) { this.banAn = banAn; }

    public double getTienCoc() { return tienCoc; }
    public void setTienCoc(double tienCoc) { this.tienCoc = tienCoc; }
    

    // ===== Phương thức tiện ích =====
    
    public String getMaDonDatBan() {
        return (donDatBan != null) ? donDatBan.getMaDonDatBan() : null;
    }
    
    public String getMaBanAn() {
        return (banAn != null) ? banAn.getMaBanAn() : null;
    }
}