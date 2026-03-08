
package com.libracoreteam.libracore.model;

import java.time.LocalDate;


public class NhanVien {
  private int idNhanVien;
  private Integer idTaiKhoan;
  private String tenNhanVien;
  private LocalDate ngaySinh;
  private String diaChi;
  private String sdt;
  private String email;
  private boolean hoatDong;
  private String anhNhanVien;

  private TaiKhoan taiKhoan;

  public NhanVien() {
  }

  public NhanVien(int idNhanVien, Integer idTaiKhoan, String tenNhanVien,
      LocalDate ngaySinh, String diaChi, String sdt, String email, boolean hoatDong) {
    this.idNhanVien = idNhanVien;
    this.idTaiKhoan = idTaiKhoan;
    this.tenNhanVien = tenNhanVien;
    this.ngaySinh = ngaySinh;
    this.diaChi = diaChi;
    this.sdt = sdt;
    this.email = email;
    this.hoatDong = hoatDong;
  }

  public NhanVien(String tenNhanVien, LocalDate ngaySinh, String diaChi, String sdt, String email) {
    this.tenNhanVien = tenNhanVien;
    this.ngaySinh = ngaySinh;
    this.diaChi = diaChi;
    this.sdt = sdt;
    this.email = email;
    this.hoatDong = true;
  }

  public int getIdNhanVien() {
    return idNhanVien;
  }

  public Integer getIdTaiKhoan() {
    return idTaiKhoan;
  }

  public String getTenNhanVien() {
    return tenNhanVien;
  }

  public LocalDate getNgaySinh() {
    return ngaySinh;
  }

  public String getDiaChi() {
    return diaChi;
  }

  public String getSdt() {
    return sdt;
  }

  public String getEmail() {
    return email;
  }

  public String getAnhNhanVien() {
    return anhNhanVien;
  }

  public TaiKhoan getTaiKhoan() {
    return taiKhoan;
  }

  public void setIdNhanVien(int idNhanVien) {
    this.idNhanVien = idNhanVien;
  }

  public void setIdTaiKhoan(Integer idTaiKhoan) {
    this.idTaiKhoan = idTaiKhoan;
  }

  public void setTenNhanVien(String tenNhanVien) {
    this.tenNhanVien = tenNhanVien;
  }

  public void setNgaySinh(LocalDate ngaySinh) {
    this.ngaySinh = ngaySinh;
  }

  public void setDiaChi(String diaChi) {
    this.diaChi = diaChi;
  }

  public void setSdt(String sdt) {
    this.sdt = sdt;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setAnhNhanVien(String anhNhanVien) {
    this.anhNhanVien = anhNhanVien;
  }

  public void setTaiKhoan(TaiKhoan taiKhoan) {
    this.taiKhoan = taiKhoan;
  }

  public boolean isHoatDong() {
    return hoatDong;
  }

  public void setHoatDong(boolean hoatDong) {
    this.hoatDong = hoatDong;
  }

  @Override
  public String toString() {
    return "NhanVien{" +
        "idNhanVien=" + idNhanVien +
        ", tenNhanVien='" + tenNhanVien + '\'' +
        ", sdt='" + sdt + '\'' +
        '}';
  }
}
