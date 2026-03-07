/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libracoreteam.libracore.dao;

import com.libracoreteam.libracore.model.NhanVien;
import com.libracoreteam.libracore.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class NhanVienDAO implements IDAO<NhanVien> {

  private static final String COL_ID = "id_NhanVien";
  private static final String COL_ID_TK = "id_TaiKhoan";
  private static final String COL_TEN = "TenNhanVien";
  private static final String COL_NGAYSINH = "NgaySinh";
  private static final String COL_DIACHI = "DiaChi";
  private static final String COL_SDT = "SDT";
  private static final String COL_EMAIL = "Email";
  private static final String COL_HOAT_DONG = "HoatDong";
  private static final String COL_ANH = "AnhNhanVien";

  public List<NhanVien> getActive() {
    String sql = "SELECT * FROM nhanvien WHERE HoatDong = ? ORDER BY TenNhanVien ASC";
    List<NhanVien> list = new ArrayList<>();
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setBoolean(1, true);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next())
          list.add(map(rs));
      }
      return list;
    } catch (SQLException e) {
      throw new RuntimeException("NhanVienDAO.getActive failed", e);
    }
  }

  @Override
  public List<NhanVien> getAll() {
    String sql = "SELECT * FROM nhanvien ORDER BY TenNhanVien ASC";
    List<NhanVien> list = new ArrayList<>();
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()) {
      while (rs.next())
        list.add(map(rs));
      return list;
    } catch (SQLException e) {
      throw new RuntimeException("NhanVienDAO.getAll failed", e);
    }
  }

  public NhanVien getById(int idNhanVien) {
    String sql = "SELECT * FROM nhanvien WHERE id_NhanVien = ?";
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, idNhanVien);
      try (ResultSet rs = ps.executeQuery()) {
        return rs.next() ? map(rs) : null;
      }
    } catch (SQLException e) {
      throw new RuntimeException("NhanVienDAO.getById failed", e);
    }
  }

  public NhanVien getByIdTaiKhoan(int idTaiKhoan) {
    String sql = "SELECT * FROM nhanvien WHERE id_TaiKhoan = ?";
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, idTaiKhoan);
      try (ResultSet rs = ps.executeQuery()) {
        return rs.next() ? map(rs) : null;
      }
    } catch (SQLException e) {
      throw new RuntimeException("NhanVienDAO.getByIdTaiKhoan failed", e);
    }
  }

  @Override
  public boolean insert(NhanVien nv) {
    String sql = "INSERT INTO nhanvien (id_TaiKhoan, TenNhanVien, NgaySinh, DiaChi, SDT, Email, HoatDong, AnhNhanVien) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      bindUpsert(ps, nv, false);
      if (ps.executeUpdate() == 0)
        return false;
      try (ResultSet keys = ps.getGeneratedKeys()) {
        if (keys.next()) {
          nv.setIdNhanVien(keys.getInt(1));
          return true;
        }
      }
      return false;
    } catch (SQLException e) {
      throw new RuntimeException("NhanVienDAO.insert failed", e);
    }
  }

  @Override
  public boolean update(NhanVien nv) {
    String sql = "UPDATE nhanvien SET id_TaiKhoan=?, TenNhanVien=?, NgaySinh=?, DiaChi=?, SDT=?, Email=?, HoatDong=?, AnhNhanVien=? WHERE id_NhanVien=?";
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      bindUpsert(ps, nv, true);
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      throw new RuntimeException("NhanVienDAO.update failed", e);
    }
  }

  public boolean updateAnh(int idNhanVien, String anhPath) {
    String sql = "UPDATE nhanvien SET AnhNhanVien=? WHERE id_NhanVien=?";
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, anhPath);
      ps.setInt(2, idNhanVien);
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      throw new RuntimeException("NhanVienDAO.updateAnh failed", e);
    }
  }

  @Override
  public boolean softDelete(int idNhanVien) {
    String sql = "UPDATE nhanvien SET HoatDong = ? WHERE id_NhanVien = ?";
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setBoolean(1, false);
      ps.setInt(2, idNhanVien);
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      throw new RuntimeException("NhanVienDAO.softDelete failed", e);
    }
  }

  public List<NhanVien> searchActive(String keyword) {
    String sql = "SELECT * FROM nhanvien WHERE HoatDong = ? AND (TenNhanVien LIKE ? OR SDT LIKE ? OR Email LIKE ?) ORDER BY TenNhanVien ASC";
    String k = "%" + (keyword == null ? "" : keyword.trim()) + "%";
    List<NhanVien> list = new ArrayList<>();
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setBoolean(1, true);
      ps.setString(2, k);
      ps.setString(3, k);
      ps.setString(4, k);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next())
          list.add(map(rs));
      }
      return list;
    } catch (SQLException e) {
      throw new RuntimeException("NhanVienDAO.searchActive failed", e);
    }
  }

  private void bindUpsert(PreparedStatement ps, NhanVien nv, boolean isUpdate) throws SQLException {
    if (nv.getIdTaiKhoan() == null)
      ps.setNull(1, Types.INTEGER);
    else
      ps.setInt(1, nv.getIdTaiKhoan());

    ps.setString(2, nv.getTenNhanVien());

    if (nv.getNgaySinh() == null)
      ps.setNull(3, Types.DATE);
    else
      ps.setDate(3, java.sql.Date.valueOf(nv.getNgaySinh()));

    ps.setString(4, nv.getDiaChi());
    ps.setString(5, nv.getSdt());
    ps.setString(6, nv.getEmail());
    ps.setBoolean(7, nv.isHoatDong());
    ps.setString(8, nv.getAnhNhanVien());

    if (isUpdate)
      ps.setInt(9, nv.getIdNhanVien());
  }

  private NhanVien map(ResultSet rs) throws SQLException {
    NhanVien nv = new NhanVien();
    nv.setIdNhanVien(rs.getInt(COL_ID));

    int idTaiKhoan = rs.getInt(COL_ID_TK);
    nv.setIdTaiKhoan(rs.wasNull() ? null : idTaiKhoan);

    nv.setTenNhanVien(rs.getString(COL_TEN));

    java.sql.Date sqlDate = rs.getDate(COL_NGAYSINH);
    nv.setNgaySinh(sqlDate != null ? sqlDate.toLocalDate() : null);

    nv.setDiaChi(rs.getString(COL_DIACHI));
    nv.setSdt(rs.getString(COL_SDT));
    nv.setEmail(rs.getString(COL_EMAIL));
    nv.setHoatDong(rs.getBoolean(COL_HOAT_DONG));
    nv.setAnhNhanVien(rs.getString(COL_ANH));
    return nv;
  }
}
