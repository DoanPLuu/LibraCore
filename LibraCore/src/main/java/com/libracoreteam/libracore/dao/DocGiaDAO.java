package com.libracoreteam.libracore.dao;

import com.libracoreteam.libracore.model.DocGia;
import com.libracoreteam.libracore.model.TheThanhVien;
import com.libracoreteam.libracore.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DocGiaDAO {
    public List<DocGia> SelectAll() {
        List<DocGia> list = new ArrayList<>();
        String sql = "SELECT * FROM DocGia WHERE HoatDong = 1";
        try (Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                DocGia docGia = new DocGia();
                docGia.setIdDocGia(resultSet.getInt("id_DocGia"));
                docGia.setTenDocGia(resultSet.getString("TenDocGia"));
                docGia.setDiaChi(resultSet.getString("DiaChi"));
                if (resultSet.getDate("NgaySinh") != null) {
                    docGia.setNgaySinh(resultSet.getDate("NgaySinh").toLocalDate());
                }
                docGia.setSdt(resultSet.getString("SDT"));
                docGia.setEmail(resultSet.getString("Email"));
                docGia.setHoatDong(resultSet.getBoolean("HoatDong"));
                list.add(docGia);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(DocGia docGia) {
        String sqlDocGia = "INSERT INTO DocGia (TenDocGia, DiaChi, NgaySinh, SDT, Email, HoatDong) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlThe = "INSERT INTO TheThanhVien (id_DocGia, NgayCap, NgayHetHan, TrangThai) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            
            try (PreparedStatement psDocGia = conn.prepareStatement(sqlDocGia, Statement.RETURN_GENERATED_KEYS)) {
                psDocGia.setString(1, docGia.getTenDocGia());
                psDocGia.setString(2, docGia.getDiaChi());
                psDocGia.setDate(3, java.sql.Date.valueOf(docGia.getNgaySinh()));
                psDocGia.setString(4, docGia.getSdt());
                psDocGia.setString(5, docGia.getEmail());
                psDocGia.setBoolean(6, true);

                int rowsAffected = psDocGia.executeUpdate();

                if (rowsAffected > 0) {
                    try (ResultSet rs = psDocGia.getGeneratedKeys()) {
                        if (rs.next()) {
                            int idDocGiaMoi = rs.getInt(1);

                            try (PreparedStatement psThe = conn.prepareStatement(sqlThe)) {
                                LocalDate ngayCap = LocalDate.now(); 
                                LocalDate ngayHetHan = ngayCap.plusYears(1);

                                psThe.setInt(1, idDocGiaMoi);
                                psThe.setDate(2, java.sql.Date.valueOf(ngayCap));
                                psThe.setDate(3, java.sql.Date.valueOf(ngayHetHan));
                                psThe.setString(4, "HoatDong");

                                psThe.executeUpdate();
                            }
                        }
                    }

                    
                    conn.commit();
                    return true;

                } else {
                    conn.rollback(); 
                    return false;
                }
            } catch (SQLException ex) {
                conn.rollback(); 
                ex.printStackTrace();
                return false;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(DocGia docGia) {
        String sql = "UPDATE DocGia SET TenDocGia=?, DiaChi=?, NgaySinh=?, SDT=?, Email=? WHERE id_DocGia=?";
        try (Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, docGia.getTenDocGia());
            preparedStatement.setString(2, docGia.getDiaChi());
            preparedStatement.setDate(3, java.sql.Date.valueOf(docGia.getNgaySinh()));
            preparedStatement.setString(4, docGia.getSdt());
            preparedStatement.setString(5, docGia.getEmail());
            preparedStatement.setInt(6, docGia.getIdDocGia());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public boolean delete(int idDocGia) {
        String sql = "UPDATE DocGia SET HoatDong = 0 WHERE id_DocGia = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idDocGia);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean isDangNoSach(int idDocGia) {
        String sql = "SELECT COUNT(*) FROM PhieuMuon pm " +
                     "JOIN TheThanhVien t ON pm.id_TheThanhVien = t.id_TheThanhVien " +
                     "WHERE t.id_DocGia = ? AND pm.TrangThai = 'DangMuon'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idDocGia);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean isTheDangHoatDong(int idDocGia) {
        String sql = "SELECT COUNT(*) FROM TheThanhVien WHERE id_DocGia = ? AND TrangThai = 'HoatDong'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idDocGia);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean checkDocGiaTonTai(int idDocGia) {
        String sql = "SELECT HoatDong FROM DocGia WHERE id_DocGia = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idDocGia);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("HoatDong");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
