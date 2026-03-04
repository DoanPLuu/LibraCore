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

// 1. Sửa hàm getAll: Chỉ lấy thể loại đang hoạt động
    public List<TheLoai> getAll() {
        String sql = BASE_SELECT + " WHERE " + COL_HOAT_DONG + " = 1 ORDER BY " + COL_TEN + " ASC";
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
    /*======WRITE========*/
    // 2. Hàm Cập nhật (Sửa)
    public boolean update(TheLoai tl) {
        String sql = "UPDATE TheLoai SET TenTheLoai = ? WHERE id_TheLoai = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tl.getTenTheLoai());
            ps.setInt(2, tl.getIdTheLoai());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 3. Hàm Xóa mềm (Đổi trạng thái HoatDong thành 0)
    public boolean softDelete(int id) {
        String sql = "UPDATE TheLoai SET HoatDong = 0 WHERE id_TheLoai = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 4. Hàm Tìm kiếm (Tìm theo cả ID và Tên)
    public List<TheLoai> search(String keyword) {
        String sql = BASE_SELECT + " WHERE " + COL_HOAT_DONG + " = 1 AND (" + COL_ID + " LIKE ? OR " + COL_TEN + " LIKE ?) ORDER BY " + COL_TEN + " ASC";
        return queryList(sql, ps -> {
            String k = "%" + keyword + "%";
            ps.setString(1, k);
            ps.setString(2, k);
        });
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


