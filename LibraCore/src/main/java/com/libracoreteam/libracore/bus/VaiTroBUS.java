package com.libracoreteam.libracore.bus;

import com.libracoreteam.libracore.dao.VaiTroDAO;
import com.libracoreteam.libracore.model.VaiTro;
import java.util.List;

public class VaiTroBUS {

    private final VaiTroDAO vaiTroDAO;

    public VaiTroBUS() {
        this.vaiTroDAO = new VaiTroDAO();
    }

    public List<VaiTro> getAll() {
        return vaiTroDAO.getAll();
    }

    public List<VaiTro> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAll();
        }
        return vaiTroDAO.search(keyword);
    }

    public boolean create(String tenVaiTro, List<Integer> quyenIds) {
        if (tenVaiTro == null || tenVaiTro.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên vai trò không được để trống.");
        }
        VaiTro vt = new VaiTro();
        vt.setTenVaiTro(tenVaiTro.trim());
        boolean ok = vaiTroDAO.insert(vt);
        if (!ok) {
            return false;
        }
        vaiTroDAO.setQuyenForVaiTro(vt.getIdVaiTro(), quyenIds);
        return true;
    }

    public boolean update(int idVaiTro, String tenVaiTro, List<Integer> quyenIds) {
        if (idVaiTro <= 0) {
            throw new IllegalArgumentException("Vai trò không hợp lệ.");
        }
        if (tenVaiTro == null || tenVaiTro.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên vai trò không được để trống.");
        }
        VaiTro vt = new VaiTro();
        vt.setIdVaiTro(idVaiTro);
        vt.setTenVaiTro(tenVaiTro.trim());
        boolean ok = vaiTroDAO.update(vt);
        if (!ok) {
            return false;
        }
        vaiTroDAO.setQuyenForVaiTro(idVaiTro, quyenIds);
        return true;
    }

    public boolean delete(int idVaiTro) {
        if (idVaiTro <= 0) {
            throw new IllegalArgumentException("Vai trò không hợp lệ.");
        }
        if (vaiTroDAO.hasTaiKhoanUsingVaiTro(idVaiTro)) {
            throw new IllegalStateException("Không thể xoá vai trò vì đang có tài khoản sử dụng.");
        }
        return vaiTroDAO.delete(idVaiTro);
    }

    public List<Integer> getQuyenIdsByVaiTro(int idVaiTro) {
        return vaiTroDAO.getQuyenIdsByVaiTro(idVaiTro);
    }
}

