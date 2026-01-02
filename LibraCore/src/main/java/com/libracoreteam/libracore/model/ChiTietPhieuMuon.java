/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libracoreteam.libracore.model;

import java.time.LocalDate;

/**
 *
 * @author luuis
 */
public class ChiTietPhieuMuon {
    private int idChiTietPhieuMuon;
    private int idPhieuMuon;
    private int idCuonSach;  
    private LocalDate ngayTra;
    private String tinhTrangTra; // "ChuaTra", "DaTra", "TreHan", "Hong"
    
    private CuonSach cuonSach; 
    
    public ChiTietPhieuMuon() {
    }
    
    public ChiTietPhieuMuon(int idChiTietPhieuMuon, int idPhieuMuon, int idCuonSach, 
                            LocalDate ngayTra, String tinhTrangTra) {
        this.idChiTietPhieuMuon = idChiTietPhieuMuon;
        this.idPhieuMuon = idPhieuMuon;
        this.idCuonSach = idCuonSach;
        this.ngayTra = ngayTra;
        this.tinhTrangTra = tinhTrangTra;
    }
    
    public ChiTietPhieuMuon(int idPhieuMuon, int idCuonSach, String tinhTrangTra) {
        this.idPhieuMuon = idPhieuMuon;
        this.idCuonSach = idCuonSach;
        this.tinhTrangTra = tinhTrangTra;
    }
    
    // Getters
    public int getIdChiTietPhieuMuon() {
        return idChiTietPhieuMuon;
    }
    
    public int getIdPhieuMuon() {
        return idPhieuMuon;
    }
    
    public int getIdCuonSach() {
        return idCuonSach;
    }
    
    public LocalDate getNgayTra() {
        return ngayTra;
    }
    
    public String getTinhTrangTra() {
        return tinhTrangTra;
    }
    
    public CuonSach getCuonSach() {
        return cuonSach;
    }
    
    // Setters
    public void setIdChiTietPhieuMuon(int idChiTietPhieuMuon) {
        this.idChiTietPhieuMuon = idChiTietPhieuMuon;
    }
    
    public void setIdPhieuMuon(int idPhieuMuon) {
        this.idPhieuMuon = idPhieuMuon;
    }
    
    public void setIdCuonSach(int idCuonSach) {
        this.idCuonSach = idCuonSach;
    }
    
    public void setNgayTra(LocalDate ngayTra) {
        this.ngayTra = ngayTra;
    }
    
    public void setTinhTrangTra(String tinhTrangTra) {
        this.tinhTrangTra = tinhTrangTra;
    }
    
    public void setCuonSach(CuonSach cuonSach) {
        this.cuonSach = cuonSach;
    }
    
    @Override
    public String toString() {
        return "ChiTietPhieuMuon{" +
                "idChiTietPhieuMuon=" + idChiTietPhieuMuon +
                ", idPhieuMuon=" + idPhieuMuon +
                ", idCuonSach=" + idCuonSach +
                ", ngayTra=" + ngayTra +
                ", tinhTrangTra='" + tinhTrangTra + '\'' +
                '}';
    }
}
