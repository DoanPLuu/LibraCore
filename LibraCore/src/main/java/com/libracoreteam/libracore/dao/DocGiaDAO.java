package com.libracoreteam.libracore.dao;

import com.libracoreteam.libracore.model.DocGia;
import com.libracoreteam.libracore.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DocGiaDAO {
    public List<DocGia> SelectAll(){
        List<DocGia> list=new ArrayList<>();
        String sql="SELECT * FROM DocGia WHERE HoatDong = 1";
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            ResultSet resultSet=preparedStatement.executeQuery()){
            while (resultSet.next()){
                DocGia docGia=new DocGia();
                docGia.setIdDocGia(resultSet.getInt("id_DocGia"));
                docGia.setTenDocGia(resultSet.getString("TenDocGia"));
                docGia.setDiaChi(resultSet.getString("DiaChi"));
                if(resultSet.getDate("NgaySinh")!=null){
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

    public boolean insert(DocGia docGia){
        String sql="INSERT INTO DocGia (TenDocGia,DiaChi,NgaySinh,SDT,Email,HoatDong) VALUE (?,?,?,?,?,?)";
        try(Connection connection=DBConnection.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(sql)){
            preparedStatement.setString(1,docGia.getTenDocGia());
            preparedStatement.setString(2, docGia.getDiaChi());
            preparedStatement.setDate(3,java.sql.Date.valueOf(docGia.getNgaySinh()));
            preparedStatement.setString(4,docGia.getSdt());
            preparedStatement.setString(5,docGia.getEmail());
            preparedStatement.setBoolean(6,true);
            return preparedStatement.executeUpdate()>0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(DocGia docGia){
        String sql="UPDATE DocGia SET TenDocGia=?, DiaChi=?, NgaySinh=?, Email=? WHERE id_DocGia=?";
        try(Connection connection=DBConnection.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(sql)){
            preparedStatement.setString(1, docGia.getTenDocGia());
            preparedStatement.setString(2,docGia.getDiaChi());
            preparedStatement.setDate(3,java.sql.Date.valueOf(docGia.getNgaySinh()));
            preparedStatement.setString(4, docGia.getSdt());
            preparedStatement.setString(5, docGia.getEmail());
            preparedStatement.setInt(6,docGia.getIdDocGia());
            return preparedStatement.executeUpdate()>0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int idDocGia){
        String sql="UPDATE DocGia SET HoatDong=0 WHERE id_DocGia=?";
        try(Connection connection=DBConnection.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(sql)){
            preparedStatement.setInt(1,idDocGia);
            return preparedStatement.executeUpdate()>0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}
