package com.libracoreteam.libracore.dao;

import com.libracoreteam.libracore.model.TacGia;
import com.libracoreteam.libracore.util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TacGiaDAO {

    private static final String TABLE = "TacGia";

    private static final String COL_ID = "id_TacGia";
    private static final String COL_TEN = "TenTacGia";
    private static final String COL_NGAY_SINH = "NgaySinh";
    private static final String COL_NOI_SINH = "NoiSinh";
    private static final String COL_SDT = "SDT";
    private static final String COL_HOAT_DONG = "HoatDong";

    private static final String BASE_SELECT =
            "SELECT " + COL_ID + ", " + COL_TEN + ", " + COL_NGAY_SINH + ", " + COL_NOI_SINH + ", " + COL_SDT + ", " + COL_HOAT_DONG +
            " FROM " + TABLE;

    //Phần đọc

    public List<TacGia> getActive() {
        String sql = BASE_SELECT +
                " WHERE " + COL_HOAT_DONG + " = ?" +
                " ORDER BY " + COL_TEN + " ASC";

        return queryList(sql, ps -> ps.setBoolean(1, true));
    }

// Sửa lại hàm getAll để chỉ lấy Tác giả đang hoạt động
    public List<TacGia> getAll() {
        String sql = BASE_SELECT + " WHERE " + COL_HOAT_DONG + " = 1 ORDER BY " + COL_TEN + " ASC";
        return queryList(sql, null);
    }   

    public TacGia getById(int id) {
        String sql = BASE_SELECT + " WHERE " + COL_ID + " = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }

        } catch (SQLException e) {
            throw new RuntimeException("TacGiaDAO.getById failed", e);
        }
    }

    //phần ghi

    public boolean insert(TacGia tacGia) {
        String sql =
                "INSERT INTO " + TABLE +
                " (" + COL_TEN + ", " + COL_NGAY_SINH + ", " + COL_NOI_SINH + ", " + COL_SDT + ", " + COL_HOAT_DONG + ") " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, tacGia.getTenTacGia());
            ps.setDate(2, tacGia.getNgaySinh() != null ? Date.valueOf(tacGia.getNgaySinh()) : null);
            ps.setString(3, tacGia.getNoiSinh());
            ps.setString(4, tacGia.getSdt());
            ps.setBoolean(5, tacGia.isHoatDong());

            if (ps.executeUpdate() == 0) return false;

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    tacGia.setIdTacGia(rs.getInt(1));
                    return true;
                }
            }

            return false;

        } catch (SQLException e) {
            throw new RuntimeException("TacGiaDAO.insert failed", e);
        }
    }

    public boolean update(TacGia tacGia) {
        String sql =
                "UPDATE " + TABLE + " SET " +
                COL_TEN + " = ?, " +
                COL_NGAY_SINH + " = ?, " +
                COL_NOI_SINH + " = ?, " +
                COL_SDT + " = ?, " +
                COL_HOAT_DONG + " = ? " +
                "WHERE " + COL_ID + " = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tacGia.getTenTacGia());
            ps.setDate(2, tacGia.getNgaySinh() != null ? Date.valueOf(tacGia.getNgaySinh()) : null);
            ps.setString(3, tacGia.getNoiSinh());
            ps.setString(4, tacGia.getSdt());
            ps.setBoolean(5, tacGia.isHoatDong());
            ps.setInt(6, tacGia.getIdTacGia());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("TacGiaDAO.update failed", e);
        }
    }

    public boolean softDelete(int id) {
        String sql =
                "UPDATE " + TABLE +
                " SET " + COL_HOAT_DONG + " = ?" +
                " WHERE " + COL_ID + " = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBoolean(1, false);
            ps.setInt(2, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("TacGiaDAO.softDelete failed", e);
        }
    }

    //phần tìm kiếm check này nọ

    public List<TacGia> searchActive(String keyword) {
        String sql = BASE_SELECT +
                " WHERE " + COL_HOAT_DONG + " = ? " +
                "AND (" + COL_TEN + " LIKE ? OR " + COL_NOI_SINH + " LIKE ? OR " + COL_SDT + " LIKE ?) " +
                "ORDER BY " + COL_TEN + " ASC";

        return queryList(sql, ps -> {
            String k = "%" + keyword + "%";
            ps.setBoolean(1, true);
            ps.setString(2, k);
            ps.setString(3, k);
            ps.setString(4, k);
        });
    }
// Sửa lại hàm search để chỉ tìm Tác giả đang hoạt động
    public List<TacGia> search(String keyword) {
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

    public boolean existsActiveByName(String tenTacGia, int excludeId) {
        String sql =
                "SELECT 1 FROM " + TABLE +
                " WHERE " + COL_TEN + " = ? " +
                "AND " + COL_HOAT_DONG + " = ? " +
                "AND " + COL_ID + " <> ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tenTacGia);
            ps.setBoolean(2, true);
            ps.setInt(3, excludeId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException("TacGiaDAO.existsActiveByName failed", e);
        }
    }

    //helper

    private List<TacGia> queryList(String sql, SQLConsumer<PreparedStatement> binder) {
        List<TacGia> list = new ArrayList<>();

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
            throw new RuntimeException("TacGiaDAO.queryList failed", e);
        }
    }

    private TacGia map(ResultSet rs) throws SQLException {
        TacGia tacGia = new TacGia();
        tacGia.setIdTacGia(rs.getInt(COL_ID));
        tacGia.setTenTacGia(rs.getString(COL_TEN));
        
        // Xử lý LocalDate conversion (có thể NULL)
        Date ngaySinhDate = rs.getDate(COL_NGAY_SINH);
        tacGia.setNgaySinh(ngaySinhDate != null ? ngaySinhDate.toLocalDate() : null);
        
        tacGia.setNoiSinh(rs.getString(COL_NOI_SINH));
        tacGia.setSdt(rs.getString(COL_SDT));
        tacGia.setHoatDong(rs.getBoolean(COL_HOAT_DONG));
        return tacGia;
    }

    @FunctionalInterface
    private interface SQLConsumer<T> {
        void accept(T t) throws SQLException;
    }
}
