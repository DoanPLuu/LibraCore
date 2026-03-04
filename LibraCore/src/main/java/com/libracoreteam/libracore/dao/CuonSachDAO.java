package com.libracoreteam.libracore.dao;

import com.libracoreteam.libracore.model.CuonSach;
import com.libracoreteam.libracore.model.Sach;
import com.libracoreteam.libracore.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CuonSachDAO{

    
    public List<CuonSach> getAll() {
        String sql =
                "SELECT c.id_CuonSach, c.id_Sach, c.MaCuonSach, c.TinhTrangSach, c.TrangThaiMuon, c.DaHuy, c.id_ChiTietPhieuNhap, s.TenSach " +
                "FROM CuonSach c " +
                "JOIN Sach s ON s.id_Sach = c.id_Sach " +
                "ORDER BY c.id_CuonSach DESC";
        return queryList(sql, false, null);
    }

    public List<CuonSach> search(String keyword) {
        String sql =
                "SELECT c.id_CuonSach, c.id_Sach, c.MaCuonSach, c.TinhTrangSach, c.TrangThaiMuon, c.DaHuy, c.id_ChiTietPhieuNhap, s.TenSach " +
                "FROM CuonSach c " +
                "JOIN Sach s ON s.id_Sach = c.id_Sach " +
                "WHERE c.MaCuonSach LIKE ? OR CAST(c.id_CuonSach AS CHAR) LIKE ? OR CAST(c.id_Sach AS CHAR) LIKE ? OR s.TenSach LIKE ? " +
                "ORDER BY c.id_CuonSach DESC";

        String k = "%" + (keyword == null ? "" : keyword.trim()) + "%";
        return queryList(sql, true, k);
    }

    public boolean softDelete(int idCuonSach) {
        String sql = "UPDATE CuonSach SET DaHuy = ? WHERE id_CuonSach = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, true);
            ps.setInt(2, idCuonSach);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("CuonSachDAO.softDelete failed", e);
        }
    }

    
    public boolean updateStatus(int idCuonSach, String trangThaiMoi) {
        String sql = "UPDATE CuonSach SET TrangThaiMuon = ? WHERE id_CuonSach = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, trangThaiMoi);
            stmt.setInt(2, idCuonSach);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Kiểm tra tính khả dụng của cuốn sách (Đang rảnh và tình trạng tốt)
    public boolean isAvailable(int idCuonSach) {
        String sql = "SELECT COUNT(*) FROM CuonSach WHERE id_CuonSach = ? AND TrangThaiMuon = 'Ranh' AND TinhTrangSach = 'Tot'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCuonSach);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {}
        return false;
    }

    public String getTenSachById(int idCuonSach) {
        String sql = "SELECT s.TenSach FROM CuonSach cs JOIN Sach s ON cs.id_Sach = s.id_Sach WHERE cs.id_CuonSach = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCuonSach);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getString("TenSach");
        } catch (Exception e) {}
        return null;
    }

    private List<CuonSach> queryList(String sql, boolean hasKeyword, String keyword) {
        List<CuonSach> list = new ArrayList<CuonSach>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (hasKeyword) {
                ps.setString(1, keyword);
                ps.setString(2, keyword);
                ps.setString(3, keyword);
                ps.setString(4, keyword);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("CuonSachDAO.queryList failed", e);
        }
    }

    private CuonSach map(ResultSet rs) throws SQLException {
        CuonSach c = new CuonSach();
        c.setIdCuonSach(rs.getInt("id_CuonSach"));
        c.setIdSach(rs.getInt("id_Sach"));
        c.setMaCuonSach(rs.getString("MaCuonSach"));
        c.setTinhTrangSach(rs.getString("TinhTrangSach"));
        c.setTrangThaiMuon(rs.getString("TrangThaiMuon"));

        boolean daHuy = rs.getBoolean("DaHuy");
        c.setDaHuy(rs.wasNull() ? false : daHuy);
        
        // --- BỔ SUNG MAPPING CHO ChiTietPhieuNhap ---
        int idCTPN = rs.getInt("id_ChiTietPhieuNhap");
        if (!rs.wasNull()) {
            c.setIdChiTietPhieuNhap(idCTPN);
        }

        Sach s = new Sach();
        s.setIdSach(c.getIdSach());
        s.setTenSach(rs.getString("TenSach"));
        c.setSach(s);
        return c;
    }
}