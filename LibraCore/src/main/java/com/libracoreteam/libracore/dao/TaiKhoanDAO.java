package com.libracoreteam.libracore.dao;

import com.libracoreteam.libracore.model.TaiKhoan;
import com.libracoreteam.libracore.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaiKhoanDAO {

    private List<TaiKhoan> queryList(String sql, Object[] params) {
        List<TaiKhoan> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    ps.setObject(i + 1, params[i]);
                }
            }

            try (ResultSet rs = ps.executeQuery()) {
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
        } catch (SQLException e) {
            throw new RuntimeException("TaiKhoanDAO.queryList failed", e);
        }
        return list;
    }

    public List<TaiKhoan> getAll() {
        String sql = "SELECT id_TaiKhoan, id_VaiTro, TaiKhoan, MatKhau FROM TaiKhoan ORDER BY id_TaiKhoan ASC";
        return queryList(sql, null);
    }

    public List<TaiKhoan> search(String keyword) {
    String sql = "SELECT tk.* FROM TaiKhoan tk " +
                 "JOIN VaiTro vt ON tk.id_VaiTro = vt.id_VaiTro " +
                 "WHERE CAST(tk.id_TaiKhoan AS CHAR) LIKE ? " +
                 "OR tk.TaiKhoan LIKE ? " +
                 "OR vt.TenVaiTro LIKE ? " +
                 "ORDER BY tk.id_TaiKhoan ASC";
    String param = "%" + keyword.trim() + "%";
    return queryList(sql, new Object[]{param, param, param});
}

    public TaiKhoan getById(int id) {
        String sql = "SELECT id_TaiKhoan, id_VaiTro, TaiKhoan, MatKhau FROM TaiKhoan WHERE id_TaiKhoan = ?";
        List<TaiKhoan> list = queryList(sql, new Object[]{id});
        return list.isEmpty() ? null : list.get(0);
    }

    public boolean insert(TaiKhoan tk) {
        String sql = "INSERT INTO TaiKhoan (id_VaiTro, TaiKhoan, MatKhau) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, tk.getIdVaiTro());
            ps.setString(2, tk.getTaiKhoan());
            ps.setString(3, tk.getMatKhau());
            
            if (ps.executeUpdate() == 0) {
                return false;
            }
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    tk.setIdTaiKhoan(keys.getInt(1));
                }
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("TaiKhoanDAO.insert failed", e);
        }
    }

    public boolean update(TaiKhoan tk) {
        String sql = "UPDATE TaiKhoan SET id_VaiTro = ?, TaiKhoan = ?, MatKhau = ? WHERE id_TaiKhoan = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, tk.getIdVaiTro());
            ps.setString(2, tk.getTaiKhoan());
            ps.setString(3, tk.getMatKhau());
            ps.setInt(4, tk.getIdTaiKhoan());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("TaiKhoanDAO.update failed", e);
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM TaiKhoan WHERE id_TaiKhoan = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("TaiKhoanDAO.delete failed", e);
        }
    }

    public Map<String, Object> getLoginInfo(String username) {
        Map<String, Object> result = null;
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
                    result.put("matKhau", rs.getString("MatKhau")); 
                    result.put("tenNhanVien", rs.getString("TenNhanVien"));
                    result.put("hoatDong", rs.getBoolean("HoatDong"));
                    result.put("tenVaiTro", rs.getString("TenVaiTro"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("TaiKhoanDAO.getLoginInfo failed", e);
        }
        return result;
    }
}