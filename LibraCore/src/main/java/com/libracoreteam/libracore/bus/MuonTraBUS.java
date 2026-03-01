package com.libracoreteam.libracore.bus;

import com.libracoreteam.libracore.dao.*;
import com.libracoreteam.libracore.model.*;
import com.libracoreteam.libracore.util.DBConnection; // Nhớ import DBConnection

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class MuonTraBUS {
    private PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO();
    private CuonSachDAO cuonSachDAO = new CuonSachDAO();
    private TheThanhVienDAO theThanhVienDAO = new TheThanhVienDAO();
    

    public List<PhieuMuon> getAllPhieuMuon() {
        return phieuMuonDAO.getAll();
    }

    public List<ChiTietPhieuMuon> getChiTiet(int idPhieuMuon) {
       
        return phieuMuonDAO.getChiTiet(idPhieuMuon);
    }

    
    public boolean muonSach(PhieuMuon phieuMuon, List<Integer> listIdCuonSach) {
        
        phieuMuon.setTongSoSachMuon(listIdCuonSach.size());
        phieuMuon.setTrangThai("DangMuon");

        
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false); 
            
            try {
             
                phieuMuonDAO.insertWithDetails(phieuMuon, listIdCuonSach, conn);
                
                conn.commit(); 
                return true;
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

    
    public boolean traSach(int idChiTiet, int idCuonSach, String tinhTrangSach) {
        
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false); 
            
            try {
              
                ChiTietPhieuMuon ct = new ChiTietPhieuMuon();
                ct.setIdChiTietPhieuMuon(idChiTiet);
                ct.setNgayTra(LocalDate.now());
                ct.setTinhTrangTra("DaTra");
                phieuMuonDAO.updateChiTiet(ct, conn);

                
                phieuMuonDAO.updateCuonSachKhiTra(idCuonSach, tinhTrangSach, conn);
                
               
                
                conn.commit(); 
                return true;
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

    public String getTenCuonSach(int idCuonSach) {
        if (!cuonSachDAO.isAvailable(idCuonSach)) return "Sách bận hoặc hỏng";
        return cuonSachDAO.getTenSachById(idCuonSach);
    }
}