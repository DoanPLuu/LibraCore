package com.libracoreteam.libracore.dao;

import com.libracoreteam.libracore.model.NCC;
import com.libracoreteam.libracore.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NCCDAO {
private static final String TABLE = "NCC";
    private static final String COL_ID = "id_NCC";
    private static final String COL_TEN = "TenNCC";

    private static final String BASE_SELECT = "SELECT " + COL_ID + ", " + COL_TEN + " FROM " + TABLE;

    /* ==================== ĐỌC DỮ LIỆU (READ) ==================== */

    public List<NCC> getAll() {
        String sql = BASE_SELECT + " ORDER BY " + COL_TEN + " ASC";
        return queryList(sql, null);
    }

    public NCC getById(int id) {
        String sql = BASE_SELECT + " WHERE " + COL_ID + " = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }

        } catch (SQLException e) {
            throw new RuntimeException("NCCDAO.getById failed", e);
        }
    }

    public List<NCC> search(String keyword) {
        String sql = BASE_SELECT + " WHERE " + COL_TEN + " LIKE ? ORDER BY " + COL_TEN + " ASC";

        return queryList(sql, ps -> {
            ps.setString(1, "%" + keyword + "%");
        });
    }

    /* ==================== GHI DỮ LIỆU (WRITE) ==================== */

    public boolean insert(NCC ncc) {
        String sql = "INSERT INTO " + TABLE + " (" + COL_TEN + ") VALUES (?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, ncc.getTenNCC());

            if (ps.executeUpdate() == 0) return false;

            // Lấy ID tự tăng vừa được tạo ra gán ngược lại cho đối tượng
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    ncc.setIdNCC(rs.getInt(1));
                    return true;
                }
            }
            return false;

        } catch (SQLException e) {
            throw new RuntimeException("NCCDAO.insert failed", e);
        }
    }

    public boolean update(NCC ncc) {
        String sql = "UPDATE " + TABLE + " SET " + COL_TEN + " = ? WHERE " + COL_ID + " = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ncc.getTenNCC());
            ps.setInt(2, ncc.getIdNCC());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("NCCDAO.update failed", e);
        }
    }

    // Xóa cứng (Hard Delete) vì bảng NCC không có cột trạng thái
    public boolean delete(int id) {
        String sql = "DELETE FROM " + TABLE + " WHERE " + COL_ID + " = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("NCCDAO.delete failed", e);
        }
    }

    /* ==================== KIỂM TRA (VALIDATION) ==================== */

    public boolean existsByName(String tenNCC, int excludeId) {
        String sql = "SELECT 1 FROM " + TABLE + " WHERE " + COL_TEN + " = ? AND " + COL_ID + " <> ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tenNCC);
            ps.setInt(2, excludeId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException("NCCDAO.existsByName failed", e);
        }
    }

    /* ==================== HÀM PHỤ TRỢ (HELPERS) ==================== */

    private List<NCC> queryList(String sql, SQLConsumer<PreparedStatement> binder) {
        List<NCC> list = new ArrayList<>();

        // Sử dụng try-with-resources để tự động đóng Connection, Statement và ResultSet
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (binder != null) {
                binder.accept(ps);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }

            return list;

        } catch (SQLException e) {
            throw new RuntimeException("NCCDAO.queryList failed", e);
        }
    }

    private NCC map(ResultSet rs) throws SQLException {
        NCC ncc = new NCC();
        ncc.setIdNCC(rs.getInt(COL_ID));
        ncc.setTenNCC(rs.getString(COL_TEN));
        return ncc;
    }

    @FunctionalInterface
    private interface SQLConsumer<T> {
        void accept(T t) throws SQLException;
    }
}
