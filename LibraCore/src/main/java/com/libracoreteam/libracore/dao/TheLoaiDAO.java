package com.libracoreteam.libracore.dao;

import com.libracoreteam.libracore.model.TheLoai;
import com.libracoreteam.libracore.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO read-only dùng tạm cho UI chọn Thể loại (đổ checklist/combobox).
 */
public class TheLoaiDAO {

    private static final String TABLE = "TheLoai";

    private static final String COL_ID = "id_TheLoai";
    private static final String COL_TEN = "TenTheLoai";
    private static final String COL_HOAT_DONG = "HoatDong";

    private static final String BASE_SELECT =
            "SELECT " + COL_ID + ", " + COL_TEN + ", " + COL_HOAT_DONG +
            " FROM " + TABLE;

    /* ==================== READ ==================== */

    public List<TheLoai> getActive() {
        String sql = BASE_SELECT +
                " WHERE " + COL_HOAT_DONG + " = ?" +
                " ORDER BY " + COL_TEN + " ASC";

        return queryList(sql, ps -> ps.setBoolean(1, true));
    }

    public List<TheLoai> getAll() {
        String sql = BASE_SELECT + " ORDER BY " + COL_TEN + " ASC";
        return queryList(sql, null);
    }

    public TheLoai getById(int id) {
        String sql = BASE_SELECT + " WHERE " + COL_ID + " = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }

        } catch (SQLException e) {
            throw new RuntimeException("TheLoaiDAO.getById failed", e);
        }
    }

    /* ==================== HELPERS ==================== */

    private List<TheLoai> queryList(String sql, SQLConsumer<PreparedStatement> binder) {
        List<TheLoai> list = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (binder != null) binder.accept(ps);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }

            return list;

        } catch (SQLException e) {
            throw new RuntimeException("TheLoaiDAO.queryList failed", e);
        }
    }

    private TheLoai map(ResultSet rs) throws SQLException {
        TheLoai tl = new TheLoai();
        tl.setIdTheLoai(rs.getInt(COL_ID));
        tl.setTenTheLoai(rs.getString(COL_TEN));
        tl.setHoatDong(rs.getBoolean(COL_HOAT_DONG));
        return tl;
    }

    @FunctionalInterface
    private interface SQLConsumer<T> {
        void accept(T t) throws SQLException;
    }
    
    
    public boolean insert(TheLoai tl) {
        String sql = "INSERT INTO TheLoai (TenTheLoai, HoatDong) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setString(1, tl.getTenTheLoai());
            ps.setBoolean(2, tl.isHoatDong()); // Mặc định là true
            
            if (ps.executeUpdate() > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        tl.setIdTheLoai(rs.getInt(1)); // Lấy ID mới tạo gán ngược lại
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}


