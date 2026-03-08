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

public class ThongKeMuonTraDAO {

    public List<Object[]> getThongKeMuonTra(LocalDate from, LocalDate to) {
        
        String sql = "SELECT " +
                     "    pm.id_PhieuMuon, " +
                     "    COALESCE(dg.TenDocGia, 'Khách lẻ') AS TenDocGia, " + 
                     "    pm.NgayMuon, " +
                     "    (SELECT MAX(NgayTra) FROM chitietphieumuon WHERE id_PhieuMuon = pm.id_PhieuMuon) AS NgayTra, " +
                     "    pm.TrangThai, " +
                     "    pm.TongSoSachMuon " + 
                     "FROM phieumuon pm " +
                     "LEFT JOIN thethanhvien ttv ON pm.id_TheThanhVien = ttv.id_TheThanhVien " +
                     "LEFT JOIN docgia dg ON ttv.id_DocGia = dg.id_DocGia " +
                     "WHERE " +
                     "   (pm.TrangThai IN ('DangMuon', 'QuaHen') AND pm.NgayMuon BETWEEN ? AND ?) " +
                     "   OR " +
                     "   (pm.TrangThai = 'DaTra' AND (SELECT MAX(NgayTra) FROM chitietphieumuon WHERE id_PhieuMuon = pm.id_PhieuMuon) BETWEEN ? AND ?) " +
                     "ORDER BY pm.NgayMuon ASC";
        
        List<Object[]> rows = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setDate(1, Date.valueOf(from));
            ps.setDate(2, Date.valueOf(to));
            ps.setDate(3, Date.valueOf(from));
            ps.setDate(4, Date.valueOf(to));
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rows.add(new Object[]{
                        rs.getInt("id_PhieuMuon"),
                        rs.getString("TenDocGia"),
                        rs.getDate("NgayMuon"),
                        rs.getDate("NgayTra"),
                        rs.getString("TrangThai"),
                        rs.getInt("TongSoSachMuon")
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
    }
}