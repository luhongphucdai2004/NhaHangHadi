package entity;

public class LoaiKhuVuc {
    private String maLoaiKhuVuc;
    private String tenLoaiKhuVuc;

    public LoaiKhuVuc() {
    }

    public LoaiKhuVuc(String maLoaiKhuVuc, String tenLoaiKhuVuc) {
        this.maLoaiKhuVuc = maLoaiKhuVuc;
        this.tenLoaiKhuVuc = tenLoaiKhuVuc;
    }


    public String getMaLoaiKhuVuc() {
        return maLoaiKhuVuc;
    }

    public void setMaLoaiKhuVuc(String maLoaiKhuVuc) {
        this.maLoaiKhuVuc = maLoaiKhuVuc;
    }

    public String getTenLoaiKhuVuc() {
        return tenLoaiKhuVuc;
    }

    public void setTenLoaiKhuVuc(String tenLoaiKhuVuc) {
        this.tenLoaiKhuVuc = tenLoaiKhuVuc;
    }

    @Override
    public String toString() {
        return tenLoaiKhuVuc;
    }
}
