package com.libracoreteam.libracore.bus;

import com.libracoreteam.libracore.dao.TheLoaiDAO;
import com.libracoreteam.libracore.model.TheLoai;
import java.util.List;

public class TheLoaiBUS {
    private final TheLoaiDAO dao = new TheLoaiDAO();

    public List<TheLoai> getAll() {
        return dao.getAll();
    }
    
    public List<TheLoai> getActive() {
        return dao.getAll(); 
    }

    public boolean insert(TheLoai tl) {
        if (tl.getTenTheLoai() == null || tl.getTenTheLoai().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên thể loại không được để trống");
        }
        return dao.insert(tl); 
    }
    public boolean update(TheLoai tl) {
        if (tl.getTenTheLoai() == null || tl.getTenTheLoai().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên thể loại không được để trống");
        }
        return dao.update(tl);
    }

    public boolean softDelete(int id) {
        if (id <= 0) return false;
        return dao.softDelete(id);
    }

    public List<TheLoai> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAll();
        }
        return dao.search(keyword.trim());
    }
}