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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table libracore.chitietphieumuon: ~5 rows (approximately)
REPLACE INTO `chitietphieumuon` (`id_ChiTietPhieuMuon`, `id_PhieuMuon`, `id_CuonSach`, `NgayTra`, `TinhTrangTra`) VALUES
	(1, 1, 1, '2026-02-27', 'Hong'),
	(2, 1, 2, '2026-02-27', 'Hong'),
	(3, 1, 3, '2026-02-27', 'DaTra'),
	(4, 2, 3, NULL, 'ChuaTra'),
	(5, 2, 6, NULL, 'ChuaTra');

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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table libracore.chitietphieunhap: ~8 rows (approximately)
REPLACE INTO `chitietphieunhap` (`id_ChiTietPhieuNhap`, `id_PhieuNhap`, `id_Sach`, `SoLuong`, `GiaTien`) VALUES
	(1, 1, 7, 1, 50000.00),
	(2, 2, 6, 2, 200000.00),
	(3, 3, 6, 12, 100000.00),
	(4, 3, 2, 12, 120000.00),
	(5, 4, 6, 1, 100000.00),
	(6, 4, 7, 5, 10000.00),
	(7, 5, 3, 5, 100000.00),
	(8, 6, 8, 112, 100000.00);

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table libracore.chitietphieuphat: ~2 rows (approximately)
REPLACE INTO `chitietphieuphat` (`id_ChiTietPhieuPhat`, `id_PhieuPhat`, `id_ChiTietPhieuMuon`, `id_MucPhat`, `SoNgayTreHan`, `TienPhatTra`) VALUES
	(1, 1, 1, 2, 0, 10000.00),
	(2, 1, 2, 2, 0, 10000.00);

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
) ENGINE=InnoDB AUTO_INCREMENT=128 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table libracore.cuonsach: ~127 rows (approximately)
REPLACE INTO `cuonsach` (`id_CuonSach`, `id_Sach`, `TinhTrangSach`, `TrangThaiMuon`, `DaHuy`, `MaCuonSach`, `id_ChiTietPhieuNhap`) VALUES
	(1, 1, 'Hong', 'Ranh', 0, 'S1-0001', NULL),
	(2, 1, 'Hong', 'Ranh', 0, 'S1-0002', NULL),
	(3, 2, 'Tot', 'DangMuon', 0, 'S2-0001', NULL),
	(4, 3, 'Tot', 'Ranh', 0, 'S3-0001', NULL),
	(5, 6, 'Tot', 'Ranh', 0, 'S6-0001', NULL),
	(6, 7, 'Tot', 'DangMuon', 0, 'S7-0001', NULL),
	(7, 7, 'Tot', 'Ranh', 0, 'S7-0002', NULL),
	(8, 7, 'Tot', 'Ranh', 0, 'S7-0003', NULL),
	(9, 7, 'Tot', 'Ranh', 0, 'S7-0004', NULL),
	(10, 7, 'Tot', 'Ranh', 0, 'S7-0005', NULL),
	(11, 3, 'Tot', 'Ranh', 1, 'S3-0002', 7),
	(12, 3, 'Tot', 'Ranh', 1, 'S3-0003', 7),
	(13, 3, 'Tot', 'Ranh', 1, 'S3-0004', 7),
	(14, 3, 'Tot', 'Ranh', 1, 'S3-0005', 7),
	(15, 3, 'Tot', 'Ranh', 1, 'S3-0006', 7),
	(16, 8, 'Tot', 'Ranh', 0, 'S8-0001', 8),
	(17, 8, 'Tot', 'Ranh', 0, 'S8-0002', 8),
	(18, 8, 'Tot', 'Ranh', 0, 'S8-0003', 8),
	(19, 8, 'Tot', 'Ranh', 0, 'S8-0004', 8),
	(20, 8, 'Tot', 'Ranh', 0, 'S8-0005', 8),
	(21, 8, 'Tot', 'Ranh', 0, 'S8-0006', 8),
	(22, 8, 'Tot', 'Ranh', 0, 'S8-0007', 8),
	(23, 8, 'Tot', 'Ranh', 0, 'S8-0008', 8),
	(24, 8, 'Tot', 'Ranh', 0, 'S8-0009', 8),
	(25, 8, 'Tot', 'Ranh', 0, 'S8-0010', 8),
	(26, 8, 'Tot', 'Ranh', 0, 'S8-0011', 8),
	(27, 8, 'Tot', 'Ranh', 0, 'S8-0012', 8),
	(28, 8, 'Tot', 'Ranh', 0, 'S8-0013', 8),
	(29, 8, 'Tot', 'Ranh', 0, 'S8-0014', 8),
	(30, 8, 'Tot', 'Ranh', 0, 'S8-0015', 8),
	(31, 8, 'Tot', 'Ranh', 0, 'S8-0016', 8),
	(32, 8, 'Tot', 'Ranh', 0, 'S8-0017', 8),
	(33, 8, 'Tot', 'Ranh', 0, 'S8-0018', 8),
	(34, 8, 'Tot', 'Ranh', 0, 'S8-0019', 8),
	(35, 8, 'Tot', 'Ranh', 0, 'S8-0020', 8),
	(36, 8, 'Tot', 'Ranh', 0, 'S8-0021', 8),
	(37, 8, 'Tot', 'Ranh', 0, 'S8-0022', 8),
	(38, 8, 'Tot', 'Ranh', 0, 'S8-0023', 8),
	(39, 8, 'Tot', 'Ranh', 0, 'S8-0024', 8),
	(40, 8, 'Tot', 'Ranh', 0, 'S8-0025', 8),
	(41, 8, 'Tot', 'Ranh', 0, 'S8-0026', 8),
	(42, 8, 'Tot', 'Ranh', 0, 'S8-0027', 8),
	(43, 8, 'Tot', 'Ranh', 0, 'S8-0028', 8),
	(44, 8, 'Tot', 'Ranh', 0, 'S8-0029', 8),
	(45, 8, 'Tot', 'Ranh', 0, 'S8-0030', 8),
	(46, 8, 'Tot', 'Ranh', 0, 'S8-0031', 8),
	(47, 8, 'Tot', 'Ranh', 0, 'S8-0032', 8),
	(48, 8, 'Tot', 'Ranh', 0, 'S8-0033', 8),
	(49, 8, 'Tot', 'Ranh', 0, 'S8-0034', 8),
	(50, 8, 'Tot', 'Ranh', 0, 'S8-0035', 8),
	(51, 8, 'Tot', 'Ranh', 0, 'S8-0036', 8),
	(52, 8, 'Tot', 'Ranh', 0, 'S8-0037', 8),
	(53, 8, 'Tot', 'Ranh', 0, 'S8-0038', 8),
	(54, 8, 'Tot', 'Ranh', 0, 'S8-0039', 8),
	(55, 8, 'Tot', 'Ranh', 0, 'S8-0040', 8),
	(56, 8, 'Tot', 'Ranh', 0, 'S8-0041', 8),
	(57, 8, 'Tot', 'Ranh', 0, 'S8-0042', 8),
	(58, 8, 'Tot', 'Ranh', 0, 'S8-0043', 8),
	(59, 8, 'Tot', 'Ranh', 0, 'S8-0044', 8),
	(60, 8, 'Tot', 'Ranh', 0, 'S8-0045', 8),
	(61, 8, 'Tot', 'Ranh', 0, 'S8-0046', 8),
	(62, 8, 'Tot', 'Ranh', 0, 'S8-0047', 8),
	(63, 8, 'Tot', 'Ranh', 0, 'S8-0048', 8),
	(64, 8, 'Tot', 'Ranh', 0, 'S8-0049', 8),
	(65, 8, 'Tot', 'Ranh', 0, 'S8-0050', 8),
	(66, 8, 'Tot', 'Ranh', 0, 'S8-0051', 8),
	(67, 8, 'Tot', 'Ranh', 0, 'S8-0052', 8),
	(68, 8, 'Tot', 'Ranh', 0, 'S8-0053', 8),
	(69, 8, 'Tot', 'Ranh', 0, 'S8-0054', 8),
	(70, 8, 'Tot', 'Ranh', 0, 'S8-0055', 8),
	(71, 8, 'Tot', 'Ranh', 0, 'S8-0056', 8),
	(72, 8, 'Tot', 'Ranh', 0, 'S8-0057', 8),
	(73, 8, 'Tot', 'Ranh', 0, 'S8-0058', 8),
	(74, 8, 'Tot', 'Ranh', 0, 'S8-0059', 8),
	(75, 8, 'Tot', 'Ranh', 0, 'S8-0060', 8),
	(76, 8, 'Tot', 'Ranh', 0, 'S8-0061', 8),
	(77, 8, 'Tot', 'Ranh', 0, 'S8-0062', 8),
	(78, 8, 'Tot', 'Ranh', 0, 'S8-0063', 8),
	(79, 8, 'Tot', 'Ranh', 0, 'S8-0064', 8),
	(80, 8, 'Tot', 'Ranh', 0, 'S8-0065', 8),
	(81, 8, 'Tot', 'Ranh', 0, 'S8-0066', 8),
	(82, 8, 'Tot', 'Ranh', 0, 'S8-0067', 8),
	(83, 8, 'Tot', 'Ranh', 0, 'S8-0068', 8),
	(84, 8, 'Tot', 'Ranh', 0, 'S8-0069', 8),
	(85, 8, 'Tot', 'Ranh', 0, 'S8-0070', 8),
	(86, 8, 'Tot', 'Ranh', 0, 'S8-0071', 8),
	(87, 8, 'Tot', 'Ranh', 0, 'S8-0072', 8),
	(88, 8, 'Tot', 'Ranh', 0, 'S8-0073', 8),
	(89, 8, 'Tot', 'Ranh', 0, 'S8-0074', 8),
	(90, 8, 'Tot', 'Ranh', 0, 'S8-0075', 8),
	(91, 8, 'Tot', 'Ranh', 0, 'S8-0076', 8),
	(92, 8, 'Tot', 'Ranh', 0, 'S8-0077', 8),
	(93, 8, 'Tot', 'Ranh', 0, 'S8-0078', 8),
	(94, 8, 'Tot', 'Ranh', 0, 'S8-0079', 8),
	(95, 8, 'Tot', 'Ranh', 0, 'S8-0080', 8),
	(96, 8, 'Tot', 'Ranh', 0, 'S8-0081', 8),
	(97, 8, 'Tot', 'Ranh', 0, 'S8-0082', 8),
	(98, 8, 'Tot', 'Ranh', 0, 'S8-0083', 8),
	(99, 8, 'Tot', 'Ranh', 0, 'S8-0084', 8),
	(100, 8, 'Tot', 'Ranh', 0, 'S8-0085', 8),
	(101, 8, 'Tot', 'Ranh', 0, 'S8-0086', 8),
	(102, 8, 'Tot', 'Ranh', 0, 'S8-0087', 8),
	(103, 8, 'Tot', 'Ranh', 0, 'S8-0088', 8),
	(104, 8, 'Tot', 'Ranh', 0, 'S8-0089', 8),
	(105, 8, 'Tot', 'Ranh', 0, 'S8-0090', 8),
	(106, 8, 'Tot', 'Ranh', 0, 'S8-0091', 8),
	(107, 8, 'Tot', 'Ranh', 0, 'S8-0092', 8),
	(108, 8, 'Tot', 'Ranh', 0, 'S8-0093', 8),
	(109, 8, 'Tot', 'Ranh', 0, 'S8-0094', 8),
	(110, 8, 'Tot', 'Ranh', 0, 'S8-0095', 8),
	(111, 8, 'Tot', 'Ranh', 0, 'S8-0096', 8),
	(112, 8, 'Tot', 'Ranh', 0, 'S8-0097', 8),
	(113, 8, 'Tot', 'Ranh', 0, 'S8-0098', 8),
	(114, 8, 'Tot', 'Ranh', 0, 'S8-0099', 8),
	(115, 8, 'Tot', 'Ranh', 0, 'S8-0100', 8),
	(116, 8, 'Tot', 'Ranh', 0, 'S8-0101', 8),
	(117, 8, 'Tot', 'Ranh', 0, 'S8-0102', 8),
	(118, 8, 'Tot', 'Ranh', 0, 'S8-0103', 8),
	(119, 8, 'Tot', 'Ranh', 0, 'S8-0104', 8),
	(120, 8, 'Tot', 'Ranh', 0, 'S8-0105', 8),
	(121, 8, 'Tot', 'Ranh', 0, 'S8-0106', 8),
	(122, 8, 'Tot', 'Ranh', 0, 'S8-0107', 8),
	(123, 8, 'Tot', 'Ranh', 0, 'S8-0108', 8),
	(124, 8, 'Tot', 'Ranh', 0, 'S8-0109', 8),
	(125, 8, 'Tot', 'Ranh', 0, 'S8-0110', 8),
	(126, 8, 'Tot', 'Ranh', 0, 'S8-0111', 8),
	(127, 8, 'Tot', 'Ranh', 0, 'S8-0112', 8);

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table libracore.docgia: ~1 rows (approximately)
REPLACE INTO `docgia` (`id_DocGia`, `TenDocGia`, `DiaChi`, `NgaySinh`, `SDT`, `Email`, `HoatDong`) VALUES
	(1, 'Đoàn Phong Lưu', 'hehe', '2004-03-22', '0395455082', 'doanphongluu@gmail.com', 1);

-- Dumping structure for table libracore.mucphat
CREATE TABLE IF NOT EXISTS `mucphat` (
  `id_MucPhat` int NOT NULL AUTO_INCREMENT,
  `TenMucPhat` varchar(255) NOT NULL,
  `LoaiPhat` enum('PerDay','Fixed') NOT NULL DEFAULT 'PerDay',
  `SoTienPhat` decimal(12,2) NOT NULL,
  `MoTa` varchar(255) DEFAULT NULL,
  `HoatDong` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_MucPhat`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table libracore.mucphat: ~2 rows (approximately)
REPLACE INTO `mucphat` (`id_MucPhat`, `TenMucPhat`, `LoaiPhat`, `SoTienPhat`, `MoTa`, `HoatDong`) VALUES
	(1, 'Tre Han', 'PerDay', 0.00, '', 1),
	(2, 'Hong Sach VIP', 'Fixed', 10000.00, '', 1);

-- Dumping structure for table libracore.ncc
CREATE TABLE IF NOT EXISTS `ncc` (
  `id_NCC` int NOT NULL AUTO_INCREMENT,
  `TenNCC` varchar(255) DEFAULT NULL,
  `HoatDong` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_NCC`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table libracore.ncc: ~6 rows (approximately)
REPLACE INTO `ncc` (`id_NCC`, `TenNCC`, `HoatDong`) VALUES
	(1, 'Công ty Cổ phần Phát hành Sách TP.HCM - FAHASA', 1),
	(2, 'Công ty TNHH Văn hóa Phương Nam', 1),
	(3, 'Tổng công ty Sách Việt Nam - SAVINA', 1),
	(4, 'Nhà xuất bản Trẻ', 1),
	(5, 'Nhà xuất bản Kim Đồng', 1),
	(6, 'Fahasa', 1);

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table libracore.nhanvien: ~0 rows (approximately)
REPLACE INTO `nhanvien` (`id_NhanVien`, `id_TaiKhoan`, `TenNhanVien`, `NgaySinh`, `DiaChi`, `SDT`, `Email`, `HoatDong`, `AnhNhanVien`) VALUES
	(1, 1, 'Nguyễn Văn A', '1990-01-01', 'Hà Nội', '0123456789', 'admin@example.com', 1, NULL);

-- Dumping structure for table libracore.nxb
CREATE TABLE IF NOT EXISTS `nxb` (
  `id_NXB` int NOT NULL AUTO_INCREMENT,
  `TenNXB` varchar(255) DEFAULT NULL,
  `DiaChi` varchar(255) DEFAULT NULL,
  `SDT` varchar(20) DEFAULT NULL,
  `HoatDong` tinyint(1) NOT NULL,
  PRIMARY KEY (`id_NXB`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table libracore.nxb: ~3 rows (approximately)
REPLACE INTO `nxb` (`id_NXB`, `TenNXB`, `DiaChi`, `SDT`, `HoatDong`) VALUES
	(1, 'NXB Trẻ', 'TP.HCM', '0281234567', 0),
	(2, 'NXB Kim Đồng', 'Hà Nội 2', '0249876543', 1),
	(3, 'NXB Giáo Dục', 'Hà Nội', '0241112223', 1);

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table libracore.phieumuon: ~2 rows (approximately)
REPLACE INTO `phieumuon` (`id_PhieuMuon`, `id_NhanVien`, `id_TheThanhVien`, `NgayMuon`, `NgayHenTra`, `TrangThai`, `TongSoSachMuon`, `LyDoHuy`) VALUES
	(1, 1, NULL, '2026-02-27', '2026-03-13', 'DaTra', 3, NULL),
	(2, 1, 1, '2026-03-03', '2026-03-17', 'DangMuon', 2, NULL);


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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table libracore.phieunhap: ~6 rows (approximately)
REPLACE INTO `phieunhap` (`id_PhieuNhap`, `id_NCC`, `NgayNhap`, `SoLuongSach`, `id_NhanVien`, `TrangThai`) VALUES
	(1, 5, '2026-02-26', 1, 1, 'DaNhap'),
	(2, 2, '2026-02-26', 2, 1, 'DaHuy'),
	(3, 1, '2026-02-26', 24, 1, 'DaNhap'),
	(4, 1, '2026-02-26', 6, 1, 'DaNhap'),
	(5, 6, '2026-02-26', 5, 1, 'DaHuy'),
	(6, 1, '2026-03-03', 112, 1, 'DaNhap');

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table libracore.phieuphat: ~1 rows (approximately)
REPLACE INTO `phieuphat` (`id_PhieuPhat`, `NgayLap`, `TienPhatPhaiNop`, `LyDoPhat`, `TrangThai`, `id_NhanVien`) VALUES
	(1, '2026-02-27', 20000.00, 'Trả sách - PM#1', 'DaThu', 1);

-- Dumping structure for table libracore.quyen
CREATE TABLE IF NOT EXISTS `quyen` (
  `id_Quyen` int NOT NULL AUTO_INCREMENT,
  `TenQuyen` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_Quyen`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table libracore.quyen: ~0 rows (approximately)

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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table libracore.sach: ~6 rows (approximately)
REPLACE INTO `sach` (`id_Sach`, `id_NXB`, `NamXuatBan`, `TenSach`, `MoTa`, `SoTrang`, `HoatDong`) VALUES
	(1, 1, '2010', 'Cho tôi xin một vé đi tuổi thơ', 'Tiểu thuyết thiếu nhi', 208, 1),
	(2, 2, '2019', 'Clean Code', 'Sách lập trình', 464, 1),
	(3, 3, '2021', 'The Pragmatic Programmer', 'Tư duy lập trình', 352, 1),
	(4, 3, '2000', 'haha', 'hehe', 100, 1),
	(5, 3, '1910', 'Bruh', 'gegeg', 1000, 1),
	(6, 2, '2004', 'test', 'alo', 500, 0),
	(7, 2, '2000', 'Lần cuối', 'ga', 1000, 1),
	(8, 3, '1999', 'gà gà', 'ge', 2025, 1);

-- Dumping structure for table libracore.sach_tacgia
CREATE TABLE IF NOT EXISTS `sach_tacgia` (
  `id_Sach` int NOT NULL,
  `id_TacGia` int NOT NULL,
  PRIMARY KEY (`id_Sach`,`id_TacGia`),
  KEY `id_TacGia` (`id_TacGia`),
  CONSTRAINT `sach_tacgia_ibfk_1` FOREIGN KEY (`id_Sach`) REFERENCES `sach` (`id_Sach`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sach_tacgia_ibfk_2` FOREIGN KEY (`id_TacGia`) REFERENCES `tacgia` (`id_TacGia`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table libracore.sach_tacgia: ~9 rows (approximately)
REPLACE INTO `sach_tacgia` (`id_Sach`, `id_TacGia`) VALUES
	(1, 1),
	(4, 1),
	(5, 1),
	(6, 1),
	(7, 1),
	(2, 2),
	(1, 3),
	(3, 3),
	(7, 3),
	(8, 4);

-- Dumping structure for table libracore.sach_theloai
CREATE TABLE IF NOT EXISTS `sach_theloai` (
  `id_Sach` int NOT NULL,
  `id_TheLoai` int NOT NULL,
  PRIMARY KEY (`id_Sach`,`id_TheLoai`),
  KEY `id_TheLoai` (`id_TheLoai`),
  CONSTRAINT `sach_theloai_ibfk_1` FOREIGN KEY (`id_Sach`) REFERENCES `sach` (`id_Sach`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sach_theloai_ibfk_2` FOREIGN KEY (`id_TheLoai`) REFERENCES `theloai` (`id_TheLoai`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table libracore.sach_theloai: ~10 rows (approximately)
REPLACE INTO `sach_theloai` (`id_Sach`, `id_TheLoai`) VALUES
	(5, 1),
	(6, 1),
	(7, 1),
	(8, 1),
	(1, 2),
	(1, 3),
	(2, 3),
	(3, 3),
	(4, 3),
	(7, 3);

-- Dumping structure for table libracore.tacgia
CREATE TABLE IF NOT EXISTS `tacgia` (
  `id_TacGia` int NOT NULL AUTO_INCREMENT,
  `TenTacGia` varchar(255) DEFAULT NULL,
  `NgaySinh` date DEFAULT NULL,
  `NoiSinh` varchar(255) DEFAULT NULL,
  `SDT` varchar(20) DEFAULT NULL,
  `HoatDong` tinyint(1) NOT NULL,
  PRIMARY KEY (`id_TacGia`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table libracore.tacgia: ~4 rows (approximately)
REPLACE INTO `tacgia` (`id_TacGia`, `TenTacGia`, `NgaySinh`, `NoiSinh`, `SDT`, `HoatDong`) VALUES
	(1, 'Nguyễn Nhật Ánh', '1955-05-07', 'Quảng Nam', NULL, 1),
	(2, 'Robert C. Martin', '1952-12-05', 'USA', NULL, 1),
	(3, 'Andrew Hunt', '1964-01-01', 'USA', NULL, 1),
	(4, 'Trần Văn Quang', '1999-01-01', 'Đồng Nai', '0395450925', 1);

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table libracore.taikhoan: ~1 rows (approximately)
REPLACE INTO `taikhoan` (`id_TaiKhoan`, `id_VaiTro`, `TaiKhoan`, `MatKhau`, `HoatDong`) VALUES
	(1, 1, 'admin', 'admin123', 1);

-- Dumping structure for table libracore.theloai
CREATE TABLE IF NOT EXISTS `theloai` (
  `id_TheLoai` int NOT NULL AUTO_INCREMENT,
  `TenTheLoai` varchar(255) DEFAULT NULL,
  `HoatDong` tinyint(1) NOT NULL,
  PRIMARY KEY (`id_TheLoai`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table libracore.theloai: ~3 rows (approximately)
REPLACE INTO `theloai` (`id_TheLoai`, `TenTheLoai`, `HoatDong`) VALUES
	(1, 'Khoa học', 1),
	(2, 'Tiểu thuyết', 1),
	(3, 'Lập trình', 1);

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table libracore.thethanhvien: ~1 rows (approximately)
REPLACE INTO `thethanhvien` (`id_TheThanhVien`, `id_DocGia`, `NgayCap`, `NgayHetHan`, `TrangThai`) VALUES
	(1, 1, '2026-03-03', '2027-03-03', 'HoatDong');

-- Dumping structure for table libracore.vaitro
CREATE TABLE IF NOT EXISTS `vaitro` (
  `id_VaiTro` int NOT NULL AUTO_INCREMENT,
  `TenVaiTro` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_VaiTro`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table libracore.vaitro: ~0 rows (approximately)
REPLACE INTO `vaitro` (`id_VaiTro`, `TenVaiTro`) VALUES
	(1, 'Admin');

-- Dumping structure for table libracore.vaitro_quyen
CREATE TABLE IF NOT EXISTS `vaitro_quyen` (
  `id_VaiTro` int NOT NULL,
  `id_Quyen` int NOT NULL,
  PRIMARY KEY (`id_VaiTro`,`id_Quyen`),
  KEY `id_Quyen` (`id_Quyen`),
  CONSTRAINT `vaitro_quyen_ibfk_1` FOREIGN KEY (`id_VaiTro`) REFERENCES `vaitro` (`id_VaiTro`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `vaitro_quyen_ibfk_2` FOREIGN KEY (`id_Quyen`) REFERENCES `quyen` (`id_Quyen`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table libracore.vaitro_quyen: ~0 rows (approximately)

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
