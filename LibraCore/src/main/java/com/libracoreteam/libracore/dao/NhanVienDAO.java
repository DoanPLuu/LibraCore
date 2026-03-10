
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


public class NhanVienDAO  {

  private static final String COL_ID = "id_NhanVien";
  private static final String COL_ID_TK = "id_TaiKhoan";
  private static final String COL_TEN = "TenNhanVien";
  private static final String COL_NGAYSINH = "NgaySinh";
  private static final String COL_DIACHI = "DiaChi";
  private static final String COL_SDT = "SDT";
  private static final String COL_EMAIL = "Email";
  private static final String COL_HOAT_DONG = "HoatDong";
  private static final String COL_ANH = "AnhNhanVien";

  private List<NhanVien> queryList(String sql, Object[] params) {
    List<NhanVien> list = new ArrayList<>();
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
        }

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        }
    } catch (SQLException e) {
        throw new RuntimeException("NhanVienDAO.queryList failed", e);
    }
    return list;
}

public List<NhanVien> getAll() {
    String sql = "SELECT * FROM nhanvien ORDER BY id_NhanVien ASC";
    return queryList(sql, null);
}

public List<NhanVien> getActive() {
    String sql = "SELECT * FROM nhanvien WHERE HoatDong = 1 ORDER BY id_NhanVien ASC";
    return queryList(sql, null);
}

    public NhanVien getById(int idNhanVien) {
        String sql = "SELECT * FROM nhanvien WHERE id_NhanVien = ?";
        List<NhanVien> list = queryList(sql, new Object[]{idNhanVien});
        return list.isEmpty() ? null : list.get(0);
    }

    public NhanVien getByIdTaiKhoan(int idTaiKhoan) {
        String sql = "SELECT * FROM nhanvien WHERE id_TaiKhoan = ?";
        List<NhanVien> list = queryList(sql, new Object[]{idTaiKhoan});
        return list.isEmpty() ? null : list.get(0);
    }

  
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

  public List<NhanVien> search(String keyword) {
        String sql = "SELECT * FROM nhanvien " +
                    "WHERE HoatDong = 1 " +
                    "AND (CAST(id_NhanVien AS CHAR) LIKE ? " +
                    "OR TenNhanVien LIKE ? " +
                    "OR SDT LIKE ? " +
                    "OR Email LIKE ? " +
                    "OR DATE_FORMAT(NgaySinh, '%d/%m/%Y') LIKE ?) " +
                    "ORDER BY id_NhanVien ASC";
        String param = "%" + keyword.trim() + "%";
        return queryList(sql, new Object[]{param, param, param, param, param});
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
