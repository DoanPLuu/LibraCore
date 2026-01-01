/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libracoreteam.libracore.model;

/**
 *
 * @author luuis
 */
public class NXB {
    private int idNXB;
    private String tenNXB;
    private String diaChi;
    private String sdt;
    
    public NXB() {
    }
    
    public NXB(int idNXB, String tenNXB, String diaChi, String sdt) {
        this.idNXB = idNXB;
        this.tenNXB = tenNXB;
        this.diaChi = diaChi;
        this.sdt = sdt;
    }
    
    // Getters và Setters
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
    
    @Override
    public String toString() {
        return tenNXB; //
    }
}
