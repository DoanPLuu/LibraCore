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

public class CuonSachDAO {

    public List<CuonSach> getAll() {
        String sql =
                "SELECT c.id_CuonSach, c.id_Sach, c.MaCuonSach, c.TinhTrangSach, c.TrangThaiMuon, c.DaHuy, s.TenSach " +
                "FROM CuonSach c " +
                "JOIN Sach s ON s.id_Sach = c.id_Sach " +
                "ORDER BY c.id_CuonSach DESC";
        return queryList(sql, false, null);
    }

    public List<CuonSach> search(String keyword) {
        String sql =
                "SELECT c.id_CuonSach, c.id_Sach, c.MaCuonSach, c.TinhTrangSach, c.TrangThaiMuon, c.DaHuy, s.TenSach " +
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

        Sach s = new Sach();
        s.setIdSach(c.getIdSach());
        s.setTenSach(rs.getString("TenSach"));
        c.setSach(s);
        return c;
    }
}
