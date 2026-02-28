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
            // NẾU ĐANG BỊ KHÓA MÀ ĐÒI MỞ -> Check xem chủ thẻ còn sống không!
            DocGiaDAO dgDao = new DocGiaDAO();
            if (!dgDao.checkDocGiaTonTai(t.getIdDocGia())) {
                return -1; // Mã lỗi -1: Độc giả đã bị xóa mềm!
            }
            
            // Nếu qua ải thì cho mở khóa
            t.setTrangThai("HoatDong");
        } else {
            // Nếu đang HoatDong thì cho Khóa bình thường
            t.setTrangThai("BiKhoa");
        }
        
        // Cập nhật xuống Database
        if (theThanhVienDAO.update(t)) {
            return 1; // Thành công
        } else {
            return 0; // Lỗi DB
        }
    }
    
    public boolean update(TheThanhVien t) {
        return theThanhVienDAO.update(t);
    }
}