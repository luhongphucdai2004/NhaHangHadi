create database QuanLyDatBan1
go
use QuanLyDatBan1
go

/* Tạo bảng LoaiKhuVuc TRƯỚC */
CREATE TABLE LoaiKhuVuc (
    maLoaiKhuVuc NVARCHAR(50) PRIMARY KEY,
    tenLoaiKhuVuc NVARCHAR(100)
);

/* Tạo bảng KhuVuc (Đã sửa) */
create table KhuVuc
(
	maKhuVuc NVARCHAR(50) NOT NULL PRIMARY KEY,
    tenKhuVuc NVARCHAR(50),
    soLuongBan INT,
    -- SỬA 1: Xóa cột 'loaiKhuVuc' và thay bằng 'maLoaiKhuVuc' làm khóa ngoại
    maLoaiKhuVuc NVARCHAR(50) FOREIGN KEY REFERENCES LoaiKhuVuc(maLoaiKhuVuc), 
    sucChua INT,
	dienTich float
)

/* Tạo bảng BanAn */
create table BanAn
(
	maBanAn Nvarchar(50) not null primary key,
	tenBanAn Nvarchar(50),
	soChoNgoi int,
	maKhuVuc Nvarchar(50) foreign key references KhuVuc(maKhuVuc),
	trangThai Nvarchar(50),
	loaiBan Nvarchar(50),
	ghiChu Nvarchar(200),
	isActivate NVARCHAR(10) NOT NULL DEFAULT 'ACTIVATE' CHECK (isActivate IN ('ACTIVATE', 'DEACTIVATE'))
)

/* Tạo bảng TaiKhoan TRƯỚC NhanVien */
create table TaiKhoan
(
    maNhanVien Nvarchar(50) Primary Key,
    matKhau Nvarchar(50),
    quyenHan Nvarchar(50)
)

/* Tạo bảng NhanVien */
create table NhanVien
(
    maNhanVien NVARCHAR(50) NOT NULL PRIMARY KEY,
    tenNhanVien NVARCHAR(100),
    soDienThoai NVARCHAR(20),
    email NVARCHAR(150),
    canCuocCongDan NVARCHAR(20),
    gioiTinh BIT,
    ngayVaoLam DATETIME,
    luongNhanVien FLOAT,
    trangThai BIT,
    anhNhanVien NVARCHAR(255),
	isActivate NVARCHAR(10) NOT NULL DEFAULT 'ACTIVATE' CHECK (isActivate IN ('ACTIVATE', 'DEACTIVATE')),
    -- SỬA 4: Ràng buộc khóa ngoại nên đặt ở đây
    CONSTRAINT [FK_NhanVien_TaiKhoan] FOREIGN KEY([maNhanVien]) REFERENCES [dbo].[TaiKhoan] ([maNhanVien])
)

/* Tạo bảng DonDatMon */
create table DonDatMon
(
	maDonDatMon Nvarchar(50) not null primary key,
	maBanAn Nvarchar(50) foreign key references BanAn(maBanAn),
	maNhanVien Nvarchar(50) foreign key references NhanVien(maNhanVien),
	ngayGoi Datetime,
	trangThai Nvarchar(50)
)

/* Tạo bảng MonAn */
create table MonAn
(
	maMonAn Nvarchar(50) not null primary key,
	tenMonAn Nvarchar(50),
	gia float,
	anhMonAn Nvarchar(50)
)

/* Tạo bảng ChiTietDonDatMon */
create table ChiTietDonDatMon
(
	maDonDatMon Nvarchar(50) not null foreign key references DonDatMon(maDonDatMon),
	maMonAn Nvarchar(50) not null foreign key references MonAn(maMonAn),
	soLuong int,
	ghiChu Nvarchar(50),
	primary key (maDonDatMon, maMonAn)
)

CREATE TABLE LoaiKhachHang (
    maLoaiKhachHang NVARCHAR(50) PRIMARY KEY,
    tenLoaiKhachHang NVARCHAR(100),
    moTa NVARCHAR(255),
    diemToiThieu FLOAT
);

/* Tạo bảng KhachHang */
create table KhachHang
(
	maKhachHang NVARCHAR(50) NOT NULL PRIMARY KEY,
    tenKhachHang NVARCHAR(100),
    soDienThoai NVARCHAR(20),
    
    /* SỬA: Thay thế 'loaiKhachHang NVARCHAR(50)' bằng khóa ngoại */
    maLoaiKhachHang NVARCHAR(50) FOREIGN KEY REFERENCES LoaiKhachHang(maLoaiKhachHang),
    
    diemTichLuy FLOAT,
    email NVARCHAR(100), /* Thêm cột email đã có trong DAO */
	isActivate NVARCHAR(10) NOT NULL DEFAULT 'ACTIVATE' CHECK (isActivate IN ('ACTIVATE', 'DEACTIVATE'))
)

 /* Tạo bảng DonDatBan (Đã sửa) */
 create table DonDatBan
 (
	maDonDatBan NVARCHAR(50) NOT NULL PRIMARY KEY,
    maKhachHang NVARCHAR(50) FOREIGN KEY REFERENCES KhachHang(maKhachHang),
    -- SỬA 3: XÓA cột maBanAn, vì đã có ChiTietDonDatBan
    maNhanVien NVARCHAR(50) FOREIGN KEY REFERENCES NhanVien(maNhanVien),
    ngayDat DATETIME,
    ngayTaoDon DATETIME DEFAULT GETDATE(),
	thoiGianCheckIn DATETIME NULL,
    -- SỬA 2: THÊM cột thoiGianCheckOut
    thoiGianCheckOut DATETIME NULL, 
	isActivate NVARCHAR(10) NOT NULL DEFAULT 'ACTIVATE' CHECK (isActivate IN ('ACTIVATE', 'DEACTIVATE'))
 )

 create table ChiTietDonDatBan
 (
	maDonDatBan Nvarchar(50) foreign key references DonDatBan(maDonDatBan),
	maBanAn Nvarchar(50) foreign key references BanAn(maBanAn),
	tienCoc float,
    PRIMARY KEY (maDonDatBan, maBanAn) -- Nên có khóa chính
 )
  
/* Tạo bảng khuyen mai */
CREATE TABLE KhuyenMai
(
    maKM NVARCHAR(50) NOT NULL PRIMARY KEY,
    tenKM NVARCHAR(100),
    ngayBatDau DATE,
    ngayKetThuc DATE,
    giaTriKM FLOAT
)

/* Tạo bảng HoaDon */
create table HoaDon
(
	maHoaDon Nvarchar(50) not null primary key,
	maBanAn Nvarchar(50) foreign key references BanAn(maBanAn),
	maKhachHang Nvarchar(50) foreign key references KhachHang(maKhachHang),
	maNhanVien Nvarchar(50) foreign key references NhanVien(maNhanVien),
	maKM NVARCHAR(50) NULL FOREIGN KEY REFERENCES KhuyenMai(maKM),
	ngayThanhToan datetime,
	diemSuDung int,
	tongTien float,
	chetKhauVat float,
	tienGiam float,
	tienNhan float,
	tienThua float,
	hinhThuc Nvarchar(50)
)

/* Tạo bảng ChiTietHoaDon */
create table ChiTietHoaDon
(
	maMonAn Nvarchar(50) not null foreign key references MonAn(maMonAn),
	maHoaDon Nvarchar(50) not null foreign key references HoaDon(maHoaDon),
	soLuong int,
	thanhTien float,
	primary key (maMonAn, maHoaDon)
)

-- Dữ liệu (Sắp xếp lại)
INSERT INTO TaiKhoan (maNhanVien, matKhau, quyenHan)
VALUES
('NV001', '123456', N'staff'), ('NV002', '123456', N'staff'), ('NV003', '123456', N'staff'),
('NV004', '123456', N'staff'), ('NV005', '123456', N'staff'), ('NV006', '123456', N'staff'),
('NV007', '123456', N'staff'), ('NV008', '123456', N'staff'), ('NV009', '123456', N'staff'),
('NV010', '123456', N'staff'), ('NV011', '123456', N'staff'), ('NV012', '123456', N'staff'),
('NV013', '123456', N'staff'), ('NV014', '123456', N'staff'), ('NV015', '123456', N'staff'),
('NV016', '123456', N'staff'), ('NV017', '123456', N'staff'), ('NV018', '123456', N'staff'),
('NV019', '123456', N'staff'), ('NV020', '123456', N'admin');

INSERT INTO NhanVien (maNhanVien, tenNhanVien, soDienThoai, email, canCuocCongDan, gioiTinh, ngayVaoLam, luongNhanVien, trangThai, anhNhanVien)
VALUES
('NV001', N'Nguyễn Văn An', '0905123456', 'an.nguyen@restaurant.vn', '079123456789', 1, '2022-03-15', 8500000, 1, ''),
('NV002', N'Lê Thị Hồng', '0905123789', 'hong.le@restaurant.vn', '079987654321', 0, '2023-01-10', 7800000, 1, ''),
('NV003', N'Trần Quốc Huy', '0905345678', 'huy.tran@restaurant.vn', '079345678912', 1, '2021-07-25', 9000000, 1, ''),
('NV004', N'Phạm Thị Mai', '0905121111', 'mai.pham@restaurant.vn', '079654321098', 0, '2020-11-12', 8200000, 1, ''),
('NV005', N'Võ Minh Tuấn', '0905987456', 'tuan.vo@restaurant.vn', '079112233445', 1, '2024-05-01', 7000000, 1, ''),
('NV006', N'Nguyễn Thị Thu', '0905777123', 'thu.nguyen@restaurant.vn', '079556677889', 0, '2022-09-09', 7600000, 1, ''),
('NV007', N'Huỳnh Tấn Lộc', '0905666888', 'loc.huynh@restaurant.vn', '079334455667', 1, '2023-08-21', 8800000, 1, ''),
('NV008', N'Phan Thị Ngọc', '0905444333', 'ngoc.phan@restaurant.vn', '079445566778', 0, '2021-04-10', 8100000, 1, ''),
('NV009', N'Bùi Văn Duy', '0905000999', 'duy.bui@restaurant.vn', '079667788990', 1, '2020-12-05', 9500000, 1, ''),
('NV010', N'Trịnh Thị Lan', '0905234567', 'lan.trinh@restaurant.vn', '079998877665', 0, '2023-02-17', 7800000, 1, ''),
('NV011', N'Lâm Quốc Bảo', '0905333222', 'bao.lam@restaurant.vn', '079121212121', 1, '2021-05-30', 9100000, 1, ''),
('NV012', N'Trần Thị Cúc', '0905123123', 'cuc.tran@restaurant.vn', '079343434343', 0, '2024-01-01', 7300000, 1, ''),
('NV013', N'Đặng Tấn Phát', '0905666777', 'phat.dang@restaurant.vn', '079565656565', 1, '2020-08-09', 9700000, 1, ''),
('NV014', N'Lê Mỹ Dung', '0905888999', 'dung.le@restaurant.vn', '079787878787', 0, '2023-03-22', 8500000, 1, ''),
('NV015', N'Vũ Thanh Bình', '0905444555', 'binh.vu@restaurant.vn', '079909090909', 1, '2022-02-11', 8900000, 1, ''),
('NV016', N'Ngô Thị Hiền', '0905777666', 'hien.ngo@restaurant.vn', '079808080808', 0, '2021-09-14', 7800000, 1, ''),
('NV017', N'Phạm Anh Khoa', '0905999000', 'khoa.pham@restaurant.vn', '079676767676', 1, '2024-07-19', 7200000, 1, ''),
('NV018', N'Trần Ngọc Bích', '0905333555', 'bich.tran@restaurant.vn', '079121298765', 0, '2020-06-07', 9100000, 1, ''),
('NV019', N'Lưu Văn Tín', '0905888777', 'tin.luu@restaurant.vn', '079565645645', 1, '2021-01-25', 8800000, 0, ''),
('NV020', N'Hoàng Thị Yến', '0905999888', 'yen.hoang@restaurant.vn', '079989898989', 0, '2022-04-10', 8400000, 1, '');

INSERT INTO LoaiKhachHang (maLoaiKhachHang, tenLoaiKhachHang, moTa, diemToiThieu)
VALUES
('LKH01', N'Thường', N'Khách hàng mới', 0),
('LKH02', N'Thân thiết', N'Khách hàng có điểm tích lũy', 400),
('LKH03', N'VIP', N'Khách hàng cao cấp', 1000);

INSERT INTO KhachHang (maKhachHang, tenKhachHang, soDienThoai, maLoaiKhachHang, diemTichLuy, email)
VALUES
('KH001', N'Nguyễn Văn Minh', '0905123456', 'LKH01', 120, 'minh.nv@gmail.com'),
('KH002', N'Lê Thị Hồng', '0905345678', 'LKH02', 560, 'hong.lt@gmail.com'),
('KH003', N'Trần Quốc Huy', '0905567890', 'LKH03', 1200, 'huy.tq@gmail.com'),
('KH004', N'Phạm Thị Mai', '0905789123', 'LKH01', 90, 'mai.pt@gmail.com'),
('KH005', N'Võ Minh Tuấn', '0905901234', 'LKH02', 450, 'tuan.vm@gmail.com'),
('KH006', N'Nguyễn Thị Thu', '0905123123', 'LKH01', 150, 'thu.nt@gmail.com'),
('KH007', N'Huỳnh Tấn Lộc', '0905234234', 'LKH02', 620, 'loc.ht@gmail.com'),
('KH008', N'Phan Thị Ngọc', '0905345345', 'LKH01', 80, 'ngoc.pt@gmail.com'),
('KH009', N'Bùi Văn Duy', '0905456456', 'LKH03', 1300, 'duy.bv@gmail.com'),
('KH010', N'Trịnh Thị Lan', '0905567567', 'LKH02', 520, 'lan.tt@gmail.com'),
('KH011', N'Lâm Quốc Bảo', '0905678678', 'LKH01', 200, 'bao.lq@gmail.com'),
('KH012', N'Trần Thị Cúc', '0905789789', 'LKH02', 480, 'cuc.tt@gmail.com'),
('KH013', N'Đặng Tấn Phát', '0905890890', 'LKH03', 1550, 'phat.dt@gmail.com'),
('KH014', N'Lê Mỹ Dung', '0905901901', 'LKH01', 170, 'dung.lm@gmail.com'),
('KH015', N'Vũ Thanh Bình', '0905012012', 'LKH02', 530, 'binh.vt@gmail.com'),
('KH016', N'Ngô Thị Hiền', '0905123123', 'LKH01', 140, 'hien.nt@gmail.com');

-- SỬA: Phải thêm dữ liệu cho LoaiKhuVuc
INSERT INTO LoaiKhuVuc (maLoaiKhuVuc, tenLoaiKhuVuc)
VALUES
('LKV01', N'Thường'),
('LKV02', N'VIP'),
('LKV03', N'Ngoài trời');

-- SỬA: Cập nhật KhuVuc để dùng maLoaiKhuVuc
insert into KhuVuc (maKhuVuc, tenKhuVuc, maLoaiKhuVuc) values
('KV1', N'Ngoài trời', 'LKV03'),
('KV2', N'Trong nhà', 'LKV01'),
('KV3', N'Tầng 1 (VIP)', 'LKV02'); -- Giả sử KV3 là VIP

insert into BanAn (maBanAn, tenBanAn, soChoNgoi, maKhuVuc, trangThai, loaiBan, ghiChu) values
-- Khu Vực 3 (KV3 - VIP) - Tổng 19 bàn
('BA301', N'301', 16, 'KV3', N'Trống', N'VIP', N''), ('BA302', N'302', 16, 'KV3', N'Trống', N'VIP', N''),
('BA303', N'303', 10, 'KV3', N'Trống', N'VIP', N''), ('BA304', N'304', 10, 'KV3', N'Trống', N'VIP', N''),
('BA305', N'305', 6, 'KV3', N'Trống', N'VIP', N''), ('BA306', N'306', 6, 'KV3', N'Trống', N'VIP', N''),
('BA307', N'307', 6, 'KV3', N'Trống', N'VIP', N''), ('BA308', N'308', 6, 'KV3', N'Trống', N'VIP', N''),
('BA309', N'309', 4, 'KV3', N'Trống', N'VIP', N''), ('BA310', N'310', 4, 'KV3', N'Trống', N'VIP', N''),
('BA311', N'311', 4, 'KV3', N'Trống', N'VIP', N''), ('BA312', N'312', 4, 'KV3', N'Trống', N'VIP', N''),
('BA313', N'313', 4, 'KV3', N'Trống', N'VIP', N''), ('BA314', N'314', 2, 'KV3', N'Trống', N'VIP', N''),
('BA315', N'315', 2, 'KV3', N'Trống', N'VIP', N''), ('BA316', N'316', 2, 'KV3', N'Trống', N'VIP', N''),
('BA317', N'317', 2, 'KV3', N'Trống', N'VIP', N''), ('BA318', N'318', 2, 'KV3', N'Trống', N'VIP', N''),
('BA319', N'319', 2, 'KV3', N'Trống', N'VIP', N''),

-- Khu Vực 2 (KV2 - Thường) - Tổng 22 bàn
('BA201', N'201', 16, 'KV2', N'Trống', N'Thường', N''), ('BA202', N'202', 16, 'KV2', N'Trống', N'Thường', N''),
('BA203', N'203', 10, 'KV2', N'Trống', N'Thường', N''), ('BA204', N'204', 10, 'KV2', N'Trống', N'Thường', N''),
('BA205', N'205', 6, 'KV2', N'Trống', N'Thường', N''), ('BA206', N'206', 6, 'KV2', N'Trống', N'Thường', N''),
('BA207', N'207', 6, 'KV2', N'Trống', N'Thường', N''), ('BA208', N'208', 6, 'KV2', N'Trống', N'Thường', N''),
('BA209', N'209', 4, 'KV2', N'Trống', N'Thường', N''), ('BA210', N'210', 4, 'KV2', N'Trống', N'Thường', N''),
('BA211', N'211', 4, 'KV2', N'Trống', N'Thường', N''), ('BA212', N'212', 4, 'KV2', N'Trống', N'Thường', N''),
('BA213', N'213', 4, 'KV2', N'Trống', N'Thường', N''), ('BA214', N'214', 4, 'KV2', N'Trống', N'Thường', N''),
('BA215', N'215', 2, 'KV2', N'Trống', N'Thường', N''), ('BA216', N'216', 2, 'KV2', N'Trống', N'Thường', N''),
('BA217', N'217', 2, 'KV2', N'Trống', N'Thường', N''), ('BA218', N'218', 2, 'KV2', N'Trống', N'Thường', N''),
('BA219', N'219', 2, 'KV2', N'Trống', N'Thường', N''), ('BA220', N'220', 2, 'KV2', N'Trống', N'Thường', N''),
('BA221', N'221', 2, 'KV2', N'Trống', N'Thường', N''), ('BA222', N'222', 2, 'KV2', N'Trống', N'Thường', N''),

-- Khu Vực 1 (KV1 - Thường) - Tổng 16 bàn
('BA101', N'101', 16, 'KV1', N'Trống', N'Thường', N''), ('BA102', N'102', 10, 'KV1', N'Trống', N'Thường', N''),
('BA103', N'103', 6,  'KV1', N'Trống', N'Thường', N''), ('BA104', N'104', 6,  'KV1', N'Trống', N'Thường', N''),
('BA105', N'105', 6,  'KV1', N'Trống', N'Thường', N''), ('BA106', N'106', 4,  'KV1', N'Trống', N'Thường', N''),
('BA107', N'107', 4,  'KV1', N'Trống', N'Thường', N''), ('BA108', N'108', 4,  'KV1', N'Trống', N'Thường', N''),
('BA109', N'109', 4,  'KV1', N'Trống', N'Thường', N''), ('BA110', N'110', 4,  'KV1', N'Trống', N'Thường', N''),
('BA111', N'111', 2,  'KV1', N'Trống', N'Thường', N''), ('BA112', N'112', 2,  'KV1', N'Trống', N'Thường', N''),
('BA113', N'113', 2,  'KV1', N'Trống', N'Thường', N''), ('BA114', N'114', 2,  'KV1', N'Trống', N'Thường', N''),
('BA115', N'115', 2,  'KV1', N'Trống', N'Thường', N''), ('BA116', N'116', 2,  'KV1', N'Trống', N'Thường', N'');