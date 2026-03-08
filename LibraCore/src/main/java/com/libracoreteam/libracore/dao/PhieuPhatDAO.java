package com.libracoreteam.libracore.dao;

import com.libracoreteam.libracore.model.*;
import com.libracoreteam.libracore.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhieuPhatDAO {

  public List<PhieuPhat> getAll() {
    String sql = "SELECT pp.id_PhieuPhat, pp.NgayLap, pp.TienPhatPhaiNop, pp.LyDoPhat, pp.TrangThai, pp.id_NhanVien, "
        + "MAX(d.TenDocGia) AS TenDocGia "
        + "FROM phieuphat pp "
        + "LEFT JOIN chitietphieuphat ctpp ON ctpp.id_PhieuPhat = pp.id_PhieuPhat "
        + "LEFT JOIN chitietphieumuon ctpm ON ctpm.id_ChiTietPhieuMuon = ctpp.id_ChiTietPhieuMuon "
        + "LEFT JOIN phieumuon pm ON pm.id_PhieuMuon = ctpm.id_PhieuMuon "
        + "LEFT JOIN thethanhvien t ON t.id_TheThanhVien = pm.id_TheThanhVien "
        + "LEFT JOIN docgia d ON d.id_DocGia = t.id_DocGia "
        + "GROUP BY pp.id_PhieuPhat ORDER BY pp.id_PhieuPhat DESC";
    List<PhieuPhat> list = new ArrayList<>();
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()) {
      while (rs.next())
        list.add(mapRow(rs));
    } catch (SQLException e) {
      throw new RuntimeException("PhieuPhatDAO.getAll failed", e);
    }
    return list;
  }

  public List<PhieuPhat> searchByStatus(String trangThai) {
    String sql = "SELECT pp.id_PhieuPhat, pp.NgayLap, pp.TienPhatPhaiNop, pp.LyDoPhat, pp.TrangThai, pp.id_NhanVien, "
        + "MAX(d.TenDocGia) AS TenDocGia "
        + "FROM phieuphat pp "
        + "LEFT JOIN chitietphieuphat ctpp ON ctpp.id_PhieuPhat = pp.id_PhieuPhat "
        + "LEFT JOIN chitietphieumuon ctpm ON ctpm.id_ChiTietPhieuMuon = ctpp.id_ChiTietPhieuMuon "
        + "LEFT JOIN phieumuon pm ON pm.id_PhieuMuon = ctpm.id_PhieuMuon "
        + "LEFT JOIN thethanhvien t ON t.id_TheThanhVien = pm.id_TheThanhVien "
        + "LEFT JOIN docgia d ON d.id_DocGia = t.id_DocGia "
        + "WHERE pp.TrangThai = ? GROUP BY pp.id_PhieuPhat ORDER BY pp.id_PhieuPhat DESC";
    List<PhieuPhat> list = new ArrayList<>();
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, trangThai);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next())
          list.add(mapRow(rs));
      }
    } catch (SQLException e) {
      throw new RuntimeException("PhieuPhatDAO.searchByStatus failed", e);
    }
    return list;
  }

  public List<PhieuPhat> search(String keyword, String trangThai) {
    String sql = "SELECT pp.id_PhieuPhat, pp.NgayLap, pp.TienPhatPhaiNop, pp.LyDoPhat, pp.TrangThai, pp.id_NhanVien, "
        + "MAX(d.TenDocGia) AS TenDocGia "
        + "FROM phieuphat pp "
        + "LEFT JOIN chitietphieuphat ctpp ON ctpp.id_PhieuPhat = pp.id_PhieuPhat "
        + "LEFT JOIN chitietphieumuon ctpm ON ctpm.id_ChiTietPhieuMuon = ctpp.id_ChiTietPhieuMuon "
        + "LEFT JOIN phieumuon pm ON pm.id_PhieuMuon = ctpm.id_PhieuMuon "
        + "LEFT JOIN thethanhvien t ON t.id_TheThanhVien = pm.id_TheThanhVien "
        + "LEFT JOIN docgia d ON d.id_DocGia = t.id_DocGia "
        + "GROUP BY pp.id_PhieuPhat HAVING "
        + "(CAST(pp.id_PhieuPhat AS CHAR) LIKE ? OR COALESCE(MAX(d.TenDocGia),'') LIKE ?)"
        + (trangThai != null && !trangThai.isEmpty() ? " AND pp.TrangThai = '" + trangThai + "'" : "")
        + " ORDER BY pp.id_PhieuPhat DESC";
    String k = "%" + (keyword != null ? keyword.trim() : "") + "%";
    List<PhieuPhat> list = new ArrayList<>();
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, k);
      ps.setString(2, k);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next())
          list.add(mapRow(rs));
      }
    } catch (SQLException e) {
      throw new RuntimeException("PhieuPhatDAO.search failed", e);
    }
    return list;
  }

  public List<ChiTietPhieuPhat> getChiTiet(int idPhieuPhat) {
    String sql = "SELECT ct.id_ChiTietPhieuPhat, ct.id_PhieuPhat, ct.id_ChiTietPhieuMuon, ct.id_MucPhat, ct.SoNgayTreHan, ct.TienPhatTra,"
        + "mp.TenMucPhat, mp.LoaiPhat, mp.SoTienPhat, "
        + "cs.MaCuonSach, s.TenSach "
        + "FROM chitietphieuphat ct "
        + "JOIN mucphat mp ON mp.id_MucPhat = ct.id_MucPhat "
        + "JOIN chitietphieumuon ctpm ON ctpm.id_ChiTietPhieuMuon = ct.id_ChiTietPhieuMuon "
        + "JOIN cuonsach cs ON cs.id_CuonSach = ctpm.id_CuonSach "
        + "JOIN sach s ON s.id_Sach = cs.id_Sach "
        + "WHERE ct.id_PhieuPhat = ?";
    List<ChiTietPhieuPhat> list = new ArrayList<>();
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, idPhieuPhat);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          ChiTietPhieuPhat ct = new ChiTietPhieuPhat();
          ct.setIdChiTietPhieuPhat(rs.getInt("id_ChiTietPhieuPhat"));
          ct.setIdPhieuPhat(rs.getInt("id_PhieuPhat"));
          ct.setIdChiTietPhieuMuon(rs.getInt("id_ChiTietPhieuMuon"));
          ct.setIdMucPhat(rs.getInt("id_MucPhat"));
          ct.setSoNgayTreHan(rs.getInt("SoNgayTreHan"));
          ct.setTienPhatTra(rs.getBigDecimal("TienPhatTra"));
          MucPhat mp = new MucPhat();
          mp.setIdMucPhat(rs.getInt("id_MucPhat"));
          mp.setTenMucPhat(rs.getString("TenMucPhat"));
          mp.setLoaiPhat(rs.getString("LoaiPhat"));
          mp.setSoTienPhat(rs.getBigDecimal("SoTienPhat"));
          ct.setMucPhat(mp);
          ChiTietPhieuMuon ctpm = new ChiTietPhieuMuon();
          ctpm.setIdChiTietPhieuMuon(rs.getInt("id_ChiTietPhieuMuon"));
          CuonSach cs = new CuonSach();
          cs.setMaCuonSach(rs.getString("MaCuonSach"));
          Sach s = new Sach();
          s.setTenSach(rs.getString("TenSach"));
          cs.setSach(s);
          ctpm.setCuonSach(cs);
          ct.setChiTietPhieuMuon(ctpm);
          list.add(ct);
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException("PhieuPhatDAO.getChiTiet failed", e);
    }
    return list;
  }

  public int insertWithDetails(PhieuPhat pp, List<ChiTietPhieuPhat> details, Connection conn) throws SQLException {
    String sqlPP = "INSERT INTO phieuphat (NgayLap, TienPhatPhaiNop, LyDoPhat, TrangThai, id_NhanVien) VALUES (?,?,?,?,?)";
    int idPhieuPhat;
    try (PreparedStatement ps = conn.prepareStatement(sqlPP, Statement.RETURN_GENERATED_KEYS)) {
      ps.setDate(1, Date.valueOf(pp.getNgayLap()));
      ps.setBigDecimal(2, pp.getTienPhatPhaiNop());
      ps.setString(3, pp.getLyDoPhat());
      ps.setString(4, "ChuaThu");
      ps.setInt(5, pp.getIdNhanVien());
      ps.executeUpdate();
      try (ResultSet keys = ps.getGeneratedKeys()) {
        keys.next();
        idPhieuPhat = keys.getInt(1);
      }
    }
    String sqlCT = "INSERT INTO chitietphieuphat (id_PhieuPhat, id_ChiTietPhieuMuon, id_MucPhat, SoNgayTreHan, TienPhatTra) VALUES (?,?,?,?,?)";
    for (ChiTietPhieuPhat ct : details) {
      try (PreparedStatement ps = conn.prepareStatement(sqlCT)) {
        ps.setInt(1, idPhieuPhat);
        ps.setInt(2, ct.getIdChiTietPhieuMuon());
        ps.setInt(3, ct.getIdMucPhat());
        ps.setInt(4, ct.getSoNgayTreHan() != null ? ct.getSoNgayTreHan() : 0);
        ps.setBigDecimal(5, ct.getTienPhatTra());
        ps.executeUpdate();
      }
    }
    return idPhieuPhat;
  }

  public boolean updateTrangThai(int id, String trangThai) {
    String sql = "UPDATE phieuphat SET TrangThai=? WHERE id_PhieuPhat=? AND TrangThai='ChuaThu'";
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, trangThai);
      ps.setInt(2, id);
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      throw new RuntimeException("PhieuPhatDAO.updateTrangThai failed", e);
    }
  }

  public boolean hasPendingFineForPhieu(int idPhieuMuon, Connection conn) throws SQLException {
    String sql = "SELECT COUNT(*) FROM phieuphat pp " +
        "JOIN chitietphieuphat ctpp ON ctpp.id_PhieuPhat = pp.id_PhieuPhat " +
        "JOIN chitietphieumuon ctpm ON ctpm.id_ChiTietPhieuMuon = ctpp.id_ChiTietPhieuMuon " +
        "WHERE ctpm.id_PhieuMuon = ? AND pp.TrangThai = 'ChuaThu'";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, idPhieuMuon);
      try (ResultSet rs = ps.executeQuery()) {
        return rs.next() && rs.getInt(1) > 0;
      }
    }
  }

  public boolean updateTrangThaiWithConn(int id, String trangThai, Connection conn) throws SQLException {
    String sql = "UPDATE phieuphat SET TrangThai=? WHERE id_PhieuPhat=? AND TrangThai='ChuaThu'";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, trangThai);
      ps.setInt(2, id);
      return ps.executeUpdate() > 0;
    }
  }

  public Integer findPhieuMuonByPhieuPhat(int idPhieuPhat, Connection conn) throws SQLException {
    String sql = "SELECT DISTINCT ctpm.id_PhieuMuon " +
        "FROM chitietphieuphat ctpp " +
        "JOIN chitietphieumuon ctpm ON ctpm.id_ChiTietPhieuMuon = ctpp.id_ChiTietPhieuMuon " +
        "WHERE ctpp.id_PhieuPhat = ? LIMIT 1";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, idPhieuPhat);
      try (ResultSet rs = ps.executeQuery()) {
        return rs.next() ? rs.getInt("id_PhieuMuon") : null;
      }
    }
  }

  private PhieuPhat mapRow(ResultSet rs) throws SQLException {
    PhieuPhat pp = new PhieuPhat();
    pp.setIdPhieuPhat(rs.getInt("id_PhieuPhat"));
    pp.setNgayLap(rs.getDate("NgayLap") != null ? rs.getDate("NgayLap").toLocalDate() : null);
    pp.setTienPhatPhaiNop(rs.getBigDecimal("TienPhatPhaiNop"));
    pp.setLyDoPhat(rs.getString("LyDoPhat"));
    pp.setTrangThai(rs.getString("TrangThai"));
    pp.setIdNhanVien(rs.getInt("id_NhanVien"));
    pp.setTenDocGia(rs.getString("TenDocGia"));
    return pp;
  }
}
