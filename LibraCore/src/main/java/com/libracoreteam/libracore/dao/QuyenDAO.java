package com.libracoreteam.libracore.dao;

import com.libracoreteam.libracore.model.Quyen;
import com.libracoreteam.libracore.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuyenDAO {

    public List<Quyen> getAll() {
        String sql = "SELECT id_Quyen, TenQuyen FROM Quyen ORDER BY id_Quyen ASC";
        List<Quyen> list = new ArrayList<Quyen>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Quyen q = new Quyen();
                q.setIdQuyen(rs.getInt("id_Quyen"));
                q.setTenQuyen(rs.getString("TenQuyen"));
                list.add(q);
            }
        } catch (SQLException e) {
            throw new RuntimeException("QuyenDAO.getAll failed", e);
        }
        return list;
    }
}

