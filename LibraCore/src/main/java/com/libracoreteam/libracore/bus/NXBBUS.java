/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libracoreteam.libracore.bus;

import com.libracoreteam.libracore.dao.NXBDAO;
import com.libracoreteam.libracore.model.NXB;
import static com.mysql.cj.util.StringUtils.safeTrim;
import java.util.List;

/**
 *
 * @author luuis
 */
public class NXBBUS {
    private final NXBDAO nxbDAO;
    
    public NXBBUS(NXBDAO nxbDAO) {
        this.nxbDAO = nxbDAO;
    }
    
    public NXBBUS() {
        this(new NXBDAO());
    }
    
    public List<NXB> getActive() {
        return nxbDAO.getActive();
    }

    public List<NXB> getAll() {
        return nxbDAO.getAll();
    }

    public NXB getById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID NXB không hợp lệ");
        }
        return nxbDAO.getById(id);
    }

    public List<NXB> searchActive(String keyword) {
        String k = keyword != null ? keyword.trim() : "";
        return nxbDAO.searchActive(k);
    }
    
    public NXB create(String tenNXB, String diaChi, String sdt) {
        tenNXB = safeTrim(tenNXB);
        diaChi = safeTrim(diaChi);
        sdt    = safeTrim(sdt);

        validateForCreateOrUpdate(0, tenNXB, diaChi, sdt);

        NXB nxb = new NXB();
        nxb.setTenNXB(tenNXB);
        nxb.setDiaChi(diaChi);
        nxb.setSdt(sdt);
        nxb.setHoatDong(true);

        boolean ok = nxbDAO.insert(nxb);
        if (!ok) {
            throw new RuntimeException("Thêm NXB thất bại (không insert được)");
        }
        return nxb; // đã có id sau khi insert
    }
    
    public boolean update(int id, String tenNXB, String diaChi, String sdt, boolean hoatDong) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID NXB không hợp lệ");
        }

        tenNXB = safeTrim(tenNXB);
        diaChi = safeTrim(diaChi);
        sdt    = safeTrim(sdt);

        validateForCreateOrUpdate(id, tenNXB, diaChi, sdt);

        NXB nxb = new NXB();
        nxb.setIdNXB(id);
        nxb.setTenNXB(tenNXB);
        nxb.setDiaChi(diaChi);
        nxb.setSdt(sdt);
        nxb.setHoatDong(hoatDong);

        return nxbDAO.update(nxb);
    }

    public boolean softDelete(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID NXB không hợp lệ");
        }
        return nxbDAO.softDelete(id);
    }
    
    private void validateForCreateOrUpdate(int id, String tenNXB, String diaChi, String sdt) {
        if (tenNXB == null || tenNXB.isEmpty()) {
            throw new IllegalArgumentException("Tên NXB không được để trống");
        }

        // Ví dụ check độ dài
        if (tenNXB.length() > 255) {
            throw new IllegalArgumentException("Tên NXB quá dài");
        }

        if (sdt != null && !sdt.isEmpty()) {
            // Yêu cầu: Bắt đầu bằng số 0 (số 0 đầu tiên) + theo sau là đúng 9 chữ số nữa (tổng 10 số)
            if (!sdt.matches("0\\d{9}")) {
                throw new IllegalArgumentException("Số điện thoại bắt buộc phải bắt đầu bằng số 0 và có đúng 10 chữ số!");
            }
        }

        // Check trùng tên trong các NXB đang hoạt động
        if (nxbDAO.existsActiveByName(tenNXB, id)) {
            throw new IllegalArgumentException("Tên NXB đã tồn tại (đang hoạt động)");
        }
    }

    private String safeTrim(String s) {
        return s == null ? null : s.trim();
    }
}
