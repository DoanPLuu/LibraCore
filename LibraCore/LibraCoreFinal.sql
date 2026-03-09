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
	(1, 1, 128, '2026-03-09', 'DaTra'),
	(2, 1, 129, '2026-03-09', 'DaTra'),
	(3, 2, 128, '2026-03-09', 'Hong'),
	(4, 2, 129, '2026-03-09', 'Hong'),
	(5, 3, 130, '2026-03-09', 'DaTra'),
	(6, 3, 131, '2026-03-09', 'DaTra'),
	(7, 4, 133, '2026-03-09', 'Hong'),
	(8, 4, 134, '2026-03-09', 'Hong');

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
	(9, 9, 1, 5, 500000.00),
	(10, 10, 10, 10, 100000.00);

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
	(1, 1, 3, 4, 0, 30000.00),
	(2, 1, 4, 4, 0, 30000.00),
	(3, 2, 7, 4, 0, 30000.00),
	(4, 3, 8, 4, 0, 30000.00);

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
	(128, 1, 'Hong', 'Ranh', 0, 'S1-0001', 9),
	(129, 1, 'Hong', 'Ranh', 0, 'S1-0002', 9),
	(130, 1, 'Tot', 'Ranh', 0, 'S1-0003', 9),
	(131, 1, 'Tot', 'Ranh', 0, 'S1-0004', 9),
	(132, 1, 'Tot', 'Ranh', 0, 'S1-0005', 9),
	(133, 10, 'Hong', 'Ranh', 0, 'S10-0001', 10),
	(134, 10, 'Hong', 'Ranh', 0, 'S10-0002', 10),
	(135, 10, 'Tot', 'Ranh', 0, 'S10-0003', 10),
	(136, 10, 'Tot', 'Ranh', 0, 'S10-0004', 10),
	(137, 10, 'Tot', 'Ranh', 0, 'S10-0005', 10),
	(138, 10, 'Tot', 'Ranh', 0, 'S10-0006', 10),
	(139, 10, 'Tot', 'Ranh', 0, 'S10-0007', 10),
	(140, 10, 'Tot', 'Ranh', 0, 'S10-0008', 10),
	(141, 10, 'Tot', 'Ranh', 0, 'S10-0009', 10),
	(142, 10, 'Tot', 'Ranh', 0, 'S10-0010', 10);

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
	(2, 'Đoàn Phong Lưu', 'Đồng Nai', '2004-03-22', '0395455082', 'doanphongluu082@gmail.com', 1);

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
DELETE FROM `nhanvien`;
INSERT INTO `nhanvien` (`id_NhanVien`, `id_TaiKhoan`, `TenNhanVien`, `NgaySinh`, `DiaChi`, `SDT`, `Email`, `HoatDong`, `AnhNhanVien`) VALUES
	(1, 1, 'Nguyễn Văn Admin', '1990-01-01', 'TP. Hồ Chí Minh', '0901234567', 'admin@libracore.vn', 1, 'images/nhanvien/1_1773073636522.jpg'),
	(5, 5, 'Trần Văn Nhập', '1999-03-22', 'Đồng Nai', '0395544012', 'nhap@libracore.com', 1, 'images/nhanvien/5_1773073653217.jpg');

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
	(1, 1, 2, '2026-03-09', '2026-03-23', 'DaTra', 2, NULL),
	(2, 1, 2, '2026-03-09', '2026-03-23', 'DaTra', 2, NULL),
	(3, 1, 2, '2026-03-09', '2026-03-23', 'DaTra', 2, NULL),
	(4, 1, 2, '2026-03-09', '2026-03-23', 'DaTra', 2, NULL);

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
	(9, 7, '2026-03-09', 5, 1, 'DaNhap'),
	(10, 7, '2026-03-09', 10, 1, 'DaNhap');

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
	(1, '2026-03-09', 60000.00, 'Trả sách - PM#2', 'DaThu', 1),
	(2, '2026-03-09', 30000.00, 'Trả sách - PM#4', 'DaThu', 1),
	(3, '2026-03-09', 30000.00, 'Trả sách - PM#4', 'DaThu', 1);

-- Dumping structure for table libracore.quyen
CREATE TABLE IF NOT EXISTS `quyen` (
  `id_Quyen` int NOT NULL AUTO_INCREMENT,
  `TenQuyen` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_Quyen`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table libracore.quyen: ~0 rows (approximately)
DELETE FROM `quyen`;
INSERT INTO `quyen` (`id_Quyen`, `TenQuyen`) VALUES
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
	(1, 4, '1999', 'Sherlock Holmes', 'Bộ sách trinh thám kinh điển', 500, 1),
	(10, 4, '2000', 'Cho tôi xin 1 vé đi tuổi thơ', 'Sách về thiếu nhi của NNA', 150, 1);

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
	(10, 5),
	(1, 6);

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
	(10, 5),
	(1, 6);

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
	(5, 'Nguyễn Nhật Ánh', '1999-01-01', 'Việt Nam', '0123456789', 1),
	(6, 'Arthur Conan Doyle', '1859-05-22', 'Anh Quốc', '0111111111', 1),
	(7, 'Robert Martin', '1986-01-01', 'USA', '0111111112', 1);

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

-- Dumping data for table libracore.taikhoan: ~1 rows (approximately)
DELETE FROM `taikhoan`;
INSERT INTO `taikhoan` (`id_TaiKhoan`, `id_VaiTro`, `TaiKhoan`, `MatKhau`, `HoatDong`) VALUES
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
	(6, 'Khoa học', 1);

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
	(2, 2, '2026-03-09', '2030-03-09', 'HoatDong');

-- Dumping structure for table libracore.vaitro
CREATE TABLE IF NOT EXISTS `vaitro` (
  `id_VaiTro` int NOT NULL AUTO_INCREMENT,
  `TenVaiTro` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_VaiTro`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table libracore.vaitro: ~1 rows (approximately)
DELETE FROM `vaitro`;
INSERT INTO `vaitro` (`id_VaiTro`, `TenVaiTro`) VALUES
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

-- Dumping data for table libracore.vaitro_quyen: ~6 rows (approximately)
DELETE FROM `vaitro_quyen`;
INSERT INTO `vaitro_quyen` (`id_VaiTro`, `id_Quyen`) VALUES
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
