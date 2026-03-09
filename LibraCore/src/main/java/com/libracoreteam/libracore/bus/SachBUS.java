package com.libracoreteam.libracore.bus;

import com.libracoreteam.libracore.dao.NXBDAO;
import com.libracoreteam.libracore.dao.SachDAO;
import com.libracoreteam.libracore.dao.TacGiaDAO;
import com.libracoreteam.libracore.dao.TheLoaiDAO;
import com.libracoreteam.libracore.model.NXB;
import com.libracoreteam.libracore.model.Sach;
import com.libracoreteam.libracore.model.TacGia;
import com.libracoreteam.libracore.model.TheLoai;

import java.time.Year;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class SachBUS {

    private final SachDAO sachDAO;
    private final NXBDAO nxbDAO;
    private final TacGiaDAO tacGiaDAO;
    private final TheLoaiDAO theLoaiDAO;

    public SachBUS(SachDAO sachDAO, NXBDAO nxbDAO, TacGiaDAO tacGiaDAO, TheLoaiDAO theLoaiDAO) {
        this.sachDAO = sachDAO;
        this.nxbDAO = nxbDAO;
        this.tacGiaDAO = tacGiaDAO;
        this.theLoaiDAO = theLoaiDAO;
    }

    public SachBUS() {
        this(new SachDAO(), new NXBDAO(), new TacGiaDAO(), new TheLoaiDAO());
    }

    /* ==================== READ ==================== */

    public List<Sach> getActive() {
        return sachDAO.getActive();
    }

    public List<Sach> getAll() {
        return sachDAO.getAll();
    }

    public Sach getById(int idSach) {
        if (idSach <= 0) {
            throw new IllegalArgumentException("ID sách không hợp lệ");
        }
        return sachDAO.getById(idSach);
    }

    public List<Sach> searchActive(String keyword) {
        String k = keyword != null ? keyword.trim() : "";
        return sachDAO.searchActive(k);
    }

    public List<Sach> searchByTacGia(String keyword) {
        String k = keyword != null ? keyword.trim() : "";
        return sachDAO.searchByTacGia(k);
    }

    public List<NXB> getNXBActive() {
        return nxbDAO.getActive();
    }

    public List<TacGia> getTacGiaActive() {
        return tacGiaDAO.getActive();
    }

    public List<TheLoai> getTheLoaiActive() {
        // Đổi thành getAll() vì hàm này ở DAO đã được thiết lập để chỉ lấy dữ liệu đang
        // hoạt động
        return theLoaiDAO.getAll();
    }

    public List<Integer> getTacGiaIdsBySach(int idSach) {
        if (idSach <= 0)
            return new ArrayList<>();
        return sachDAO.getTacGiaIdsBySach(idSach);
    }

    public List<Integer> getTheLoaiIdsBySach(int idSach) {
        if (idSach <= 0)
            return new ArrayList<>();
        return sachDAO.getTheLoaiIdsBySach(idSach);
    }

    /* ==================== WRITE ==================== */

    public Sach create(
            String tenSach,
            Integer idNXB,
            String namXBText,
            String soTrangText,
            String moTa,
            List<Integer> tacGiaIds,
            List<Integer> theLoaiIds) {
        tenSach = safeTrim(tenSach);
        moTa = safeTrim(moTa);

        Integer namXB = parseNullableInt(namXBText, "Năm xuất bản");
        Integer soTrang = parseNullableInt(soTrangText, "Số trang");

        List<Integer> tgIds = normalizeIds(tacGiaIds);
        List<Integer> tlIds = normalizeIds(theLoaiIds);

        validateForCreateOrUpdate(0, tenSach, idNXB, namXB, soTrang, moTa, tgIds, tlIds);

        Sach s = new Sach();
        s.setTenSach(tenSach);
        s.setIdNXB(normalizeNullableFk(idNXB));
        s.setNamXuatBan(namXB);
        s.setSoTrang(soTrang);
        s.setMoTa(moTa);
        s.setHoatDong(true);

        boolean ok = sachDAO.insertWithRelations(s, tgIds, tlIds);
        if (!ok) {
            throw new RuntimeException("Thêm sách thất bại (không insert được)");
        }
        return s;
    }

    public boolean update(
            int idSach,
            String tenSach,
            Integer idNXB,
            String namXBText,
            String soTrangText,
            String moTa,
            boolean hoatDong,
            List<Integer> tacGiaIds,
            List<Integer> theLoaiIds) {
        if (idSach <= 0) {
            throw new IllegalArgumentException("ID sách không hợp lệ");
        }

        tenSach = safeTrim(tenSach);
        moTa = safeTrim(moTa);

        Integer namXB = parseNullableInt(namXBText, "Năm xuất bản");
        Integer soTrang = parseNullableInt(soTrangText, "Số trang");

        List<Integer> tgIds = normalizeIds(tacGiaIds);
        List<Integer> tlIds = normalizeIds(theLoaiIds);

        validateForCreateOrUpdate(idSach, tenSach, idNXB, namXB, soTrang, moTa, tgIds, tlIds);

        Sach s = new Sach();
        s.setIdSach(idSach);
        s.setTenSach(tenSach);
        s.setIdNXB(normalizeNullableFk(idNXB));
        s.setNamXuatBan(namXB);
        s.setSoTrang(soTrang);
        s.setMoTa(moTa);
        s.setHoatDong(hoatDong);

        return sachDAO.updateWithRelations(s, tgIds, tlIds);
    }

    public boolean softDelete(int idSach) {
        if (idSach <= 0) {
            throw new IllegalArgumentException("ID sách không hợp lệ");
        }
        return sachDAO.softDelete(idSach);
    }

    /* ==================== VALIDATION ==================== */

    private void validateForCreateOrUpdate(
            int idSach,
            String tenSach,
            Integer idNXB,
            Integer namXB,
            Integer soTrang,
            String moTa,
            List<Integer> tacGiaIds,
            List<Integer> theLoaiIds) {
        if (tenSach == null || tenSach.isEmpty()) {
            throw new IllegalArgumentException("Tên sách không được để trống");
        }
        if (tenSach.length() > 255) {
            throw new IllegalArgumentException("Tên sách quá dài");
        }

        // NXB: cho phép null; nếu có thì phải > 0
        if (idNXB != null && idNXB <= 0) {
            throw new IllegalArgumentException("Nhà xuất bản không hợp lệ");
        }

        if (namXB != null) {
            int current = Year.now().getValue();
            if (namXB < 1901 || namXB > current + 1) {
                throw new IllegalArgumentException("Năm xuất bản phải từ 1901 đến " + (current + 1));
            }
        }

        if (soTrang != null) {
            if (soTrang <= 0) {
                throw new IllegalArgumentException("Số trang phải > 0");
            }
        }

        if (moTa != null && moTa.length() > 1000) {
            throw new IllegalArgumentException("Mô tả quá dài");
        }

        // Tạm thời: yêu cầu tối thiểu 1 tác giả + 1 thể loại (thường đúng nghiệp vụ)
        if (tacGiaIds == null || tacGiaIds.isEmpty()) {
            throw new IllegalArgumentException("Vui lòng chọn ít nhất 1 tác giả");
        }
        if (theLoaiIds == null || theLoaiIds.isEmpty()) {
            throw new IllegalArgumentException("Vui lòng chọn ít nhất 1 thể loại");
        }

        if (sachDAO.existsActiveByTenSach(tenSach, idSach)) {
            throw new IllegalArgumentException("Tên sách đã tồn tại (đang hoạt động)");
        }
    }

    /* ==================== HELPERS ==================== */

    private static String safeTrim(String s) {
        return s == null ? null : s.trim();
    }

    private static Integer normalizeNullableFk(Integer id) {
        if (id == null)
            return null;
        return id > 0 ? id : null;
    }

    private static Integer parseNullableInt(String text, String fieldName) {
        String s = safeTrim(text);
        if (s == null || s.isEmpty())
            return null;
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + " phải là số hợp lệ");
        }
    }

    private static List<Integer> normalizeIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty())
            return new ArrayList<>();
        Set<Integer> set = new LinkedHashSet<>();
        for (Integer id : ids) {
            if (id == null)
                continue;
            if (id > 0)
                set.add(id);
        }
        return new ArrayList<>(set);
    }
}
