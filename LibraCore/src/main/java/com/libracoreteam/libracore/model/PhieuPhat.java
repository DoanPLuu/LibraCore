
package com.libracoreteam.libracore.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


public class PhieuPhat {
  private int idPhieuPhat;
  private LocalDate ngayLap;
  private BigDecimal tienPhatPhaiNop;
  private String lyDoPhat;
  private String trangThai;
  private int idNhanVien;
  private String tenDocGia;

  private List<ChiTietPhieuPhat> chiTiet;
  private NhanVien nhanVien;

  public PhieuPhat() {
  }

  public PhieuPhat(int idPhieuPhat, LocalDate ngayLap, BigDecimal tienPhatPhaiNop,
      String lyDoPhat, String trangThai, int idNhanVien) {
    this.idPhieuPhat = idPhieuPhat;
    this.ngayLap = ngayLap;
    this.tienPhatPhaiNop = tienPhatPhaiNop;
    this.lyDoPhat = lyDoPhat;
    this.trangThai = trangThai;
    this.idNhanVien = idNhanVien;
  }

  public PhieuPhat(LocalDate ngayLap, BigDecimal tienPhatPhaiNop,
      String lyDoPhat, int idNhanVien) {
    this.ngayLap = ngayLap;
    this.tienPhatPhaiNop = tienPhatPhaiNop;
    this.lyDoPhat = lyDoPhat;
    this.trangThai = "ChuaThu";
    this.idNhanVien = idNhanVien;
  }

  public int getIdPhieuPhat() {
    return idPhieuPhat;
  }

  public LocalDate getNgayLap() {
    return ngayLap;
  }

  public BigDecimal getTienPhatPhaiNop() {
    return tienPhatPhaiNop;
  }

  public String getLyDoPhat() {
    return lyDoPhat;
  }

  public String getTrangThai() {
    return trangThai;
  }

  public int getIdNhanVien() {
    return idNhanVien;
  }

  public List<ChiTietPhieuPhat> getChiTiet() {
    return chiTiet;
  }

  public NhanVien getNhanVien() {
    return nhanVien;
  }

  // Setters
  public void setIdPhieuPhat(int idPhieuPhat) {
    this.idPhieuPhat = idPhieuPhat;
  }

  public void setNgayLap(LocalDate ngayLap) {
    this.ngayLap = ngayLap;
  }

  public void setTienPhatPhaiNop(BigDecimal tienPhatPhaiNop) {
    this.tienPhatPhaiNop = tienPhatPhaiNop;
  }

  public void setLyDoPhat(String lyDoPhat) {
    this.lyDoPhat = lyDoPhat;
  }

  public void setTrangThai(String trangThai) {
    this.trangThai = trangThai;
  }

  public void setIdNhanVien(int idNhanVien) {
    this.idNhanVien = idNhanVien;
  }

  public void setChiTiet(List<ChiTietPhieuPhat> chiTiet) {
    this.chiTiet = chiTiet;
  }

  public void setNhanVien(NhanVien nhanVien) {
    this.nhanVien = nhanVien;
  }

  public String getTenDocGia() {
    return tenDocGia;
  }

  public void setTenDocGia(String tenDocGia) {
    this.tenDocGia = tenDocGia;
  }

  public boolean isDaThu() {
    return "DaThu".equals(trangThai);
  }

  public boolean isChuaThu() {
    return "ChuaThu".equals(trangThai);
  }

  public boolean isDaHuy() {
    return "DaHuy".equals(trangThai);
  }

  @Override
  public String toString() {
    return "PhieuPhat{" +
        "idPhieuPhat=" + idPhieuPhat +
        ", ngayLap=" + ngayLap +
        ", tienPhatPhaiNop=" + tienPhatPhaiNop +
        ", trangThai='" + trangThai + '\'' +
        '}';
  }
}
