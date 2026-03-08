
package com.libracoreteam.libracore.model;

import java.math.BigDecimal;


public class MucPhat {
    private int idMucPhat;
    private String tenMucPhat;
    private String loaiPhat; // "PerDay", "Fixed"
    private BigDecimal soTienPhat;
    private String moTa;
    private boolean hoatDong;
    
    public MucPhat() {
    }
    
    public MucPhat(int idMucPhat, String tenMucPhat, String loaiPhat, 
                   BigDecimal soTienPhat, String moTa) {
        this.idMucPhat = idMucPhat;
        this.tenMucPhat = tenMucPhat;
        this.loaiPhat = loaiPhat;
        this.soTienPhat = soTienPhat;
        this.moTa = moTa;
    }
    
    public MucPhat(String tenMucPhat, String loaiPhat, BigDecimal soTienPhat, String moTa) {
        this.tenMucPhat = tenMucPhat;
        this.loaiPhat = loaiPhat;
        this.soTienPhat = soTienPhat;
        this.moTa = moTa;
    }
    
    public int getIdMucPhat() {
        return idMucPhat;
    }
    
    public String getTenMucPhat() {
        return tenMucPhat;
    }
    
    public String getLoaiPhat() {
        return loaiPhat;
    }
    
    public BigDecimal getSoTienPhat() {
        return soTienPhat;
    }
    
    public String getMoTa() {
        return moTa;
    }
    
    public boolean isHoatDong() {
        return hoatDong;
    }
    
    public void setIdMucPhat(int idMucPhat) {
        this.idMucPhat = idMucPhat;
    }
    
    public void setTenMucPhat(String tenMucPhat) {
        this.tenMucPhat = tenMucPhat;
    }
    
    public void setLoaiPhat(String loaiPhat) {
        this.loaiPhat = loaiPhat;
    }
    
    public void setSoTienPhat(BigDecimal soTienPhat) {
        this.soTienPhat = soTienPhat;
    }
    
    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
    
    public void setHoatDong(boolean hoatDong) {
        this.hoatDong = hoatDong;
    }
    
    public boolean isPerDay() {
        return "PerDay".equals(loaiPhat);
    }
    
    public boolean isFixed() {
        return "Fixed".equals(loaiPhat);
    }
    
    public BigDecimal tinhTienPhat(int soNgayTreHan) {
        if (isPerDay()) {
            return soTienPhat.multiply(BigDecimal.valueOf(soNgayTreHan));
        } else {
            return soTienPhat;
        }
    }
    
    @Override
    public String toString() {
        return "MucPhat{" +
                "idMucPhat=" + idMucPhat +
                ", tenMucPhat='" + tenMucPhat + '\'' +
                ", loaiPhat='" + loaiPhat + '\'' +
                ", soTienPhat=" + soTienPhat +
                '}';
    }
}
