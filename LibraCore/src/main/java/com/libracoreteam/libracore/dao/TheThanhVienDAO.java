package com.libracoreteam.libracore.dao;

import com.libracoreteam.libracore.model.TheThanhVien;
import com.libracoreteam.libracore.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TheThanhVienDAO {
    public List<TheThanhVien> getAll(){
        List<TheThanhVien> list=new ArrayList<>();
        String sql="SELECT t.*, d.TenDocGia FROM TheThanhVien t "+"JOIN DocGia d ON t.id_DocGia = d.id_DocGia";
        try(Connection connection= DBConnection.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            ResultSet resultSet=preparedStatement.executeQuery()){

            while(resultSet.next()){
                TheThanhVien theThanhVien=new TheThanhVien();
                theThanhVien.setIdTheThanhVien(resultSet.getInt("id_TheThanhVien"));
                theThanhVien.setIdDocGia(resultSet.getInt("id_DocGia"));

                if(resultSet.getDate("NgayCap")!=null) theThanhVien.setNgayCap(resultSet.getDate("NgayCap").toLocalDate());
                if(resultSet.getDate("NgayHetHan")!=null) theThanhVien.setNgayHetHan(resultSet.getDate("NgayHetHan").toLocalDate());

                theThanhVien.setTrangThai(resultSet.getString("TrangThai"));
                
                list.add(theThanhVien);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    public boolean add(TheThanhVien theThanhVien){
        String sql="INSERT INTO TheThanhVien (id_DocGia, NgayCap, NgayHetHan, TrangThai) VALUES (?, ?, ?, ?)";
        try(Connection connection=DBConnection.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(sql)){

            preparedStatement.setInt(1,theThanhVien.getIdDocGia());
            preparedStatement.setDate(2, Date.valueOf(theThanhVien.getNgayCap()));
            preparedStatement.setDate(3,Date.valueOf(theThanhVien.getNgayHetHan()));
            preparedStatement.setString(4,theThanhVien.getTrangThai());

            return preparedStatement.executeUpdate()>0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(TheThanhVien theThanhVien){
        String sql="UPDATE TheThanhVien SET ngayHetHan = ?, TrangThai = ? WHERE id_TheThanhVien = ?";
        try(Connection connection= DBConnection.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(sql)){

            preparedStatement.setDate(1,Date.valueOf(theThanhVien.getNgayHetHan()));
            preparedStatement.setString(2,theThanhVien.getTrangThai());
            preparedStatement.setInt(3, theThanhVien.getIdTheThanhVien());

            return preparedStatement.executeUpdate()>0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkDocGiaHasCard(int idDocGia){
        String sql="SELECT COUNT(*) FROM TheThanhVien WHERE id_DocGia = ?";
        try(Connection connection=DBConnection.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(sql)){

            preparedStatement.setInt(1,idDocGia);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()) return resultSet.getInt(1)>0;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public String getTenDocGia(int idDocGia) {
        String sql = "SELECT TenDocGia FROM DocGia WHERE id_DocGia = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idDocGia);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getString("TenDocGia");
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy tên độc giả ID " + idDocGia);
            e.printStackTrace();
        }
        return "Unknown";
    }

    public com.libracoreteam.libracore.model.TheThanhVien getById(int idThe) {
        String sql = "SELECT * FROM TheThanhVien WHERE id_TheThanhVien = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idThe);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                com.libracoreteam.libracore.model.TheThanhVien t = new com.libracoreteam.libracore.model.TheThanhVien();
                t.setIdTheThanhVien(rs.getInt("id_TheThanhVien"));
                t.setIdDocGia(rs.getInt("id_DocGia"));
                if (rs.getDate("NgayCap") != null) t.setNgayCap(rs.getDate("NgayCap").toLocalDate());
                if (rs.getDate("NgayHetHan") != null) t.setNgayHetHan(rs.getDate("NgayHetHan").toLocalDate());
                t.setTrangThai(rs.getString("TrangThai"));
                return t;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getTenDocGiaByTheId(int idThe) {
        String sql = "SELECT d.TenDocGia FROM DocGia d " +
                "JOIN TheThanhVien t ON d.id_DocGia = t.id_DocGia " +
                "WHERE t.id_TheThanhVien = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idThe);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getString("TenDocGia");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Không xác định";
    }


}
