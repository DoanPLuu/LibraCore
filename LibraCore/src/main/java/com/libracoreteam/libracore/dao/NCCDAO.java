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

    public List<NCC> getAll() {
        String sql = BASE_SELECT + " ORDER BY " + COL_TEN + " ASC";
        return queryList(sql, null);
    }

    private List<NCC> queryList(String sql, SQLConsumer<PreparedStatement> binder) {
        List<NCC> list = new ArrayList<NCC>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);

            if (binder != null)
                binder.accept(ps);

            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(map(rs));
            }

            return list;

        } catch (SQLException e) {
            throw new RuntimeException("NCCDAO.queryList failed", e);
        } finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            if (ps != null)
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                }
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
