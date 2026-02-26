package com.libracoreteam.libracore.dao;

import com.libracoreteam.libracore.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class TaiKhoanDAO {

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