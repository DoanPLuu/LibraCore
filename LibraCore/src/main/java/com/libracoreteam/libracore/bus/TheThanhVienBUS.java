package com.libracoreteam.libracore.bus;

import com.libracoreteam.libracore.dao.DocGiaDAO;
import com.libracoreteam.libracore.dao.TheThanhVienDAO;
import com.libracoreteam.libracore.model.TheThanhVien;
import java.time.LocalDate;
import java.util.List;
import com.libracoreteam.libracore.dao.DocGiaDAO;

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
        if ("BiKhoa".equals(t.getTrangThai())) {
            return false; 
        }
        t.GiaHanThe(soNam);
        return theThanhVienDAO.update(t);
    }
    
    public boolean giaHanTheoThang(TheThanhVien t, int soThang) {
        if ("BiKhoa".equals(t.getTrangThai())) {
            return false; 
        }
        
        t.GiaHanTheoThang(soThang);
        return theThanhVienDAO.update(t);
    }
    
    public boolean giaHanDenNgayCuThe(TheThanhVien t, LocalDate ngayChot) {
        if ("BiKhoa".equals(t.getTrangThai())) {
            return false; 
        }
        
        t.GiaHanDenNgayCuThe(ngayChot);
        return theThanhVienDAO.update(t);
    }

    

    public int toggleLockCard(TheThanhVien t) {
        if ("BiKhoa".equals(t.getTrangThai())) {
            DocGiaDAO dgDao = new DocGiaDAO();
            if (!dgDao.checkDocGiaTonTai(t.getIdDocGia())) {
                return -1;
            }
            
            t.setTrangThai("HoatDong");
        } else {
            t.setTrangThai("BiKhoa");
        }
        
        if (theThanhVienDAO.update(t)) {
            return 1; 
        } else {
            return 0; 
        }
    }
    
    public String kiemTraTheKhaDung(int idThe) {
        TheThanhVien theThanhVien = theThanhVienDAO.getById(idThe);

        if (theThanhVien == null) {
            return "Thẻ không tồn tại trong hệ thống!";
        }

        if (!"HoatDong".equals(theThanhVien.getTrangThai())) {
            return "Thẻ đang bị khóa hoặc tạm ngưng hoạt động!";
        }

        if (theThanhVien.getNgayHetHan() != null && theThanhVien.getNgayHetHan().isBefore(LocalDate.now())) {
            return "Thẻ đã hết hạn vào ngày " + theThanhVien.getNgayHetHan() + ". Vui lòng gia hạn!";
        }

        return null;
    }

    public String getTenDocGiaByThe(int idThe) {
        return theThanhVienDAO.getTenDocGiaByTheId(idThe);
    }
    
    public boolean update(TheThanhVien t) {
        return theThanhVienDAO.update(t);
    }
}