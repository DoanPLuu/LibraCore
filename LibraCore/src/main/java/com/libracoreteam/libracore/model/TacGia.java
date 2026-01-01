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
public class TacGia {
    private int idTacGia;
    private String tenTacGia;
    private LocalDate ngaySinh;
    private String noiSinh;
    private String sdt;
    
    public TacGia() {
    }
    
    public TacGia(int idTacGia, String tenTacGia, LocalDate ngaySinh, String noiSinh, String sdt) {
        this.idTacGia = idTacGia;
        this.tenTacGia = tenTacGia;
        this.ngaySinh = ngaySinh;
        this.noiSinh = noiSinh;
        this.sdt = sdt;
    }
    
    // Getters và Setters
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
    
    @Override
    public String toString() {
        return tenTacGia;
    }
}
