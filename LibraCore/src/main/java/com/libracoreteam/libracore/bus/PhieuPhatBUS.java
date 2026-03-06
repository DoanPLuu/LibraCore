package com.libracoreteam.libracore.bus;

import com.libracoreteam.libracore.dao.PhieuMuonDAO;
import com.libracoreteam.libracore.dao.PhieuPhatDAO;
import com.libracoreteam.libracore.model.ChiTietPhieuMuon;
import com.libracoreteam.libracore.model.ChiTietPhieuPhat;
import com.libracoreteam.libracore.model.PhieuPhat;
import com.libracoreteam.libracore.util.DBConnection;

import java.sql.Connection;
import java.util.List;

public class PhieuPhatBUS {

  private final PhieuPhatDAO phieuPhatDAO = new PhieuPhatDAO();
  private final PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO();

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
    try (Connection conn = DBConnection.getConnection()) {
      conn.setAutoCommit(false);
      try {
        boolean ok = phieuPhatDAO.updateTrangThaiWithConn(idPhieuPhat, "DaThu", conn);
        if (!ok) {
          conn.rollback();
          throw new RuntimeException("Phiếu phạt không ở trạng thái Chưa thu hoặc không tồn tại");
        }

        Integer idPhieuMuon = phieuPhatDAO.findPhieuMuonByPhieuPhat(idPhieuPhat, conn);
        if (idPhieuMuon != null) {
          boolean conPhat = phieuPhatDAO.hasPendingFineForPhieu(idPhieuMuon, conn);
          if (!conPhat) {
            List<ChiTietPhieuMuon> chiTiet = phieuMuonDAO.getChiTiet(idPhieuMuon);
            boolean tatCaDaTra = chiTiet.stream()
                .allMatch(ct -> !"ChuaTra".equals(ct.getTinhTrangTra()));
            if (tatCaDaTra) {
              phieuMuonDAO.updateTrangThaiPhieuMuon(idPhieuMuon, "DaTra", conn);
            }
          }
        }

        conn.commit();
      } catch (RuntimeException e) {
        conn.rollback();
        throw e;
      } catch (Exception e) {
        conn.rollback();
        throw new RuntimeException("Lỗi khi thanh toán phiếu phạt", e);
      }
    } catch (RuntimeException e) {
      throw e;
    } catch (Exception e) {
      throw new RuntimeException("Lỗi kết nối khi thanh toán", e);
    }
  }

  public void huy(int idPhieuPhat) {
    boolean ok = phieuPhatDAO.updateTrangThai(idPhieuPhat, "DaHuy");
    if (!ok)
      throw new RuntimeException("Phiếu phạt không ở trạng thái Chưa thu hoặc không tồn tại");
  }
}
