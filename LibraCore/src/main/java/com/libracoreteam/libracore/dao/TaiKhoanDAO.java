package com.libracoreteam.libracore.dao;

import com.libracoreteam.libracore.model.TaiKhoan;
import com.libracoreteam.libracore.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaiKhoanDAO {

    // Lấy tất cả tài khoản
    public List<TaiKhoan> getAll() {
        List<TaiKhoan> list = new ArrayList<>();
        String sql = "SELECT id_TaiKhoan, id_VaiTro, TaiKhoan, MatKhau FROM TaiKhoan";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            
            while (rs.next()) {
                TaiKhoan tk = new TaiKhoan(
                    rs.getInt("id_TaiKhoan"),
                    rs.getInt("id_VaiTro"),
                    rs.getString("TaiKhoan"),
                    rs.getString("MatKhau")
                );
                list.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Lấy tài khoản theo ID
    public TaiKhoan getById(int id) {
        String sql = "SELECT id_TaiKhoan, id_VaiTro, TaiKhoan, MatKhau FROM TaiKhoan WHERE id_TaiKhoan = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new TaiKhoan(
                        rs.getInt("id_TaiKhoan"),
                        rs.getInt("id_VaiTro"),
                        rs.getString("TaiKhoan"),
                        rs.getString("MatKhau")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Thêm tài khoản mới
    public boolean insert(TaiKhoan tk) {
        String sql = "INSERT INTO TaiKhoan (id_VaiTro, TaiKhoan, MatKhau) VALUES (?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            
            pst.setInt(1, tk.getIdVaiTro());
            pst.setString(2, tk.getTaiKhoan());
            pst.setString(3, tk.getMatKhau());
            
            if (pst.executeUpdate() > 0) {
                // Lấy auto-increment ID từ database
                try (java.sql.ResultSet rs = pst.getGeneratedKeys()) {
                    if (rs.next()) {
                        tk.setIdTaiKhoan(rs.getInt(1));
                    }
                }
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Cập nhật tài khoản
    public boolean update(TaiKhoan tk) {
        String sql = "UPDATE TaiKhoan SET id_VaiTro = ?, TaiKhoan = ?, MatKhau = ? WHERE id_TaiKhoan = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setInt(1, tk.getIdVaiTro());
            pst.setString(2, tk.getTaiKhoan());
            pst.setString(3, tk.getMatKhau());
            pst.setInt(4, tk.getIdTaiKhoan());
            
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Xóa tài khoản
    public boolean delete(int id) {
        String sql = "DELETE FROM TaiKhoan WHERE id_TaiKhoan = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setInt(1, id);
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Tìm kiếm tài khoản
    public List<TaiKhoan> search(String keyword) {
        List<TaiKhoan> list = new ArrayList<>();
        String sql = "SELECT id_TaiKhoan, id_VaiTro, TaiKhoan, MatKhau FROM TaiKhoan WHERE TaiKhoan LIKE ? OR CAST(id_TaiKhoan AS NVARCHAR) LIKE ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            String searchTerm = "%" + keyword + "%";
            pst.setString(1, searchTerm);
            pst.setString(2, searchTerm);
            
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    TaiKhoan tk = new TaiKhoan(
                        rs.getInt("id_TaiKhoan"),
                        rs.getInt("id_VaiTro"),
                        rs.getString("TaiKhoan"),
                        rs.getString("MatKhau")
                    );
                    list.add(tk);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Map<String, Object> getLoginInfo(String username) {
        Map<String, Object> result = null;

        // Lấy mật khẩu, tên nhân viên và trạng thái hoạt động
        String sql = "SELECT tk.id_TaiKhoan, tk.MatKhau, nv.TenNhanVien, nv.HoatDong, vt.TenVaiTro " +
                "FROM TaiKhoan tk " +
                "JOIN NhanVien nv ON tk.id_TaiKhoan = nv.id_TaiKhoan " +
                "JOIN VaiTro vt ON tk.id_VaiTro = vt.id_VaiTro " +
                "WHERE tk.TaiKhoan = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, username);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    result = new HashMap<>();
                    result.put("idTaiKhoan", rs.getInt("id_TaiKhoan"));
                    result.put("matKhau", rs.getString("MatKhau")); // Mật khẩu chữ thường
                    result.put("tenNhanVien", rs.getString("TenNhanVien"));
                    result.put("hoatDong", rs.getBoolean("HoatDong"));
                    result.put("tenVaiTro", rs.getString("TenVaiTro"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}