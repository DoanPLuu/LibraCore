package com.libracoreteam.libracore.dao;

import com.libracoreteam.libracore.model.*;
import com.libracoreteam.libracore.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhieuMuonDAO {

  public List<PhieuMuon> getAll() {
    String sql = "SELECT pm.id_PhieuMuon, pm.id_NhanVien, pm.id_TheThanhVien, pm.NgayMuon, pm.NgayHenTra, pm.TrangThai, pm.TongSoSachMuon, "
        + "d.TenDocGia, d.SDT "
        + "FROM phieumuon pm "
        + "LEFT JOIN thethanhvien t ON t.id_TheThanhVien = pm.id_TheThanhVien "
        + "LEFT JOIN docgia d ON d.id_DocGia = t.id_DocGia "
        + "ORDER BY pm.id_PhieuMuon DESC";
    return queryList(sql);
  }

  public List<PhieuMuon> search(String keyword, String trangThai) {
    String where = "WHERE ";
    if (trangThai != null && !trangThai.isEmpty()) {
      where += "pm.TrangThai = '" + trangThai + "' AND ";
    }
    where += "(COALESCE(d.TenDocGia,'') LIKE ? OR CAST(pm.id_PhieuMuon AS CHAR) LIKE ?)";
    String sql = "SELECT pm.id_PhieuMuon, pm.id_NhanVien, pm.id_TheThanhVien, pm.NgayMuon, pm.NgayHenTra, pm.TrangThai, pm.TongSoSachMuon, "
        + "d.TenDocGia, d.SDT "
        + "FROM phieumuon pm "
        + "LEFT JOIN thethanhvien t ON t.id_TheThanhVien = pm.id_TheThanhVien "
        + "LEFT JOIN docgia d ON d.id_DocGia = t.id_DocGia "
        + where + " ORDER BY pm.id_PhieuMuon DESC";
    String k = "%" + keyword.trim() + "%";
    List<PhieuMuon> list = new ArrayList<>();
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, k);
      ps.setString(2, k);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next())
          list.add(mapRow(rs));
      }
    } catch (SQLException e) {
      throw new RuntimeException("PhieuMuonDAO.search failed", e);
    }
    return list;
  }

  public PhieuMuon getById(int id) {
    String sql = "SELECT pm.id_PhieuMuon, pm.id_NhanVien, pm.id_TheThanhVien, pm.NgayMuon, pm.NgayHenTra, pm.TrangThai, pm.TongSoSachMuon, "
        + "d.TenDocGia, d.SDT "
        + "FROM phieumuon pm "
        + "LEFT JOIN thethanhvien t ON t.id_TheThanhVien = pm.id_TheThanhVien "
        + "LEFT JOIN docgia d ON d.id_DocGia = t.id_DocGia "
        + "WHERE pm.id_PhieuMuon = ?";
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next())
          return mapRow(rs);
      }
    } catch (SQLException e) {
      throw new RuntimeException("PhieuMuonDAO.getById failed", e);
    }
    return null;
  }

  public List<ChiTietPhieuMuon> getChiTiet(int idPhieuMuon) {
    String sql = "SELECT ct.id_ChiTietPhieuMuon, ct.id_PhieuMuon, ct.id_CuonSach, ct.NgayTra, ct.TinhTrangTra, " +
        "c.MaCuonSach, s.TenSach " +
        "FROM chitietphieumuon ct " +
        "JOIN cuonsach c ON c.id_CuonSach = ct.id_CuonSach " +
        "JOIN sach s ON s.id_Sach = c.id_Sach " +
        "WHERE ct.id_PhieuMuon = ?";
    List<ChiTietPhieuMuon> list = new ArrayList<>();
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, idPhieuMuon);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          ChiTietPhieuMuon ct = new ChiTietPhieuMuon();
          ct.setIdChiTietPhieuMuon(rs.getInt("id_ChiTietPhieuMuon"));
          ct.setIdPhieuMuon(rs.getInt("id_PhieuMuon"));
          ct.setIdCuonSach(rs.getInt("id_CuonSach"));
          ct.setNgayTra(rs.getDate("NgayTra") != null ? rs.getDate("NgayTra").toLocalDate() : null);
          ct.setTinhTrangTra(rs.getString("TinhTrangTra"));
          CuonSach c = new CuonSach();
          c.setIdCuonSach(rs.getInt("id_CuonSach"));
          c.setMaCuonSach(rs.getString("MaCuonSach"));
          Sach s = new Sach();
          s.setTenSach(rs.getString("TenSach"));
          c.setSach(s);
          ct.setCuonSach(c);
          list.add(ct);
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException("PhieuMuonDAO.getChiTiet failed", e);
    }
    return list;
  }

  public List<CuonSach> getCuonSachRanh() {
    String sql = "SELECT c.id_CuonSach, c.MaCuonSach, c.TinhTrangSach, c.TrangThaiMuon, c.id_Sach, s.TenSach " +
        "FROM cuonsach c JOIN sach s ON s.id_Sach = c.id_Sach " +
        "WHERE c.TrangThaiMuon = 'Ranh' AND c.TinhTrangSach = 'Tot' AND (c.DaHuy IS NULL OR c.DaHuy = 0) " +
        "ORDER BY s.TenSach, c.MaCuonSach";
    List<CuonSach> list = new ArrayList<>();
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        CuonSach c = new CuonSach();
        c.setIdCuonSach(rs.getInt("id_CuonSach"));
        c.setMaCuonSach(rs.getString("MaCuonSach"));
        c.setTinhTrangSach(rs.getString("TinhTrangSach"));
        c.setTrangThaiMuon(rs.getString("TrangThaiMuon"));
        c.setIdSach(rs.getInt("id_Sach"));
        Sach s = new Sach();
        s.setIdSach(c.getIdSach());
        s.setTenSach(rs.getString("TenSach"));
        c.setSach(s);
        list.add(c);
      }
    } catch (SQLException e) {
      throw new RuntimeException("PhieuMuonDAO.getCuonSachRanh failed", e);
    }
    return list;
  }

  public void insertWithDetails(PhieuMuon pm, List<Integer> idCuonSachList, Connection conn) throws SQLException {
    String sqlPM = "INSERT INTO phieumuon (id_NhanVien, id_TheThanhVien, NgayMuon, NgayHenTra, TrangThai, TongSoSachMuon) VALUES (?,?,?,?,?,?)";
    int idPhieuMuon;
    try (PreparedStatement ps = conn.prepareStatement(sqlPM, Statement.RETURN_GENERATED_KEYS)) {
      ps.setInt(1, pm.getIdNhanVien());
      if (pm.getIdTheThanhVien() != null && pm.getIdTheThanhVien() > 0) {
        ps.setInt(2, pm.getIdTheThanhVien());
      } else {
        ps.setNull(2, Types.INTEGER);
      }
      ps.setDate(3, Date.valueOf(pm.getNgayMuon()));
      ps.setDate(4, Date.valueOf(pm.getNgayHenTra()));
      ps.setString(5, "DangMuon");
      ps.setInt(6, idCuonSachList.size());
      ps.executeUpdate();
      try (ResultSet keys = ps.getGeneratedKeys()) {
        keys.next();
        idPhieuMuon = keys.getInt(1);
      }
    }
    String sqlCT = "INSERT INTO chitietphieumuon (id_PhieuMuon, id_CuonSach, TinhTrangTra) VALUES (?,?,'ChuaTra')";
    String sqlCS = "UPDATE cuonsach SET TrangThaiMuon = 'DangMuon' WHERE id_CuonSach = ?";
    for (int idCS : idCuonSachList) {
      try (PreparedStatement ps = conn.prepareStatement(sqlCT)) {
        ps.setInt(1, idPhieuMuon);
        ps.setInt(2, idCS);
        ps.executeUpdate();
      }
      try (PreparedStatement ps = conn.prepareStatement(sqlCS)) {
        ps.setInt(1, idCS);
        ps.executeUpdate();
      }
    }
  }

  public void updateChiTiet(ChiTietPhieuMuon ct, Connection conn) throws SQLException {
    String sql = "UPDATE chitietphieumuon SET NgayTra=?, TinhTrangTra=? WHERE id_ChiTietPhieuMuon=?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setDate(1, Date.valueOf(ct.getNgayTra()));
      ps.setString(2, ct.getTinhTrangTra());
      ps.setInt(3, ct.getIdChiTietPhieuMuon());
      ps.executeUpdate();
    }
  }

  public void updateCuonSachKhiTra(int idCuonSach, String tinhTrangSach, Connection conn) throws SQLException {
    String trangThaiMuon = "Tot".equals(tinhTrangSach) ? "Ranh" : "Ranh";
    String sql = "UPDATE cuonsach SET TrangThaiMuon=?, TinhTrangSach=? WHERE id_CuonSach=?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, trangThaiMuon);
      ps.setString(2, tinhTrangSach);
      ps.setInt(3, idCuonSach);
      ps.executeUpdate();
    }
  }

  public void updateTrangThaiPhieuMuon(int idPhieuMuon, String trangThai, Connection conn) throws SQLException {
    String sql = "UPDATE phieumuon SET TrangThai=? WHERE id_PhieuMuon=?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, trangThai);
      ps.setInt(2, idPhieuMuon);
      ps.executeUpdate();
    }
  }

  public void huyPhieuMuon(int idPhieuMuon) {
    String sqlCheck = "SELECT id_CuonSach, TinhTrangTra FROM chitietphieumuon WHERE id_PhieuMuon = ?";
    String sqlUpdatePM = "UPDATE phieumuon SET TrangThai='DaHuy' WHERE id_PhieuMuon=? AND TrangThai='DangMuon'";
    String sqlRestoreCS = "UPDATE cuonsach SET TrangThaiMuon='Ranh' WHERE id_CuonSach=? AND TrangThaiMuon='DangMuon'";
    try (Connection conn = DBConnection.getConnection()) {
      conn.setAutoCommit(false);
      List<Integer> idCuonSachList = new ArrayList<>();
      try (PreparedStatement ps = conn.prepareStatement(sqlCheck)) {
        ps.setInt(1, idPhieuMuon);
        try (ResultSet rs = ps.executeQuery()) {
          while (rs.next()) {
            if ("ChuaTra".equals(rs.getString("TinhTrangTra"))) {
              idCuonSachList.add(rs.getInt("id_CuonSach"));
            } else {
              conn.rollback();
              throw new RuntimeException("Không thể hủy: đã có sách được trả");
            }
          }
        }
      }
      try (PreparedStatement ps = conn.prepareStatement(sqlUpdatePM)) {
        ps.setInt(1, idPhieuMuon);
        int rows = ps.executeUpdate();
        if (rows == 0) {
          conn.rollback();
          throw new RuntimeException("Phiếu không tồn tại hoặc không ở trạng thái DangMuon");
        }
      }
      try (PreparedStatement ps = conn.prepareStatement(sqlRestoreCS)) {
        for (int idCS : idCuonSachList) {
          ps.setInt(1, idCS);
          ps.executeUpdate();
          ps.clearParameters();
        }
      }
      conn.commit();
    } catch (SQLException e) {
      throw new RuntimeException("PhieuMuonDAO.huyPhieuMuon failed", e);
    }
  }

  private List<PhieuMuon> queryList(String sql) {
    List<PhieuMuon> list = new ArrayList<>();
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()) {
      while (rs.next())
        list.add(mapRow(rs));
    } catch (SQLException e) {
      throw new RuntimeException("PhieuMuonDAO.queryList failed", e);
    }
    return list;
  }

  private PhieuMuon mapRow(ResultSet rs) throws SQLException {
    PhieuMuon pm = new PhieuMuon();
    pm.setIdPhieuMuon(rs.getInt("id_PhieuMuon"));
    pm.setIdNhanVien(rs.getInt("id_NhanVien"));
    int idTTV = rs.getInt("id_TheThanhVien");
    if (!rs.wasNull())
      pm.setIdTheThanhVien(idTTV);
    pm.setNgayMuon(rs.getDate("NgayMuon") != null ? rs.getDate("NgayMuon").toLocalDate() : null);
    pm.setNgayHenTra(rs.getDate("NgayHenTra") != null ? rs.getDate("NgayHenTra").toLocalDate() : null);
    pm.setTrangThai(rs.getString("TrangThai"));
    pm.setTongSoSachMuon(rs.getInt("TongSoSachMuon"));
    TheThanhVien ttv = new TheThanhVien();
    DocGia dg = new DocGia();
    dg.setTenDocGia(rs.getString("TenDocGia"));
    dg.setSdt(rs.getString("SDT"));
    ttv.setDocGia(dg);
    pm.setTheThanhVien(ttv);
    return pm;
  }
}
