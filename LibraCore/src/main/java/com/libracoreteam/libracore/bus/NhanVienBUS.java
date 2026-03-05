/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libracoreteam.libracore.bus;

import com.libracoreteam.libracore.dao.NhanVienDAO;
import com.libracoreteam.libracore.model.NhanVien;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class NhanVienBUS {
    private final NhanVienDAO nhanVienDAO;

    public NhanVienBUS() {
        this.nhanVienDAO = new NhanVienDAO();
    }

    // Lấy danh sách nhân viên đang hoạt động
    public List<NhanVien> getActive() {
        return nhanVienDAO.getActive();
    }

    // Lấy tất cả nhân viên (kể cả đã xóa mềm)
    public List<NhanVien> getAll() {
        return nhanVienDAO.getAll();
    }

    // Lấy nhân viên theo ID
    public NhanVien getById(int id) {
        if (id <= 0) return null;
        return nhanVienDAO.getById(id);
    }

    // Lấy nhân viên theo ID tài khoản
    public NhanVien getByIdTaiKhoan(int idTaiKhoan) {
        if (idTaiKhoan <= 0) return null;
        return nhanVienDAO.getByIdTaiKhoan(idTaiKhoan);
    }

    // Thêm nhân viên mới
    public boolean add(NhanVien nv) {
        if (!validate(nv)) {
            return false;
        }
        return nhanVienDAO.insert(nv);
    }

    // Cập nhật thông tin nhân viên
    public boolean update(NhanVien nv) {
        if (!validate(nv)) {
            return false;
        }
        return nhanVienDAO.update(nv);
    }

    // Xóa mềm nhân viên
    public boolean delete(int idNhanVien) {
        if (idNhanVien <= 0) return false;
        return nhanVienDAO.softDelete(idNhanVien);
    }

    // Tìm kiếm nhân viên
    public List<NhanVien> searchActive(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getActive();
        }
        return nhanVienDAO.searchActive(keyword);
    }

    /* ==================== VALIDATION ==================== */
    
    // Hàm kiểm tra tính hợp lệ của dữ liệu trước khi thêm/sửa
    private boolean validate(NhanVien nv) {
        if (nv == null) return false;

        // Tên không được để trống
        if (nv.getTenNhanVien() == null || nv.getTenNhanVien().trim().isEmpty()) {
            System.err.println("Lỗi: Tên nhân viên không được để trống.");
            return false;
        }

        // Validate số điện thoại (10 số)
        if (nv.getSdt() != null && !nv.getSdt().trim().isEmpty()) {
            if (!nv.getSdt().matches("^\\d{10}$")) {
                System.err.println("Lỗi: Số điện thoại phải bao gồm 10 chữ số.");
                return false;
            }
        }

        // Validate email
        if (nv.getEmail() != null && !nv.getEmail().trim().isEmpty()) {
            if (!nv.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                System.err.println("Lỗi: Định dạng email không hợp lệ.");
                return false;
            }
        }

        // Validate ngày sinh (phải trước ngày hiện tại)
        if (nv.getNgaySinh() != null) {
            if (nv.getNgaySinh().isAfter(LocalDate.now())) {
                System.err.println("Lỗi: Ngày sinh không thể lớn hơn ngày hiện tại.");
                return false;
            }
        }

        return true;
    }
}
