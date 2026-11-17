package com.libracoreteam.libracore.bus;

import com.libracoreteam.libracore.dao.ThongKeDAO;
import com.libracoreteam.libracore.model.DashboardStatistics;
import com.libracoreteam.libracore.model.DashboardStatistics.Granularity;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.sql.SQLException;
import java.util.Objects;

public class ThongKeBUS {
    private final ThongKeDAO thongKeDAO = new ThongKeDAO();

    public DashboardStatistics getDashboardStatistics(LocalDate from, LocalDate to, Granularity granularity)
            throws SQLException {
        Objects.requireNonNull(from, "from");
        Objects.requireNonNull(to, "to");
        Objects.requireNonNull(granularity, "granularity");
        if (from.isAfter(to)) {
            throw new IllegalArgumentException("Ngày bắt đầu không được sau ngày kết thúc.");
        }
        return thongKeDAO.getDashboardStatistics(from, to, granularity);
    }

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