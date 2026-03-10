
package com.libracoreteam.libracore.model;


public class NXB {
    private int idNXB;
    private String tenNXB;
    private String diaChi;
    private String sdt;
    private boolean hoatDong;
    
    public NXB() {
    }
    
    public NXB(int idNXB, String tenNXB, String diaChi, String sdt, boolean hoatDong) {
        this.idNXB = idNXB;
        this.tenNXB = tenNXB;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.hoatDong = hoatDong;
    }
    
    public NXB(String tenNXB, String diaChi, String sdt) {
        this.tenNXB = tenNXB;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.hoatDong = true;
    }
    
    public int getIdNXB() {
        return idNXB;
    }
    
    public void setIdNXB(int idNXB) {
        this.idNXB = idNXB;
    }
    
    public String getTenNXB() {
        return tenNXB;
    }
    
    public void setTenNXB(String tenNXB) {
        this.tenNXB = tenNXB;
    }
    
    public String getDiaChi() {
        return diaChi;
    }
    
    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
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
        return tenNXB;
    }
}
