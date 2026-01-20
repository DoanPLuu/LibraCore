package com.libracoreteam.libracore.bus;

import com.libracoreteam.libracore.dao.TheThanhVienDAO;
import com.libracoreteam.libracore.model.TheThanhVien;
import java.time.LocalDate;
import java.util.List;

public class TheThanhVienBUS {
    private TheThanhVienDAO theThanhVienDAO;

    public TheThanhVienBUS() {
        theThanhVienDAO = new TheThanhVienDAO();
    }

    public List<TheThanhVien> getAll() {
        return theThanhVienDAO.getAll();
    }

    public String getTenDocGia(int idDocGia) {
        return theThanhVienDAO.getTenDocGia(idDocGia);
    }

    public boolean createCard(int idDocGia) {
        if (theThanhVienDAO.checkDocGiaHasCard(idDocGia)) {
            return false;
        }

        TheThanhVien t = new TheThanhVien(idDocGia);
        return theThanhVienDAO.add(t);
    }


    public boolean giaHanThe(TheThanhVien t, int soNam) {
        t.GiaHanThe(soNam);
        return theThanhVienDAO.update(t);
    }

    public boolean toggleLockCard(TheThanhVien t) {
        if ("BiKhoa".equals(t.getTrangThai())) {
            t.setTrangThai("HoatDong");
        } else {
            t.setTrangThai("BiKhoa");
        }
        return theThanhVienDAO.update(t);
    }
}