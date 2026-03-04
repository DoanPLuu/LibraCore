package com.libracoreteam.libracore.bus;

import com.libracoreteam.libracore.dao.TacGiaDAO;
import com.libracoreteam.libracore.model.TacGia;
import java.util.List;


public class TacGiaBUS {
    private final TacGiaDAO dao = new TacGiaDAO();

    public List<TacGia> getAll() {
        return dao.getAll();
    }
    
    public List<TacGia> getActive() {
        return dao.getActive();
    }

    public boolean insert(TacGia tacGia) {
        if (tacGia.getTenTacGia() == null || tacGia.getTenTacGia().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên tác giả không được để trống");
        }
        return dao.insert(tacGia);
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