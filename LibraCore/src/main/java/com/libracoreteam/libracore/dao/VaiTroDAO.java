package com.libracoreteam.libracore.dao;

import com.libracoreteam.libracore.model.VaiTro;
import com.libracoreteam.libracore.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VaiTroDAO {

    public List<VaiTro> getAll() {
        String sql = "SELECT id_VaiTro, TenVaiTro FROM VaiTro ORDER BY id_VaiTro ASC";
        return queryList(sql, null);
    }

    public List<VaiTro> search(String keyword) {
        if (keyword == null) {
            keyword = "";
        }
        String k = "%" + keyword.trim() + "%";
        String sql = "SELECT id_VaiTro, TenVaiTro FROM VaiTro " +
                     "WHERE CAST(id_VaiTro AS CHAR) LIKE ? OR TenVaiTro LIKE ? " +
                     "ORDER BY id_VaiTro ASC";
        return queryList(sql, new Object[]{k, k});
    }

    private List<VaiTro> queryList(String sql, Object[] params) {
        List<VaiTro> list = new ArrayList<VaiTro>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    ps.setObject(i + 1, params[i]);
                }
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    VaiTro vt = new VaiTro();
                    vt.setIdVaiTro(rs.getInt("id_VaiTro"));
                    vt.setTenVaiTro(rs.getString("TenVaiTro"));
                    list.add(vt);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("VaiTroDAO.queryList failed", e);
        }
        return list;
    }

    public boolean insert(VaiTro vaiTro) {
        String sql = "INSERT INTO VaiTro (TenVaiTro) VALUES (?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, vaiTro.getTenVaiTro());
            if (ps.executeUpdate() == 0) {
                return false;
            }
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    vaiTro.setIdVaiTro(keys.getInt(1));
                }
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("VaiTroDAO.insert failed", e);
        }
    }

    public boolean update(VaiTro vaiTro) {
        String sql = "UPDATE VaiTro SET TenVaiTro = ? WHERE id_VaiTro = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, vaiTro.getTenVaiTro());
            ps.setInt(2, vaiTro.getIdVaiTro());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("VaiTroDAO.update failed", e);
        }
    }

    public boolean delete(int idVaiTro) {
        String sql = "DELETE FROM VaiTro WHERE id_VaiTro = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idVaiTro);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("VaiTroDAO.delete failed", e);
        }
    }

    public boolean hasTaiKhoanUsingVaiTro(int idVaiTro) {
        String sql = "SELECT COUNT(*) FROM TaiKhoan WHERE id_VaiTro = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idVaiTro);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException("VaiTroDAO.hasTaiKhoanUsingVaiTro failed", e);
        }
    }

    public List<Integer> getQuyenIdsByVaiTro(int idVaiTro) {
        String sql = "SELECT id_Quyen FROM VaiTro_Quyen WHERE id_VaiTro = ? ORDER BY id_Quyen ASC";
        List<Integer> list = new ArrayList<Integer>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idVaiTro);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getInt("id_Quyen"));
                }
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("VaiTroDAO.getQuyenIdsByVaiTro failed", e);
        }
    }

    public void setQuyenForVaiTro(int idVaiTro, List<Integer> quyenIds) {
        if (quyenIds == null) {
            quyenIds = Collections.<Integer>emptyList();
        }
        String sqlDelete = "DELETE FROM VaiTro_Quyen WHERE id_VaiTro = ?";
        String sqlInsert = "INSERT INTO VaiTro_Quyen (id_VaiTro, id_Quyen) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            boolean oldAuto = conn.getAutoCommit();
            conn.setAutoCommit(false);
            try {
                try (PreparedStatement psDel = conn.prepareStatement(sqlDelete)) {
                    psDel.setInt(1, idVaiTro);
                    psDel.executeUpdate();
                }

                if (!quyenIds.isEmpty()) {
                    try (PreparedStatement psIns = conn.prepareStatement(sqlInsert)) {
                        for (Integer qid : quyenIds) {
                            if (qid == null) continue;
                            psIns.setInt(1, idVaiTro);
                            psIns.setInt(2, qid);
                            psIns.addBatch();
                        }
                        psIns.executeBatch();
                    }
                }

                conn.commit();
            } catch (SQLException e) {
                try {
                    conn.rollback();
                } catch (SQLException ignore) {
                }
                throw new RuntimeException("VaiTroDAO.setQuyenForVaiTro failed", e);
            } finally {
                try {
                    conn.setAutoCommit(oldAuto);
                } catch (SQLException ignore) {
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("VaiTroDAO.setQuyenForVaiTro failed", e);
        }
    }
}

