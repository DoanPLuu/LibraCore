package com.libracoreteam.libracore.bus;

import com.libracoreteam.libracore.dao.NhanVienDAO;
import com.libracoreteam.libracore.model.NhanVien;
import java.util.ArrayList;
import java.util.List;

public class NhanVienBUS {

    private final NhanVienDAO nhanVienDAO;

    public NhanVienBUS() {
        this.nhanVienDAO = new NhanVienDAO();
    }

    public List<NhanVien> getActive() {
        return nhanVienDAO.getActive();
    }

    public List<NhanVien> getAll() {
        return nhanVienDAO.getAll();
    }

    public NhanVien getById(int id) {
        if (id <= 0) throw new IllegalArgumentException("ID nhân viên không hợp lệ.");
        return nhanVienDAO.getById(id);
    }

    public NhanVien getByIdTaiKhoan(int idTaiKhoan) {
        if (idTaiKhoan <= 0) throw new IllegalArgumentException("ID tài khoản không hợp lệ.");
        return nhanVienDAO.getByIdTaiKhoan(idTaiKhoan);
    }

    public boolean add(NhanVien nv) {
        validate(nv);
        return nhanVienDAO.insert(nv);
    }

    public boolean update(NhanVien nv) {
        if (nv == null || nv.getIdNhanVien() <= 0)
            throw new IllegalArgumentException("Nhân viên không hợp lệ.");
        validate(nv);
        return nhanVienDAO.update(nv);
    }

    public boolean delete(int idNhanVien) {
        if (idNhanVien <= 0) throw new IllegalArgumentException("ID nhân viên không hợp lệ.");
        return nhanVienDAO.softDelete(idNhanVien);
    }

    public List<NhanVien> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) return getActive();

        List<NhanVien> all = nhanVienDAO.search(keyword);
        List<NhanVien> uuTien = new ArrayList<>();
        List<NhanVien> lienQuan = new ArrayList<>();

        for (NhanVien nv : all) {
            if (String.valueOf(nv.getIdNhanVien()).equals(keyword.trim())) {
                uuTien.add(nv);
            } else {
                lienQuan.add(nv);
            }
        }
        uuTien.addAll(lienQuan);
        return uuTien;
    }

    /* ==================== VALIDATION ==================== */

    private void validate(NhanVien nv) {
        if (nv == null)
            throw new IllegalArgumentException("Nhân viên không được null.");
        if (nv.getTenNhanVien() == null || nv.getTenNhanVien().trim().isEmpty())
            throw new IllegalArgumentException("Tên nhân viên không được để trống.");
    }
}