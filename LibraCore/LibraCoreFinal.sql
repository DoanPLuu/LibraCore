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

-- Data exporting was unselected.

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

-- Data exporting was unselected.

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

-- Data exporting was unselected.

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

-- Data exporting was unselected.

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

-- Data exporting was unselected.

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

-- Data exporting was unselected.

-- Dumping structure for table libracore.ncc
CREATE TABLE IF NOT EXISTS `ncc` (
  `id_NCC` int NOT NULL AUTO_INCREMENT,
  `TenNCC` varchar(255) DEFAULT NULL,
  `HoatDong` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_NCC`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table libracore.nxb
CREATE TABLE IF NOT EXISTS `nxb` (
  `id_NXB` int NOT NULL AUTO_INCREMENT,
  `TenNXB` varchar(255) DEFAULT NULL,
  `DiaChi` varchar(255) DEFAULT NULL,
  `SDT` varchar(20) DEFAULT NULL,
  `HoatDong` tinyint(1) NOT NULL,
  PRIMARY KEY (`id_NXB`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

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

-- Data exporting was unselected.

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

-- Data exporting was unselected.

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

-- Data exporting was unselected.

-- Dumping structure for table libracore.quyen
CREATE TABLE IF NOT EXISTS `quyen` (
  `id_Quyen` int NOT NULL AUTO_INCREMENT,
  `TenQuyen` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_Quyen`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

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

-- Data exporting was unselected.

-- Dumping structure for table libracore.sach_tacgia
CREATE TABLE IF NOT EXISTS `sach_tacgia` (
  `id_Sach` int NOT NULL,
  `id_TacGia` int NOT NULL,
  PRIMARY KEY (`id_Sach`,`id_TacGia`),
  KEY `id_TacGia` (`id_TacGia`),
  CONSTRAINT `sach_tacgia_ibfk_1` FOREIGN KEY (`id_Sach`) REFERENCES `sach` (`id_Sach`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sach_tacgia_ibfk_2` FOREIGN KEY (`id_TacGia`) REFERENCES `tacgia` (`id_TacGia`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table libracore.sach_theloai
CREATE TABLE IF NOT EXISTS `sach_theloai` (
  `id_Sach` int NOT NULL,
  `id_TheLoai` int NOT NULL,
  PRIMARY KEY (`id_Sach`,`id_TheLoai`),
  KEY `id_TheLoai` (`id_TheLoai`),
  CONSTRAINT `sach_theloai_ibfk_1` FOREIGN KEY (`id_Sach`) REFERENCES `sach` (`id_Sach`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sach_theloai_ibfk_2` FOREIGN KEY (`id_TheLoai`) REFERENCES `theloai` (`id_TheLoai`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

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

-- Data exporting was unselected.

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table libracore.theloai
CREATE TABLE IF NOT EXISTS `theloai` (
  `id_TheLoai` int NOT NULL AUTO_INCREMENT,
  `TenTheLoai` varchar(255) DEFAULT NULL,
  `HoatDong` tinyint(1) NOT NULL,
  PRIMARY KEY (`id_TheLoai`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

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

-- Data exporting was unselected.

-- Dumping structure for table libracore.vaitro
CREATE TABLE IF NOT EXISTS `vaitro` (
  `id_VaiTro` int NOT NULL AUTO_INCREMENT,
  `TenVaiTro` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_VaiTro`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table libracore.vaitro_quyen
CREATE TABLE IF NOT EXISTS `vaitro_quyen` (
  `id_VaiTro` int NOT NULL,
  `id_Quyen` int NOT NULL,
  PRIMARY KEY (`id_VaiTro`,`id_Quyen`),
  KEY `id_Quyen` (`id_Quyen`),
  CONSTRAINT `vaitro_quyen_ibfk_1` FOREIGN KEY (`id_VaiTro`) REFERENCES `vaitro` (`id_VaiTro`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `vaitro_quyen_ibfk_2` FOREIGN KEY (`id_Quyen`) REFERENCES `quyen` (`id_Quyen`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
