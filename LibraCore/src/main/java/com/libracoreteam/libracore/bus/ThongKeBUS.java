package com.libracoreteam.libracore.bus;

import com.libracoreteam.libracore.dao.ThongKeDAO;
import com.libracoreteam.libracore.dao.ThongKeDAO;
import java.text.DecimalFormat;

public class ThongKeBUS {
    private ThongKeDAO thongKeDAO;

    public ThongKeBUS() {
        thongKeDAO = new ThongKeDAO();
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

    // Trả về chuỗi đã format tiền tệ (VD: 150,000 VNĐ)
    public String getTongDoanhThuPhatFormatted() {
        double doanhThu = thongKeDAO.getTongDoanhThuPhat();
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(doanhThu) + " VNĐ";
    }
}