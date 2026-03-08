
package com.libracoreteam.libracore.dao;


import com.libracoreteam.libracore.util.DBConnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ThongKeTienPhatDAO {

   
    public List<Object[]> getThongKeTienPhat(LocalDate from, LocalDate to) {
        String sql = "SELECT id_PhieuPhat, NgayLap, LyDoPhat, TrangThai, TienPhatPhaiNop " +
                     "FROM PhieuPhat " +
                     "WHERE NgayLap BETWEEN ? AND ? " +
                     "ORDER BY NgayLap ASC, id_PhieuPhat ASC";
        
        List<Object[]> rows = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setDate(1, Date.valueOf(from));
            ps.setDate(2, Date.valueOf(to));
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rows.add(new Object[]{
                        rs.getInt("id_PhieuPhat"),
                        rs.getDate("NgayLap"),
                        rs.getString("LyDoPhat"),
                        rs.getString("TrangThai"),
                        rs.getDouble("TienPhatPhaiNop")
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
    }
}
