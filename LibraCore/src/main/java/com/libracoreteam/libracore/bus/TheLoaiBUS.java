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
        return dao.getActive();
    }

    public boolean insert(TheLoai tl) {
        if (tl.getTenTheLoai() == null || tl.getTenTheLoai().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên thể loại không được để trống");
        }
        return dao.insert(tl); // Hàm insert này là anh em mình vừa thêm vào DAO ở Lỗi số 5 đó
    }
}