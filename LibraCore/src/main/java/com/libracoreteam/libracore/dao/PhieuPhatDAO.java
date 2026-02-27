package com.libracoreteam.libracore.dao;

import com.libracoreteam.libracore.model.*;
import com.libracoreteam.libracore.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhieuPhatDAO {

  public List<PhieuPhat> getAll() {
    String sql = "SELECT pp.id_PhieuPhat, pp.NgayLap, pp.TienPhatPhaiNop, pp.LyDoPhat, pp.TrangThai, pp.id_NhanVien " +
        "FROM phieuphat pp ORDER BY pp.id_PhieuPhat DESC";
    return queryList(sql, null);
  }

  public List<PhieuPhat> search(String keyword, String trangThai) {
    String where = "WHERE ";
    if (trangThai != null && !trangThai.isEmpty()) {
      where += "pp.TrangThai = '" + trangThai + "' AND ";
    }
    where += "CAST(pp.id_PhieuPhat AS CHAR) LIKE ?";
    String sql = "SELECT pp.id_PhieuPhat, pp.NgayLap, pp.TienPhatPhaiNop, pp.LyDoPhat, pp.TrangThai, pp.id_NhanVien " +
        "FROM phieuphat pp " + where + " ORDER BY pp.id_PhieuPhat DESC";
    return queryList(sql, "%" + keyword.trim() + "%");
  }

  public List<ChiTietPhieuPhat> getChiTiet(int idPhieuPhat) {
    String sql = "SELECT ct.id_ChiTietPhieuPhat, ct.id_PhieuPhat, ct.id_ChiTietPhieuMuon, ct.id_MucPhat, ct.SoNgayTreHan, ct.TienPhatTra,"
        +
        "mp.TenMucPhat, mp.LoaiPhat, mp.SoTienPhat, " +
        "cs.MaCuonSach, s.TenSach " +
        "FROM chitietphieuphat ct " +
        "JOIN mucphat mp ON mp.id_MucPhat = ct.id_MucPhat " +
        "JOIN chitietphieumuon ctpm ON ctpm.id_ChiTietPhieuMuon = ct.id_ChiTietPhieuMuon " +
        "JOIN cuonsach cs ON cs.id_CuonSach = ctpm.id_CuonSach " +
        "JOIN sach s ON s.id_Sach = cs.id_Sach " +
        "WHERE ct.id_PhieuPhat = ?";
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

  private List<PhieuPhat> queryList(String sql, String keyword) {
    List<PhieuPhat> list = new ArrayList<>();
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      if (keyword != null)
        ps.setString(1, keyword);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next())
          list.add(mapRow(rs));
      }
    } catch (SQLException e) {
      throw new RuntimeException("PhieuPhatDAO.queryList failed", e);
    }
    return list;
  }

  private PhieuPhat mapRow(ResultSet rs) throws SQLException {
    PhieuPhat pp = new PhieuPhat();
    pp.setIdPhieuPhat(rs.getInt("id_PhieuPhat"));
    pp.setNgayLap(rs.getDate("NgayLap") != null ? rs.getDate("NgayLap").toLocalDate() : null);
    pp.setTienPhatPhaiNop(rs.getBigDecimal("TienPhatPhaiNop"));
    pp.setLyDoPhat(rs.getString("LyDoPhat"));
    pp.setTrangThai(rs.getString("TrangThai"));
    pp.setIdNhanVien(rs.getInt("id_NhanVien"));
    return pp;
  }
}
