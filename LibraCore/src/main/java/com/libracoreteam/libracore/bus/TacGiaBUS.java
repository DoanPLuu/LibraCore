package com.libracoreteam.libracore.bus;

import com.libracoreteam.libracore.dao.TacGiaDAO;
import com.libracoreteam.libracore.model.TacGia;
import java.time.LocalDate;
import java.util.List;

public class TacGiaBUS {
    private final TacGiaDAO dao = new TacGiaDAO();

    public List<TacGia> getAll() {
        return dao.getAll();
    }
    
    public List<TacGia> getActive() {
        return dao.getActive();
    }
    private void validateTacGia(TacGia tacGia) {

        if (tacGia.getTenTacGia() == null || tacGia.getTenTacGia().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên tác giả không được để trống!");
        }
        if (tacGia.getNgaySinh() != null) {
            LocalDate ngayHienTai = LocalDate.now(); 
            if (tacGia.getNgaySinh().isAfter(ngayHienTai)) {
                throw new IllegalArgumentException("Ngày sinh không hợp lệ! Không được phép nhập ngày lớn hơn ngày hiện tại (" + ngayHienTai + ").");
            }
        }
    }

    public boolean insert(TacGia tacGia) {
        validateTacGia(tacGia);
        return dao.insert(tacGia);
    }
    public boolean update(TacGia tacGia) {
        validateTacGia(tacGia);
        return dao.update(tacGia);
    }

    public boolean softDelete(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Mã tác giả không hợp lệ!");
        }
        return dao.softDelete(id);
    }

    public List<TacGia> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return dao.getAll();
        }
        return dao.search(keyword.trim());
    }
}