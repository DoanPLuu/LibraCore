-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.4.3 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.8.0.6908
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for libracore
CREATE DATABASE IF NOT EXISTS `libracore` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `libracore`;

-- Dumping structure for table libracore.chitietphieumuon
CREATE TABLE IF NOT EXISTS `chitietphieumuon` (
  `id_ChiTietPhieuMuon` int NOT NULL AUTO_INCREMENT,
  `id_PhieuMuon` int NOT NULL,
  `id_CuonSach` int NOT NULL,
  `NgayTra` date DEFAULT NULL,
  `TinhTrangTra` enum('ChuaTra','DaTra','TreHan','Hong') DEFAULT 'ChuaTra',
  PRIMARY KEY (`id_ChiTietPhieuMuon`),
  KEY `idx_ctpm_cuonsach` (`id_CuonSach`),
  KEY `idx_ctpm_phieumuon` (`id_PhieuMuon`),
  CONSTRAINT `chitietphieumuon_ibfk_1` FOREIGN KEY (`id_PhieuMuon`) REFERENCES `phieumuon` (`id_PhieuMuon`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `chitietphieumuon_ibfk_2` FOREIGN KEY (`id_CuonSach`) REFERENCES `cuonsach` (`id_CuonSach`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table libracore.chitietphieumuon: ~4 rows (approximately)
DELETE FROM `chitietphieumuon`;
INSERT INTO `chitietphieumuon` (`id_ChiTietPhieuMuon`, `id_PhieuMuon`, `id_CuonSach`, `NgayTra`, `TinhTrangTra`) VALUES
	(1, 1, 1, NULL, 'ChuaTra'),
	(2, 1, 2, NULL, 'ChuaTra'),
	(3, 2, 3, NULL, 'ChuaTra'),
	(4, 2, 4, NULL, 'ChuaTra'),
	(5, 3, 6, NULL, 'ChuaTra'),
	(6, 3, 7, NULL, 'ChuaTra'),
	(7, 4, 8, NULL, 'ChuaTra'),
	(8, 4, 9, NULL, 'ChuaTra'),
	(9, 5, 10, NULL, 'ChuaTra'),
	(10, 5, 14, NULL, 'ChuaTra'),
	(11, 6, 5, '2026-11-16', 'Hong'),
	(12, 6, 11, NULL, 'ChuaTra'),
	(13, 7, 12, '2026-11-16', 'Hong'),
	(14, 7, 16, NULL, 'ChuaTra'),
	(15, 8, 19, '2026-11-17', 'Hong'),
	(16, 8, 20, NULL, 'ChuaTra'),
	(17, 8, 22, NULL, 'ChuaTra'),
	(18, 9, 26, '2026-11-17', 'Hong'),
	(19, 9, 27, NULL, 'ChuaTra'),
	(20, 9, 28, NULL, 'ChuaTra'),
	(21, 10, 33, '2026-09-18', 'TreHan'),
	(22, 11, 34, NULL, 'ChuaTra'),
	(23, 12, 35, NULL, 'ChuaTra'),
	(24, 13, 36, NULL, 'ChuaTra'),
	(25, 14, 37, NULL, 'ChuaTra'),
	(26, 15, 38, NULL, 'ChuaTra'),
	(27, 16, 39, NULL, 'ChuaTra'),
	(28, 17, 40, NULL, 'ChuaTra'),
	(29, 18, 41, NULL, 'ChuaTra'),
	(30, 19, 42, NULL, 'ChuaTra'),
	(31, 20, 43, NULL, 'ChuaTra');

-- Dumping structure for table libracore.chitietphieunhap
CREATE TABLE IF NOT EXISTS `chitietphieunhap` (
  `id_ChiTietPhieuNhap` int NOT NULL AUTO_INCREMENT,
  `id_PhieuNhap` int NOT NULL,
  `id_Sach` int NOT NULL,
  `SoLuong` int DEFAULT NULL,
  `GiaTien` decimal(12,2) DEFAULT NULL,
  PRIMARY KEY (`id_ChiTietPhieuNhap`),
  KEY `idx_ctpn_phieunhap` (`id_PhieuNhap`),
  KEY `idx_ctpn_sach` (`id_Sach`),
  CONSTRAINT `chitietphieunhap_ibfk_1` FOREIGN KEY (`id_PhieuNhap`) REFERENCES `phieunhap` (`id_PhieuNhap`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `chitietphieunhap_ibfk_2` FOREIGN KEY (`id_Sach`) REFERENCES `sach` (`id_Sach`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table libracore.chitietphieunhap: ~0 rows (approximately)
DELETE FROM `chitietphieunhap`;
INSERT INTO `chitietphieunhap` (`id_ChiTietPhieuNhap`, `id_PhieuNhap`, `id_Sach`, `SoLuong`, `GiaTien`) VALUES
	(1, 1, 1, 5, 500000.00),
	(2, 2, 2, 5, 450000.00),
	(3, 3, 3, 5, 400000.00),
	(4, 4, 4, 5, 425000.00),
	(5, 5, 5, 5, 550000.00),
	(6, 6, 6, 5, 600000.00),
	(7, 7, 7, 5, 500000.00),
	(8, 8, 8, 5, 475000.00),
	(9, 9, 9, 5, 650000.00),
	(10, 10, 10, 5, 700000.00);

-- Dumping structure for table libracore.chitietphieuphat
CREATE TABLE IF NOT EXISTS `chitietphieuphat` (
  `id_ChiTietPhieuPhat` int NOT NULL AUTO_INCREMENT,
  `id_PhieuPhat` int NOT NULL,
  `id_ChiTietPhieuMuon` int NOT NULL,
  `id_MucPhat` int NOT NULL,
  `SoNgayTreHan` int DEFAULT '0',
  `TienPhatTra` decimal(12,2) DEFAULT '0.00',
  PRIMARY KEY (`id_ChiTietPhieuPhat`),
  KEY `idx_ctpp_phieuphat` (`id_PhieuPhat`),
  KEY `idx_ctpp_ctpm` (`id_ChiTietPhieuMuon`),
  KEY `id_MucPhat` (`id_MucPhat`),
  CONSTRAINT `chitietphieuphat_ibfk_1` FOREIGN KEY (`id_PhieuPhat`) REFERENCES `phieuphat` (`id_PhieuPhat`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `chitietphieuphat_ibfk_2` FOREIGN KEY (`id_ChiTietPhieuMuon`) REFERENCES `chitietphieumuon` (`id_ChiTietPhieuMuon`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `chitietphieuphat_ibfk_3` FOREIGN KEY (`id_MucPhat`) REFERENCES `mucphat` (`id_MucPhat`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table libracore.chitietphieuphat: ~2 rows (approximately)
DELETE FROM `chitietphieuphat`;
INSERT INTO `chitietphieuphat` (`id_ChiTietPhieuPhat`, `id_PhieuPhat`, `id_ChiTietPhieuMuon`, `id_MucPhat`, `SoNgayTreHan`, `TienPhatTra`) VALUES
	(1, 1, 11, 4, 0, 30000.00),
	(2, 2, 13, 4, 0, 30000.00),
	(3, 3, 15, 4, 0, 30000.00),
	(4, 4, 18, 4, 0, 30000.00),
	(5, 5, 21, 3, 15, 75000.00);

-- Dumping structure for table libracore.cuonsach
CREATE TABLE IF NOT EXISTS `cuonsach` (
  `id_CuonSach` int NOT NULL AUTO_INCREMENT,
  `id_Sach` int NOT NULL,
  `TinhTrangSach` enum('Tot','Hong','Mat') DEFAULT 'Tot',
  `TrangThaiMuon` enum('Ranh','DangMuon') DEFAULT 'Ranh',
  `DaHuy` tinyint(1) DEFAULT NULL,
  `MaCuonSach` varchar(50) NOT NULL,
  `id_ChiTietPhieuNhap` int DEFAULT NULL,
  PRIMARY KEY (`id_CuonSach`),
  UNIQUE KEY `uq_cuonsach_macuonsach` (`MaCuonSach`),
  KEY `idx_cuonsach_sach` (`id_Sach`),
  KEY `idx_cuonsach_ctpn` (`id_ChiTietPhieuNhap`),
  CONSTRAINT `cuonsach_ibfk_1` FOREIGN KEY (`id_Sach`) REFERENCES `sach` (`id_Sach`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_cuonsach_ctpn` FOREIGN KEY (`id_ChiTietPhieuNhap`) REFERENCES `chitietphieunhap` (`id_ChiTietPhieuNhap`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=143 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table libracore.cuonsach: ~5 rows (approximately)
DELETE FROM `cuonsach`;
INSERT INTO `cuonsach` (`id_CuonSach`, `id_Sach`, `TinhTrangSach`, `TrangThaiMuon`, `DaHuy`, `MaCuonSach`, `id_ChiTietPhieuNhap`) VALUES
	(1, 1, 'Tot', 'DangMuon', 0, 'S1-0001', 1),
	(2, 1, 'Tot', 'DangMuon', 0, 'S1-0002', 1),
	(3, 1, 'Tot', 'DangMuon', 0, 'S1-0003', 1),
	(4, 1, 'Tot', 'DangMuon', 0, 'S1-0004', 1),
	(5, 1, 'Hong', 'Ranh', 0, 'S1-0005', 1),
	(6, 2, 'Tot', 'DangMuon', 0, 'S2-0001', 2),
	(7, 2, 'Tot', 'DangMuon', 0, 'S2-0002', 2),
	(8, 2, 'Tot', 'DangMuon', 0, 'S2-0003', 2),
	(9, 2, 'Tot', 'DangMuon', 0, 'S2-0004', 2),
	(10, 2, 'Tot', 'DangMuon', 0, 'S2-0005', 2),
	(11, 3, 'Tot', 'DangMuon', 0, 'S3-0001', 3),
	(12, 3, 'Hong', 'Ranh', 0, 'S3-0002', 3),
	(13, 3, 'Tot', 'Ranh', 0, 'S3-0003', 3),
	(14, 3, 'Tot', 'DangMuon', 0, 'S3-0004', 3),
	(15, 3, 'Tot', 'Ranh', 0, 'S3-0005', 3),
	(16, 4, 'Tot', 'DangMuon', 0, 'S4-0001', 4),
	(17, 4, 'Tot', 'Ranh', 0, 'S4-0002', 4),
	(18, 4, 'Tot', 'Ranh', 0, 'S4-0003', 4),
	(19, 4, 'Hong', 'Ranh', 0, 'S4-0004', 4),
	(20, 4, 'Tot', 'DangMuon', 0, 'S4-0005', 4),
	(21, 5, 'Tot', 'Ranh', 0, 'S5-0001', 5),
	(22, 5, 'Tot', 'DangMuon', 0, 'S5-0002', 5),
	(23, 5, 'Tot', 'Ranh', 0, 'S5-0003', 5),
	(24, 5, 'Tot', 'Ranh', 0, 'S5-0004', 5),
	(25, 5, 'Tot', 'Ranh', 0, 'S5-0005', 5),
	(26, 6, 'Hong', 'Ranh', 0, 'S6-0001', 6),
	(27, 6, 'Tot', 'DangMuon', 0, 'S6-0002', 6),
	(28, 6, 'Tot', 'DangMuon', 0, 'S6-0003', 6),
	(29, 6, 'Tot', 'Ranh', 0, 'S6-0004', 6),
	(30, 6, 'Tot', 'Ranh', 0, 'S6-0005', 6),
	(31, 7, 'Tot', 'Ranh', 0, 'S7-0001', 7),
	(32, 7, 'Tot', 'Ranh', 0, 'S7-0002', 7),
	(33, 7, 'Tot', 'Ranh', 0, 'S7-0003', 7),
	(34, 7, 'Tot', 'DangMuon', 0, 'S7-0004', 7),
	(35, 7, 'Tot', 'DangMuon', 0, 'S7-0005', 7),
	(36, 8, 'Tot', 'DangMuon', 0, 'S8-0001', 8),
	(37, 8, 'Tot', 'DangMuon', 0, 'S8-0002', 8),
	(38, 8, 'Tot', 'DangMuon', 0, 'S8-0003', 8),
	(39, 8, 'Tot', 'DangMuon', 0, 'S8-0004', 8),
	(40, 8, 'Tot', 'DangMuon', 0, 'S8-0005', 8),
	(41, 9, 'Tot', 'DangMuon', 0, 'S9-0001', 9),
	(42, 9, 'Tot', 'DangMuon', 0, 'S9-0002', 9),
	(43, 9, 'Tot', 'DangMuon', 0, 'S9-0003', 9),
	(44, 9, 'Tot', 'Ranh', 0, 'S9-0004', 9),
	(45, 9, 'Tot', 'Ranh', 0, 'S9-0005', 9),
	(46, 10, 'Tot', 'Ranh', 0, 'S10-0001', 10),
	(47, 10, 'Tot', 'Ranh', 0, 'S10-0002', 10),
	(48, 10, 'Tot', 'Ranh', 0, 'S10-0003', 10),
	(49, 10, 'Tot', 'Ranh', 0, 'S10-0004', 10),
	(50, 10, 'Tot', 'Ranh', 0, 'S10-0005', 10);

-- Dumping structure for table libracore.docgia
CREATE TABLE IF NOT EXISTS `docgia` (
  `id_DocGia` int NOT NULL AUTO_INCREMENT,
  `TenDocGia` varchar(255) DEFAULT NULL,
  `DiaChi` varchar(255) DEFAULT NULL,
  `NgaySinh` date DEFAULT NULL,
  `SDT` varchar(20) DEFAULT NULL,
  `Email` varchar(255) DEFAULT NULL,
  `HoatDong` tinyint(1) NOT NULL,
  PRIMARY KEY (`id_DocGia`),
  KEY `idx_docgia_ten` (`TenDocGia`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table libracore.docgia: ~0 rows (approximately)
DELETE FROM `docgia`;
INSERT INTO `docgia` (`id_DocGia`, `TenDocGia`, `DiaChi`, `NgaySinh`, `SDT`, `Email`, `HoatDong`) VALUES
	(1, 'Nguyễn Minh Anh', 'Quận 1, TP. Hồ Chí Minh', '2002-04-12', '0901000001', 'minhanh01@example.com', 1),
	(2, 'Trần Quốc Bảo', 'Thủ Đức, TP. Hồ Chí Minh', '2001-08-23', '0901000002', 'quocbao02@example.com', 1),
	(3, 'Lê Ngọc Chi', 'Biên Hòa, Đồng Nai', '2003-01-15', '0901000003', 'ngocchi03@example.com', 1),
	(4, 'Phạm Gia Huy', 'Dĩ An, Bình Dương', '2000-11-04', '0901000004', 'giahuy04@example.com', 1),
	(5, 'Võ Thảo Linh', 'Quận 3, TP. Hồ Chí Minh', '2002-06-30', '0901000005', 'thaolinh05@example.com', 1),
	(6, 'Đặng Hoàng Nam', 'Tân Bình, TP. Hồ Chí Minh', '1999-03-08', '0901000006', 'hoangnam06@example.com', 1),
	(7, 'Bùi Khánh Ngân', 'Long Khánh, Đồng Nai', '2004-02-19', '0901000007', 'khanhngan07@example.com', 1),
	(8, 'Phan Đức Phúc', 'Thuận An, Bình Dương', '2001-12-11', '0901000008', 'ducphuc08@example.com', 1),
	(9, 'Huỳnh Mai Phương', 'Quận 5, TP. Hồ Chí Minh', '2003-07-26', '0901000009', 'maiphuong09@example.com', 1),
	(10, 'Đỗ Thanh Tâm', 'Vũng Tàu, Bà Rịa - Vũng Tàu', '2000-05-17', '0901000010', 'thanhtam10@example.com', 1),
	(11, 'Ngô Nhật Vy', 'Quận 7, TP. Hồ Chí Minh', '2002-09-02', '0901000011', 'nhatvy11@example.com', 1),
	(12, 'Dương Anh Tuấn', 'Thủ Dầu Một, Bình Dương', '1998-10-29', '0901000012', 'anhtuan12@example.com', 1),
	(13, 'Lý Mỹ Duyên', 'Quận 10, TP. Hồ Chí Minh', '2004-01-21', '0901000013', 'myduyen13@example.com', 1),
	(14, 'Trịnh Hoài An', 'Bến Cát, Bình Dương', '2001-02-14', '0901000014', 'hoaian14@example.com', 1),
	(15, 'Mai Thành Đạt', 'Trảng Bom, Đồng Nai', '1999-06-05', '0901000015', 'thanhdat15@example.com', 1),
	(16, 'Cao Bảo Trân', 'Quận 11, TP. Hồ Chí Minh', '2003-03-27', '0901000016', 'baotran16@example.com', 1),
	(17, 'Vũ Hải Đăng', 'Củ Chi, TP. Hồ Chí Minh', '2000-12-18', '0901000017', 'haidang17@example.com', 1),
	(18, 'Tạ Quỳnh Như', 'Bà Rịa, Bà Rịa - Vũng Tàu', '2002-07-09', '0901000018', 'quynhnhu18@example.com', 1),
	(19, 'Hồ Minh Khang', 'Quận 8, TP. Hồ Chí Minh', '2001-05-31', '0901000019', 'minhkhang19@example.com', 1),
	(20, 'Đinh Yến Nhi', 'Nhơn Trạch, Đồng Nai', '2004-10-06', '0901000020', 'yennhi20@example.com', 1);

-- Dumping structure for table libracore.mucphat
CREATE TABLE IF NOT EXISTS `mucphat` (
  `id_MucPhat` int NOT NULL AUTO_INCREMENT,
  `TenMucPhat` varchar(255) NOT NULL,
  `LoaiPhat` enum('PerDay','Fixed') NOT NULL DEFAULT 'PerDay',
  `SoTienPhat` decimal(12,2) NOT NULL,
  `MoTa` varchar(255) DEFAULT NULL,
  `HoatDong` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_MucPhat`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table libracore.mucphat: ~0 rows (approximately)
DELETE FROM `mucphat`;
INSERT INTO `mucphat` (`id_MucPhat`, `TenMucPhat`, `LoaiPhat`, `SoTienPhat`, `MoTa`, `HoatDong`) VALUES
	(3, 'Phạt trễ hạn', 'PerDay', 5000.00, 'Trễ hạn tính theo từng ngày', 1),
	(4, 'Phạt làm ướt sách', 'Fixed', 30000.00, 'Phạt làm ướt sách', 1);

-- Dumping structure for table libracore.ncc
CREATE TABLE IF NOT EXISTS `ncc` (
  `id_NCC` int NOT NULL AUTO_INCREMENT,
  `TenNCC` varchar(255) DEFAULT NULL,
  `HoatDong` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_NCC`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table libracore.ncc: ~0 rows (approximately)
DELETE FROM `ncc`;
INSERT INTO `ncc` (`id_NCC`, `TenNCC`, `HoatDong`) VALUES
	(7, 'Công ty TNHH Thái Hà', 1),
	(8, 'Công ty TNHH Thành Công', 1);

-- Dumping structure for table libracore.nhanvien
CREATE TABLE IF NOT EXISTS `nhanvien` (
  `id_NhanVien` int NOT NULL AUTO_INCREMENT,
  `id_TaiKhoan` int DEFAULT NULL,
  `TenNhanVien` varchar(255) DEFAULT NULL,
  `NgaySinh` date DEFAULT NULL,
  `DiaChi` varchar(255) DEFAULT NULL,
  `SDT` varchar(20) DEFAULT NULL,
  `Email` varchar(255) DEFAULT NULL,
  `HoatDong` tinyint(1) NOT NULL,
  `AnhNhanVien` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_NhanVien`),
  KEY `id_TaiKhoan` (`id_TaiKhoan`),
  CONSTRAINT `nhanvien_ibfk_1` FOREIGN KEY (`id_TaiKhoan`) REFERENCES `taikhoan` (`id_TaiKhoan`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table libracore.nhanvien: ~1 rows (approximately)
DELETE FROM `nhanvien` WHERE `id_NhanVien` <> 1;
INSERT IGNORE INTO `nhanvien` (`id_NhanVien`, `id_TaiKhoan`, `TenNhanVien`, `NgaySinh`, `DiaChi`, `SDT`, `Email`, `HoatDong`, `AnhNhanVien`) VALUES
	(1, 1, 'Nguyễn Văn Admin', '1990-01-01', 'TP. Hồ Chí Minh', '0901234567', 'admin@libracore.vn', 1, 'images/nhanvien/1_1773073636522.jpg'),
	(2, NULL, 'Nguyễn Hoàng Long', '1998-02-14', 'Quận 5, TP. Hồ Chí Minh', '0912000002', 'long.nv02@libracore.vn', 1, NULL),
	(3, NULL, 'Trần Thị Hạnh', '1997-07-21', 'Thủ Đức, TP. Hồ Chí Minh', '0912000003', 'hanh.nv03@libracore.vn', 1, NULL),
	(4, NULL, 'Lê Minh Quân', '1995-11-09', 'Biên Hòa, Đồng Nai', '0912000004', 'quan.nv04@libracore.vn', 1, NULL),
	(5, 5, 'Trần Văn Nhập', '1999-03-22', 'Đồng Nai', '0395544012', 'nhap@libracore.com', 1, 'images/nhanvien/5_1773073653217.jpg'),
	(6, NULL, 'Phạm Thu Trang', '1998-05-17', 'Dĩ An, Bình Dương', '0912000006', 'trang.nv06@libracore.vn', 1, NULL);

-- Dumping structure for table libracore.nxb
CREATE TABLE IF NOT EXISTS `nxb` (
  `id_NXB` int NOT NULL AUTO_INCREMENT,
  `TenNXB` varchar(255) DEFAULT NULL,
  `DiaChi` varchar(255) DEFAULT NULL,
  `SDT` varchar(20) DEFAULT NULL,
  `HoatDong` tinyint(1) NOT NULL,
  PRIMARY KEY (`id_NXB`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table libracore.nxb: ~0 rows (approximately)
DELETE FROM `nxb`;
INSERT INTO `nxb` (`id_NXB`, `TenNXB`, `DiaChi`, `SDT`, `HoatDong`) VALUES
	(4, 'Nhà xuất bản Kim Đồng', 'TP. Hồ Chí Minh', '0395455091', 1);

-- Dumping structure for table libracore.phieumuon
CREATE TABLE IF NOT EXISTS `phieumuon` (
  `id_PhieuMuon` int NOT NULL AUTO_INCREMENT,
  `id_NhanVien` int NOT NULL,
  `id_TheThanhVien` int DEFAULT NULL,
  `NgayMuon` date DEFAULT NULL,
  `NgayHenTra` date DEFAULT NULL,
  `TrangThai` enum('DangMuon','DaTra','QuaHen','DaHuy') DEFAULT 'DangMuon',
  `TongSoSachMuon` int NOT NULL,
  `LyDoHuy` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_PhieuMuon`),
  KEY `idx_phieumuon_the` (`id_TheThanhVien`),
  KEY `id_NhanVien` (`id_NhanVien`),
  CONSTRAINT `phieumuon_ibfk_1` FOREIGN KEY (`id_NhanVien`) REFERENCES `nhanvien` (`id_NhanVien`) ON UPDATE CASCADE,
  CONSTRAINT `phieumuon_ibfk_2` FOREIGN KEY (`id_TheThanhVien`) REFERENCES `thethanhvien` (`id_TheThanhVien`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table libracore.phieumuon: ~2 rows (approximately)
DELETE FROM `phieumuon`;
INSERT INTO `phieumuon` (`id_PhieuMuon`, `id_NhanVien`, `id_TheThanhVien`, `NgayMuon`, `NgayHenTra`, `TrangThai`, `TongSoSachMuon`, `LyDoHuy`) VALUES
	(1, 2, 1, '2026-10-01', '2026-10-15', 'QuaHen', 2, NULL),
	(2, 3, 2, '2026-10-02', '2026-10-16', 'QuaHen', 2, NULL),
	(3, 4, 3, '2026-10-03', '2026-10-17', 'QuaHen', 2, NULL),
	(4, 5, 4, '2026-10-04', '2026-10-18', 'QuaHen', 2, NULL),
	(5, 6, 5, '2026-10-05', '2026-10-19', 'QuaHen', 2, NULL),
	(6, 2, 6, '2026-11-10', '2026-11-24', 'DangMuon', 2, NULL),
	(7, 3, 7, '2026-11-11', '2026-11-25', 'DangMuon', 2, NULL),
	(8, 4, 8, '2026-11-12', '2026-11-26', 'DangMuon', 3, NULL),
	(9, 5, 9, '2026-11-13', '2026-11-27', 'DangMuon', 3, NULL),
	(10, 6, 10, '2026-08-20', '2026-09-03', 'DaTra', 1, NULL),
	(11, 2, 11, '2026-11-10', '2026-11-24', 'DangMuon', 1, NULL),
	(12, 3, 12, '2026-11-11', '2026-11-25', 'DangMuon', 1, NULL),
	(13, 4, 13, '2026-11-12', '2026-11-26', 'DangMuon', 1, NULL),
	(14, 5, 14, '2026-11-13', '2026-11-27', 'DangMuon', 1, NULL),
	(15, 6, 15, '2026-11-14', '2026-11-28', 'DangMuon', 1, NULL),
	(16, 2, 16, '2026-11-14', '2026-11-28', 'DangMuon', 1, NULL),
	(17, 3, 17, '2026-11-15', '2026-11-29', 'DangMuon', 1, NULL),
	(18, 4, 18, '2026-11-15', '2026-11-29', 'DangMuon', 1, NULL),
	(19, 5, 19, '2026-11-16', '2026-11-30', 'DangMuon', 1, NULL),
	(20, 6, 20, '2026-11-17', '2026-12-01', 'DangMuon', 1, NULL);

-- Dumping structure for table libracore.phieunhap
CREATE TABLE IF NOT EXISTS `phieunhap` (
  `id_PhieuNhap` int NOT NULL AUTO_INCREMENT,
  `id_NCC` int DEFAULT NULL,
  `NgayNhap` date DEFAULT NULL,
  `SoLuongSach` int DEFAULT NULL,
  `id_NhanVien` int NOT NULL,
  `TrangThai` enum('DaNhap','DaHuy','ChuaNhap') NOT NULL DEFAULT 'ChuaNhap',
  PRIMARY KEY (`id_PhieuNhap`),
  KEY `id_NCC` (`id_NCC`),
  KEY `id_NhanVien` (`id_NhanVien`),
  CONSTRAINT `phieunhap_ibfk_1` FOREIGN KEY (`id_NCC`) REFERENCES `ncc` (`id_NCC`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `phieunhap_ibfk_2` FOREIGN KEY (`id_NhanVien`) REFERENCES `nhanvien` (`id_NhanVien`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table libracore.phieunhap: ~1 rows (approximately)
DELETE FROM `phieunhap`;
INSERT INTO `phieunhap` (`id_PhieuNhap`, `id_NCC`, `NgayNhap`, `SoLuongSach`, `id_NhanVien`, `TrangThai`) VALUES
	(1, 7, '2026-08-01', 5, 2, 'DaNhap'),
	(2, 8, '2026-08-05', 5, 3, 'DaNhap'),
	(3, 7, '2026-08-10', 5, 4, 'DaNhap'),
	(4, 8, '2026-08-15', 5, 5, 'DaNhap'),
	(5, 7, '2026-08-20', 5, 6, 'DaNhap'),
	(6, 8, '2026-08-25', 5, 2, 'DaNhap'),
	(7, 7, '2026-09-01', 5, 3, 'DaNhap'),
	(8, 8, '2026-09-05', 5, 4, 'DaNhap'),
	(9, 7, '2026-09-10', 5, 5, 'DaNhap'),
	(10, 8, '2026-09-15', 5, 6, 'DaNhap');

-- Dumping structure for table libracore.phieuphat
CREATE TABLE IF NOT EXISTS `phieuphat` (
  `id_PhieuPhat` int NOT NULL AUTO_INCREMENT,
  `NgayLap` date DEFAULT NULL,
  `TienPhatPhaiNop` decimal(12,2) DEFAULT '0.00',
  `LyDoPhat` varchar(255) DEFAULT NULL,
  `TrangThai` enum('DaThu','ChuaThu','DaHuy') DEFAULT 'ChuaThu',
  `id_NhanVien` int NOT NULL,
  PRIMARY KEY (`id_PhieuPhat`),
  KEY `id_NhanVien` (`id_NhanVien`),
  CONSTRAINT `phieuphat_ibfk_1` FOREIGN KEY (`id_NhanVien`) REFERENCES `nhanvien` (`id_NhanVien`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table libracore.phieuphat: ~1 rows (approximately)
DELETE FROM `phieuphat`;
INSERT INTO `phieuphat` (`id_PhieuPhat`, `NgayLap`, `TienPhatPhaiNop`, `LyDoPhat`, `TrangThai`, `id_NhanVien`) VALUES
	(1, '2026-11-16', 30000.00, 'Trả sách hỏng - PM#6', 'DaThu', 2),
	(2, '2026-11-16', 30000.00, 'Trả sách hỏng - PM#7', 'DaThu', 3),
	(3, '2026-11-17', 30000.00, 'Trả sách hỏng - PM#8', 'DaThu', 4),
	(4, '2026-11-17', 30000.00, 'Trả sách hỏng - PM#9', 'DaThu', 5),
	(5, '2026-09-18', 75000.00, 'Trả sách trễ 15 ngày - PM#10', 'DaThu', 6);

-- Dumping structure for table libracore.quyen
CREATE TABLE IF NOT EXISTS `quyen` (
  `id_Quyen` int NOT NULL AUTO_INCREMENT,
  `TenQuyen` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_Quyen`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table libracore.quyen: ~0 rows (approximately)
-- Preserve existing permissions referenced by administrator roles.
INSERT IGNORE INTO `quyen` (`id_Quyen`, `TenQuyen`) VALUES
	(1, 'QL_SACH'),
	(2, 'QL_NHAPSACH'),
	(3, 'QL_DOCGIA_THE'),
	(4, 'QL_MUON_TRA'),
	(5, 'QL_PHIEU_PHAT'),
	(6, 'QL_NHANVIEN');

-- Dumping structure for table libracore.sach
CREATE TABLE IF NOT EXISTS `sach` (
  `id_Sach` int NOT NULL AUTO_INCREMENT,
  `id_NXB` int DEFAULT NULL,
  `NamXuatBan` year DEFAULT NULL,
  `TenSach` varchar(255) DEFAULT NULL,
  `MoTa` varchar(1000) DEFAULT NULL,
  `SoTrang` int DEFAULT NULL,
  `HoatDong` tinyint(1) NOT NULL,
  PRIMARY KEY (`id_Sach`),
  KEY `idx_sach_tensach` (`TenSach`),
  KEY `id_NXB` (`id_NXB`),
  CONSTRAINT `sach_ibfk_1` FOREIGN KEY (`id_NXB`) REFERENCES `nxb` (`id_NXB`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table libracore.sach: ~1 rows (approximately)
DELETE FROM `sach`;
INSERT INTO `sach` (`id_Sach`, `id_NXB`, `NamXuatBan`, `TenSach`, `MoTa`, `SoTrang`, `HoatDong`) VALUES
	(1, 4, '1999', 'Sherlock Holmes', 'Tuyển tập truyện trinh thám kinh điển.', 500, 1),
	(2, 4, '2018', 'Cho tôi xin một vé đi tuổi thơ', 'Tác phẩm dành cho thiếu nhi và người trưởng thành.', 208, 1),
	(3, 4, '1941', 'Dế Mèn phiêu lưu ký', 'Cuộc phiêu lưu của chú dế qua nhiều vùng đất.', 180, 1),
	(4, 4, '1957', 'Đất rừng phương Nam', 'Câu chuyện về thiên nhiên và con người Nam Bộ.', 320, 1),
	(5, 4, '2021', 'Hành trình khám phá vũ trụ', 'Kiến thức nhập môn về các hành tinh và vũ trụ.', 240, 1),
	(6, 4, '2022', 'Tư duy khoa học căn bản', 'Giới thiệu phương pháp quan sát và tư duy khoa học.', 216, 1),
	(7, 4, '2020', 'Lược sử Việt Nam', 'Tóm lược các giai đoạn lịch sử Việt Nam.', 350, 1),
	(8, 4, '2023', 'Những miền đất cổ', 'Khảo cứu về các nền văn minh cổ đại.', 288, 1),
	(9, 4, '2024', 'Lập trình Java thực hành', 'Bài tập thực hành lập trình hướng đối tượng Java.', 410, 1),
	(10, 4, '2024', 'Cơ sở dữ liệu ứng dụng', 'Thiết kế, truy vấn và quản trị cơ sở dữ liệu.', 390, 1);

-- Dumping structure for table libracore.sach_tacgia
CREATE TABLE IF NOT EXISTS `sach_tacgia` (
  `id_Sach` int NOT NULL,
  `id_TacGia` int NOT NULL,
  PRIMARY KEY (`id_Sach`,`id_TacGia`),
  KEY `id_TacGia` (`id_TacGia`),
  CONSTRAINT `sach_tacgia_ibfk_1` FOREIGN KEY (`id_Sach`) REFERENCES `sach` (`id_Sach`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sach_tacgia_ibfk_2` FOREIGN KEY (`id_TacGia`) REFERENCES `tacgia` (`id_TacGia`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table libracore.sach_tacgia: ~0 rows (approximately)
DELETE FROM `sach_tacgia`;
INSERT INTO `sach_tacgia` (`id_Sach`, `id_TacGia`) VALUES
	(1, 1),
	(2, 2),
	(3, 3),
	(4, 4),
	(5, 5),
	(6, 6),
	(7, 7),
	(8, 8),
	(9, 9),
	(10, 10);

-- Dumping structure for table libracore.sach_theloai
CREATE TABLE IF NOT EXISTS `sach_theloai` (
  `id_Sach` int NOT NULL,
  `id_TheLoai` int NOT NULL,
  PRIMARY KEY (`id_Sach`,`id_TheLoai`),
  KEY `id_TheLoai` (`id_TheLoai`),
  CONSTRAINT `sach_theloai_ibfk_1` FOREIGN KEY (`id_Sach`) REFERENCES `sach` (`id_Sach`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sach_theloai_ibfk_2` FOREIGN KEY (`id_TheLoai`) REFERENCES `theloai` (`id_TheLoai`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table libracore.sach_theloai: ~0 rows (approximately)
DELETE FROM `sach_theloai`;
INSERT INTO `sach_theloai` (`id_Sach`, `id_TheLoai`) VALUES
	(1, 4),
	(2, 5),
	(3, 5),
	(4, 5),
	(5, 6),
	(6, 6),
	(7, 7),
	(8, 7),
	(9, 8),
	(10, 8);

-- Dumping structure for table libracore.tacgia
CREATE TABLE IF NOT EXISTS `tacgia` (
  `id_TacGia` int NOT NULL AUTO_INCREMENT,
  `TenTacGia` varchar(255) DEFAULT NULL,
  `NgaySinh` date DEFAULT NULL,
  `NoiSinh` varchar(255) DEFAULT NULL,
  `SDT` varchar(20) DEFAULT NULL,
  `HoatDong` tinyint(1) NOT NULL,
  PRIMARY KEY (`id_TacGia`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table libracore.tacgia: ~0 rows (approximately)
DELETE FROM `tacgia`;
INSERT INTO `tacgia` (`id_TacGia`, `TenTacGia`, `NgaySinh`, `NoiSinh`, `SDT`, `HoatDong`) VALUES
	(1, 'Arthur Conan Doyle', '1859-05-22', 'Edinburgh, Scotland', NULL, 1),
	(2, 'Nguyễn Nhật Ánh', '1955-05-07', 'Quảng Nam, Việt Nam', NULL, 1),
	(3, 'Tô Hoài', '1920-09-27', 'Hà Nội, Việt Nam', NULL, 1),
	(4, 'Đoàn Giỏi', '1925-05-17', 'Tiền Giang, Việt Nam', NULL, 1),
	(5, 'Lê Minh Khoa', '1978-04-12', 'Việt Nam', NULL, 1),
	(6, 'Phạm Thu Hà', '1982-11-03', 'Việt Nam', NULL, 1),
	(7, 'Trần Quốc Việt', '1975-02-19', 'Việt Nam', NULL, 1),
	(8, 'Nguyễn An Bình', '1980-08-24', 'Việt Nam', NULL, 1),
	(9, 'Võ Minh Tân', '1985-06-30', 'Việt Nam', NULL, 1),
	(10, 'Lê Bảo Ngọc', '1987-01-16', 'Việt Nam', NULL, 1);

-- Dumping structure for table libracore.taikhoan
CREATE TABLE IF NOT EXISTS `taikhoan` (
  `id_TaiKhoan` int NOT NULL AUTO_INCREMENT,
  `id_VaiTro` int NOT NULL,
  `TaiKhoan` varchar(255) DEFAULT NULL,
  `MatKhau` varchar(255) DEFAULT NULL,
  `HoatDong` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_TaiKhoan`),
  UNIQUE KEY `TaiKhoan` (`TaiKhoan`),
  KEY `id_VaiTro` (`id_VaiTro`),
  CONSTRAINT `taikhoan_ibfk_1` FOREIGN KEY (`id_VaiTro`) REFERENCES `vaitro` (`id_VaiTro`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Keep existing accounts unchanged; INSERT IGNORE only supplies defaults when absent.
INSERT IGNORE INTO `taikhoan` (`id_TaiKhoan`, `id_VaiTro`, `TaiKhoan`, `MatKhau`, `HoatDong`) VALUES
	(1, 1, 'admin', 'admin123', 1),
	(5, 3, 'nhapsach', '123456', 1);

-- Dumping structure for table libracore.theloai
CREATE TABLE IF NOT EXISTS `theloai` (
  `id_TheLoai` int NOT NULL AUTO_INCREMENT,
  `TenTheLoai` varchar(255) DEFAULT NULL,
  `HoatDong` tinyint(1) NOT NULL,
  PRIMARY KEY (`id_TheLoai`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table libracore.theloai: ~0 rows (approximately)
DELETE FROM `theloai`;
INSERT INTO `theloai` (`id_TheLoai`, `TenTheLoai`, `HoatDong`) VALUES
	(4, 'Trinh thám', 1),
	(5, 'Thiếu nhi', 1),
	(6, 'Khoa học', 1),
	(7, 'Lịch sử', 1),
	(8, 'Công nghệ thông tin', 1);

-- Dumping structure for table libracore.thethanhvien
CREATE TABLE IF NOT EXISTS `thethanhvien` (
  `id_TheThanhVien` int NOT NULL AUTO_INCREMENT,
  `id_DocGia` int NOT NULL,
  `NgayCap` date DEFAULT NULL,
  `NgayHetHan` date DEFAULT NULL,
  `TrangThai` enum('HoatDong','BiKhoa','HetHan') DEFAULT 'HoatDong',
  PRIMARY KEY (`id_TheThanhVien`),
  KEY `id_DocGia` (`id_DocGia`),
  CONSTRAINT `thethanhvien_ibfk_1` FOREIGN KEY (`id_DocGia`) REFERENCES `docgia` (`id_DocGia`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table libracore.thethanhvien: ~0 rows (approximately)
DELETE FROM `thethanhvien`;
INSERT INTO `thethanhvien` (`id_TheThanhVien`, `id_DocGia`, `NgayCap`, `NgayHetHan`, `TrangThai`) VALUES
	(1, 1, '2026-01-05', '2028-01-05', 'HoatDong'),
	(2, 2, '2026-01-12', '2028-01-12', 'HoatDong'),
	(3, 3, '2026-02-03', '2028-02-03', 'HoatDong'),
	(4, 4, '2026-02-18', '2028-02-18', 'HoatDong'),
	(5, 5, '2026-03-09', '2028-03-09', 'HoatDong'),
	(6, 6, '2026-03-21', '2028-03-21', 'HoatDong'),
	(7, 7, '2026-04-02', '2028-04-02', 'HoatDong'),
	(8, 8, '2026-04-17', '2028-04-17', 'HoatDong'),
	(9, 9, '2026-05-08', '2028-05-08', 'HoatDong'),
	(10, 10, '2026-05-22', '2028-05-22', 'HoatDong'),
	(11, 11, '2026-06-04', '2028-06-04', 'HoatDong'),
	(12, 12, '2026-06-19', '2028-06-19', 'HoatDong'),
	(13, 13, '2026-07-03', '2028-07-03', 'HoatDong'),
	(14, 14, '2026-07-16', '2028-07-16', 'HoatDong'),
	(15, 15, '2026-08-01', '2028-08-01', 'HoatDong'),
	(16, 16, '2026-08-14', '2028-08-14', 'HoatDong'),
	(17, 17, '2026-09-02', '2028-09-02', 'HoatDong'),
	(18, 18, '2026-09-18', '2028-09-18', 'HoatDong'),
	(19, 19, '2026-10-05', '2028-10-05', 'HoatDong'),
	(20, 20, '2026-10-21', '2028-10-21', 'HoatDong');

-- Dumping structure for table libracore.vaitro
CREATE TABLE IF NOT EXISTS `vaitro` (
  `id_VaiTro` int NOT NULL AUTO_INCREMENT,
  `TenVaiTro` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_VaiTro`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Preserve existing roles referenced by the administrator account.
INSERT IGNORE INTO `vaitro` (`id_VaiTro`, `TenVaiTro`) VALUES
	(1, 'Admin'),
	(3, 'Nhập sách');

-- Dumping structure for table libracore.vaitro_quyen
CREATE TABLE IF NOT EXISTS `vaitro_quyen` (
  `id_VaiTro` int NOT NULL,
  `id_Quyen` int NOT NULL,
  PRIMARY KEY (`id_VaiTro`,`id_Quyen`),
  KEY `id_Quyen` (`id_Quyen`),
  CONSTRAINT `vaitro_quyen_ibfk_1` FOREIGN KEY (`id_VaiTro`) REFERENCES `vaitro` (`id_VaiTro`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `vaitro_quyen_ibfk_2` FOREIGN KEY (`id_Quyen`) REFERENCES `quyen` (`id_Quyen`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Preserve existing permissions and add any missing defaults.
INSERT IGNORE INTO `vaitro_quyen` (`id_VaiTro`, `id_Quyen`) VALUES
	(1, 1),
	(3, 1),
	(1, 2),
	(3, 2),
	(1, 3),
	(1, 4),
	(1, 5),
	(1, 6);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
