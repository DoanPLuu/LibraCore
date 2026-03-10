package com.libracoreteam.libracore.dao;

import com.libracoreteam.libracore.util.DBConnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ThongKeDAO {

    public int getTongSoSach() {
        return executeCountQuery("SELECT COUNT(*) FROM Sach WHERE HoatDong = 1");
    }

    public int getTongDocGia() {
        return executeCountQuery("SELECT COUNT(*) FROM DocGia WHERE HoatDong = 1");
    }

    public int getSoPhieuDangMuon() {
        return executeCountQuery("SELECT COUNT(*) FROM PhieuMuon WHERE TrangThai = 'DangMuon'");
    }

    public double getTongDoanhThuPhat() {
        String sql = "SELECT SUM(TienPhatPhaiNop) FROM PhieuPhat WHERE TrangThai = 'DaThu'";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            if (rs.next())
                return rs.getDouble(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }


 
    public List<Object[]> getSachNhapKho(LocalDate from, LocalDate to) {
        String sql = "SELECT s.TenSach, " +
                "  COALESCE(GROUP_CONCAT(DISTINCT tl.TenTheLoai ORDER BY tl.TenTheLoai SEPARATOR ', '), '') AS TheLoai, "
                +
                "  ct.SoLuong, " +
                "  pn.NgayNhap " +
                "FROM ChiTietPhieuNhap ct " +
                "JOIN Sach s ON s.id_Sach = ct.id_Sach " +
                "JOIN PhieuNhap pn ON pn.id_PhieuNhap = ct.id_PhieuNhap " +
                "LEFT JOIN Sach_TheLoai stl ON stl.id_Sach = s.id_Sach " +
                "LEFT JOIN TheLoai tl ON tl.id_TheLoai = stl.id_TheLoai " +
                "WHERE pn.NgayNhap BETWEEN ? AND ? " +
                "  AND pn.TrangThai <> 'DaHuy' " +
                "GROUP BY ct.id_ChiTietPhieuNhap, s.TenSach, ct.SoLuong, pn.NgayNhap " +
                "ORDER BY pn.NgayNhap DESC, s.TenSach";
        return runQuery(sql, from, to);
    }

   
    public List<Object[]> getSachDangMuon(LocalDate from, LocalDate to) {
        String sql = "SELECT s.TenSach, " +
                "  COALESCE(GROUP_CONCAT(DISTINCT tl.TenTheLoai ORDER BY tl.TenTheLoai SEPARATOR ', '), '') AS TheLoai, "
                +
                "  COUNT(DISTINCT ct.id_ChiTietPhieuMuon) AS SoLuong, " +
                "  pm.NgayMuon " +
                "FROM ChiTietPhieuMuon ct " +
                "JOIN PhieuMuon pm ON pm.id_PhieuMuon = ct.id_PhieuMuon " +
                "JOIN CuonSach cs ON cs.id_CuonSach = ct.id_CuonSach " +
                "JOIN Sach s ON s.id_Sach = cs.id_Sach " +
                "LEFT JOIN Sach_TheLoai stl ON stl.id_Sach = s.id_Sach " +
                "LEFT JOIN TheLoai tl ON tl.id_TheLoai = stl.id_TheLoai " +
                "WHERE pm.NgayMuon BETWEEN ? AND ? " +
                "  AND ct.TinhTrangTra = 'ChuaTra' " +
                "GROUP BY s.id_Sach, s.TenSach, pm.NgayMuon " +
                "ORDER BY pm.NgayMuon DESC, s.TenSach";
        return runQuery(sql, from, to);
    }

   
    public List<Object[]> getSachHongHoacMat(LocalDate from, LocalDate to) {
        String sql = "SELECT s.TenSach, " +
                "  COALESCE(GROUP_CONCAT(DISTINCT tl.TenTheLoai ORDER BY tl.TenTheLoai SEPARATOR ', '), '') AS TheLoai, "
                +
                "  COUNT(DISTINCT cs.id_CuonSach) AS SoLuong, " +
                "  MAX(pn.NgayNhap) AS NgayNhap " +
                "FROM CuonSach cs " +
                "JOIN Sach s ON s.id_Sach = cs.id_Sach " +
                "LEFT JOIN ChiTietPhieuNhap ct ON ct.id_ChiTietPhieuNhap = cs.id_ChiTietPhieuNhap " +
                "LEFT JOIN PhieuNhap pn ON pn.id_PhieuNhap = ct.id_PhieuNhap " +
                "LEFT JOIN Sach_TheLoai stl ON stl.id_Sach = s.id_Sach " +
                "LEFT JOIN TheLoai tl ON tl.id_TheLoai = stl.id_TheLoai " +
                "WHERE cs.TinhTrangSach IN ('Hong', 'Mat') " +
                "  AND COALESCE(cs.DaHuy, 0) = 0 " +
                "  AND (pn.NgayNhap IS NULL OR pn.NgayNhap BETWEEN ? AND ?) " +
                "GROUP BY s.id_Sach, s.TenSach " +
                "ORDER BY SoLuong DESC, s.TenSach";
        return runQuery(sql, from, to);
    }

   
    public List<Object[]> getTongSoSachTheoSach(LocalDate from, LocalDate to) {
        String sql = "SELECT s.TenSach, " +
                "  COALESCE(GROUP_CONCAT(DISTINCT tl.TenTheLoai ORDER BY tl.TenTheLoai SEPARATOR ', '), '') AS TheLoai, "
                +
                "  COUNT(DISTINCT cs.id_CuonSach) AS SoLuong, " +
                "  MAX(pn.NgayNhap) AS NgayNhap " +
                "FROM CuonSach cs " +
                "JOIN Sach s ON s.id_Sach = cs.id_Sach " +
                "LEFT JOIN ChiTietPhieuNhap ct ON ct.id_ChiTietPhieuNhap = cs.id_ChiTietPhieuNhap " +
                "LEFT JOIN PhieuNhap pn ON pn.id_PhieuNhap = ct.id_PhieuNhap " +
                "LEFT JOIN Sach_TheLoai stl ON stl.id_Sach = s.id_Sach " +
                "LEFT JOIN TheLoai tl ON tl.id_TheLoai = stl.id_TheLoai " +
                "WHERE COALESCE(cs.DaHuy, 0) = 0 " +
                "  AND (pn.NgayNhap IS NULL OR pn.NgayNhap BETWEEN ? AND ?) " +
                "GROUP BY s.id_Sach, s.TenSach " +
                "ORDER BY SoLuong DESC, s.TenSach";
        return runQuery(sql, from, to);
    }

    private List<Object[]> runQuery(String sql, LocalDate from, LocalDate to) {
        List<Object[]> rows = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(from));
            ps.setDate(2, Date.valueOf(to));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rows.add(new Object[] {
                            rs.getString(1), 
                            rs.getString(2), 
                            rs.getInt(3), 
                            rs.getDate(4) 
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
    }

    private int executeCountQuery(String sql) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            if (rs.next())
                return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}