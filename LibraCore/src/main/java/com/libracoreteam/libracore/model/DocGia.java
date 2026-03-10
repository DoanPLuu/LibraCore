
package com.libracoreteam.libracore.model;

import java.time.LocalDate;


public class DocGia {
    private int idDocGia;
    private String tenDocGia;
    private String diaChi;
    private LocalDate ngaySinh;
    private String sdt;
    private String email;
    private boolean hoatDong;
    
    public DocGia() {
    }
    
    public DocGia(int idDocGia, String tenDocGia, String diaChi, LocalDate ngaySinh, String sdt, String email, boolean hoatDong) {
        this.idDocGia = idDocGia;
        this.tenDocGia = tenDocGia;
        this.diaChi = diaChi;
        this.ngaySinh = ngaySinh;
        this.sdt = sdt;
        this.email = email;
        this.hoatDong = hoatDong;
    }
    
    public DocGia(String tenDocGia, String diaChi, LocalDate ngaySinh, String sdt, String email) {
        this.tenDocGia = tenDocGia;
        this.diaChi = diaChi;
        this.ngaySinh = ngaySinh;
        this.sdt = sdt;
        this.email = email;
        this.hoatDong = true;
    }
    
    public int getIdDocGia() {
        return idDocGia;
    }
    
    public String getTenDocGia() {
        return tenDocGia;
    }
    
    public String getDiaChi() {
        return diaChi;
    }
    
    public LocalDate getNgaySinh() {
        return ngaySinh;
    }
    
    public String getSdt() {
        return sdt;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setIdDocGia(int idDocGia) {
        this.idDocGia = idDocGia;
    }
    
    public void setTenDocGia(String tenDocGia) {
        this.tenDocGia = tenDocGia;
    }
    
    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
    
    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }
    
    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public boolean isHoatDong() {
        return hoatDong;
    }
    
    public void setHoatDong(boolean hoatDong) {
        this.hoatDong = hoatDong;
    }
    
    @Override
    public String toString() {
        return "DocGia{" +
                "idDocGia=" + idDocGia +
                ", tenDocGia='" + tenDocGia + '\'' +
                ", sdt='" + sdt + '\'' +
                '}';
    }
}
