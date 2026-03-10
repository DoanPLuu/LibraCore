
package com.libracoreteam.libracore.model;


public class TheLoai {
    private int idTheLoai;
    private String tenTheLoai;
    private boolean hoatDong;
    
    public TheLoai() {
    }
    
    public TheLoai(int idTheLoai, String tenTheLoai, boolean hoatDong) {
        this.idTheLoai = idTheLoai;
        this.tenTheLoai = tenTheLoai;
        this.hoatDong = hoatDong;
    }
    
    public TheLoai(String tenTheLoai) {
        this.tenTheLoai = tenTheLoai;
        this.hoatDong = true;
    }
    
    public int getIdTheLoai() {
        return idTheLoai;
    }
    
    public void setIdTheLoai(int idTheLoai) {
        this.idTheLoai = idTheLoai;
    }
    
    public String getTenTheLoai() {
        return tenTheLoai;
    }
    
    public void setTenTheLoai(String tenTheLoai) {
        this.tenTheLoai = tenTheLoai;
    }
    
    public boolean isHoatDong() {
        return hoatDong;
    }
    
    public void setHoatDong(boolean hoatDong) {
        this.hoatDong = hoatDong;
    }
    
    @Override
    public String toString() {
        return tenTheLoai;
    }
}
