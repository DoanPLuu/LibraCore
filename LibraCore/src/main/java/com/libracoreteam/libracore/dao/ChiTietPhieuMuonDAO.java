package com.libracoreteam.libracore.dao;

import com.libracoreteam.libracore.model.ChiTietPhieuMuon;
import com.libracoreteam.libracore.model.CuonSach;
import com.libracoreteam.libracore.model.Sach;
import com.libracoreteam.libracore.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChiTietPhieuMuonDAO{

    public List<ChiTietPhieuMuon> getByPhieuMuonId(int idPhieuMuon) {
        List<ChiTietPhieuMuon> list = new ArrayList<>();
        // JOIN 3 bảng: ChiTiet -> CuonSach -> Sach (để lấy tên sách)
        String sql = "SELECT ct.*, s.ten_Sach FROM ChiTietPhieuMuon ct " +
                "JOIN CuonSach cs ON ct.id_CuonSach = cs.id_CuonSach " +
                "JOIN Sach s ON cs.id_Sach = s.id_Sach " +
                "WHERE ct.id_PhieuMuon = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPhieuMuon);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ChiTietPhieuMuon ct = new ChiTietPhieuMuon();
                ct.setIdChiTietPhieuMuon(rs.getInt("id_ChiTietPhieuMuon"));
                ct.setIdPhieuMuon(rs.getInt("id_PhieuMuon"));
                ct.setIdCuonSach(rs.getInt("id_CuonSach"));
                if (rs.getDate("ngay_Tra") != null) ct.setNgayTra(rs.getDate("ngay_Tra").toLocalDate());
                ct.setTinhTrangTra(rs.getString("tinh_Trang_Tra"));

                // Tạo đối tượng CuonSach giả để chứa tên sách hiển thị
                CuonSach cs = new CuonSach();
                Sach s = new Sach();
                s.setTenSach(rs.getString("ten_Sach"));
                cs.setSach(s);
                ct.setCuonSach(cs);

                list.add(ct);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean add(ChiTietPhieuMuon ct) {
        String sql = "INSERT INTO ChiTietPhieuMuon (id_PhieuMuon, id_CuonSach, tinh_Trang_Tra) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ct.getIdPhieuMuon());
            stmt.setInt(2, ct.getIdCuonSach());
            stmt.setString(3, ct.getTinhTrangTra()); // Thường là "ChuaTra"
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateTraSach(int idChiTiet, java.time.LocalDate ngayTra, String tinhTrang) {
        String sql = "UPDATE ChiTietPhieuMuon SET ngay_Tra = ?, tinh_Trang_Tra = ? WHERE id_ChiTietPhieuMuon = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(ngayTra));
            stmt.setString(2, tinhTrang);
            stmt.setInt(3, idChiTiet);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}