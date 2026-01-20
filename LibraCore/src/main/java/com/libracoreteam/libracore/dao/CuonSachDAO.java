package com.libracoreteam.libracore.dao;

import com.libracoreteam.libracore.util.DBConnection;
import java.sql.*;

public class CuonSachDAO {
    // Bên sách cần để lại cái này vì chúng quan trọng cho bên mượn
    public boolean updateStatus(int idCuonSach, String trangThaiMoi) {
        String sql = "UPDATE CuonSach SET trang_Thai_Muon = ? WHERE id_CuonSach = ?";
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
    //Bên sách nên thêm hàm này vì quan trọng cho bên mượn sách
    public boolean isAvailable(int idCuonSach) {
        String sql = "SELECT COUNT(*) FROM CuonSach WHERE id_CuonSach = ? AND trang_Thai_Muon = 'Ranh' AND tinh_Trang_Sach = 'Tot'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCuonSach);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {}
        return false;
    }
    //Bên sách nên thêm hàm này vò nó quan trọng cho bên mượn sách
    public String getTenSachById(int idCuonSach) {
        String sql = "SELECT s.ten_Sach FROM CuonSach cs JOIN Sach s ON cs.id_Sach = s.id_Sach WHERE cs.id_CuonSach = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCuonSach);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getString("ten_Sach");
        } catch (Exception e) {
        }
        return null;
    }
}