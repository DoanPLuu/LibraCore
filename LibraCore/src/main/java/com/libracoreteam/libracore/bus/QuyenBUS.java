package com.libracoreteam.libracore.bus;

import com.libracoreteam.libracore.dao.QuyenDAO;
import com.libracoreteam.libracore.model.Quyen;
import java.util.List;

public class QuyenBUS {

    private final QuyenDAO quyenDAO;

    public QuyenBUS() {
        this.quyenDAO = new QuyenDAO();
    }

    public List<Quyen> getAll() {
        return quyenDAO.getAll();
    }
}

