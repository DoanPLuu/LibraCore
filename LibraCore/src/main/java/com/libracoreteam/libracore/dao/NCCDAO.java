package com.libracoreteam.libracore.dao;

import com.libracoreteam.libracore.model.NCC;
import com.libracoreteam.libracore.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NCCDAO {
    private static final String TABLE = "ncc";
    private static final String COL_ID = "id_NCC";
    private static final String COL_TEN = "TenNCC";
    private static final String COL_HOAT_DONG = "HoatDong"; 
    private static final String BASE_SELECT = "SELECT " + COL_ID + ", " + COL_TEN + " FROM " + TABLE;

    public List<NCC> getAll() {
        String sql = BASE_SELECT + " WHERE " + COL_HOAT_DONG + " = 1 ORDER BY " + COL_TEN + " ASC";
        return queryList(sql, null);
    }

    public NCC getById(int id) {
        String sql = BASE_SELECT + " WHERE " + COL_ID + " = ? AND " + COL_HOAT_DONG + " = 1";

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
        String sql = BASE_SELECT + " WHERE " + COL_HOAT_DONG + " = 1 AND (" + COL_ID + " LIKE ? OR " + COL_TEN + " LIKE ?) ORDER BY " + COL_TEN + " ASC";

        return queryList(sql, ps -> {
            try {
                String k = "%" + keyword + "%";
                ps.setString(1, k);
                ps.setString(2, k);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
    
    public boolean insert(NCC ncc) {
        String sql = "INSERT INTO " + TABLE + " (" + COL_TEN + ", " + COL_HOAT_DONG + ") VALUES (?, 1)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, ncc.getTenNCC());

            if (ps.executeUpdate() == 0) return false;

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

    public boolean update(com.libracoreteam.libracore.model.NCC ncc) {
        String sql = "UPDATE " + TABLE + " SET " + COL_TEN + " = ? WHERE " + COL_ID + " = ?";

        try (java.sql.Connection conn = com.libracoreteam.libracore.util.DBConnection.getConnection();
             java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ncc.getTenNCC());
            ps.setInt(2, ncc.getIdNCC());

            return ps.executeUpdate() > 0;

        } catch (java.sql.SQLException e) {
            throw new RuntimeException("Lỗi khi cập nhật NCC: " + e.getMessage(), e);
        }
    }

    public boolean softDelete(int id) {
        String sql = "UPDATE " + TABLE + " SET " + COL_HOAT_DONG + " = 0 WHERE " + COL_ID + " = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("NCCDAO.softDelete failed", e);
        }
    }

    public boolean existsByName(String tenNCC, int excludeId) {
        String sql = "SELECT 1 FROM " + TABLE + " WHERE " + COL_TEN + " = ? AND " + COL_HOAT_DONG + " = 1 AND " + COL_ID + " <> ?";

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


    private List<NCC> queryList(String sql, SQLConsumer<PreparedStatement> binder) {
        List<NCC> list = new ArrayList<>();

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
            e.printStackTrace();
            throw new RuntimeException("Lỗi truy vấn: " + e.getMessage(), e);
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