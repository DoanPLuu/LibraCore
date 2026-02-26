package com.libracoreteam.libracore.dao;

import com.libracoreteam.libracore.model.ChiTietPhieuNhap;
import com.libracoreteam.libracore.model.NCC;
import com.libracoreteam.libracore.model.PhieuNhap;
import com.libracoreteam.libracore.model.Sach;
import com.libracoreteam.libracore.util.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PhieuNhapDAO {

    public List<PhieuNhap> getActive() {
        String sql =
                "SELECT p.id_PhieuNhap, p.id_NCC, p.NgayNhap, p.SoLuongSach, p.id_NhanVien, p.TrangThai, n.TenNCC " +
                "FROM PhieuNhap p " +
                "LEFT JOIN NCC n ON n.id_NCC = p.id_NCC " +
                "WHERE p.TrangThai <> ? " +
                "ORDER BY p.id_PhieuNhap DESC";
        return queryList(sql, false, "DaHuy");
    }

    public List<PhieuNhap> getDaHuy() {
        String sql =
                "SELECT p.id_PhieuNhap, p.id_NCC, p.NgayNhap, p.SoLuongSach, p.id_NhanVien, p.TrangThai, n.TenNCC " +
                "FROM PhieuNhap p " +
                "LEFT JOIN NCC n ON n.id_NCC = p.id_NCC " +
                "WHERE p.TrangThai = ? " +
                "ORDER BY p.id_PhieuNhap DESC";
        return queryList(sql, false, "DaHuy");
    }

    public List<PhieuNhap> search(String keyword, boolean onlyDaHuy) {
        String sql =
                "SELECT p.id_PhieuNhap, p.id_NCC, p.NgayNhap, p.SoLuongSach, p.id_NhanVien, p.TrangThai, n.TenNCC " +
                "FROM PhieuNhap p " +
                "LEFT JOIN NCC n ON n.id_NCC = p.id_NCC " +
                "WHERE " + (onlyDaHuy ? "p.TrangThai = ?" : "p.TrangThai <> ?") +
                " AND (CAST(p.id_PhieuNhap AS CHAR) LIKE ? OR COALESCE(n.TenNCC, '') LIKE ?) " +
                "ORDER BY p.id_PhieuNhap DESC";

        String k = "%" + (keyword == null ? "" : keyword.trim()) + "%";
        return queryList(sql, true, "DaHuy", k, k);
    }

    public boolean insertWithDetails(PhieuNhap phieuNhap, List<ChiTietPhieuNhap> details) {
        String sqlInsertPhieu =
                "INSERT INTO PhieuNhap (id_NCC, NgayNhap, SoLuongSach, id_NhanVien, TrangThai) VALUES (?, ?, ?, ?, ?)";
        String sqlInsertDetail =
                "INSERT INTO ChiTietPhieuNhap (id_PhieuNhap, id_Sach, SoLuong, GiaTien, MaDauSach) VALUES (?, ?, ?, ?, ?)";
        String sqlInsertCuonSach =
                "INSERT INTO CuonSach (id_Sach, TinhTrangSach, TrangThaiMuon, DaHuy) VALUES (?, ?, ?, ?)";

        List<ChiTietPhieuNhap> chiTietList = details == null ? Collections.emptyList() : details;

        try (Connection conn = DBConnection.getConnection()) {
            boolean oldAutoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            try {
                try (PreparedStatement ps = conn.prepareStatement(sqlInsertPhieu, Statement.RETURN_GENERATED_KEYS)) {
                    if (phieuNhap.getIdNCC() == null) {
                        ps.setNull(1, Types.INTEGER);
                    } else {
                        ps.setInt(1, phieuNhap.getIdNCC());
                    }

                    LocalDate ngayNhap = phieuNhap.getNgayNhap();
                    if (ngayNhap == null) {
                        ps.setDate(2, Date.valueOf(LocalDate.now()));
                    } else {
                        ps.setDate(2, Date.valueOf(ngayNhap));
                    }

                    if (phieuNhap.getSoLuongSach() == null) {
                        ps.setNull(3, Types.INTEGER);
                    } else {
                        ps.setInt(3, phieuNhap.getSoLuongSach());
                    }

                    ps.setInt(4, phieuNhap.getIdNhanVien());
                    ps.setString(5, phieuNhap.getTrangThai() == null ? "DaNhap" : phieuNhap.getTrangThai());

                    if (ps.executeUpdate() == 0) {
                        conn.rollback();
                        return false;
                    }

                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        if (!keys.next()) {
                            conn.rollback();
                            return false;
                        }
                        phieuNhap.setIdPhieuNhap(keys.getInt(1));
                    }
                }

                try (PreparedStatement psDetail = conn.prepareStatement(sqlInsertDetail)) {
                    for (ChiTietPhieuNhap ct : chiTietList) {
                        psDetail.setInt(1, phieuNhap.getIdPhieuNhap());
                        psDetail.setInt(2, ct.getIdSach());

                        if (ct.getSoLuong() == null) {
                            psDetail.setNull(3, Types.INTEGER);
                        } else {
                            psDetail.setInt(3, ct.getSoLuong());
                        }

                        if (ct.getGiaTien() == null) {
                            psDetail.setNull(4, Types.DECIMAL);
                        } else {
                            psDetail.setBigDecimal(4, ct.getGiaTien());
                        }

                        psDetail.setString(5, ct.getMaDauSach());
                        psDetail.addBatch();
                    }
                    psDetail.executeBatch();
                }

                // 3) Nhap kho thuc te: tao ban sao trong bang CuonSach theo SoLuong tung dong chi tiet
                try (PreparedStatement psCuonSach = conn.prepareStatement(sqlInsertCuonSach)) {
                    for (ChiTietPhieuNhap ct : chiTietList) {
                        int soLuong = (ct.getSoLuong() == null) ? 0 : ct.getSoLuong();
                        if (soLuong <= 0) {
                            continue;
                        }

                        for (int i = 0; i < soLuong; i++) {
                            psCuonSach.setInt(1, ct.getIdSach());
                            psCuonSach.setString(2, "Tot");
                            psCuonSach.setString(3, "Ranh");
                            psCuonSach.setBoolean(4, false);
                            psCuonSach.addBatch();
                        }
                    }
                    psCuonSach.executeBatch();
                }

                conn.commit();
                return true;
            } catch (SQLException e) {
                try {
                    conn.rollback();
                } catch (SQLException ignore) {
                }
                throw new RuntimeException("PhieuNhapDAO.insertWithDetails failed", e);
            } finally {
                try {
                    conn.setAutoCommit(oldAutoCommit);
                } catch (SQLException ignore) {
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("PhieuNhapDAO.insertWithDetails failed", e);
        }
    }

    public List<ChiTietPhieuNhap> getDetailsByPhieuNhap(int idPhieuNhap) {
        String sql =
                "SELECT c.id_ChiTietPhieuNhap, c.id_PhieuNhap, c.id_Sach, c.SoLuong, c.GiaTien, c.MaDauSach, s.TenSach " +
                "FROM ChiTietPhieuNhap c " +
                "JOIN Sach s ON s.id_Sach = c.id_Sach " +
                "WHERE c.id_PhieuNhap = ? " +
                "ORDER BY c.id_ChiTietPhieuNhap ASC";

        List<ChiTietPhieuNhap> list = new ArrayList<ChiTietPhieuNhap>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPhieuNhap);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ChiTietPhieuNhap ct = new ChiTietPhieuNhap();
                    ct.setIdChiTietPhieuNhap(rs.getInt("id_ChiTietPhieuNhap"));
                    ct.setIdPhieuNhap(rs.getInt("id_PhieuNhap"));
                    ct.setIdSach(rs.getInt("id_Sach"));

                    int soLuong = rs.getInt("SoLuong");
                    ct.setSoLuong(rs.wasNull() ? null : soLuong);

                    ct.setGiaTien(rs.getBigDecimal("GiaTien"));
                    ct.setMaDauSach(rs.getString("MaDauSach"));

                    Sach sach = new Sach();
                    sach.setIdSach(ct.getIdSach());
                    sach.setTenSach(rs.getString("TenSach"));
                    ct.setSach(sach);

                    list.add(ct);
                }
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("PhieuNhapDAO.getDetailsByPhieuNhap failed", e);
        }
    }

    public boolean cancel(int idPhieuNhap) {
        String sql = "UPDATE PhieuNhap SET TrangThai = ? WHERE id_PhieuNhap = ? AND TrangThai <> ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "DaHuy");
            ps.setInt(2, idPhieuNhap);
            ps.setString(3, "DaHuy");
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("PhieuNhapDAO.cancel failed", e);
        }
    }

    private List<PhieuNhap> queryList(String sql, boolean hasKeyword, String trangThai, String... keywords) {
        List<PhieuNhap> list = new ArrayList<PhieuNhap>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, trangThai);
            if (hasKeyword) {
                ps.setString(2, keywords[0]);
                ps.setString(3, keywords[1]);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapSummary(rs));
                }
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("PhieuNhapDAO.queryList failed", e);
        }
    }

    private PhieuNhap mapSummary(ResultSet rs) throws SQLException {
        PhieuNhap p = new PhieuNhap();
        p.setIdPhieuNhap(rs.getInt("id_PhieuNhap"));

        int idNcc = rs.getInt("id_NCC");
        if (rs.wasNull()) {
            p.setIdNCC(null);
        } else {
            p.setIdNCC(idNcc);
            NCC ncc = new NCC();
            ncc.setIdNCC(idNcc);
            ncc.setTenNCC(rs.getString("TenNCC"));
            p.setNcc(ncc);
        }

        Date ngayNhap = rs.getDate("NgayNhap");
        p.setNgayNhap(ngayNhap == null ? null : ngayNhap.toLocalDate());

        int soLuongSach = rs.getInt("SoLuongSach");
        p.setSoLuongSach(rs.wasNull() ? null : soLuongSach);

        p.setIdNhanVien(rs.getInt("id_NhanVien"));
        p.setTrangThai(rs.getString("TrangThai"));
        return p;
    }
}
