
package com.libracoreteam.libracore.model;

import java.math.BigDecimal;


public class ChiTietPhieuNhap {
    private int idChiTietPhieuNhap;
    private int idPhieuNhap;
    private int idSach;
    private Integer soLuong;
    private BigDecimal giaTien;
    
    private Sach sach;
    
    public ChiTietPhieuNhap() {
    }
    
    public ChiTietPhieuNhap(int idChiTietPhieuNhap, int idPhieuNhap, int idSach,
                            Integer soLuong, BigDecimal giaTien) {
        this.idChiTietPhieuNhap = idChiTietPhieuNhap;
        this.idPhieuNhap = idPhieuNhap;
        this.idSach = idSach;
        this.soLuong = soLuong;
        this.giaTien = giaTien;
    }
    
    public ChiTietPhieuNhap(int idPhieuNhap, int idSach, Integer soLuong,
                            BigDecimal giaTien) {
        this.idPhieuNhap = idPhieuNhap;
        this.idSach = idSach;
        this.soLuong = soLuong;
        this.giaTien = giaTien;
    }
    
    public int getIdChiTietPhieuNhap() {
        return idChiTietPhieuNhap;
    }
    
    public int getIdPhieuNhap() {
        return idPhieuNhap;
    }
    
    public int getIdSach() {
        return idSach;
    }
    
    public Integer getSoLuong() {
        return soLuong;
    }
    
    public BigDecimal getGiaTien() {
        return giaTien;
    }
    
    public Sach getSach() {
        return sach;
    }
    
    public void setIdChiTietPhieuNhap(int idChiTietPhieuNhap) {
        this.idChiTietPhieuNhap = idChiTietPhieuNhap;
    }
    
    public void setIdPhieuNhap(int idPhieuNhap) {
        this.idPhieuNhap = idPhieuNhap;
    }
    
    public void setIdSach(int idSach) {
        this.idSach = idSach;
    }
    
    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }
    
    public void setGiaTien(BigDecimal giaTien) {
        this.giaTien = giaTien;
    }
    
    public void setSach(Sach sach) {
        this.sach = sach;
    }
    
    public BigDecimal tinhThanhTien() {
        if (soLuong != null && giaTien != null) {
            return giaTien.multiply(BigDecimal.valueOf(soLuong));
        }
        return BigDecimal.ZERO;
    }
    
    @Override
    public String toString() {
        return "ChiTietPhieuNhap{" +
                "idChiTietPhieuNhap=" + idChiTietPhieuNhap +
                ", idPhieuNhap=" + idPhieuNhap +
                ", idSach=" + idSach +
                ", soLuong=" + soLuong +
                ", giaTien=" + giaTien +
                '}';
    }
}
