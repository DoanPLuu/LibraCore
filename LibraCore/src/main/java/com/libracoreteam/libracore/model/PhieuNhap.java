/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libracoreteam.libracore.model;

import java.time.LocalDate;
import java.util.List;
/**
 *
 * @author luuis
 */
public class PhieuNhap {
     private int idPhieuNhap;
    private Integer idNCC;
    private LocalDate ngayNhap;
    private Integer soLuongSach;
    private String loaiPhieuNhap; // "Mua", "Tang"
    private int idNhanVien;
    private String trangThai; // "DaNhap", "DaHuy", "ChuaNhap"
    
    private List<ChiTietPhieuNhap> chiTiet;
    private NCC ncc;
    private NhanVien nhanVien;
    
    public PhieuNhap() {
    }
    
    public PhieuNhap(int idPhieuNhap, Integer idNCC, LocalDate ngayNhap, 
                     Integer soLuongSach, String loaiPhieuNhap, int idNhanVien, String trangThai) {
        this.idPhieuNhap = idPhieuNhap;
        this.idNCC = idNCC;
        this.ngayNhap = ngayNhap;
        this.soLuongSach = soLuongSach;
        this.loaiPhieuNhap = loaiPhieuNhap;
        this.idNhanVien = idNhanVien;
        this.trangThai = trangThai;
    }
    
    public PhieuNhap(Integer idNCC, LocalDate ngayNhap, Integer soLuongSach, 
                     String loaiPhieuNhap, int idNhanVien) {
        this.idNCC = idNCC;
        this.ngayNhap = ngayNhap;
        this.soLuongSach = soLuongSach;
        this.loaiPhieuNhap = loaiPhieuNhap;
        this.idNhanVien = idNhanVien;
        this.trangThai = "ChuaNhap";
    }
    
    // Getters
    public int getIdPhieuNhap() {
        return idPhieuNhap;
    }
    
    public Integer getIdNCC() {
        return idNCC;
    }
    
    public LocalDate getNgayNhap() {
        return ngayNhap;
    }
    
    public Integer getSoLuongSach() {
        return soLuongSach;
    }
    
    public String getLoaiPhieuNhap() {
        return loaiPhieuNhap;
    }
    
    public int getIdNhanVien() {
        return idNhanVien;
    }
    
    public List<ChiTietPhieuNhap> getChiTiet() {
        return chiTiet;
    }
    
    public NCC getNcc() {
        return ncc;
    }
    
    public NhanVien getNhanVien() {
        return nhanVien;
    }
    
    // Setters
    public void setIdPhieuNhap(int idPhieuNhap) {
        this.idPhieuNhap = idPhieuNhap;
    }
    
    public void setIdNCC(Integer idNCC) {
        this.idNCC = idNCC;
    }
    
    public void setNgayNhap(LocalDate ngayNhap) {
        this.ngayNhap = ngayNhap;
    }
    
    public void setSoLuongSach(Integer soLuongSach) {
        this.soLuongSach = soLuongSach;
    }
    
    public void setLoaiPhieuNhap(String loaiPhieuNhap) {
        this.loaiPhieuNhap = loaiPhieuNhap;
    }
    
    public void setIdNhanVien(int idNhanVien) {
        this.idNhanVien = idNhanVien;
    }
    
    public void setChiTiet(List<ChiTietPhieuNhap> chiTiet) {
        this.chiTiet = chiTiet;
    }
    
    public void setNcc(NCC ncc) {
        this.ncc = ncc;
    }
    
    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }
    
    public String getTrangThai() {
        return trangThai;
    }
    
    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
    
    // Utility methods
    public boolean isDaNhap() {
        return "DaNhap".equals(trangThai);
    }
    
    public boolean isDaHuy() {
        return "DaHuy".equals(trangThai);
    }
    
    public boolean isChuaNhap() {
        return "ChuaNhap".equals(trangThai);
    }
    
    @Override
    public String toString() {
        return "PhieuNhap{" +
                "idPhieuNhap=" + idPhieuNhap +
                ", ngayNhap=" + ngayNhap +
                ", soLuongSach=" + soLuongSach +
                ", loaiPhieuNhap='" + loaiPhieuNhap + '\'' +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }
}
