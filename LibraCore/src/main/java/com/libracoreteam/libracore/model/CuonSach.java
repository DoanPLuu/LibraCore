/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libracoreteam.libracore.model;

public class CuonSach {
    private int idCuonSach;
    private int idSach;
    private String maCuonSach;
    private String tinhTrangSach; // Enum: Tot, Hong, Mat
    private String trangThaiMuon; // Enum: Ranh, DangMuon
    private Boolean daHuy;
    private Integer idChiTietPhieuNhap;
    
    
    private Sach sach;
    
    public CuonSach() {
    }

    public CuonSach(int idCuonSach, int idSach, String tinhTrangSach, String trangThaiMuon, Boolean daHuy) {
        this.idCuonSach = idCuonSach;
        this.idSach = idSach;
        this.tinhTrangSach = tinhTrangSach;
        this.trangThaiMuon = trangThaiMuon;
        this.daHuy = daHuy;
    }
    
    public CuonSach(int idSach, String tinhTrangSach, String trangThaiMuon) {
        this.idSach = idSach;
        this.tinhTrangSach = tinhTrangSach;
        this.trangThaiMuon = trangThaiMuon;
        this.daHuy = false;
    }
    
     // Getters
    public int getIdCuonSach() {
        return idCuonSach;
    }
    
    public int getIdSach() {
        return idSach;
    }

    public String getMaCuonSach() {
        return maCuonSach;
    }
    
    public String getTinhTrangSach() {
        return tinhTrangSach;
    }
    
    public String getTrangThaiMuon() {
        return trangThaiMuon;
    }
    
    public Sach getSach() {
        return sach;
    }
    
    public Integer getidChiTietPhieuNhap(){
        return this.idChiTietPhieuNhap;
    }

    
    
    // Setters
    public void setIdCuonSach(int idCuonSach) {
        this.idCuonSach = idCuonSach;
    }
    
    public void setIdSach(int idSach) {
        this.idSach = idSach;
    }

    public void setMaCuonSach(String maCuonSach) {
        this.maCuonSach = maCuonSach;
    }
    
    public void setTinhTrangSach(String tinhTrangSach) {
        this.tinhTrangSach = tinhTrangSach;
    }
    
    public void setTrangThaiMuon(String trangThaiMuon) {
        this.trangThaiMuon = trangThaiMuon;
    }
    
    public void setSach(Sach sach) {
        this.sach = sach;
    }
    
    public void setIdChiTietPhieuNhap(int idChiTietPhieuNhap) {
        this.idChiTietPhieuNhap = idChiTietPhieuNhap;
    }
    
    public Boolean getDaHuy() {
        return daHuy;
    }
    
    public void setDaHuy(Boolean daHuy) {
        this.daHuy = daHuy;
    }
    
    // Utility methods
    public boolean isRanh() {
        return "Ranh".equals(trangThaiMuon);
    }
    
    public boolean isDangMuon() {
        return "DangMuon".equals(trangThaiMuon);
    }
    
    public boolean isTot() {
        return "Tot".equals(tinhTrangSach);
    }
    
    public boolean isDaHuy() {
        return Boolean.TRUE.equals(daHuy);
    }
    
    @Override
    public String toString() {
        return "CuonSach{" +
                "idCuonSach=" + idCuonSach +
                ", idSach=" + idSach +
                ", maCuonSach='" + maCuonSach + '\'' +
                ", tinhTrangSach='" + tinhTrangSach + '\'' +
                ", trangThaiMuon='" + trangThaiMuon + '\'' +
                '}';
    }
}
