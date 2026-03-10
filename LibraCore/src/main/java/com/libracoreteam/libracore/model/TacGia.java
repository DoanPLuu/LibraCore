
package com.libracoreteam.libracore.model;

import java.time.LocalDate;



public class TacGia {
    private int idTacGia;
    private String tenTacGia;
    private LocalDate ngaySinh;
    private String noiSinh;
    private String sdt;
    private boolean hoatDong;
    
    public TacGia() {
    }
    
    public TacGia(int idTacGia, String tenTacGia, LocalDate ngaySinh, String noiSinh, String sdt, boolean hoatDong) {
        this.idTacGia = idTacGia;
        this.tenTacGia = tenTacGia;
        this.ngaySinh = ngaySinh;
        this.noiSinh = noiSinh;
        this.sdt = sdt;
        this.hoatDong = hoatDong;
    }
    
    public TacGia(String tenTacGia, LocalDate ngaySinh, String noiSinh, String sdt) {
        this.tenTacGia = tenTacGia;
        this.ngaySinh = ngaySinh;
        this.noiSinh = noiSinh;
        this.sdt = sdt;
        this.hoatDong = true;
    }
    public int getIdTacGia() {
        return idTacGia;
    }
    
    public void setIdTacGia(int idTacGia) {
        this.idTacGia = idTacGia;
    }
    
    public String getTenTacGia() {
        return tenTacGia;
    }
    
    public void setTenTacGia(String tenTacGia) {
        this.tenTacGia = tenTacGia;
    }
    
    public LocalDate getNgaySinh() {
        return ngaySinh;
    }
    
    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }
    
    public String getNoiSinh() {
        return noiSinh;
    }
    
    public void setNoiSinh(String noiSinh) {
        this.noiSinh = noiSinh;
    }
    
    public String getSdt() {
        return sdt;
    }
    
    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
    
    public boolean isHoatDong() {
        return hoatDong;
    }
    
    public void setHoatDong(boolean hoatDong) {
        this.hoatDong = hoatDong;
    }
    
    @Override
    public String toString() {
        return tenTacGia;
    }
}
