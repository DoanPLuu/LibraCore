package com.libracoreteam.libracore.dao;

import com.libracoreteam.libracore.model.MucPhat;
import com.libracoreteam.libracore.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MucPhatDAO {

  public MucPhat getPerDayActive() {
    String sql = "SELECT id_MucPhat, TenMucPhat, LoaiPhat, SoTienPhat, MoTa FROM mucphat WHERE LoaiPhat = 'PerDay' AND HoatDong = 1 LIMIT 1";
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()) {
      if (rs.next())
        return map(rs);
    } catch (SQLException e) {
      throw new RuntimeException("MucPhatDAO.getPerDayActive failed", e);
    }
    return null;
  }

  public List<MucPhat> getAllFixedActive() {
    String sql = "SELECT id_MucPhat, TenMucPhat, LoaiPhat, SoTienPhat, MoTa FROM mucphat WHERE LoaiPhat = 'Fixed' AND HoatDong = 1 ORDER BY id_MucPhat";
    List<MucPhat> list = new ArrayList<>();
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()) {
      while (rs.next())
        list.add(map(rs));
    } catch (SQLException e) {
      throw new RuntimeException("MucPhatDAO.getAllFixedActive failed", e);
    }
    return list;
  }

  public List<MucPhat> getAll() {
    String sql = "SELECT id_MucPhat, TenMucPhat, LoaiPhat, SoTienPhat, MoTa FROM mucphat ORDER BY LoaiPhat, id_MucPhat";
    List<MucPhat> list = new ArrayList<>();
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()) {
      while (rs.next())
        list.add(map(rs));
    } catch (SQLException e) {
      throw new RuntimeException("MucPhatDAO.getAll failed", e);
    }
    return list;
  }

  public boolean insert(MucPhat mp) {
    String sql = "INSERT INTO mucphat (TenMucPhat, LoaiPhat, SoTienPhat, MoTa, HoatDong) VALUES (?,?,?,?,1)";
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, mp.getTenMucPhat());
      ps.setString(2, mp.getLoaiPhat());
      ps.setBigDecimal(3, mp.getSoTienPhat());
      ps.setString(4, mp.getMoTa());
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      throw new RuntimeException("MucPhatDAO.insert failed", e);
    }
  }

  public boolean update(MucPhat mp) {
    String sql = "UPDATE mucphat SET TenMucPhat=?, LoaiPhat=?, SoTienPhat=?, MoTa=? WHERE id_MucPhat=?";
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, mp.getTenMucPhat());
      ps.setString(2, mp.getLoaiPhat());
      ps.setBigDecimal(3, mp.getSoTienPhat());
      ps.setString(4, mp.getMoTa());
      ps.setInt(5, mp.getIdMucPhat());
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      throw new RuntimeException("MucPhatDAO.update failed", e);
    }
  }

  public boolean setHoatDong(int id, boolean hoatDong) {
    String sql = "UPDATE mucphat SET HoatDong=? WHERE id_MucPhat=?";
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, hoatDong ? 1 : 0);
      ps.setInt(2, id);
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      throw new RuntimeException("MucPhatDAO.setHoatDong failed", e);
    }
  }

  private MucPhat map(ResultSet rs) throws SQLException {
    MucPhat mp = new MucPhat();
    mp.setIdMucPhat(rs.getInt("id_MucPhat"));
    mp.setTenMucPhat(rs.getString("TenMucPhat"));
    mp.setLoaiPhat(rs.getString("LoaiPhat"));
    mp.setSoTienPhat(rs.getBigDecimal("SoTienPhat"));
    mp.setMoTa(rs.getString("MoTa"));
    return mp;
  }
}
