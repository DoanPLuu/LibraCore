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
public class PhieuMuon {
     private int idPhieuMuon;
    private Integer idNhanVien;
    private Integer idTheThanhVien;
    private LocalDate ngayMuon;
    private LocalDate ngayHenTra;
    private String trangThai; // "DangMuon", "DaTra", "QuaHan", "DaHuy"
    private int tongSoSachMuon;  
    private List<ChiTietPhieuMuon> chiTiet;
    private TheThanhVien theThanhVien;
    private NhanVien nhanVien;
    
    public PhieuMuon() {
    }
    
    public PhieuMuon(int idPhieuMuon, int idNhanVien, Integer idTheThanhVien, 
                     LocalDate ngayMuon, LocalDate ngayHenTra, String trangThai, int tongSoSachMuon) {
        this.idPhieuMuon = idPhieuMuon;
        this.idNhanVien = idNhanVien;
        this.idTheThanhVien = idTheThanhVien;
        this.ngayMuon = ngayMuon;
        this.ngayHenTra = ngayHenTra;
        this.trangThai = trangThai;
        this.tongSoSachMuon = tongSoSachMuon;
    }
    
    public PhieuMuon(int idNhanVien, Integer idTheThanhVien, 
                     LocalDate ngayMuon, LocalDate ngayHenTra, int tongSoSachMuon) {
        this.idNhanVien = idNhanVien;
        this.idTheThanhVien = idTheThanhVien;
        this.ngayMuon = ngayMuon;
        this.ngayHenTra = ngayHenTra;
        this.trangThai = "DangMuon";
        this.tongSoSachMuon = tongSoSachMuon;
    }
    
    // Getters và Setters (bao gồm tongSoSachMuon)
    public int getIdPhieuMuon() {
        return idPhieuMuon;
    }
    
    public void setIdPhieuMuon(int idPhieuMuon) {
        this.idPhieuMuon = idPhieuMuon;
    }
    
    public int getIdNhanVien() {
        return idNhanVien;
    }
    
    public void setIdNhanVien(int idNhanVien) {
        this.idNhanVien = idNhanVien;
    }
    
    public Integer getIdTheThanhVien() {
        return idTheThanhVien;
    }
    
    public void setIdTheThanhVien(Integer idTheThanhVien) {
        this.idTheThanhVien = idTheThanhVien;
    }
    
    public LocalDate getNgayMuon() {
        return ngayMuon;
    }
    
    public void setNgayMuon(LocalDate ngayMuon) {
        this.ngayMuon = ngayMuon;
    }
    
    public LocalDate getNgayHenTra() {
        return ngayHenTra;
    }
    
    public void setNgayHenTra(LocalDate ngayHenTra) {
        this.ngayHenTra = ngayHenTra;
    }
    
    public String getTrangThai() {
        return trangThai;
    }
    
    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
    
    public int getTongSoSachMuon() {
        return tongSoSachMuon;
    }
    
    public void setTongSoSachMuon(int tongSoSachMuon) {
        this.tongSoSachMuon = tongSoSachMuon;
    }
    
    public List<ChiTietPhieuMuon> getChiTiet() {
        return chiTiet;
    }
    
    public void setChiTiet(List<ChiTietPhieuMuon> chiTiet) {
        this.chiTiet = chiTiet;
    }
    
    public TheThanhVien getTheThanhVien() {
        return theThanhVien;
    }
    
    public void setTheThanhVien(TheThanhVien theThanhVien) {
        this.theThanhVien = theThanhVien;
    }
    
    public NhanVien getNhanVien() {
        return nhanVien;
    }
    
    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }
    
    // Utility methods
    public boolean isDangMuon() {
        return "DangMuon".equals(trangThai);
    }
    
    public boolean isDaTra() {
        return "DaTra".equals(trangThai);
    }
    
    public boolean isQuaHan() {
        return "QuaHan".equals(trangThai);
    }
    
    public boolean isDaHuy() {
        return "DaHuy".equals(trangThai);
    }
    
    @Override
    public String toString() {
        return "PhieuMuon{" +
                "idPhieuMuon=" + idPhieuMuon +
                ", ngayMuon=" + ngayMuon +
                ", tongSoSachMuon=" + tongSoSachMuon +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }
}
