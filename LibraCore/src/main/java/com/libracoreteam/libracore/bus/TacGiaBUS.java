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
}