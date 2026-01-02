/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libracoreteam.libracore.model;

import java.util.List;

/**
 *
 * @author luuis
 */
public class VaiTro {
    private int idVaiTro;
    private String tenVaiTro;
    
    // Quan hệ (optional)
    private List<Quyen> danhSachQuyen;
    
    public VaiTro() {
    }
    
    public VaiTro(int idVaiTro, String tenVaiTro) {
        this.idVaiTro = idVaiTro;
        this.tenVaiTro = tenVaiTro;
    }
    
    public VaiTro(String tenVaiTro) {
        this.tenVaiTro = tenVaiTro;
    }
    
    // Getters
    public int getIdVaiTro() {
        return idVaiTro;
    }
    
    public String getTenVaiTro() {
        return tenVaiTro;
    }
    
    public List<Quyen> getDanhSachQuyen() {
        return danhSachQuyen;
    }
    
    // Setters
    public void setIdVaiTro(int idVaiTro) {
        this.idVaiTro = idVaiTro;
    }
    
    public void setTenVaiTro(String tenVaiTro) {
        this.tenVaiTro = tenVaiTro;
    }
    
    public void setDanhSachQuyen(List<Quyen> danhSachQuyen) {
        this.danhSachQuyen = danhSachQuyen;
    }
    
    @Override
    public String toString() {
        return tenVaiTro;
    }
}
