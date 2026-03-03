package com.libracoreteam.libracore.bus;

import com.libracoreteam.libracore.dao.ThongKeDAO;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;

public class ThongKeBUS {
    private final ThongKeDAO thongKeDAO = new ThongKeDAO();

    // ─── Dashboard summary ───────────────────────────────────────────────────

    public int getTongSoSach() {
        return thongKeDAO.getTongSoSach();
    }

    public int getTongDocGia() {
        return thongKeDAO.getTongDocGia();
    }

    public int getSoPhieuDangMuon() {
        return thongKeDAO.getSoPhieuDangMuon();
    }

    public String getTongDoanhThuPhatFormatted() {
        double doanhThu = thongKeDAO.getTongDoanhThuPhat();
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(doanhThu) + " VNĐ";
    }

    // ─── Thống kê theo ngày ─────────────────────────────────────────────────
    // Trả về List<Object[]>: [TenSach, TheLoai, SoLuong, Ngay]

    public List<Object[]> getSachNhapKho(LocalDate from, LocalDate to) {
        return thongKeDAO.getSachNhapKho(from, to);
    }

    public List<Object[]> getSachDangMuon(LocalDate from, LocalDate to) {
        return thongKeDAO.getSachDangMuon(from, to);
    }

    public List<Object[]> getSachHongHoacMat(LocalDate from, LocalDate to) {
        return thongKeDAO.getSachHongHoacMat(from, to);
    }

    public List<Object[]> getTongSoSachTheoSach(LocalDate from, LocalDate to) {
        return thongKeDAO.getTongSoSachTheoSach(from, to);
    }
}