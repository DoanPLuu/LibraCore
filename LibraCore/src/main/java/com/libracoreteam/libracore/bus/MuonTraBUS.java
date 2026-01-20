package com.libracoreteam.libracore.bus;

import com.libracoreteam.libracore.dao.*;
import com.libracoreteam.libracore.model.*;
import java.time.LocalDate;
import java.util.List;

public class MuonTraBUS {
    private PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO();
    private ChiTietPhieuMuonDAO chiTietDAO = new ChiTietPhieuMuonDAO();
    private CuonSachDAO cuonSachDAO = new CuonSachDAO();
    private TheThanhVienDAO theThanhVienDAO=new TheThanhVienDAO();

    public List<PhieuMuon> getAllPhieuMuon() {
        return phieuMuonDAO.getAll();
    }

    public List<ChiTietPhieuMuon> getChiTiet(int idPhieuMuon) {
        return chiTietDAO.getByPhieuMuonId(idPhieuMuon);
    }

    public boolean muonSach(PhieuMuon pm, List<Integer> listIdCuonSach) {
        pm.setTongSoSachMuon(listIdCuonSach.size());
        pm.setTrangThai("DangMuon");
        int idPhieuMoi = phieuMuonDAO.add(pm);

        if (idPhieuMoi == -1) return false;

        for (int idCuonSach : listIdCuonSach) {
            ChiTietPhieuMuon ct = new ChiTietPhieuMuon(idPhieuMoi, idCuonSach, "ChuaTra");
            chiTietDAO.add(ct);

            cuonSachDAO.updateStatus(idCuonSach, "DangMuon");
        }
        return true;
    }

    public boolean traSach(int idChiTiet, int idCuonSach, String tinhTrangSach) {
        boolean ok = chiTietDAO.updateTraSach(idChiTiet, LocalDate.now(), tinhTrangSach);

        if (ok) {
            cuonSachDAO.updateStatus(idCuonSach, "Ranh");
        }
        return ok;
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