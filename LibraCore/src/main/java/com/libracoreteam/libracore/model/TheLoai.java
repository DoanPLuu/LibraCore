/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libracoreteam.libracore.model;

/**
 *
 * @author luuis
 */
public class TheLoai {
    private int idTheLoai;
    private String tenTheLoai;
    
    public TheLoai() {
    }
    
    public TheLoai(int idTheLoai, String tenTheLoai) {
        this.idTheLoai = idTheLoai;
        this.tenTheLoai = tenTheLoai;
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
    
    @Override
    public String toString() {
        return tenTheLoai;
    }
}
