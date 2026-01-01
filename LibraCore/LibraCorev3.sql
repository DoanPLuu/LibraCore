CREATE TABLE IF NOT EXISTS `NXB` (
	`id_NXB` INTEGER NOT NULL AUTO_INCREMENT,
	`TenNXB` VARCHAR(255),
	`DiaChi` VARCHAR(255),
	`SDT` VARCHAR(20),
	PRIMARY KEY(`id_NXB`)
);


CREATE TABLE IF NOT EXISTS `TheLoai` (
	`id_TheLoai` INTEGER NOT NULL AUTO_INCREMENT,
	`TenTheLoai` VARCHAR(255),
	PRIMARY KEY(`id_TheLoai`)
);


CREATE TABLE IF NOT EXISTS `TacGia` (
	`id_TacGia` INTEGER NOT NULL AUTO_INCREMENT,
	`TenTacGia` VARCHAR(255),
	`NgaySinh` DATE,
	`NoiSinh` VARCHAR(255),
	`SDT` VARCHAR(20),
	PRIMARY KEY(`id_TacGia`)
);


CREATE TABLE IF NOT EXISTS `MucPhat` (
	`id_MucPhat` INTEGER NOT NULL AUTO_INCREMENT,
	`TenMucPhat` VARCHAR(255) NOT NULL,
	`LoaiPhat` ENUM('PerDay', 'Fixed') NOT NULL DEFAULT 'PerDay',
	`SoTienPhat` DECIMAL(12,2) NOT NULL,
	`MoTa` VARCHAR(255),
	PRIMARY KEY(`id_MucPhat`)
);


CREATE TABLE IF NOT EXISTS `NCC` (
	`id_NCC` INTEGER NOT NULL AUTO_INCREMENT,
	`TenNCC` VARCHAR(255),
	PRIMARY KEY(`id_NCC`)
);


CREATE TABLE IF NOT EXISTS `VaiTro` (
	`id_VaiTro` INTEGER NOT NULL AUTO_INCREMENT,
	`TenVaiTro` VARCHAR(255),
	PRIMARY KEY(`id_VaiTro`)
);


CREATE TABLE IF NOT EXISTS `Quyen` (
	`id_Quyen` INTEGER NOT NULL AUTO_INCREMENT,
	`TenQuyen` VARCHAR(255),
	PRIMARY KEY(`id_Quyen`)
);


CREATE TABLE IF NOT EXISTS `VaiTro_Quyen` (
	`id_VaiTro` INTEGER NOT NULL,
	`id_Quyen` INTEGER NOT NULL,
	PRIMARY KEY(`id_VaiTro`, `id_Quyen`)
);


CREATE TABLE IF NOT EXISTS `TaiKhoan` (
	`id_TaiKhoan` INTEGER NOT NULL AUTO_INCREMENT,
	`id_VaiTro` INTEGER NOT NULL,
	`TaiKhoan` VARCHAR(255) UNIQUE,
	`MatKhau` VARCHAR(255),
	PRIMARY KEY(`id_TaiKhoan`)
);


CREATE TABLE IF NOT EXISTS `NhanVien` (
	`id_NhanVien` INTEGER NOT NULL AUTO_INCREMENT,
	`id_TaiKhoan` INTEGER,
	`TenNhanVien` VARCHAR(255),
	`NgaySinh` DATE,
	`DiaChi` VARCHAR(255),
	`SDT` VARCHAR(20),
	`Email` VARCHAR(255),
	PRIMARY KEY(`id_NhanVien`)
);


CREATE TABLE IF NOT EXISTS `DocGia` (
	`id_DocGia` INTEGER NOT NULL AUTO_INCREMENT,
	`TenDocGia` VARCHAR(255),
	`DiaChi` VARCHAR(255),
	`NgaySinh` DATE,
	`SDT` VARCHAR(20),
	`Email` VARCHAR(255),
	PRIMARY KEY(`id_DocGia`)
);


CREATE INDEX `idx_docgia_ten`
ON `DocGia` (`TenDocGia`);
CREATE TABLE IF NOT EXISTS `TheThanhVien` (
	`id_TheThanhVien` INTEGER NOT NULL AUTO_INCREMENT,
	`id_DocGia` INTEGER NOT NULL,
	`NgayCap` DATE,
	`NgayHetHan` DATE,
	`TrangThai` ENUM('HoatDong', 'BiKhoa', 'HetHan') DEFAULT 'HoatDong',
	PRIMARY KEY(`id_TheThanhVien`)
);


CREATE TABLE IF NOT EXISTS `Sach` (
	`id_Sach` INTEGER NOT NULL AUTO_INCREMENT,
	`id_NXB` INTEGER,
	`NamXuatBan` YEAR,
	`TenSach` VARCHAR(255),
	`MoTa` VARCHAR(1000),
	`SoTrang` INTEGER,
	PRIMARY KEY(`id_Sach`)
);


CREATE INDEX `idx_sach_tensach`
ON `Sach` (`TenSach`);
CREATE TABLE IF NOT EXISTS `Sach_TheLoai` (
	`id_Sach` INTEGER NOT NULL,
	`id_TheLoai` INTEGER NOT NULL,
	PRIMARY KEY(`id_Sach`, `id_TheLoai`)
);


CREATE TABLE IF NOT EXISTS `Sach_TacGia` (
	`id_Sach` INTEGER NOT NULL,
	`id_TacGia` INTEGER NOT NULL,
	PRIMARY KEY(`id_Sach`, `id_TacGia`)
);


CREATE TABLE IF NOT EXISTS `CuonSach` (
	`id_CuonSach` INTEGER NOT NULL AUTO_INCREMENT,
	`id_Sach` INTEGER NOT NULL,
	`TinhTrangSach` ENUM('Tot', 'Hong', 'Mat') DEFAULT 'Tot',
	`TrangThaiMuon` ENUM('Ranh', 'DangMuon') DEFAULT 'Ranh',
	PRIMARY KEY(`id_CuonSach`)
);


CREATE INDEX `idx_cuon_sach`
ON `CuonSach` (`id_Sach`);
CREATE TABLE IF NOT EXISTS `PhieuNhap` (
	`id_PhieuNhap` INTEGER NOT NULL AUTO_INCREMENT,
	`id_NCC` INTEGER,
	`NgayNhap` DATE,
	`SoLuongSach` INTEGER,
	`LoaiPhieuNhap` ENUM('Mua', 'Tang') DEFAULT 'Mua',
	`id_NhanVien` INTEGER NOT NULL,
	PRIMARY KEY(`id_PhieuNhap`)
);


CREATE TABLE IF NOT EXISTS `ChiTietPhieuNhap` (
	`id_ChiTietPhieuNhap` INTEGER NOT NULL AUTO_INCREMENT,
	`id_PhieuNhap` INTEGER NOT NULL,
	`id_Sach` INTEGER NOT NULL,
	`SoLuong` INTEGER,
	`GiaTien` DECIMAL(12,2),
	`MaDauSach` VARCHAR(255),
	PRIMARY KEY(`id_ChiTietPhieuNhap`)
);


CREATE INDEX `idx_ctpn_phieunhap`
ON `ChiTietPhieuNhap` (`id_PhieuNhap`);
CREATE INDEX `idx_ctpn_sach`
ON `ChiTietPhieuNhap` (`id_Sach`);
CREATE TABLE IF NOT EXISTS `PhieuMuon` (
	`id_PhieuMuon` INTEGER NOT NULL AUTO_INCREMENT,
	`id_NhanVien` INTEGER NOT NULL,
	`id_TheThanhVien` INTEGER,
	`NgayMuon` DATE,
	`NgayHenTra` DATE,
	`TrangThai` ENUM('DangMuon', 'DaTra', 'QuaHen') DEFAULT 'DangMuon',
	`TongSoSachMuon` INTEGER NOT NULL,
	PRIMARY KEY(`id_PhieuMuon`)
);


CREATE INDEX `idx_phieumuon_the`
ON `PhieuMuon` (`id_TheThanhVien`);
CREATE TABLE IF NOT EXISTS `ChiTietPhieuMuon` (
	`id_ChiTietPhieuMuon` INTEGER NOT NULL AUTO_INCREMENT,
	`id_PhieuMuon` INTEGER NOT NULL,
	`id_CuonSach` INTEGER NOT NULL,
	`NgayTra` DATE,
	`TinhTrangTra` ENUM('ChuaTra', 'DaTra', 'TreHan', 'Hong') DEFAULT 'ChuaTra',
	PRIMARY KEY(`id_ChiTietPhieuMuon`)
);


CREATE INDEX `idx_ctpm_cuonsach`
ON `ChiTietPhieuMuon` (`id_CuonSach`);
CREATE INDEX `idx_ctpm_phieumuon`
ON `ChiTietPhieuMuon` (`id_PhieuMuon`);
CREATE TABLE IF NOT EXISTS `PhieuPhat` (
	`id_PhieuPhat` INTEGER NOT NULL AUTO_INCREMENT,
	`NgayLap` DATE,
	`TienPhatPhaiNop` DECIMAL(12,2) DEFAULT 0.00,
	`LyDoPhat` VARCHAR(255),
	`TrangThai` ENUM('DaThu', 'ChuaThu') DEFAULT 'ChuaThu',
	`id_NhanVien` INTEGER NOT NULL,
	PRIMARY KEY(`id_PhieuPhat`)
);


CREATE TABLE IF NOT EXISTS `ChiTietPhieuPhat` (
	`id_ChiTietPhieuPhat` INTEGER NOT NULL AUTO_INCREMENT,
	`id_PhieuPhat` INTEGER NOT NULL,
	`id_ChiTietPhieuMuon` INTEGER NOT NULL,
	`id_MucPhat` INTEGER NOT NULL,
	`SoNgayTreHan` INTEGER DEFAULT 0,
	`TienPhatTra` DECIMAL(12,2) DEFAULT 0.00,
	PRIMARY KEY(`id_ChiTietPhieuPhat`)
);


CREATE INDEX `idx_ctpp_phieuphat`
ON `ChiTietPhieuPhat` (`id_PhieuPhat`);
CREATE INDEX `idx_ctpp_ctpm`
ON `ChiTietPhieuPhat` (`id_ChiTietPhieuMuon`);
ALTER TABLE `VaiTro_Quyen`
ADD FOREIGN KEY(`id_VaiTro`) REFERENCES `VaiTro`(`id_VaiTro`)
ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE `VaiTro_Quyen`
ADD FOREIGN KEY(`id_Quyen`) REFERENCES `Quyen`(`id_Quyen`)
ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE `TaiKhoan`
ADD FOREIGN KEY(`id_VaiTro`) REFERENCES `VaiTro`(`id_VaiTro`)
ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE `NhanVien`
ADD FOREIGN KEY(`id_TaiKhoan`) REFERENCES `TaiKhoan`(`id_TaiKhoan`)
ON UPDATE CASCADE ON DELETE SET NULL;
ALTER TABLE `TheThanhVien`
ADD FOREIGN KEY(`id_DocGia`) REFERENCES `DocGia`(`id_DocGia`)
ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE `Sach`
ADD FOREIGN KEY(`id_NXB`) REFERENCES `NXB`(`id_NXB`)
ON UPDATE CASCADE ON DELETE SET NULL;
ALTER TABLE `Sach_TheLoai`
ADD FOREIGN KEY(`id_Sach`) REFERENCES `Sach`(`id_Sach`)
ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE `Sach_TheLoai`
ADD FOREIGN KEY(`id_TheLoai`) REFERENCES `TheLoai`(`id_TheLoai`)
ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE `Sach_TacGia`
ADD FOREIGN KEY(`id_Sach`) REFERENCES `Sach`(`id_Sach`)
ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE `Sach_TacGia`
ADD FOREIGN KEY(`id_TacGia`) REFERENCES `TacGia`(`id_TacGia`)
ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE `CuonSach`
ADD FOREIGN KEY(`id_Sach`) REFERENCES `Sach`(`id_Sach`)
ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE `PhieuNhap`
ADD FOREIGN KEY(`id_NCC`) REFERENCES `NCC`(`id_NCC`)
ON UPDATE CASCADE ON DELETE SET NULL;
ALTER TABLE `ChiTietPhieuNhap`
ADD FOREIGN KEY(`id_PhieuNhap`) REFERENCES `PhieuNhap`(`id_PhieuNhap`)
ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE `ChiTietPhieuNhap`
ADD FOREIGN KEY(`id_Sach`) REFERENCES `Sach`(`id_Sach`)
ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE `PhieuMuon`
ADD FOREIGN KEY(`id_NhanVien`) REFERENCES `NhanVien`(`id_NhanVien`)
ON UPDATE CASCADE ON DELETE SET NULL;
ALTER TABLE `PhieuMuon`
ADD FOREIGN KEY(`id_TheThanhVien`) REFERENCES `TheThanhVien`(`id_TheThanhVien`)
ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE `ChiTietPhieuMuon`
ADD FOREIGN KEY(`id_PhieuMuon`) REFERENCES `PhieuMuon`(`id_PhieuMuon`)
ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE `ChiTietPhieuMuon`
ADD FOREIGN KEY(`id_CuonSach`) REFERENCES `CuonSach`(`id_CuonSach`)
ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE `ChiTietPhieuPhat`
ADD FOREIGN KEY(`id_PhieuPhat`) REFERENCES `PhieuPhat`(`id_PhieuPhat`)
ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE `ChiTietPhieuPhat`
ADD FOREIGN KEY(`id_ChiTietPhieuMuon`) REFERENCES `ChiTietPhieuMuon`(`id_ChiTietPhieuMuon`)
ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE `ChiTietPhieuPhat`
ADD FOREIGN KEY(`id_MucPhat`) REFERENCES `MucPhat`(`id_MucPhat`)
ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE `PhieuPhat`
ADD FOREIGN KEY(`id_NhanVien`) REFERENCES `NhanVien`(`id_NhanVien`)
ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE `PhieuNhap`
ADD FOREIGN KEY(`id_NhanVien`) REFERENCES `NhanVien`(`id_NhanVien`)
ON UPDATE NO ACTION ON DELETE NO ACTION;