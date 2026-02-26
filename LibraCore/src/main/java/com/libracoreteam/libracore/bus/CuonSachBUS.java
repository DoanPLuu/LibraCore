package com.libracoreteam.libracore.bus;

import com.libracoreteam.libracore.dao.CuonSachDAO;
import com.libracoreteam.libracore.model.CuonSach;

import java.util.List;

public class CuonSachBUS {

    private final CuonSachDAO cuonSachDAO;

    public CuonSachBUS() {
        this(new CuonSachDAO());
    }

    public CuonSachBUS(CuonSachDAO cuonSachDAO) {
        this.cuonSachDAO = cuonSachDAO;
    }

    public List<CuonSach> getAll() {
        return cuonSachDAO.getAll();
    }

    public List<CuonSach> search(String keyword) {
        String k = keyword == null ? "" : keyword.trim();
        if (k.isEmpty()) {
            return getAll();
        }
        return cuonSachDAO.search(k);
    }

    public boolean softDelete(int idCuonSach, String trangThaiMuon, boolean daHuy) {
        if (idCuonSach <= 0) {
            throw new IllegalArgumentException("ID cuốn sách không hợp lệ");
        }
        if (daHuy) {
            throw new IllegalArgumentException("Cuốn sách này đã huỷ trước đó");
        }
        if ("DangMuon".equals(trangThaiMuon)) {
            throw new IllegalArgumentException("Không thể huỷ cuốn sách đang được mượn");
        }
        boolean ok = cuonSachDAO.softDelete(idCuonSach);
        if (!ok) {
            throw new RuntimeException("Huỷ cuốn sách thất bại");
        }
        return true;
    }
}
