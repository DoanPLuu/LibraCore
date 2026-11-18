package com.libracoreteam.libracore.dao;

import com.libracoreteam.libracore.util.DBConnection;
import com.libracoreteam.libracore.model.DashboardStatistics;
import com.libracoreteam.libracore.model.DashboardStatistics.BookRanking;
import com.libracoreteam.libracore.model.DashboardStatistics.GenreInventory;
import com.libracoreteam.libracore.model.DashboardStatistics.Granularity;
import com.libracoreteam.libracore.model.DashboardStatistics.LoanTrendPoint;
import com.libracoreteam.libracore.model.DashboardStatistics.ReaderTrendPoint;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ThongKeDAO {

    public DashboardStatistics getDashboardStatistics(LocalDate from, LocalDate to, Granularity granularity)
            throws SQLException {
        String summarySql = "SELECT " +
                "(SELECT COUNT(*) FROM Sach WHERE HoatDong = 1), " +
                "(SELECT COUNT(*) FROM DocGia), " +
                "(SELECT COUNT(*) FROM DocGia d WHERE d.HoatDong = 1 " +
                " AND EXISTS (SELECT 1 FROM TheThanhVien t WHERE t.id_DocGia = d.id_DocGia " +
                " AND t.TrangThai = 'HoatDong')), " +
                "(SELECT COUNT(*) FROM (SELECT id_DocGia FROM TheThanhVien " +
                " WHERE NgayCap IS NOT NULL GROUP BY id_DocGia " +
                " HAVING MIN(NgayCap) BETWEEN ? AND ?) registrations), " +
                "(SELECT COUNT(DISTINCT d.id_DocGia) FROM DocGia d " +
                " JOIN TheThanhVien t ON t.id_DocGia = d.id_DocGia " +
                " JOIN PhieuMuon pm ON pm.id_TheThanhVien = t.id_TheThanhVien " +
                " JOIN ChiTietPhieuMuon ct ON ct.id_PhieuMuon = pm.id_PhieuMuon " +
                " WHERE d.HoatDong = 1 AND ct.TinhTrangTra = 'ChuaTra' AND pm.TrangThai <> 'DaHuy'), " +
                "(SELECT COUNT(*) FROM PhieuMuon WHERE TrangThai IN ('DangMuon', 'QuaHen')), " +
                "(SELECT COUNT(*) FROM ChiTietPhieuMuon ct JOIN PhieuMuon pm ON pm.id_PhieuMuon = ct.id_PhieuMuon " +
                " WHERE pm.TrangThai <> 'DaHuy'), " +
                "(SELECT COUNT(*) FROM ChiTietPhieuMuon ct JOIN PhieuMuon pm ON pm.id_PhieuMuon = ct.id_PhieuMuon " +
                " WHERE pm.TrangThai <> 'DaHuy' AND ct.TinhTrangTra IN ('DaTra', 'TreHan', 'Hong') " +
                " AND ct.NgayTra IS NOT NULL), " +
                "(SELECT COUNT(*) FROM ChiTietPhieuMuon ct JOIN PhieuMuon pm ON pm.id_PhieuMuon = ct.id_PhieuMuon " +
                " WHERE pm.TrangThai <> 'DaHuy' AND ct.TinhTrangTra = 'ChuaTra'), " +
                "(SELECT COUNT(*) FROM ChiTietPhieuMuon ct JOIN PhieuMuon pm ON pm.id_PhieuMuon = ct.id_PhieuMuon " +
                " WHERE pm.TrangThai <> 'DaHuy' AND ct.TinhTrangTra = 'ChuaTra' " +
                " AND pm.NgayHenTra < CURRENT_DATE), " +
                "(SELECT COUNT(*) FROM CuonSach WHERE DaHuy IS NULL OR DaHuy = 0), " +
                "(SELECT COUNT(*) FROM CuonSach WHERE (DaHuy IS NULL OR DaHuy = 0) " +
                " AND TinhTrangSach = 'Tot' AND TrangThaiMuon = 'Ranh'), " +
                "(SELECT COUNT(*) FROM CuonSach WHERE (DaHuy IS NULL OR DaHuy = 0) " +
                " AND TrangThaiMuon = 'DangMuon'), " +
                "(SELECT COALESCE(SUM(TienPhatPhaiNop), 0) FROM PhieuPhat WHERE TrangThai = 'DaThu')";

        try (Connection connection = DBConnection.getConnection()) {
            connection.setAutoCommit(false);
            try {
                long[] summary = new long[13];
                BigDecimal collectedFines;
                try (PreparedStatement statement = connection.prepareStatement(summarySql)) {
                    statement.setDate(1, Date.valueOf(from));
                    statement.setDate(2, Date.valueOf(to));
                    try (ResultSet result = statement.executeQuery()) {
                        result.next();
                        for (int i = 0; i < summary.length; i++) {
                            summary[i] = result.getLong(i + 1);
                        }
                        collectedFines = result.getBigDecimal(14);
                    }
                }

                List<LoanTrendPoint> loanTrend = getLoanTrend(connection, from, to, granularity);
                List<ReaderTrendPoint> readerTrend = getReaderTrend(connection, from, to, granularity);
                List<BookRanking> mostBorrowedBooks = getMostBorrowedBooks(connection, from, to);
                List<GenreInventory> inventoryByGenre = getInventoryByGenre(connection);
                connection.commit();

                long unavailableCopies = summary[10] - summary[11] - summary[12];
                return new DashboardStatistics(
                        summary[0], summary[1], summary[2], summary[3], summary[4], summary[5],
                        summary[6], summary[7], summary[8], summary[9], summary[10], summary[11],
                        summary[12], unavailableCopies, collectedFines, loanTrend, readerTrend,
                        mostBorrowedBooks, inventoryByGenre);
            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackError) {
                    e.addSuppressed(rollbackError);
                }
                throw e;
            }
        }
    }

    private List<LoanTrendPoint> getLoanTrend(
            Connection connection, LocalDate from, LocalDate to, Granularity granularity) throws SQLException {
        String borrowBucket = bucketExpression("pm.NgayMuon", granularity);
        String returnBucket = bucketExpression("ct.NgayTra", granularity);
        String labelFormat = labelFormat(granularity);
        String sql = "SELECT DATE_FORMAT(activity.period_start, '" + labelFormat + "') AS period_label, " +
                "SUM(activity.borrowed_count), SUM(activity.returned_count) " +
                "FROM (" +
                " SELECT " + borrowBucket + " AS period_start, COUNT(*) AS borrowed_count, 0 AS returned_count " +
                " FROM ChiTietPhieuMuon ct JOIN PhieuMuon pm ON pm.id_PhieuMuon = ct.id_PhieuMuon " +
                " WHERE pm.TrangThai <> 'DaHuy' AND pm.NgayMuon BETWEEN ? AND ? " +
                " GROUP BY " + borrowBucket +
                " UNION ALL " +
                " SELECT " + returnBucket + " AS period_start, 0 AS borrowed_count, COUNT(*) AS returned_count " +
                " FROM ChiTietPhieuMuon ct JOIN PhieuMuon pm ON pm.id_PhieuMuon = ct.id_PhieuMuon " +
                " WHERE pm.TrangThai <> 'DaHuy' AND ct.TinhTrangTra IN ('DaTra', 'TreHan', 'Hong') " +
                " AND ct.NgayTra BETWEEN ? AND ? GROUP BY " + returnBucket +
                ") activity GROUP BY activity.period_start ORDER BY activity.period_start";
        List<LoanTrendPoint> points = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, Date.valueOf(from));
            statement.setDate(2, Date.valueOf(to));
            statement.setDate(3, Date.valueOf(from));
            statement.setDate(4, Date.valueOf(to));
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    points.add(new LoanTrendPoint(result.getString(1), result.getLong(2), result.getLong(3)));
                }
            }
        }
        return points;
    }

    private List<ReaderTrendPoint> getReaderTrend(
            Connection connection, LocalDate from, LocalDate to, Granularity granularity) throws SQLException {
        String bucket = bucketExpression("registrations.first_issue", granularity);
        String sql = "SELECT DATE_FORMAT(reader_period.period_start, '" + labelFormat(granularity) +
                "') AS period_label, COUNT(*) FROM (" +
                " SELECT " + bucket + " AS period_start FROM (" +
                "  SELECT id_DocGia, MIN(NgayCap) AS first_issue FROM TheThanhVien " +
                "  WHERE NgayCap IS NOT NULL GROUP BY id_DocGia " +
                "  HAVING MIN(NgayCap) BETWEEN ? AND ?" +
                " ) registrations" +
                ") reader_period GROUP BY reader_period.period_start ORDER BY reader_period.period_start";
        List<ReaderTrendPoint> points = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, Date.valueOf(from));
            statement.setDate(2, Date.valueOf(to));
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    points.add(new ReaderTrendPoint(result.getString(1), result.getLong(2)));
                }
            }
        }
        return points;
    }

    private List<BookRanking> getMostBorrowedBooks(Connection connection, LocalDate from, LocalDate to)
            throws SQLException {
        String sql = "SELECT s.id_Sach, s.TenSach, COUNT(DISTINCT ct.id_ChiTietPhieuMuon) AS borrow_count " +
                "FROM ChiTietPhieuMuon ct " +
                "JOIN PhieuMuon pm ON pm.id_PhieuMuon = ct.id_PhieuMuon " +
                "JOIN CuonSach cs ON cs.id_CuonSach = ct.id_CuonSach " +
                "JOIN Sach s ON s.id_Sach = cs.id_Sach " +
                "WHERE pm.TrangThai <> 'DaHuy' AND pm.NgayMuon BETWEEN ? AND ? " +
                "GROUP BY s.id_Sach, s.TenSach " +
                "ORDER BY borrow_count DESC, s.TenSach ASC LIMIT 10";
        List<BookRanking> rankings = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, Date.valueOf(from));
            statement.setDate(2, Date.valueOf(to));
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    rankings.add(new BookRanking(
                            result.getInt(1), result.getString(2), result.getLong(3)));
                }
            }
        }
        return rankings;
    }

    private List<GenreInventory> getInventoryByGenre(Connection connection) throws SQLException {
        String sql = "SELECT COALESCE(tl.TenTheLoai, 'Chưa phân loại') AS genre, " +
                "COUNT(DISTINCT cs.id_CuonSach) AS total_copies, " +
                "COUNT(DISTINCT CASE WHEN cs.TinhTrangSach = 'Tot' AND cs.TrangThaiMuon = 'Ranh' " +
                " THEN cs.id_CuonSach END) AS available_copies, " +
                "COUNT(DISTINCT CASE WHEN cs.TrangThaiMuon = 'DangMuon' THEN cs.id_CuonSach END) AS copies_on_loan " +
                "FROM CuonSach cs " +
                "JOIN Sach s ON s.id_Sach = cs.id_Sach " +
                "LEFT JOIN Sach_TheLoai stl ON stl.id_Sach = s.id_Sach " +
                "LEFT JOIN TheLoai tl ON tl.id_TheLoai = stl.id_TheLoai " +
                "WHERE cs.DaHuy IS NULL OR cs.DaHuy = 0 " +
                "GROUP BY COALESCE(tl.TenTheLoai, 'Chưa phân loại') ORDER BY genre";
        List<GenreInventory> inventory = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                inventory.add(new GenreInventory(
                        result.getString(1), result.getLong(2), result.getLong(3), result.getLong(4)));
            }
        }
        return inventory;
    }

    private String bucketExpression(String dateColumn, Granularity granularity) {
        return switch (granularity) {
            case DAY -> "DATE(" + dateColumn + ")";
            case MONTH -> "CAST(DATE_FORMAT(" + dateColumn + ", '%Y-%m-01') AS DATE)";
            case YEAR -> "CAST(DATE_FORMAT(" + dateColumn + ", '%Y-01-01') AS DATE)";
        };
    }

    private String labelFormat(Granularity granularity) {
        return switch (granularity) {
            case DAY -> "%d/%m/%Y";
            case MONTH -> "%m/%Y";
            case YEAR -> "%Y";
        };
    }

    public int getTongSoSach() {
        return executeCountQuery("SELECT COUNT(*) FROM Sach WHERE HoatDong = 1");
    }

    public int getTongDocGia() {
        return executeCountQuery("SELECT COUNT(*) FROM DocGia WHERE HoatDong = 1");
    }

    public int getSoPhieuDangMuon() {
        return executeCountQuery("SELECT COUNT(*) FROM PhieuMuon WHERE TrangThai = 'DangMuon'");
    }

    public double getTongDoanhThuPhat() {
        String sql = "SELECT SUM(TienPhatPhaiNop) FROM PhieuPhat WHERE TrangThai = 'DaThu'";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            if (rs.next())
                return rs.getDouble(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }


 
    public List<Object[]> getSachNhapKho(LocalDate from, LocalDate to) {
        String sql = "SELECT s.TenSach, " +
                "  COALESCE(GROUP_CONCAT(DISTINCT tl.TenTheLoai ORDER BY tl.TenTheLoai SEPARATOR ', '), '') AS TheLoai, "
                +
                "  ct.SoLuong, " +
                "  pn.NgayNhap " +
                "FROM ChiTietPhieuNhap ct " +
                "JOIN Sach s ON s.id_Sach = ct.id_Sach " +
                "JOIN PhieuNhap pn ON pn.id_PhieuNhap = ct.id_PhieuNhap " +
                "LEFT JOIN Sach_TheLoai stl ON stl.id_Sach = s.id_Sach " +
                "LEFT JOIN TheLoai tl ON tl.id_TheLoai = stl.id_TheLoai " +
                "WHERE pn.NgayNhap BETWEEN ? AND ? " +
                "  AND pn.TrangThai <> 'DaHuy' " +
                "GROUP BY ct.id_ChiTietPhieuNhap, s.TenSach, ct.SoLuong, pn.NgayNhap " +
                "ORDER BY pn.NgayNhap DESC, s.TenSach";
        return runQuery(sql, from, to);
    }

   
    public List<Object[]> getSachDangMuon(LocalDate from, LocalDate to) {
        String sql = "SELECT s.TenSach, " +
                "  COALESCE(GROUP_CONCAT(DISTINCT tl.TenTheLoai ORDER BY tl.TenTheLoai SEPARATOR ', '), '') AS TheLoai, "
                +
                "  COUNT(DISTINCT ct.id_ChiTietPhieuMuon) AS SoLuong, " +
                "  pm.NgayMuon " +
                "FROM ChiTietPhieuMuon ct " +
                "JOIN PhieuMuon pm ON pm.id_PhieuMuon = ct.id_PhieuMuon " +
                "JOIN CuonSach cs ON cs.id_CuonSach = ct.id_CuonSach " +
                "JOIN Sach s ON s.id_Sach = cs.id_Sach " +
                "LEFT JOIN Sach_TheLoai stl ON stl.id_Sach = s.id_Sach " +
                "LEFT JOIN TheLoai tl ON tl.id_TheLoai = stl.id_TheLoai " +
                "WHERE pm.NgayMuon BETWEEN ? AND ? " +
                "  AND ct.TinhTrangTra = 'ChuaTra' " +
                "GROUP BY s.id_Sach, s.TenSach, pm.NgayMuon " +
                "ORDER BY pm.NgayMuon DESC, s.TenSach";
        return runQuery(sql, from, to);
    }

   
    public List<Object[]> getSachHongHoacMat(LocalDate from, LocalDate to) {
        String sql = "SELECT s.TenSach, " +
                "  COALESCE(GROUP_CONCAT(DISTINCT tl.TenTheLoai ORDER BY tl.TenTheLoai SEPARATOR ', '), '') AS TheLoai, "
                +
                "  COUNT(DISTINCT cs.id_CuonSach) AS SoLuong, " +
                "  MAX(pn.NgayNhap) AS NgayNhap " +
                "FROM CuonSach cs " +
                "JOIN Sach s ON s.id_Sach = cs.id_Sach " +
                "LEFT JOIN ChiTietPhieuNhap ct ON ct.id_ChiTietPhieuNhap = cs.id_ChiTietPhieuNhap " +
                "LEFT JOIN PhieuNhap pn ON pn.id_PhieuNhap = ct.id_PhieuNhap " +
                "LEFT JOIN Sach_TheLoai stl ON stl.id_Sach = s.id_Sach " +
                "LEFT JOIN TheLoai tl ON tl.id_TheLoai = stl.id_TheLoai " +
                "WHERE cs.TinhTrangSach IN ('Hong', 'Mat') " +
                "  AND COALESCE(cs.DaHuy, 0) = 0 " +
                "  AND (pn.NgayNhap IS NULL OR pn.NgayNhap BETWEEN ? AND ?) " +
                "GROUP BY s.id_Sach, s.TenSach " +
                "ORDER BY SoLuong DESC, s.TenSach";
        return runQuery(sql, from, to);
    }

   
    public List<Object[]> getTongSoSachTheoSach(LocalDate from, LocalDate to) {
        String sql = "SELECT s.TenSach, " +
                "  COALESCE(GROUP_CONCAT(DISTINCT tl.TenTheLoai ORDER BY tl.TenTheLoai SEPARATOR ', '), '') AS TheLoai, "
                +
                "  COUNT(DISTINCT cs.id_CuonSach) AS SoLuong, " +
                "  MAX(pn.NgayNhap) AS NgayNhap " +
                "FROM CuonSach cs " +
                "JOIN Sach s ON s.id_Sach = cs.id_Sach " +
                "LEFT JOIN ChiTietPhieuNhap ct ON ct.id_ChiTietPhieuNhap = cs.id_ChiTietPhieuNhap " +
                "LEFT JOIN PhieuNhap pn ON pn.id_PhieuNhap = ct.id_PhieuNhap " +
                "LEFT JOIN Sach_TheLoai stl ON stl.id_Sach = s.id_Sach " +
                "LEFT JOIN TheLoai tl ON tl.id_TheLoai = stl.id_TheLoai " +
                "WHERE COALESCE(cs.DaHuy, 0) = 0 " +
                "  AND (pn.NgayNhap IS NULL OR pn.NgayNhap BETWEEN ? AND ?) " +
                "GROUP BY s.id_Sach, s.TenSach " +
                "ORDER BY SoLuong DESC, s.TenSach";
        return runQuery(sql, from, to);
    }

    private List<Object[]> runQuery(String sql, LocalDate from, LocalDate to) {
        List<Object[]> rows = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(from));
            ps.setDate(2, Date.valueOf(to));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rows.add(new Object[] {
                            rs.getString(1), 
                            rs.getString(2), 
                            rs.getInt(3), 
                            rs.getDate(4) 
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
    }

    private int executeCountQuery(String sql) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            if (rs.next())
                return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}