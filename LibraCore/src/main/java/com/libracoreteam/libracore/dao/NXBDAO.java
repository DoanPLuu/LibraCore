package com.libracoreteam.libracore.dao;

import com.libracoreteam.libracore.model.NXB;
import com.libracoreteam.libracore.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NXBDAO {

    private static final String TABLE = "NXB";

    private static final String COL_ID = "id_NXB";
    private static final String COL_TEN = "TenNXB";
    private static final String COL_DIA_CHI = "DiaChi";
    private static final String COL_SDT = "SDT";
    private static final String COL_HOAT_DONG = "HoatDong";

    private static final String BASE_SELECT =
            "SELECT " + COL_ID + ", " + COL_TEN + ", " + COL_DIA_CHI + ", " + COL_SDT + ", " + COL_HOAT_DONG +
            " FROM " + TABLE;


    public List<NXB> getActive() {
        String sql = BASE_SELECT +
                " WHERE " + COL_HOAT_DONG + " = ?" +
                " ORDER BY " + COL_TEN + " ASC";

        return queryList(sql, ps -> ps.setBoolean(1, true));
    }

    public List<NXB> getAll() {
        String sql = BASE_SELECT + " ORDER BY " + COL_TEN + " ASC";
        return queryList(sql, null);
    }

    public NXB getById(int id) {
        String sql = BASE_SELECT + " WHERE " + COL_ID + " = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }

        } catch (SQLException e) {
            throw new RuntimeException("NXBDAO.getById failed", e);
        }
    }

    public boolean insert(NXB nxb) {
        String sql =
                "INSERT INTO " + TABLE +
                " (" + COL_TEN + ", " + COL_DIA_CHI + ", " + COL_SDT + ", " + COL_HOAT_DONG + ") " +
                "VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, nxb.getTenNXB());
            ps.setString(2, nxb.getDiaChi());
            ps.setString(3, nxb.getSdt());
            ps.setBoolean(4, nxb.isHoatDong());

            if (ps.executeUpdate() == 0) return false;

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    nxb.setIdNXB(rs.getInt(1));
                    return true;
                }
            }

            return false;

        } catch (SQLException e) {
            throw new RuntimeException("NXBDAO.insert failed", e);
        }
    }

    public boolean update(NXB nxb) {
        String sql =
                "UPDATE " + TABLE + " SET " +
                COL_TEN + " = ?, " +
                COL_DIA_CHI + " = ?, " +
                COL_SDT + " = ?, " +
                COL_HOAT_DONG + " = ? " +
                "WHERE " + COL_ID + " = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nxb.getTenNXB());
            ps.setString(2, nxb.getDiaChi());
            ps.setString(3, nxb.getSdt());
            ps.setBoolean(4, nxb.isHoatDong());
            ps.setInt(5, nxb.getIdNXB());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("NXBDAO.update failed", e);
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
            throw new RuntimeException("NXBDAO.softDelete failed", e);
        }
    }

    public List<NXB> searchActive(String keyword) {
        String sql = BASE_SELECT +
                " WHERE " + COL_HOAT_DONG + " = ? " +
                "AND (" + COL_TEN + " LIKE ? OR " + COL_DIA_CHI + " LIKE ? OR " + COL_SDT + " LIKE ?) " +
                "ORDER BY " + COL_TEN + " ASC";

        return queryList(sql, ps -> {
            String k = "%" + keyword + "%";
            ps.setBoolean(1, true);
            ps.setString(2, k);
            ps.setString(3, k);
            ps.setString(4, k);
        });
    }

    public boolean existsActiveByName(String tenNXB, int excludeId) {
        String sql =
                "SELECT 1 FROM " + TABLE +
                " WHERE " + COL_TEN + " = ? " +
                "AND " + COL_HOAT_DONG + " = ? " +
                "AND " + COL_ID + " <> ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tenNXB);
            ps.setBoolean(2, true);
            ps.setInt(3, excludeId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException("NXBDAO.existsActiveByName failed", e);
        }
    }

    private List<NXB> queryList(String sql, SQLConsumer<PreparedStatement> binder) {
        List<NXB> list = new ArrayList<>();

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
            throw new RuntimeException("NXBDAO.queryList failed", e);
        }
    }

    private NXB map(ResultSet rs) throws SQLException {
        NXB nxb = new NXB();
        nxb.setIdNXB(rs.getInt(COL_ID));
        nxb.setTenNXB(rs.getString(COL_TEN));
        nxb.setDiaChi(rs.getString(COL_DIA_CHI));
        nxb.setSdt(rs.getString(COL_SDT));
        nxb.setHoatDong(rs.getBoolean(COL_HOAT_DONG));
        return nxb;
    }

    @FunctionalInterface
    private interface SQLConsumer<T> {
        void accept(T t) throws SQLException;
    }
}
