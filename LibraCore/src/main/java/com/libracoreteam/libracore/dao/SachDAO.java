package com.libracoreteam.libracore.dao;

import com.libracoreteam.libracore.model.Sach;
import com.libracoreteam.libracore.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SachDAO {

    private static final String TABLE = "Sach";

    private static final String COL_ID = "id_Sach";
    private static final String COL_ID_NXB = "id_NXB";
    private static final String COL_NAM_XB = "NamXuatBan";
    private static final String COL_TEN = "TenSach";
    private static final String COL_MO_TA = "MoTa";
    private static final String COL_SO_TRANG = "SoTrang";
    private static final String COL_HOAT_DONG = "HoatDong";

    private static final String TABLE_SACH_TACGIA = "Sach_TacGia";
    private static final String TABLE_SACH_THELOAI = "Sach_TheLoai";
    private static final String COL_ID_TACGIA = "id_TacGia";
    private static final String COL_ID_THELOAI = "id_TheLoai";


    public List<Sach> getActive() {
        String sql =
                "SELECT id_Sach, id_NXB, NamXuatBan, TenSach, MoTa, SoTrang, HoatDong " +
                "FROM Sach " +
                "WHERE HoatDong = ? " +
                "ORDER BY TenSach ASC";

        List<Sach> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBoolean(1, true);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("SachDAO.getActive failed", e);
        }
    }

    public List<Sach> getAll() {
        String sql =
                "SELECT id_Sach, id_NXB, NamXuatBan, TenSach, MoTa, SoTrang, HoatDong " +
                "FROM Sach " +
                "ORDER BY TenSach ASC";

        List<Sach> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(map(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("SachDAO.getAll failed", e);
        }
    }

    public Sach getById(int idSach) {
        String sql =
                "SELECT id_Sach, id_NXB, NamXuatBan, TenSach, MoTa, SoTrang, HoatDong " +
                "FROM Sach " +
                "WHERE id_Sach = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idSach);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("SachDAO.getById failed", e);
        }
    }


    public boolean insert(Sach s) {
        String sql =
                "INSERT INTO Sach (id_NXB, NamXuatBan, TenSach, MoTa, SoTrang, HoatDong) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            bindSachForUpsert(ps, s, false);

            if (ps.executeUpdate() == 0) return false;

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    s.setIdSach(keys.getInt(1));
                    return true;
                }
            }

            return false;
        } catch (SQLException e) {
            throw new RuntimeException("SachDAO.insert failed", e);
        }
    }

    public boolean update(Sach s) {
        String sql =
                "UPDATE Sach SET " +
                "id_NXB = ?, " +
                "NamXuatBan = ?, " +
                "TenSach = ?, " +
                "MoTa = ?, " +
                "SoTrang = ?, " +
                "HoatDong = ? " +
                "WHERE id_Sach = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            bindSachForUpsert(ps, s, true);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("SachDAO.update failed", e);
        }
    }

    public boolean softDelete(int idSach) {
        String sql = "UPDATE Sach SET HoatDong = ? WHERE id_Sach = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, false);
            ps.setInt(2, idSach);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("SachDAO.softDelete failed", e);
        }
    }


    public List<Sach> searchActive(String keyword) {
        String sql =
                "SELECT id_Sach, id_NXB, NamXuatBan, TenSach, MoTa, SoTrang, HoatDong " +
                "FROM Sach " +
                "WHERE HoatDong = ? AND (TenSach LIKE ? OR MoTa LIKE ?) " +
                "ORDER BY TenSach ASC";

        String k = "%" + (keyword == null ? "" : keyword.trim()) + "%";

        List<Sach> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBoolean(1, true);
            ps.setString(2, k);
            ps.setString(3, k);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("SachDAO.searchActive failed", e);
        }
    }
    
    public List<Sach> searchByTacGia(String keyword) {
        String sql = 
                "SELECT DISTINCT s.id_Sach, s.id_NXB, s.NamXuatBan, s.TenSach, s.MoTa, s.SoTrang, s.HoatDong " +
                "FROM Sach s " +
                "JOIN Sach_TacGia st ON s.id_Sach = st.id_Sach " +
                "JOIN TacGia tg ON st.id_TacGia = tg.id_TacGia " +
                "WHERE s.HoatDong = ? AND tg.TenTacGia LIKE ? " +
                "ORDER BY s.TenSach ASC";

        String k = "%" + (keyword == null ? "" : keyword.trim()) + "%";

        List<Sach> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBoolean(1, true);
            ps.setString(2, k);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("SachDAO.searchByTacGia failed", e);
        }
    }

    public boolean existsActiveByTenSach(String tenSach, int excludeIdSach) {
        String sql =
                "SELECT 1 " +
                "FROM Sach " +
                "WHERE TenSach = ? AND HoatDong = ? AND id_Sach <> ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tenSach);
            ps.setBoolean(2, true);
            ps.setInt(3, excludeIdSach);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("SachDAO.existsActiveByTenSach failed", e);
        }
    }


    public List<Integer> getTacGiaIdsBySach(int idSach) {
        String sql = "SELECT id_TacGia FROM Sach_TacGia WHERE id_Sach = ? ORDER BY id_TacGia ASC";
        return queryIntList(sql, idSach);
    }

    public List<Integer> getTheLoaiIdsBySach(int idSach) {
        String sql = "SELECT id_TheLoai FROM Sach_TheLoai WHERE id_Sach = ? ORDER BY id_TheLoai ASC";
        return queryIntList(sql, idSach);
    }

    public boolean insertWithRelations(Sach s, List<Integer> tacGiaIds, List<Integer> theLoaiIds) {
        String sqlInsertSach =
                "INSERT INTO Sach (id_NXB, NamXuatBan, TenSach, MoTa, SoTrang, HoatDong) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        List<Integer> tgIds = tacGiaIds == null ? Collections.emptyList() : tacGiaIds;
        List<Integer> tlIds = theLoaiIds == null ? Collections.emptyList() : theLoaiIds;

        try (Connection conn = DBConnection.getConnection()) {
            boolean oldAutoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            try {
                try (PreparedStatement ps = conn.prepareStatement(sqlInsertSach, Statement.RETURN_GENERATED_KEYS)) {
                    bindSachForUpsert(ps, s, false);
                    if (ps.executeUpdate() == 0) {
                        conn.rollback();
                        return false;
                    }
                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        if (!keys.next()) {
                            conn.rollback();
                            return false;
                        }
                        s.setIdSach(keys.getInt(1));
                    }
                }

                replaceTacGiaForSach(conn, s.getIdSach(), tgIds);
                replaceTheLoaiForSach(conn, s.getIdSach(), tlIds);

                conn.commit();
                return true;
            } catch (SQLException e) {
                try {
                    conn.rollback();
                } catch (SQLException ignore) {
                }
                throw new RuntimeException("SachDAO.insertWithRelations failed", e);
            } finally {
                try {
                    conn.setAutoCommit(oldAutoCommit);
                } catch (SQLException ignore) {
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("SachDAO.insertWithRelations failed", e);
        }
    }

    public boolean updateWithRelations(Sach s, List<Integer> tacGiaIds, List<Integer> theLoaiIds) {
        String sqlUpdateSach =
                "UPDATE Sach SET " +
                "id_NXB = ?, " +
                "NamXuatBan = ?, " +
                "TenSach = ?, " +
                "MoTa = ?, " +
                "SoTrang = ?, " +
                "HoatDong = ? " +
                "WHERE id_Sach = ?";

        List<Integer> tgIds = tacGiaIds == null ? Collections.emptyList() : tacGiaIds;
        List<Integer> tlIds = theLoaiIds == null ? Collections.emptyList() : theLoaiIds;

        try (Connection conn = DBConnection.getConnection()) {
            boolean oldAutoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            try {
                try (PreparedStatement ps = conn.prepareStatement(sqlUpdateSach)) {
                    bindSachForUpsert(ps, s, true);
                    if (ps.executeUpdate() == 0) {
                        conn.rollback();
                        return false;
                    }
                }

                replaceTacGiaForSach(conn, s.getIdSach(), tgIds);
                replaceTheLoaiForSach(conn, s.getIdSach(), tlIds);

                conn.commit();
                return true;
            } catch (SQLException e) {
                try {
                    conn.rollback();
                } catch (SQLException ignore) {
                }
                throw new RuntimeException("SachDAO.updateWithRelations failed", e);
            } finally {
                try {
                    conn.setAutoCommit(oldAutoCommit);
                } catch (SQLException ignore) {
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("SachDAO.updateWithRelations failed", e);
        }
    }


    private void bindSachForUpsert(PreparedStatement ps, Sach s, boolean includeIdAtEnd) throws SQLException {
        if (s.getIdNXB() == null) ps.setNull(1, Types.INTEGER);
        else ps.setInt(1, s.getIdNXB());

        if (s.getNamXuatBan() == null) ps.setNull(2, Types.INTEGER);
        else ps.setInt(2, s.getNamXuatBan());

        ps.setString(3, s.getTenSach());
        ps.setString(4, s.getMoTa());

        if (s.getSoTrang() == null) ps.setNull(5, Types.INTEGER);
        else ps.setInt(5, s.getSoTrang());

        ps.setBoolean(6, s.isHoatDong());

        if (includeIdAtEnd) {
            ps.setInt(7, s.getIdSach());
        }
    }

    private Sach map(ResultSet rs) throws SQLException {
        Sach s = new Sach();
        s.setIdSach(rs.getInt(COL_ID));

        int idNXB = rs.getInt(COL_ID_NXB);
        s.setIdNXB(rs.wasNull() ? null : idNXB);

        int nam = rs.getInt(COL_NAM_XB);
        s.setNamXuatBan(rs.wasNull() ? null : nam);

        s.setTenSach(rs.getString(COL_TEN));
        s.setMoTa(rs.getString(COL_MO_TA));

        int soTrang = rs.getInt(COL_SO_TRANG);
        s.setSoTrang(rs.wasNull() ? null : soTrang);

        s.setHoatDong(rs.getBoolean(COL_HOAT_DONG));
        return s;
    }

    private List<Integer> queryIntList(String sql, int idSach) {
        List<Integer> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idSach);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(rs.getInt(1));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("SachDAO.queryIntList failed", e);
        }
    }

    private void replaceTacGiaForSach(Connection conn, int idSach, List<Integer> tacGiaIds) throws SQLException {
        String sqlDelete = "DELETE FROM Sach_TacGia WHERE id_Sach = ?";
        String sqlInsert = "INSERT INTO Sach_TacGia (id_Sach, id_TacGia) VALUES (?, ?)";

        try (PreparedStatement psDel = conn.prepareStatement(sqlDelete)) {
            psDel.setInt(1, idSach);
            psDel.executeUpdate();
        }

        if (tacGiaIds == null || tacGiaIds.isEmpty()) return;

        try (PreparedStatement psIns = conn.prepareStatement(sqlInsert)) {
            for (Integer idTacGia : tacGiaIds) {
                if (idTacGia == null) continue;
                psIns.setInt(1, idSach);
                psIns.setInt(2, idTacGia);
                psIns.addBatch();
            }
            psIns.executeBatch();
        }
    }

    private void replaceTheLoaiForSach(Connection conn, int idSach, List<Integer> theLoaiIds) throws SQLException {
        String sqlDelete = "DELETE FROM Sach_TheLoai WHERE id_Sach = ?";
        String sqlInsert = "INSERT INTO Sach_TheLoai (id_Sach, id_TheLoai) VALUES (?, ?)";

        try (PreparedStatement psDel = conn.prepareStatement(sqlDelete)) {
            psDel.setInt(1, idSach);
            psDel.executeUpdate();
        }

        if (theLoaiIds == null || theLoaiIds.isEmpty()) return;

        try (PreparedStatement psIns = conn.prepareStatement(sqlInsert)) {
            for (Integer idTheLoai : theLoaiIds) {
                if (idTheLoai == null) continue;
                psIns.setInt(1, idSach);
                psIns.setInt(2, idTheLoai);
                psIns.addBatch();
            }
            psIns.executeBatch();
        }
    }
}
