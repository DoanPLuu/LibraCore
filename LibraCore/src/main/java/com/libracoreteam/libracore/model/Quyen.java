/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libracoreteam.libracore.model;

/**
 *
 * @author luuis
 */
public class Quyen {
    private int idQuyen;
    private String tenQuyen;
    
    public Quyen() {
    }
    
    public Quyen(int idQuyen, String tenQuyen) {
        this.idQuyen = idQuyen;
        this.tenQuyen = tenQuyen;
    }
    
    public Quyen(String tenQuyen) {
        this.tenQuyen = tenQuyen;
    }
    
    // Getters
    public int getIdQuyen() {
        return idQuyen;
    }
    
    public String getTenQuyen() {
        return tenQuyen;
    }
    
    // Setters
    public void setIdQuyen(int idQuyen) {
        this.idQuyen = idQuyen;
    }
    
    public void setTenQuyen(String tenQuyen) {
        this.tenQuyen = tenQuyen;
    }
    
    @Override
    public String toString() {
        return tenQuyen;
    }
}
