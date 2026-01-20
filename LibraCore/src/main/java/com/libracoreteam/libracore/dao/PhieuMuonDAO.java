package com.libracoreteam.libracore.dao;

import com.libracoreteam.libracore.model.PhieuMuon;
import com.libracoreteam.libracore.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhieuMuonDAO {

    public List<PhieuMuon> getAll() {
        List<PhieuMuon> list = new ArrayList<>();
        String sql = "SELECT * FROM PhieuMuon ORDER BY ngay_Muon DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                PhieuMuon pm = new PhieuMuon();
                pm.setIdPhieuMuon(rs.getInt("id_PhieuMuon"));
                pm.setIdNhanVien(rs.getInt("id_NhanVien"));
                pm.setIdTheThanhVien(rs.getInt("id_TheThanhVien"));
                if (rs.getDate("ngay_Muon") != null) pm.setNgayMuon(rs.getDate("ngay_Muon").toLocalDate());
                if (rs.getDate("ngay_HenTra") != null) pm.setNgayHenTra(rs.getDate("ngay_HenTra").toLocalDate());
                pm.setTrangThai(rs.getString("trang_Thai"));
                pm.setTongSoSachMuon(rs.getInt("tong_So_Sach_Muon"));
                list.add(pm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int add(PhieuMuon pm) {
        String sql = "INSERT INTO PhieuMuon (id_NhanVien, id_TheThanhVien, ngay_Muon, ngay_HenTra, trang_Thai, tong_So_Sach_Muon) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, pm.getIdNhanVien());
            stmt.setInt(2, pm.getIdTheThanhVien());
            stmt.setDate(3, Date.valueOf(pm.getNgayMuon()));
            stmt.setDate(4, Date.valueOf(pm.getNgayHenTra()));
            stmt.setString(5, pm.getTrangThai());
            stmt.setInt(6, pm.getTongSoSachMuon());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean updateStatus(int idPhieuMuon, String trangThai) {
        String sql = "UPDATE PhieuMuon SET trang_Thai = ? WHERE id_PhieuMuon = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, trangThai);
            stmt.setInt(2, idPhieuMuon);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}