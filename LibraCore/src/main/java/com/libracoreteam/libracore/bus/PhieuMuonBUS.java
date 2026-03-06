package com.libracoreteam.libracore.bus;

import com.libracoreteam.libracore.dao.MucPhatDAO;
import com.libracoreteam.libracore.dao.PhieuMuonDAO;
import com.libracoreteam.libracore.dao.PhieuPhatDAO;
import com.libracoreteam.libracore.model.*;
import com.libracoreteam.libracore.util.DBConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class PhieuMuonBUS {

  public static class TraSachItem {
    public final int idChiTietPhieuMuon;
    public final int idCuonSach;
    public final String tinhTrangTra;
    public final Integer idMucPhatFixed;

    public TraSachItem(int idChiTietPhieuMuon, int idCuonSach, String tinhTrangTra, Integer idMucPhatFixed) {
      this.idChiTietPhieuMuon = idChiTietPhieuMuon;
      this.idCuonSach = idCuonSach;
      this.tinhTrangTra = tinhTrangTra;
      this.idMucPhatFixed = idMucPhatFixed;
    }
  }

  private final PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO();
  private final PhieuPhatDAO phieuPhatDAO = new PhieuPhatDAO();
  private final MucPhatDAO mucPhatDAO = new MucPhatDAO();

  public List<PhieuMuon> getAll() {
    return phieuMuonDAO.getAll();
  }

  public List<PhieuMuon> search(String keyword, String trangThai) {
    if (keyword == null || keyword.trim().isEmpty())
      return phieuMuonDAO.getAll();
    return phieuMuonDAO.search(keyword, trangThai);
  }

  public PhieuMuon getById(int id) {
    return phieuMuonDAO.getById(id);
  }

  public List<ChiTietPhieuMuon> getChiTiet(int idPhieuMuon) {
    return phieuMuonDAO.getChiTiet(idPhieuMuon);
  }

  public List<CuonSach> getCuonSachRanh() {
    return phieuMuonDAO.getCuonSachRanh();
  }

  public void addPhieuMuon(int idNhanVien, int idTheThanhVien, LocalDate ngayMuon, LocalDate ngayHenTra,
      List<Integer> idCuonSachList) {
    if (idCuonSachList == null || idCuonSachList.isEmpty())
      throw new IllegalArgumentException("Ph\u1ea3i ch\u1ecdn \u00edt nh\u1ea5t 1 cu\u1ed1n s\u00e1ch");
    if (idCuonSachList.size() > 7)
      throw new IllegalArgumentException(
          "Ch\u1ec9 \u0111\u01b0\u1ee3c m\u01b0\u1ee3n t\u1ed1i \u0111a 7 cu\u1ed1n s\u00e1ch m\u1ed7i l\u1ea7n");
    if (idNhanVien <= 0)
      throw new IllegalArgumentException("Nh\u00e2n vi\u00ean kh\u00f4ng h\u1ee3p l\u1ec7");

    PhieuMuon pm = new PhieuMuon();
    pm.setIdNhanVien(idNhanVien);
    pm.setIdTheThanhVien(idTheThanhVien);
    pm.setNgayMuon(ngayMuon != null ? ngayMuon : LocalDate.now());
    pm.setNgayHenTra(ngayHenTra != null ? ngayHenTra : LocalDate.now().plusDays(14));

    try (Connection conn = DBConnection.getConnection()) {
      conn.setAutoCommit(false);
      try {
        phieuMuonDAO.insertWithDetails(pm, idCuonSachList, conn);
        conn.commit();
      } catch (Exception e) {
        conn.rollback();
        throw new RuntimeException("L\u1ed7i khi th\u00eam phi\u1ebfu m\u01b0\u1ee3n: " + e.getMessage(), e);
      }
    } catch (RuntimeException e) {
      throw e;
    } catch (Exception e) {
      throw new RuntimeException("L\u1ed7i k\u1ebft n\u1ed1i khi th\u00eam phi\u1ebfu m\u01b0\u1ee3n", e);
    }
  }

  public void traSachBulk(int idPhieuMuon, List<TraSachItem> items, int idNhanVien) {
    if (items == null || items.isEmpty())
      throw new IllegalArgumentException("Ph\u1ea3i ch\u1ecdn \u00edt nh\u1ea5t 1 s\u00e1ch \u0111\u1ec3 tr\u1ea3");

    for (TraSachItem item : items) {
      if (!"Tot".equals(item.tinhTrangTra) && item.idMucPhatFixed == null)
        throw new IllegalArgumentException(
            "S\u00e1ch h\u01b0 h\u1ecfng/m\u1ea5t ph\u1ea3i ch\u1ecdn m\u1ee9c ph\u1ea1t");
    }

    PhieuMuon pm = phieuMuonDAO.getById(idPhieuMuon);
    if (pm == null)
      throw new RuntimeException("Phi\u1ebfu m\u01b0\u1ee3n kh\u00f4ng t\u1ed3n t\u1ea1i");

    MucPhat mucPhatPerDay = mucPhatDAO.getPerDayActive();
    LocalDate ngayHenTra = pm.getNgayHenTra();
    LocalDate ngayTra = LocalDate.now();

    try (Connection conn = DBConnection.getConnection()) {
      conn.setAutoCommit(false);
      try {
        List<ChiTietPhieuPhat> dsChiTietPhat = new ArrayList<>();
        java.util.Set<Integer> returningIds = new java.util.HashSet<>();

        for (TraSachItem item : items) {
          returningIds.add(item.idChiTietPhieuMuon);
          long soNgayTre = ngayHenTra != null ? Math.max(0, ChronoUnit.DAYS.between(ngayHenTra, ngayTra)) : 0;

          String dbTinhTrang = mapTinhTrangTraForDB(item.tinhTrangTra, soNgayTre > 0);
          ChiTietPhieuMuon ct = new ChiTietPhieuMuon();
          ct.setIdChiTietPhieuMuon(item.idChiTietPhieuMuon);
          ct.setNgayTra(ngayTra);
          ct.setTinhTrangTra(dbTinhTrang);
          phieuMuonDAO.updateChiTiet(ct, conn);

          String tinhTrangSachMoi = mapTinhTrangSach(item.tinhTrangTra);
          phieuMuonDAO.updateCuonSachKhiTra(item.idCuonSach, tinhTrangSachMoi, conn);

          if (soNgayTre > 0 && mucPhatPerDay != null) {
            ChiTietPhieuPhat ctpp = new ChiTietPhieuPhat();
            ctpp.setIdChiTietPhieuMuon(item.idChiTietPhieuMuon);
            ctpp.setIdMucPhat(mucPhatPerDay.getIdMucPhat());
            ctpp.setSoNgayTreHan((int) soNgayTre);
            ctpp.setTienPhatTra(mucPhatPerDay.getSoTienPhat().multiply(BigDecimal.valueOf(soNgayTre)));
            dsChiTietPhat.add(ctpp);
          }

          if (item.idMucPhatFixed != null) {
            MucPhat fixedMp = mucPhatDAO.getAllFixedActive().stream()
                .filter(mp -> mp.getIdMucPhat() == item.idMucPhatFixed)
                .findFirst().orElse(null);
            if (fixedMp != null) {
              ChiTietPhieuPhat ctpp = new ChiTietPhieuPhat();
              ctpp.setIdChiTietPhieuMuon(item.idChiTietPhieuMuon);
              ctpp.setIdMucPhat(item.idMucPhatFixed);
              ctpp.setSoNgayTreHan(0);
              ctpp.setTienPhatTra(fixedMp.getSoTienPhat());
              dsChiTietPhat.add(ctpp);
            }
          }
        }

        if (!dsChiTietPhat.isEmpty()) {
          BigDecimal tongTien = dsChiTietPhat.stream()
              .map(ChiTietPhieuPhat::getTienPhatTra)
              .reduce(BigDecimal.ZERO, BigDecimal::add);
          PhieuPhat pp = new PhieuPhat(ngayTra, tongTien, "Tr\u1ea3 s\u00e1ch - PM#" + idPhieuMuon, idNhanVien);
          phieuPhatDAO.insertWithDetails(pp, dsChiTietPhat, conn);
        }

        List<ChiTietPhieuMuon> tatCaChiTiet = phieuMuonDAO.getChiTiet(idPhieuMuon);
        boolean truaHet = tatCaChiTiet.stream()
            .allMatch(
                ct -> !"ChuaTra".equals(ct.getTinhTrangTra()) || returningIds.contains(ct.getIdChiTietPhieuMuon()));
        if (truaHet) {
          boolean conPhatChuaThu = phieuPhatDAO.hasPendingFineForPhieu(idPhieuMuon, conn);
          if (!conPhatChuaThu) {
            phieuMuonDAO.updateTrangThaiPhieuMuon(idPhieuMuon, "DaTra", conn);
          }
        }

        conn.commit();
      } catch (Exception e) {
        conn.rollback();
        throw new RuntimeException("L\u1ed7i khi tr\u1ea3 s\u00e1ch: " + e.getMessage(), e);
      }
    } catch (RuntimeException e) {
      throw e;
    } catch (Exception e) {
      throw new RuntimeException("L\u1ed7i k\u1ebft n\u1ed1i khi tr\u1ea3 s\u00e1ch", e);
    }
  }

  public void huyPhieuMuon(int idPhieuMuon, String lyDoHuy) {
    PhieuMuon pm = phieuMuonDAO.getById(idPhieuMuon);
    if (pm == null)
      throw new RuntimeException("Phi\u1ebfu m\u01b0\u1ee3n kh\u00f4ng t\u1ed3n t\u1ea1i");
    if (!"DangMuon".equals(pm.getTrangThai()))
      throw new RuntimeException("Ch\u1ec9 c\u00f3 th\u1ec3 h\u1ee7y phi\u1ebfu \u0111ang m\u01b0\u1ee3n");
    phieuMuonDAO.huyPhieuMuon(idPhieuMuon, lyDoHuy);
  }

  private String mapTinhTrangTraForDB(String tinhTrangTra, boolean treLan) {
    return switch (tinhTrangTra) {
      case "Hong", "Mat" -> "Hong";
      default -> treLan ? "TreHan" : "DaTra";
    };
  }

  private String mapTinhTrangSach(String tinhTrangTra) {
    return switch (tinhTrangTra) {
      case "Hong", "Mat" -> "Hong";
      default -> "Tot";
    };
  }
}
