/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libracoreteam.libracore.bus;

/**
 *
 * @author Sang
 */
import com.libracoreteam.libracore.dao.ThongKeTienPhatDAO;
import java.time.LocalDate;
import java.util.List;

public class ThongKeTienPhatBUS {
    
    private final ThongKeTienPhatDAO dao = new ThongKeTienPhatDAO();

    // Trả về List<Object[]>: [id_PhieuPhat, NgayLap, LyDoPhat, TrangThai, TienPhatPhaiNop]
    public List<Object[]> getThongKeTienPhat(LocalDate from, LocalDate to) {
        return dao.getThongKeTienPhat(from, to);
    }
}
