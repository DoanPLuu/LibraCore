package com.libracoreteam.libracore.bus;

import com.libracoreteam.libracore.dao.PhieuNhapDAO;
import com.libracoreteam.libracore.model.ChiTietPhieuNhap;
import com.libracoreteam.libracore.model.PhieuNhap;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PhieuNhapBUS {

    private final PhieuNhapDAO phieuNhapDAO;

    public PhieuNhapBUS() {
        this(new PhieuNhapDAO());
    }

    public PhieuNhapBUS(PhieuNhapDAO phieuNhapDAO) {
        this.phieuNhapDAO = phieuNhapDAO;
    }

    public List<PhieuNhap> getActive() {
        return phieuNhapDAO.getActive();
    }

    public List<PhieuNhap> getDaHuy() {
        return phieuNhapDAO.getDaHuy();
    }

    public List<PhieuNhap> search(String keyword, boolean onlyDaHuy) {
        String k = keyword == null ? "" : keyword.trim();
        if (k.isEmpty()) {
            return onlyDaHuy ? getDaHuy() : getActive();
        }
        return phieuNhapDAO.search(k, onlyDaHuy);
    }

    public List<ChiTietPhieuNhap> getDetailsByPhieuNhap(int idPhieuNhap) {
        if (idPhieuNhap <= 0) {
            throw new IllegalArgumentException("ID phiếu nhập không hợp lệ");
        }
        return phieuNhapDAO.getDetailsByPhieuNhap(idPhieuNhap);
    }

    public boolean cancel(int idPhieuNhap) {
        if (idPhieuNhap <= 0) {
            throw new IllegalArgumentException("ID phiếu nhập không hợp lệ");
        }
        boolean ok = phieuNhapDAO.cancel(idPhieuNhap);
        if (!ok) {
            throw new RuntimeException("Không thể hủy phiếu nhập này");
        }
        return true;
    }

    public boolean create(PhieuNhap phieuNhap, List<ChiTietPhieuNhap> details) {
        if (phieuNhap == null) {
            throw new IllegalArgumentException("Phiếu nhập không hợp lệ");
        }
        if (details == null || details.isEmpty()) {
            throw new IllegalArgumentException("Phiếu nhập phải có ít nhất 1 dòng chi tiết");
        }
        if (phieuNhap.getIdNhanVien() <= 0) {
            throw new IllegalArgumentException("Nhân viên lập phiếu không hợp lệ");
        }

        List<ChiTietPhieuNhap> normalizedDetails = new ArrayList<ChiTietPhieuNhap>();
        int tongSoLuong = 0;

        for (ChiTietPhieuNhap ct : details) {
            if (ct == null) {
                continue;
            }
            if (ct.getIdSach() <= 0) {
                throw new IllegalArgumentException("ID sách trong chi tiết phiếu nhập không hợp lệ");
            }
            if (ct.getSoLuong() == null || ct.getSoLuong() <= 0) {
                throw new IllegalArgumentException("Số lượng nhập phải lớn hơn 0");
            }
            if (ct.getGiaTien() == null || ct.getGiaTien().compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Đơn giá nhập phải >= 0");
            }

            String maDauSach = ct.getMaDauSach();
            if (maDauSach == null || maDauSach.trim().isEmpty()) {
                ct.setMaDauSach("S-" + ct.getIdSach());
            } else {
                ct.setMaDauSach(maDauSach.trim());
            }

            normalizedDetails.add(ct);
            tongSoLuong += ct.getSoLuong();
        }

        if (normalizedDetails.isEmpty()) {
            throw new IllegalArgumentException("Phiếu nhập phải có ít nhất 1 dòng chi tiết hợp lệ");
        }

        if (phieuNhap.getNgayNhap() == null) {
            phieuNhap.setNgayNhap(LocalDate.now());
        }
        phieuNhap.setSoLuongSach(tongSoLuong);

        String trangThai = phieuNhap.getTrangThai();
        if (trangThai == null || trangThai.trim().isEmpty()) {
            phieuNhap.setTrangThai("DaNhap");
        }

        boolean ok = phieuNhapDAO.insertWithDetails(phieuNhap, normalizedDetails);
        if (!ok) {
            throw new RuntimeException("Không thể lưu phiếu nhập");
        }
        return true;
    }
}
