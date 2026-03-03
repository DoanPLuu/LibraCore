package com.libracoreteam.libracore.dao;

import com.libracoreteam.libracore.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ThongKeDAO {

    // Đếm tổng số lượng sách đang hoạt động
    public int getTongSoSach() {
        String sql = "SELECT COUNT(*) FROM Sach WHERE HoatDong = 1";
        return executeCountQuery(sql);
    }

    // Đếm tổng số độc giả đang hoạt động (chưa bị khóa/xóa)
    public int getTongDocGia() {
        String sql = "SELECT COUNT(*) FROM DocGia WHERE HoatDong = 1";
        return executeCountQuery(sql);
    }

    // Đếm số phiếu mượn ĐANG MƯỢN (chưa trả)
    public int getSoPhieuDangMuon() {
        String sql = "SELECT COUNT(*) FROM PhieuMuon WHERE TrangThai = 'DangMuon'";
        return executeCountQuery(sql);
    }

    // Tính tổng doanh thu tiền phạt (Những phiếu phạt đã thu)
    public double getTongDoanhThuPhat() {
        String sql = "SELECT SUM(TienPhatPhaiNop) FROM PhieuPhat WHERE TrangThai = 'DaThu'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble(1); // Trả về 0.0 nếu kết quả là null
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    // Hàm dùng chung để chạy các lệnh COUNT cho gọn code
    private int executeCountQuery(String sql) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}