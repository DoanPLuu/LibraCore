package com.libracoreteam.libracore.bus;

import com.libracoreteam.libracore.dao.PhieuPhatDAO;
import com.libracoreteam.libracore.model.ChiTietPhieuPhat;
import com.libracoreteam.libracore.model.PhieuPhat;

import java.util.List;

public class PhieuPhatBUS {

  private final PhieuPhatDAO phieuPhatDAO = new PhieuPhatDAO();

  public List<PhieuPhat> getAll() {
    return phieuPhatDAO.getAll();
  }

  public List<PhieuPhat> search(String keyword, String trangThai) {
    if (keyword == null || keyword.trim().isEmpty())
      return phieuPhatDAO.getAll();
    return phieuPhatDAO.search(keyword, trangThai);
  }

  public List<PhieuPhat> searchWithFilter(String keyword, String trangThai) {
    if (keyword == null || keyword.trim().isEmpty()) {
      if (trangThai == null || trangThai.isEmpty())
        return phieuPhatDAO.getAll();
      return phieuPhatDAO.searchByStatus(trangThai);
    }
    return phieuPhatDAO.search(keyword, trangThai);
  }

  public List<ChiTietPhieuPhat> getChiTiet(int idPhieuPhat) {
    return phieuPhatDAO.getChiTiet(idPhieuPhat);
  }

  public void thanhToan(int idPhieuPhat) {
    boolean ok = phieuPhatDAO.updateTrangThai(idPhieuPhat, "DaThu");
    if (!ok)
      throw new RuntimeException("Phiếu phạt không ở trạng thái Chưa thu hoặc không tồn tại");
  }

  public void huy(int idPhieuPhat) {
    boolean ok = phieuPhatDAO.updateTrangThai(idPhieuPhat, "DaHuy");
    if (!ok)
      throw new RuntimeException("Phiếu phạt không ở trạng thái Chưa thu hoặc không tồn tại");
  }
}
