package com.libracoreteam.libracore.bus;

import com.libracoreteam.libracore.dao.NCCDAO;
import com.libracoreteam.libracore.model.NCC;

import java.util.List;

public class NCCBUS {

    private final NCCDAO nccDAO;

    public NCCBUS() {
        this(new NCCDAO());
    }

    public NCCBUS(NCCDAO nccDAO) {
        this.nccDAO = nccDAO;
    }
    public List<NCC> getAll() {
        return nccDAO.getAll();
    }

    public NCC getById(int idNCC) {
        if (idNCC <= 0) {
            throw new IllegalArgumentException("ID Nhà cung cấp không hợp lệ");
        }
        return nccDAO.getById(idNCC);
    }

    public List<NCC> search(String keyword) {
        String k = safeTrim(keyword);
        if (k == null || k.isEmpty()) {
            return getAll();
        }
        return nccDAO.search(k);
    }

    public NCC create(String tenNCC) {
        tenNCC = safeTrim(tenNCC);
        validateForCreateOrUpdate(0, tenNCC);
        NCC ncc = new NCC();
        ncc.setTenNCC(tenNCC);

        boolean ok = nccDAO.insert(ncc);
        if (!ok) {
            throw new RuntimeException("Thêm Nhà cung cấp thất bại (lỗi kết nối database)");
        }
        return ncc;
    }

public boolean update(com.libracoreteam.libracore.model.NCC ncc) {
        if (ncc == null) {
            return false;
        }
        if (ncc.getTenNCC() == null || ncc.getTenNCC().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên nhà cung cấp không được để trống!");
        }
        return nccDAO.update(ncc);
    }

        public boolean softDelete(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Mã nhà cung cấp không hợp lệ!");
        }
        return nccDAO.softDelete(id);
    }

    /* ==================== VALIDATION ==================== */

    private void validateForCreateOrUpdate(int idNCC, String tenNCC) {
        if (tenNCC == null || tenNCC.isEmpty()) {
            throw new IllegalArgumentException("Tên Nhà cung cấp không được để trống");
        }
        if (tenNCC.length() > 255) {
            throw new IllegalArgumentException("Tên Nhà cung cấp quá dài (tối đa 255 ký tự)");
        }
        if (nccDAO.existsByName(tenNCC, idNCC)) {
            throw new IllegalArgumentException("Tên Nhà cung cấp đã tồn tại trong hệ thống");
        }
    }
    private static String safeTrim(String s) {
        return s == null ? null : s.trim();
    }

}