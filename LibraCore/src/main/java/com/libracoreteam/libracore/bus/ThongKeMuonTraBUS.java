/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libracoreteam.libracore.bus;

import com.libracoreteam.libracore.dao.ThongKeMuonTraDAO;
import java.time.LocalDate;
import java.util.List;

public class ThongKeMuonTraBUS {
    private final ThongKeMuonTraDAO dao = new ThongKeMuonTraDAO();

    public List<Object[]> getThongKeMuonTra(LocalDate from, LocalDate to) {
        return dao.getThongKeMuonTra(from, to);
    }
}   