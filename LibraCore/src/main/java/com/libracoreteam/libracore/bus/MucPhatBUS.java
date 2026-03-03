package com.libracoreteam.libracore.bus;

import com.libracoreteam.libracore.dao.MucPhatDAO;
import com.libracoreteam.libracore.model.MucPhat;
import java.util.List;

public class MucPhatBUS {
    private final MucPhatDAO dao = new MucPhatDAO();

    public List<MucPhat> getAll() {
        return dao.getAll();
    }
    
    public MucPhat getPerDayActive() {
        return dao.getPerDayActive();
    }

    public List<MucPhat> getAllFixedActive() {
        return dao.getAllFixedActive();
    }

    public void insert(MucPhat mp) {
        // Có thể thêm logic kiểm tra dữ liệu ở đây (ví dụ tiền phạt phải > 0)
        boolean ok = dao.insert(mp);
        if (!ok) {
            throw new RuntimeException("Lỗi khi thêm mức phạt mới!");
        }
    }

    public void update(MucPhat mp) {
        boolean ok = dao.update(mp);
        if (!ok) {
            throw new RuntimeException("Lỗi khi cập nhật mức phạt!");
        }
    }
}