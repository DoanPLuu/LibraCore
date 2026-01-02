/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libracoreteam.libracore.model;

import java.math.BigDecimal;
/**
 *
 * @author luuis
 */
public class ChiTietPhieuPhat {
    private int idChiTietPhieuPhat;
    private int idPhieuPhat;
    private int idChiTietPhieuMuon;
    private int idMucPhat;
    private Integer soNgayTreHan;
    private BigDecimal tienPhatTra;
    
    private ChiTietPhieuMuon chiTietPhieuMuon;
    private MucPhat mucPhat;
    
    public ChiTietPhieuPhat() {
    }
    
    public ChiTietPhieuPhat(int idChiTietPhieuPhat, int idPhieuPhat, 
                            int idChiTietPhieuMuon, int idMucPhat, 
                            Integer soNgayTreHan, BigDecimal tienPhatTra) {
        this.idChiTietPhieuPhat = idChiTietPhieuPhat;
        this.idPhieuPhat = idPhieuPhat;
        this.idChiTietPhieuMuon = idChiTietPhieuMuon;
        this.idMucPhat = idMucPhat;
        this.soNgayTreHan = soNgayTreHan;
        this.tienPhatTra = tienPhatTra;
    }
    
    public ChiTietPhieuPhat(int idPhieuPhat, int idChiTietPhieuMuon, 
                            int idMucPhat, Integer soNgayTreHan, BigDecimal tienPhatTra) {
        this.idPhieuPhat = idPhieuPhat;
        this.idChiTietPhieuMuon = idChiTietPhieuMuon;
        this.idMucPhat = idMucPhat;
        this.soNgayTreHan = soNgayTreHan;
        this.tienPhatTra = tienPhatTra;
    }
    
    // Getters
    public int getIdChiTietPhieuPhat() {
        return idChiTietPhieuPhat;
    }
    
    public int getIdPhieuPhat() {
        return idPhieuPhat;
    }
    
    public int getIdChiTietPhieuMuon() {
        return idChiTietPhieuMuon;
    }
    
    public int getIdMucPhat() {
        return idMucPhat;
    }
    
    public Integer getSoNgayTreHan() {
        return soNgayTreHan;
    }
    
    public BigDecimal getTienPhatTra() {
        return tienPhatTra;
    }
    
    public ChiTietPhieuMuon getChiTietPhieuMuon() {
        return chiTietPhieuMuon;
    }
    
    public MucPhat getMucPhat() {
        return mucPhat;
    }
    
    // Setters
    public void setIdChiTietPhieuPhat(int idChiTietPhieuPhat) {
        this.idChiTietPhieuPhat = idChiTietPhieuPhat;
    }
    
    public void setIdPhieuPhat(int idPhieuPhat) {
        this.idPhieuPhat = idPhieuPhat;
    }
    
    public void setIdChiTietPhieuMuon(int idChiTietPhieuMuon) {
        this.idChiTietPhieuMuon = idChiTietPhieuMuon;
    }
    
    public void setIdMucPhat(int idMucPhat) {
        this.idMucPhat = idMucPhat;
    }
    
    public void setSoNgayTreHan(Integer soNgayTreHan) {
        this.soNgayTreHan = soNgayTreHan;
    }
    
    public void setTienPhatTra(BigDecimal tienPhatTra) {
        this.tienPhatTra = tienPhatTra;
    }
    
    public void setChiTietPhieuMuon(ChiTietPhieuMuon chiTietPhieuMuon) {
        this.chiTietPhieuMuon = chiTietPhieuMuon;
    }
    
    public void setMucPhat(MucPhat mucPhat) {
        this.mucPhat = mucPhat;
    }
    
    @Override
    public String toString() {
        return "ChiTietPhieuPhat{" +
                "idChiTietPhieuPhat=" + idChiTietPhieuPhat +
                ", idPhieuPhat=" + idPhieuPhat +
                ", soNgayTreHan=" + soNgayTreHan +
                ", tienPhatTra=" + tienPhatTra +
                '}';
    }
}
